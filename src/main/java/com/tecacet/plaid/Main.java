package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.response.*;
import com.tecacet.plaid.export.CategoryExporter;
import com.tecacet.plaid.export.TransactionExporter;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

    private static PlaidApiService plaidApiService = PlaidServiceFactory.buildPlaidApiService();
    private static PlaidTokenService plaidTokenService = new PlaidTokenService(plaidApiService);
    private static PlaidService plaidService = new PlaidService(plaidApiService, plaidTokenService);

    private static TransactionExporter transactionExporter = new TransactionExporter();
    private static CategoryExporter categoryExporter = new CategoryExporter();

    public static void main(String[] args) throws IOException {

        Institution institution = plaidService.getInstitution("ins_109509");
        System.out.println(institution);
        String institutionId = institution.getInstitutionId();

        Calendar calendar = new GregorianCalendar(2019, Calendar.APRIL, 1);
        TransactionsGetResponse transactionsGetResponse = plaidService.getTransactions(institutionId, calendar.getTime());

        List<Account> institutionAccounts = plaidService.getAccounts(institutionId);
        // System.out.println(institutionAccounts);

        transactionExporter.exportTransactions(transactionsGetResponse);

        List<CategoriesGetResponse.Category> categories = plaidService.getAllCategories();
        categoryExporter.exportCategories(categories, "categories.csv");
    }
}
