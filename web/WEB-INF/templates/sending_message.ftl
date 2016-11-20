<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Создать раздел</title>
</#macro>

<#macro page_body>
<form action="/admin/create_section" method="post">
    <label>
        <input type="text" name="username"/>
    </label>
    <label>
        <input type="text" name="topic"/>
    </label>
    <textarea name="content"></textarea>
    <input type="submit"/>
</form>
</#macro>

<@display_page/>