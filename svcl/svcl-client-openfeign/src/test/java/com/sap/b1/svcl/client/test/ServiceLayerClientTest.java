package com.sap.b1.svcl.client.test;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.paweladamski.httpclientmock.HttpClientMock;
import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.entitytype.B1Session;
import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;

import feign.Feign;
import feign.Feign.Builder;
import feign.Logger;
import feign.httpclient.ApacheHttpClient;
import feign.slf4j.Slf4jLogger;
public class ServiceLayerClientTest 
{
	ServiceLayerClient httpClient;
	HttpClientMock httpClientMock;
	
    @Before
    public void setUp() throws Exception 
    {
    	httpClientMock = new HttpClientMock("http://localhost");
		Builder builder = Feign.builder().client(new ApacheHttpClient(httpClientMock))
				.logger(new Slf4jLogger())
				.logLevel(Logger.Level.FULL);
    	httpClient = new ServiceLayerClient(builder, "http://localhost/b1s/v1");
    	
    }
    
    
    @Test
    public void login() 
    {
    	httpClientMock.onPost("/b1s/v1/Login").doReturnJSON("{\"odata.metadata\": \"https://10.58.109.95:50000/b1s/v1/$metadata#B1Sessions/@Element\",\"SessionId\": \"69fc492a-38d0-11e9-8000-005056bd23cf\",\"Version\": \"930190\",\"SessionTimeout\": 30}");
    	Login login = httpClient.target(Login.class);
		Login.LoginParam param = new Login.LoginParam();
		param.setCompanyDB("TESTCC");
		param.setUserName("manager");
		param.setPassword("manager");
		B1Session session = login.login(param);
		assertEquals("69fc492a-38d0-11e9-8000-005056bd23cf", session.getSessionId());
    	httpClientMock.verify().post("/b1s/v1/Login").withBody(containsString("CompanyDB")).called();
    }
    
    @Test
    public void testLogout() 
    {
    	httpClientMock.onPost("/b1s/v1/Logout").doReturnJSON("{permission:1}");
    	Logout o = httpClient.target(Logout.class);
		o.logout();
    	httpClientMock.verify().post("/b1s/v1/Logout").called();
    }
}
