package com.tecacet.plaid;

import com.plaid.client.PlaidApiService;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.InstitutionsGetRequest;
import com.plaid.client.response.Institution;
import com.plaid.client.response.InstitutionsGetByIdResponse;
import com.plaid.client.response.InstitutionsGetResponse;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PlaidInstitutionService extends  AbstractPlaidService {

    public PlaidInstitutionService(){super();}

    public PlaidInstitutionService(PlaidApiService plaidApiService) {
        super(plaidApiService);
    }

    //    First Platypus Bank 	ins_109508
    //    First Gingham Credit Union 	ins_109509
    //    Tattersall Federal Credit Union 	ins_109510
    //    Tartan Bank 	ins_109511
    //    Houndstooth Bank 	ins_109512
    //    Tartan-Dominion Bank of Canada 	ins_43
    public Institution getInstitution(String institutionId)  {
        InstitutionsGetByIdRequest req = new InstitutionsGetByIdRequest(institutionId,
                Collections.singletonList("USA"));
        Response<InstitutionsGetByIdResponse> response;
        try {
            response = plaidApiService.institutionsGetById(req).execute();
            return extractBody(response).getInstitution();
        } catch (IOException e) {
            throw new PlaidException(e);
        }

    }

    public List<Institution> getInstitutions(List<String> countryCodes, int count, int offset)  {
        try {
            Response<InstitutionsGetResponse> response = plaidApiService
                    .institutionsGet(new InstitutionsGetRequest(count, offset, countryCodes)).execute();
            return extractBody(response).getInstitutions();
        } catch (IOException e) {
            throw new PlaidException(e);
        }
    }

}
