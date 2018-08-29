package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

public class TripFormValidation {

	@Size.List({
		@Size(min = 2, message = "Sie m?ssen mindesten 2 Buchstaben eintragen!"),
		@Size(max = 50, message = "Der Name der Stadt kann nicht mehr als 50 Buchstabe sein")
	})
	private String start, end;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull(message = "Bitte geben Sie ein Datum an")
	private Date date;

	private boolean smoking, pet,bookable;

	@Min(value = 1, message = "Mindestens m�ssen Sie einen freien Platz um einen Trip zulegen")
	@Max(value = 14, message = "Mehr als 14 platz geht leider nicht, wir sind keine Buszentrale")
	private int freeSeats;

	@Min(0)
	@Max(value = 1000, message = "Mehr als 1000 Euro f�r einen Platz kann man nicht anfragen")
	private int price;

	public TripFormValidation() {
	}
	public TripFormValidation(Trip trip) {
		trip.setStart(getStart());
		trip.setEnd(getEnd());
		trip.setPet(isPet());
		trip.setFreeSeats(getFreeSeats());
		trip.setDate(getDate());
		trip.setPrice(getPrice());
		trip.setSmoking(isSmoking());
		trip.setBookable(isBookable());
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date)  {
		this.date = date;
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

	public boolean isBookable() {
		return bookable;
	}

	public void setBookable(boolean bookable) {
		this.bookable = bookable;
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
}
