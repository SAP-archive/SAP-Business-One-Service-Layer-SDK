package com.sap.b1.svcl.client.entityset;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import com.sap.b1.svcl.client.entitytype.*;
import feign.Param;
import feign.RequestLine;
import feign.QueryMap;
import feign.Headers;
import com.sap.b1.svcl.client.utils.ODataList;
import com.sap.b1.svcl.client.utils.ODataKeyExpander;

<#assign ODataUtil=statics['tools.svcl.feign.ODataUtil']>

@SuppressWarnings("unused")
@Headers("Content-Type: application/json; charset=utf-8")
public interface ${data.name}
{
	<#assign type=ODataUtil.convertODataType(data.entityType)>
	<#assign idParams=ODataUtil.getIdParams(data, edmx)>
	<#assign idFormat=ODataUtil.getIdFormat(data, edmx)>

	@RequestLine("GET /${data.name}")
	ODataList<${type}> findAll();

	@RequestLine("GET /${data.name}?$filter={filter}")
	ODataList<${type}> find(@Param("filter") String filter);

	@RequestLine("GET /${data.name}")
	ODataList<${type}> find(@QueryMap Map<String, Object> params);


	@RequestLine("GET /${data.name}(${idFormat})")
	${type} get(${idParams});
	
	@RequestLine("POST /${data.name}")
	${type} create(${type} o);
	
	@RequestLine("PATCH /${data.name}(${idFormat})")
	void update(${idParams},${type} o);
	
	@RequestLine("DELETE /${data.name}(${idFormat})")
	void delete(${idParams});
	
	<#list function as function>
	@RequestLine("POST /${data.name}(${idFormat})/${function.name}")
	void ${ODataUtil.decapitalize(function.name)}(${idParams});
    </#list>
}

