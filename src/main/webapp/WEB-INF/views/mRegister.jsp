<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册</title>
    <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="assets/css/mLogin.css" rel="stylesheet" />
    <!-- My styles -->
</head>
<body>
<div class="container contaner-l">
    <form class="form-signin login-c ">
        <h4 class="form-signin-heading text-center">学生注册</h4>

        <div class="input-m">
            <label class="sr-only">登录名</label>
            <input type="text" class="form-control username-field" placeholder="登录名" id="loginName">
        </div>
        <div class="input-m">
            <label class="sr-only">姓名</label>
            <input type="text" class="form-control username-field" placeholder="姓名" id="nickName">
        </div>
        <div class="input-m">
            <label class="sr-only">密码</label>
            <input type="password"  class="form-control username-password" placeholder="密&nbsp;&nbsp;&nbsp;&nbsp;码" id="password">
        </div>
        <div class="input-m">
            <label class="sr-only">重复</label>
            <input type="password" class="form-control username-password" placeholder="确认密码" id="confirm">
        </div>
        <div class="input-m">
            <label class="sr-only">学号</label>
            <input type="text" class="form-control username-field" placeholder="学号" id="idNo">
        </div>
        <div class="input-m">
            <input type="text" class="form-control yzm-w" id="kaptcha" placeholder="验证码">&nbsp;
            <img class="yzm" style="" src="web/kaptcha" title="看不清，点击换一张" tabindex="6">
            <div class="clearfix"></div>
            &nbsp;<span id="message" style="color: green"></span>
        </div>
        <div class="input-m">
            <a class="btn btn-lg btn-info btn-block" type="submit" href="javascript:void(0);">注册</a>
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

        $(".btn-info").click(function(){
            sbmForm();
        });

        $(document).keydown(function(event){
            if(event.keyCode ==13){
                sbmForm();
            }
        });

        $(".yzm").click(function(){
            $(this).attr("src", "web/kaptcha?time="+new Date());
            $("#kaptcha").val("").focus();
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
                    $("#message").text(msg.resultInfo).css("color", "red");
                    blink($("#message"));
                }else{
                    $("#message").text("注册成功！请使用新账号登录系统").css("color", "green");
                    setTimeout('window.location.href= "web/login"', 3000);
                }
            },
            error:function(msg){
                alert("服务器内部错误！");
            }

        });
    }

    var i = 6;
    function blink(selector){
        if(i>0) {
            $(selector).fadeOut('slow', function(){
                $(this).fadeIn('slow', function () {
                    blink(this);
                });
            });
            i--;
        } else {
            $(selector).fadeOut('slow');
            i = 6;
        }
    }
</script>