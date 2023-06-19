package ru.shorty.linkshortener.oauth2;

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
public class UserPrincipal implements OAuth2User, UserDetails {

    private UUID uid;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(UUID uid, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserModel user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserPrincipal(user.getUid(), user.getName(), user.getEmail(), authorities);
    }

    public static UserPrincipal create(UserModel user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
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
