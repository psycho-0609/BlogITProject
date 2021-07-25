$(document).ready(function () {
    let articleId = $("#articleOfArticle").val();
    $("#formComment").on('submit', function (e) {
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if (validateData(data)) {
            ajaxCreate(data);
        }
    })

    function validateData(data) {
        console.log(data);
        if (data.content === undefined || data.content === "") {
            $("messageError").text("Content is required!")
            return false;
        } else {
            $("").text("Content is required!")
            return true;
        }
    }

    function ajaxCreate(data) {
        $.ajax({
            url: "/api/user/comment/create",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res) {
            $("#contentComment").val("");
            getData();
        })
    }

    function getData() {
        $.ajax({
            url: `/api/user/comment/getAll?postId=${articleId}`,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            fetchData(res);
        })
    }

    function fetchData(data) {
        let res = "";
        data.forEach(el => res += write(el));
        $("#commentBox").html(res);
    }

    function write(el) {
        let res = " <div class=\"comment\" id=\"commentBox\">" +
            "<div class='comment-reader'>\n" +
            "                            <div class='infor-reader'>\n" +
            "                                <a href='/author/" + el.account.id + "' class=\"article-img-author\">\n" +
            "                                    <img src='" + el.account.userDetail.thumbnail + "' alt=''>\n" +
            "                                </a>\n" +
            "                                <div class='detail-reader'>\n" +
            "                                    <a href='/author/" + el.account.id + "'>" + el.account.userDetail.firstName + " " + el.account.userDetail.lastName + "</a>\n" +
            "                                    <span style='font-size: .8rem'>" + formatDate(el.createdDate) + "</span>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                            <div style='padding-left: 3.5rem'  class=\"content-comment-box\"><p class='mb-1 content-comment'>" + el.content + "</p>\n" +
            "                            <button class='btn btn-reply' style='font-size: .8rem' id='btnReply_" + el.id + "'>Reply</button>\n" +
            "                            <button class='btn btn-edit-comment' style='font-size: .8rem' id='btnEdit_" + el.id + "'>Edit</button></div>\n" +
            "                        </div>" +
            "<div class=\"subcomment\"></div>" +
            "<div class=\"editComment\"></div>" +
                replyComment(el.replyComment) +
            "</div>"
        return res;
    }


    function replyComment(data) {
        let res = "";
        if(data != null && data.length > 0){
            data.forEach(el => res += writeReply(el))
        }

        return res;
    }

    function writeReply(el) {
        let res ="<div style='padding-left: 3.5rem'>"+
            "<div class='infor-reader'>\n" +
            "           <a href='/author/" + el.account.id + "' class=\"article-img-author\">\n" +
            "            <img src='" + el.account.userDetail.thumbnail + "' alt=''>\n" +
            "             </a>\n" +
            "              <div class='detail-reader'>\n" +
            "                    <a href='/author/" + el.account.id + "'>" + el.account.userDetail.firstName + " " + el.account.userDetail.lastName + "</a>\n" +
            "                        <span style='font-size: .8rem'>" + formatDate(el.createdDate) + "</span>\n" +
            "               </div>\n" +
            "              </div>\n" +
            "        <div style='padding-left: 3.5rem'><p class='mb-1'>" + el.content + "</p>\n" +
            "        </div>\n"+
            "</div>"
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

    const el = '<div class="article-comment reply-comment" id="subComment">'
        + '<a href="" class="article-img-author">'
        + '<img src="' + $("#avatarUser").attr("src") + '" alt="">'
        + '</a>'
        + '<form action="" id="formReply" class="w-100">'
        + '<input id="id" name="id" type="hidden"/>'
        + '<input id="commentId" name="commentId" type="hidden"/>'
        + '<textarea name="content" id="content" class="form-control mb-3" rows="3"></textarea>'
        + '<div id="errorMess" style="color: red"></div>'
        + '<div class="text-right">'
        + '<button type="button" class="btn btn-secondary mr-2" id="btnCancel">Cancel</button>'
        + '<button type="submit" class="btn btn-primary" id="btnSubmitReply">Reply</button>'
        + '</div>'
        + '</form>'
        + '</div>';

    const editComment = '<div class="article-comment edit-comment" >'
        +'<a href="" class="article-img-author">'
        + '<img src="' + $("#avatarUser").attr("src") + '" alt="">'
        + '</a>'
        + '<form action="" class="w-100" id="formEditComment">'
        + '<input type="hidden" id="id" name="id"/>'
        + '<input type="hidden" id="articleId" name="articleId"/>'
        + '<textarea name="content" id="contentComment" class="form-control mb-3" rows="3"></textarea>'
        + '<div id="errorMess" style="color: red"></div>'
        + '<div class="text-right">'
        + '<button type="button" class="btn btn-secondary mr-2" id="btnCancelEditComment">Cancel</button>'
        + '<button type="submit" class="btn btn-primary">Update</button>'
        + '</div>'
        + '</form>'
        +'</div>'
// reply comment
    $(document).on("click", ".btn-reply", function () {
        hideFormEditComment();
        hideFormSubComment();
        $(this).css("display", "none");
        let div = $(this).closest('div').parent().parent().children(".subcomment");
        div.html(el)
        $("#commentId").val($(this).attr("id").split("_")[1]);
        SubComment(div, $(this));
        submitFormReply();
    })

    function SubComment() {
        $("#btnCancel").click(function () {
            hideFormSubComment();
        })
    }

    function hideFormSubComment() {

        let editReply = $(".edit-reply-comment");
        let els = $(".subcomment");
        let btnReply = $(".btn-reply")
        let mainReply = $(".main-content-reply-comment");
        let max = editReply.length;
        if(max < els.length){
            max = els.length;
        }else if(max < btnReply.length){
            max = btnReply.length;
        }else if(max < mainReply.length){
            max = mainReply.length;
        }
        for (let i = 0; i < max; i++) {
            els.eq(i).html("");
            btnReply.eq(i).css("display", "inline-block");
            editReply.eq(i).html("");
            mainReply.eq(i).css("display","block");
        }

    }

    function submitFormReply() {

        $("#formReply").on("submit", function (e) {
            e.preventDefault();
            let data = {};
            let formData = $(this).serializeArray();
            $.each(formData, function (i, v) {
                data["" + v.name + ""] = v.value;
            });
            if(data["id"] === undefined || data["id"] === ""){
                data["id"] = null;
            }
            if (data["content"] === undefined || data["content"] === "") {
                $("#errorMess").text("Content is require");
            } else {
                $("#errorMess").text("");
                ajaxReplyComment(data);
            }
        })
    }

    function ajaxReplyComment(data) {
        $.ajax({
            url: "/api/user/replyComment/create",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function () {
            getData();
            hideFormEditComment();
            hideFormSubComment();
        })
    }
// edit comment
    $(document).on("click", ".btn-edit-comment", function () {
        hideFormSubComment();
        hideFormEditComment();
        let id = $(this).attr("id").split("_")[1];
        let commentBox = $(this).closest('div').parent().parent().children(".comment-reader");
        commentBox.css("display","none")
        let div = $(this).closest('div').parent().parent().children(".editComment");
        div.html(editComment)
        $("#formEditComment #contentComment").val(commentBox.children(".content-comment-box").children(".content-comment").text())
        $("#formEditComment #id").val(id);
        $("#formEditComment #articleId").val(articleId);
        cancelEditComment()
        handleEditComment();

    })
    function cancelEditComment (){
        $("#btnCancelEditComment").click(function () {
            hideFormEditComment()
        })
    }

    function hideFormEditComment(){
        let els =$(".edit-comment")
        let commentBox = $(".comment-reader");
        console.log(commentBox);
        for (let i = 0; i < (els.length > commentBox.length ? els.length : commentBox.length); i++) {
            els.eq(i).html("");
            commentBox.eq(i).css("display", "block")
        }
    }

    function handleEditComment(){
        $("#formEditComment").on("submit",function (e){
            e.preventDefault();
            let data = {};
            let formData = $(this).serializeArray();
            $.each(formData, function (i, v) {
                data["" + v.name + ""] = v.value;
            });
            console.log(data);
            if (data["content"] === undefined || data["content"] === "") {
                $("#formEditComment #errorMess").text("Content is require");
            } else {
                $("#formEditComment #errorMess").text("");
                ajaxEditComment(data);
            }
        })

    }

    function ajaxEditComment(data){
        $.ajax({
            url: "/api/user/comment/create",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function () {
            getData();
            hideFormEditComment();
            hideFormSubComment();
        })
    }


    /// edit reply
    $(document).on("click",".btn-edit-reply", function (){
        hideFormEditComment();
        hideFormSubComment();
        let parentDiv = $(this).parent().parent().parent();
        console.log(parentDiv);
        let mainContent = parentDiv.children(".main-content-reply-comment");
        let divForm =  parentDiv.children(".edit-reply-comment");
        let id = $(this).attr("id").split("_")[1];
        mainContent.css("display","none");
        divForm.html(el);
        $("#formReply #id").val(id)
        $("#formReply #content").val($(this).parent().children(".reply-content").text());
        $("#formReply #articleId").val(articleId);
        $("#formReply #btnSubmitReply").text("Update");
        SubComment();
        submitFormReply();
    })
})