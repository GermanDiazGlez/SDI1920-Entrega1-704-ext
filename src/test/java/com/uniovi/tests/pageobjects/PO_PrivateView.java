package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PO_PrivateView extends PO_NavView {

	static public void login(WebDriver driver, String classname, String email, String password, String elementType,
			String elementValue) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", classname);
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, email, password);
		// COmprobamos que entramos en la pagina privada del Profesor
		PO_View.checkElement(driver, elementType, elementValue);
	}

	static public void logout(WebDriver driver, String elementType, String elementValue) {
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", elementType, elementValue);
	}

	static public void enviarPeticion(WebDriver driver, String user) {
		By boton = By.id(user);
		driver.findElement(boton).click();
	}
}
