$(document).ready(function (){

    let errorMessCurrentPass = "Current password is required";
    let errorMessNewPass = "Current password is required";
    let errorMessNewPassLength = "Password must be at least 8 character";
    let errorMessConfirmPass = "Confirm password is required";
    let errorMessConfirmMatch = "Confirm password is not match";
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";

    function validate(data){
        let count = 0;
        if(data.currentPassword === undefined || data.currentPassword === null || data.currentPassword === ""){
            $("#errorMessCurrentPass").text(errorMessCurrentPass);
        }else{
            $("#errorMessCurrentPass").text("");
            count++;
        }

        if(data.newPassword === undefined || data.newPassword === null || data.newPassword === ""){
            $("#errorMessNewPass").text(errorMessNewPass);
        }else{
            if(data.newPassword.length < 8){
                $("#errorMessNewPass").text(errorMessNewPassLength);
            }else{
                $("#errorMessNewPass").text("");
                count++;
            }
        }

        if(data.confirmPassword === undefined || data.confirmPassword === null || data.confirmPassword === ""){
            $("#errorMessConfirmPass").text(errorMessConfirmPass);
        }else{
            if(data.confirmPassword !== data.newPassword){
                $("#errorMessConfirmPass").text(errorMessConfirmMatch);
            }else{
                $("#errorMessConfirmPass").text("");
                count++;
            }
        }

        if(count === 3){
            return true;
        }
        return false;
    }

    function updatePass(data){
        $("#processing").addClass("active");
        console.log(data);
        $.ajax({
            url: "/api/authen/changePassword",
            type: "put",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'

        }).done(function (response) {
            $("#processing").removeClass("active");
            console.log(response);
            $("#currentPassword").val("");
            $("#newPassword").val("");
            $("#confirmPassword").val("");
            success(" Update password success")
        }).fail(function (res) {
            $("#processing").removeClass("active");
            console.log("fail");
            errorMessage(res.responseJSON.message);
        })
    }

    function errorMessage(message){
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        setTimeout(function () {
            $("#fail").fadeOut(500);
        }, 3000)
    }
    function success(message){
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        setTimeout(function () {
            $("#success").fadeOut(500);
        }, 3000)
    }

    $("#formUpdatePass").on('submit',function (e){
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        console.log(formData);
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if(validate(data) === true){
            updatePass(data);
        }
    })
})
