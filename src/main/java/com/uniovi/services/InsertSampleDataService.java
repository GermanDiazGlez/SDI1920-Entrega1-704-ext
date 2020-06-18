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
		User adminUser = new User("Edward Rolando", "Nuñez Valdez", "admin@email.com");
		adminUser.setRole(rolesService.getRoles()[0]);
		adminUser.setPassword("admin");

		usersService.addUser(adminUser);

		// Añadir usuarios estandar
		User user1 = new User("Jesus", "Perez Noriega", "jesus@email.com");
		user1.setRole(rolesService.getRoles()[1]);
		user1.setPassword("123456");

		User user2 = new User("German", "Diaz Gonzalez", "german@email.com");
		user2.setRole(rolesService.getRoles()[1]);
		user2.setPassword("123456");

		User user3 = new User("Frodo", "Bolson", "frodo@email.com");
		user3.setRole(rolesService.getRoles()[1]);
		user3.setPassword("123456");

		User user4 = new User("Merry", "Brandigamo", "merry@email.com");
		user4.setRole(rolesService.getRoles()[1]);
		user4.setPassword("123456");

		User user5 = new User("Pippin", "Peregrin", "pippin@email.com");
		user5.setRole(rolesService.getRoles()[1]);
		user5.setPassword("123456");

		User user6 = new User("Samsagaz", "Gamyi", "samsagaz@email.com");
		user6.setRole(rolesService.getRoles()[1]);
		user6.setPassword("123456");

		User user7 = new User("Gandalf", "Mithrandir", "gandalf@email.com");
		user7.setRole(rolesService.getRoles()[1]);
		user7.setPassword("123456");

		User user8 = new User("Legolas", "Hojaverde", "legolas@email.com");
		user8.setRole(rolesService.getRoles()[1]);
		user8.setPassword("123456");

		User user9 = new User("Gimli", "Enano", "gimli@email.com");
		user9.setRole(rolesService.getRoles()[1]);
		user9.setPassword("123456");

		User user10 = new User("Aragorn", "Dunedain", "aragorn@email.com");
		user10.setRole(rolesService.getRoles()[1]);
		user10.setPassword("123456");

		User user11 = new User("Boromir", "Gondor", "boromir@email.com");
		user11.setRole(rolesService.getRoles()[1]);
		user11.setPassword("123456");

		User user12 = new User("Elrond", "Rivendel", "elrond@email.com");
		user12.setRole(rolesService.getRoles()[1]);
		user12.setPassword("123456");
		
		User user13 = new User("Ernesto", "HijoDeThron", "ernestin@email.com");
		user13.setRole(rolesService.getRoles()[1]);
		user13.setPassword("123456");

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
		usersService.addUser(user10);
		usersService.addUser(user11);
		usersService.addUser(user12);
		usersService.addUser(user13);
	}
}
