package sop.project.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;
import sop.project.booking.model.Booking;
import sop.project.booking.model.extendModel.BookingRoomDetail;
import sop.project.booking.model.extendModel.Hotel;
import sop.project.booking.model.extendModel.User;
import sop.project.booking.repository.BookingRepository;
import sop.project.booking.repository.RoomDetailRepository;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomDetailRepository roomDetailRepository;

    @Autowired
    ServiceDiscoveryClient serviceDiscoveryClient;

    @RequestMapping(
            value = "/createBooking",
            produces = "application/json",
            method = RequestMethod.POST)
    public Object createBooking(@RequestBody BookingRoomDetail bookingRoomDetail) {
        Hotel hotel = serviceDiscoveryClient.getHotel(bookingRoomDetail.getHotelId());
        User user = serviceDiscoveryClient.getUser(bookingRoomDetail.getUserId());

        if (hotel != null && user != null) {
            Booking booking = bookingRepository.save(bookingRoomDetail.createBooking());

            bookingRoomDetail.getRoomDetail().forEach(roomDetail -> {
                roomDetail.setBooking(booking);
                roomDetailRepository.save(roomDetail);
            });

            return booking;
        } else {
            return "invalid booking information";
        }
    }

    @RequestMapping(
            value = "/hoteldetail/{hotelId}",
            produces = "application/json",
            method = RequestMethod.GET)
    public Hotel hotel(@PathVariable("hotelId") long hotelId) {
        Hotel hotel = serviceDiscoveryClient.getHotel(hotelId);
        return hotel;
    }

    @RequestMapping(
            value = "/user/{userId}",
            produces = "application/json",
            method = RequestMethod.GET)
    public User user(@PathVariable("userId") long userId) {
        User user = serviceDiscoveryClient.getUser(userId);
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingController.class, args);
    }
}
