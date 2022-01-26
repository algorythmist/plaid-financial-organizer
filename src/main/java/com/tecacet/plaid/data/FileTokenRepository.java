package com.tecacet.plaid.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileTokenRepository implements TokenRepository {

    private final String filename;
    private final Properties values = new Properties();

    public FileTokenRepository(String filename) throws IOException {
        this.filename = filename;
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileReader fr = new FileReader(file)) {
            values.load(fr);
        }
    }

    @Override
    public String getToken(String institutionId) {
        return values.getProperty(institutionId);
    }

    @Override
    public void storeToken(String institutionId, String token) {
        values.put(institutionId, token);
        try {
            try (FileOutputStream fos = new FileOutputStream(filename)) {
                values.store(fos, null);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }
}
