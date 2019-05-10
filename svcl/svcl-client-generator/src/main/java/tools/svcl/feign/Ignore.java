package tools.svcl.feign;

public class Ignore 
{
	public static boolean ignore(String name) 
	{
		char c = name.charAt(0);
		if(c>='0' && c<='9')
		{
			return true;
		}
		if(name.startsWith("SAP_"))
		{
			return true;
		}
		if(name.startsWith("U_"))
		{
			return true;
		}
		if(name.indexOf(" ")>0)
		{
			return true;
		}
		if(name.equals("Table2TypeMapping"))
		{
			return true;
		}		
		return false;
	}
}
