package com.sistem_bank.fibank.security;

import com.sistem_bank.fibank.domain.User;
import com.sistem_bank.fibank.domain.UserPrincipal;
import com.sistem_bank.fibank.exceptions.UserNotFoundException;
import com.sistem_bank.fibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("user not found"));
        return new UserPrincipal(user);
    }
}
