package com.sap.b1.svcl.client.test;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sap.b1.svcl.client.entityset.BusinessPartners;
import com.sap.b1.svcl.client.entitytype.BusinessPartner;
public class UnicodeTest extends TestBase
{
    @Test
    public void urlEncoding() 
    {
    	httpClientMock.onGet("/b1s/v1/BusinessPartners(CardCode='%E4%B8%AD%E6%96%87')").doReturn(200,"");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	service.get("中文");
    	httpClientMock.verify().get("/b1s/v1/BusinessPartners(CardCode='%E4%B8%AD%E6%96%87')").called();
    }
    
    @Test
    public void bodyEncoding() 
    {
    	httpClientMock.onPost("/b1s/v1/BusinessPartners").doReturnJSON("{\"CardName\":\"日本語\"}");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	BusinessPartner data = new BusinessPartner();
    	data.setCardCode("中文");
		BusinessPartner dataReturn = service.create(data);
    	httpClientMock.verify().post("/b1s/v1/BusinessPartners").withBody(containsString("中文")).called();
    	assertEquals("日本語",dataReturn.getCardName());
    }
}
