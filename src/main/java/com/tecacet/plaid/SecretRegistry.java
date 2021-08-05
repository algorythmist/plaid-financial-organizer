package com.tecacet.plaid;

/**
 * A method for obtaining Plaid credentials
 */
public interface SecretRegistry {

    String clientId();

    String clientSecret();
}
