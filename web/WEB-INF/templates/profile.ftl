<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
<div class="row">
    <div class="content panel panel-default col-md-9">
        <div class="panel-group">
            <div class="col-md-3">
                <div class="thumbnail" style="margin-top: 20px">

                </div>
            </div>

            <div class="col-md-6">
                <h1>
                    Логин:
                    <small>${user.get("username")}</small>
                </h1>
                <h1>Почта:
                    <small>${user.get("email")}</small>
                </h1>
        <#if user.get("first_name")??>
                <h1>Имя:
                    <small>${user.get("first_name")}</small>
                </h1>
        </#if>
        <#if user.get("last_name")??>
                <h1>Фамилия:
                    <small>${user.get("last_name")}</small>
                </h1>
        </#if>
                <h1>Дата рождения:
                    <small>${user.get("birth_date")}</small>
                </h1>
            </div>
        </div>
        <div class="col-md-3">
            <a href="/edit_profile"><button class="btn btn-primary">Редактировать</button></a>
        </div>
    <#if user.get("interest")??>
        <div class="panel-group col-md-10">
            <h1>Интересы:</h1>
            <p class="lead">${user.get("interest")}</p>
        </div>
    </#if>
    </div>
</div>
</#macro>

<@display_page/>