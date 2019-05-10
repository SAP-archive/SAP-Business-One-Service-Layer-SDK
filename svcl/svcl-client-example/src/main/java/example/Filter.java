package example;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entitytype.Document;
import com.sap.b1.svcl.client.utils.ODataList;
public class Filter 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = Logon.login();
		filter(client);
		Logon.logout(client);
	}

	private static void filter(ServiceLayerClient client) 
	{
		Orders orders = client.target(Orders.class);
		ODataList<Document> documents = orders.find("DocEntry gt 1");
		documents.getValue().forEach(action->System.out.println(action.getDocEntry()));
		
	}

}
