<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
<a href="/logout">Выход</a>
${article.get("name")}
${article.get("content")}
${article.get("begin_date")}
${article.get("end_date")}
<ul>
    <#list comments as g>
        <li><p>${g.get("content")} ${g.get("relations").get("sender").get("username")}</p></li>
    </#list>
</ul>
<#if current_user??>
<form action="/articles/event" method="post">
    <input type="hidden" name="id" value="${article.get("id")}"
    <label>
        <textarea name="content"></textarea>
    </label>
    <input type="submit"/>
</form>
</#if>
</#macro>

<@display_page/>