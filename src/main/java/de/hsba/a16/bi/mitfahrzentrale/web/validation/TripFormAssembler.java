package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripFormAssembler {
	public TripFormValidation toForm(Trip trip){
		TripFormValidation tripFormValidation = new TripFormValidation();
		return tripFormValidation;
	}

	// TODO: 13.08.2018: Datum muss comparable gemacht werden und genau formalliert
	public Trip update (Trip trip, TripFormValidation tripFormValidation){
		trip.setBookable(tripFormValidation.isBookable());
		trip.setSmoking(tripFormValidation.isSmoking());
		trip.setPet(tripFormValidation.isPet());
		trip.setPrice(tripFormValidation.getPrice());
		trip.setStart(tripFormValidation.getStart());
		trip.setDate(tripFormValidation.getDate());
		trip.setEnd(tripFormValidation.getEnd());
		trip.setFreeSeats(tripFormValidation.getFreeSeats());
		return trip;
	}
}
