package com.sistem_bank.fibank.config;

import com.sistem_bank.fibank.domain.Role;
import com.sistem_bank.fibank.domain.User;
import com.sistem_bank.fibank.repository.RoleRepository;
import com.sistem_bank.fibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));d
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

        if(!userRepository.existsByUsername("admin")) {
            User admin = User
                    .builder()
                    .username("admin")
                    .phoneNumber("0563452345")
                    .email("admin1@gmail.com")
                    .password(passwordEncoder.encode("pass123"))
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);
        }
    }
}

