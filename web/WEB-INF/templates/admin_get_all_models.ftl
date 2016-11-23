<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
<ul>
<li><a href="/admin/entities/?model=User">User</a></li>
<li><a href="/admin/entities/?model=TopicComment">TopicComment</a></li>
<li><a href="/admin/entities/?model=EventComment">EventComment</a></li>
<li><a href="/admin/entities/?model=PersonComment">PersonComment</a></li>
<li><a href="/admin/entities/?model=CustomCookie">CustomCookie</a></li>
<li><a href="/admin/entities/?model=PersonArticle">PersonArticle</a></li>
<li><a href="/admin/entities/?model=EventArticle">EventArticle</a></li>
<li><a href="/admin/entities/?model=Topic">Topic</a></li>
<li><a href="/admin/entities/?model=Section">Section</a></li>
<li><a href="/admin/entities/?model=Role">Role</a></li>
<li><a href="/admin/entities/?model=Message">Message</a></li>
</ul>
</#macro>

<@display_page/>