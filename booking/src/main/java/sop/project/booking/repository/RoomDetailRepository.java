package sop.project.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sop.project.booking.model.RoomDetail;

@Repository
public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {}
