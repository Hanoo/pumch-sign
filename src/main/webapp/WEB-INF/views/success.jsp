<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>成功</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <!-- My styles -->
    <style>
        .contaner-l{
            margin-top: 25%;
        }

    </style>
</head>
<body>
<div class="container contaner-l">
    <div class="text-center">
        <img src="assets/image/ok.png" width="5%">
        <h4>操作成功！</h4>
    </div>
</div>

</body>
</html>
<script>
    setTimeout("window.location.href='${pageContext.request.contextPath}/s/mySignList'", 3000);
</script>