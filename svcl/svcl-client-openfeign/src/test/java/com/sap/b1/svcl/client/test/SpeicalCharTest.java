package com.sap.b1.svcl.client.test;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.sap.b1.svcl.client.entityset.BusinessPartners;
public class SpeicalCharTest extends TestBase
{
    @Test
    public void apostropheInString() 
    {
    	httpClientMock.onGet("/b1s/v1/BusinessPartners(CardCode='ABC%27%27DEF')").doReturn(200,"");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	service.get("ABC'DEF");
    	httpClientMock.verify().get("/b1s/v1/BusinessPartners(CardCode='ABC%27%27DEF')").called();
    } 
    
    @Test
    public void blankTest() 
    {
    	httpClientMock.onGet("/b1s/v1/BusinessPartners").withParameter("$orderby", "DocNum desc").doReturn(200,"");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
		Map<String, Object> params = new HashMap<>();
		params.put("$orderby", "DocNum desc");
		service.find(params);
		httpClientMock.verify().get("/b1s/v1/BusinessPartners").withParameter("$orderby", "DocNum desc").called();
    }
    @Test
    public void blankFilterTest() 
    {
    	httpClientMock.onGet("/b1s/v1/BusinessPartners").withParameter("$filter", "CardCode eq 'C001'").doReturn(200,"");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
		service.find("CardCode eq 'C001'");
		httpClientMock.verify().get("/b1s/v1/BusinessPartners").withParameter("$filter", "CardCode eq 'C001'").called();
    }
}
