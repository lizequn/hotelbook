package uk.ac.ncl.cs.zequnli.data;

import uk.ac.ncl.cs.zequnli.model.Hotel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Auther: Li Zequn
 * Date: 06/11/13
 */
@ApplicationScoped
public class HotelRepository {
    @Inject
    private EntityManager em;

    public Hotel findById(Long id) {
        return em.find(Hotel.class, id);
    }

    public Hotel findByHotelName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteria = cb.createQuery(Hotel.class);
        Root<Hotel> Flight = criteria.from(Hotel.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(Flight).where(cb.equal(Flight.get("hotelName"), name));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Hotel> findAllOrderedByNo() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteria = cb.createQuery(Hotel.class);
        Root<Hotel> Flight = criteria.from(Hotel.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(Flight).orderBy(cb.asc(Flight.get("hotelName")));
        return em.createQuery(criteria).getResultList();
    }
}
