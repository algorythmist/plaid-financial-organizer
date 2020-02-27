package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.common.Product;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlaidTokenService {

    private Map<String, String> tokenDatabase = new HashMap<>();
    private final PlaidApiService plaidApiService;

    public PlaidTokenService(PlaidApiService plaidApiService) {
        this.plaidApiService = plaidApiService;
    }

    public String createSandboxPublicToken(String institutionId) throws IOException {
        return plaidApiService.sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest(institutionId,
                Arrays.asList(Product.TRANSACTIONS))).execute().body().getPublicToken();
    }

    public String exchangeToken(String publicToken) throws IOException {
        ItemPublicTokenExchangeRequest exchangeRequest = new ItemPublicTokenExchangeRequest(publicToken);
        ItemPublicTokenExchangeResponse exchangeResponse = plaidApiService.itemPublicTokenExchange(exchangeRequest).execute().body();
        return exchangeResponse.getAccessToken();
    }

    public String getPublicToken(String institutionId) throws IOException {
        String accessToken = tokenDatabase.get(institutionId);
        if (accessToken == null) {
            String publicToken = createSandboxPublicToken(institutionId);
            accessToken = exchangeToken(publicToken);
            tokenDatabase.put(publicToken, accessToken);
        }
        return accessToken;
    }
}
