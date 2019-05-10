package tools.svcl.feign;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sap.b1.svcl.metadata.edm.Schema;
import com.sap.b1.svcl.metadata.edmx.Edmx;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;


public class NormalizeMetadata
{
	private static final class NamespacePrefixMapperExtension extends NamespacePrefixMapper {
		@Override
		public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		    if ("http://schemas.microsoft.com/ado/2007/06/edmx".equals(namespaceUri)) return "edmx";
		    if ("http://schemas.microsoft.com/ado/2007/08/dataservices/metadata".equals(namespaceUri)) return "m";
		    return "";
		}
	}
	public static void normalize(Edmx edmx) throws Exception
	{
		Schema schema = edmx.getDataServices().getSchema();
		
		schema.getEntityType().removeIf(a->Ignore.ignore(a.getName()));
		schema.getComplexType().removeIf(a->Ignore.ignore(a.getName()));
		
		schema.getComplexType().forEach(action->
		{
			action.getProperty().removeIf(prop->Ignore.ignore(prop.getName()));
		});
		schema.getEntityType().forEach(action->
		{
			action.getProperty().removeIf(prop->Ignore.ignore(prop.getName()));
		});
		schema.getEntityContainer().getEntitySet().removeIf(a->Ignore.ignore(a.getName()));
		schema.getEntityContainer().getFunctionImport().forEach(action->
		{
			if(!action.getHttpMethod().equalsIgnoreCase("post"))
			{
				throw new RuntimeException("GET functionImport is not implemented");
			}
		});
	}

	public static void main(String[] args) throws Exception 
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(Edmx.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Edmx edmx = (Edmx) jaxbUnmarshaller.unmarshal(new File("../svcl-client-openfeign/src/main/resources/metadata.xml"));
		normalize(edmx);	

	    jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapperExtension());
		jaxbMarshaller.marshal(edmx, new File("../svcl-client-openfeign/src/main/resources/metadata.debug.xml"));
	}
}
