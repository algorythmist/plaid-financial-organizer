package com.tecacet.plaid;

import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.model.Products;
import com.plaid.client.model.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.PlaidApi;
import com.tecacet.plaid.data.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlaidTokenService extends AbstractPlaidService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TokenRepository tokenRepository;

    public PlaidTokenService(PlaidApi plaidApi, TokenRepository tokenRepository) {
        super(plaidApi);
        this.tokenRepository = tokenRepository;
    }

    public String createSandboxPublicToken(String institutionId) {
        SandboxPublicTokenCreateRequest createRequest = new SandboxPublicTokenCreateRequest();
        createRequest.setInstitutionId(institutionId);
        createRequest.setInitialProducts(List.of(Products.TRANSACTIONS));
        return invoke(plaidApi.sandboxPublicTokenCreate(createRequest)).getPublicToken();
    }

    public String exchangeToken(String publicToken) {
        ItemPublicTokenExchangeRequest exchangeRequest = new ItemPublicTokenExchangeRequest();
        exchangeRequest.setPublicToken(publicToken);
        ItemPublicTokenExchangeResponse exchangeResponse =
                invoke(plaidApi.itemPublicTokenExchange(exchangeRequest));
        return exchangeResponse.getAccessToken();
    }

    public String getPublicToken(String institutionId) {
        String accessToken = tokenRepository.getToken(institutionId);
        if (accessToken == null) {
            logger.info("Token not stored. Obtaining a token from sandbox");
            String publicToken = createSandboxPublicToken(institutionId);
            accessToken = exchangeToken(publicToken);
            tokenRepository.storeToken(publicToken, accessToken);
        }
        return accessToken;
    }
}
