package com.tecacet.plaid.data;

/**
 * A method for obtaining Plaid credentials
 */
public interface SecretsRepository {

    String PLAID_CLIENT_ID = "PLAID_CLIENT_ID";
    String PLAID_SECRET = "PLAID_SECRET";

    String clientId();

    String clientSecret();
}
