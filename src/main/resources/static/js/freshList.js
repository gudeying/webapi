$(document).ready(function() {
    $(".btn-link").click(function (e) {
        // console.log(e);
        e.preventDefault();
        var num = $(this).attr("data");
        $("#list").empty();
        myinit(num);
        $('html,body').animate({
            scrollTop:'10'
        },500);
    });
})