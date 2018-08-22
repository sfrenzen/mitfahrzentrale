package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripRating;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.web.fehler.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trips/{id}")
@PreAuthorize("authenticated")
public class TripRatingController {
	private final TripServices tripServices;

	// constructor der Klasse
	public TripRatingController(TripServices tripServices) {
		this.tripServices = tripServices;
	}
	// Fahrt mit den ID={id} aufrufen .. wenn die Fahrt nicht exitiert wird eine Exception aufgel�st
	@ModelAttribute("tripNumber")
	public Trip getTrip(@PathVariable("id") Long id){
		Trip rating = tripServices.findTripById(id);
		if (rating == null){
			throw new NotFoundException();
		}
		return rating;
	}
	@GetMapping
	public String showRating (Model model, @PathVariable("id") Long id){
		model.addAttribute("ratingform", new TripRating());
		return "trips/rating";
	}

	@PostMapping // bewertung wird hier behandlet
	public String sendRating(@PathVariable("id") Long id, @ModelAttribute("ratingForm") TripRating tripRating, BindingResult bindingResult, Model model) {
		Trip trip = getTrip(id);
		if (trip == null) {
			throw new NotFoundException();
		}
		if (bindingResult.hasErrors()){
			//todo validation need to be constructed here
			return "trips/rating";
		}
		 tripServices.addRating(trip, tripRating);
		return "redirect:/index";

	}


}
