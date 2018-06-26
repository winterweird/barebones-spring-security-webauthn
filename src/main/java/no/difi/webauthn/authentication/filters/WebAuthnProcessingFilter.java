package no.difi.webauthn.authentication.filters;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;

import java.util.List;

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
}
