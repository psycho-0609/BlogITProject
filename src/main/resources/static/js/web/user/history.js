$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i>";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    const keySearch = window.location.search;

    let errorTimeout;
    let successTimeOut;
    function clearTime() {
        clearTimeout(errorTimeout);
        clearTimeout(successTimeOut);
        $("#success").fadeOut();
        $("#fail").fadeOut();
    }
    function errorMessage(message){
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

    $(document).on("click", ".btn-delete-his", function () {
        let id = $(this).attr('id').split("_")[1];
        if (id !== undefined || id !== "") {
            deleteFunc(parseInt(id));
        }

    })



    function deleteFunc(id) {
        // $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/history/delete/" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            getData();
            $("#processing").removeClass("active");
            success(" Delete successfully");
        }).fail(function (res) {
            // $("#processing").removeClass("active");
            getData();
            errorMessage(" " + res.responseJSON.message);
        })
    }

    function getData() {
        $.ajax({
            url: "/api/user/history/posts"+keySearch,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            console.log(res);
            if(res.length <= 0){
                $("#btnDeleteAll").addClass("d-none")
            }
            fetchData(res);
        }).fail(function (res) {

            errorMessage(" " + res.responseJSON.message);
        })
    }

    let container = $("#historyMainContent");

    function fetchData(data) {
        let res = "";
        if (data.length <= 0) {
            res = "<div class=\"mt-4\">\n" +
                "       <p style=\"color: grey; font-weight: bold; text-align: center\">There is nothing here!</p>\n" +
                "  </div>"
        } else {

            data.forEach(el => {
                res += "<div class=\"history-date\">\n" +
                    "                            <hr>\n" +
                    "                            <p>" + el.date + "</p>\n" +
                    "                            <hr>\n" +
                    "    </div>"
                el.historyArticle.forEach(item => {
                    res += write(item);
                })

            })
        }
        container.html(res);
    }

    function write(el) {
        let publicDate = formatDate(el.article.publishedDate);
        let urlImg = el.article.imagePath;
        let item = "<div class='card card-content'>\n" +
            "                            <div class='row no-gutters'>\n" +
            "                                <div class='col-md-5'>\n" +
            "                                    <a href='/posts/"+ el.article.id +"'> <img src='" + urlImg + "' ></a>\n" +
            "                                </div>\n" +
            "                                <div class='col-md-7'>\n" +
            "\n" +
            "                                    <div class='card-body card-original'>\n" +
            "                                        <p class='card-original-cate'><a href='/posts/topic/"+ el.article.topic.id +"'>" + el.article.topic.name + "</a>\n" +
            "                                        </p>\n" +
            "                                        <h5 class='card-title card-original-title'><a\n" +
            "                                                 href='/posts/"+ el.article.id +"'>" + el.article.title + "</a></h5>\n" +
            "                                        <div class='card-original-author'>\n" +
            "                                            <span><i class='fas fa-calendar-day'></i> " + publicDate + "</span>\n" +
            "                                            <a href='/author/"+el.article.userAccount.userDetail.id +"?page=1'><span class=''><i class='fas fa-user'></i></span>\n" +
            "                                                " + el.article.userAccount.userDetail.firstName + "\n" +
            "                                                " + el.article.userAccount.userDetail.lastName + "\n" +
            "                                            </a>\n" +
            "                                            <div class='dropdow dropMenuBox'>\n" +
            "                                                <button style='outline: none;' class='btn-drop-menu' type='button'\n" +
            "                                                        id='dropdownMenu' data-toggle='dropdown' aria-haspopup='true'\n" +
            "                                                        aria-expanded='false'>\n" +
            "                                                    <i class='fas fa-ellipsis-h'></i>\n" +
            "                                                </button>\n" +
            "                                                <div class='dropdown-menu drop-menu' style='padding: 0;'\n" +
            "                                                     aria-labelledby='dropdownMenu'>\n" +
            "                                                    <hr style='margin: 0;'>\n" +
            "                                                    <a class='dropdown-item btn-delete-his' style='font-size: .8rem;'\n" +
            "                                                       id='delete_" + el.id + "' type='button'>Delete</a>\n" +
            "                                                </div>\n" +
            "                                            </div>\n" +
            "\n" +
            "                                        </div>\n" +
            "                                        <p class='card-original-short'>" + el.article.shortDescription + "</p>\n" +
            "                                    </div>\n" +
            "\n" +
            "\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>"

        return item;
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

    function deleteAll(){
        // $("#processing").addClass("active");
        $.ajax({
            url:"/api/user/history/deleteAll",
            method:"delete"
        }).done(function (){
            $("#processing").removeClass("active");
            getData();
            success(" Delete successfully");
        }).fail(function (){
            // $("#processing").removeClass("active");
        })
    }

    $("#btnDeleteAll").click(function (){
        deleteAll();
    })
})