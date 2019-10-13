package sop.project.hotel.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sop.project.hotel.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {}
