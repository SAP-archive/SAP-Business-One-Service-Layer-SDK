package tools.svcl.feign;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.sap.b1.svcl.metadata.edmx.Edmx;

@Configuration
public class AppConfig 
{
	
	@Value("${metadata:../svcl-client-openfeign/src/main/resources/metadata.xml}")
	String metadataFileName;
	
	@Bean Edmx edmx() throws Exception
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(Edmx.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		//Edmx edmx = (Edmx) jaxbUnmarshaller.unmarshal(new File("src/main/resources/metadata/metadata.xml"));
		Edmx edmx = (Edmx) jaxbUnmarshaller.unmarshal(ResourceUtils.getURL(metadataFileName).openStream());
		NormalizeMetadata.normalize(edmx);
		return edmx;		
	}

	@Bean FunctionManager functionManager(Edmx edmx)
	{
		FunctionManager functionManager = new FunctionManager(edmx);
		return functionManager;
	}
	
	@Bean BindableFunctionImport bindableFunction(Edmx edmx)
	{
		BindableFunctionImport rt = new BindableFunctionImport(edmx);
		return rt;
	}
}
