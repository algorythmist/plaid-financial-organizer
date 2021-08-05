package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.request.AccountsBalanceGetRequest;
import com.plaid.client.response.Account;

import java.util.List;

public class PlaidAccountService extends AbstractPlaidService {

    private final PlaidTokenService plaidTokenService;

    public PlaidAccountService(PlaidApiService plaidApiService, PlaidTokenService plaidTokenService) {
        super(plaidApiService);
        this.plaidTokenService = plaidTokenService;
    }

    public List<Account> getAccounts(String institutionId) {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest(accessToken);
        return invoke(plaidApiService.accountsBalanceGet(request)).getAccounts();
    }

}
