<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
<div class="row ">
    <div class="content panel panel-default">

        <div class="row">
            <div class="col-md-9" style="margin-left: 20px; margin-right: 20px">
                <h1>${topic.get("name")}</h1>
            </div>
            <div class="col-md-2" style="margin-top: 20px">
                <form>
                    <div class="form-group col-md-2">
                        <button class="btn btn-default">Close theme</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-1">
                <small>Topic is closed</small>
            </div>
        </div>
        <div class="row">
            <div class="panel" id="list">
                <div class="panel-group">
                    <div class="panel panel-default" style="margin: 10px">
                        <div class="panel-body">
                            <p>${topic.get("content")}</p>
                        </div>
                    </div>
<ul>
    <#list comments as g>
                    <div class="panel panel-default" style="margin: 10px">
                        <div class="thumbnail col-md-2 col-sm-2"
                             style="margin-top: 20px; margin-left: 20px; margin-right: 20px">
                            <img src="http://lorempixel.com/64/64/?6"/>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-2">
                                    <p class="lead">${g.get("relations").get("sender").get("username")}</p>
                                </div>
                            </div>

                            <p>${g.get("content")}</p>

                            <div class="likes col-md-12 lead">
                                <span class="glyphicon glyphicon-thumbs-up"></span>
                                <span class="glyphicon glyphicon-thumbs-down"></span>
                                <small>100500</small>
                            </div>
                        </div>
                    </div>
    </#list>
</ul>
                </div>
                <#if current_user??>
                <form method="post" action="/forum/section/topic" class="form-horizontal">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${topic.get("id")}"
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
</#if>
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