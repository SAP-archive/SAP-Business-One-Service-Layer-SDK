package com.sap.b1.svcl.client.utils;
import feign.Param;


public class ODataKeyExpander implements Param.Expander
{
	@Override
	public String expand(Object value) 
	{
		String str = value.toString();
		str = str.replaceAll("'", "''");
		return str;
	}
}
