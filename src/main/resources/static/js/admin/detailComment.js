$(document).ready(function (){
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i> ";
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

    let url = new URL(window.location.href);
    let articleId = url.searchParams.get("postId");
    /// delete comment
    $(document).on("click",".btn-delete-comment",function (){
        let id = $(this).attr("id").split("_")[1];
        if(id !== undefined && id !== ""){
            swalAlertDeleteComment(id);
        }
    })

    function swalAlertDeleteComment(id) {
        swal({
            title: "Are you sure?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    deleteComment(id);
                }
            });
    }

    function deleteComment(id){
        $.ajax({
            url:'/api/admin/comment/delete/'+id,
            method:'delete',
            type:'json'
        }).done(function (res){
            success(res.message);
            getData();
        }).fail(function (res){
            getData();
            error(res.responseJSON.message)
        })
    }

    /// delete reply
    $(document).on("click",".btn-delete-reply",function (){
        let id = $(this).attr("id").split("_")[1];
        if(id !== undefined && id !== ""){
            swalAlertDeleteReplyComment(id);
        }
    })
    function swalAlertDeleteReplyComment(id) {
        swal({
            title: "Are you sure?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    deleteReplyComment(id);
                }
            });
    }
    function deleteReplyComment(id){
        $.ajax({
            url:'/api/admin/replyComment/delete/'+id,
            method:'delete',
            type:'json'
        }).done(function (res){
            success(" " + res.message);
            getData();
        }).fail(function (res){
            getData();
            error(" " + res.responseJSON.message)
        })
    }

    function getData(){
        $.ajax({
            url:'/api/admin/comment/detail?postId='+articleId,
            method: 'get',
            dataType:'json'
        }).done(function (res){
            fetch(res);
        })
    }

    function fetch(data){
        let res = "";
        if(data.length !== null && data.length > 0){
            data.forEach(el => res += write(el));
        }else{
            res = "<div'> <p style='color: grey; font-weight: bold; text-align: center'>There is nothing here!</p></div>"
        }
        $("#commentBox").html(res);
    }

    function write(el){
        let res = " <div class=\"comment\">\n" +
            "                                <div class=\"wrap-comment\">\n" +
            "                                    <div class='comment-reader'>\n" +
            "                                        <div class='infor-reader'>\n" +
            "                                            <a href='/author/" + el.account.id + "' class=\"article-img-author\">\n" +
            "                                                <img src='" + el.account.userDetail.thumbnail + "' alt=''>\n" +
            "                                            </a>\n" +
            "                                            <div class='detail-reader'>\n" +
            "                                                <a href='/author/" + el.account.id + "'>"+el.account.userDetail.firstName +"\n" +
            "                                                    " + el.account.userDetail.lastName + "</a>\n" +
            "                                                <span style=\"font-size: .8rem\"> "+ formatDate(el.createdDate) + "</span>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                        <div style=\"padding-left: 3.5rem\" class=\"content-comment-box\">\n" +
            "                                            <p class='mb-1 content-comment'>" + el.content + "</p>\n" +
            "\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"btn-box\">\n" +
            "                                        <button class=\"btn btn-outline-danger btn-delete-comment\" id='delete_" + el.id + "'><i class=\"far fa-trash-alt\"></i></button>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
                                                replyComment(el.replyComment) +
            "                                <hr>\n" +
            "                            </div>";
        return res;

    }

    function replyComment(data){
        let res = "";
        if(data != null && data.length > 0){
            data.forEach(el => res += writeReply(el));
        }
        return res;
    }

    function writeReply(el){
        let content = "";
        if (el.replyAccount != null) {
            content = "<p class='mb-1 reply-content'><a href='/author/" + el.replyAccount.id + "'>"+el.replyAccount.firstName +" " + el.replyAccount.lastName + "</a> " + el.content + "</p>"
        }else{
            content =" <p class='mb-1 reply-content'>" + el.content + "</p>";
        }
        let res = " <div style=\"padding-left: 3.5rem; margin-bottom: 1rem\" class=\"reply-comment wrap-comment\"\n" +
            "                                    >\n" +
            "                                    <div class=\"main-content-reply-comment\">\n" +
            "                                        <div class='infor-reader'>\n" +
            "                                            <a href='/author/" + el.account.id + "' class=\"article-img-author\">\n" +
            "                                                <img src='" + el.account.userDetail.thumbnail + "' alt=''>\n" +
            "                                            </a>\n" +
            "                                            <div class='detail-reader'>\n" +
            "                                                <a href='/author/" + el.account.id + "'>" + el.account.userDetail.firstName +"\n" +
            "                                                    " + el.account.userDetail.lastName + "</a>\n" +
            "                                                <span style=\"font-size: .8rem\"> " + formatDate(el.createdDate) +"</span>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                        <div style=\"padding-left: 3.5rem\">\n" +
                                                        content +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"btn-box\">\n" +
            "                                        <button class=\"btn btn-sm btn-outline-danger btn-delete-reply\" id='delete_" + el.id + "'><i class=\"far fa-trash-alt\"></i></button>\n" +
            "                                    </div>\n" +
            "                                </div>"
        return res;
    }

    function formatDate(data) {
        let date = new Date(data);
        let day = date.getDate();
        let month = date.getMonth() + 1;
        let hour = date.getHours();
        let minute = date.getMinutes();
        if (day < 10) {
            day = "0" + day;
        }
        if (month < 10) {
            month = "0" + month;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minute < 10) {
            minute = "0" + minute;
        }
        let formatted_date = date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + minute;
        return formatted_date;
    }

})