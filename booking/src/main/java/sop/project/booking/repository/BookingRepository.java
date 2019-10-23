package sop.project.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sop.project.booking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> { }
