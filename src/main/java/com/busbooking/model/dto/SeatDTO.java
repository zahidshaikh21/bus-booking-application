package com.busbooking.model.dto;

import com.busbooking.model.Seat.SeatStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long seatId;
    private int seatNumber;
    private SeatStatus status;
}
