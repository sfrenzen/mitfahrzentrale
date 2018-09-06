package de.hsba.a16.bi.mitfahrzentrale.web;

import de.hsba.a16.bi.mitfahrzentrale.trip.TripServices;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	private TripServices tripServices;


    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    // jede kann diesen URL aufrufen
    @GetMapping("/login")
    public String login() {
        //Bevor die Login Seite gezeigt wird, wird geprueft ob der Benutzer anonym oder bereits angemeldet ist,
        //falls der Benutzer anonym ist, wird die Login Seite angezeigt, sonst wird die Start Seite gezeigt
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth instanceof AnonymousAuthenticationToken ? "login" : "redirect:/index";
    }
}
