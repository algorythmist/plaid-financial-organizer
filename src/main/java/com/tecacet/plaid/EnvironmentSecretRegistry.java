package com.tecacet.plaid;

/**
 * Retrieve Plaid credentials from environment variables.
 * PLAID_CLIENT_ID: client_id
 * PLAID_SECRET: secret
 */
public class EnvironmentSecretRegistry implements SecretRegistry {

    @Override
    public String clientId() {
        return System.getenv("PLAID_CLIENT_ID");
    }

    @Override
    public String clientSecret() {
        return System.getenv("PLAID_SECRET");
    }

}
