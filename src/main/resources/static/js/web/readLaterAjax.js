$(document).ready(function (){
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";

    function error(message){
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        setTimeout(function () {
            $("#fail").fadeOut(3000);
        }, 1500)
    }
    function success(message){
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        setTimeout(function () {
            $("#success").fadeOut(3000);
        }, 1500)
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