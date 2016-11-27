<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
<form method="post" action="/admin/entities/create">
    <input type="hidden" value="${model}" name="model">
<#list fields as f>
    <#if f.getType().getSimpleName().equals("String") && !f.getName().equals("password")>
        <label>
            <input type="text" name="${f.getName()}" placeholder="${f.getName()}">
        </label>
    <#elseif f.getType().getSimpleName().equals("Date")>
        <label>
            <input type="date" name="${f.getName()}" placeholder="${f.getName()}">
        </label>
    <#elseif f.getType().getSimpleName().equals("Time")>
        <label>
            <input type="text" placeholder="${f.getName()}" pattern="([0-1]{1}[0-9]{1}|20|21|22|23):[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}" name="${f.getName()}" />
        </label>
    <#elseif f.getType().getSimpleName().equals("String") && f.getName().equals("password")>
        <label>
            <input type="password" name="${f.getName()}" placeholder="${f.getName()}">
        </label>
    <#elseif f.getType().getSimpleName().equals("boolean")>
        <label>
            <input type="checkbox" name="${f.getName()}" placeholder="${f.getName()}">
        </label>
    <#elseif fk?? && fk.get(f.getName())??>
        <label>
            <select name="${f.getName()}">
                <#list fk.get(f.getName()) as o>
                    <option value="${o.get("id")}">${o.get("username")}</option>
                </#list>
            </select>
        </label>
    <#else>
        <label>
            <input type="number" min="0" step="1" placeholder="${f.getName()}" name="${f.getName()}"">
        </label>
    </#if>
</#list>
    <input type="submit">
</form>
</#macro>

<@display_page/>