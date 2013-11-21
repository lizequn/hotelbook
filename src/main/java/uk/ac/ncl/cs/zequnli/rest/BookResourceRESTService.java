package uk.ac.ncl.cs.zequnli.rest;

import uk.ac.ncl.cs.zequnli.data.BookRepository;
import uk.ac.ncl.cs.zequnli.model.BookInfo;
import uk.ac.ncl.cs.zequnli.service.BookRegistration;

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
@Path("/books")
@RequestScoped
public class BookResourceRESTService {
    @Inject
    private Logger log;



    @Inject
    private BookRepository bookRepository;

    @Inject
    private Validator validator;

    @Inject
    private BookRegistration registration;

    /**
     * query method
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookInfo> listAllBookInfo() {
        return bookRepository.findAllOrderedById();
    }


    /**
     * get method
     */
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BookInfo loopUpBookInfoById(@PathParam("id") Long id) {
        BookInfo f = bookRepository.findById(id);
        if (f == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return f;
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(BookInfo bookinfo) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateBook(bookinfo);
//            log.info("current:"+bookinfo.getHotel().getCurrent_num());
//            log.info("max:"+bookinfo.getHotel().getFlight_size());
//            if(bookinfo.getHotel().getCurrent_num()>= bookinfo.getHotel().getFlight_size()){
//                throw new IllegalStateException("the flight you choose is in its max size");
//            }
            registration.register(bookinfo);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            log.info("err1"+ce.getMessage());
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            log.info("err2"+e.getMessage());
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("bookinfo", "bookinfo taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            log.info("err3"+e.getMessage());
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    @DELETE
    @Path("/{fid}/{mid}")
    public Response deleteUser(@PathParam("fid") Long para1,@PathParam("mid") Long para2)  {

        BookInfo bookInfo = bookRepository.findByBookInfo(para1,para2);
        Response.ResponseBuilder builder = null;
        if (bookInfo == null) {
            log.info("eee1");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        try {
            registration.delete(bookInfo);
        } catch (Exception e) {
            log.info("eee2");
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.NOT_MODIFIED);

        }
        builder = Response.ok();
        return builder.build();
    }


    private void validateBook(BookInfo bookInfo){
        Set<ConstraintViolation<BookInfo>> violations = validator.validate(bookInfo);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        if (BookinfoAlreadyExists(bookInfo)) {
            throw new ValidationException("Unique bookinfo Violation");
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


    private boolean BookinfoAlreadyExists(BookInfo bookInfo){
        BookInfo bookInfo1 = null;
        try {
            bookInfo1 = bookRepository.findByBookInfo(bookInfo.getMember().getId(),bookInfo.getHotel().getId());
        } catch (NoResultException e) {
            // ignore
        }
        return bookInfo1 != null;
    }


}
