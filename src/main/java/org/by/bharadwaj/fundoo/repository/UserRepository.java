package org.by.bharadwaj.fundoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.by.bharadwaj.fundoo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByPhoneNumber(String phoneNumber);
}
