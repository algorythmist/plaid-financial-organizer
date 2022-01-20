package com.tecacet.plaid;


import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.model.Products;
import com.plaid.client.model.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.PlaidApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaidTokenService extends AbstractPlaidService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //TODO: interface
    private final Map<String, String> tokenDatabase = new HashMap<>();

    public PlaidTokenService(PlaidApi plaidApiService) {
        super(plaidApiService);
    }

    public String createSandboxPublicToken(String institutionId) {
        SandboxPublicTokenCreateRequest createRequest = new SandboxPublicTokenCreateRequest();
        createRequest.setInstitutionId(institutionId);
        createRequest.setInitialProducts(List.of(Products.TRANSACTIONS));
        return invoke(plaidApiService.sandboxPublicTokenCreate(createRequest)).getPublicToken();
    }

    public String exchangeToken(String publicToken) {
        ItemPublicTokenExchangeRequest exchangeRequest = new ItemPublicTokenExchangeRequest();
        exchangeRequest.setPublicToken(publicToken);
        ItemPublicTokenExchangeResponse exchangeResponse =
                invoke(plaidApiService.itemPublicTokenExchange(exchangeRequest));
        try {
            logger.info("Waiting for access token...");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exchangeResponse.getAccessToken();
    }

    public String getPublicToken(String institutionId) {
        String accessToken = tokenDatabase.get(institutionId);
        if (accessToken == null) {
            logger.info("Token not stored. Obtaining a token from sandbox");
            String publicToken = createSandboxPublicToken(institutionId);
            accessToken = exchangeToken(publicToken);
            tokenDatabase.put(publicToken, accessToken);
        }
        return accessToken;
    }
}
