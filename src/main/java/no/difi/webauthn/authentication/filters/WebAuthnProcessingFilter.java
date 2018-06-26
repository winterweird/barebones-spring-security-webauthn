package no.difi.webauthn.authentication.filters;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import no.difi.webauthn.exception.*;
import no.difi.webauthn.authentication.tokens.FirstOfMultiFactorAuthenticationToken;
import no.difi.webauthn.authentication.providers.WebAuthnFirstOfMultiFactorAuthenticationProvider;

/**
 * This is the class that coordinates the application of the WebAuthn login
 * method.
 *
 * As far as Spring knows, this is the actual authentication filter that gets
 * run, and it decides based on the provided credentials whether to authenticate
 * using the password or using the WebAuthn protocol.
 *
 * This filter is configured using the WebAuthnProcessingFilterConfigurer class.
 */
public class WebAuthnProcessingFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * Authorities of the user at the time of password login.
     */
    private List<GrantedAuthority> authorities;
    
    /**
     * Default constructor.
     *
     * Construct the processing filter with default values.
     */
    public WebAuthnProcessingFilter() {
        this(AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    }
    
    /**
     * Parametrized constructor.
     *
     * @param authorities the authorities of the user at the time of password
     * login
     */
    public WebAuthnProcessingFilter(List<GrantedAuthority> authorities) {
        super();
        Assert.notNull(authorities, "authorities must be set");
        this.authorities = authorities;
    }

    /**
     * Attempt to authenticate by deciding whether to accept the
     * username/password authentication method or to use the WebAuthn
     * authentication protocol.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // TODO: postOnly flag check (ignored for simplicity)

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        
        // NOTE: in a production environment, DON'T do this
        System.out.println("USERNAME: " + username);
        System.out.println("PASSWORD: " + password);

        // TODO: retrieve the params from the request (inline)
        // TODO: create constants for the names of the fields of the request
        // parameters
        String credentialId = null; // this must be a variable

        AbstractAuthenticationToken authRequest;
        if (StringUtils.isEmpty(credentialId)) {
            authRequest = new FirstOfMultiFactorAuthenticationToken(username, password, authorities);
        }
        else {
            throw new NotImplementedException("Not implemented: attemptAuthentication");
        }

        // Allow subclasses to set the "details" property
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        //                     ^ probably a protected field in some superclass
        
        return getAuthenticationManager().authenticate(authRequest);
    }
}
