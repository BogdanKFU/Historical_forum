<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Создать раздел</title>
</#macro>

<#macro page_body>
<div class="row">

    <div class="content panel panel-default col-md-9">
        <div class="page-heading">
            <h1>Создать тему</h1>
        </div>
        <div class="alert alert-danger" role="alert">
            <strong>Ошибка </strong>Какая-то ошибка
        </div>
        <form method="post" action="/forum/section/create_topic" class="form-horizontal">
            <input type="hidden" name="id" value="${id_section}">
            <div class="form-group">
                <div class="col-md-2"><label for="name" class="col-sm-4">Название</label></div>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="name" placeholder="Theme name">
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-2"><label for="post-text" class="col-sm-4">Текст</label></div>
                <div class="col-sm-8"><textarea class="form-control" name="content" id="post-text"></textarea></div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-md-2">
                    <input type="submit" class="btn btn-primary" value="Создать">
                </div>
            </div>

        </form>

    </div>
</div>
</#macro>

<@display_page/>