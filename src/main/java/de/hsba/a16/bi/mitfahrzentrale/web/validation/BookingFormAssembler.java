package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.trip.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingFormAssembler {

    public BookingForm toForm(Booking booking) {
        BookingForm form = new BookingForm();
        form.setBookedSeats(booking.getBookedSeats());
        return form;
    }

    public Booking update(Booking booking, BookingForm form) {
        booking.setBookedSeats(form.getBookedSeats());

        return booking;
    }
}