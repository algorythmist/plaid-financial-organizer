package com.tecacet.plaid;


import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.request.PlaidApi;

import java.util.List;

public class PlaidAccountService extends AbstractPlaidService {

    private final PlaidTokenService plaidTokenService;

    public PlaidAccountService(PlaidApi plaidApiService, PlaidTokenService plaidTokenService) {
        super(plaidApiService);
        this.plaidTokenService = plaidTokenService;
    }

    public List<AccountBase> getAccounts(String institutionId) {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest();
        request.setAccessToken(accessToken);
        return invoke(plaidApiService.accountsBalanceGet(request)).getAccounts();
    }

}
