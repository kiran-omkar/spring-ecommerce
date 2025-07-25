package com._6.EComm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com._6.EComm.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
