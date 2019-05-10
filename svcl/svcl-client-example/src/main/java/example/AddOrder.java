package example;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.complextype.DocumentLine;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entitytype.Document;

public class AddOrder 
{
	static public void main(String[] argc) throws Exception 
	{
		ServiceLayerClient client = Logon.login();
		addOrder(client);
		updateOrder(client);
		Logon.logout(client);
	}
	private static void updateOrder(ServiceLayerClient client) {
		Orders orderService = client.target(Orders.class);
		Document document= new Document();
		document.setReference1("test");
		orderService.update(2, document);
	}
	private static void addOrder(ServiceLayerClient client) 
	{
		Orders orderService = client.target(Orders.class);
		Document document= new Document();
		document.setCardCode("C");
		document.setDocDate(LocalDate.now());
		document.setDocDueDate(LocalDate.now());
		DocumentLine line = new DocumentLine();
		line.setItemCode("i1");
		line.setQuantity(BigDecimal.valueOf(1));
		line.setPrice(BigDecimal.valueOf(1.3));
		document.getDocumentLines().add(line);
		Document docCreated = orderService.create(document);
		System.out.println("Document created with Number :" + docCreated.getDocNum());		
	}
	
}

















