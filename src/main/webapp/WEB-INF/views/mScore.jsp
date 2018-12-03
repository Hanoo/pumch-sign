<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>评分</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/mobile.css">
</head>
<body>
<div class="head">
    <div class="text-center logo">课程评分</div>
    <div class="pull-right head-nav">
        <a type="button" href="javascript:history.back()">返回</a>
    </div>
</div>

<div class="container contaner-l text-center">
    <h3>${signIn.courseName}</h3>
    <p></p>
    <div class="text-center in-5">
        请评分：<input type="text" class="form-control in-5" placeholder="请输入1到10的整数" id="scorer">
    </div>
</div>
<div class=" text-center tj col-md-1">
    <a class="btn btn-primary btn-lg btn-block btn-tj">提交</a>
</div>

</body>
</html>
<script src="assets/js/jquery-1.11.1.min.js"></script>
<script src="assets/js/common.js"></script>
<script>
    var signInId = ${signIn.id};
    $(".btn-primary").on("click", function() {
        var score = $("#scorer").val();
        if(score=="请选择") {
            alert("请选择分数");
            return false;
        } else {
            var score = $("#scorer").val();
            if(!/^\d+$/.test(score)) {
                alert("不是整数");
                return false;
            } else if(score<0 || score>10) {
                alert("评分应大于1小于10！");
                return false;
            }
            $.ajax({
                type: 'post', // 提交方式 get/post
                url: '${pageContext.request.contextPath}/s/score',
                contentType: 'application/json;charset=UTF-8',
                data:JSON.stringify({"signInId":signInId, "score":score}),
                dataType: "json",
                success: function(data) {
                    if(data.error) {
                        alert("接口访问异常！");
                    } else {
                        window.location = 'success';
                    }
                },
                error: function(data){
                    console.log(data.error);
                    alert("系统内部错误，请重试。");
                }
            });
        }
    });
</script>