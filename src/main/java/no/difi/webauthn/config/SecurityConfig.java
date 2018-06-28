package no.difi.webauthn.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static no.difi.webauthn.authentication.filters.WebAuthnProcessingFilterConfigurer.webAuthnLogin;
import no.difi.webauthn.authentication.providers.WebAuthnFirstOfMultiFactorAuthenticationProvider;

@Configuration
@Import(value = SecurityConfigBeans.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        WebAuthnFirstOfMultiFactorAuthenticationProvider provider =
            new WebAuthnFirstOfMultiFactorAuthenticationProvider();
        provider.setAuthenticationProvider(daoAuthenticationProvider);
        builder.authenticationProvider(provider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .antMatchers("/login", "/register").permitAll()
            .antMatchers("/registerUser").permitAll()
            .antMatchers("/authenticate").hasAuthority("WEBAUTHN_AUTHENTICABLE")
            .antMatchers("/user").hasRole("USER")
            .antMatchers("/js/**").permitAll()
            .anyRequest().authenticated();
        http.apply(webAuthnLogin())
            .loginPage("/login")
            .successForwardUrl("/authenticate");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
