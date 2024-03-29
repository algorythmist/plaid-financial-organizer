package com.tecacet.plaid.export;


import com.plaid.client.model.AccountBase;
import com.plaid.client.model.TransactionsGetResponse;
import com.tecacet.jflat.CSVWriter;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionExporter {

    private static final String[] HEADER = {"Date", "Amount",  "Description", "Categories", "Type", "Account"};
    private static final String[] PROPERTIES = {"date", "amount",  "name", "category", "transactionType", "accountId"};

    public void exportTransactions(TransactionsGetResponse transactionsGetResponse) throws IOException {
        Map<String, AccountBase> accounts = transactionsGetResponse.getAccounts().stream()
                .collect(Collectors.toMap(AccountBase::getAccountId, Function.identity()));
        String filename = transactionsGetResponse.getItem().getInstitutionId() +".csv";
        CSVWriter writer = CSVWriter.writerForProperties( PROPERTIES ).withFormat(CSVFormat.RFC4180.withQuote('"'))
            .withHeader( HEADER );
        writer.registerConverterForProperty( "accountId", accountId -> {
            AccountBase account = accounts.get( accountId );
            return account.getName();
        } );
        writer.writeToFile(filename, transactionsGetResponse.getTransactions());
    }
}
