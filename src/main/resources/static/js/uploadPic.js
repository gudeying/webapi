
function showPicture(picture){

    var galle = '            <li class="item-thumbs col-sm-6 col-xs-8">' +
        '                <div>\n' +
        '                    <a class="overlay" data-lightbox="gallery"' +
        '                       data-caption="{des}" onclick="showImg(this)"' +
        '                       data="images/{src}">' +
        '                        <span class="icon-lightbulb-o"></span>' +
        '                    </a> <img src="images/{comsrc}" alt="image" />' +
        '                </div>\n' +
        '                <h6 class="editor hidden"><span class="badge bg-mix swing-hover"><a data="{src}">删除</a></span></h6>\n' +
        '            </li>';
    $.cachedScript("/js/string-format.js").done(function () {
        format.extend(String.prototype, {});

    });
    var li = document.createElement("li");
    li.classList.add("album")
    var img = document.createElement("img");
    img.setAttribute("src","/images/"+picture.comsrc);
    img.setAttribute("alt",picture.des);
    var h = document.createElement("h6");
    var ahref = document.createElement("a");
    ahref.setAttribute("href","/images/"+picture.src);
    ahref.appendChild(document.createTextNode("查看"));
    h.appendChild(ahref);
    li.appendChild(img);
    li.appendChild(h);
    document.getElementById("gallerys").appendChild(li);
    layer.closeAll('loading');
}
function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}
var picMap = new Map();
function sc(source) {
    if (picMap.size > 6) {
        layer.msg("一次最多上传六张图片！",{ icon:4, time:800, shade:0.2 });
        return false;
    } else {
        var numonly = 0;
        var animateimg = $("#f").val(); //获取上传的图片名 带//
        var imgarr = animateimg.split('\\'); //分割
        var myimg = imgarr[imgarr.length - 1]; //去掉 // 获取图片名
        var houzui = myimg.lastIndexOf('.'); //获取 . 出现的位置
        var ext = myimg.substring(houzui, myimg.length).toUpperCase(); //切割 . 获取文件后缀

        var file = $('#f').get(0).files[0]; //获取上传的文件

        var fileSize = file.size; //获取上传的文件大小

        var maxSize = 1048576; //最大1MB

        if (ext != '.PNG' && ext != '.GIF' && ext != '.JPG' && ext != '.JPEG' && ext != '.BMP') {

            layer.msg('文件类型错误,请上传图片类型');

            return false;

        } else if (parseInt(fileSize) >= parseInt(maxSize)) {

            layer.msg('上传的文件不能超过1MB');

            return false;

        } else {
            var formData = new FormData();

            formData.append("desc", $("#desc").val());
            formData.append("picture", $("#f")[0].files[0]);
            var gid = guid();
            picMap.set(gid,formData);
            var file = source.files[0];

            if (window.FileReader) {
                var fr = new FileReader();
                fr.onloadend = function(e) {
                    var li = document.createElement("li");
                    var description = "删除";
                    li.classList.add("album");
                    var img = document.createElement("img");
                    img.setAttribute("src", e.target.result);
                    img.setAttribute("alt", "");
                    var h = document.createElement("h6");
                    var ahref = document.createElement("a");
                    ahref.setAttribute("onclick", "deletPic(this)");
                    ahref.setAttribute("uid",gid);
                    ahref.appendChild(document.createTextNode(description));
                    h.appendChild(ahref);
                    li.appendChild(img);
                    li.appendChild(h);
                    document.getElementById("gallery").appendChild(li);
                };
                fr.readAsDataURL(file);
            }
            return false;
        }
    }

}
function deletPic (arg) {
    picMap.delete($(arg).attr("uid"));
    $(arg).parent().parent().remove();
}

function uploadPic() {
    if (picMap.size!=0){
        layer.closeAll('page');
        layer.load(0);
        picMap.forEach(function (value,key,map) {
            $.ajax({
                url: "/ajaxUpload",
                type: 'POST',
                data: value,
                dataType: 'JSON',
                cache: false,
                processData: false,
                contentType: false

            }).done(function(data) {
                picMap.delete(key);
                if (data["result"]){
                    if (picMap.size==0) {
                        layer.closeAll('loading');
                        layer.msg("全部上传成功！",{ icon:1, time:800, shade:0.2 },function () {
                            location.reload();
                        });
                    }
                }else {
                    layer.closeAll('loading');
                    layer.msg("权限不足",{ icon:2, time:800, shade:0.2 });
                } 
            });
        });
    } else {
        layer.closeAll('page');
        layer.msg("没有图片被选择",{ icon:0, time:1000, shade:0.2 });

    }

}
function cancel() {
    layer.closeAll('page');
}
function layerShow() {
    picMap.clear();
    var layercontent = '<body><style>img{width:100%}small{display:inline-block;min-width:10px;padding:3px 7px;font-size:12px;font-weight:normal;line-height:1;color:#468847;text-align:center;white-space:nowrap;vertical-align:baseline;background-color:#dff0d8;border-radius:10px;font-family:Consolas,"Courier\t\tNew",monospace;margin-bottom:5px;opacity:0.8}.list-group{padding:0;margin-top:30px;position:relative}.list-group li{list-style:none;padding:10px;width:21%;float:left}h6{margin:0;text-align:center}ul>small{position:absolute;top:-10px}.page-header{border-bottom:1px solid#eee;font-family:"Helvetica Neue",Helvetica,Arial,sans-serif}.page-header h1{margin-bottom:5px;line-height:1;color:#4B4B4B;font-weight:200;margin-left:8px}.page-header h2{font-weight:100;padding-bottom:0;padding-top:0;margin-top:0;margin-left:8px}*{line-height:1.5}h1,h2,h3,p{line-height:1.5}h2{color:#ccc;font-size:20px}.album img{}#search{margin-top:20px;padding:5px;width:200px}label{font-size:12px}.avatar{border-radius:50%;width:50px;float:left;margin-right:15px;padding-left:5px}</style><h1>相册(您上传的图片所有人都可以看到)</h1><div class="container"><ul class="list-group"id="gallery"><li class="album"data-album="single atlas"id="atlas"><img src="/images/atlas.jpg"alt="atlas"><h6><a>示例图片</a></h6></li></ul></div><div class="container"><form id="form1"><label><span class="badge bg-mix">图片标题</span></label><input type="text" id="desc" value="geeklemon"/><input type="button" value="选择图片" onclick="f.click()" class="button bg-main"/><br><p><input type="file"id="f"name="f"onchange="sc(this);"style="display:none"/></p></form><button class="button bg-sub radius-rounded" onclick="uploadPic()">确认上传</button><button class="button bg-sub radius-rounded" onclick="cancel()">取消</button></div></body>';
    layer.closeAll('page');
    layer.open({
        type: 1,
        area: ['80%', '80%'],
        title: '上传图片',
        shade: 0.2,
        skin: 'layui-layer-demo',
        maxmin: true,
        anim: 1,
        content: layercontent
    });
}
function showImg(arg) {

    var src = $(arg).attr("data");
    var description = $(arg).attr("data-caption");
    layer.photos(
        {
            photos: {"title": "gallery",
                "data": [{"src":src,
                    "alt": description
                }]
            }
        });
}
function geditor() {
    if ( $(".editor").hasClass("hidden")) {
        $("#editor").html("取消");
        $(".editor").removeClass("hidden");
    }else {
        $("#editor").html("编辑");
        $(".editor").addClass("hidden");
    }

}
function deletgallery(arg) {
    layer.confirm('确定要删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var id = $(arg).attr("data");
        var data={"file":id};

        query("/deleteimg",data,function (data) {
            if (!data["result"]){
                layer.msg(data["message"],{time:500});
            } else {
                $(arg).parents("li").remove();
                layer.closeAll();
            }
        },"json");
    }, function(){
        layer.msg('取消',{
            icon:1,
            time: 500,
        });
    });

}