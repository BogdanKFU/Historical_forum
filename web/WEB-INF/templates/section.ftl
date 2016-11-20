<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Создать раздел</title>
</#macro>

<#macro page_body>
<div class="row ">
    <div class="content panel panel-default">
        <div class="row">
            <div class="col-md-4 col-sm-7">
                <form class="form-inline" style="margin-top: 20px; margin-left: 25px">
                    <input type="text" class="form-control" placeholder="Theme name" name="name">

                    <button type="submit" class=" btn btn-primary" name="search">Search</button>
                </form>
            </div>
            <div class="col-md-2 col-sm-2">
                <a href="/forum/section/create_topic?id=${id}"><button type="submit" class=" btn btn-primary" name="search">Create theme</button></a>
            </div>
        </div>

        <div class="row">
            <div class="panel" id="list">
                <div class="panel-group">
                    <ul>
                    <#if topics??>
                        <#list topics as g>
                    <div class="panel panel-default" style="margin: 10px">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-sm-12">
                                    <p class="lead"><a href="/forum/section/topic?id=${g.get("id")}">${g.get("name")}</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                        </#list>
                    </#if>
                    </ul>
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
</div>
</#macro>

<@display_page/>