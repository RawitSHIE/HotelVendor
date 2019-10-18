package sop.project.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;
import sop.project.hotel.exception.NotFoundException;
import sop.project.hotel.model.*;
import sop.project.hotel.respository.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableDiscoveryClient
public class HotelController {
    @Autowired
    private HotelRespository hotelRespository;

    @Autowired
    private RoomRepository roomRepository;

//  hotel
    @RequestMapping(
            value = "/allhotel",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public List<Hotel> getAllHotels() {
        return hotelRespository.findAll();
    }

    @RequestMapping(
            value = "/hoteldetail/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Object getHotelDetails(@PathVariable("hotelId") long hotelId){
        return hotelRespository.findById(hotelId);
    }

    @RequestMapping(
            value = "/createHotel",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public Hotel createHotel(@RequestBody Hotel hotel){
        return hotelRespository.save(hotel);
    }

//    room
    @RequestMapping(
            value = "/getroom/hotel/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public List<Room> getAllRooms(@PathVariable("hotelId") long hotelId) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        roomRepository.findAll().forEach(room -> {
            if  (room.getHotel().getHotelId() == (hotelId)) {
                rooms.add(room);
            }
        });

        return rooms;
    }

    @RequestMapping(
            value = "/createroom/hotel/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public Room createRoom(@Valid @RequestBody Room room,
                           @PathVariable("hotelId") long hotelId){
        return hotelRespository.findById(hotelId)
                .map(hotel -> {
                    room.setHotel(hotel);

                    return roomRepository.save(room);
                }).orElseThrow(() ->
                        new NotFoundException("Hotel not found"));
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelController.class, args);
    }

}
