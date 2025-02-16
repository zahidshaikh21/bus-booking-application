package com.busbooking.controller;

import com.busbooking.model.Booking;
import com.busbooking.model.Bus;
import com.busbooking.model.dto.UserDto;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.BusRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect if not authenticated
        }
        model.addAttribute("loggedInUser", loggedInUser);
        return "admin-dashboard";
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @PostMapping("/addBus")
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus) {
        return ResponseEntity.ok(busRepository.save(bus));
    }

    @PutMapping("/updateBus/{busId}")
    public ResponseEntity<Bus> updateBus(@PathVariable long busId, @RequestBody Bus updatedBus) {
        return busRepository.findById(busId)
                .map(bus -> {
                    bus.setBusName(updatedBus.getBusName());
                    bus.setOrigin(updatedBus.getOrigin());
                    bus.setDestination(updatedBus.getDestination());
                    bus.setDepartureDate(updatedBus.getDepartureDate());
                    bus.setDepartureTime(updatedBus.getDepartureTime());
                    bus.setArrivalTime(updatedBus.getArrivalTime());
                    bus.setSeatsAvailable(updatedBus.getSeatsAvailable());
                    bus.setFare(updatedBus.getFare());
                    return ResponseEntity.ok(busRepository.save(bus));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteBus/{busId}")
    public ResponseEntity<String> deleteBus(@PathVariable long busId) {
        if (busRepository.existsById(busId)) {
            busRepository.deleteById(busId);
            return ResponseEntity.ok("Bus deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bus not found.");
        }
    }


}
