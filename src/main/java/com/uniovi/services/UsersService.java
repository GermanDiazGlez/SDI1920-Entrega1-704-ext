package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private int numeroUsuarios = 0;

	public Page<User> getUsers(Pageable pageable) {
		return usersRepository.findAll(pageable);
	}

	public Page<User> searchUsersBySearchText(String searchText, Pageable pageable, String userEmail) {
		return usersRepository.findUsersByNameLastNameAndEmail("%" + searchText + "%", userEmail, pageable);
	}

	public Page<User> getOtherNonAdminUsers(String email, Pageable pageable) {
		return usersRepository.findOtherNonAdminUsers(email, pageable);
	}

	public User getUser(String email) {
		return usersRepository.findByEmail(email);
	}

	public User getUserById(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
		numeroUsuarios++;
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public void updateUser(User user) {
		usersRepository.save(user);
	}

	public Page<User> getRequestsByUser(Long id, Pageable pageable) {
		return usersRepository.findRequestsByUser(id, pageable);
	}

	public Page<User> getFriendsByUser(Long id, Pageable pageable) {
		return usersRepository.findFriendsByUser(id, pageable);
	}

	public int getNumeroUsuarios() {
		return numeroUsuarios;
	}

}
