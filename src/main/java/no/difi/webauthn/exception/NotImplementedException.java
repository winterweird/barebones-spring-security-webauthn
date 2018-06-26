package no.difi.webauthn.exception;

/**
 * Exception indicating that a piece of functionality has not yet been
 * implemented.
 */
public final class NotImplementedException extends WebAuthnException {
    public NotImplementedException() {super();}
    public NotImplementedException(String msg) {super(msg);}
}
