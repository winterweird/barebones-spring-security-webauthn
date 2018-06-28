package no.difi.webauthn.authentication.filters;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;


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

    /**
     * Set the success handler for this filter.
     *
     * @param forwardUrl the target URL in case of success
     * @return this configurer
     */
    public WebAuthnProcessingFilterConfigurer<H> successForwardUrl(String forwardUrl) {
        successHandler(new ForwardAuthenticationSuccessHandler(forwardUrl));
        return this;
    }
}
