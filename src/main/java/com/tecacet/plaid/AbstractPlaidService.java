package com.tecacet.plaid;


import com.google.gson.Gson;
import com.plaid.client.model.Error;
import com.plaid.client.request.PlaidApi;
import com.tecacet.plaid.data.EnvironmentSecretsRepository;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public abstract class AbstractPlaidService {

    protected final PlaidApi plaidApi;

    protected AbstractPlaidService() {
        this(PlaidServiceFactory.buildPlaidApiService(new EnvironmentSecretsRepository()));
    }

    protected AbstractPlaidService(PlaidApi plaidApi) {
        this.plaidApi = plaidApi;
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

    protected Error extractError(Response response) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(response.errorBody().string(), Error.class);
    }
}
