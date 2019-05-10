package example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AddOrderWoSdk 
{
	static public void main(String[] argc) throws Exception 
	{
		CloseableHttpClient client = HttpClients.custom()
				.setSSLContext(
						new SSLContextBuilder().loadTrustMaterial(null, TrustSelfSignedStrategy.INSTANCE).build())
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
		login(client);
		
		//Add order
		HttpPost post = new HttpPost("https://10.58.109.95:50000/b1s/v1/Orders");
		post.setHeader("Content-Type", "application/json; charset=utf-8");
		ObjectMapper om = new ObjectMapper();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("CardCode", "C");
		data.put("DocDueDate", "2019-02-22");
		List<HashMap<String, Object>> lines = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> line = new HashMap<String, Object>();
		line.put("ItemCode","i1");
		line.put("Quantity",BigDecimal.ONE);
		line.put("Price",BigDecimal.ONE);
		lines.add(line);		
		data.put("DocumentLines", lines);
		String json = om.writeValueAsString(data);
		HttpEntity entity = new StringEntity(json);
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode<200 || statusCode>=300) {
			throw new RuntimeException("add order failed with code " + statusCode);
		}
		String responseBody = EntityUtils.toString(response.getEntity());
		@SuppressWarnings("rawtypes")
		HashMap newOrder = om.readValue(responseBody,HashMap.class);
		Integer docEntry = (Integer) newOrder.get("DocEntry");
		System.out.println("Document created with Number :" + docEntry);
		
		// Logout
		post = new HttpPost("https://10.58.109.95:50000/b1s/v1/Logout");
		response = client.execute(post);
		if (statusCode<200 || statusCode>=300) {
			throw new RuntimeException("logout failed");
		}
	}

	private static void login(CloseableHttpClient client) throws Exception {
		HttpPost post = new HttpPost("https://10.58.109.95:50000/b1s/v1/Login");
		post.setHeader("Content-Type", "application/json; charset=utf-8");

		ObjectMapper om = new ObjectMapper();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("CompanyDB", "SUN");
		data.put("UserName", "manager");
		data.put("Password", "Initial0@");
		String json = om.writeValueAsString(data);
		HttpEntity entity = new StringEntity(json);
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode<200 || statusCode>=300) {
			throw new RuntimeException("login failed");
		}
	}
}
