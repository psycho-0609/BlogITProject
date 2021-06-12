$(document).ready(function (){
    let fail = "<i class=\"fas fa-times\"></i> ";
    function validate(data){
        if(data.newPassword === ""){
            $("#errorNewPass").text("New password is required")
        }else{
            if(data.newPassword.length >= 8){
                $("#errorPasswordRegister").text("")
            }else{
                $("#errorPasswordRegister").text("Password must has at least 8 characters")
            }
        }

        if(data.confirmPassword === ""){
            $("#errorConfirmPass").text("Confirm password is required")
        }else{
            $("#errorConfirmPass").text("")
        }

        if(data.newPassword !== "" && data.confirmPassword !== ""){
            if(data.confirmPassword !== data.newPassword){
                $("#errorConfirmPass").text("Confirm password is not match")
                return false;
            }else{
                $("#errorConfirmPass").text("")
                return true;
            }
        }
    }


    $("#formResetPassword").on("submit",function (e){
        e.preventDefault();
        let data ={};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if(validate(data) === true){
            resetPassword(data);
        }
    })
    function resetInput(){
        $("#newPassword").val("");
        $("#confirmPassword").val("");
    }

    function resetPassword(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/authen/resetPassword",
            method:"put",
            contentType:"application/json",
            data:JSON.stringify(data),
            dataType:'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            resetInput();
            $("#success").fadeIn();
            setTimeout(function () {
                $("#success").fadeOut(1500);
            }, 1500)
        }).fail(function (res){
            $("#processing").removeClass("active");
            $("#fail").html(fail + res.responseJSON.message);
            $("#fail").fadeIn();
            setTimeout(function () {
                $("#fail").fadeOut(3000);
            }, 1500)
        })
    }
})