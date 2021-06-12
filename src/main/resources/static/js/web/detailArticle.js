$(document).ready(function (){
    const articleId = $("#articleOfArticle").val();
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

    $("#btnSaveReadLater").click(function (e){
        let data={
            articleId:articleId
        }
        saveReadLater(data);
    })

    function saveReadLater(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/readLater/create",
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

    $(".btn-favorite").click(function (){
        let data={
            articleId:articleId
        }
        let id = parseInt($(this).attr("id"));
        if(id !== 0){
            deleteFav(id);
        }else if(id === 0){
            crateFav(data);
        }
    })

    function crateFav(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/favorite/create",
            method:"post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            $(".btn-favorite").css("color","deeppink")
            $(".btn-favorite").attr("id",res.id);
            $("#favCount").text(res.countFav);
        }).fail(function(){
            $("#processing").removeClass("active");
            console.log("fail")
        })
    }

    function deleteFav(data){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/favorite/delete/"+data,
            method:"delete",
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            $(".btn-favorite").css("color","gray")
            $(".btn-favorite").attr("id",0);
            $("#favCount").text(res.countFav);
        }).fail(function(){
            $("#processing").removeClass("active");
            console.log("fail")

        })
    }

})