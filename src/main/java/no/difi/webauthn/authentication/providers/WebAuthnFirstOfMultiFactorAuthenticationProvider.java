package no.difi.webauthn.authentication.providers;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationProvider;

import no.difi.webauthn.exception.*;

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
    
    @Override
    public Authentication authenticate(Authentication authentication) {
        if (!supports(authentication.getClass())) {
            throw new IllegalArgumentException("Only supports " +
                    "FirstOfMultiFactorAuthenticationToken, but got " + 
                    authentication.getClass().getName());
        }
        // TODO: implement
        throw new NotImplementedException("Not yet implemented: authenticate");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO: implement
        throw new NotImplementedException("Not yet implemented: supports");
    }
}
