package com.busbooking.services;

import com.busbooking.model.Booking;
import com.busbooking.model.Bus;
import com.busbooking.model.User;
import com.busbooking.repository.BookingRepository;
import com.busbooking.repository.BusRepository;
import com.busbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private NotificationService notificationService;


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

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public double getTotalRevenue() {
        return bookingRepository.calculateTotalRevenue();
    }

    public List<Map<String, Object>> getBookingsByDate(LocalDate date) {
        return bookingRepository.findBookingsByDate(date);
    }

    public List<Booking> findUserBookings(Long userId) {
        User user = userRepository.getById(userId);
        return bookingRepository.findByUser(user); // Assuming you have this method in your repository
    }

    public Booking bookSeat(Long id, Long busId, String seatNumber, LocalDateTime now, int totalSeats, double totalFare) {

        Booking booking = new Booking();
        booking.setSeatNumbers(seatNumber);
        booking.setTotalFare(totalFare);
        booking.setSeatsBooked(totalSeats);
        booking.setBookingDate(now);
        booking.setUser(userRepository.getReferenceById(id));
        booking.setBus(busRepository.getReferenceById(busId));
        Booking savedBooking = bookingRepository.save(booking);

        // Call async notification method
        notificationService.sendBookingNotifications(savedBooking);

        return savedBooking;

    }
}
