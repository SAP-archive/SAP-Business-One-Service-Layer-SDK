package example;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;

public class Logon 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = login();
		logout(client);
	}


	public static void logout(ServiceLayerClient client) 
	{
		Logout logout = client.target(Logout.class);
		logout.logout();		
	}


	public static ServiceLayerClient login() throws Exception
	{
		ServiceLayerClient client = ServiceLayerClient.inSecureClient("https://xxx.xxx.xxx.xxx:50000/b1s/v1");
		Login login = client.target(Login.class);
		Login.LoginParam param = new Login.LoginParam();
		param.setCompanyDB("SBODEMOUS");
		param.setUserName("manager");
		param.setPassword("******");
		login.login(param);	
		return client;
	}
}
