package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.PlaidClient;

import okhttp3.ConnectionPool;

import java.util.concurrent.TimeUnit;

public class PlaidServiceFactory {

    private static int poolSize = 1;
    private static int connectionIdle = 1;
    private static String plaidUrl = "https://sandbox.plaid.com";

    public static PlaidApiService buildPlaidApiService() {
        String clientId = System.getenv("PLAID_CLIENT_ID");
        String clientSecret = System.getenv("PLAID_SECRET");
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
        return plaidClient.service();
    }
}
