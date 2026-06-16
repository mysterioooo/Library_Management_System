package com.library;

import com.library.entity.Role;
import com.library.entity.User;
import com.library.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryApplication {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(
				LibraryApplication.class,
				args);
	}

	@PostConstruct
	public void addAdminDetail() {

		String adminEmail =
				"contact@fullstackjavadeveloper.com";

		if (userRepository
				.findByEmail(adminEmail)
				.isPresent()) {

			System.out.println(
					"Admin already exists");

			return;
		}

		User user =
				User.builder()
						.name("Admin")
						.email(adminEmail)
						.password(
								passwordEncoder.encode(
										"12345"))
						.role(Role.ROLE_ADMIN)
						.build();

		userRepository.save(user);

		System.out.println(
				"Admin user created successfully");
	}
}