package uk.ac.ncl.cs.zequnli.data;

import uk.ac.ncl.cs.zequnli.model.Hotel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class HotelListProducer {
    @Inject
    private HotelRepository repository;

    private List<Hotel> hotels;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Hotel> getHotels() {
        return hotels;
    }

    public void onFlightListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Hotel hotel) {
        retrieveAllFlightsOrderedByName();
    }

    @PostConstruct
    public void retrieveAllFlightsOrderedByName() {
        hotels = repository.findAllOrderedByNo();
    }
}
