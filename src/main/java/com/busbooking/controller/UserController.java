package com.busbooking.controller;

import com.busbooking.model.Booking;
import com.busbooking.model.Bus;
import com.busbooking.model.dto.UserDto;
import com.busbooking.model.util.CityUtils;
import com.busbooking.services.BookingService;
import com.busbooking.services.BusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/user/dashboard")
public class UserController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    @GetMapping
    public String userDashboard(Model model, HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedInUser);

        List<Booking> userBookings = bookingService.findUserBookings(loggedInUser.getId());
        model.addAttribute("userBookings", userBookings);

        List<String> maharashtraCities = CityUtils.getMaharashtraCities();
        model.addAttribute("originCities", maharashtraCities);
        model.addAttribute("destinationCities", maharashtraCities);
        model.addAttribute("busSearch", new Bus());

        return "user-dashboard";
    }

    @PostMapping("/searchBuses")
    public String searchBuses(@ModelAttribute("busSearch") Bus busSearch, RedirectAttributes redirectAttributes, HttpSession session) { // Use RedirectAttributes
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Bus> availableBuses = busService.searchBuses(busSearch.getOrigin(), busSearch.getDestination());

        // Add availableBuses and search criteria as flash attributes
        redirectAttributes.addFlashAttribute("availableBuses", availableBuses);
        redirectAttributes.addFlashAttribute("busSearch", busSearch); // Keep search criteria

        return "redirect:/api/user/dashboard"; // Redirect!
    }

    @PostMapping("/bookSeat/{busId}")
    @ResponseBody
    public ResponseEntity<?> bookSeat(
            @PathVariable Long busId,
            @RequestParam("seatNumbers") String seatNumbers,
            @RequestParam("totalSeats") int totalSeats,
            @RequestParam("totalFare") double totalFare,
            HttpSession session) {

        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "User not logged in"));
        }

        try {
            Booking booking = bookingService.bookSeat(loggedInUser.getId(), busId, seatNumbers, LocalDateTime.now(), totalSeats, totalFare);

            return ResponseEntity.ok(Map.of(
                    "message", "Booking successful",
                    "bookingId", booking.getBookingId(),
                    "totalPrice", booking.getTotalFare(),
                    "busId", busId
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Booking failed: " + e.getMessage()));
        }
    }


}