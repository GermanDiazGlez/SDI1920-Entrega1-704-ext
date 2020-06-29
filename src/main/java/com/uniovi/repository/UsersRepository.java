package com.uniovi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE (u.role != 'ROLE_ADMIN' AND u.email != ?1)")
	Page<User> findOtherNonAdminUsers(String email, Pageable pageable);

	@Query("SELECT u FROM User u WHERE (u.role != 'ROLE_ADMIN' AND u.email != ?2) AND ((LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1) OR LOWER(u.lastname) LIKE LOWER(?1)))")
	Page<User> findUsersByNameLastNameAndEmail(String seachtext, String userEmail, Pageable pageable);

	Page<User> findAll(Pageable pageable);

	@Query("SELECT r FROM User u JOIN u.requests r where u.id= ?1")
	Page<User> findRequestsByUser(Long id, Pageable pageable);

	@Query("SELECT f FROM User u JOIN u.friends f where u.id= ?1")
	Page<User> findFriendsByUser(Long id, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE (u.role != 'ROLE_ADMIN') ORDER BY u.comunidad ASC")
	Page<User> findUsersByLocation(Pageable pageable);
	
//	@Query("SELECT u.comunidad FROM User u WHERE (u.role != 'ROLE_ADMIN' AND u.email != ?1) ORDER BY u.comunidad ASC")
//	List<User> findLocation(String comunidad);

}
