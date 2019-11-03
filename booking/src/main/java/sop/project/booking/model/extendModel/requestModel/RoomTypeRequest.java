package sop.project.booking.model.extendModel.requestModel;

public class RoomTypeRequest {

    private String roomTypeName;
    private long price;
    private long quantity;

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}