package example.udt;

import com.sap.b1.svcl.client.ServiceLayerClient;

import example.Logon;
public class UserDefinedTable 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = Logon.login();
		get(client);
		Logon.logout(client);
	}

	private static void get(ServiceLayerClient client) 
	{
		Colors service = client.target(Colors.class);
		Color color = service.get("Blue");
		System.out.println(color.name);
	}
}
