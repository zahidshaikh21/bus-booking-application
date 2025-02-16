package com.busbooking.services;

import com.busbooking.model.dto.SeatDTO;
import com.busbooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<SeatDTO> getSeatsByBusId(Long busId) {
        return seatRepository.findByBus_BusId(busId).stream()
                .map(seat -> new SeatDTO(seat.getSeatId(), seat.getSeatNumber(), seat.getStatus()))
                .collect(Collectors.toList());
    }
}


