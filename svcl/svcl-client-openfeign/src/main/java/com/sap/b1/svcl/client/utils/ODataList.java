package com.sap.b1.svcl.client.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ODataList<T> 
{

	public void setValue(List<T> value) {
		this.value = value;
	}

	public List<T> getValue() 
	{
		return value;
	}
	
	@JsonProperty(value="value")   	
	protected List<T> value; 
	@JsonProperty(value="odata.metadata")
	protected String odataMetadata;
	
	public String getOdataMetadata() {
		return odataMetadata;
	}

	public void setOdataMetadata(String odataMetadata) {
		this.odataMetadata = odataMetadata;
	}
}
