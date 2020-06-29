package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;
	
	@PostConstruct
	public void init() {
		// Añadir administrador
		User adminUser = new User("Edward Rolando", "Nuñez Valdez", "admin@email.com", "oviedo", "Asturias");
		adminUser.setRole(rolesService.getRoles()[0]);
		adminUser.setPassword("admin");

		usersService.addUser(adminUser);

		// Añadir usuarios estandar
		User user1 = new User("Jesus", "Perez Noriega", "jesus@email.com", "oviedo", "Asturias");
		user1.setRole(rolesService.getRoles()[1]);
		user1.setPassword("123456");

		User user2 = new User("German", "Diaz Gonzalez", "german@email.com", "oviedo", "Asturias");
		user2.setRole(rolesService.getRoles()[1]);
		user2.setPassword("123456");

		User user3 = new User("Frodo", "Bolson", "frodo@email.com", "oviedo", "Asturias");
		user3.setRole(rolesService.getRoles()[1]);
		user3.setPassword("123456");

		User user4 = new User("Merry", "Brandigamo", "merry@email.com", "oviedo", "Asturias");
		user4.setRole(rolesService.getRoles()[1]);
		user4.setPassword("123456");

		User user5 = new User("Pippin", "Peregrin", "pippin@email.com", "oviedo", "Asturias");
		user5.setRole(rolesService.getRoles()[1]);
		user5.setPassword("123456");


		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);

		
	}
}
