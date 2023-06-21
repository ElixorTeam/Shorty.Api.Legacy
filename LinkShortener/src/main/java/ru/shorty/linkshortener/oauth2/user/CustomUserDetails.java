package ru.shorty.linkshortener.oauth2.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.shorty.linkshortener.models.UserModel;

import java.util.*;

@Getter
@Setter
public class CustomUserDetails implements OAuth2User, UserDetails {

    private UUID uid;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetails(UUID uid, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(UserModel user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
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
        return email;
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
