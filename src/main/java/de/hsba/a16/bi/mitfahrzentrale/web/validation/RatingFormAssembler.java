package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.trip.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingFormAssembler {

    public RatingForm toForm(Rating rating) {
        RatingForm form = new RatingForm();
        form.setRate(rating.getRate());
        form.setComment(rating.getComment());
        return form;
    }

    public Rating update(Rating rating, RatingForm form) {
        rating.setRate(form.getRate());
        rating.setComment(form.getComment());
        return rating;
    }
}