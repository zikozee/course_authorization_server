package com.zee.courseoauth2.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.UUID;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Nov, 2026
 */

@Slf4j
public final class AuthUtil {

    private AuthUtil(){
        throw new OAuth2AuthenticationException("This is utility class and cannot be instantiated");
    }

    public static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)){
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        return new AuthorizationGrantType(authorizationGrantType);
    }

    public static UUID fromString(String uuid){
        return UUID.fromString(uuid);
    }

    public static String uuidToString(UUID uuid){
        return uuid.toString();
    }

    public static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod){
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)){
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.NONE;
        }
        return new ClientAuthenticationMethod(clientAuthenticationMethod);

    }
}
