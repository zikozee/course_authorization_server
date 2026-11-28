package com.zee.courseoauth2.repository;


import com.zee.courseoauth2.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Nov, 2026
 */

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, UUID> {

    Optional<Authorization> findByState(String state);
    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);
    Optional<Authorization> findByAccessTokenValue(String accessToken);
    Optional<Authorization> findByRefreshTokenValue(String refreshToken);
    Optional<Authorization> findByOidcIdTokenValue(String idToken);


    @Query("select a from Authorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token"
    )
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValue(@Param("token") String token);


    @Modifying
    @Query(nativeQuery = true, value = """
        DELETE FROM authorizations a
        WHERE (a.username=:username or a.principal_name=:username) and a.id <> :currentAuthorizationId
      """)
    void deleteAllPreviousUserSessions(@Param("username") String username, @Param("currentAuthorizationId") UUID currentAuthorizationId);


    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authorizations a WHERE a.access_token_value=?1")
    void deleteAuthRecordOnLogout(String token);

}
