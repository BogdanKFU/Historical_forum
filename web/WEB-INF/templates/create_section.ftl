<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Создать раздел</title>
</#macro>

<#macro page_body>
<div class="row">

    <div class="content panel panel-default col-md-9">
        <div class="page-heading">
            <h1>Section creation</h1>
        </div>
        <div class="alert alert-danger" role="alert">
            <strong>Error: </strong>Some error
        </div>
        <form method="post" action="/admin/create_section" class="form-horizontal">
            <div class="form-group">
                <div class="col-md-2"><label for="name" class="col-sm-4">Section name</label></div>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="name" placeholder="Section name">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-md-2">
                    <input type="submit" class="btn btn-primary" value="Create">
                </div>
            </div>

        </form>

    </div>
</div>
</#macro>

<@display_page/>