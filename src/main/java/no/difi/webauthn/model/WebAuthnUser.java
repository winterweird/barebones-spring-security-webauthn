package no.difi.webauthn.model;

import org.springframework.security.core.userdetails.User;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WebAuthnUser extends User {
    /**
     * The credential id of the authenticator.
     */
    private byte[] credentialId;
    
    /**
     * Custom WebAuthnUserBuilder that allows for the specification of
     * WebAuthnUser specific fields.
     */
    public static class WebAuthnUserBuilder {
        private PasswordEncoder passwordEncoder;
        private byte[] credentialId;
        private User.UserBuilder ub = User.builder();

        // private to restrict direct access
        private WebAuthnUserBuilder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }
        
        /**
         * The credentialId of the authenticator should be provided.
         *
         * @param credentialId the credential id of the authenticator
         * @return this object
         */
        public WebAuthnUserBuilder credentialId(byte[] credentialId) {
            this.credentialId = Arrays.copyOf(credentialId, credentialId.length);
            return this;
        }

        public WebAuthnUserBuilder username(String username) {
            ub.username(username);
            return this;
        }

        public WebAuthnUserBuilder password(String password) {
            ub.password(password);
            return this;
        }

        public WebAuthnUserBuilder roles(String... roles) {
            ub.roles(roles);
            return this;
        }

        public WebAuthnUser build() {
            User u = (User)ub.build();
            return new WebAuthnUser(
                    u.getUsername(),
                    passwordEncoder.encode(u.getPassword()),
                    u.isEnabled(),
                    u.isAccountNonExpired(),
                    u.isCredentialsNonExpired(),
                    u.isAccountNonLocked(),
                    u.getAuthorities(),
                    credentialId);
        }
    }
    
    public static WebAuthnUserBuilder webAuthnUserBuilder() {
        return new WebAuthnUserBuilder(new BCryptPasswordEncoder());
    }
 
    /**
     * Full constructor.
     *
     * All required and non-required fields are present. This is the constructor
     * used by the builder.
     */
    public WebAuthnUser(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities,
            byte[] credentialId) {
        
        super(username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
        
        this.credentialId = credentialId;
    }

    /**
     * Partial constructor.
     *
     * Only required fields are present.
     */
    public WebAuthnUser(String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            byte[] credentialId) {
        
        super(username, password, authorities);

        this.credentialId = credentialId;
    }
}
