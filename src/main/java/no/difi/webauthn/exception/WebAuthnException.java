package no.difi.webauthn.exception;

/**
 * Base exception class used to categorize exceptions the WebAuthn server might
 * throw.
 */
public class WebAuthnException extends RuntimeException {
    public WebAuthnException() {super();}
    public WebAuthnException(String msg) {super(msg);}
}
