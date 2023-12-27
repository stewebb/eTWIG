<#assign trace = "null">
<#assign exception = "null">

<#if error.trace?has_content>
	<#assign trace = error.trace?replace("\t", " ")>
	<#assign trace = trace?replace("\n", " ")>
	<#assign exception = trace?split(" at ")>
</#if>

{
	"status": ${error.status},
	"error": "${error.error}",
    "path": <#if path?has_content>"${path}"<#else>null</#if>,
    "message": <#if error.message?has_content>"${error.message}"<#else>null</#if>,
    "exception": <#if exception?size gt 0>"${exception[0]}"</#if>,
    "trace": "${trace}"
}