<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Входяшие</title>
</#macro>

<#macro page_body>
<ul>
    <#list sections as g>
        <li><p><a href="/forum/section?id=${g.get("id")}">${g.get("name")}</a></p></li>
    </#list>
</ul>
</#macro>

<@display_page/>