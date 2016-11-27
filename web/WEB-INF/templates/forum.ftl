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
                <form class="form-inline" style="margin-top: 20px; margin-left: 20px">
                    <input type="text" class="form-control" placeholder="Section name" name="name">

                    <button type="submit" class=" btn btn-primary">Search</button>
                </form>
            </div>
        </div>

        <div class="row">
            <div class="panel" id="list">
                <div class="panel-group">
                    <#if sections??>
                <ul>
                    <#list sections as g>
                    <div class="panel panel-default" style="margin: 10px">
                        <div class="panel-body">
                            <p class="lead"><a href="/forum/section?id=${g.get("id")}">${g.get("name")}</a></p>
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