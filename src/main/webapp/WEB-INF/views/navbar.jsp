<%@ page language="java" pageEncoding="UTF-8"%>
<nav class="navbar navbj">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <h5 class="navbar-brand login-nav" ><a href="/"><img class="menu-icon" src="assets/image/logo.png" width="24">课程签到后台管理系统</a></h5>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="sys-a"><span><img src="assets/image/prf.png">${userInfo.nickName}</span></li>
                <li><a  class="list" href="logout">退出</a></li>
            </ul>
        </div>
    </div>
</nav>