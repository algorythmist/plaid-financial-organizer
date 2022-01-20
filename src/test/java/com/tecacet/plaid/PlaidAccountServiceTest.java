package com.tecacet.plaid;


import com.plaid.client.model.AccountBase;
import com.plaid.client.request.PlaidApi;
import org.junit.jupiter.api.Test;

import java.util.List;

class PlaidAccountServiceTest {

    @Test
    void getAccounts() {
        PlaidApi plaidApiService =
                PlaidServiceFactory.buildPlaidApiService(new EnvironmentSecretRegistry());
        PlaidTokenService plaidTokenService = new PlaidTokenService(plaidApiService);
        PlaidAccountService accountService = new PlaidAccountService(plaidApiService, plaidTokenService);
        List<AccountBase> accounts = accountService.getAccounts("ins_109509");
        System.out.println(accounts);
    }
}