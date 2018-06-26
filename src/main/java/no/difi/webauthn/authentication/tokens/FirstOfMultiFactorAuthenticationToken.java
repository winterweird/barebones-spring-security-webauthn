package no.difi.webauthn.authentication.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Generic token that can allow multi factor authentication using a
 * FirstOfMultiFactorAuthenticationProvider class accepting this type of token.
 *
 * This is based on the FirstOfMultiFactorAuthenticationToken code made by
 * ynojima and used in their project at
 * https://github.com/ynojima/spring-security-webauthn
 *
 * The author made a pull request to spring-security where this class was part
 * of the implementation, but as far as I can tell it was rejected because of
 * a few pull request policies it didn't fully meet. However, it's possible that
 * something similar to this will be present in a new version of Spring Security
 * in the foreseeable future.
 *
 * See https://github.com/spring-projects/spring-security/pull/5196 for the
 * relevant pull request.
 *
 * Original file:
 * https://github.com/ynojima/spring-security/blob/c1012e92a514f60efbef87b90cee73d0a180a41c/core/src/main/java/org/springframework/security/authentication/FirstOfMultiFactorAuthenticationToken.java
 */
public class FirstOfMultiFactorAuthenticationToken extends AbstractAuthenticationToken {
    
    private Object principal;
    private Object credentials;

    public FirstOfMultiFactorAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
