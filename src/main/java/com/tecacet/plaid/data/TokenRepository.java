package com.tecacet.plaid.data;

public interface TokenRepository {

    String getToken(String institutionId);

    void storeToken(String institutionId, String token);
}
