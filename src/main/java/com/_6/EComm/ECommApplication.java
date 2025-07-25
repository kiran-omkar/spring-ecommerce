package com._6.EComm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.beans.factory.annotation.Autowired;
import com._6.EComm.entity.User;
import com._6.EComm.entity.Role;
import com._6.EComm.repository.UserRepository;

@SpringBootApplication
public class ECommApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner createAdminUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			String adminEmail = "admin@gmail.com";
			if (userRepository.findByEmail(adminEmail).isEmpty()) {
				User admin = new User();
				admin.setName("Admin");
				admin.setEmail(adminEmail);
				admin.setPassword(passwordEncoder.encode("admin@123"));
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);
				System.out.println("Admin user created: " + adminEmail + " / admin@123");
			}
		};
	}
}
