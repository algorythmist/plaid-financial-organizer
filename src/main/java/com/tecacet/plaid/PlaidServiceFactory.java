package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.PlaidClient;


public class PlaidServiceFactory {

    public static PlaidApiService buildPlaidApiService(SecretRegistry secretRegistry) {
        String clientId = secretRegistry.clientId();
        String clientSecret = secretRegistry.clientSecret();

        PlaidClient plaidClient = PlaidClient.newBuilder()
                .clientIdAndSecret(clientId, clientSecret)
                .sandboxBaseUrl()
                .build();
        return plaidClient.service();
    }
}
