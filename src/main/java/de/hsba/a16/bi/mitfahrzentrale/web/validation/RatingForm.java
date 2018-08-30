package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.trip.Trip;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Size;

public class RatingForm {

    private int rate;

    private String comment;

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

