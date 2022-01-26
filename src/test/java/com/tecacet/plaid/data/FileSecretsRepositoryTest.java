package com.tecacet.plaid.data;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileSecretsRepositoryTest {

    @Test
    void test() throws IOException {
        String filename = "secrets.properties";
        Properties properties = new Properties();
        properties.setProperty(SecretsRepository.PLAID_CLIENT_ID, "clientId");
        properties.setProperty(SecretsRepository.PLAID_SECRET, "secret");
        File file = new File(filename);
        try (FileWriter fw = new FileWriter(file)) {
            properties.store(fw, "");
        }

        SecretsRepository secretsRepository = new FileSecretsRepository(filename);
        assertEquals("clientId", secretsRepository.clientId());
        assertEquals("secret", secretsRepository.clientSecret());

        file.delete();

    }

}