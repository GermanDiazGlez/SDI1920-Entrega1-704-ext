package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UsersController {
	
	private Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private RolesService rolesService;

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("Acceso al Home");
		return "home";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);

		if (result.hasErrors()) {
			return "signup";
		}

		user.setRole(rolesService.getRoles()[1]);
		
		

		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		logger.info("Usuario se registra");
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", true);
		}
		logger.info("Usuario se loggea");
		return "login";
	}

	@RequestMapping("/user/list")
	public String listUsers(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

		Page<User> users;

		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersBySearchText(searchText, pageable, userEmail);
		} else {
			users = usersService.getUsersByLocation(pageable);
		}

		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUser(email);

		model.addAttribute("activeUser", activeUser);
		logger.info("Lista de usuarios");
		return "user/list";
	}

	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable, @RequestParam Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User activeUser = usersService.getUser(auth.getName());

		User friend = usersService.getUserById(id);
		friend.getRequests().add(activeUser);
		usersService.updateUser(activeUser);

		Page<User> users = usersService.getOtherNonAdminUsers(activeUser.getEmail(), pageable);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("activeUser", activeUser);
		model.addAttribute("page", users);
		logger.info("Actualizar lista de usuarios");
		return "user/list :: tableUsers";
	}

	@RequestMapping("/user/requests")
	public String getRequests(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User activeUser = usersService.getUser(auth.getName());

		Page<User> users = usersService.getRequestsByUser(activeUser.getId(), pageable);

		model.addAttribute("requests", users.getContent());
		model.addAttribute("page", users);
		logger.info("Mostrando peticiones");
		return "user/requests";
	}

	@RequestMapping("/user/friends")
	public String getFriends(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User activeUser = usersService.getUser(auth.getName());

		Page<User> users = usersService.getFriendsByUser(activeUser.getId(), pageable);

		model.addAttribute("friends", users.getContent());
		model.addAttribute("page", users);
		logger.info("Mostrando amigos del usuario");
		return "user/friends";
	}

	@RequestMapping("/user/acceptRequest/{id}")
	public String acceptRequest(Model model, @PathVariable Long id) {
		User friend = usersService.getUserById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User activeUser = usersService.getUser(auth.getName());
		activeUser.acceptRequestFrom(friend);

		usersService.updateUser(activeUser);
		logger.info("Enviando peticion a usuario: " + friend.getName());
		return "redirect:/user/requests";
	}
	
	@RequestMapping(value = { "/user/paginaAdmin" }, method = RequestMethod.GET)
	public String adminPage(Model model) {
		logger.warn("Mostrando pagina del administrador");
		return "/user/paginaAdmin";
	}
	
	
}
