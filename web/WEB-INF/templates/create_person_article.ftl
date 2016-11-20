<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Создать раздел</title>
</#macro>

<#macro page_body>
<div class="row">

    <div class="content panel panel-default col-md-9">
        <div class="page-heading">
            <h1>Создать статью об исторической личности</h1>
        </div>
        <div class="alert alert-danger" role="alert">
            <strong>Ошибка </strong>Какая-то ошибка
        </div>
        <form method="post" action="/articles/persons/create_new" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2"><label for="name">Имя</label></div>
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
                <div class="col-sm-2"><label for="birth_date">Дата рождения</label></div>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="birth_date">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="dead_date">Дата смерти</label></div>
                <div class="col-sm-3">
                    <input type="date" class="form-control" name="dead_date">
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