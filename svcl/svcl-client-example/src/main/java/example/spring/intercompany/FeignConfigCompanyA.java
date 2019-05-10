package example.spring.intercompany;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;

@Configuration
public class FeignConfigCompanyA 
{
	@Bean("a-client") ServiceLayerClient feignClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		return ServiceLayerClient.inSecureClient("https://10.58.109.95:50000/b1s/v1");
	}
	
	@Bean("a-login") Login login(@Qualifier("a-client")  ServiceLayerClient client) {return client.target(Login.class);}
	@Bean("a-logout") Logout logout(@Qualifier("a-client")  ServiceLayerClient client)	{return client.target(Logout.class);}
}
