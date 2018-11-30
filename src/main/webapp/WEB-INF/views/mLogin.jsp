<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="assets/css/mLogin.css" rel="stylesheet" />
    <!-- My styles -->
</head>
<body>
<div class="container contaner-l">
    <form class="form-signin login-c ">
        <h4 class="form-signin-heading text-center">用户登录</h4>

        <div class="input-m">
            <label  class="sr-only">手机号</label>
            <input type="email" class="form-control username-field" placeholder="登录名" id="loginName">
        </div>
        <div class="input-m">
            <label class="sr-only">邮箱</label>
            <input type="password"  class="form-control username-password" placeholder="密&nbsp;&nbsp;&nbsp;&nbsp;码" id="password">
        </div>
        <div class="input-m">
            <%--<input type="text" class="form-control yzm-w" id="exampleInputAmount" placeholder="验证码">--%>
            <%--<div class="pull-right send-m"><span>10978</span></div>--%>
            <%--<div class="clearfix"></div>--%>
            <input type="checkbox" id="rememberMe" /> 记得我
            <span id="message"></span>
        </div>
        <div class="input-m">
            <a class="btn btn-lg btn-info btn-block" type="submit" href="javascript:void(0);">登录</a>
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
    });

    function sbmForm(){
        var loginName=$("#loginName").val();
        var password=$("#password").val();
        var prts =  $("#message");
        if(loginName==""||loginName==null){
            $('#loginName').css('borderColor', 'red').focus();
            return;
        }
        if(password=="" || password==null){
            $('#password').css('borderColor', 'red').focus();
            return;
        }
        var rememberMe = $("#rememberMe").is(':checked');
        password=hashCode(password);

        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/web/login",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({"loginName":loginName,"password":password, "rememberMe":rememberMe}),
            dataType: 'json',
            async: false,
            success:function(msg){
                if(msg.result=="error"){
                    errorInfo(msg.resultInfo, $("#message"));
                }else{
                    window.location.href= genUrl(msg.resultInfo);
                }
            }

        })
    }
</script>