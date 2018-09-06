package de.hsba.a16.bi.mitfahrzentrale.web.fehler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbidden extends RuntimeException {
}
