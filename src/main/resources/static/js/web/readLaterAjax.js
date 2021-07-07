$(document).ready(function (){
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";

    let errorTimeout;
    let successTimeOut;
    function clearTime() {
        clearTimeout(errorTimeout);
        clearTimeout(successTimeOut);
        $("#success").fadeOut();
        $("#fail").fadeOut();
    }
    function error(message){
        clearTime();
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        errorTimeout = setTimeout(function () {
            $("#fail").fadeOut(500);
        }, 3000)
    }
    function success(message){
        clearTime();
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        successTimeOut = setTimeout(function () {
            $("#success").fadeOut(500);
        }, 3000)
    }

    $(document).on("click", ".btn-add-read-later", function () {
        let id = $(this).attr('id').split("_")[1];
        let data = {
            articleId: parseInt(id)
        }
        if (id !== undefined || id !== "") {
            saveReadLater(data);
        }

    })

    function saveReadLater(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/user/readLater/create",
            method:"post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            success(" Saved to Read Later");
        }).fail(function (res){
            $("#processing").removeClass("active");
            error(" " +res.responseJSON.message);
        })
    }

})