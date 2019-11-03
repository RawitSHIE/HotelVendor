package sop.project.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;
import sop.project.booking.exception.NotFoundException;
import sop.project.booking.model.Booking;
import sop.project.booking.model.RoomTypeDetail;
import sop.project.booking.model.extendModel.BookingRoomDetail;
import sop.project.booking.model.extendModel.requestModel.Hotel;
import sop.project.booking.model.extendModel.requestModel.User;
import sop.project.booking.model.extendModel.response.BookingFullDetail;
import sop.project.booking.repository.BookingRepository;
import sop.project.booking.repository.RoomTypeDetailRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomTypeDetailRepository roomTypeDetailRepository;

    @Autowired
    ServiceDiscoveryClient serviceDiscoveryClient;

    @RequestMapping(
            value = "/createBooking",
            produces = "application/json",
            method = RequestMethod.POST)
//  TODO: CREATE CONSTRAINT FOR AVAILIBILITY (FOR EACH DATE)
    public Object createBooking(@RequestBody BookingRoomDetail bookingRoomTypeDetail) {
        Hotel hotel = serviceDiscoveryClient.getHotel(bookingRoomTypeDetail.getHotelId());
        User user = serviceDiscoveryClient.getUser(bookingRoomTypeDetail.getUserId());
        if (hotel != null && user != null) {
            Booking booking = bookingRepository.save(bookingRoomTypeDetail.createBooking());
            List<String> roomTypes = new ArrayList<>();
            serviceDiscoveryClient.getHotelFullDetail(hotel.getHotelId()).getRoomTypes().forEach(roomType -> {
                roomTypes.add(roomType.getRoomTypeName());
            });
            for (int i = 0; i < bookingRoomTypeDetail.getRoomTypeRequests().size(); i++) {
                String roomRequestName = bookingRoomTypeDetail.getRoomTypeRequests().get(i).getRoomTypeName();
                if (roomTypes.size() == 0 || !roomTypes.contains(roomRequestName)) {
                    return "invalid room type";
                }
            }

            bookingRoomTypeDetail.getRoomTypeRequests().forEach(roomTypeRequest -> {
                RoomTypeDetail roomTypeDetail = new RoomTypeDetail();
                roomTypeDetail.setRoomTypeName(roomTypeRequest.getRoomTypeName());
                roomTypeDetail.setQuantity(roomTypeRequest.getQuantity());
                roomTypeDetail.setPrice(roomTypeRequest.getPrice());
                roomTypeDetail.setBooking(booking);
                roomTypeDetailRepository.save(roomTypeDetail);
            });

            List<RoomTypeDetail> roomTypeDetails = new ArrayList<RoomTypeDetail>();
            roomTypeDetailRepository.findAll().forEach(roomTypeDetail -> {
                if (roomTypeDetail.getBooking().getId() == booking.getId()) {
                    roomTypeDetails.add(roomTypeDetail);
                }
            });

            return new BookingFullDetail(hotel, user, booking, roomTypeDetails);
        }

        return "invalid booking information";
    }

    @RequestMapping(
            value = "/getbookingdetail/{bookingId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Object getBooking(@PathVariable("bookingId") long bookingId) {
        return bookingRepository.findById(bookingId).map(booking -> {
            List<RoomTypeDetail> roomTypeDetails = new ArrayList<RoomTypeDetail>();
            roomTypeDetailRepository.findAll().forEach(roomTypeDetail -> {
                if (roomTypeDetail.getBooking().getId() == bookingId) {
                    roomTypeDetails.add(roomTypeDetail);
                }
            });
            Hotel hotel = serviceDiscoveryClient.getHotel(booking.getHotelId());
            User user = serviceDiscoveryClient.getUser(booking.getUserId());

            return new BookingFullDetail(hotel, user, booking, roomTypeDetails);
        }).orElseThrow(() -> new NotFoundException("booking doesn't exist"));
    }

    @RequestMapping(
            value = "/getbookingbyhotel/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Object getBookingByHotel(@PathVariable("hotelId") long hotelId) {
        List<Booking> bookings = new ArrayList<Booking>();
        bookingRepository.findAll().forEach(booking -> {
            if (booking.getHotelId() == hotelId) {
                bookings.add(booking);
            }
        });

        return bookings;
    }

    @RequestMapping(
            value = "/hoteldetail/allroomtype/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Object getAllRoomTypes (@PathVariable("hotelId") long hotelId) {
        return serviceDiscoveryClient.getHotelFullDetail(hotelId);
    }

    @RequestMapping(
            value = "/hoteldetail/{hotelId}",
            produces = "application/json",
            method = RequestMethod.GET)
    public Hotel hotel(@PathVariable("hotelId") long hotelId) {
        return serviceDiscoveryClient.getHotel(hotelId);
    }

    @RequestMapping(
            value = "/user/{userId}",
            produces = "application/json",
            method = RequestMethod.GET)
    public User user(@PathVariable("userId") long userId) {
        return serviceDiscoveryClient.getUser(userId);
    }

//    TODO: CREATE CHECK AVAILIBILITY FUNCTION
    public int isAvailable(long hotelId, String roomTypeName) {
        return 0;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingController.class, args);
    }
}
