package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.response.Account;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaidAccountServiceTest {

    @Test
    void getAccounts() {
        PlaidApiService plaidApiService =
                PlaidServiceFactory.buildPlaidApiService(new EnvironmentSecretRegistry());
        PlaidTokenService plaidTokenService = new PlaidTokenService(plaidApiService);
        PlaidAccountService accountService = new PlaidAccountService(plaidApiService, plaidTokenService);
        List<Account> accounts = accountService.getAccounts("ins_109509");
        System.out.println(accounts);
    }
}