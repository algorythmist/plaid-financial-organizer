package com.tecacet.plaid.data;

import java.util.HashMap;
import java.util.Map;

public class MemoryTokenRepository implements TokenRepository {

    private final Map<String, String> tokenMap = new HashMap<>();

    @Override
    public String getToken(String institutionId) {
        return tokenMap.get(institutionId);
    }

    @Override
    public void storeToken(String institutionId, String token) {
        tokenMap.put(institutionId, token);
    }
}
