package example;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.complextype.DocumentLine;
import com.sap.b1.svcl.client.entityset.BusinessPartners;
import com.sap.b1.svcl.client.entityset.Items;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entitytype.BusinessPartner;
import com.sap.b1.svcl.client.entitytype.Document;
import com.sap.b1.svcl.client.entitytype.Item;
import com.sap.b1.svcl.client.enums.BoCardTypes;
import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;
import com.sap.b1.svcl.client.utils.ODataList;
@SuppressWarnings("unused")
public class Example 
{
	static public void main(String[] argc) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException 
	{
		ServiceLayerClient client = ServiceLayerClient.inSecureClient("https://10.58.109.95:50000/b1s/v1");
		login(client);

		String customer = listCustomer(client);
		String item = listItem(client);
		System.out.println(customer);
		System.out.println(item);
		
		Logout logout = client.target(Logout.class);;
		logout.logout();
	}
	private static String listCustomer(ServiceLayerClient client) {
		BusinessPartners bpService = client.target(BusinessPartners.class);
		ODataList<BusinessPartner> bp = bpService.findAll();
		return bp.getValue().get(0).getCardCode();
	}
	
	private static String listItem(ServiceLayerClient client) {
		Items item = client.target(Items.class);
		ODataList<Item> list = item.findAll();
		return list.getValue().get(0).getItemCode();
	}
	
	private static void readCustomer(ServiceLayerClient client) {
		BusinessPartners bpService = client.target(BusinessPartners.class);
		BusinessPartner bp = bpService.get("1''23");
		System.out.println(String.format("Customer loaded : %s",bp.getCardCode()));
	}

	private static void createCustomer(ServiceLayerClient client) {
		BusinessPartners bpService = client.target(BusinessPartners.class);
		BusinessPartner bp = new BusinessPartner();
		bp.setCardCode("C004");
		bp.setCardType(BoCardTypes.cCustomer);
		bp = bpService.create(bp);
		BoCardTypes cardType = bp.getCardType();
		System.out.println(String.format("Customer created : %s",bp.getCardName()));
		
		bp.setCardName("NAME");
		bpService.update(bp.getCardCode(), bp);
		
		
	}

	private static void listOrder(ServiceLayerClient client) {
		Orders orderService = client.target(Orders.class);
		List<Document> allOrder = orderService.findAll().getValue();
		for(Document order:allOrder)
		{
			System.out.println(order.getDocEntry());
		}
		
		/*Document order = orderService.get(allOrder.get(0).getDocEntry());
		System.out.println(order.getCardCode());*/
		
	}

	private static void createOrder(ServiceLayerClient client) {
		Orders orderService = client.target(Orders.class);
		Document document= new Document();
		document.setCardCode("C1");
		
		document.setDocDueDate(LocalDate.now());
		DocumentLine line = new DocumentLine();
		line.setItemCode("i1");
		line.setQuantity(BigDecimal.ONE);
		line.setPrice(BigDecimal.ONE);
		document.getDocumentLines().add(line);
		Document docCreated = orderService.create(document);
		System.out.println(docCreated.getDocNum());
		
	}

	private static void login(ServiceLayerClient client) {
		Login login = client.target(Login.class);
		Login.LoginParam param = new Login.LoginParam();
		param.setCompanyDB("TESTCC");
		param.setUserName("manager");
		param.setPassword("manager");
		login.login(param);	
	}
}
