package sop.project.booking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Booking")
public class Booking extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private long userId;

    @NotNull
    private long hotelId;

    @NotNull
    private String bookingStatus;

    @NotNull
    private Date startDate = new Date();

    @NotNull
    private Date endDate = new Date();

    private Date checkInDate;

    private Date checkOutDate;

    @NotNull
    private long totalPrice;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

}