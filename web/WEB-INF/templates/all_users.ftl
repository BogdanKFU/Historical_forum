<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Все пользователи</title>
</#macro>
<#macro page_body>
<script type="application/javascript" src="js/jquery-3.1.1.js"></script>

<script type="application/javascript">
    var f = function () {
        $.ajax({
            'url': '/search_user',
            'data': {
                'q': $("#q").val()
            },
            'method': 'get',
            'success': function (obj) {
                $("#res").html(obj.result.join("<br>"));
            }
        });
    }
</script>

<div class="row ">
    <div class="content panel panel-default">
        <label>
            <input class="form-control input-sm " placeholder="Name" name="name" id="q" type="text" oninput="f()">
        </label>
        <p id="res"></p>
        <div class="row">
            <div class="panel" id="list">
                <div class="panel-group">
                <ul>
                    <#list users as f>
                    <div class="panel panel-default" style="margin: 10px">
                        <div class="thumbnail col-md-2 col-sm-2"
                             style="margin-top: 20px; margin-left: 20px; margin-right: 20px">
                        </div>
                        <div class="panel-body">
                            <p><a href="/profile/?id=${f.get("id")}">${f.get("username")}</a></p>
                        </div>
                    </div>
                    </#list>
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