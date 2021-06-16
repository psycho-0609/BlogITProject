$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i>";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    const keySearch = window.location.search;

    function errorMessage(message) {
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        setTimeout(function () {
            $("#fail").fadeOut(3000);
        }, 1500)
    }

    function success(message) {
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

    $(document).on("click", ".btn-delete-his", function () {
        let id = $(this).attr('id').split("_")[1];
        if (id !== undefined || id !== "") {
            deleteFunc(parseInt(id));
        }

    })

    function saveReadLater(data) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/readLater/create",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res) {
            $("#processing").removeClass("active");
            success(" Saved to Read Later successfully");
        }).fail(function (res) {
            $("#processing").removeClass("active");
            errorMessage(" " + res.responseJSON.message);
        })
    }

    function deleteFunc(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/history/delete/" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            getData();
            $("#processing").removeClass("active");
            success(" Delete successfully");
        }).fail(function (res) {
            $("#processing").removeClass("active");
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
            "                                    <a href=''> <img src='" + urlImg + "' ></a>\n" +
            "                                </div>\n" +
            "                                <div class='col-md-7'>\n" +
            "\n" +
            "                                    <div class='card-body card-original'>\n" +
            "                                        <p class='card-original-cate'><a href=''>" + el.article.topic.name + "</a>\n" +
            "                                        </p>\n" +
            "                                        <h5 class='card-title card-original-title'><a\n" +
            "                                                href=''>" + el.article.title + "</a></h5>\n" +
            "                                        <div class='card-original-author'>\n" +
            "                                            <span><i class='fas fa-calendar-day'></i> " + publicDate + "</span>\n" +
            "                                            <a href=''><span class=''><i class='fas fa-user'></i></span>\n" +
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
            "                                                    <a class='dropdown-item btn-add-read-later'\n" +
            "                                                       style='font-size: .8rem;'\n" +
            "                                                       id='readLate_" + el.article.id + "' type='button'>Add to\n" +
            "                                                        Read later</a>\n" +
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
        let formatted_date = date.getDay() + "-" + date.getMonth() + "-" + date.getFullYear() + " " + date.getHours()+":"+date.getMinutes();
        return formatted_date;
    }

    function deleteAll(){
        $("#processing").addClass("active");
        $.ajax({
            url:"/api/user/history/deleteAll",
            method:"delete"
        }).done(function (){
            $("#processing").removeClass("active");
            getData();
            success(" Delete successfully");
        }).fail(function (){
            $("#processing").removeClass("active");
        })
    }

    $("#btnDeleteAll").click(function (){
        deleteAll();
    })
})