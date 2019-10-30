package sop.project.hotel.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sop.project.hotel.model.Hotel;

import java.util.List;

@Repository
public interface HotelRespository extends JpaRepository<Hotel, Long> {
    List<Hotel> findHotelByHotelNameStartingWith(String hotelName);
    List<Hotel> findHotelByProvinceStateIgnoreCase(String provinceState);
}
