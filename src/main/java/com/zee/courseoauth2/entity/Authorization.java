package com.zee.courseoauth2.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Nov, 2026
 */

@Getter
@Setter
@Entity
@Table(name = "authorizations",
        indexes = {
            @Index(name = "idx_auth_access_token", columnList = "access_token_value"),
            @Index(name = "idx_auth_refresh_token", columnList = "refresh_token_value"),
            @Index(name = "idx_auth_username", columnList = "username"),
            @Index(name = "idx_auth_session_id", columnList = "session_id")
        })
public class Authorization extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50)
    private String username;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Column(length = 1000)
    private String authorizedScopes;
    @Column(columnDefinition = "TEXT")
    private String attributes;
    @Column(length = 500)
    private String state;

    @Column(length = 4000)
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;

    @Column(length = 4000)
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Column(length = 2000)
    private String accessTokenMetadata;
    private String accessTokenType;
    @Column(length = 1000)
    private String accessTokenScopes;

    @Column(length = 4000)
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Column(length = 2000)
    private String refreshTokenMetadata;

    @Column(length = 4000)
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Column(length = 2000)
    private String oidcIdTokenMetadata;
    @Column(length = 2000)
    private String oidcIdTokenClaims;
    @Column(length = 100)
    private String sessionId;

}
