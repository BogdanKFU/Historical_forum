<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
Hello! It is a profile page!
<a href="/logout">Выход</a>
${user.get("username")}
${user.get("first_name")}
${user.get("last_name")}
${user.get("birth_date")}
${user.get("email")}
<form action="/profile/ban/" method="post">
    <input type="hidden" name="id" value="${user.get("id")}">
    <input type="submit" value="Забанить">
</form>
</#macro>

<@display_page/>