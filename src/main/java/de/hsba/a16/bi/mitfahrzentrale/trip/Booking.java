package de.hsba.a16.bi.mitfahrzentrale.trip;

import de.hsba.a16.bi.mitfahrzentrale.user.User;

import javax.persistence.*;

//Diese Klasse definiert eine Buchung
@Entity
public class Booking {
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    @Id
    @GeneratedValue
    private Long id;


    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @ManyToOne
    private Trip trip;

    public Long getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(Long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    private Long bookedSeats;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    private User user;


    @PrePersist
    private void onPersist() {
        this.user = User.getCurrentUser();
    }
}
