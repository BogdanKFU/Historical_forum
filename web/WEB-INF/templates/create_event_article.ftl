<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Создать раздел</title>
</#macro>

<#macro page_body>
<div class="row">

    <div class="content panel panel-default col-md-9">
        <div class="page-heading">
            <h1>Создать статью об историческом событии</h1>
        </div>
        <div class="alert alert-danger" role="alert">
            <strong>Ошибка </strong>Какая-то ошибка
        </div>
        <form method="post" action="/articles/events/create_new" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2"><label for="name">Название</label></div>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="name" placeholder="Title">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="content">Текст</label></div>
                <div class="col-sm-9">
                    <textarea name="content" class="form-control"></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="begin_date">Дата начала</label></div>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="begin_date">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="end_date">Дата конца</label></div>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="end_date">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-2 col-md-offset-2">
                    <button type="submit" class="btn btn-primary " name="send">Создать</button>
                </div>
            </div>

        </form>

    </div>
</div>
</#macro>

<@display_page/>