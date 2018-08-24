package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Booking;
import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.user.UserService;
import de.hsba.a16.bi.mitfahrzentrale.web.fehler.NotFoundException;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.TripFormValidation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
// @RequestMapping("/trips/{id}")
public class BookTripController {
    private  final TripServices tripServices;
    private final BookingFormAssembler formAssembler;
    public BookTripController(TripServices tripServices,
                              BookingFormAssembler formAssembler) {
        this.tripServices = tripServices;
        this.formAssembler = formAssembler;
    }

    //Methode um Buchungsform anzuzeigen
    @GetMapping("/trips/book-trip/{id}")
    public String showBookTrip(Model model,
                           @PathVariable("id") Long id) {
        Trip trip = tripServices.findTripById(id);

        if (trip == null) {
            throw new NotFoundException();
        }

        Long remainingSeats = tripServices.getRemainingSeats(trip);

//        if(remainingSeats <= 0) {
//            return "redirect:/trips/trip-fully-booked";
//        }

        Booking booking = new Booking();
        BookingForm bookingForm = formAssembler.toForm(booking);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("trip", trip);
        model.addAttribute("remainingSeats", remainingSeats);

        return "trips/book-trip";
    }

    //Methode um Buchung zu erzeugen
    @PostMapping("/trips/book-trip/{id}")
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

        return "redirect:/trips/trip-booked";
    }

    @GetMapping("/trips/trip-booked")
    public String showTripBooked() {
        return "trips/trip-booked";
    }

/*  @PreAuthorize("authenticated")
    @PostMapping("/new-trip")
    public String bookTrip (@ModelAttribute("newTripForm")@Valid TripFormValidation tripFormValidation, BindingResult bindingResult){
    // wenn ein Fehler eintritt
    if (bindingResult.hasErrors()){
        return "trips/new-trip";}
    // sonst
    Trip trip = new Trip();
    tripServices.create(formAssembler.update(trip, tripFormValidation));
    return "redirect:/index";
} */
}
