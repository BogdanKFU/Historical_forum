<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Admin - Models</title>
</#macro>

<#macro page_body>
<a href="/admin/entities/create?model=${model}">Создать</a>
<div class="panel panel-default">
    <#if error??>${error}</#if>
    <table class="table">
        <thead>
        <tr>
            <th>Удалить</th>
            <th>Редактировать</th>
            <#if fields??>
            <#list fields as g>
                <th>${g.getName()}</th>
            </#list>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#if models??>
        <#list models as f>
            <tr>
                <#if fields??>
                    <td>
                        <form method="post" action="/admin/entities/delete">
                            <input type="hidden" value="${model}" name="model">
                            <input type="hidden" value="${f.get("id")}" name="id">
                            <input type="submit" value="Удалить">
                        </form>
                    </td>
                    <td><a href="/admin/entities/edit?model=${model}&id=${f.get("id")}">Редактировать</a></td>
                <#list fields as g>
                <#if f.get(g.getName())?is_boolean>
                    <td>${f.get(g.getName())?c}</td>
                <#elseif f.get(g.getName())?is_string || f.get(g.getName())?is_date>
                    <td>${f.get(g.getName())}</td>
                <#elseif f.get(g.getName())?is_nan>
                <td></td>
                <#else>
                <td>${f.get(g.getName())}</td>
                </#if>
                </#list>
                </#if>
            </tr>
        </#list>
        </#if>
        </tbody>
    </table>
</div>

</#macro>

<@display_page/>