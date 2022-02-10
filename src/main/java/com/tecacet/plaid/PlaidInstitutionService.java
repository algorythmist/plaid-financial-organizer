package com.tecacet.plaid;

import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PlaidInstitutionService extends  AbstractPlaidService {

    public PlaidInstitutionService(){super();}

    public PlaidInstitutionService(PlaidApi plaidApiService) {
        super(plaidApiService);
    }

    //    First Platypus Bank 	ins_109508
    //    First Gingham Credit Union 	ins_109509
    //    Tattersall Federal Credit Union 	ins_109510
    //    Tartan Bank 	ins_109511
    //    Houndstooth Bank 	ins_109512
    //    Tartan-Dominion Bank of Canada 	ins_43
    public Institution getInstitution(String institutionId)  {
        InstitutionsGetByIdRequest req = new InstitutionsGetByIdRequest();
        req.setInstitutionId(institutionId);
        req.setCountryCodes(Collections.singletonList(CountryCode.US));
        Response<InstitutionsGetByIdResponse> response;
        try {
            response = plaidApi.institutionsGetById(req).execute();
            return extractBody(response).getInstitution();
        } catch (IOException e) {
            throw new PlaidException(e);
        }

    }

    public List<Institution> getInstitutions(List<CountryCode> countryCodes, int count, int offset)  {
        try {
            InstitutionsGetRequest getRequest = new InstitutionsGetRequest();
            getRequest.setCountryCodes(countryCodes);
            getRequest.setCount(count);
            getRequest.setOffset(offset);
            Response<InstitutionsGetResponse> response = plaidApi.institutionsGet(getRequest).execute();
            return extractBody(response).getInstitutions();
        } catch (IOException e) {
            throw new PlaidException(e);
        }
    }

}
