package com.tecacet.plaid;

import com.plaid.client.model.*;

import com.plaid.client.request.PlaidApi;

import retrofit2.Response;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PlaidService {

    private final PlaidApi plaidApi;
    private final PlaidTokenService plaidTokenService;

    public PlaidService(PlaidApi plaidApi, PlaidTokenService plaidTokenService) {
        this.plaidApi = plaidApi;
        this.plaidTokenService = plaidTokenService;
    }


    public List<AccountBase> getAccounts(String institutionId) throws IOException {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest();
        request.setAccessToken(accessToken);
        Response<AccountsGetResponse> response = plaidApi.accountsBalanceGet(request).execute();
        return response.body().getAccounts();
    }

    public TransactionsGetResponse getTransactions(String institutionId, LocalDate fromDate) throws IOException {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        TransactionsGetRequest request = new TransactionsGetRequest();
        request.setAccessToken(accessToken);
        request.setStartDate(fromDate);
        request.setEndDate(LocalDate.now());
        return plaidApi.transactionsGet(request).execute().body();
    }

    public List<Category> getAllCategories() throws IOException {
        return plaidApi.categoriesGet(new Object()).execute().body().getCategories();
    }

}
