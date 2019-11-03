package sop.project.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sop.project.booking.model.RoomTypeDetail;

@Repository
public interface RoomTypeDetailRepository extends JpaRepository<RoomTypeDetail, Long> {}
