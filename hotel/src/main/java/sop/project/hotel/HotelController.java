package sop.project.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;
import sop.project.hotel.exception.NotFoundException;
import sop.project.hotel.model.*;
import sop.project.hotel.model.responseModel.HotelFullDetail;
import sop.project.hotel.respository.*;

import java.util.*;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableDiscoveryClient
public class HotelController {
    @Autowired
    private HotelRespository hotelRespository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ServiceDiscoveryClient serviceDiscoveryClient;

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
    public Object updatehotel(@RequestHeader("Authorization") String value, @RequestBody Map<String, Object> body,
                              @PathVariable("hotelId") long hotelId) throws Exception {
        int userId = serviceDiscoveryClient.getUserId("Authorization", value);
        boolean canUpdate = false;
        Hotel hotel = hotelRespository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel Does't Exist"));
        for(int user_id: hotel.getUsers_id()){
            if(user_id == userId) {
                canUpdate = true;
            }
        }
        if(canUpdate == false)
            return "you can't update (permission)";
        if (body.get("hotelName") != null)
            hotel.setHotelName((String) body.get("hotelName"));
        if (body.get("country") != null)
            hotel.setCountry((String) body.get("country"));
        if (body.get("provinceState") != null)
            hotel.setProvinceState((String) body.get("provinceState"));
        if (body.get("district") != null)
            hotel.setDistrict((String) body.get("district"));
        if (body.get("street") != null)
            hotel.setStreet((String) body.get("street"));
        if (body.get("additionalDetail") != null)
            hotel.setAdditionalDetail((String) body.get("additionalDetail"));
        if (body.get("hotelImages") != null)
            hotel.setHotelImages((List<String>) body.get("hotelImages"));
        if (body.get("user_id") != null)
            hotel.setUsers_id((List<Integer>) body.get("user_id"));
        if (body.get("tel") != null)
            hotel.setTel((List<String>) body.get("tel"));
        if (body.get("email") != null)
            hotel.setEmail((List<String>) body.get("email"));
        if (body.get("availible") != null)
            hotel.setAvailible((boolean) body.get("availible"));

        return hotelRespository.save(hotel);
    }

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
            value = "/fullhoteldetail/{hotelId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public Object getHotelFullDetail(@PathVariable("hotelId") long hotelId) {
        Hotel hotel = hotelRespository.findById(hotelId).map(thatHotel -> thatHotel)
                .orElseThrow(() -> new NotFoundException("no Hotel found"));
        List<RoomType> roomTypes = new ArrayList<RoomType>();
        roomTypeRepository.findAll().forEach(eachType -> {
            if (eachType.getHotel().getHotelId() == hotelId) {
                roomTypes.add(eachType);
            }
        });
        return new HotelFullDetail(hotel.getHotelId(), hotel, roomTypes);
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
