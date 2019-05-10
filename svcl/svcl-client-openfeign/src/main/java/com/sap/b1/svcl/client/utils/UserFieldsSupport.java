package com.sap.b1.svcl.client.utils;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class UserFieldsSupport 
{
	protected Map<String,Object> userFields;
	
    @JsonAnyGetter
    public Map<String, Object> getUserFields() {
    	if(userFields==null) {
    	userFields = new TreeMap<>();
    	}
        return userFields;
    }

    public void setUserFields(Map<String, Object> val) {
    	userFields = val;
    }
    
    @JsonAnySetter
    public void setUserField(String name, Object value) 
    {
    	getUserFields().put(name, value);
    } 
}
