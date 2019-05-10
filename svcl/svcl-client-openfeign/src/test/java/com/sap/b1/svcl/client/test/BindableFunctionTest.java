package com.sap.b1.svcl.client.test;


import org.junit.Test;

import com.sap.b1.svcl.client.entityset.Orders;
public class BindableFunctionTest extends TestBase
{
   
    @Test
    public void bindFunctionExists() 
    {
    	httpClientMock.onPost("/b1s/v1/Orders(DocEntry=1)/Cancel").doReturn(200, "");
    	Orders service = httpClient.target(Orders.class);
		service.cancel(1);
    	httpClientMock.verify().post("/b1s/v1/Orders(DocEntry=1)/Cancel").called();
    }
}
