package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.request.common.Product;
import com.plaid.client.response.InstitutionsGetByIdResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.SandboxPublicTokenCreateResponse;
import com.plaid.client.response.TransactionsGetResponse;
import okhttp3.ConnectionPool;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PlaidService {

    private int poolSize = 1;
    private int connectionIdle = 1;
    private String plaidUrl = "https://sandbox.plaid.com";

    private final PlaidApiService plaidApiService;

    public PlaidService() {

        String clientId = System.getenv("PLAID_CLIENT_ID");
        String clientSecret = System.getenv("PLAID_CLIENT_SECRET");
        String publicKey = System.getenv("PLAID_PUBLIC_KEY");

        PlaidClient.Builder builder = PlaidClient.newBuilder();
        builder.okHttpClientBuilder()
                .connectionPool(new ConnectionPool(
                        poolSize,
                        connectionIdle, TimeUnit.MILLISECONDS));
        PlaidClient plaidClient = builder
                .clientIdAndSecret(clientId, clientSecret)
                .publicKey(publicKey)
                .baseUrl(plaidUrl)
                .build();
        plaidApiService = plaidClient.service();
    }

    public Response<InstitutionsGetByIdResponse> getInstitution(String institutionId) throws IOException {
        return plaidApiService.institutionsGetById(new InstitutionsGetByIdRequest(institutionId)).execute();
    }

    public Response<SandboxPublicTokenCreateResponse> createSandboxPublicToken(String institutionId) throws IOException {
        return plaidApiService.sandboxPublicTokenCreate(new SandboxPublicTokenCreateRequest(institutionId,
                Arrays.asList(Product.TRANSACTIONS))).execute();
    }

    public String exchangeToken(String publicToken) throws IOException {
        ItemPublicTokenExchangeRequest exchangeRequest = new ItemPublicTokenExchangeRequest(publicToken);
        ItemPublicTokenExchangeResponse exchangeResponse = plaidApiService.itemPublicTokenExchange(exchangeRequest).execute().body();
        return exchangeResponse.getAccessToken();
    }

    public Response<TransactionsGetResponse> getTransactions(String accessToken, Date fromDate) throws IOException {
        TransactionsGetRequest request = new TransactionsGetRequest(accessToken, fromDate, new Date());
        return plaidApiService.transactionsGet(request).execute();
    }

}
