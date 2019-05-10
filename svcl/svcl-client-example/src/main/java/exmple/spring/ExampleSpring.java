package exmple.spring;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;

@SpringBootApplication
public class ExampleSpring 
{
	@Autowired
	Login login;
	
	@Autowired
	Logout logout;
	static public void main(String[] argc) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException 
	{
		ApplicationContext appCtx = SpringApplication.run(ExampleSpring.class, argc);
		ExampleSpring app = appCtx.getBean(ExampleSpring.class);
		app.execute();
	}

	private void execute() 
	{
		Login.LoginParam param = new Login.LoginParam();
		param.setCompanyDB("TESTCC");
		param.setUserName("manager");
		param.setPassword("manager");
		login.login(param);
		logout.logout();
		
	}
}
