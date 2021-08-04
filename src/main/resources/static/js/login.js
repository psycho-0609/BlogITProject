$(document).ready(function () {
    // validate login
    let errorFirstName = "First name is required";
    let errorLastName = "Last name is required";
    let errorEmail = "Email is required";
    let errorPassword = "Password is required";
    let errorConfirmPass = "Comfirm passsword is required"
    let errorMatchConfirmPass = "Comfirm passsword is not match";
    let errorEmailForm = "The wrong email format";
    let fail = "<i class=\"fas fa-times\"></i> ";

    function loginError(){
        let message = $("#errorLogin").text();
        if(message !== undefined && message != ""){
                $("#fail").html(fail + message)
                $("#fail").fadeIn();
                setTimeout(function () {
                    $("#fail").fadeOut(500);
                }, 3000)
        }
    }
    loginError();
    $("#btnRigister").click(function (e) {
        e.preventDefault();
        $("#register").css("display", "block");
        $("#loginForm").css("display", "none");

    })

    $("#btnLoginNow").click(function (e) {
        e.preventDefault();
        $("#register").css("display", "none");
        $("#loginForm").css("display", "block");
    })


    function validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    function validateRegister(data) {
        $("#alertRegisSuccess").css("display","none");
        let count = 0;
        if (data.firstName === "") {
            $("#errorFirstNameRegister").text(errorFirstName);

        } else {
            $("#errorFirstNameRegister").text("");
            count++;
        }

        if (data.lastName === "") {
            $("#errorLastNameRegister").text(errorLastName);

        } else {
            $("#errorLastNameRegister").text("");
            count++;
        }

        if (data.email === "") {
            $("#errorEmailRegister").text(errorEmail);
        } else {
            if (!validateEmail(data.email)) {
                $("#errorEmailRegister").text(errorEmailForm);
            } else {
                $("#errorEmailRegister").text("");
                count++;
            }

        }

        if (data.password === "") {
            $("#errorPasswordRegister").text(errorPassword)
        } else {
            if(data.password.length >= 8){
                $("#errorPasswordRegister").text("")
                count++;
            }else{
                $("#errorPasswordRegister").text("Password must has at least 8 characters")
            }

        }


        if (data.confirmPass === "") {
            $("#errorConfirmPassRegister").text(errorConfirmPass);
        } else {
            $("#errorConfirmPassRegister").text("");
            count++;
        }

        if (data.confirmPass !== "" && data.password !== "") {
            if (data.confirmPass !== data.password) {
                $("#errorConfirmPassRegister").text(errorMatchConfirmPass);
            } else {
                $("#errorConfirmPassRegister").text("");
                count++;
            }
        }
        if (count === 6) {
            return true;
        } else {
            return false;
        }
    }

    function resetInput(){
        $("#emailRegister").val("");
        $("#lastName").val("");
        $("#firstName").val("");
        $("#passwordRegister").val("");
        $("#confirmPass").val("");
    }

    function errorMessage(message){
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        setTimeout(function () {
            $("#fail").fadeOut(500);
        }, 3000)
    }
// registter
    $("#formRegister").on("submit", function (e) {
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if (validateRegister(data) === true) {
            registerAccount(data);
        }
    })

    ///
    function validEmailReset(email) {
        $(".box-alert-success").css("display","none");
        $("#emailSent").text("");
        if (email === "") {
            $("#emailResetError").text("Email is required");

        } else {

            if (!validateEmail(email)) {
                $("#emailResetError").text(errorEmailForm);
                return false;
            } else {
                $("#emailResetError").text("");
                return true;
            }
        }



    }
    // COUNT DOWN SEND AGAIN
    function countDown() {
        let numberCount = 10;
        $("#timeCount").text("");
        let myInter = setInterval(() => {
            $("#timeCount").text("Again " + "(" + numberCount + ")");
            if (numberCount <= 0) {
                clearInterval(myInter);
                $("#timeCount").css("display", "none");
                $("#btnSendMail").removeAttr("disabled");
            } else {
                numberCount--;
            }

        }, 1000);


    }
    // reset Password
    $("#formReset").on("submit", function (e) {
        e.preventDefault();
        let email = $("#emailReset").val();
        if (validEmailReset(email) === true) {
            let data={
                email:email
            }
            resetPassword(data);
        }

    })



//    ajax
    let messConfirm = "We have sent an email with a link to confirm your account. Please check your inbox "
    function registerAccount(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/authen/register",
            method:"post",
            contentType:"application/json",
            data:JSON.stringify(data),
            dataType:'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            resetInput();
            $("#messConfirmAccount").html(messConfirm + " <b>"+data.email+"</b>");
            $("#alertRegisSuccess").css("display","block");
        }).fail(function (res){
            $("#processing").removeClass("active");
            errorMessage(res.responseJSON.message);
        })
    }

    let messSendEmail = "We have sent an email with a link to reset your password. Please check your inbox "
    function resetPassword(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/authen/resetPassword",
            method:"post",
            contentType:"application/json",
            data:JSON.stringify(data),
            dataType:'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            $("#emailSent").html(messSendEmail + " <b>"+data.email+"</b>");
            $("#timeCount").css("display", "block");
            $("#btnSendMail").attr("disabled", "true");
            $("#emailReset").val("");
            $(".box-alert-success").css("display","block");
            countDown();
        }).fail(function (res){
            $("#processing").removeClass("active");
            $("#emailResetError").text(res.responseJSON.message);
            $("#emailSent").text("");
        })
    }




})