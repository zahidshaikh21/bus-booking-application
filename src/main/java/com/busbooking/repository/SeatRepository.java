package com.busbooking.repository;

import com.busbooking.model.Seat;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByBus_BusId(Long busId);
}
