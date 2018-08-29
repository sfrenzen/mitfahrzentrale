package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// MyTrips Controller is to control the bookings of a user
@Controller
public class MyTripsController {
	private final TripServices tripServices;
	private User user;

	public MyTripsController(TripServices tripServices) {
		this.tripServices = tripServices;
	}

	@GetMapping("/my-trips")
	public String user(Model model) {
		model.addAttribute("tripsOfferedByUser", tripServices.findUsertrips());
		model.addAttribute("tripsBookedByUser", tripServices.findTripsBookedByCurrentUser());
		return "trips/my-trips";
	}
}
