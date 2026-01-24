package com.school.admin.bootstrap;

import com.school.admin.domain.Role;
import com.school.admin.domain.User;
import com.school.admin.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    Role.ADMIN
            );
            userRepository.save(admin);
            System.out.println("✅ Created default admin: admin / admin123");
        }
    }
}