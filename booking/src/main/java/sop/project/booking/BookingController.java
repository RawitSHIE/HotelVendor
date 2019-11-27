package sop.project.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sop.project.booking.exception.NotFoundException;
import sop.project.booking.model.Booking;
import sop.project.booking.model.RoomTypeDetail;
import sop.project.booking.model.extendModel.BookingRoomDetail;
import sop.project.booking.model.extendModel.BookingStatus;
import sop.project.booking.model.extendModel.requestModel.*;
import sop.project.booking.model.extendModel.response.BookingFullDetail;
import sop.project.booking.repository.BookingRepository;
import sop.project.booking.repository.RoomTypeDetailRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public ResponseEntity createBooking(@RequestHeader("Authorization") String value,
                                        @RequestBody BookingRoomDetail bookingRoomTypeDetail) throws Exception {
        Hotel hotel = serviceDiscoveryClient.getHotel(bookingRoomTypeDetail.getHotelId());
        int userId = serviceDiscoveryClient.getUserId("Authorization", value);
        bookingRoomTypeDetail.setUserId(userId);

        User user = serviceDiscoveryClient.getUser(userId);
        SelectDate selectDate = new SelectDate();
        selectDate.setStartDate(bookingRoomTypeDetail.getBookingStartDate());
        selectDate.setEndDate(bookingRoomTypeDetail.getBookingEndDate());

        if (hotel != null && user != null) {
            Date currentDate = new Date();
            bookingRoomTypeDetail.setBookingCreateDate(currentDate);

            if (bookingRoomTypeDetail.getBookingStartDate()
                    .after(bookingRoomTypeDetail.getBookingEndDate())
                    || bookingRoomTypeDetail.getBookingCreateDate()
                    .after(bookingRoomTypeDetail.getBookingStartDate()) ) {
                return new ResponseEntity("invalid date", HttpStatus.NOT_ACCEPTABLE);
            }

            HashMap<String, Long> remainAvailableRoom =
                    remainAvailableRoom(hotel.getHotelId(), selectDate);
            List<String> roomTypes = new ArrayList<>();
            serviceDiscoveryClient.getHotelFullDetail(hotel.getHotelId())
                    .getRoomTypes().forEach(roomType -> {
                roomTypes.add(roomType.getRoomTypeName());
            });

            for (int i = 0; i < bookingRoomTypeDetail.getRoomTypeRequests().size(); i++) {
                RoomTypeRequest roomRequest = bookingRoomTypeDetail.getRoomTypeRequests().get(i);
                if (roomTypes.size() == 0 || !roomTypes.contains(roomRequest.getRoomTypeName())) {
                    return new ResponseEntity("invalid room type", HttpStatus.NOT_ACCEPTABLE);
                }
                if (roomRequest.getQuantity() > remainAvailableRoom.get(roomRequest.getRoomTypeName())){
                    return new ResponseEntity("request quantity exceed availibility", HttpStatus.NOT_ACCEPTABLE) ;
                }
            }

            Booking booking = bookingRepository.save(bookingRoomTypeDetail.createBooking());

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
                    booking.setTotalPrice(booking.getTotalPrice() + roomTypeDetail.getPrice());
                }
            });
            bookingRepository.save(booking);

            return new ResponseEntity(new BookingFullDetail(hotel, user, booking, roomTypeDetails), HttpStatus.OK);
        }

        return new ResponseEntity("invalid booking information", HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(
            value = "/getbookingbyuser/{userId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity getBookingByUser(@PathVariable("userId") long userId) {
        User user = serviceDiscoveryClient.getUser(userId);
        if (user == null){
            return new ResponseEntity("user not found", HttpStatus.NOT_ACCEPTABLE);
        }

        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(booking -> {
            if (booking.getUserId() == userId) {
                bookings.add(booking);
            }
        });

        return new ResponseEntity(bookings, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getbookingdetail/{bookingId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Object getBooking(@RequestHeader("Authorization") String value,
                             @PathVariable("bookingId") long bookingId) throws Exception {
        int userId = serviceDiscoveryClient.getUserId("Authorization", value);
        return bookingRepository.findById(bookingId).map(booking -> {
            if(booking.getUserId() != userId)
                return new ResponseEntity("you can't get booking detail (permission)", HttpStatus.FORBIDDEN);
            List<RoomTypeDetail> roomTypeDetails = new ArrayList<RoomTypeDetail>();
            roomTypeDetailRepository.findAll().forEach(roomTypeDetail -> {
                if (roomTypeDetail.getBooking().getId() == bookingId) {
                    roomTypeDetails.add(roomTypeDetail);
                }
            });
            Hotel hotel = serviceDiscoveryClient.getHotel(booking.getHotelId());
            User user = serviceDiscoveryClient.getUser(booking.getUserId());

            return new ResponseEntity(new BookingFullDetail(hotel, user, booking, roomTypeDetails), HttpStatus.OK);
        }).orElseThrow(() -> new NotFoundException("booking doesn't exist"));
    }

    @RequestMapping(
            value = "/updateBookingStatus/{bookingId}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public Object updateBooking(@RequestHeader("Authorization") String value,
                                @PathVariable("bookingId") long bookingId,
                                @RequestBody HashMap<String, String> status) throws Exception {
        Date currentDate = new Date();
        int userId = serviceDiscoveryClient.getUserId("Authorization", value);
        return bookingRepository.findById(bookingId).map(booking -> {
            if (booking.getBookingStartDate().after(currentDate) && status.get("status") != null) {
                Hotel hotel = serviceDiscoveryClient.getHotel(booking.getHotelId());
                if (hotel == null) {
                    return new ResponseEntity("hotel can't be found", HttpStatus.NOT_ACCEPTABLE);
                }

                boolean canUpdate = false;
                for (long id : hotel.getUsers_id()){
                    if (userId == id) {
                        canUpdate = true;
                        break;
                    }
                }
                if (!canUpdate){
                    return new ResponseEntity("you can't get booking detail (permission)", HttpStatus.FORBIDDEN);
                }

                String newStatus = status.get("status");
                if (newStatus.equals("Cancel")) {
                    booking.setBookingStatus(BookingStatus.Cancelled);
                    return new ResponseEntity(bookingRepository.save(booking), HttpStatus.OK);
                } else {
                    return new ResponseEntity("status type not acceptable", HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity("invalid type request", HttpStatus.BAD_REQUEST);
            }
        }).orElseThrow(() -> new NotFoundException("booking doesn't exist"));
    }


    @RequestMapping(
            value = "/getbookingbyhotel/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity getBookingByHotel(@RequestHeader("Authorization") String value,
                                            @PathVariable("hotelId") long hotelId) throws Exception {
        List<Booking> bookings = new ArrayList<Booking>();
        int userId = serviceDiscoveryClient.getUserId("Authorization", value);
        AtomicBoolean permission = new AtomicBoolean(false);

        bookingRepository.findAll().forEach(booking -> {
            if (booking.getHotelId() == hotelId) {
                Hotel hotel = serviceDiscoveryClient.getHotel(hotelId);
                hotel.getUsers_id().forEach(user_id -> {
                    if (user_id == userId) {
                        bookings.add(booking);
                        permission.set(true);
                    }
                });
            }
        });
        if (!permission.get())
            return new ResponseEntity("you can't get booking detail (permission)", HttpStatus.FORBIDDEN);
        else
            return new ResponseEntity(bookings, HttpStatus.OK);
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

    @RequestMapping(
            value="/freeroom/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public Object remainRoom(@PathVariable long hotelId, @RequestBody SelectDate selectDate) {
        HashMap<String, Long> availableRooms = remainAvailableRoom(hotelId, selectDate);
        if (availableRooms == null) {
            return new ResponseEntity("Hotel Unreachable", HttpStatus.NOT_ACCEPTABLE);
        }
        HotelFullDetail hotelFullDetail = serviceDiscoveryClient.getHotelFullDetail(hotelId);
        List<RoomType> availableRoomTypes = hotelFullDetail.getRoomTypes();
        availableRoomTypes.forEach(roomType -> {
            roomType.setQuantity(availableRooms.get(roomType.getRoomTypeName()));
        });

        return new ResponseEntity(availableRoomTypes, HttpStatus.OK);
    }

    public HashMap<String, Long> remainAvailableRoom(long hotelId, SelectDate selectDate) {
        HotelFullDetail hotelFullDetail = serviceDiscoveryClient.getHotelFullDetail(hotelId);
        if (hotelFullDetail == null) {
            return null;
        }
        HashMap<String, Long> availableRooms = new HashMap<>();
        hotelFullDetail.getRoomTypes().forEach(roomType ->
                availableRooms.put(roomType.getRoomTypeName(), roomType.getQuantity()));

        bookingRepository.findAll().forEach(booking -> {
            if (booking.getHotelId() == hotelId) {
                if (booking.getBookingStartDate().before(selectDate.getEndDate())
                        && booking.getBookingEndDate().after(selectDate.getStartDate())) {
                    roomTypeDetailRepository.findAll().forEach(roomTypeDetail -> {
                        if (roomTypeDetail.getBooking().getId() == booking.getId() && booking.getBookingStatus().equals(BookingStatus.Booked)) {
                            availableRooms.put(
                                    roomTypeDetail.getRoomTypeName(),
                                    availableRooms.get(roomTypeDetail.getRoomTypeName()) - roomTypeDetail.getQuantity()
                            );
                        }
                    });
                }
            }
        });

        return availableRooms;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingController.class, args);
    }
}
