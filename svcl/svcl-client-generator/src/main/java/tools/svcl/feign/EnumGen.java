package tools.svcl.feign;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.sap.b1.svcl.metadata.edm.EnumType;

import freemarker.template.TemplateException;
@SpringBootApplication
@Component
public class EnumGen extends GeneratorBase implements InitializingBean
{
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext appContext = SpringApplication.run(EnumGen.class, args);
		EnumGen instance = appContext.getBean(EnumGen.class);
		instance.execute();
	}
	void execute() throws IOException, TemplateException
	{		
		for(EnumType enumType:edmx.getDataServices().getSchema().getEnumType())
		{
			String outputFileName = FilenameUtils.getBaseName(enumType.getName()) + ".java";
			HashMap<String,Object> data = new HashMap<>();
			data.put("data", enumType);
			super.process(data, outputFileName);
		}	
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.init("classpath:template/enum.ftl","com/sap/b1/svcl/client/enums");
		
	}
}
