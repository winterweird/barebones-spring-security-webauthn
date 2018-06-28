package no.difi.webauthn.util;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.Base64;

/**
 * Utility class for UUIDs.
 */
public final class UUIDUtil {
    // prevent instantiation
    private UUIDUtil() {}
    
    /**
     * Create a byte array based on the UUID.
     */
    public static byte[] toByteArray(UUID uuid) {
        long hi = uuid.getMostSignificantBits();
        long lo = uuid.getLeastSignificantBits();
        return ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
    }

    public static String base64UrlString(UUID uuid) {
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(toByteArray(uuid));
    }
}
