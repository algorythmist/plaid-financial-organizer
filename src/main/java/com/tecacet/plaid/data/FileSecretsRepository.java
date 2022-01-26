package com.tecacet.plaid.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileSecretsRepository implements SecretsRepository {

    private final String clientId;
    private final String clientSecret;

    public FileSecretsRepository(String filename) throws IOException {
        Properties properties = new Properties();
        try (FileReader fr = new FileReader(filename)) {
            properties.load(fr);
        }
        this.clientId = properties.getProperty(PLAID_CLIENT_ID);
        this.clientSecret = properties.getProperty(PLAID_SECRET);
    }

    @Override
    public String clientId() {
        return clientId;
    }

    @Override
    public String clientSecret() {
        return clientSecret;
    }
}
