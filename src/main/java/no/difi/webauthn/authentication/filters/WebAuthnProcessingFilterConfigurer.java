package no.difi.webauthn.authentication.filters;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class WebAuthnProcessingFilterConfigurer<H extends HttpSecurityBuilder<H>>
    extends AbstractAuthenticationFilterConfigurer
            <H,
             WebAuthnProcessingFilterConfigurer<H>,
             WebAuthnProcessingFilter> {

    /**
     * Default constructor.
     *
     * Initializes super with a new WebAuthnProcessingFilter.
     */
    public WebAuthnProcessingFilterConfigurer() {
        super(new WebAuthnProcessingFilter(), null);
    }
    
    /**
     * Return the default WebAuthnLoginConfigurer object.
     */
    public static WebAuthnProcessingFilterConfigurer webAuthnLogin() {
        return new WebAuthnProcessingFilterConfigurer();
    }

    /**
     * Wrapper around super.loginPage to allow public access.
     *
     * {@inheritDoc}
     */
    @Override
    public WebAuthnProcessingFilterConfigurer<H> loginPage(String loginPage) {
        return super.loginPage(loginPage);
    }
    
    // required implementation
    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }
}
