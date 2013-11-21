package uk.ac.ncl.cs.zequnli.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Auther: Li Zequn
 * Date: 05/11/13
 */
@SuppressWarnings("serial")
@Entity(name = "BookInfo")
@XmlRootElement
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"Member_Id", "Hotel_Id"}))
public class BookInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Member_Id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "Hotel_Id")
    private Hotel hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;

    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
