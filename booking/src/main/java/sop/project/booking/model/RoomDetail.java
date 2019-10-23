package sop.project.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RoomDetail")
public class RoomDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private String roomId;

    @NotNull
    private long price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookingId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Booking booking;

    public long getId() {
        return id;
    }

    public String getRoomId() {
        return roomId;
    }

    public Booking getBooking() {
        return booking;
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

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
