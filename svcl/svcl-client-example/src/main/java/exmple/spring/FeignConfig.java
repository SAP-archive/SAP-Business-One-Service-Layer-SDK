package exmple.spring;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;

@Configuration
public class FeignConfig 
{
	@Bean ServiceLayerClient feignClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		return ServiceLayerClient.inSecureClient("https://10.58.109.95:50000/b1s/v1");
	}
	
	@Bean Login login(ServiceLayerClient client) {return client.target(Login.class);}
	@Bean Logout logout(ServiceLayerClient client)	{return client.target(Logout.class);}
}
