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
            <h5 class="text-center login-name">课程签到与评价系统</h5>
            <form class="form-signin ">
                <div class="form-group login-height">
                    <label for="loginName" class="col-sm-3 login-text control-label">用户名：</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control input3 m-input" id="loginName" placeholder="请输入用户名称">
                    </div>
                </div>
                <div class="form-group login-height">
                    <label for="password" class="col-sm-3 login-text control-label">密&nbsp;&nbsp;&nbsp;码：</label>
                    <div class="col-sm-7">
                        <input type="password" class="form-control input3 m-input" id="password" placeholder="******">
                    </div>
                </div>
                <div class="col-sm-offset-3 col-sm-7 remember">
                    <%--<input type="checkbox" value="remember-me" > 记得我--%>
                    <span id="message" style="display: none;color:red">  </span>
                </div>
                <div class="col-sm-offset-3 col-sm-7">
                    <a href="javascript:void(0);" type="button" class="btn btn-warning">登录</a>
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

//        $("#kaptcha").click(function(){
//            $(this).attr("src", "page/kaptcha?time="+new Date());
//            $("#jcaptcha").val("").focus();
//        });

        $(document).keydown(function(event){
            if(event.keyCode ==13){
                sbmForm();
            }
        });
    });

    function sbmForm(){
        var loginName=$("#loginName").val();
        var password=$("#password").val();
//        var jcaptcha=$("#jcaptcha").val();
        var prts =  $("#message");
        if(loginName==""||loginName==null){
            $('#loginName').css('borderColor', 'red').focus();
            return;
        }
        if(password=="" || password==null){
            $('#password').css('borderColor', 'red').focus();
            return;
        }
//        if(jcaptcha=="" || jcaptcha==null){
//            $('#jcaptcha').css('borderColor', 'red').focus();
//            return;
//        }
        password=hashCode(password);

        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/web/login",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({"loginName":loginName,"password":password}),
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