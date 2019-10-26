package sop.project.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class RoomType extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull(message = "can't be empty")
    private String roomTypeName;

    @NotNull(message = "can't be empty")
    private double price;

    @NotNull(message = "can't be empty")
    private long quantity;

    @ElementCollection
    @CollectionTable(
            name = "room_images",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    @Column(name = "images")
    private List<String> roomTypeImages = new ArrayList<String>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotelId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
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
