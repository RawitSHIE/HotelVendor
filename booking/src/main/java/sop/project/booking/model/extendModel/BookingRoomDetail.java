package sop.project.booking.model.extendModel;

import sop.project.booking.model.Booking;
import sop.project.booking.model.RoomDetail;

import java.util.Date;
import java.util.List;

public class BookingRoomDetail {
    private long userId;
    private long hotelId;
    private String bookingStatus;
    private Date bookingCreateDate = new Date();
    private Date checkInDate;
    private Date checkOutDate;
    private long totalPrice;
    private List<RoomDetail> roomDetail;

    public Booking createBooking() {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setHotelId(hotelId);
        booking.setBookingStatus(bookingStatus);
        booking.setBookingCreateDate(bookingCreateDate);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalPrice(totalPrice);

        return booking;
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

    public long getTotalPrice() {
        return totalPrice;
    }

    public List<RoomDetail> getRoomDetail() {
        return roomDetail;
    }
}
