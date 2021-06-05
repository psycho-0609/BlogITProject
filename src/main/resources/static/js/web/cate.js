$(document).ready(()=>{
    $(window).scroll(function () {
        var winTop = $(window).scrollTop();

        if (winTop > 30) {
            $("#cateBox").addClass("cate-box-fixed")
        } else {
            $("#cateBox").removeClass("cate-box-fixed")
        }
    })
})