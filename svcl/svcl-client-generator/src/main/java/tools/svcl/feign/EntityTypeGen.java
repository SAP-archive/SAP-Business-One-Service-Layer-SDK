package tools.svcl.feign;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.sap.b1.svcl.metadata.edm.EntityType;


@SpringBootApplication
@Component
public class EntityTypeGen  extends GeneratorBase implements InitializingBean
{
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext appContext = SpringApplication.run(EntityTypeGen.class, args);
		EntityTypeGen instance = appContext.getBean(EntityTypeGen.class);
		instance.execute();
	}
	void execute() throws IOException, Exception
	{
		for(EntityType type:edmx.getDataServices().getSchema().getEntityType())
		{
			String name = type.getName();
			System.out.println(name);
			String outputFileName = FilenameUtils.getBaseName(type.getName()) + ".java";
			HashMap<String,Object> data = new HashMap<>();
			data.put("data", type);			
			super.process(data, outputFileName);			
		}			
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		super.init("classpath:template/entityType.ftl","com/sap/b1/svcl/client/entitytype");
	}
}
