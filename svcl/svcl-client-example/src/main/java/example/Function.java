package example;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.complextype.BinLocationFieldParams;
import com.sap.b1.svcl.client.functionimport.BinLocationFieldsService;
import com.sap.b1.svcl.client.utils.ODataList;
public class Function 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = Logon.login();
		filter(client);
		Logon.logout(client);
	}

	private static void filter(ServiceLayerClient client) 
	{
		BinLocationFieldsService service = client.target(BinLocationFieldsService.class);
		ODataList<BinLocationFieldParams> data = service.getList();
		data.getValue().forEach(action->System.out.println(action.getAbsEntry()));
	}

}
