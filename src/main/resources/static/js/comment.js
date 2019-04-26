(function(){
    $("#doComment").click(function () {
        if ($("#comment").val()!=""&&$("#comment").val().trim().length!=0) {
            comment();
        }else {
            layer.msg("内容为空",{icon:5,time:500});
            return false;
        }
    });

})();
var articleId = $("#shoucang").attr("artid");
function comment() {
     layer.load(0);
    var comment = $("#comment").val();
    var articleId = $("#shoucang").attr("artid");
    format.extend(String.prototype, {});

    $.ajax({
        url:"/comment",
        type:"post",
        data:{"comment":comment,"id":articleId},
        dataType:"json",
        success:function (data) {
            if (data["result"]){
                newComment(data["comment"]);
            }else {
                layer.closeAll("loading");
                layer.msg(data["message"],{icon:0,time:500});
            }
        },
        error:function () {
            layer.closeAll("loading");
            layer.msg("对不起，发生了错误！如果你已登陆，请稍后重试",{icon:2,time:500})
        }
    });
}
function newComment(data) {
    format.extend(String.prototype, {});
    var li = '                <li>' +
        '                    <div class="panel-body">' +
        '                        <p >{user}:{time}</p>' +
        '                        <p>{content}<a class="button button-little border-blue swing-hover  radius-rounded" href="">回复</a></p>' +
        '                        <hr class="bg-mix" />' +
        '                    </div>' +
        '                </li>';
    $("#commentList").append(li.format(data));
    $("#comment").val("");
    layer.closeAll("loading");
    layer.msg("感谢您的评论",{icon:6,time:500},function () {
        closeComModal();
    });
}

function showCommentModal() {
    $("#commentBtn").hide();
    $("#commentModal").removeClass("hidden");
    layer.open({
        type: 1,
        shade: false,
        title: false,
        content: $('#commentModal'),
        cancel: function(){
            $("#commentModal").addClass("hidden");
            $("#commentBtn").show();
        }
    });
    $("#comment").focus();

}
function  closeComModal() {
    layer.closeAll();
    $("#commentModal").addClass("hidden");
    $("#commentBtn").show();
}
function showCommentList(param) {
    var commentId = $(param).attr('commentId');
    var url = '/comments/'+articleId+'/show/'+commentId;
    layer.open({
        type: 2,
        area: ['80%', '80%'],
        fixed: false, //不固定
        maxmin: true,
        content: url
    });
}
function answerComment(param){
	var parentId = $(param).attr('parentid');
	var towho = $(param).attr('towho');
	var articleId = $("#shoucang").attr("artid");
	var content;
	layer.prompt({title: '回复', formType: 2}, function(pass, index){
		  layer.close(index);
		  layer.msg(pass);
		  content = pass;
		  if(content){
			  var paramBody = {"parentId":parentId,"towho":towho,"articleId":articleId,"content":content,};
			  sendAnswer(paramBody);
		  }
		});
}
function sendAnswer(data){
    $.ajax({
        url:"/answerComment",
        type:"post",
        data:data,
        dataType:"json",
        success:function (data) {
        	 layer.closeAll("loading");
            if (data["result"]){
            	layer.msg("评论成功！",{icon:1,time:500});
            }else {
                layer.msg(data["message"],{icon:0,time:500});
            }
        },
        error:function () {
            layer.closeAll("loading");
            layer.msg("对不起，发生了错误！如果你已登陆，请稍后重试",{icon:2,time:500})
        }
    });
	
}
function showLongCommentIframe() {
    window.event.preventDefault();
    closeComModal();
    layer.open({
        title:"评论",
        type: 2,
        area: ['90%', '80%'],
        fixed: false, //不固定
        maxmin: true,
        content: '/longcomment.html'
    });
}