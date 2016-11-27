<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Profile</title>
</#macro>

<#macro page_body>
<div class="row">
    <div class="content panel panel-default col-md-9">
        <div class="page-heading">
            <h1>Registration</h1>
        </div>
        <form method="post" action="/auth/sign_up" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2"><label for="email">Email</label></div>
                <div class="col-sm-6">
                    <input type="email" class="form-control" name="email" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="login">Username</label></div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="login" placeholder="Login">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="password">Password</label></div>
                <div class="col-sm-6">
                    <input type="password" class="form-control" name="password" placeholder="password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="name">Name</label></div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="name" placeholder="Name">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="surname">Second name</label></div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="surname" placeholder="Second name">
                </div>
            </div>
                <div class="form-group">
                    <div class="col-sm-2"><label for="birth_date">Введите дату:</label></div>
                    <div class="col-sm-6"><input type="date" class="form-control" name="birth_date"></div>
                </div>


            <div class="form-group">
                <div class="col-sm-2"></div>
                <button type="submit" class="btn btn-primary" name="send">Submit</button>
            </div>
        </form>
        <#if error??>
            ${error}
        </#if>
    </div>
</div>
</#macro>

<@display_page/>