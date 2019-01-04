<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>课程签到</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/main.css"/>
</head>
<body class="bodybj">
<div class="container-fluid">
    <h5 class="text-center login-name">课程签到与评价系统注册</h5>
    <form class="form-reg ">
        <div class="form-group login-height">
            <label for="loginName" class="col-sm-3 login-text control-label">登录名：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input3 m-input" id="loginName" placeholder="请输入用户名称">
            </div>
        </div>
        <div class="form-group login-height">
            <label for="loginName" class="col-sm-3 login-text control-label">姓&nbsp;&nbsp;&nbsp;名：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input3 m-input" id="nickName" placeholder="请输入用户名称">
            </div>
        </div>
        <div class="form-group login-height">
            <label for="loginName" class="col-sm-3 login-text control-label">学&nbsp;&nbsp;&nbsp;号：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control input3 m-input" id="idNo" placeholder="请输入用户名称">
            </div>
        </div>
        <div class="form-group login-height">
            <label for="password" class="col-sm-3 login-text control-label">密&nbsp;&nbsp;&nbsp;码：</label>
            <div class="col-sm-7">
                <input type="password" class="form-control input3 m-input" id="password" placeholder="******">
            </div>
        </div>
        <div class="form-group login-height">
            <label for="password" class="col-sm-3 login-text control-label">确&nbsp;&nbsp;&nbsp;认：</label>
            <div class="col-sm-7">
                <input type="password" class="form-control input3 m-input" id="confirm" placeholder="******">
            </div>
        </div>
        <div class="form-group login-height">
            <label for="password" class="col-sm-3 login-text control-label">验证码：</label>
            <div class="col-sm-7">
                <input type="text" class="form-control m-input" id="kaptcha" placeholder="">
                <img class="yzm" style="" src="web/kaptcha" title="看不清，点击换一张" tabindex="6">
            </div>
        </div>
        <div class="col-sm-offset-3 col-sm-7">
            <a href="javascript:void(0);" type="button" class="btn btn-warning">注册</a>
            <a href="javascript:void(0);" type="button" class="btn btn-default">重置</a>
        </div>
    </form>
</div>
</body>
</html>
<script src="assets/bootstrap/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js" rel="script"></script>
<script src="assets/js/common.js"></script>
<script>
    $(document).ready(function(){

        $(".btn-warning").click(function(){
            sbmForm();
        });

        $(".yzm").click(function(){
            $(this).attr("src", "web/kaptcha?time="+new Date());
            $("#kaptcha").val("").focus();
        });

        $(document).keydown(function(event){
            if(event.keyCode ==13){
                sbmForm();
            }
        });
    });

    function sbmForm(){
        var loginName=$("#loginName").val();
        var nickName=$("#nickName").val();
        var password=$("#password").val();
        var kaptcha=$("#kaptcha").val();
        var confirm=$("#confirm").val();
        var idNo = $("#idNo").val();
        var prts =  $("#message");
        if(loginName==""||loginName==null){
            $('#loginName').css('borderColor', 'red').focus();
            return;
        }
        if(nickName==""||nickName==null){
            $('#nickName').css('borderColor', 'red').focus();
            return;
        }
        if(kaptcha=="" || kaptcha==null){
            $('#kaptcha').css('borderColor', 'red').focus();
            return;
        }
        if(password=="" || password==null){
            $('#password').css('borderColor', 'red').focus();
            return;
        }
        if(password!=confirm) {
            alert("两次输入的密码不一致！");
            return;
        }

        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/web/doReg",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({"loginName":loginName,"password":hashCode(password),"nickName":nickName, "kaptcha":kaptcha, "idNo":idNo}),
            dataType: 'json',
            async: false,
            success:function(msg){
                if(msg.result=="error"){
                    alert(msg.resultInfo);
                }else{
                    alert("注册成功！将转到登录界面");
                    window.location.href= "web/login";
                }
            },
            error:function(msg){
                alert("服务器内部错误！");
            }

        });
    }
</script>