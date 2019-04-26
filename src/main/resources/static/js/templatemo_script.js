/* Credit: http://www.templatemo.com */

jQuery(document).ready(function()
{
    //mobile menu s
    //single page nav
    // jQuery("header ul").singlePageNav({offset: jQuery('header').outerHeight()});
    // //open scroll function
    // jQuery("html, body").animate({ scrollTop: 50 }, 0, function(){
    //     jQuery(this).animate({ scrollTop: 0 },1000);
    // });
    // //call flex slider function
    // jQuery('#main-slider').flexslider();
    //scroll to top
    jQuery(window).scroll(function(){
        if(jQuery(this).scrollTop() > 100){
            jQuery('.scrollup').fadeIn();
        } else {
            jQuery('.scrollup').fadeOut();
        }
    });
    jQuery('.scrollup').click(function(){
        jQuery("html, body").animate({ scrollTop: 0 }, 1000);
        return false;
    });    //lightbox