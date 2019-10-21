package sop.project.booking.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class RoomDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String roomId;

    @NotNull
    private long price;

    public long getId() {
        return id;
    }

    public String getRoomId() {
        return roomId;
    }

    public long getPrice() {
        return price;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
