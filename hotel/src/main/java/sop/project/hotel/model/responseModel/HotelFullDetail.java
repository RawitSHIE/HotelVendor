package sop.project.hotel.model.responseModel;

import sop.project.hotel.model.Hotel;
import sop.project.hotel.model.RoomType;

import java.util.List;

public class HotelFullDetail {
    private long hotelId;
    private Hotel hotel;
    private List<RoomType> roomTypes;

    public HotelFullDetail(long hotelId, Hotel hotel, List<RoomType> roomTypes) {
        this.hotelId = hotelId;
        this.hotel = hotel;
        this.roomTypes = roomTypes;
    }

    public long getHotelId() {
        return hotelId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }
}
