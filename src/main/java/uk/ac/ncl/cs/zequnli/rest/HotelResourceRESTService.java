package uk.ac.ncl.cs.zequnli.rest;

import uk.ac.ncl.cs.zequnli.data.HotelRepository;
import uk.ac.ncl.cs.zequnli.model.Hotel;
import uk.ac.ncl.cs.zequnli.service.HotelRegistration;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * @Auther: Li Zequn
 * Date: 06/11/13
 */
@Path("/hotels")
@RequestScoped
public class HotelResourceRESTService {
    @Inject
    private Logger log;

    @Inject
    private HotelRepository repository;

    @Inject
    private Validator validator;

    @Inject
    private HotelRegistration registration;

    /**
     * query method
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hotel> listAllFlight() {
        return repository.findAllOrderedByNo();
    }

    /**
     * get method
     */
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel loopUpFlightById(@PathParam("id") Long id) {
        Hotel f = repository.findById(id);
        if (f == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return f;
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(Hotel hotel) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateFlight(hotel);


            registration.register(hotel);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("hotel", "hotel taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private void validateFlight(Hotel hotel){
        Set<ConstraintViolation<Hotel>> violations = validator.validate(hotel);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        if (hotelNameAlreadyExist(hotel.getHotelName())) {
            throw new ValidationException("Unique hotel no Violation");
        }

    }


    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }


    private boolean hotelNameAlreadyExist(String name){
        Hotel hotel = null;
        try {
            hotel = repository.findByHotelName(name);
        } catch (NoResultException e) {
            // ignore
        }
        return hotel != null;
    }


}
