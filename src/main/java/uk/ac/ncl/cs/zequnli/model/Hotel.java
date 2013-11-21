package uk.ac.ncl.cs.zequnli.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Auther: Li Zequn
 * Date: 05/11/13
 */
@SuppressWarnings("serial")
@XmlRootElement
@Entity(name = "hotel")
public class Hotel implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Hotel_id")
    private Long id;

    @NotNull
    private String hotelName;

    @NotNull
    private int hotel_size;

    @NotNull
    @Pattern(regexp = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$",message = "date is not in right format")
    private String date;

    @NotNull
    @Pattern(regexp = "^(([1-9]{1})|([0-1][0-9])|([1-2][0-3])):([0-5][0-9])$",message = "time is not in right format")
    private String time;

    @NotNull
    private String place;

    @NotNull
    private String prize;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHotel_size() {
        return hotel_size;
    }

    public void setHotel_size(int hotel_size) {
        this.hotel_size = hotel_size;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
