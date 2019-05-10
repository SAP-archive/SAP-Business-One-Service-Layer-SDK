package tools.svcl.feign;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.sap.b1.svcl.metadata.edm.FunctionImport;
@SpringBootApplication
@Component
public class FunctionImportGen extends GeneratorBase implements InitializingBean
{
	
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext appContext = SpringApplication.run(FunctionImportGen.class, args);
		FunctionImportGen instance = appContext.getBean(FunctionImportGen.class);
		instance.execute();
	}
	@Autowired
	FunctionManager functionManager;
	
	void execute() throws IOException, Exception
	{
		for(Map.Entry<String,List<FunctionImport>> entry:functionManager.entrySet())
		{
			String name = entry.getKey();
			System.out.println(name);
			String outputFileName = FilenameUtils.getBaseName(name) + ".java";
			HashMap<String,Object> data = new HashMap<>();
			data.put("name", name);
			data.put("function", entry.getValue());
			super.process(data, outputFileName);
		}		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.init("classpath:template/functionImport.ftl","com/sap/b1/svcl/client/functionimport");
		
	}




}
