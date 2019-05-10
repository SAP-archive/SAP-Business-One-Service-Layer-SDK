package com.sap.b1.svcl.client.test;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.sap.b1.svcl.client.entityset.BusinessPartners;
import com.sap.b1.svcl.client.entitytype.BusinessPartner;
public class UserDefinedField extends TestBase
{
   
    @Test
    public void userDefinedField() 
    {
    	httpClientMock.onPost("/b1s/v1/BusinessPartners").doReturnJSON("{\"U_Field2\":200}");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	BusinessPartner data = new BusinessPartner();
    	data.getUserFields().put("U_Field", 100);
    	data.getUserFields().put("U_FieldDate", new Date());
		BusinessPartner dataReturn = service.create(data);
    	httpClientMock.verify().post("/b1s/v1/BusinessPartners").withBody(containsString("U_Field")).called();
    	assertEquals(200, dataReturn.getUserFields().get("U_Field2"));
    }
}
