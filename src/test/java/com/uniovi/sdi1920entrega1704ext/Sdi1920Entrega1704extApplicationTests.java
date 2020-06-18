package com.uniovi.sdi1920entrega1704ext;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.utils.SeleniumUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sdi1920Entrega1704extApplicationTests {

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\germa\\Desktop\\SDI\\Practica-5-Selenium\\OneDrive_2020-03-01\\PL-SDI-Sesion5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	// PR1. Registro de Usuario con datos válidos
	@Test
	public void PR1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "primeoptimus@email.com", "Optimus", "Prime", "123456", "123456");
		PO_View.getP();
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Listar usuarios");
		PO_View.checkElement(driver, "text", "Listar peticiones");
		PO_View.checkElement(driver, "text", "Listar amigos");
	}

	// PR2. Registro de Usuario con datos inválidos(email, nombre y apellidos
	// vacios)
	@Test
	public void PR2() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "", "", "77777", "77777");
		PO_View.getP();
		// Comprobamos el error de campos vacios.
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
	}

	// PR3. Registro de Usuario con datos inválidos(contraseñas no coinciden)
	@Test
	public void PR3() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "primeoptimus@email.com", "Optimus", "Prime", "77777", "66666");
		PO_View.getP();
		// Comprobamos el error de contraseñas no coinciden.
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PR4. Registro de Usuario con datos inválidos(email existente)
	@Test
	public void PR4() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "gandalf@email.com", "Gandalf", "Mithrandir", "77777", "77777");
		PO_View.getP();
		// Comprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.duplicate", PO_Properties.getSPANISH());
	}

	// PR5. Loguearse con exito (administrador)
	@Test
	public void PR5() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Listar usuarios");
		PO_View.checkElement(driver, "text", "Listar peticiones");
		PO_View.checkElement(driver, "text", "Listar amigos");
	}

	// PR6. Loguearse con exito (usuario estandar)
	@Test
	public void PR6() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "gandalf@email.com", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Listar usuarios");
	}

	// PR7. Loguearse sin exito (usuario estandar)
	@Test
	public void PR7() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos el error de campos vacios
		PO_RegisterView.checkKey(driver, "Login.error", PO_Properties.getSPANISH());
	}

	// PR8. Loguearse sin exito (usuario estandar)
	@Test
	public void PR8() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "gandalf@email.com", "55555");
		// Comprobamos el error de contraseña incorrecta
		PO_RegisterView.checkKey(driver, "Login.error", PO_Properties.getSPANISH());
	}

	// PR9. Identificación válida y desconexión, redirigir a Home
	@Test
	public void PR9() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "optimus@email.com", "Optimus", "Prime", "123456", "123456");
		PO_View.getP();
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", "Listar usuarios");
		PO_View.checkElement(driver, "text", "Listar peticiones");
		PO_View.checkElement(driver, "text", "Listar amigos");
		// Desconexion
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado
	@Test
	public void PR10() {
		driver.navigate().to("http://localhost:8090");
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");

		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");
		PO_View.checkElement(driver, "text", "Desconectar");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
	}

	// PR11. Loguearse, mostrar listado de usuarios y comprobar que se muestran
	// todos los que hay en el sistema
	@Test
	public void PR11() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

		driver.navigate().to("http://localhost:8090/user/list?page=1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

		driver.navigate().to("http://localhost:8090/user/list?page=2");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());

		assertTrue(elementos.size() == 4);
	}

	// PR12. Loguearse, mostrar listado de usuarios, hacer una busqueda vacia y
	// comprobar que salen todos los usuarios
	@Test
	public void PR12() {
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		WebElement searchField = driver.findElement(By.id("search"));
		searchField.click();
		searchField.clear();
		searchField.sendKeys("");

		By boton = By.className("btn");
		driver.findElement(boton).click();

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

		driver.navigate().to("http://localhost:8090/user/list?searchText=");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

		driver.navigate().to("http://localhost:8090/user/list?page=2");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());

		assertTrue(elementos.size() == 4);
	}

	// PR13. Loguearse, mostrar listado de usuarios, hacer una busqueda que no
	// exista y comprobar que no salen usuarios
	@Test
	public void PR13() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		// En el campo de busqueda introducimos el criterio a buscar
		WebElement searchField = driver.findElement(By.id("search"));
		searchField.click();
		searchField.clear();
		searchField.sendKeys("");

		// Clicamos el boton de enviar query
		By boton = By.className("btn");
		driver.findElement(boton).click();

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
	}

	// PR14. Loguearse, mostrar listado de usuarios, hacer una busqueda correcta y
	// comprobar que salen los usuarios que deberian
	@Test
	public void PR14() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		// En el campo de busqueda introducimos el criterio a buscar
		WebElement searchField = driver.findElement(By.id("search"));
		searchField.click();
		searchField.clear();
		searchField.sendKeys("gan");

		// Clicamos el botonde enviar query
		By boton = By.className("btn");
		driver.findElement(boton).click();

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}

	// PR15. Enviar una invitacion de amistad valida a un usuario de la lista de
	// usuarios
	@Test
	public void PR15() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		// Se puede enviar petición de amistad a samsagaz@email.com
		SeleniumUtils.EsperaCargaPagina(driver, "text", "samsagaz@email.com", PO_View.getTimeout());

		// Enviamos una invitación de amistad a Samsagaz
		PO_PrivateView.enviarPeticion(driver, "addRequest7");

		// Comprobamos que aparece el mensaje "Petición de solicitud enviada"
		PO_View.checkElement(driver, "text", "Solicitud enviada");

		// Cambiamos de usuario
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "samsagaz@email.com", "123456", "text", "Listar peticiones");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/requests");

		List<WebElement> peticiones = SeleniumUtils.EsperaCargaPagina(driver, "text", "Aceptar", PO_View.getTimeout());
		assertTrue(peticiones.size() == 1);
	}

	// PR16. Intentar enviar una invitacion de amistad a un usuario al que ya se la
	// habíamos mandado
	@Test(expected = NoSuchElementException.class)
	public void PR16() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		// Se puede enviar petición de amistad a samsagaz@email.com
		SeleniumUtils.EsperaCargaPagina(driver, "text", "samsagaz@email.com", PO_View.getTimeout());

		// Enviamos una invitación de amistad a Samsagaz
		PO_PrivateView.enviarPeticion(driver, "addRequest7");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/list");

		// Comprobamos que aparece el mensaje "Petición de solicitud enviada"
		PO_View.checkElement(driver, "text", "Solicitud enviada");

		// Enviamos otra invitación de amistad a Samsagaz y debe saltar una
		// NoSuchElementException
		PO_PrivateView.enviarPeticion(driver, "addRequest7");

		SeleniumUtils.textoPresentePagina(driver, "Solicitud enviada");
	}

	// PR17. Enviar varias invitaciones de amistad a un usuario y comprobar que le
	// llegan
	@Test
	public void PR17() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		// Se puede enviar petición de amistad a merry@email.com
		SeleniumUtils.EsperaCargaPagina(driver, "text", "merry@email.com", PO_View.getTimeout());

		// Enviamos una invitación de amistad a Merry
		PO_PrivateView.enviarPeticion(driver, "addRequest5");

		// Cambiamos de usuario
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "elrond@email.com", "123456", "text", "Listar usuarios");

		// Se puede enviar petición de amistad a merry@email.com
		SeleniumUtils.EsperaCargaPagina(driver, "text", "merry@email.com", PO_View.getTimeout());

		// Enviamos una invitación de amistad a Merry
		PO_PrivateView.enviarPeticion(driver, "addRequest5");

		// Cambiamos de usuario ahora a samsagaz
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "merry@email.com", "123456", "text", "Listar peticiones");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/requests");

		List<WebElement> peticiones = SeleniumUtils.EsperaCargaPagina(driver, "text", "Aceptar", PO_View.getTimeout());
		assertTrue(peticiones.size() == 2);
	}

	// PR18. Enviar una invitacion de amistad valida y aceptarla
	@Test
	public void PR18() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "Listar usuarios");

		// Se puede enviar petición de amistad a pippin@email.com
		SeleniumUtils.EsperaCargaPagina(driver, "text", "pippin@email.com", PO_View.getTimeout());

		// Enviamos una invitación de amistad a Pippin
		PO_PrivateView.enviarPeticion(driver, "addRequest6");

		// Comprobamos que aparece el mensaje "Petición de solicitud enviada"
		PO_View.checkElement(driver, "text", "Solicitud enviada");

		// Cambiamos de usuario
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "pippin@email.com", "123456", "text", "Listar peticiones");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/requests");

		List<WebElement> peticiones = SeleniumUtils.EsperaCargaPagina(driver, "text", "Aceptar", PO_View.getTimeout());
		assertTrue(peticiones.size() == 1);

		PO_View.checkElement(driver, "text", "Aceptar");

		// Aceptamos la invitación de amistad de frodo
		PO_PrivateView.enviarPeticion(driver, "btnAceptar");

		SeleniumUtils.textoNoPresentePagina(driver, "Aceptar");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/friends");

		SeleniumUtils.textoPresentePagina(driver, "frodo");
	}

	// PR19. Enviar una invitacion de amistad valida, aceptarla y comprobar que
	// pasan a ser amigos
	@Test
	public void PR19() {
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "ernestin@email.com", "123456", "text", "Listar usuarios");

		// Se puede enviar petición de amistad a german@email.com
		SeleniumUtils.EsperaCargaPagina(driver, "text", "german@email.com", PO_View.getTimeout());

		SeleniumUtils.textoPresentePagina(driver, "german");

		// Enviamos una invitación de amistad a German
		PO_PrivateView.enviarPeticion(driver, "addRequest3");

		// Comprobamos que aparece el mensaje "Petición de solicitud enviada"
		PO_View.checkElement(driver, "text", "Solicitud enviada");

		// Cambiamos de usuario
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "german@email.com", "123456", "text", "Listar peticiones");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/requests");

		List<WebElement> peticiones = SeleniumUtils.EsperaCargaPagina(driver, "text", "Aceptar", PO_View.getTimeout());
		assertTrue(peticiones.size() == 1);

		PO_View.checkElement(driver, "text", "Aceptar");

		// Aceptamos la invitación de amistad de frodo
		PO_PrivateView.enviarPeticion(driver, "btnAceptar");

		SeleniumUtils.textoNoPresentePagina(driver, "Aceptar");

		// Nos movemos a la lista de solicitudes
		driver.navigate().to("http://localhost:8090/user/friends");

		SeleniumUtils.textoPresentePagina(driver, "ernestin");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	// PR20. Internacionalizacion de 4 vistas
	@Test
	public void PR20() {
		driver.navigate().to("http://localhost:8090");

		// Comprobamos el texto HOME en español
		SeleniumUtils.EsperaCargaPagina(driver, "text", "RED SOCIAL", PO_View.getTimeout());
		
		SeleniumUtils.textoPresentePagina(driver, "RED SOCIAL");

		// Cambiamos de idioma
		driver.navigate().to("http://localhost:8090/?lang=EN");
		SeleniumUtils.esperarSegundos(driver, 3);

		SeleniumUtils.EsperaCargaPagina(driver, "text", "SOCIAL NETWORK", PO_View.getTimeout());

		// Comprobamos el texto en HOME en ingles
		SeleniumUtils.textoPresentePagina(driver, "SOCIAL NETWORK");

		// Login
		PO_PrivateView.login(driver, "btn btn-primary", "frodo@email.com", "123456", "text", "List users");

		// Comprobamos el texto lista usuarios en ingles
		SeleniumUtils.textoPresentePagina(driver, "Users");
		SeleniumUtils.textoPresentePagina(driver, "Name");

		// Cambiamos de idioma
		PO_NavView.changeIdiom(driver, "Spanish");
		
		SeleniumUtils.esperarSegundos(driver, 3);
		// Comprobamos el texto lista usuarios en español
		SeleniumUtils.textoPresentePagina(driver, "Usuarios");
		SeleniumUtils.textoPresentePagina(driver, "Nombre");

		// Nos movemos a las solicitudes de amistad
		driver.navigate().to("http://localhost:8090/user/requests");
		SeleniumUtils.textoPresentePagina(driver, "Peticiones de amistad");
		SeleniumUtils.textoPresentePagina(driver, "Correo electronico");

		// Cambiamos de idioma
		PO_NavView.changeIdiom(driver, "English");
		SeleniumUtils.esperarSegundos(driver, 3);
		SeleniumUtils.textoPresentePagina(driver, "Friendship requests");
		SeleniumUtils.textoPresentePagina(driver, "Email");

		// Nos movemos a la lista de amigos
		driver.navigate().to("http://localhost:8090/user/friends");
		SeleniumUtils.esperarSegundos(driver, 3);
		SeleniumUtils.textoPresentePagina(driver, "My friends");
		SeleniumUtils.textoPresentePagina(driver, "Lastname");

		// Cambiamos de idioma
		PO_NavView.changeIdiom(driver, "Spanish");
		SeleniumUtils.esperarSegundos(driver, 3);
		SeleniumUtils.textoPresentePagina(driver, "Mis amigos");
		SeleniumUtils.textoPresentePagina(driver, "Apellidos");
	}

	// PR21. Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios. Se deberá volver al formulario de login
	@Test
	public void PR21() {
		driver.navigate().to("http://localhost:8090/user/list");

		SeleniumUtils.textoPresentePagina(driver, "Identificate");
	}

	// PR22. Intentar acceder sin estar autenticado a la opción de listado de
	// peticiones. Se deberá volver al formulario de login
	@Test
	public void PR22() {
		driver.navigate().to("http://localhost:8090/user/requests");

		SeleniumUtils.textoPresentePagina(driver, "Identificate");
	}

	// PR23. Intentar acceder sin estar autorizado a un apartado de admin. Debera
	// salir un mensaje de forbiden
	@Test
	public void PR23() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "gandalf@email.com", "123456");

		// Comprobamos que NO entramos en la pagina privada de Admin
		driver.navigate().to("http://localhost:8090/user/paginaAdmin");

		// Pagina no accesible
		PO_View.checkElement(driver, "h1", "HTTP Status 403 – Forbidden");
	}
}
