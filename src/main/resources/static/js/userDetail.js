function edit(){
    // autographContent可编辑
    $("#autographContent").attr("readonly",false);
    $("#autographContent").focus();
}
function showEdit(){
    // 显示“编辑”按钮
    $(".edit").show();  //姓名年龄
    $("#editFile").show();  //logo
    edit(); //签名
    $("#submitUserDetail").show();
    $("#submitUserDetail").attr("disabled",false);
}
function enableshowUserLogoFile(){
    $("#uploadUserLogo").show();
}

function editUserDetail(arg){
    var formdata = new FormData();
    var $input =   $("input[name^='user']");
    $input.each(function(){
        if(!Boolean($(this).attr("readonly"))){
            formdata.append($(this).attr("name"),$(this).val());
        }
    });
    if(Boolean($("#userLogoFile")[0].files[0])){
        formdata.append("userLogo",$("#userLogoFile")[0].files[0]);
    }
    $.ajax({
        url: "/userDetail",
        type: 'POST',
        data: formdata,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        error:function () {
            layer.msg(data["message"],{ icon:2, time:1000, shade:0.2 });
        },
        success:function(data){
            if (!data["result"]){
                layer.msg(data["message"],{ icon:2, time:1000, shade:0.2 });
            }else {
                layer.msg("修改成功",{ icon:1, time:500, shade:0.2 },function () {
                    location.reload();
                });
            }
        }
    })
}


function loadImg() {
    var animateimg = $("#userLogoFile").val(); //获取上传的图片名 带//
    var imgarr = animateimg.split('\\'); //分割
    var myimg = imgarr[imgarr.length - 1]; //去掉 // 获取图片名
    var houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
    var ext = myimg.substring(houzui, myimg.length).toUpperCase(); //切割 . 获取文件后缀

    var file = $('#userLogoFile').get(0).files[0]; //获取上传的文件
    var fileSize = file.size; //获取上传的文件大小
    var maxSize = 1048576; //最大1MB
    if (ext != '.PNG' && ext != '.GIF' && ext != '.JPG' && ext != '.JPEG' && ext != '.BMP') {

        layer.msg('文件类型错误,请上传图片类型');
        $("#userLogoFile")[0].files[0]=null;
        $("#userLogoFile").val(null);
        return false;

    } else if (parseInt(fileSize) >= parseInt(maxSize)) {
        layer.msg('上传的文件不能超过1MB');
        $("#userLogoFile")[0].files[0]=null;
        $("#userLogoFile").val(null);
        return false;

    } else {
        if (window.FileReader) {
            var fr = new FileReader();
            fr.onloadend = function(e) {
                var img = document.getElementById("userLogo");
                img.setAttribute("src", e.target.result);
                img.setAttribute("alt", "");
            };
            fr.readAsDataURL(file);
        }
        return false;
    }
}

function LoadUserDetailModal(){
	$.ajax({
		url:"/js/userdetail.html",
		type:"get",
		dataType:'html',
		success:function(data){
			$("body").append(data);
			$(".edit").on("click",function(){
			    $(this).prev().attr("readOnly",false);
			    $(this).prev().focus();
			});
			completeUserForm();
		}
	})
	
}
function completeUserForm(){
	$.ajax({
		url:"/getUserInfo",
		type:"post",
        data:{"userId":$("#loginuser").attr("loginuser")},
		dataType:'json',
		success:function(data){
			if(data["result"]){
				var ModalUser = data["data"];
				$("#userLogo").attr("src",'/images/userlogo/'+ModalUser["userlogo"]);
				$("#ModalUserAge").val(ModalUser["age"]);
				$("#ModalUserEmail").val(ModalUser["mail"]);
				$("#ModalUserName").val(ModalUser["nickName"]);
				$("#autographContent").val(ModalUser["autograph"]);
                $('#userDetailModal').modal('toggle');
			}
		}
	});
}

(function(){
	LoadUserDetailModal();
})();