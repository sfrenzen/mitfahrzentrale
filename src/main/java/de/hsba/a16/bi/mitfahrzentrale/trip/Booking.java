package de.hsba.a16.bi.mitfahrzentrale.trip;

import de.hsba.a16.bi.mitfahrzentrale.user.User;

import javax.persistence.*;

//Diese Klasse definiert eine Buchung
@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private User user;

    private Long bookedSeats;

    //
    // Getter und Setter
    //

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public Trip getTrip() {
        return trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Long getBookedSeats() {
        return bookedSeats;
    }
    public void setBookedSeats(Long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    //Methoden
    @PrePersist
    private void onPersist() {
        this.user = User.getCurrentUser();
    }
}
