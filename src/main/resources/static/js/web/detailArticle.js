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
            url:"/api/user/favorite/create",
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
            url:"/api/user/favorite/delete/"+data,
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
            url:'/api/report/create',
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