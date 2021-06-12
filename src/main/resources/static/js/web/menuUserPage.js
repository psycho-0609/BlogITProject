// Filter box active
$("#btnActiveMenu").click(function(){
    $("#hideContent").addClass("display-bg-behind-menu");
    $("#menuBox").addClass("menu-box-all-active");
})
$("#hideContent").click(function () {
    $("#hideContent").removeClass("display-bg-behind-menu");
    $("#menuBox").removeClass("menu-box-all-active");
})
$("#btnOpenMenuSm").click(function () {
    $("#menuSm").slideToggle(500);
})