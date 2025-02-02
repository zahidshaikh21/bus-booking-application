package com.busbooking.services;

import com.busbooking.model.Bus;
import com.busbooking.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public List<Bus> searchBuses(String origin, String destination, LocalDate date) {
        return busRepository.findByOriginAndDestinationAndDepartureDate(origin, destination, date);
    }
}
