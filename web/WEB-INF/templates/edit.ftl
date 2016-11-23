<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Edit profile</title>
</#macro>

<#macro page_body>
<form action="/edit_profile" method="post">
    <label>
        <input type="text" name="first_name"/>
    </label>
    <label>
        <input type="text" name="last_name"/>
    </label>
    <label>
        <input type="date" name="birth_date"/>
    </label>
    <label>
        <textarea name="interest"></textarea>
    </label>
    <input type="file" name="image"/>
    <label>
        <input type="password" name="password"/>
    </label>
    <label>
        <input type="password" name="password2"/>
    </label>
    <input type="submit"/>
</form>
</#macro>

<@display_page/>