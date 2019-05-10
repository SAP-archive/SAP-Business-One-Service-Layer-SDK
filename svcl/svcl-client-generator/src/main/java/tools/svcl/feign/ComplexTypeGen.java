package tools.svcl.feign;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.sap.b1.svcl.metadata.edm.ComplexType;


@SpringBootApplication
@Component
public class ComplexTypeGen extends GeneratorBase implements InitializingBean
{
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext appContext = SpringApplication.run(ComplexTypeGen.class, args);
		ComplexTypeGen instance = appContext.getBean(ComplexTypeGen.class);
		instance.execute();
	}
	
	void execute() throws IOException, Exception
	{
		for(ComplexType complexType:this.edmx.getDataServices().getSchema().getComplexType())
		{
			String name = complexType.getName();
			System.out.println(name);
			String outputFileName = FilenameUtils.getBaseName(complexType.getName()) + ".java";
			HashMap<String,Object> data = new HashMap<>();
			data.put("data", complexType);				
			super.process(data, outputFileName);			
		}	
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.init("classpath:template/complexType.ftl", "com/sap/b1/svcl/client/complextype");
		
	}
}
