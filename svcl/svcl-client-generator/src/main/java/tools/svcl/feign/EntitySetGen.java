package tools.svcl.feign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.sap.b1.svcl.metadata.edm.EntitySet;
import com.sap.b1.svcl.metadata.edm.FunctionImport;
@SpringBootApplication
@Component
public class EntitySetGen  extends GeneratorBase implements InitializingBean
{
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext appContext = SpringApplication.run(EntitySetGen.class, args);
		EntitySetGen instance = appContext.getBean(EntitySetGen.class);
		instance.execute();
	}
	
	@Autowired
	BindableFunctionImport bindableFunctions;
	
	void execute() throws Exception
	{
		for(EntitySet entitySet:this.edmx.getDataServices().getSchema().getEntityContainer().getEntitySet())
		{
			String name = entitySet.getName();
			System.out.println(name);
			String outputFileName = FilenameUtils.getBaseName(entitySet.getName()) + ".java";
			
			String entityType = entitySet.getEntityType();
			List<FunctionImport> functions = bindableFunctions.get(entityType.substring("SAPB1.".length()));
	
			if(functions==null)
			{
				functions = new ArrayList<>();
			}

			HashMap<String,Object> data = new HashMap<>();
			data.put("function", functions);
			data.put("data", entitySet);			
			super.process(data, outputFileName);			
		}	
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.init("classpath:template/entitySet.ftl","com/sap/b1/svcl/client/entityset");
		
	}


}
