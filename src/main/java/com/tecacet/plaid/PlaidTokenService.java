package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.common.Product;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PlaidTokenService extends AbstractPlaidService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //TODO: interface
    private final Map<String, String> tokenDatabase = new HashMap<>();

    public PlaidTokenService(PlaidApiService plaidApiService) {
        super(plaidApiService);
    }

    public String createSandboxPublicToken(String institutionId) {
        return invoke(plaidApiService.sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest(institutionId,
                Arrays.asList(Product.TRANSACTIONS)))).getPublicToken();
    }

    public String exchangeToken(String publicToken) {
        ItemPublicTokenExchangeRequest exchangeRequest = new ItemPublicTokenExchangeRequest(publicToken);
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
