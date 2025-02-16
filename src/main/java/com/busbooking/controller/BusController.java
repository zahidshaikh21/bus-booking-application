package com.busbooking.controller;

import com.busbooking.model.Bus;
import com.busbooking.model.util.CityUtils;
import com.busbooking.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/manage-buses")
public class BusController {
    @Autowired
    private BusService busService;

    @GetMapping

    public String showManageBusesPage(Model model) {
        List<String> maharashtraCities = CityUtils.getMaharashtraCities(); // Your logic to fetch cities
        model.addAttribute("maharashtraCities", maharashtraCities);
        model.addAttribute("buses", busService.getAllBuses()); // Assuming you have a busService
        model.addAttribute("bus", new Bus()); // For the form
        return "manage-buses";
    }

    @PostMapping("/add")
    public String addBus(@ModelAttribute Bus bus, RedirectAttributes redirectAttributes) {
        try {
            busService.saveBus(bus);
            redirectAttributes.addFlashAttribute("successMessage", "Bus added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding bus: " + e.getMessage()); // Or a more generic message
            // Optionally log the exception:  log.error("Error adding bus", e);
        }
        return "redirect:/api/manage-buses"; // Correct redirect URL
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateBus(@RequestParam Long id, @RequestParam Map<String, String> updatedBusData, RedirectAttributes redirectAttributes) {
        try {
            Optional<Bus> existingBus = busService.getBusById(id);

            if (existingBus.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Bus not found!");
                return "redirect:/api/manage-buses";
            }

            Bus busToUpdate = existingBus.get();

            // Update the fields dynamically from updatedBusData
            busToUpdate.setBusName(updatedBusData.get("busName"));
            busToUpdate.setOrigin(updatedBusData.get("origin"));
            busToUpdate.setDestination(updatedBusData.get("destination"));
            busToUpdate.setDepartureDate(LocalDate.parse(updatedBusData.get("departureDate")));
            busToUpdate.setArrivalTime(LocalTime.parse(updatedBusData.get("arrivalTime")));
            busToUpdate.setDepartureTime(LocalTime.parse(updatedBusData.get("departureTime")));
            busToUpdate.setSeatsAvailable(Integer.parseInt(updatedBusData.get("seatsAvailable")));
            busToUpdate.setFare(BigDecimal.valueOf(Double.parseDouble(updatedBusData.get("fare"))));

            busService.saveBus(busToUpdate);
            redirectAttributes.addFlashAttribute("successMessage", "Bus updated successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating bus: " + e.getMessage());
        }
        return "redirect:/api/manage-buses";
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBus(@RequestParam Long id) {
        try {
            busService.deleteBus(id);
            return ResponseEntity.ok("Bus deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting bus: " + e.getMessage());
        }
    }

}