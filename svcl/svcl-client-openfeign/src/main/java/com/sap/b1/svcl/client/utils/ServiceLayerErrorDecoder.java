package com.sap.b1.svcl.client.utils;

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.svcl.client.ServiceLayerException;
import com.sap.b1.svcl.client.ExceptionDetail;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ServiceLayerErrorDecoder implements ErrorDecoder{
	
	Logger logger = LoggerFactory.getLogger(ServiceLayerErrorDecoder.class);
	ErrorDecoder detault = new ErrorDecoder.Default();
    @Override
    public Exception decode(String methodKey, Response response) 
    {
    	Exception defaultException = detault.decode(methodKey, response);
    	int httpStatus = response.status();
    	
        if ( httpStatus == 400||httpStatus == 404)
        {
        	try(Reader reader = response.body().asReader())
        	{
	        	ObjectMapper om = new ObjectMapper();
	        	ExceptionDetail detail;
				detail = om.readValue(reader, ExceptionDetail.class);
				return new ServiceLayerException(detail);
        	} catch (IOException e) 
        	{
        		logger.debug(e.getMessage());
			}
        }
        return defaultException;
    }
}