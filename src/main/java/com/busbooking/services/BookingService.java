package com.busbooking.services;

import com.busbooking.model.Booking;
import com.busbooking.model.Bus;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BusRepository busRepository;

    public Booking bookTicket(Booking booking) {
        Bus bus = busRepository.findById(booking.getBus().getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        if (bus.getSeatsAvailable() < booking.getSeatsBooked()) {
            throw new RuntimeException("Not enough seats available");
        }

        bus.setSeatsAvailable(bus.getSeatsAvailable() - booking.getSeatsBooked());
        busRepository.save(bus);

        return bookingRepository.save(booking);
    }
}
