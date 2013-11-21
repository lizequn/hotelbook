package uk.ac.ncl.cs.zequnli.service;

import uk.ac.ncl.cs.zequnli.model.Hotel;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * @Auther: Li Zequn
 * Date: 06/11/13
 */
@Stateless
public class HotelRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Hotel> memberEventSrc;

    public void register(Hotel hotel) throws Exception {
        log.info("Registering " + hotel.getHotelName());
        em.persist(hotel);
        memberEventSrc.fire(hotel);
    }
}
