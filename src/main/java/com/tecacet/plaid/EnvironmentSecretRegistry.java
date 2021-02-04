package com.tecacet.plaid;

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
