package com.tecacet.plaid;


import com.plaid.client.model.CountryCode;
import com.plaid.client.model.Institution;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaidInstitutionServiceTest {

    @Test
    void getInstitution() {
        PlaidInstitutionService institutionService = new PlaidInstitutionService();
        Institution institution = institutionService.getInstitution("ins_109509");
        assertEquals("First Gingham Credit Union", institution.getName());
    }

    @Test
    void getInstitutionWithWrongId() {
        PlaidInstitutionService institutionService = new PlaidInstitutionService();
        assertThrows(PlaidException.class,
                () -> institutionService.getInstitution("ins_X"));

    }

    @Test
    void getInstitutions() {
        PlaidInstitutionService institutionService = new PlaidInstitutionService();
        List<Institution> institutions =
                institutionService.getInstitutions(Collections.singletonList(CountryCode.US), 100, 1);
        System.out.println(institutions);
    }
}