package com.busbooking.controller;

import com.busbooking.model.Bus;
import com.busbooking.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    @Autowired
    private BusService busService;

    @GetMapping("/search")
    public List<Bus> searchBuses(@RequestParam String origin, @RequestParam String destination, @RequestParam String date) {
        return busService.searchBuses(origin, destination, LocalDate.parse(date));
    }
}
