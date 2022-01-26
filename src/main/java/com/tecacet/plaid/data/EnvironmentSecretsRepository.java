package com.tecacet.plaid.data;

import com.tecacet.plaid.data.SecretsRepository;

/**
 * Retrieve Plaid credentials from environment variables.
 * PLAID_CLIENT_ID: client_id
 * PLAID_SECRET: secret
 */
public class EnvironmentSecretsRepository implements SecretsRepository {

    @Override
    public String clientId() {
        return System.getenv(PLAID_CLIENT_ID);
    }

    @Override
    public String clientSecret() {
        return System.getenv(PLAID_SECRET);
    }

}
