package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.user.User;
import org.springframework.stereotype.Component;

/**
 *Form-Validierung
*/

@Component
public class SignupFormAssembler {
	public SignupFormValidation toForm (User user){
		SignupFormValidation formValidation = new SignupFormValidation(user);
		return formValidation;
	}
	public User update (User user, SignupFormValidation formValidation){
		user.setName(formValidation.getName());
//		user.setFirstName(formValidation.getFirstName());
//		user.setLastName(formValidation.getLastName());
		user.setPassword(formValidation.getPassword());
//		user.setEmail(formValidation.getEmail());
		return user;
	}
}
