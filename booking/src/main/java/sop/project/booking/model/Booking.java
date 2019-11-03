package sop.project.booking.model;

import sop.project.booking.model.extendModel.BookingStatus;
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
    private BookingStatus bookingStatus;

    @NotNull
    private Date bookingCreateDate;

    @NotNull
    private Date bookingStartDate = new Date();

    @NotNull
    private Date bookingEndDate = new Date();

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

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public Date getBookingCreateDate() {
        return bookingCreateDate;
    }

    public Date getBookingStartDate() {
        return bookingStartDate;
    }

    public Date getBookingEndDate() {
        return bookingEndDate;
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

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setBookingCreateDate(Date bookingCreateDate) {
        this.bookingCreateDate = bookingCreateDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public void setBookingEndDate(Date bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}