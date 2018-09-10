package de.hsba.a16.bi.mitfahrzentrale.trip;
import de.hsba.a16.bi.mitfahrzentrale.user.User;

import javax.persistence.*;


//Diese Klasse definiert die Bewertung einer Fahrt

@Entity
public class Rating {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Trip trip;

	@ManyToOne
	private User givenBy;

	private int rate;

	@Basic(optional = true)
	private String comment;

	// für die leere Form
	public Rating() {
	}

	public Rating(User givenBy, Trip trip) {
		// number of user who called this function
		this.givenBy = givenBy;
		this.trip = trip;
	}

	//
	// Getter und Setter
	//

	public Long getId() {
		return id;
	}

	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public User getGivenBy() {
		return givenBy;
	}
	public void setGivenBy(User givenBy) {
		this.givenBy = givenBy;
	}

	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
