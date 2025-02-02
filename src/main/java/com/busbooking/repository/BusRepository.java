package com.busbooking.repository;

import com.busbooking.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByOriginAndDestinationAndDepartureDate(String origin, String destination, LocalDate departureDate);
}
