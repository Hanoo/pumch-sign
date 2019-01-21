<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>课程签到后台管理系统</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/main.css"/>
</head>
<body ryt13519="1">

<nav class="navbar  navbj">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <h5 class="navbar-brand login-nav" ><a href="index0.html"><img class="menu-icon" src="assets/image/logo.png" width="24">课程签到后台管理系统</a></h5>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="sys-a"><span><img src="assets/image/tx2.jpg">${userInfo.nickName}</span></li>
                <li><a class="list" href="logout">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <%@ include file="nav-sidebar.jsp"  %>
        <div class="main">
            <ol class="breadcrumb">
                <li><a href="javascript:void(0);">首页</a></li>
                <li><a href="javascript:void(0);">添加课程</a></li>
            </ol>
            <h1 class="pull-left">创建用户</h1>

            <hr class="clearfix">
            <form class="form-horizontal cont" id="mainForm">
                <div class="form-group">
                    <label  class="col-sm-5 control-label text-right text-list">课程名称：</label>
                    <div class="col-sm-7">
                        <input type="text" maxlength="30" class="form-control input2" placeholder="" id="courseName">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-5 control-label text-right text-list">课程类型：</label>
                    <div class="col-sm-7">
                        <select class="form-control input2" id="courseType">
                            <option>请选择</option>
                            <option value="1">必修</option>
                            <option value="0">选修</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-5 control-label text-right text-list">任课教师：</label>
                    <div class="col-sm-7">
                        <select class="form-control input2" id="tId">
                            <option>请选择</option>
                        </select>
                    </div>
                </div>
                <hr>
                <div class="form-group cont">
                    <label  class="col-sm-5 control-label"></label>
                    <div class="col-sm-7">
                        <button type="button" class="btn btn-default save">保存</button>
                        <button type="reset" class="btn btn-default reset">重置</button>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
<script src="assets/js/jquery-1.11.1.min.js"></script>
<script src="assets/js/common.js"></script>
<script>
    $(document).ready(function(){
        var dataList = ${data};
        for(var i=0;i<dataList.length;i++) {
            var user = dataList[i];
            var html = "<option value=" + user.id + ">"+user.nickName+"</option>";
            $("#tId").append(html);
        }

        $(".save").on("click", function() {
            var tId = $("#tId").val();
            if(tId=='请选择') {
                alert("请选择任课教师！");
                return false;
            }
            var courseName = $("#courseName").val();
            if(!courseName) {
                alert("请填写课程名称");
                return false;
            }
            var courseType = $("#courseType").val();
            if('请选择' == courseType) {
                alert("请选择课程类型");
                return false;
            }

            var queryParam = {"tId":tId, "courseName":courseName, "courseType":courseType};

            $.ajax({
                type: 'post',
                url: '${pageContext.request.contextPath}/mt/newCourse',
                contentType: 'application/json;charset=UTF-8',
                data:JSON.stringify(queryParam),
                dataType: "json",
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    if(data.result=='failed') {
                        alert("数据读取访问异常！");
                    } else if(data.result=='duplicateName') {
                        alert("课程名称重复，创建失败！");
                    } else {
                        alert("课程创建成功。");
                    }
                },
                error: function(data){
                    console.log(data.error);
                    alert("系统内部错误，请重试。");
                }
            });
        });
        
        $(".reset").on("click", function () {
            $("#mainForm").reset();
        });

        active();
    });
</script>

</body>
</html>