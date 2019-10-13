package sop.project.hotel.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sop.project.hotel.model.Hotel;

@Repository
public interface HotelRespository extends JpaRepository<Hotel, Long> {}
