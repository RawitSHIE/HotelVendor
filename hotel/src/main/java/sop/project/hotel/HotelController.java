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
    private RoomTypeRepository roomTypeRepository;

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
            value = "/createhotel",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public Object createHotel(@RequestBody Hotel hotel){
        return hotelRespository.save(hotel);
    }

    @RequestMapping(
            value = "/updatehotel/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.POST
    )
    public Object updatehotel(@RequestBody Hotel hotel,
                              @PathVariable("hotelId") long hotelId) {
        return hotelRespository.findById(hotelId).map(thatHotel -> {
            if (!hotel.getHotelName().isEmpty())
                thatHotel.setHotelName(hotel.getHotelName());
            if (!hotel.getCountry().isEmpty())
                thatHotel.setCountry(hotel.getCountry());
            if (!hotel.getProvinceState().isEmpty())
                thatHotel.setProvinceState(hotel.getProvinceState());
            if (!hotel.getDistrict().isEmpty())
                thatHotel.setDistrict(hotel.getDistrict());
            if (!hotel.getStreet().isEmpty())
                thatHotel.setStreet(hotel.getStreet());
            if (!hotel.getAdditionalDetail().isEmpty())
                thatHotel.setAdditionalDetail(hotel.getAdditionalDetail());
            if (!hotel.getHotelImages().isEmpty())
                thatHotel.setHotelImages(hotel.getHotelImages());
            if (!hotel.getTel().isEmpty())
                thatHotel.setTel(hotel.getTel());
            if (!hotel.getEmail().isEmpty())
                thatHotel.setEmail(hotel.getEmail());

            thatHotel.setAvailible(hotel.getAvailible());

            return hotelRespository.save(thatHotel);
        }).orElseThrow(() -> new NotFoundException("Hotel Does't Exist"));
    }

//    room
    @RequestMapping(
            value = "/createroomtype/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    public Object createRoomType(@RequestBody RoomType roomType,
                                 @PathVariable("hotelId") long hotelId) {
        return hotelRespository.findById(hotelId).map( hotel -> {
            List<String> allType = new ArrayList<String>();
            List<RoomType> types = new ArrayList<RoomType>();

            roomTypeRepository.findAll().forEach(eachType -> {
                if (eachType.getHotel().getHotelId() == hotelId) {
                    types.add(eachType);
                }
            });

            if (!types.isEmpty()) {
                types.forEach(type -> {
                    allType.add(type.getRoomTypeName());
                });
                if (allType.contains(roomType.getRoomTypeName())) {
                    return "this room type is Already exist";
                } else {
                    roomType.setHotel(hotel);

                    return roomTypeRepository.save(roomType);
                }
            }
            roomType.setHotel(hotel);

            return roomTypeRepository.save(roomType);
        }).orElseThrow(() -> new NotFoundException("Hotel not found"));
    }

    @RequestMapping(
            value="/getallroomtype/{hotelId}",
            produces={"application/json"},
            method = RequestMethod.GET)
    public List<RoomType> getallRoomType(@PathVariable("hotelId") long hotelId) {
        List<RoomType> roomTypes = new ArrayList<RoomType>();
        roomTypeRepository.findAll().forEach(eachType -> {
            if (eachType.getHotel().getHotelId() == hotelId) {
                roomTypes.add(eachType);
            }
        });

        return roomTypes;
    }

    @RequestMapping(
            value = "/updateroomtype/{hotelId}/{roomTypeId}",
            produces = {"application/json"},
            method = RequestMethod.POST
    )
    public Object updateRoomType(@PathVariable("hotelId") long hotelId,
                                 @PathVariable("roomTypeId") long roomTypeId,
                                 @RequestBody RoomType newRoomTypeDetail) {
        RoomType roomTypes = new RoomType();
        return roomTypeRepository.findById(roomTypeId).map(roomType -> {
            if (!newRoomTypeDetail.getRoomTypeName().isEmpty())
                roomType.setRoomTypeName(newRoomTypeDetail.getRoomTypeName());
            if (newRoomTypeDetail.getPrice() == 0)
                roomType.setPrice(newRoomTypeDetail.getPrice());
            if (newRoomTypeDetail.getQuantity() == 0)
                roomType.setQuantity(newRoomTypeDetail.getQuantity());
            if (!newRoomTypeDetail.getRoomTypeImages().isEmpty())
                roomType.setRoomTypeImages(newRoomTypeDetail.getRoomTypeImages());

            return roomTypeRepository.save(roomType);
        }).orElseThrow(() -> new NotFoundException("no room found"));
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelController.class, args);
    }
}
