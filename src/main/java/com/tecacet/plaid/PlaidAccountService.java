package com.tecacet.plaid;


import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsGetRequest;
import com.plaid.client.model.AccountsGetRequestOptions;
import com.plaid.client.request.PlaidApi;

import java.util.List;

public class PlaidAccountService extends AbstractPlaidService {

    private final PlaidTokenService plaidTokenService;

    public PlaidAccountService(PlaidApi plaidApi, PlaidTokenService plaidTokenService) {
        super(plaidApi);
        this.plaidTokenService = plaidTokenService;
    }

    public List<AccountBase> getAccounts(String institutionId, List<String> optionalAccountIds) {
        String accessToken = plaidTokenService.getPublicToken(institutionId);
        AccountsGetRequest request = new AccountsGetRequest();
        request.setAccessToken(accessToken);
        if (!optionalAccountIds.isEmpty()) {
            AccountsGetRequestOptions options = new AccountsGetRequestOptions();
            options.accountIds(optionalAccountIds);
            request.setOptions(options);
        }
        return invoke(plaidApi.accountsGet(request)).getAccounts();
    }

}
