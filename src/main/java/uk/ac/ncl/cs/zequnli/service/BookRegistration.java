package uk.ac.ncl.cs.zequnli.service;

import uk.ac.ncl.cs.zequnli.model.BookInfo;
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
public class BookRegistration {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;



    @Inject
    private Event<BookInfo> memberEventSrc;

    public void register(BookInfo bookinfo) throws Exception {
        log.info("Registering " + bookinfo.getMember().getId());
       // em.getTransaction().begin();

        Hotel hotel = em.find(Hotel.class,bookinfo.getHotel().getId());
       // hotel.setCurrent_num(hotel.getCurrent_num()+1);
        em.persist(bookinfo);
        //em.getTransaction().commit();
        memberEventSrc.fire(bookinfo);
    }

    public void delete(BookInfo bookInfo) throws Exception{
        log.info("delete"+ bookInfo.getMember().getId());
        //em.remove(bookInfo);
        em.remove(em.contains(bookInfo) ? bookInfo : em.merge(bookInfo));
        memberEventSrc.fire(bookInfo);
    }
}
