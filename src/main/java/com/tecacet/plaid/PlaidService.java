package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.request.AccountsBalanceGetRequest;
import com.plaid.client.request.CategoriesGetRequest;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.InstitutionsSearchRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsBalanceGetResponse;
import com.plaid.client.response.CategoriesGetResponse;
import com.plaid.client.response.Institution;
import com.plaid.client.response.InstitutionsGetByIdResponse;
import com.plaid.client.response.InstitutionsSearchResponse;
import com.plaid.client.response.TransactionsGetResponse;

import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PlaidService {

    private final PlaidApiService plaidApiService;
    private final PlaidTokenService plaidTokenService;

    public PlaidService(PlaidApiService plaidApiService, PlaidTokenService plaidTokenService) {
        this.plaidApiService = plaidApiService;
        this.plaidTokenService = plaidTokenService;
    }

    //    First Platypus Bank 	ins_109508
    //    First Gingham Credit Union 	ins_109509
    //    Tattersall Federal Credit Union 	ins_109510
    //    Tartan Bank 	ins_109511
    //    Houndstooth Bank 	ins_109512
    //    Tartan-Dominion Bank of Canada 	ins_43
    public Institution getInstitution(String institutionId) throws IOException {
        InstitutionsGetByIdRequest req = new InstitutionsGetByIdRequest(institutionId,
                Arrays.asList("USA"));
        Response<InstitutionsGetByIdResponse> response = plaidApiService.institutionsGetById(req)
                .execute();

        return response.body().getInstitution();
    }

    public List<Account> getAccounts(String institutionId) throws IOException {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest(accessToken);
        Response<AccountsBalanceGetResponse> response = plaidApiService.accountsBalanceGet(request).execute();
        return response.body().getAccounts();
    }

    public TransactionsGetResponse getTransactions(String institutionId, Date fromDate) throws IOException {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        TransactionsGetRequest request = new TransactionsGetRequest(accessToken, fromDate, new Date());
        return plaidApiService.transactionsGet(request).execute().body();
    }

    public List<CategoriesGetResponse.Category> getAllCategories() throws IOException {
        return plaidApiService.categoriesGet(new CategoriesGetRequest()).execute().body().getCategories();
    }

}
