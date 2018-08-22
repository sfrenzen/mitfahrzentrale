package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AllTripsController {
	private  final TripServices tripServices;

	public AllTripsController(TripServices tripServices) {
		this.tripServices = tripServices;
	}

	// die Seite von allen Fahrt zeigen
	@GetMapping("/all-trips")
	public String showForm (Model model){
		model.addAttribute("allTrips", tripServices.getAllTrips());
		return "trips/all-trips";
	}

	// delete Controller
	@PostMapping("/all-trips/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		tripServices.delete(id);
		return "redirect:/all-trips";
	}

	// filtern der Fahrten nach Abfahrts- und Ankunftsort
	@GetMapping("/all-trips/search")
	public String search(Model model,
                         @RequestParam(value = "start", required = false) String start,
                         @RequestParam(value = "destination", required = false) String destination) {
        model.addAttribute("start", start);
        model.addAttribute("destination", destination);
        model.addAttribute("allTrips", tripServices.searchTrips(start, destination));
        return "trips/all-trips";
	}

	@PostMapping("/all-trips/bookable/{id}")
	public String makeItBookable(@PathVariable("id") Long id) {
		tripServices.bookable(id);
		return "redirect:/personal";
	}
}
