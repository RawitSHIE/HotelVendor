package sop.project.booking.model.extendModel.response;

import sop.project.booking.model.Booking;
import sop.project.booking.model.RoomTypeDetail;
import sop.project.booking.model.extendModel.requestModel.Hotel;
import sop.project.booking.model.extendModel.requestModel.User;

import java.util.ArrayList;
import java.util.List;

public class BookingFullDetail {
    Hotel hotel;
    User user;
    Booking booking;
    List<RoomTypeDetail> roomTypeDetails = new ArrayList<RoomTypeDetail>();

    public BookingFullDetail(){}

    public BookingFullDetail(Hotel hotel, User user, Booking booking, List<RoomTypeDetail> roomTypeDetails) {
        this.hotel = hotel;
        this.user = user;
        this.booking = booking;
        this.roomTypeDetails = roomTypeDetails;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public User getUser() {
        return user;
    }

    public Booking getBooking() {
        return booking;
    }

    public List<RoomTypeDetail> getRoomTypeDetails() {
        return roomTypeDetails;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setRoomTypeDetails(List<RoomTypeDetail> roomTypeDetails) {
        this.roomTypeDetails = roomTypeDetails;
    }
}
