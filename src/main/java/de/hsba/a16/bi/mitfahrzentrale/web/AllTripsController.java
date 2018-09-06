package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import de.hsba.a16.bi.mitfahrzentrale.user.User;
import de.hsba.a16.bi.mitfahrzentrale.user.UserService;
import de.hsba.a16.bi.mitfahrzentrale.web.fehler.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class AllTripsController {
    private final TripServices tripServices;
    private final UserService userService;

    public AllTripsController(TripServices tripServices,
                              UserService userService) {
        this.tripServices = tripServices;
        this.userService = userService;
    }

    // die Seite von allen Fahrten zeigen
    @GetMapping("/all-trips")
    public String showForm(Model model) {
        model.addAttribute("allTrips", tripServices.findAllTrips());
        return "trips/all-trips";
    }

    // die Seite von allen Fahrt-Details zeigen
    @GetMapping("/all-trips/{id}")
    public String showTripDetails(Model model,
                                  @PathVariable("id") Long id) {

        Trip trip = tripServices.findTripById(id);

        if (trip == null) {
            throw new NotFoundException();
        }

        model.addAttribute("trip", trip);

        return "trips/trip-details";
    }

    // delete Controller
    @PostMapping("/all-trips/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        tripServices.delete(id);
        return "redirect:/all-trips";
    }

    // filtern der Fahrten nach Abfahrts- und Ziel
    @GetMapping("/all-trips/search")
    public String search(Model model,
                         @RequestParam(value = "start", required = false) String start,
                         @RequestParam(value = "end", required = false) String end) {
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("allTrips", tripServices.findTripsByStartAndEnd(start, end));
        return "trips/all-trips";
    }

    @PostMapping("/all-trips/bookable/{id}")
    public String makeItBookable(@PathVariable("id") Long id) {
        tripServices.bookable(id);
        return "redirect:/my-trips";
    }

    // Methode um Kommentare zu Fahrer zu öffnen
    @GetMapping("/users/{id}/comments")
    public String showUserComments(Model model,
                                   @PathVariable("id") Long id) {

        User user = userService.findUserById(id);
        if (user == null) {
            throw new NotFoundException();
        }

        model.addAttribute("allRatings", tripServices.findRatingsForTripOwner(user));
        model.addAttribute("owner", user);
        return "trips/comments";
    }
}