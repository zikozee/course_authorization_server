# SPRING AUTHORIZATION SERVER

## AUTHORIZATION CODE
- http://127.0.0.1:8080/oauth2/authorize?response_type=code&client_id=oidc-client&scope=openid%20profile&redirect_uri=https://spring.io&state=xyz

openid profile
openid%20profile

## classes to debug

### request
 - OAuth2AuthorizationCodeRequestAuthenticationConverter
 - OAuth2AuthorizationCodeRequestAuthenticationToken
 - OAuth2AuthorizationCodeRequestAuthenticationProvider


### token
 - OAuth2AuthorizationCodeAuthenticationConverter
 - OAuth2AuthorizationCodeAuthenticationToken
 - OAuth2AuthorizationCodAuthenticationProvider


## AUTHORIZATION CODE PKCE
- http://127.0.0.1:8080/oauth2/authorize?response_type=code&client_id=oidc-client2&scope=openid&redirect_uri=https://spring.io&state=xyz&code_challenge=ACUdsiwTKNwm2jM5ByMDpCZCp4jd9jQjUSPk98Zdyqs&code_challenge_method=S256

- ClientAuthenticationMethod.NONE for SPA and mobile apps
  - you should not get refresh token
  - does not require Basic Authentication 

- ClientAuthenticationMethod.CLIENT_SECRET_BASIC
  - you should get refresh token
  - requires Basic Authentication 