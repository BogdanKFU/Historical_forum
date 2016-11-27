<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Admin - Edit model</title>
</#macro>

<#macro page_body>
<form method="post" action="/admin/entities/edit">
    <input type="hidden" value="${model}" name="model">
    <input type="hidden" value="${object.get("id")}" name="id">
    <#list fields as f>
    <#if object.get(f.getName())??>
        <#if f.getType().getSimpleName().equals("String") && !f.getName().equals("password")>
            <label>
                <input type="text" name="${f.getName()}" value="${object.get(f.getName())}" placeholder="${f.getName()}">
            </label>
        <#elseif f.getType().getSimpleName().equals("Date")>
            <label>
                <input type="date" name="${f.getName()}" value="${object.get(f.getName())?iso_utc}" placeholder="${f.getName()}">
            </label>
        <#elseif f.getType().getSimpleName().equals("Time")>
            <label>
                <input type="text" pattern="([0-1]{1}[0-9]{1}|20|21|22|23):[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}" name="${f.getName()}" value="${object.get(f.getName())?time}" placeholder="${f.getName()}"/>
            </label>
        <#elseif f.getType().getSimpleName().equals("String") && f.getName().equals("password")>
            <label>
                <input type="password" name="${f.getName()}" placeholder="${f.getName()}">
            </label>
        <#elseif f.getType().getSimpleName().equals("boolean")>
            <label>
                <input type="checkbox" name="${f.getName()}" placeholder="${f.getName()}" <#if object.get(f.getName()).equals(true)>checked="checked"</#if>>
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
                <input type="number" min="0" step="1" name="${f.getName()}" placeholder="${f.getName()}" value="${object.get(f.getName())?int}">
            </label>
        </#if>
    <#else>
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
                <input type="text" pattern="([0-1]{1}[0-9]{1}|20|21|22|23):[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}" name="${f.getName()}" placeholder="${f.getName()}" />
            </label>
        <#elseif f.getType().getSimpleName().equals("String") && f.getName().equals("password")>
            <label>
                <input type="password" name="${f.getName()}" placeholder="${f.getName()}">
            </label>
        <#elseif f.getType().getSimpleName().equals("boolean")>
            <label>
                <input type="radio" name="${f.getName()}" placeholder="${f.getName()}">
            </label>
        <#elseif fk?? && fk.get(f.getName())??>
            <label>
                <select name="${f.getName()}">
                    <#list fk.get(f.getName()) as o>
                        <option value="${o.get("id")}">${o.get("username")}</option>
                    </#list>
                </select>
            </label>
        </#if>
        </#if>
    </#list>
    <input type="submit">
</form>
</#macro>

<@display_page/>