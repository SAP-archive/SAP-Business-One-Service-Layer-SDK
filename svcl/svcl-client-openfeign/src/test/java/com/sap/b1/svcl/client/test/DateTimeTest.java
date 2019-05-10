package com.sap.b1.svcl.client.test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entitytype.Document;

public class DateTimeTest extends TestBase
{
    @Test
    public void dateTest() 
    {
    	httpClientMock.onPost("/b1s/v1/Orders").doReturn(200, "{\"DocDate\":\"2099-01-11\"}");
    	Orders service = httpClient.target(Orders.class);
		Document document = new Document();
		document.setDocDate(LocalDate.of(2088, 12, 1));
		Document docReturn = service.create(document);
		assertEquals(LocalDate.of(2099, 1, 11), docReturn.getDocDate());
    	httpClientMock.verify().post("/b1s/v1/Orders").withBody(containsString("\"2088-12-01\"")).called();
    }
    @Test
    public void timeTest() 
    {
    	httpClientMock.onPost("/b1s/v1/Orders").doReturn(200, "{\"DocTime\":\"06:00\"}");
    	Orders service = httpClient.target(Orders.class);
		Document document = new Document();
		document.setDocTime(LocalTime.of(16,0));
		Document docReturn = service.create(document);
		assertEquals(LocalTime.of(6, 0), docReturn.getDocTime());
    	httpClientMock.verify().post("/b1s/v1/Orders").withBody(containsString("\"16:00\"")).called();
   }

}
