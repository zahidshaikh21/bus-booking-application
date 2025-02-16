package com.busbooking.repository;

import com.busbooking.model.Booking;
import com.busbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT DATE_FORMAT(b.bookingDate, '%Y-%m-%d') AS bookingDate, COUNT(b.bookingId) AS bookingCount, SUM(b.totalFare) AS totalFare " +
            "FROM Booking b " +
            "WHERE DATE(b.bookingDate) = :date " +
            "GROUP BY DATE_FORMAT(b.bookingDate, '%Y-%m-%d')")
    List<Map<String, Object>> findBookingsByDate(@Param("date") LocalDate date);

    @Query("SELECT SUM(b.totalFare) FROM Booking b")
    double calculateTotalRevenue();
    List<Booking> findByUser(User user);

}
