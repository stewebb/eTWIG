package net.etwig.webapp.handler;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * A custom implementation of {@link UsernamePasswordAuthenticationToken} used to signify that
 * an authentication request should bypass password verification.
 *
 * <p>This token should be used carefully and only in contexts where it's safe to authenticate a user
 * without a password, such as in controlled internal environments or during specific demo scenarios.</p>
 */

public class CustomToken extends UsernamePasswordAuthenticationToken {
    /**
     * Constructs a new {@link CustomToken} with the specified principal and credentials.
     *
     * @param principal the principal (typically the username) representing the user.
     * @param credentials the credentials (which can be null or any object representing the security credentials).
     */

    public CustomToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    // Additional methods or properties can be implemented here if necessary
}