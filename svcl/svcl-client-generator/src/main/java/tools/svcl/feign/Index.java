package tools.svcl.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Index 
{
	@Autowired
	EnumGen enums;
	@Autowired
	ComplexTypeGen complexType;
	@Autowired
	EntityTypeGen entityType;
	@Autowired
	EntitySetGen entitySet;
	@Autowired
	FunctionImportGen functionImport;
	
	public static void main(String[] args) throws Exception 
	{
		ApplicationContext appContext = SpringApplication.run(Index.class, args);
		Index instance = appContext.getBean(Index.class);
		instance.enums.execute();
		instance.complexType.execute();	
		instance.entityType.execute();
		instance.entitySet.execute();
		instance.functionImport.execute();
	}
}
