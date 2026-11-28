package com.zee.courseoauth2.repository.impl;


import com.zee.courseoauth2.entity.Client;
import com.zee.courseoauth2.repository.ClientRepository;
import com.zee.courseoauth2.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Nov, 2026
 */

@Repository
@RequiredArgsConstructor
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientRepository clientRepository;

    @Override
    public void save(@NonNull RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.clientRepository.save(buildFromRegisteredClient(registeredClient));
    }

    @Override
    public @Nullable RegisteredClient findById(@NonNull String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.clientRepository.findById(Long.valueOf(id))
                .map(this::buildFromClient).orElse(null);
    }

    @Override
    public @Nullable RegisteredClient findByClientId(@NonNull String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.clientRepository.findByClientId(clientId)
                .map(this::buildFromClient).orElse(null);
    }


    private Client buildFromRegisteredClient(RegisteredClient registeredClient){
        List<String> clientAuthenticationMethods = new ArrayList<>(registeredClient.getClientAuthenticationMethods().size());

        registeredClient.getClientAuthenticationMethods().forEach(
                method ->
                        clientAuthenticationMethods.add(method.getValue())
        );

        List<String> authorizationGrantTypes = new ArrayList<>(registeredClient.getAuthorizationGrantTypes().size());

        registeredClient.getAuthorizationGrantTypes().forEach(
                grantType ->
                        authorizationGrantTypes.add(grantType.getValue())
        );



        Client client = new Client();
        client.setId(Long.valueOf(registeredClient.getId()));
        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setClientName(registeredClient.getClientName());
        client.setClientAuthenticationMethods(
                StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods));
        client.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes));
        client.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        client.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getPostLogoutRedirectUris()));
        client.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));
        client.setAccessTokenTimeToLiveInMinutes((int)registeredClient.getTokenSettings().getAccessTokenTimeToLive().toMinutes());
        client.setRefreshTokenTimeToLiveInMinutes((int)registeredClient.getTokenSettings().getRefreshTokenTimeToLive().toMinutes());

        return client;
    }

    private RegisteredClient buildFromClient(Client client){
        Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(
                client.getClientAuthenticationMethods());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(
                client.getAuthorizationGrantTypes());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(
                client.getRedirectUris());
        Set<String> postLogoutRedirectUris = StringUtils.commaDelimitedListToSet(
                client.getPostLogoutRedirectUris());
        Set<String> clientScopes = StringUtils.commaDelimitedListToSet(
                client.getScopes());


        RegisteredClient.Builder builder = RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(authenticationMethodsConsumer ->
                        clientAuthenticationMethods.forEach(method ->
                                authenticationMethodsConsumer.add(AuthUtil.resolveClientAuthenticationMethod(method)))
                )
                .authorizationGrantTypes(grantTypesConsumer ->
                        authorizationGrantTypes.forEach(grantType ->
                                grantTypesConsumer.add(AuthUtil.resolveAuthorizationGrantType(grantType))
                        )
                )
                .redirectUris(redirectUrisConsumer -> redirectUrisConsumer.addAll(redirectUris))
                .postLogoutRedirectUris(postLogoutRedirectUrisConsumer ->
                        postLogoutRedirectUrisConsumer.addAll(postLogoutRedirectUris)
                )
                .scopes(scopesConsumer -> scopesConsumer.addAll(clientScopes));


        builder.tokenSettings(
                TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(client.getAccessTokenTimeToLiveInMinutes()))
                        .refreshTokenTimeToLive(Duration.ofMinutes(client.getRefreshTokenTimeToLiveInMinutes()))
                        .reuseRefreshTokens(false)
                        .build()
        );

        builder.clientSettings(
                ClientSettings.builder()
                        .requireProofKey(client.isRequiresProofKey())
                        .requireAuthorizationConsent(false)
                        .build()
        );

        return builder.build();
    }
}
