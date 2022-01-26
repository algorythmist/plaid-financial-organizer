package com.tecacet.plaid.data;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileTokenRepositoryTest {

    @Test
    void storeGetToken() throws IOException {
        String filename = "test.properties";
        TokenRepository tokenRepository = new FileTokenRepository(filename);
        File file = new File(filename);
        assertTrue(file.exists());

        tokenRepository.storeToken("inst1", "token1");
        assertEquals("token1", tokenRepository.getToken("inst1"));
        assertNull(tokenRepository.getToken("inst2"));

        file.delete();
    }
}