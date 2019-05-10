package tools.svcl.feign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.sap.b1.svcl.metadata.edm.FunctionImport;
import com.sap.b1.svcl.metadata.edmx.Edmx;

public class FunctionManager extends HashMap<String,List<FunctionImport>>
{
	private static final long serialVersionUID = -6891961514789630494L;

	public FunctionManager(Edmx edmx) 
	{
		List<FunctionImport> functions = edmx.getDataServices().getSchema().getEntityContainer().getFunctionImport();
		for(FunctionImport function:functions)
		{			
			if(BooleanUtils.isTrue(function.isIsBindable()))
			{
				continue;
			}
			String[] names = function.getName().split("_");
			String name = names[0];
			if(get(name)==null)
			{
				put(name, new ArrayList<FunctionImport>());
			}
			List<FunctionImport> list = get(name);
			list.add(function);
		}
	}
}
