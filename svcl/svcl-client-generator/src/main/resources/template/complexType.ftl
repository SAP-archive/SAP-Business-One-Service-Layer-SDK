package com.sap.b1.svcl.client.complextype;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.*;
import com.sap.b1.svcl.client.enums.*;
import com.sap.b1.svcl.client.utils.UserFieldsSupport;
import java.time.LocalTime;
import java.time.LocalDate;

<#assign ODataUtil=statics['tools.svcl.feign.ODataUtil']>

@SuppressWarnings("unused")
public class ${data.name} extends UserFieldsSupport
{
    <#list data.property as prop>
    
    <#assign x = "">
    <#assign writeable = false>
   	<#if prop.isWritable()??>
   		<#if prop.isWritable()>
   			<#assign writeable = true>
	   	<#else>
	   		<#assign x = ", access = Access.WRITE_ONLY">
   		</#if>
   	<#else>
   	</#if>
   	<#assign type=ODataUtil.convertODataType(prop.type)>
	<#assign lowerName=ODataUtil.decapitalize(prop.name)>
	<#assign upperName=prop.name?cap_first>
   	@JsonProperty(value="${prop.name}"${x})   	
	protected ${type} ${lowerName};
	/**
 	 * Getter<p>
	<@propComments title="Getter" prop=prop writeable=writeable/>
 	 * @return field "${prop.name}"
 	 */
	public ${type} get${upperName}() {return ${lowerName};}
	/**
 	 * Setter<p>
	<@propComments title="Getter" prop=prop writeable=writeable/>	
 	 * @param val the value to set
 	 */
	public void set${upperName}(${type} val) {${lowerName}=val;}
    
    </#list>
}
<#macro propComments title prop writeable>
 	 * ${title}<p>
 	 * <strong>OData Infromation</strong><p>
 	 * Name: "${prop.name}"<br>
 	 * Type: ${prop.type}<br>
 	 * Writable: ${writeable?c}<br>
 	 * Nullable: ${prop.nullable?c}<p> 	 
 	 * <strong>Database</strong><p> 	 
 	 * Name: "${prop.alias!}"<p>
</#macro>