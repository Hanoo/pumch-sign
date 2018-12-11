<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="">
<head>
    <base href="<%=request.getContextPath()%>/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>问卷</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/mobile.css">
</head>
<body>
<div class="head">
    <div class="text-center logo">反馈调查</div>
    <div class="pull-right head-nav">
        <a type="button" href="javascript:history.back()">返回</a>
    </div>
</div>

<div class="container-fluid ">

    <h4 class="text-center">${signIn.courseName}(${tName})</h4>
    <div class="panel-body">
        <span class=" star" aria-hidden="true">*</span><span>1、课程总体质量</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score1" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score1" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score1" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score1" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score1" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class="star" aria-hidden="true">*</span><span>2、课前对授课内容的掌握程度</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score2" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score2" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score2" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score2" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score2" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class=" star" aria-hidden="true">*</span><span>3、课后对授课内容的掌握程度</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score3" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score3" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score3" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score3" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score3" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class=" star" aria-hidden="true">*</span><span>4、课程对临床工作的帮助</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score4" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score4" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score4" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score4" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score4" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class="star" aria-hidden="true">*</span><span>5、您觉得教师准备是否充分(不充分到充分1-5分)</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score5" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score5" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score5" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score5" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score5" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class="star" aria-hidden="true">*</span><span>6、教师准备教材PPT是否重点突出，安排得当</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score6" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score6" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score6" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score6" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score6" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class="star" aria-hidden="true">*</span><span>7、教师讲课的语音、语调、语速适中，讲课生动，容易理解</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score7" value="1" checked="">
                        1
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score7" value="2">
                        2
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score7" value="3">
                        3
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score7" value="4">
                        4
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score7" value="5">
                        5
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="panel-body">
        <span class="star" aria-hidden="true">*</span><span>8、我愿意参加该讲师主讲的课程</span>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score8" value="1" checked="">
                        愿意
                    </label>
                </div>
            </li>
            <li class="list-group-item">
                <div class="radio">
                    <label>
                        <input type="radio" name="score8" value="0">
                        不愿意
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="text-center wen-tj">
        <a class="btn btn-primary btn-lg btn-block btn-tj">提交</a>
    </div>
</div>
</body>
</html>
<script src="assets/js/jquery-1.11.1.min.js"></script>
<script src="assets/js/common.js"></script>
<script>
    $(document).ready(function () {
        var signIn = JSON.parse('${signIn}');
        for(var key in signIn) {
            var pageEle = $("input[name='"+key+"']");
            if(pageEle) {
                pageEle.each(function (i, e) {
                    if(signIn[key]==$(e).val()) {
                        $(e).attr("checked", true);
                    }
                });
            }
        }

        var signInId = ${signIn.id};
        $(".btn-primary").on("click", function() {
            var scores = []
            for(var i=1;i<=8;i++) {
                scores.push(parseInt($("input[name='score"+i+"']:checked").val()));
            }

            $.ajax({
                type: 'post', // 提交方式 get/post
                url: '${pageContext.request.contextPath}/s/score',
                contentType: 'application/json;charset=UTF-8',
                data:JSON.stringify({"signInId":signInId, "scores":scores}),
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
        });
    });
</script>