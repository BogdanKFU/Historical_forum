<#ftl encoding="utf-8"/>
<#macro page_head>
<title>Base page</title>
</#macro>
<#macro page_body>
<h1>Welcome!</h1>
</#macro>
<#macro display_page>
<!doctype html>
<html>
<head>
    <@page_head/>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <script type="application/javascript" src="../../js/jquery-3.1.1.js"></script>
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="stylesheet" href="../../css/bootstrap-theme-spacelab.min.css">
    <link rel="stylesheet" href="../../css/mycss.css">
    <script type="application/javascript" src="../../js/bootstrap.js"></script>
    <@page_head/>
</head>
<body>
<div class="content clearfix container-fluid" id="content">
    <div class="row text-center">
        <img src="/files/Logo.png">
    </div>
    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#">Main</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Posts<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="/articles/persons">Persons</a></li>
                            <li><a href="/articles/events">Events</a></li>
                        </ul>
                    </li>
                    <li><a href="/forum">Forum</a></li>
                    <li><a href="/get_user_list">Users</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/auth/login">Log in</a></li>
                    <li><a href="/auth/sign_up">Registration</a></li>
                    <li class="dropdown">
                        <a href="/profile" class="dropdown-toggle" data-toggle="dropdown">Profile<b
                                class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="/profile">Private page</a></li>
                            <li><a href="/logout">Log out</a></li>
                        </ul>
                    </li>
                </ul>

            </div>
        </div>
    </div>
    <@page_body/>
    <div class="footer">
        <p class="text-muted">Copyright (c) 2016</p>
    </div>
</div>
</body>
</html>
</#macro>