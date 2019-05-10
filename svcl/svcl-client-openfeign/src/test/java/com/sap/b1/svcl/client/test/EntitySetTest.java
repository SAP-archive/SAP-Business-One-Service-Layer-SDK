package com.sap.b1.svcl.client.test;
import java.util.HashMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.sap.b1.svcl.client.entityset.BusinessPartners;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entityset.States;
import com.sap.b1.svcl.client.entitytype.BusinessPartner;
import com.sap.b1.svcl.client.ServiceLayerException;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.containsString;

public class EntitySetTest extends TestBase
{

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Test
    public void create() 
    {
    	httpClientMock.onPost("/b1s/v1/BusinessPartners").doReturn(201, "{\"CardCode\":\"C001\"}");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	BusinessPartner data = new BusinessPartner();
    	BusinessPartner returnData = service.create(data);
    	httpClientMock.verify().post("/b1s/v1/BusinessPartners").called();
    	assertEquals("C001", returnData.getCardCode());
    }	
    @Test
    public void createFailed() 
    {
    	httpClientMock.onPost("/b1s/v1/BusinessPartners").doReturn(400, "{\"error\":{\"code\":-5002,\"message\" : {\"lang\" : \"en-us\",\"value\" : \"message\"}}}");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	BusinessPartner data = new BusinessPartner();
    	expectedException.expectCause(isA(ServiceLayerException.class));
    	service.create(data);
    }
    
	@Test
    public void update() 
    {
    	httpClientMock.onPatch("/b1s/v1/BusinessPartners(CardCode='C001')").doReturn(204, "");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	BusinessPartner data = new BusinessPartner();
    	data.setCardName("NAME");
    	service.update("C001", data);
    	httpClientMock.verify().patch("/b1s/v1/BusinessPartners(CardCode='C001')").withBody(containsString("NAME")).called();
    }
	@Test
    public void updateFailed() 
    {
    	httpClientMock.onPatch("/b1s/v1/BusinessPartners(CardCode='C001')").doReturn(404, "{\"error\":{\"code\":-5002,\"message\" : {\"lang\" : \"en-us\",\"value\" : \"message\"}}}");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	BusinessPartner data = new BusinessPartner();
    	data.setCardName("NAME");
    	expectedException.expectCause(isA(ServiceLayerException.class));
    	service.update("C001", data);
    }
	
	@Test
    public void delete() 
    {
    	httpClientMock.onDelete("/b1s/v1/BusinessPartners(CardCode='C001')").doReturn(204, "");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	service.delete("C001");
    	httpClientMock.verify().delete("/b1s/v1/BusinessPartners(CardCode='C001')").called();
    }
	
	@Test
    public void deleteFailed() 
    {
    	httpClientMock.onDelete("/b1s/v1/BusinessPartners(CardCode='C001')").doReturn(404, "{\"error\":{\"code\":-5002,\"message\" : {\"lang\" : \"en-us\",\"value\" : \"message\"}}}");
    	BusinessPartners service = httpClient.target(BusinessPartners.class);
    	expectedException.expectCause(isA(ServiceLayerException.class));
    	service.delete("C001");    	
   }
    @Test
    public void getByTwoKey() 
    {
    	httpClientMock.onGet("/b1s/v1/States(Country='A',Code='B')").doReturn(200, "{}");
    	States service = httpClient.target(States.class);
    	service.get("A", "B");
    	httpClientMock.verify().get("/b1s/v1/States(Country='A',Code='B')").called();
    }
    
    @Test
    public void filter() 
    {
    	httpClientMock.onGet("/b1s/v1/Orders").withParameter("$filter", "DocEntry eq 1").doReturn(200, "");
    	Orders service = httpClient.target(Orders.class);
		service.find("DocEntry eq 1");
    	httpClientMock.verify().get("/b1s/v1/Orders").withParameter("$filter", "DocEntry eq 1").called();
    }
    
    
    @Test
    public void advanced() 
    {
    	httpClientMock.onGet("/b1s/v1/Orders")
    		.withParameter("$filter", "DocEntry eq 1")
    		.withParameter("$orderby", "DocEntry desc")
    		.doReturn(200, "");
    	
    	Orders service = httpClient.target(Orders.class);
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("$filter", "DocEntry eq 1");
    	map.put("$orderby", "DocEntry desc");
		service.find(map);
		
    	httpClientMock.verify().get("/b1s/v1/Orders")
    		.withParameter("$filter", "DocEntry eq 1")
    		.withParameter("$orderby", "DocEntry desc")
    		.called();
    }
}
