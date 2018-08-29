package de.hsba.a16.bi.mitfahrzentrale.trip;

import de.hsba.a16.bi.mitfahrzentrale.user.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;
/**
 *Diese Klasse definiert eine Fahrt
*/
@Entity
@Table(name = "trip")
@Component
// Folgende Klasse repräsentiert eine Fahrt
public class Trip {

	@Id @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String start, end;

    private boolean smoking, pet, bookable;

    @Basic(optional = false)
    private int freeSeats;

    @Basic(optional = false)
    private int price;

	@ManyToOne
	private User owner;

	//@Temporal ist noetig damit Spring eine Date Variable korrekt in Datenbank speichern kann
	//@DateTimeFormat Annotation ist noetig damit das Datum korrekt angezeigt wird z.B. auf der Details-Seite
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date date;

	private long remainingSeats;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "trip")
	@OrderBy
	private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "trip")
	private List<Rating> ratings;

    //
	//Getter und Setter
	//

    public Long getId() {
        return id;
    }

	public boolean isBookable() {
		return bookable;
	}

	public void setBookable(boolean bookable) {
		this.bookable = bookable;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public boolean isPet() {
        return pet;
    }

    public void setPet(boolean pet) {
        this.pet = pet;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

	public long getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(long remainingSeats) {
		this.remainingSeats = remainingSeats;
	}

    public List<Booking> getBookings() {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        return bookings;
    }

    public List<Rating> getRatings() {
		if (ratings == null){
			ratings = new ArrayList<>();
		}
		return ratings;
	}


	public User getOwner() {
		return owner;
	}

	//
	// Methoden
    //

	@PrePersist
	private void onPersist() {
		this.owner = User.getCurrentUser();
	}

    public boolean isOwnedByCurrentUser() {
	    return this.owner != null && this.owner.equals(User.getCurrentUser());
    }

    public boolean hasBookingForCurrentUser() {
        User currentUser = User.getCurrentUser();
        if(currentUser == null) {
            return false;
        }

        //Suche in Buchungen nach current user
        for( Booking booking: bookings) {
            if( currentUser.equals(booking.getUser())) {
                return true;
            }
        }

	    return false;
    }

    public Rating getRatingGivenByCurrentUser() {
        User currentUser = User.getCurrentUser();
        if(currentUser == null) {
            return null;
        }

        for( Rating rating: ratings) {
            if( currentUser.equals(rating.getGivenBy())) {
                return rating;
            }
        }

        return null;
    }

    //Methode die Anzahl der freien Plätze berechnet
    public long calculateRemainingSeats() {
        long sumBookedSeats = 0L;
        for (Booking booking : bookings) {
            sumBookedSeats = sumBookedSeats + booking.getBookedSeats();
        }

        return freeSeats - sumBookedSeats;
    }
}
