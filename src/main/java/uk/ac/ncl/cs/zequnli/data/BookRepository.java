package uk.ac.ncl.cs.zequnli.data;


import uk.ac.ncl.cs.zequnli.model.BookInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class BookRepository {
    @Inject
    private EntityManager em;

    public BookInfo findById(Long id) {
        return em.find(BookInfo.class, id);
    }
     //todo rewrite
    public BookInfo findByMember(Long bookid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookInfo> criteria = cb.createQuery(BookInfo.class);
        Root<BookInfo> BookInfo = criteria.from(BookInfo.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(BookInfo).where(cb.equal(BookInfo.get("member"), bookid));
        return em.createQuery(criteria).getSingleResult();
    }
    public BookInfo findByBookInfo(Long flightid,Long memberid){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookInfo> criteria = cb.createQuery(BookInfo.class);
        Root<BookInfo> BookInfo = criteria.from(BookInfo.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(BookInfo).where(cb.and(cb.equal(BookInfo.get("member").get("id"),memberid),cb.equal(BookInfo.get("flight").get("id"),flightid)));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<BookInfo> findAllOrderedById() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookInfo> criteria = cb.createQuery(BookInfo.class);
        Root<BookInfo> BookInfo = criteria.from(BookInfo.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(BookInfo).orderBy(cb.asc(BookInfo.get("id")));
        return em.createQuery(criteria).getResultList();
    }

}
