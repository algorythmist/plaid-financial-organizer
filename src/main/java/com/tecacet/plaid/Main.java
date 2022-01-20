package com.tecacet.plaid;


import com.plaid.client.model.AccountBase;
import com.plaid.client.model.Category;
import com.plaid.client.model.Institution;
import com.plaid.client.model.TransactionsGetResponse;
import com.plaid.client.request.PlaidApi;
import com.tecacet.plaid.export.CategoryExporter;
import com.tecacet.plaid.export.TransactionExporter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Main {

    private static final PlaidApi plaidApiService =
            PlaidServiceFactory.buildPlaidApiService(new EnvironmentSecretRegistry());
    private static final PlaidTokenService plaidTokenService = new PlaidTokenService(plaidApiService);
    private static final PlaidService plaidService = new PlaidService(plaidApiService, plaidTokenService);
    private static final PlaidInstitutionService institutionService = new PlaidInstitutionService(plaidApiService);

    private static final TransactionExporter transactionExporter = new TransactionExporter();
    private static final CategoryExporter categoryExporter = new CategoryExporter();

    public static void main(String[] args) throws IOException {

        Institution institution = institutionService.getInstitution("ins_109509");
        System.out.println(institution.getName());
        String institutionId = institution.getInstitutionId();

        LocalDate date = LocalDate.of(2019, Calendar.APRIL, 1);
        TransactionsGetResponse transactionsGetResponse = plaidService.getTransactions(institutionId, date);

        List<AccountBase> institutionAccounts = plaidService.getAccounts(institutionId);

        transactionExporter.exportTransactions(transactionsGetResponse);

        List<Category> categories = plaidService.getAllCategories();
        categoryExporter.exportCategories(categories, "categories.csv");
    }
}
