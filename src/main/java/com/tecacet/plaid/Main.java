package com.tecacet.plaid;

import com.plaid.client.response.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

    private static PlaidService plaidService = new PlaidService();
    private static TransactionExporter transactionExporter = new TransactionExporter();

    public static void main(String[] args) throws IOException {

        Response<InstitutionsGetByIdResponse> institutionResponse =
                plaidService.getInstitution("ins_3");
        Institution institution = institutionResponse.body().getInstitution();
        SandboxPublicTokenCreateResponse tokenCreateResponse =
                plaidService.createSandboxPublicToken(institution.getInstitutionId()).body();

        String publicToken = tokenCreateResponse.getPublicToken();

        String accessToken = plaidService.exchangeToken(publicToken);

        Calendar calendar = new GregorianCalendar(2019, Calendar.APRIL, 1);
        Response<TransactionsGetResponse> response = plaidService.getTransactions(accessToken, calendar.getTime());
        TransactionsGetResponse transactionsGetResponse = response.body();
        List<Account> accounts = transactionsGetResponse.getAccounts();
        List<TransactionsGetResponse.Transaction> transactions = transactionsGetResponse.getTransactions();
        transactionExporter.exportTransactions(transactionsGetResponse, "txn.csv");
    }
}