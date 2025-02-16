package com.busbooking.controller;

import com.busbooking.model.dto.SeatDTO;
import com.busbooking.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/bus/{busId}")
    public ResponseEntity<?> getSeatsByBusId(@PathVariable Long busId) {
        try {
            List<SeatDTO> seats = seatService.getSeatsByBusId(busId);
            return ResponseEntity.ok(seats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
