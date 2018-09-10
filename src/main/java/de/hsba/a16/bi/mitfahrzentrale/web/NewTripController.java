package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.TripFormAssembler;
import de.hsba.a16.bi.mitfahrzentrale.web.validation.TripFormValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class NewTripController {
	private  final TripServices tripServices;
	private final TripFormAssembler formAssembler;

	public NewTripController(TripServices tripServices, TripFormAssembler formAssembler) {
		this.tripServices = tripServices;
		this.formAssembler = formAssembler;
	}

	// die Seite von neuer Fahrt zu zeigen
	@GetMapping("/new-trip") // url
	public String showForm (Model model){
		model.addAttribute("newTripForm", new TripFormValidation());
		return "trips/new-trip";
	}

	// gleich wie oben
	@PostMapping("/new-trip")
	public String createTrip (@ModelAttribute("newTripForm")@Valid TripFormValidation tripFormValidation, BindingResult bindingResult){
		// wenn ein Fehler eintritt
		if (bindingResult.hasErrors()){
			return "trips/new-trip";}
		// sonst
		Trip trip = new Trip();
		tripServices.create(formAssembler.update(trip, tripFormValidation));
		return "redirect:/index";
	}
}
