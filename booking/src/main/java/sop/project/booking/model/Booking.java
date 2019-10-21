package sop.project.booking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date bookingCreateDate;

    @NotNull
    private Date checkInDate;

    @NotNull
    private Date checkOutDate;

    @NotNull
    private List<RoomDetail> RoomDetails = new ArrayList<RoomDetail>();

    @NotNull
    private long totalPrice = 0;

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

    public Date getBookingCreateDate() {
        return bookingCreateDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public List<RoomDetail> getRoomDetails() {
        return RoomDetails;
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

    public void setBookingCreateDate(Date bookingCreateDate) {
        this.bookingCreateDate = bookingCreateDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setRoomDetails(List<RoomDetail> RoomDetails) {
        this.RoomDetails = RoomDetails;
        RoomDetails.forEach(RoomDetail ->
                this.setTotalPrice(RoomDetail.getPrice())
        );
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice += totalPrice;
    }

}