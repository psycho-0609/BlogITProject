$(document).ready(function () {

    ////////////////// active fixed navbar /////////////////
    $(window).scroll(function () {

        var winTop = $(window).scrollTop();
        if (winTop > 30) {
            $("#navbarFixed").addClass("navbar-fixed")
        } else {
            $("#navbarFixed").removeClass("navbar-fixed")
        }
    })

    //////////// hide active nav left /////////////////
    $("#btn-active-nav").click(function () {
        $("#hideContent").addClass("hide-content");
        $("#navbar-active").addClass("active-nav");
    })

    $("#hideContent").click(function () {
        $("#hideContent").removeClass("hide-content");
        $("#navbar-active").removeClass("active-nav");
    })

    $("#btnDropdownMenuProduct").click(function () {
        $("#dropdownMenuProduct").slideToggle();
    })

    $("#btnDropdownMenuPosts").click(function (){
        $("#dropdownMenuPosts").slideToggle();
    })




    ////Search///////

    $("#btnSearch").click(function(){
        $("#hideContentSearch").addClass("hide-content-search");
        $("#searchBox").addClass("search-active");
    })


    $("#hideContentSearch").click(function () {
        $("#hideContentSearch").removeClass("hide-content-search");
        $("#searchBox").removeClass("search-active");
    })



    $(".btnCancel").click(function(){
        $("#hideContentSearch").removeClass("hide-content-search");
        $("#searchBox").removeClass("search-active");
    })

    $("#btnSearchNav").click(function(){
        var classList = document.getElementById('navSearchBox').className;
        if(classList.includes("nav-search-box-active"))
        {
            $(".nav-search-box").removeClass("nav-search-box-active");
        }else{
            $(".nav-search-box").addClass("nav-search-box-active");
        }
    })



    /////////////////  Creat calender ///////////////////////

    /////// Day //////////
    function createDay(maxDay) {
        var elementSelect = $("#formSigninSlectDay");
        for (let i = 1; i <= maxDay; i++) {
            var elOption = $("<option></option>").text(i);
            elOption.attr("value", i);
            elementSelect.append(elOption);
        }
    }

    //////// Month ////////
    function createMonth(maxDay) {
        var elementSelect = $("#formSigninSlectMonth");
        for (let i = 1; i <= 12; i++) {
            var elOption = $("<option></option>").text(i);
            elOption.attr("value", i);
            elementSelect.append(elOption);
        }
    }

    //////// Year ////////
    function createYear(minYear) {
        var date = new Date();
        var currentYear = date.getFullYear();
        var elementSelect = $("#formSigninSlectYear");
        for (let i = minYear; i <= currentYear; i++) {
            var elOption = $("<option></option>").text(i);
            elOption.attr("value", i);
            elementSelect.append(elOption);
        }
    }

    createDay(31);
    createMonth();
    createYear(1920);

    $("#btnSubmitLogin").click(function(e){
        e.preventDefault();
        checkValidateLogin();
    })
    function checkValidateLogin(){
        let email = $("#inputEmailLogin");
        let password = $("#inputPasswordLogin");
        let errEmail = $("#errorInputEmailLogin");
        let errPass = $("#errorInputPassLogin");

        if(email.val() == ""){
            errEmail.text("Vui l??ng nh???p t??n Email/S??T");
        }else{
            errEmail.text("");
        }

        if(password.val().length < 9 || password.val().length > 32){
            errPass.text("M???t kh???u t??? 9 ?????n 32 k?? t???");
        }else{
            errPass.text("");
        }

    }

    $("#btnSubmitRegis").click(function(e){
        e.preventDefault();
        checkValidReisAcc();
    })

    function checkValidReisAcc(){
        let name = $("#inputNameRegis");
        let phone = $("#inputPhoneRegis");
        let email = $("#inputEmailRegis");
        let password = $("#inputPasswordRegis");
        let day = $("#formSigninSlectDay")
        let month = $("#formSigninSlectMonth");
        let year = $("#formSigninSlectYear");
        let errName = $("#errorInputNameRegis");
        let errPhone = $("#errorInputPhoneRegis");
        let errEmail = $("#errorInputEmailRegis");
        let errPass = $("#errorInputPassRegis");
        let errDate = $("#errorSelectDate");

        if(name.val() == ""){
            errName.text("Nh???p t??n c???a b???n")
        }else{
            errName.text("")
        }

        if(phone.val().length != 10){
            errPhone.text("S??? ??i???n tho???i ph???i 10 s???")
        }else{
            errPhone.text("");
        }

        if(!validateEmail(email.val())){
            errEmail.text("Email kh??ng ????ng ?????nh d???ng");
        }
        else{
            errEmail.text("");
        }
        if(password.val().length < 9 || password.val().length > 32){
            errPass.text("M???t kh???u ph???i t??? 9 ?????n 32 k?? t???")
        }else{
            errPass.text("");
        }
        if(day.val() <= 0 || month.val() <= 0 || year.val() <= 0){
            errDate.text("Ch???n ng??y sinh c???a b???n")
        }else{
            errDate.text("");
        }


    }

    function validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    $("#btn-login").click(function(){
        $("#hideContent").removeClass("hide-content");
        $("#navbar-active").removeClass("active-nav");
    })





})