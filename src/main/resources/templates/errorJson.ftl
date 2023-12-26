<#assign trace = "null">
<#if error.trace?has_content>
	<#assign trace = error.trace?replace("\t", " ")>
	<#assign trace = trace?replace("\n", " ")>
</#if>

{
	"status": ${error.status},
	"error": "${error.error}",
    "path": <#if path?has_content>"${path}"<#else>null</#if>,
    "message": <#if error.message?has_content>"${error.message}"<#else>null</#if>,
    "trace": "${trace}"
}