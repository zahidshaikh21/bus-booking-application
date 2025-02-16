package com.busbooking.controller;

import com.busbooking.model.Booking;
import com.busbooking.services.BookingService;
import com.busbooking.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/admin/manage-bookings")
public class AdminBookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    @GetMapping
    public String manageBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin-manage-bookings"; // Thymeleaf template name
    }


    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateBooking(@RequestParam Long id, @RequestParam Map<String, String> updatedBookingData, RedirectAttributes redirectAttributes) {
        try {
            Optional<Booking> existingBooking = bookingService.getBookingById(id);

            if (existingBooking.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Booking not found!");
                return "redirect:/api/admin/manage-bookings";
            }

            Booking bookingToUpdate = existingBooking.get();

            // Update fields (exclude bookingId and user)
            bookingToUpdate.setBus(busService.getBusById(Long.parseLong(updatedBookingData.get("bus"))).orElse(bookingToUpdate.getBus()));
            bookingToUpdate.setBookingDate(java.time.LocalDateTime.parse(updatedBookingData.get("bookingDate")));
            bookingToUpdate.setSeatsBooked(Integer.parseInt(updatedBookingData.get("seatsBooked")));
            bookingToUpdate.setSeatNumbers(updatedBookingData.get("seatNumbers"));
            bookingToUpdate.setTotalFare(Double.parseDouble(updatedBookingData.get("totalFare")));

            bookingService.saveBooking(bookingToUpdate);
            redirectAttributes.addFlashAttribute("successMessage", "Booking updated successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating booking: " + e.getMessage());
        }
        return "redirect:/api/admin/manage-bookings";
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBooking(@RequestParam Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.ok("Booking deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting booking: " + e.getMessage());
        }
    }
}