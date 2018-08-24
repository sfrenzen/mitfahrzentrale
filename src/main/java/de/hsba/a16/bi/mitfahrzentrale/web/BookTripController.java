package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
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

    public BookTripController(TripServices tripServices) {
        this.tripServices = tripServices;
    }

    @GetMapping("/trips/book-trip/{id}")
    public String showBookTrip(Model model,
                           @PathVariable("id") Long id) {
        Trip trip = tripServices.findTripById(id);

        if (trip == null) {
            throw new NotFoundException();
        }

        model.addAttribute("trip", trip);

        return "trips/book-trip";
    }

    @PostMapping("/trips/book-trip/{id}")
    public String bookTrip(Model model,
                           @PathVariable("id") Long id) {
        Trip trip = tripServices.findTripById(id);

        if (trip == null) {
            throw new NotFoundException();
        }

        model.addAttribute("trip", trip);

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
