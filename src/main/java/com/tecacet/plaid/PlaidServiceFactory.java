package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.PlaidClient;

//TODO: singleton
public class PlaidServiceFactory {

    private PlaidServiceFactory() {

    }

    public static PlaidApiService buildPlaidApiService(SecretRegistry secretRegistry) {
        String clientId = secretRegistry.clientId();
        String clientSecret = secretRegistry.clientSecret();
        if (clientId == null || clientSecret == null) {
            throw new PlaidException("Failed to obtain credentials from registry.");
        }

        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret(clientId, clientSecret)
                .sandboxBaseUrl()
                .build();
        return plaidClient.service();
    }
}
