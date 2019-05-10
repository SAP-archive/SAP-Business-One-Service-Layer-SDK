package example;

import java.util.HashMap;
import java.util.Map;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entitytype.Document;
import com.sap.b1.svcl.client.utils.ODataList;
public class Sort 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = Logon.login();
		sort(client);
		Logon.logout(client);

	}

	private static void sort(ServiceLayerClient client) 
	{
		Orders orders = client.target(Orders.class);
		Map<String, Object> params = new HashMap<>();
		params.put("$orderby", "DocNum desc");
		ODataList<Document> documents = orders.find(params);
		documents.getValue().forEach(action->System.out.println(action.getDocEntry()));
	}
}
