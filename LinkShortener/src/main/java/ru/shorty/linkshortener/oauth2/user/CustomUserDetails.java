package ru.shorty.linkshortener.oauth2.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.shorty.linkshortener.models.UserModel;

import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetails implements OAuth2User, UserDetails {

    UUID uid;
    String name;
    String email;
    Collection<? extends GrantedAuthority> authorities;
    Map<String, Object> attributes;

    public CustomUserDetails(UUID uid, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(UserModel user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        return new CustomUserDetails(user.getUid(), user.getName(), user.getEmail(), authorities);
    }

    public static CustomUserDetails create(UserModel user, Map<String, Object> attributes) {
        CustomUserDetails customUserDetails = create(user);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return String.valueOf(uid);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
