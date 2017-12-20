package com.hellokoding.account.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellokoding.account.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
