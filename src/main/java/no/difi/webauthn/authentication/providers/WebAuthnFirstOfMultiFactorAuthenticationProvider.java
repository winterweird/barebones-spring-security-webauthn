package no.difi.webauthn.authentication.providers;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import no.difi.webauthn.exception.*;
import no.difi.webauthn.authentication.tokens.FirstOfMultiFactorAuthenticationToken;

/**
 * Authentication provider that authenticates based on a username and password
 * provided by the FirstOfMultiFactorAuthenticationToken authentication request.
 *
 * The <code>authenticate()</code> method delegates authentication
 * responsibility to an AbstractUserDetailsAuthenticationProvider, but is
 * possible to configure to just accept this result, or to wrap it in a
 * FirstOfMultiFactorAuthenticationToken instead.
 * (I'm not entirely sure what it achieves by doing that yet.)
 */
public class WebAuthnFirstOfMultiFactorAuthenticationProvider implements AuthenticationProvider {
    /**
     * The authentication provider which actually performs the authentication of
     * the username and password.
     *
     * This can be provided by Spring Security.
     */
    private AbstractUserDetailsAuthenticationProvider authenticationProvider;
    
    // Authenticate by delegating to standard username/password authentication
    // by another provider
    @Override
    public Authentication authenticate(Authentication authentication) {
        if (!supports(authentication.getClass())) {
            throw new IllegalArgumentException("Only supports " +
                    "FirstOfMultiFactorAuthenticationToken, but got " + 
                    authentication.getClass().getName());
        }

        FirstOfMultiFactorAuthenticationToken token = (FirstOfMultiFactorAuthenticationToken)authentication;
        UsernamePasswordAuthenticationToken upToken = 
            new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials());
        Authentication result = authenticationProvider.authenticate(upToken);
        
        // TODO allow for legacy password authentication without webauthn as an
        // optional (flag-determined) operation

        return new FirstOfMultiFactorAuthenticationToken(
                result.getPrincipal(),
                result.getCredentials(),
                authentication.getAuthorities());
    }

    // Methods like these should be easy to auto-generate, as they are
    // generally in what they do.
    @Override
    public boolean supports(Class<?> authentication) {
        return FirstOfMultiFactorAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Setter for the authentication provider that actually provides
     * authentication.
     *
     * @param provider the provider to use to authenticate the username/password
     * authentication details
     */
    public void setAuthenticationProvider(AbstractUserDetailsAuthenticationProvider provider) {
        this.authenticationProvider = provider;
    }
}
