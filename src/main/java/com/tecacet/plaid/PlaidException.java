package com.tecacet.plaid;

public class PlaidException extends RuntimeException {


    public PlaidException(String message) {
        super(message);
    }

    public PlaidException(Exception e) {
        super(e);
    }

}
