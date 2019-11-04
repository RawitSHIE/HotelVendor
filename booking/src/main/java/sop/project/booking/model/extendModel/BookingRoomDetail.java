package sop.project.booking.model.extendModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import sop.project.booking.model.Booking;
import sop.project.booking.model.extendModel.requestModel.RoomTypeRequest;

import java.util.Date;
import java.util.List;

public class BookingRoomDetail {
    private long userId;
    private long hotelId;
    private BookingStatus bookingStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Bangkok")
    private Date bookingCreateDate = new Date();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Bangkok")
    private Date bookingStartDate = new Date();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Bangkok")
    private Date bookingEndDate = new Date();

    private List<RoomTypeRequest> roomTypeRequests;

    public Booking createBooking() {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setHotelId(hotelId);
        booking.setBookingStatus(bookingStatus);
        booking.setBookingCreateDate(bookingCreateDate);
        booking.setBookingStartDate(bookingStartDate);
        booking.setBookingEndDate(bookingEndDate);

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

    public void setRoomTypeRequests(List<RoomTypeRequest> roomTypeRequests) {
        this.roomTypeRequests = roomTypeRequests;
    }
}
