package example;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.entityset.BusinessPartners;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entityset.States;
import com.sap.b1.svcl.client.entitytype.BusinessPartner;
import com.sap.b1.svcl.client.entitytype.Document;
import com.sap.b1.svcl.client.entitytype.State;
public class GetByKey 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = Logon.login();
		getCustomer(client);
		getOrder(client);
		getCustomer(client);
		getStates(client);
		Logon.logout(client);

	}
	private static void getStates(ServiceLayerClient client) {
		States service = client.target(States.class);
		State data = service.get("US", "AK2");
		System.out.println(data.getName());
		
	}
	private static void getOrder(ServiceLayerClient client) 
	{
		Orders orders = client.target(Orders.class);
		Document document = orders.get(2);
		System.out.println(document.getCreationDate());
		System.out.println(document.getDocTime());
	}
	private static void getCustomer(ServiceLayerClient client) 
	{
		BusinessPartners service = client.target(BusinessPartners.class);
		BusinessPartner data = service.get("C");
		System.out.println(data);
	}
}
