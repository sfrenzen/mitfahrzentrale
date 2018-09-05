package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Booking;
import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.web.fehler.NotFoundException;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.BookingForm;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.BookingFormAssembler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/trips/book-trip/{id}")
public class BookTripController {
    private final TripServices tripServices;
    private final BookingFormAssembler formAssembler;

    public BookTripController(TripServices tripServices,
                              BookingFormAssembler formAssembler) {
        this.tripServices = tripServices;
        this.formAssembler = formAssembler;
    }

    //Methode um Buchungsform anzuzeigen
    @GetMapping
    public String showBookTrip(Model model,
                           @PathVariable("id") Long id) {
        Trip trip = tripServices.findTripById(id);

        if (trip == null) {
            throw new NotFoundException();
        }

        //Neue Buchung anlegen
        Booking booking = new Booking();

        //Buchungsform objekt aus Buchung eryeugen
        BookingForm bookingForm = formAssembler.toForm(booking);

        //Attribute setzen
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("trip", trip);

        return "trips/book-trip";
    }

    //Methode um Buchung zu erzeugen
    @PostMapping
	@PreAuthorize("authenticated")
    public String bookTrip(Model model,
                           @PathVariable("id") Long id,
                           @ModelAttribute("bookingForm") @Valid BookingForm bookingForm,
                           BindingResult entryBinding) {
        Trip trip = tripServices.findTripById(id);
        if (trip == null) {
            throw new NotFoundException();
        }

        if (entryBinding.hasErrors()) {
            model.addAttribute("trip", trip);
            return "trips/book-trip";
        }

        Booking booking = new Booking();
        formAssembler.update(booking, bookingForm);
        tripServices.addBooking(trip, booking);

        //Zeige eine Nachricht an
        model.addAttribute("message", "Vielen Dank für die Buchung!");
        return "message";
    }
}
