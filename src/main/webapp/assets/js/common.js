var basePath = $("base").attr("href");

function hashCode(strKey) {
    var hash = 0;
    if(!isNull(strKey)) {
        for (var i = 0; i < strKey.length; i++) {
            hash = hash * 31 + strKey.charCodeAt(i);
            hash = intValue(hash);
        }
    }
    return hash;
}
function isNull(str){
    return str == null || str == "";
}

function intValue(num) {
    var MAX_VALUE = 0x7fffffff;
    var MIN_VALUE = -0x80000000;
    if(num > MAX_VALUE || num < MIN_VALUE) {
        return num &= 0xFFFFFFFF;
    }
    return num;
}

function trim(str){
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
function checkSubmit(tel, mail, password, confirmPwd, catpcha){

    if(!checkTelephone(tel)){return}
    if(!checkisEmail(mail)){return}

    var messagePane = $("#message");
    if (password.length<=5 || password.length>=31 || password==""){

        errorInfo("密码长度必须在6-32个字符之间！", messagePane);
        $("#password").val("");
        $("#password").focus();
        return false;
    }
    if (password!=confirmPwd) {
        errorInfo("两次输入的密码不一致！", messagePane);
        return false;
    }
    if(!catpcha) {
        errorInfo("请输入验证码！", messagePane);
        $("#jcaptcha").focus();
        return false;
    }
    return true;
}
function checkTelephone(telephone) {

    //var telephone = document.getElementById("telephone").value;
    var re = /(^(\d{3,4}-)?\d{7,8})$|(^1[3|4|5|7|8][0-9]{9})/;

    if (!re.test(telephone)) {
        var messagePane = $("#message");
        errorInfo("手机格式错误！", messagePane);
        $("#tel").val("");
        $("#tel").focus();
        return ;
    }else{
        return true;
    }
}
function isAllNo(telNo) {
    var reg = new RegExp("^[0-9]*$");
    return reg.test(telNo);
}
function checkisEmail(email) {

    if (email.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
    {
        return true;
    }else{
        var messagePane = $("#message");
        errorInfo("邮箱格式错误！", messagePane);
        $("#mail").focus();

        return false;
    }
}
function isEmail(email){
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    return myreg.test(email);
}

function errorInfo(message, op) {
    var delay = 0;
    if(op.html()&&op.html().length>0) {
        op.fadeOut("slow");
        delay = 500;
    }
    setTimeout(function(){
        op.css("color", "red");
        op.html(message);
        op.fadeIn("slow");
    }, delay);
}

function checkTimeFormat(time){
    var reg=/^(\d{4})-(\d{2})-(\d{2})$/;
    if (time=='') {
        return false;
    }
    var arr=reg.exec(time);
    if(!reg.test(time) || parseInt(arr[2])>12 || parseInt(arr[2])==0 || parseInt(arr[3])>31 || parseInt(arr[3])==0){
        return false;
    }
    return true;
}

function IsDate(str){
    var reg=/^(\d{4})-(\d{2})-(\d{2})$/;
    if (str=='') {
        return false;
    }
    var arr=reg.exec(str);

    if(!reg.test(arr) || parseInt(RegExp.$2)>=12 || parseInt(RegExp.$3)>=31){
        return false;
    }
    return true;
}

var t;
function errMsg(message){
    clearTimeout(t);
    $(".errorInfo").fadeIn().html(message);
    t = setTimeout(function () {
        $(".errorInfo").fadeOut();
    }, 5000);
}

function loadData(jsonData){
    for(var key in jsonData) {
        var eInForm = $("*[name='"+key+"']");//$("#"+key);
        if(eInForm.attr("name")) {
            var eTagName = eInForm.prop("tagName");
            switch (eTagName){
                case "SELECT":
                    eInForm.find("option").each(function(i,e){
                        if($(e).val() == jsonData[key]){
                            $(e).attr("selected", true);
                        } else {
                            $(e).attr("selected", false);
                        }
                    });
                    break;
                case "TEXTAREA":
                    if(jsonData[key]) {
                        eInForm.val(jsonData[key]).parents("tr").fadeIn();
                    }
                    break;
                default :
                    var inputType = eInForm.attr("type");
                    switch (inputType){
                        case "checkbox":
                            eInForm.each(function(i, e){
                                if(jsonData[key].indexOf($(e).val())!=-1) {
                                    $(e).attr("checked", true);
                                }
                            });
                            break;
                        case "radio":
                            eInForm.each(function(i, e){
                                if(jsonData[key] == $(e).val()) {
                                    $(e).attr("checked", true);
                                } else if(jsonData[key].length>0) {
                                    $(e).attr("checked", false);
                                }
                            });
                            break;
                        default :
                            eInForm.val(jsonData[key]);
                    }
            }
        }
    }
}

String.prototype.trim = function() {
    var str = this,
        str = str.replace(/^\s\s*/, ''),
        ws = /\s/,
        i = str.length;
    while (ws.test(str.charAt(--i)));
    return str.slice(0, i + 1);
}
String.prototype.startWith=function(str){
    var reg=new RegExp("^"+str);
    return reg.test(this);
}

String.prototype.endWith=function(str){
    var reg=new RegExp(str+"$");
    return reg.test(this);
}
// 获取浏览器的类型，支持4种主流浏览器
function getAgentName() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    // Edge浏览器里一定有自己的名字
    if (userAgent.indexOf("Edge") > -1) {
        return "edge";
    };
    // Safari浏览器也不加别人的名字
    if (userAgent.indexOf("Macintosh")>-1 && userAgent.indexOf("Safari")>-1 && userAgent.indexOf("Chrome")==-1) {
        return "safari";
    }
    // Firefox肯定没必要加别人的名字
    if (userAgent.indexOf("Firefox") > -1) {
        return "firefox";
    }
    //其他的都当Chrome处理
    return "chrome";
}
// 解决edge浏览器下，base重复的问题
function genUrl(url) {
    if(url.startWith("/")) {
        url = url.substring(1, url.length);
    }
    return basePath + url;
}
function processMsg(html){
    html = html.replace("*","");
    html = html.replace("：","");
    return html.trim();
}

function active(){
    var url =  window.location.href;
    var uArray = url.split("/");
    var uP = uArray[uArray.length - 1];

    $(".nav-sidebar").find("a").each(function (i, e) {
        var href = $(e).attr("href");
        var hArray = href.split("/");
        var dP = hArray[hArray.length - 1];
        if(uP == dP) {
            $(e).parent().addClass("active");
        }
    })
}