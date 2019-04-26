$.cachedScript("/js/string-format.js").done(function () {
    format.extend(String.prototype, {});
    getGallery();
});
var groupGallery ='<li class="item-thumbs col-sm-6 col-xs-8">' +
    '            <div>' +
    '                <a th:class="overlay" th:data-lightbox="gallery"' +
    '                    th:data-caption="{picture.des}"' +
    '                    th:href="images/{picture.src}"> <span' +
    '                    class="glyphicon glyphicon-zoom-in"></span>' +
    '                </a> <img th:src="images/{picture.comsrc}" alt="image" />' +
    '            </div>' +
    '        </li>';
var rowGallery = '';
function getGallery() {

}
$("body").on("click",".img-list li img",function(e){
    layer.photos({ photos: {"data": [{"src": e.target.src}]} });
});
