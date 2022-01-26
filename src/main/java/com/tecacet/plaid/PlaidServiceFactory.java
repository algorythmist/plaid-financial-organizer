package com.tecacet.plaid;

import com.plaid.client.ApiClient;
import com.plaid.client.request.PlaidApi;
import com.tecacet.plaid.data.SecretsRepository;

import java.util.HashMap;

//TODO: singleton
public class PlaidServiceFactory {

    private PlaidServiceFactory() {

    }

    public static PlaidApi buildPlaidApiService(SecretsRepository secretRegistry) {
        String clientId = secretRegistry.clientId();
        String clientSecret = secretRegistry.clientSecret();
        if (clientId == null || clientSecret == null) {
            throw new PlaidException("Failed to obtain credentials from registry.");
        }

        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", clientId);
        apiKeys.put("secret", clientSecret);
        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Sandbox);
        return apiClient.createService(PlaidApi.class);
    }
}
