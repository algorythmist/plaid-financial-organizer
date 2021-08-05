package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public abstract class AbstractPlaidService {

    protected final PlaidApiService plaidApiService;

    protected AbstractPlaidService() {
        this(PlaidServiceFactory.buildPlaidApiService(new EnvironmentSecretRegistry()));
    }

    protected AbstractPlaidService(PlaidApiService plaidApiService) {
        this.plaidApiService = plaidApiService;
    }

    protected <T> T invoke(Call<T> call) {
        Response<T> response = execute(call);
        return extractBody(response);
    }

    protected <T> Response<T> execute(Call<T> call) {
        try {
            return call.execute();
        } catch (IOException e) {
            throw new PlaidException(e);
        }
    }

    protected  <T> T extractBody(Response<T> response) {
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new PlaidException(response.raw().toString());
    }
}
