package sop.project.booking.model.extendModel;

import sop.project.booking.model.Booking;
import sop.project.booking.model.extendModel.requestModel.RoomTypeRequest;

import java.util.Date;
import java.util.List;

public class BookingRoomDetail {
    private long userId;
    private long hotelId;
    private BookingStatus bookingStatus;
    private Date bookingCreateDate = new Date();
    private Date bookingStartDate = new Date();
    private Date bookingEndDate = new Date();
    private long totalPrice;
    private List<RoomTypeRequest> roomTypeRequests;

    public Booking createBooking() {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setHotelId(hotelId);
        booking.setBookingStatus(bookingStatus);
        booking.setBookingCreateDate(bookingCreateDate);
        booking.setBookingStartDate(bookingStartDate);
        booking.setBookingEndDate(bookingEndDate);
        booking.setTotalPrice(totalPrice);

        return booking;
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

    public List<RoomTypeRequest> getRoomTypeRequests() {
        return roomTypeRequests;
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

    public void setRoomTypeRequests(List<RoomTypeRequest> roomTypeRequests) {
        this.roomTypeRequests = roomTypeRequests;
    }
}
