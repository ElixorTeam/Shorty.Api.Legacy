package ru.shorty.linkshortener.oauth2.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.shorty.linkshortener.exceptions.common.ResourceNotFoundException;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.repositories.UserRepository;

import java.util.UUID;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomUserDetailsService {

    UserRepository userRepository;


    public CustomUserDetails loadUserByUid(UUID uid) {
        UserModel user = userRepository.findByUid(uid).orElseThrow(() ->
            new ResourceNotFoundException("User", "id", uid));
        return CustomUserDetails.create(user);
    }
}
