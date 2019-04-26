
function checkForm() {
    var title = document.getElementById("title");
    var content = UE.getEditor('container').getContentTxt();
    if (title.value.trim().length == 0 || title.value == null) {
        layer.msg("标题不能为空！",{ icon:0, time:1000, shade:0.2 });
        title.focus();
        $('html,body').animate({
            scrollTop:'0'
        },500);
        return false;
    }
    if (content.trim().length == 0 || content == null) {
        layer.msg("内容不能为空！",{ icon:0, time:1000, shade:0.2 });
        return false;
    } else {
        return getContentTxt();
    }
}
function getFile(source) {
    var animateimg = $("#file").val(); //获取上传的图片名 带//
    var imgarr = animateimg.split('\\'); //分割
    var myimg = imgarr[imgarr.length - 1]; //去掉 // 获取图片名
    var houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
    var ext = myimg.substring(houzui, myimg.length).toUpperCase(); //切割 . 获取文件后缀
    var file = $('#file').get(0).files[0]; //获取上传的文件
    var fileSize = file.size; //获取上传的文件大小
    var maxSize = 3145728; //最大3MB

    if (ext != '.PNG' && ext != '.GIF' && ext != '.JPG' && ext != '.JPEG' && ext != '.BMP') {
        $("#file")[0].files[0]=null;
        layer.msg('文件类型错误,请上传正确图片类型！');
        return false;

    } else if (parseInt(fileSize) >= parseInt(maxSize)) {
        layer.msg('上传的文件不能超过3MB');
        return false;

    } else {
            layer.msg("图片将在文章发表后上传！",{ icon:1, time:500, shade:0.2 });
            return true;
        }
}

function getContentTxt() {
    var aa = document.getElementById("description").value = UE.getEditor('container').getContentTxt();
    document.getElementById("content").value = UE.getEditor('container').getContent();
    return (true);
}

function articleSubmit(){
    var e = window.event;
    e.preventDefault();
    layer.load(0);
    if (checkForm()){
        var articleData = new FormData();

        articleData.append("title", $("#title").val());
        articleData.append("src", $("#file")[0].files[0]);
        articleData.append("author", $("#author").val());
        articleData.append("subject", $("#subject").val());
        articleData.append("content", $("#content").val());
        articleData.append("description", $("#description").val());
        $("#submit").addClass("hidden");

        $.ajax({
            url: "/ajaxWrite",
            type: 'POST',
            data: articleData,
            dataType: 'JSON',
            cache: false,
            processData: false,
            contentType: false,
            error:function () {
                layer.closeAll("loading");
                layer.msg("发布失败！请重试！",{ icon:2, time:800, shade:0.2 });
                $("#submit").removeClass("hidden");
            },
            success:function (data) {
                layer.closeAll("loading");
                if (data["result"]){
                    layer.msg('发布成功！', {
                        time:10000,
                        btn: ['查看','再写一篇'] //按钮
                    }, function(){
                        var art = data["data"];
                        window.location="/article/detail/"+art["id"];
                    }, function(){
                        document.getElementById("article").reset();
                        UE.getEditor('container').execCommand('cleardoc');
                        $("#submit").removeClass("hidden");
                    });
                }else {
                    layer.msg(data["message"],{ icon:0, time:1000, shade:0.2 });
                }
            }
        })
    }else { layer.closeAll("loading");}
};