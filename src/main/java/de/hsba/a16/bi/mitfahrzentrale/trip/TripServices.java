package de.hsba.a16.bi.mitfahrzentrale.trip;


import de.hsba.a16.bi.mitfahrzentrale.web.fehler.InvalidOperationException;
import de.hsba.a16.bi.mitfahrzentrale.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Component
public class TripServices {
    // Variable for trip repository
    private final TripRepository repository;
    // Varible for trip rating repository
    private final TripRatingRepository ratingRepository;

    // Constructor
    public TripServices(TripRepository repository, TripRatingRepository ratingRepository) {
        this.repository = repository;
        this.ratingRepository = ratingRepository;
    }

    // creating a trip
    public void create(Trip trip) {

        //Set initial value for remaining seats
        trip.setRemainingSeats(trip.getFreeSeats());

        repository.save(trip);
    }

    // finding a trip by ID
    public Trip findTripById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // get all trips to repeat them in foreach
    public Collection<Trip> findAllTrips() {
        return repository.findAll();
    }

    // delete a trip
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    // make it a trip bookable
    public void bookable(Long id) {
        Trip trip = repository.findById(id).orElse(null);
        boolean temp = trip.isBookable();
        // if true turn it false
        if (temp == true) {
            temp = false;
        } else {
            temp = true;
        }
        trip.setBookable(temp);
    }

    // Finde meine angebotenen und meine gebuchten Fahrten des current user
    public Collection<Trip> findUsertrips() {
        return repository.findAllByOwnedByCurrentUser(User.getCurrentUser());
    }

    public Collection<Trip> findTripsBookedByCurrentUser() {
        return repository.findTripsBookedByUser(User.getCurrentUser());
    }

    // Folgender Bereich ist für das TripRating
    //find all rating
    public Rating findTripRating(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    // save rating
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Filter-Methode (filtern der Fahrten nach Abfahrts- und Ziel)
    public Collection<Trip> findTripsByStartAndEnd(String start, String end) {
        return repository.findTripsByStartAndEnd(start, end);
    }


    //Methode um Buchung zu erzeugen
    public void addBooking(Trip trip, Booking booking) {
        // Trip für Booking (Buchung) setzen
        booking.setTrip(trip);

        long remainingSeats = trip.getRemainingSeats();

        //Genug freie Plaetze fuer Buchung?
        if(remainingSeats < booking.getBookedSeats()) {
            throw new InvalidOperationException("Zu wenig freie Plaetze fuer Buchung");
        }

        // Buchung zu Trip hinzufügen
        trip.getBookings().add(booking);

        //Freie Plaetze in trip neu berechnen
        long newRemainingSeats = remainingSeats - booking.getBookedSeats();
        trip.setRemainingSeats(newRemainingSeats);
    }

    // add rating and making a trip and rating as parameter to call this function
    public void addRating(Trip trip, Rating rating) {

        rating.setTrip(trip);
        trip.getRatings().add(rating);

        //Aktualisiere Durchschnittsbewertung von Fahrer
        User owner = trip.getOwner();

        List<Rating> ratings = ratingRepository.findRatingsForTripOwner(owner);
        float averageRating = calculateAverageRating(ratings);
        owner.setAverageRating(averageRating);
    }

    //Methode um Ratings für Fahrer zu ermitteln
    public List<Rating> findRatingsForTripOwner (User owner) {
        List<Rating> ratings = ratingRepository.findRatingsForTripOwner(owner);
        return ratings;
    }

    //Berechne Durchschnittsbewertung von allen Bewertungen
    private float calculateAverageRating(List<Rating> ratings) {

        if(ratings == null || ratings.isEmpty()) {
            return 0; // 0 heisst keine Rating existiert
        }

        int sumRatings = 0;
        for (Rating rating : ratings) {
            sumRatings = sumRatings + rating.getRate();
        }

        return Math.round((sumRatings * 10.0F) / ratings.size()) / 10.0F;
    }

}
