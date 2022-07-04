package com.tecacet.plaid;

import com.plaid.client.model.PlaidError;

public class PlaidException extends RuntimeException {

    public PlaidException(String message) {
        super(message);
    }

    public PlaidException(PlaidError plaidError) {
        super(plaidError.getErrorMessage());
    }

    public PlaidException(Exception e) {
        super(e);
    }

}
