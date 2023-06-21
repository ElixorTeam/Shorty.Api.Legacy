package ru.shorty.linkshortener.oauth2.user;

import io.micrometer.common.util.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.shorty.linkshortener.exceptions.OAuth2AuthenticationProcessingException;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.oauth2.providers.common.AuthProvider;
import ru.shorty.linkshortener.oauth2.providers.common.OAuth2ProviderFactory;
import ru.shorty.linkshortener.oauth2.providers.common.OAuth2UserProvider;
import ru.shorty.linkshortener.repositories.UserRepository;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws Exception {
        String providerName = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserProvider oAuth2UserInfo = OAuth2ProviderFactory.getOAuth2User(providerName, oAuth2User.getAttributes());

        UserModel user = getUserByEmail(oAuth2UserInfo).map(existingUser -> {
                validateUserProvider(existingUser, oAuth2UserRequest.getClientRegistration().getRegistrationId());
                return updateExistingUser(existingUser, oAuth2UserInfo);
            })
            .orElseGet(() -> registerNewUser(oAuth2UserRequest, oAuth2UserInfo));

        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    private UserModel registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserProvider oAuth2UserInfo) {
        UserModel user = new UserModel();
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setProviderId(oAuth2UserInfo.getId());
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        return userRepository.save(user);
    }

    private Optional<UserModel> getUserByEmail(OAuth2UserProvider oAuth2UserInfo) {
        String email = oAuth2UserInfo.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        return userRepository.findByEmail(email);
    }

    private UserModel updateExistingUser(UserModel existingUser, OAuth2UserProvider oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private void validateUserProvider(UserModel user, String providerName) {
        if (!user.getProvider().equals(AuthProvider.valueOf(providerName))) {
            String msg = String.format("Looks like you're signed up with %s account. Please use your %s account to login.",
                user.getProvider(), user.getProvider());
            throw new OAuth2AuthenticationProcessingException(msg);
        }
    }
}
