<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>签到成功</title>
</head>
<body>
<h3>
    签到成功！
</h3>
</body>
</html>
<script>
    setTimeout("window.location.href='${pageContext.request.contextPath}/s/mySignList'", 3000);
</script>