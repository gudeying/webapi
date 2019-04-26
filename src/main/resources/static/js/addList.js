format.extend(String.prototype, {});
var article = '<li class="art">'+
'                    <div class="media media-x">'+
'                        '+
'                        <a class="float-left" href="/article/detail/{id}"><img src="/images/{src}" width="100" height="88" class="radius" alt="...">'+
'                        </a>'+
'                       '+
'                        <div class="media-body" style="height: 102px">'+
'                            <strong>&nbsp; &nbsp; '+
'                                <div class="line" style="display: inline"> '+
'                                <a class="text-sub zuozhe float-left" href="/user/{authorid}/home">'+
'                                	&nbsp; &nbsp;{author}'+
'                                </a> &nbsp; &nbsp;'+
'                                <a class="text-sub float-left" href="/article/topic/{subject}">'+
'                                	{subject}'+
'                                </a>'+
'                        '+
'                                    <div style="padding-bottom: 5px;" class="text-main title_big xl10 xm6">'+
'                                        <a href="/article/detail/{id}">{title}</a>'+
'                                    </div> '+
'                                '+
'                                </div>'+
'                            </strong>'+
'                            <div class="" style="text-overflow: ellipsis;overflow: hidden">'+
'                              <a style="text-decoration:none;" href="/article/detail/{id}">{description}<span class=" text-main border-blue swing-hover radius-rounded">...more</span> </a>'+
'                            </div>'+
'                        </div>'+
'                    </div>'+
'                </li>';


 var $list = $("#list");
 function myinit(num) {
     layer.load(0);
     var num = (num==null)?1:num;
     var user = $("#pagetype").attr("user");
     var topic = $("#pagetype").attr("mytype");
     $.ajax({
         type: "post",
         url: "/getArticle",
         data: {"num":num,"user":user,"topic":topic},
         dataType: "json",
         success : showlist
     });
 }
myinit(1);
 function showlist(data){
     if (data) {
         var pagenum = data.pageNum;
         var totlePage = data.lastPage;
         var arr = data.list;
         var prePage = data.prePage;
         var nextPage = data.nextPage;
         if (!data.hasPreviousPage) {
             $("#previous").hide();
         }
         if (!data.hasNextPage){
             $("#next").hide();
         }
         if (data.hasPreviousPage) {
             $("#previous").show();
         }
         if (data.hasNextPage){
             $("#next").show();
         }
         $("#totlenum").text("共"+totlePage+"页");
         $("#last").attr("data",data.lastPage);
         $("#previous").attr("data",prePage);
         $("#next").attr("data",nextPage);
         $("#currentPage").text(pagenum);
         for (var i = arr.length - 1; i >= 0; i--) {
             $list.append(article.format(arr[i]))
         }
         layer.closeAll('loading');
     }
 }
 	