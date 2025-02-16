package com.busbooking.controller;

import com.busbooking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    @Autowired
    private BookingService bookingService; // Or your reporting service

    @GetMapping
    public String viewReports(Model model) {
        // Example: Report on bookings by date
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> bookingsByDate = bookingService.getBookingsByDate(today);

        // Example: Total revenue report
        double totalRevenue = bookingService.getTotalRevenue();

        model.addAttribute("bookingsByDate", bookingsByDate);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("reportDate", today); // Add the report date to the model

        return "admin-view-reports"; // Thymeleaf template name
    }

    // Add more report generation methods as needed (e.g., by bus, by user, etc.)
}