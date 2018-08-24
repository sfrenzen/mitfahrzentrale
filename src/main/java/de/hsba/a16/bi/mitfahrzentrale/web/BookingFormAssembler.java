package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingFormAssembler {

    BookingForm toForm(Booking booking) {
        BookingForm form = new BookingForm();
        form.setBookedSeats(booking.getBookedSeats());
        return form;
    }

    Booking update(Booking booking, BookingForm form) {
        booking.setBookedSeats(form.getBookedSeats());

        return booking;
    }
}