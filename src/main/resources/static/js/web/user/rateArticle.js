$(document).ready(function () {
    let articleId = $("#articleOfArticle").val();
    let userId = $("#idOfUser").val();
    let fail = "<i class=\"fas fa-times\"></i> ";

    let errorTimeout;
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
    function getOne(){
        $.ajax({
            url:"/api/user/rate/getOne?postId="+articleId,
            method:"get",
            dataType:"json"
        }).done(function (res){
            if(res.id !== null){
                writeRate(res.score)
            }else{
                writeRate(0);
            }
        })
    }

    function writeRate(score){
        $("#rateBox").rate({
            length: 5,
            value: score,
            readonly: false,
            size: '20px',
            selectClass: 'fxss_rate_select',
            incompleteClass: 'fxss_rate_no_all_select',
            customClass: 'custom_class',
            callback: function (object) {
                let data = {
                    "articleId":articleId,
                    "score":object.index + 1
                }
                console.log(data);
                ajaxRate(data);

            }
        });
    }

    // $("#rateBox").rate({
    //
    // });
    getOne();

    function ajaxRate(data){
        if(userId !== undefined && userId !== ''){
            $.ajax({
                url:"/api/user/rate/create",
                method:"post",
                contentType: "application/json",
                data:JSON.stringify(data),
                dataType:"json"
            }).done(function (res){
                getOne();
            })
        }else{
            error("Please login to rate this post!")
            writeRate(0);
        }

    }

})