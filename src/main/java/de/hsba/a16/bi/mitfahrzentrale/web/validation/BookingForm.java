package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Size;

public class BookingForm {

    public void setBookedSeats(Long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }
    public Long getBookedSeats() {
        return bookedSeats;
    }

    private Long bookedSeats;
}

