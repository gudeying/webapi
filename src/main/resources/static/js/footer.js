    var footterhtml = '  <div class="layout padding-big-top padding-big-bottom border-top bg">'+
'        <div class="container padding">'+
'            <div class="line">'+
'                <div class="hidden-l xs9 table-responsive">'+
'                    <ul class="nav nav-sitemap">'+
'                        <li><a href="/">首页</a>'+
'                            <ul>'+
'                                <li><a href="/write">写文章</a></li>'+
'                            </ul>'+
'                        </li>'+
'                        <li><a href="/article">文章</a>'+
'                            <ul>'+
'                                <li><a href="/article/topic/program">编程</a></li>'+
'                                <li><a href="/article/topic/emotion">情感</a></li>'+
'                                <li><a href="/article/topic/share">分享</a></li>'+
'                            </ul>'+
'                        </li>'+
'                        <li><a href="sleep.html">发呆</a>'+
'                        </li>'+
'                        <!--  -->'+
'                    </ul>'+
'                </div>'+
'                <div class="xl12 xs3 padding-top">'+
'                    <div class="media media-x">'+
'                        <div class="float-left txt-border radius-circle border-main">'+
'                            <div class="txt radius-circle bg-main icon-envelope-o text-large"></div>'+
'                        </div>'+
'                        <div class="media-body"><strong class="text-big text-main">联系邮箱</strong> <a href="mailto:2235733868@qq.com" target="_blank">1979908465@qq.com</a> </div>'+
'                    </div>'+
'                    <div class="media media-x">'+
'                        <div class="float-left txt-border radius-circle border-main">'+
'                            <div class="txt radius-circle bg-main icon-weibo text-large"></div>'+
'                        </div>'+
'                        <div class="media-body"><strong class="text-big text-main">微博关注我</strong><a class="button button-little bg-red shake-hover" href="http://www.weibo.com/">点击关注</a></div>'+
'                    </div>'+
'                </div>'+
'            </div>'+
'        </div>'+
'    </div>';

    function query(url,data,call,dataType) {
        $.ajax({
            type: "post",
            url:  url,
            data: data?data:null,
            dataType: dataType?dataType:"json",
            success : call?call:null,
            error:function () {
                    layer.closeAll();
            }
        });
    };
    var scriptsArray = new Array();
    $.cachedScript = function (url, options) {
        for (var s in scriptsArray) {
//console.log(scriptsArray[s]);
            if (scriptsArray[s]==url) {
                return {
                    done: function (method) {
                        if (typeof method == 'function'){
                            method();
                        }
                    }
                };
            }
        }
        options = $.extend(options || {}, {
            dataType: "script",
            url: url,
            cache:true
        });
        scriptsArray.push(url);
        return $.ajax(options);
    };

    (function($) {

        $("body").append(footterhtml);
        $("body").append('<script src="/js/bootstrap.min.js"></script>');
        $("body").append('<script src="/js/string-format.js"></script>');
        $.expr[":"].Contains = function(a, i, m) {
            return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
        };
        
        function filterList(header, list) {
            //@header 头部元素
            //@list 无需列表
            //创建一个搜素表单
            var form = $("<form>").attr({
                    "class": "filterform",
                    action: "#"
                }),
                input = $("<input>").attr({
                    "class": "filterinput",
                    type: "text"
                });
            $(form).append(input).appendTo(header);
            $(input).change(function() {
                var filter = $(this).val();
                if (filter) {
                    $matches = $(list).find("a:Contains(" + filter + ")").parent();
                    $("li", list).not($matches).slideUp();
                    $matches.slideDown();
                } else {
                    $(list).find("li").slideDown();
                }
                return false;
            }).keyup(function() {
                $(this).change();
            });
        }
        $(function() {
            filterList($("#form"), $("#demo-list"));
        });
    })(jQuery);
    jQuery("#jquery-accordion-menu").jqueryAccordionMenu();
    function login(e){
        e.preventDefault();
        $.cachedScript("/js/loginModal.js").done(function () {
            $('#loginModal').modal('toggle');
        });
    };
    $(document).ready(function() {

        var $toggleButton = $('.toggle-button'),
            $menuWrap = $('.mymenu'),
            $sidebarArrow = $('.sidebar-menu-arrow');

        $("#uploadGallery").on('click touchstart',function (e) {
           $.cachedScript("/js/uploadPic.js").done(function () {
               layerShow();
           });
        });

        $("#showUserDetail").on('click touchstart',function (e) {
            $.cachedScript("/js/userDetail.js").done(function () {
                $('#userDetailModal').modal('toggle');
            });
        });
        
        $toggleButton.on('click', function(e) {
            e.stopPropagation()
            $(this).toggleClass('button-open');
            //$menuWrap.fadeIn();
            $menuWrap.fadeToggle(600);
        });
        
        $sidebarArrow.click(function() {
            $(this).next().slideToggle(300);
        });

        $(".ajaxLogin").click(function (e) {
           login(e);
        });
        $("#ajaxLogin").click(function (e) {
            login(e);
        });
        $("#ajaxLogin").on("touchstart",function (e) {
            login(e);
        });
        $("#logout").on("touchstart",function () {
            $("#logout").click;
        });
        $("#logout").click(function (e) {
            $.ajax({
                url:"/ajaxLogout",
                type:"post",
                data:{"userId":$("#loginuser").attr("loginuser")},
                dataType:"json",
                success:function (data) {
                    if (data.result){
                        location.reload();
                    }else {
                        console.log(data.message);
                    }
                },
                error:null
            });
        })
        $(document).click(function(e) {
            e = window.event || e; // 兼容IE7
            var _con = $('#menuctrl'); // 设置目标区域
            if (!_con.is(e.target) && _con.has(e.target).length === 0) {

                if ($('.toggle-button').hasClass("button-open")) {

                    $('.toggle-button').click();
                }
            }
        });

    });