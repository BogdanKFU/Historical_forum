<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Все личности</title>
</#macro>
<#macro page_body>
<div class="row ">
    <div class="content panel panel-default">
        <div class="row">
            <div class="col-md-10">
                <form class="form-inline" style="margin-top: 20px; margin-left: 20px">
                    <input type="text" class="form-control " placeholder="Name" name="name">

                    <div class="btn-group">
                        <button type="button" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
                            Profession
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="#">Prof 1</a></li>
                            <li><a href="#">Prof 2</a></li>
                        </ul>
                    </div>
                    <input type="date" class="form-control" placeholder="First date" name="firstdate">
                    <input type="date" class="form-control" placeholder="Last date" name="lastdate">

                    <button type="submit" class=" btn btn-primary" name="search">Search</button>
                </form>
            </div>
            <div class="col-md-2" style="margin-top: 19px">
                <a href="/articles/persons/create_new" class="btn btn-primary">Create person</a>
            </div>
        </div>
        <div class="row">
            <div class="panel" id="list">
                <div class="panel-group">
<ul>
    <#list articles as f>
                    <div class="panel panel-default" style="margin: 10px">
                        <div class="panel-body">
                            <p class="lead"><a href="/articles/person?id=${f.get("id")}">${f.get("name")}</a></p>

                            <p>${f.get("content")}</p>
                        </div>
                    </div>
    </#list>
</ul>
                    </div>
                </div>

                <ul class="pagination" style="margin-left: 20px">
                    <li><a href="#">&laquo;</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">&raquo;</a></li>
                </ul>
            </div>
        </div>
    </div>
</#macro>

<@display_page/>