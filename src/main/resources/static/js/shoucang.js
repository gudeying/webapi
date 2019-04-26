function shoucang(){
    var $btn = $("#shoucang");
    var isSubscribe =  $("#subscribe");
    var articleId = $btn.attr("artid");
    var userid = $("#loginuser").attr("loginuser");
    var ownerId = $("#shoucang").attr("ownerId");
    var queryData  = {"articleId":articleId,"userId":userid,"ownerId":ownerId};
    query("/isEnshrinedAndSubscribed",queryData,function(data){
        var starNum=data["starNum"];
        $btn.text("收藏"+starNum);
        if (data["isChouCang"]) {
            $btn.removeClass("text-red");
            $btn.addClass("text-blue");
            $btn.text("已收藏"+starNum);
        }if (data["isSubscribed"]) {
            isSubscribe.removeClass("text-yellow");
            isSubscribe.addClass("text-blue");
            isSubscribe.text("已订阅");
        }

    },"json");

    $btn.click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            doIsSubscribeAndSC($("#shoucang"),"/shoucang","/quxiao",queryData,"已收藏","收藏");
    });
    isSubscribe.click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        var tosend = {"targetUser":ownerId};
        doIsSubscribeAndSC(isSubscribe,"/subscribe","/unSubscribe",tosend,"已订阅","订阅");
    });
}

function doIsSubscribeAndSC(targetBtn,queryUrl,notAnyUrl,queryData,message,notAnyMess) {
    if ( targetBtn.hasClass("text-red")){
        // 要收藏或订阅
        query(queryUrl,queryData,function (data) {
            if (data["result"]) {
                targetBtn.removeClass("text-red");
                targetBtn.addClass("text-blue");
                targetBtn.text(message);
                layer.msg(message,{ icon:6, time:500, shade:0.1 });
            }else {
                $(".ajaxLogin").click();
            }
        },"json");
    }else if (targetBtn.hasClass("text-blue")){
        layer.confirm('真的要取消吗?', {
            btn: ['确定'] //按钮
        }, function(){
            query(notAnyUrl,queryData,function (data) {
                if (data["result"]) {
                    targetBtn.removeClass("text-blue");
                    targetBtn.addClass("text-red");
                    targetBtn.text(notAnyMess);
                    layer.msg('已取消', {icon: 1, time:500,shade:0.1});
                }else {
                    $(".ajaxLogin").click();
                }
            },"json");
        });
    }
}

