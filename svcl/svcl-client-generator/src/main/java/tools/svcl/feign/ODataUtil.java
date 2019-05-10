package tools.svcl.feign;

import java.beans.Introspector;
import java.util.Optional;

import com.sap.b1.svcl.metadata.edm.EntitySet;
import com.sap.b1.svcl.metadata.edm.EntityType;
import com.sap.b1.svcl.metadata.edm.Property;
import com.sap.b1.svcl.metadata.edm.PropertyRef;
import com.sap.b1.svcl.metadata.edmx.Edmx;

public class ODataUtil 
{
	static public String convertODataType(String type) 
	{
		if(type==null)
		{
			return "void";
		}
		if(type.startsWith("Collection("))
		{
			String[] a = type.split("[()]");
			return "List<" + a[1].substring("SAPB1.".length()) + ">";
		}
		if(type.startsWith("SAPB1."))
		{
			return type.substring("SAPB1.".length());
		}
		switch(type)
		{
		case "Edm.String": return "String";
		case "Edm.Int32": return "Integer";
		case "Edm.Double": return "BigDecimal";
		case "Edm.DateTime": return "LocalDate";
		case "Edm.Time":return "LocalTime";
		}
		throw new RuntimeException(type + " is not supported");
	}
/*	public static String getIdType(EntitySet entitySet,Edmx edmx)
	{
		String type = entitySet.getEntityType().substring("SAPB1.".length());
		Optional<EntityType> entityTypeTemp = edmx.getDataServices().getSchema().getEntityType().stream().filter(a->a.getName().equals(type)).findFirst();
		EntityType entityType = entityTypeTemp.get();
		String columnName = entityType.getKey().getPropertyRef().get(0).getName();
		Property prop = entityType.getProperty().stream().filter(a->a.getName().equals(columnName)).findFirst().get();
		String odataType = prop.getType();
		String rt = convertODataType(odataType);
		return rt;
	}*/
	
	public static String getIdParams(EntitySet entitySet,Edmx edmx) 
	{
		String type = entitySet.getEntityType().substring("SAPB1.".length());
		Optional<EntityType> entityTypeTemp = edmx.getDataServices().getSchema().getEntityType().stream().filter(a->a.getName().equals(type)).findFirst();
		EntityType entityType = entityTypeTemp.get();
		
		StringBuilder rt  =new StringBuilder();
		for(PropertyRef propRef:entityType.getKey().getPropertyRef())
		{
			String columnName = propRef.getName();
			Property prop = entityType.getProperty().stream().filter(a->a.getName().equals(columnName)).findFirst().get();
			String odataType = prop.getType();
			String javaType = convertODataType(odataType);
			rt.append(String.format("@Param(value=\"%s\",expander=ODataKeyExpander.class) %s %s,",columnName, javaType,columnName));
			
		}
		rt.setLength(rt.length()-1);
		return rt.toString();
	}
	public static String getIdFormat(EntitySet entitySet,Edmx edmx) 
	{
		String type = entitySet.getEntityType().substring("SAPB1.".length());
		Optional<EntityType> entityTypeTemp = edmx.getDataServices().getSchema().getEntityType().stream().filter(a->a.getName().equals(type)).findFirst();
		EntityType entityType = entityTypeTemp.get();
		
		StringBuilder rt  =new StringBuilder();
		for(PropertyRef propRef:entityType.getKey().getPropertyRef())
		{
			String columnName = propRef.getName();
			Property prop = entityType.getProperty().stream().filter(a->a.getName().equals(columnName)).findFirst().get();
			String odataType = prop.getType();
			
			switch(odataType)
			{
			case "Edm.String": 
				rt.append(String.format("%s='{%s}',",columnName,columnName));
				break;
			case "Edm.Int32": 
				rt.append(String.format("%s={%s},",columnName,columnName));
				break;
			default:
				throw new RuntimeException(odataType + " is not supported");
			}
			
		}
		rt.setLength(rt.length()-1);
		return rt.toString();
	}
	static String [] keywords = {"import","protected","break"};
	public static boolean isKeyWords(String words)
	{
		for(String key:keywords)
		{
			if(key.equals(words))
			{
				return true;
			}
		}
		return false;
	}	
	public static String convertKeyWords(String words)
	{
		if(isKeyWords(words))
		{
			return words+"Z";
		}
		return words;
	}
	public static String decapitalize(String words)
	{
		String rt = Introspector.decapitalize(words);
		rt = convertKeyWords(rt);
		return rt;
	}
}
