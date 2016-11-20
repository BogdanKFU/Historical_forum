<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Person article</title>
</#macro>

<#macro page_body>
<div class="row">
    <div class="content panel panel-default col-md-9">
        <div class="panel-group">

            <div class="col-md-8">
                <h3>${article.get("name")}</h3>
                <#if article.get("birth_date")??>
                    <h3>Дата рождения:
                        <small>${article.get("birth_date")}</small>
                    </h3>
                </#if>
                <#if article.get("dead_date")??>
                    <h3>Дата смерти:
                        <small>${article.get("dead_date")}</small>
                    </h3>
                </#if>
            </div>
        </div>

        <div class="panel panel-default panel-group col-md-12">
            <h3>Биография:</h3>

            <p class="lead">${article.get("content")}</p>
        </div>

        <div class="panel panel-default panel-group col-md-12">
            <div class="panel panel-default" style="margin: 10px">
<ul>
    <#list comments as g>
                <div class="thumbnail col-md-2 col-sm-2"
                     style="margin-top: 20px; margin-left: 20px; margin-right: 20px">
                    <img src="#"/>
                </div>
                <div class="panel-body">
                    <h4><a href="/profile/?id=${g.get("relations").get("sender").get("id")}">${g.get("relations").get("sender").get("username")}</a></h4>

                    <p>${g.get("content")}</p>
                    <div class="likes col-md-12 lead">
                        <span class="glyphicon glyphicon-thumbs-up"></span>
                        <span class="glyphicon glyphicon-thumbs-down"></span><small>100500</small>
                    </div>
                </div>
    </#list>
</ul>
            </div>

            <form method="post" action="/articles/person" class="form-horizontal">
                <div class="form-group">
                    <input type="hidden" name="id" value="${article.get("id")}">
                    <div class="col-sm-2"><label for="content">Commentary</label></div>
                    <div class="col-sm-6">
                        <textarea maxlength="200" id="text" oninput="document.getElementById('p').innerHTML=200-document.getElementById('text').value.length+' charachters remaining.'" class="form-control" name="content"></textarea>
                        <p id="p"></p>
                    </div>
                </div>


                <div class="form-group" style="margin: 20px">
                    <div class="btn-group">
                        <button type="submit" class="btn btn-primary" name="send">Send</button>
                    </div>
                </div>

            </form>

        </div>
    </div>
</div>
</#macro>

<@display_page/>