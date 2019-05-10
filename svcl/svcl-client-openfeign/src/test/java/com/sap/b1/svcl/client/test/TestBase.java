package com.sap.b1.svcl.client.test;

import org.junit.Before;

import com.github.paweladamski.httpclientmock.HttpClientMock;
import com.sap.b1.svcl.client.ServiceLayerClient;

import feign.Feign;
import feign.Logger;
import feign.Feign.Builder;
import feign.httpclient.ApacheHttpClient;
import feign.slf4j.Slf4jLogger;

public class TestBase {
	
	protected ServiceLayerClient httpClient;
	protected HttpClientMock httpClientMock;
	
    @Before
    public void setUp() throws Exception 
    {
    	httpClientMock = new HttpClientMock("http://localhost");
		Builder builder = Feign.builder().client(new ApacheHttpClient(httpClientMock))
				.logger(new Slf4jLogger())
				.logLevel(Logger.Level.FULL);
    	httpClient = new ServiceLayerClient(builder, "http://localhost/b1s/v1");
    	
    }
}
