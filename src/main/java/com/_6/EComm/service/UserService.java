package com._6.EComm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com._6.EComm.entity.Role;
import com._6.EComm.entity.User;
import com._6.EComm.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User createUser(User user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.USER);
    return userRepository.save(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User updateUser(User user) {
    // update only name and password
    User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    existingUser.setName(user.getName());
    existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(existingUser);
  }

  public User getUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    // do not return password of the user
    user.setPassword(null);
    return user;
  }
}
