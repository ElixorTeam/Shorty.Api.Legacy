package ru.shorty.linkshortener.oauth2.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shorty.linkshortener.exceptions.ResourceNotFoundException;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.repositories.UserRepository;

import java.util.UUID;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUserDetails loadUserByUsername(String email) {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("User not found with email : " + email)
        );
        return CustomUserDetails.create(user);
    }

    @Transactional
    public CustomUserDetails loadUserByUid(UUID uid) {
        UserModel user = userRepository.findByUid(uid).orElseThrow(() ->
            new ResourceNotFoundException("User", "id", uid)
        );
        return CustomUserDetails.create(user);
    }
}
