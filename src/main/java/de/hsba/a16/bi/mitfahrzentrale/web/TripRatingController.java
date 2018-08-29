package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Rating;
import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.user.User;
import de.hsba.a16.bi.mitfahrzentrale.web.fehler.InvalidOperationException;
import de.hsba.a16.bi.mitfahrzentrale.web.fehler.NotFoundException;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.RatingForm;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.RatingFormAssembler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trips/rate/{id}")
public class TripRatingController {
    private final TripServices tripServices;
    private final RatingFormAssembler formAssembler;

    // constructor der Klasse
    public TripRatingController(TripServices tripServices,
                                RatingFormAssembler formAssembler) {
        this.tripServices = tripServices;
        this.formAssembler = formAssembler;
    }

    @GetMapping
    public String showRating(Model model, @PathVariable("id") Long id) {

        Trip trip = tripServices.findTripById(id);
        if (trip == null) {
            throw new NotFoundException();
        }

        if (!trip.hasBookingForCurrentUser()) {
            throw new InvalidOperationException("Der angemeldete Benutzer hat keine Buchung fuer diese Fahrt");
        }

        Rating rating = trip.getRatingGivenByCurrentUser();
        if (rating == null) {
            rating = new Rating(User.getCurrentUser(), trip);
        }

        RatingForm ratingForm = formAssembler.toForm(rating);

        model.addAttribute("ratingForm", ratingForm);
        model.addAttribute("trip", trip);
        return "trips/rating";
    }

    @PostMapping // bewertung wird hier behandlet
    public String sendRating(Model model,
                             @PathVariable("id") Long id,
                             @ModelAttribute("ratingForm") RatingForm ratingForm,
                             BindingResult bindingResult) {

        Trip trip = tripServices.findTripById(id);
        if (trip == null) {
            throw new NotFoundException();
        }

        if (bindingResult.hasErrors()) {
            //todo validation need to be constructed here
            return "trips/ratingForm";
        }

        if (!trip.hasBookingForCurrentUser()) {
            throw new InvalidOperationException("Der angemeldete Benutzer hat keine Buchung fuer diese Fahrt");
        }

        //Hole Trip Bewertung fuer aktuellen Benutzer.
        //Falls es noch keine Bewertung von diesem Benutzer gibt, lege eine neue an
        Rating rating = trip.getRatingGivenByCurrentUser();
        if (rating == null) {
            rating = new Rating(User.getCurrentUser(), trip);
        }

        formAssembler.update(rating, ratingForm);

        tripServices.addRating(trip, rating);

        //Zeige eine Nachricht an
        model.addAttribute("message", "Vielen Dank für Ihre Bewertung!");
        return "message";

    }
}
