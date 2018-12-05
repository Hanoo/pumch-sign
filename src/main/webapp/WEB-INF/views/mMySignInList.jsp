<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/mobile.css">
    <title>我的签到</title>
</head>
<body class="bj">
    <div class="head">
        <div class="text-center logo">我的签到</div>
    </div>
    <div class="container-fluid">
        <div class="search">
            <input type="text" class="form-control" id="courseName" aria-describedby="inputSuccess2Status" placeholder="请输入搜索课程">
            <a class="btn btn-search">搜索</a>
        </div>
        <div class="con-list" id="container">
            <ul id="square"></ul>
            <div class="text-center more">
                <a type="text" href="javascript:void(0);" id="loadMore">加载更多</a>
            </div>
        </div>
    </div>
</body>
</html>
<script src="assets/bootstrap/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js" rel="script"></script>
<script src="assets/js/common.js"></script>
<script>
    var queryParam={
        "courseName":"",
        "startTime":"",
        "endTime":"",
        "pageSize":10,
        "currentPageIndex":1
    };
    $(document).ready(function() {
        loadNext(queryParam);

        $("body").delegate(".signRow",'click',function(){
            var signInId = $(this).attr("id");
            window.location = "s/score/"+signInId;
        });

        $("#loadMore").on("click", function(){
            $(this).html("加载中~");
            var pageCount = Math.ceil(queryParam.totalRecord/queryParam.pageSize);
            if(queryParam.currentPageIndex<pageCount){
                queryParam.currentPageIndex++;
                loadNext(queryParam);
                $(this).html("加载更多");
            } else {
                $(this).html("没有更多了").disable();
            }
        });
    });

    // 生成html内容
    function buildLi(data) {
        var square = $("#square");
        for(var i=0;i<data.length;i++) {
            var scoreTime = data[i].scoreTime;
            var displayTime;
            if(scoreTime) {
                displayTime = "评分时间:" + scoreTime;
            } else {
                displayTime = "签到时间:" + data[i].signInTime;
            }

            var score = data[i].score;
            var displayScore;
            if(score) {
                displayScore = score;
            } else {
                displayScore = "未评分";
            }
            var li = "<li><div class='pull-left text-list'><h5>课程名称：" +
                data[i].courseName + "</h5><p>" +
                displayTime + "</p></div>" +
                "<div class='pull-right text-nav signRow' id='" + data[i].id +
                "'><div class=\"pull-right\"><h6>评分</h6><p><span class=\"yellow\">" +
                displayScore + "</span></p>" +
                "</div></div><div class=\"clearfix\"></div></li>"
            square.append(li);
        }
    }

    // 访问数据借口加载数据
    function loadNext(queryParam) {
        $.ajax({
            type: 'post',
            url: '${pageContext.request.contextPath}/s/signList',
            contentType: 'application/json;charset=UTF-8',
            data:JSON.stringify(queryParam),
            dataType: "json",
            success: function(result) {
                if(result.error) {
                    alert("接口访问异常！");
                } else {
                    $.extend(queryParam, result);
                    buildLi(result.data);
                }
            },
            error: function(result){
                console.log(result.error);
                alert("系统内部错误，请重试。");
            }
        });
    }
</script>
