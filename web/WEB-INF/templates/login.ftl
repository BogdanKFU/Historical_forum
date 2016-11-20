<#ftl encoding="utf-8"/>
<#include "base.ftl">
<#macro page_head>
<title>Sign in</title>
</#macro>

<#macro page_body>
<div class="row">

    <div class="content panel panel-default col-md-9">
        <div class="page-heading">
            <h1>Login</h1>
        </div>
        <form method="post" action="/auth/login" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2"><label for="username">Username</label></div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="username" placeholder="Login">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2"><label for="password">Password</label></div>
                <div class="col-sm-6">
                    <input type="password" class="form-control" name="password" placeholder="password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember"> Remember me
                        </label>
                    </div>
                </div>
            </div>


            <div class="form-group">
                <div class="col-sm-2"></div>
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-primary" name="send">Send</button>
                </div>
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-primary" name="send">Forgot password?</button>
                </div>
            </div>

        </form>
    </div>
</div>
${error}
</#macro>

<@display_page/>