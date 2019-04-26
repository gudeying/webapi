function forLogin() {
    if ($.trim($("#username").val()) && $.trim($("#password").val())) {
        $.ajax({
            type: "post",
            url: "/ajaxLogin",
            data: $("#loginform").serialize(),
            dataType: "json",
            async: false,
            success: loginsuccess,
            error: function () {
                $("#tipMessage").text("请求出错！");
            }
        });
    } else {
        $("#tipMessage").text("请输入正确的用户名和密码！");
    }
};
var loginform = '<div class="container"><div class="modal fade" id="loginModal"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"></button><h4 class="modal-title">登陆</h4></div><div class="modal-body"><form action="javascript:forLogin();" class="form-horizontal" id="loginform" role="form"><div class="form-group"><label for="username" class="col-sm-2 control-label">登陆名</label><div class="col-sm-10"><input type="text" class="form-control" name="username" id="username" placeholder="请输入密码"></div></div><div class="form-group"><label for="passsword" class="col-sm-2 control-label">密码</label><div class="col-sm-10"><input type="password" name="password" class="form-control" id="password" placeholder="请输入用户名"></div></div><p class="text-warning" id="tipMessage"></p></form></div><div class="modal-footer"><button class="btn btn-default" data-dismiss="modal">关闭</button><button type="submit" class="btn btn-primary"  onclick="forLogin();"id="login">登陆</button></div></div></div></div></div></div>';
$("body").append(loginform);
inintKeyEvent();
function loginsuccess(data) {
    if (data.result){

        $('#loginModal').modal('hide');
        layer.msg(data.message,{ icon:6, time:500, shade:0.2 },function () {
            location.reload();
        });
    }else {
        $("#tipMessage").text(data.message);
    }
}
function inintKeyEvent(){
	    $("#loginModal").keydown(function(event) {  
	        if (event.keyCode == 13) { 
	            forLogin();
	        }  
	    })  
}

