<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zxx">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>遇到问题了</title>
    <link rel="stylesheet" href="assets/css/error.css"/>
</head>
<body>
<div class="page404">
    <div class="content-sticky">
        <div class="wrap-error">
            <div class="error clearfix">
                <div class="error__left">
                    <p class="error__text">${eCode}</p>
                </div>
                <div class="error__right">
                    <div class="error__head"><c:if test="${empty message}">系统出故障了...</c:if><c:if test="${!empty message}">${message}</c:if></div>
                    <p class="error__text">很抱歉，没有找到您需要的页面。请您返回首页，谢谢！</p>
                    <div class="error__list">
                        <a href="web/login" class="link">返回首页</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>