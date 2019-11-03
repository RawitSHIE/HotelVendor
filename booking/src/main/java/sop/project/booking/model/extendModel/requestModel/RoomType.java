package sop.project.booking.model.extendModel.requestModel;

import java.util.ArrayList;
import java.util.List;

public class RoomType {

    private long id;
    private String roomTypeName;
    private double price;
    private long quantity;
    private List<String> roomTypeImages = new ArrayList<String>();
    private Hotel hotel;

    public long getId() {
        return id;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public double getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public List<String> getRoomTypeImages() {
        return roomTypeImages;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setRoomTypeImages(List<String> roomTypeImages) {
        this.roomTypeImages = roomTypeImages;
    }

    public void addRoomTypeImages(String imageUrl) {
        this.roomTypeImages.add(imageUrl);
    }
}
