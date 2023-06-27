package ru.shorty.linkshortener.oauth2.user;

import org.springframework.stereotype.Service;
import ru.shorty.linkshortener.exceptions.common.ResourceNotFoundException;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.repositories.UserRepository;

import java.util.UUID;


@Service
public class CustomUserDetailsService {

    final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUserDetails loadUserByUid(UUID uid) {
        UserModel user = userRepository.findByUid(uid).orElseThrow(() ->
            new ResourceNotFoundException("User", "id", uid));
        return CustomUserDetails.create(user);
    }
}
