package com.sap.b1.svcl.client.functionimport;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import feign.Param;
import feign.RequestLine;
import feign.Headers;
import com.sap.b1.svcl.client.entitytype.*;
import com.sap.b1.svcl.client.complextype.*;
import com.sap.b1.svcl.client.utils.ODataList;

<#assign ODataUtil=statics['tools.svcl.feign.ODataUtil']>
<#assign Introspector=statics['java.beans.Introspector']>
<#assign WordUtils=statics['org.apache.commons.lang3.text.WordUtils']>

@SuppressWarnings("unused")
@Headers("Content-Type: application/json; charset=utf-8")
public interface ${name}
{
    <#list function as function>
    <#assign functionName=ODataUtil.convertKeyWords(WordUtils.uncapitalize(function.name?split("_")?last))>
    	
	<#if function.parameter?has_content>
	class ${function.name}Param
	{
		<#list function.parameter as param>
		<#assign paramType = ODataUtil.convertODataType(param.type)>
		<#assign paramName = Introspector.decapitalize(param.name)>
		@JsonProperty("${param.name}")
		${paramType} ${paramName};
		public ${paramType} get${param.name}() { return ${paramName}; }
		public void set${param.name}(${paramType} val) { ${paramName}=val; }
		</#list>
	}
    </#if>
    	
	<#assign returnType = ODataUtil.convertODataType(function.returnType)>
	@RequestLine("POST /${function.name}")
	<#if returnType?starts_with("List<")>OData</#if>${returnType} ${functionName}(<#if function.parameter?has_content>${function.name}Param param</#if>);
		
    </#list>
}
