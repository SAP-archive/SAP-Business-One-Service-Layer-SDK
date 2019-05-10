package com.sap.b1.svcl.client.utils;

import java.util.Map;

public class EnumUtil 
{
	@SuppressWarnings("rawtypes")
	public static Object get(Map map, String val) 
	{
		if(val==null) return null;
		Object rt=map.get(val);
		if(rt==null) throw new RuntimeException("invalid enum value "  + val);
		return rt;
	}
}
