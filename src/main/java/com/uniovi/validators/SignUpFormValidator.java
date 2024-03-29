package com.uniovi.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class SignUpFormValidator implements Validator {

	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if (user.getEmail().trim().length() == 0) {
			errors.rejectValue("email", "Error.empty");
		}

		if (!emailValido(user.getEmail())) {
			errors.rejectValue("email", "Error.signup.email");
		}

		if (usersService.getUser(user.getEmail()) != null) {
			errors.rejectValue("email", "Error.signup.duplicate");
		}

		if (!user.getName().matches("[a-zA-Z ]*")) {
			errors.rejectValue("name", "Error.signup.name.incorrect");
		}

		if (user.getName().length() < 5 || user.getName().length() > 24) {
			errors.rejectValue("name", "Error.signup.name.length");
		}

		if (!user.getLastname().matches("[a-zA-Z ]*")) {
			errors.rejectValue("lastname", "Error.signup.lastname.incorrect");
		}

		if (user.getLastname().length() < 5 || user.getLastname().length() > 24) {
			errors.rejectValue("lastname", "Error.signup.lastname.length");
		}

		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
		}
		
		if(user.getDireccion().length() <= 5) {
			errors.rejectValue("direccion", "Error.signup.direccion");
		}

	}

	private boolean emailValido(String email) {
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		Matcher mather = pattern.matcher(email);

		return mather.find();
	}

}
