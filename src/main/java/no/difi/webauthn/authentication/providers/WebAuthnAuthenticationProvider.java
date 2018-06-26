package no.difi.webauthn.authentication.providers;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationProvider;

import no.difi.webauthn.exception.*;

/**
 * This is the backbone class of WebAuthn authentication.
 *
 * This class implements the <code>authenticate()</code> method, which is used
 * by the authentication manager authenticate a user based on the WebAuthn
 * authentication protocol.
 */
public class WebAuthnAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) {
        // TODO: implement
        throw new NotImplementedException("Not yet implemented: authenticate");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO: implement
        throw new NotImplementedException("Not yet implemented: supports");
    }
}
