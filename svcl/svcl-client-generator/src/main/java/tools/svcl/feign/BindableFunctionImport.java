package tools.svcl.feign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.sap.b1.svcl.metadata.edm.FunctionImport;
import com.sap.b1.svcl.metadata.edmx.Edmx;

public class BindableFunctionImport extends HashMap<String,List<FunctionImport>>
{
	private static final long serialVersionUID = 538643561745157251L;
	public BindableFunctionImport(Edmx edmx) 
	{
		List<FunctionImport> functions = edmx.getDataServices().getSchema().getEntityContainer().getFunctionImport();
		for(FunctionImport function:functions)
		{			
			if(!BooleanUtils.isTrue(function.isIsBindable()))
			{
				continue;
			}
			if(function.getParameter().size()!=1)
			{
				throw new RuntimeException("not supported");
			}
			String name = function.getParameter().get(0).getType().split("\\.")[1];
			if(get(name)==null)
			{
				put(name, new ArrayList<FunctionImport>());
			}
			List<FunctionImport> list = get(name);
			list.add(function);
		}
	}

}
