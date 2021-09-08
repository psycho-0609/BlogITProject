$(document).ready(function (){
    const articleId = $("#articleOfArticle").val();
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
            $("#fail").fadeOut(3000);
        }, 1500)
    }
    function success(message){
        clearTime();
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        successTimeOut = setTimeout(function () {
            $("#success").fadeOut(3000);
        }, 1500)
    }

    $("#btnSaveReadLater").click(function (e){
        let data={
            articleId:articleId
        }
        saveBookMark(data);
    })

    function saveBookMark(data){
        // $("#processing").addClass("active");
        $.ajax({
            url:"/api/user/readLater/create",
            method:"post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            // success(" Saved Bookmark Success");
            $(".book-mark").css("color","#ffffff")
            $(".book-mark").css("background","#5488c7")
            $(".book-mark").attr("id",res.id);
            $("#bookMarkCount").html("<i class=\"fas fa-bookmark\"></i>" +res.totalBookMark);
        }).fail(function (res){
            $("#processing").removeClass("active");
            error(" " +res.responseJSON.message);
        })
    }

    function deleteBookMark(id) {
        // $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/readLater/delete/" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            $(".book-mark").css("color","gray")
            $(".book-mark").css("background","none")
            $(".book-mark").attr("id",0);
            $("#processing").removeClass("active");
            // success(" Delete successfully");
            $("#bookMarkCount").html("<i class=\"fas fa-bookmark\"></i>" +res.totalBookMark);
        }).fail(function (res) {
            $("#processing").removeClass("active");

            // error(" " + res.responseJSON.message);
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

    $(".book-mark").click(function (){
        let data={
            articleId:articleId
        }
        let id = parseInt($(this).attr("id"));
        if(id !== 0){
            deleteBookMark(id);
        }else if(id === 0){
            saveBookMark(data);
        }
    })
    function crateFav(data){
        // $("#processing").addClass("active");
        $.ajax({
            url:"/api/user/favorite/create",
            method:"post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            $(".btn-favorite").css("color","#ffffff")
            $(".btn-favorite").css("background","deeppink")
            $(".btn-favorite").attr("id",res.id);
            $("#favCount").html("<i class=\"fas fa-heart\"></i>"+res.countFav);
        }).fail(function(){
            $("#processing").removeClass("active");
            console.log("fail")
        })
    }

    function deleteFav(data){
        // $("#processing").addClass("active");
        $.ajax({
            url:"/api/user/favorite/delete/"+data,
            method:"delete",
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            $(".btn-favorite").css("color","gray")
            $(".btn-favorite").css("background","none")
            $(".btn-favorite").attr("id",0);
            $("#favCount").html("<i class=\"fas fa-heart\"></i>"+res.countFav);
        }).fail(function(){
            $("#processing").removeClass("active");
            console.log("fail")

        })
    }

    $("#formReport").on('submit',function (e){
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if(validateReport(data) === true){
            report(data);
        }

    })

    function report(data){
        $("#processing").addClass("active");
        $.ajax({
            url:'/api/web/report/create',
            method:'post',
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res){
            $("#processing").removeClass("active");
            success(" Report successfully");
            $(".type-report:checked")[0].checked = false;
            $("#commentReport").val("");
            $("#modalReport").modal('hide');
        }).fail(function (res){
            $("#processing").removeClass("active");
            error(" " +res.responseJSON.message);
        })
    }

    function validateReport(data){
        if(data.typeReportId === undefined || data.typeReportId === ""){
            $("#errorMessReport").text('Please chose the type of report!')
            return false;
        }else{
            $("#errorMessReport").text('')
            return true;
        }
    }



})