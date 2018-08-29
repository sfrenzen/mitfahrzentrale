package de.hsba.a16.bi.mitfahrzentrale.web.validation;

import de.hsba.a16.bi.mitfahrzentrale.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class SignupFormValidation {
	@Size.List({
		@Size(min = 4, message = "Ihr Nutzername muss mindestens 4 Zeichen lang sein."),
		@Size(max = 30, message = "Ihr Nutzername darf maximal 30 Zeichen lang sein.")
	})

	private String name;
	@Size.List({
		@Size(min = 6, message = "Ihr Passwortmuss mindestens 4 Zeichen lang sein."),
		@Size(max = 30, message = "Ihre Password darf maximal 30 Zeichen lang sein.")
	})
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	@Email(message = "Die Form Ihrer E-Mail ist nicht vollst�?ndig")
//	private String email;

	public SignupFormValidation() {
	}

	public SignupFormValidation(User user) {
		user.setName(getName());
		user.setPassword(getPassword());
//		user.setFirstName(getFirstName());
//		user.setLastName(getLastName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
}
