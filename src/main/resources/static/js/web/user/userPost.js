$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    let statusType = parseInt($("#type").val());
    let container = $("#mainContentPosts");
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

    $(document).on('click', '.btnDeletePosts', function () {
        let id = $(this).attr("id").split("_")[1];
        if (id !== undefined && id !== "") {
            confirmDelete(id);
        }
    })

    function deletePost(id) {

        $("#processing").addClass("active");
        $.ajax({
            url: '/api/user/posts/delete/' + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            if(statusType === 1){
                getPostsPublished();
            }else if(statusType === 2){
                getPostsPrivate();
            }else if(statusType === 3){
                getPostsUnapproved();
            }

            $("#processing").removeClass("active");
            success(" Delete successfully");
        }).fail(function (res) {
            $("#processing").removeClass("active");
            if(statusType === 1){
                getPostsPublished();
            }else if(statusType === 2){
                getPostsPrivate();
            }else if(statusType === 3){
                getPostsUnapproved();
            }
            errorMessage(res.responseJSON.message);
        })
    }

    function confirmDelete(id) {
        swal({
            title: "Are you sure?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    deletePost(id);
                }
            });
    }

    function getPostsUnapproved() {
        $.ajax({
            url: '/api/user/posts/unapproved' + keySearch,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            console.log(res);
            fetchData(res);
        }).fail(function () {
            console.log("fail");
        })
    }

    function getPostsPublished() {
        $.ajax({
            url: '/api/user/posts/published'+keySearch,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            console.log(res);
            fetchData(res);
        }).fail(function () {
            console.log("fail");
        })
    }

    function getPostsPrivate() {
        $.ajax({
            url: '/api/user/posts/private'+keySearch,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            console.log(res);
            fetchData(res);
        }).fail(function () {
            console.log("fail");
        })
    }
    function fetchData(data) {
        let res = "";
        container.html("");
        if (data.length <= 0) {
            res = "<div class=\"mt-4\">\n" +
                "       <p style=\"color: grey; font-weight: bold; text-align: center\">There is nothing here!</p>\n" +
                "  </div>"
        } else {

            data.forEach(el => {
                res += write(el);
            })
        }
        container.html(res);

    }

    function write(el) {
        let published = "";
        let lastEdit = "";
        let url ="";
        if (el.modifiedDate == null) {
            lastEdit = formatDate(el.createdDate);
        } else {
            lastEdit = formatDate(el.createdDate);
        }


        if (el.publishedDate != null) {
            published = "<div class='card-original-author card-edit'>\n" +
                "     <span style='padding-top: .5rem'><i class='fas fa-calendar-day'></i> " + formatDate(el.publishedDate) + "</span>\n" +
                "</div>\n"
        }
        if(el.published === 1 && el.status === 1){
            url = "/posts/"
        }else{
            url = "/user/posts/"
        }
        let item = "<div id='mainContentPosts' class='card card-content mb-0'>\n" +
            "                        <div class='card-body p-0 card-original'>\n" +
            "                            <p class='card-original-cate' style='color: red'>" + el.topic.name + " </p>\n" +
            "                            <h5 class='card-title card-original-title'><a href='" + url + el.id + "'>" + el.title + "</a></h5>\n" + published +
            "                            <div class='d-flex mt-3'>\n" +
            "                                    <p style='color:grey; margin-right: 1rem;margin-bottom: 0'>Last edit: " + lastEdit + "</p>\n" +
            "                                <div class='dropdown'>\n" +
            "                                    <button class='btn-edit' style='outline: none;' type='button'\n" +
            "                                            id='' data-toggle='dropdown' aria-haspopup='true'\n" +
            "                                            aria-expanded='false'>\n" +
            "                                        <i class='fas fa-ellipsis-h'></i>\n" +
            "                                    </button>\n" +
            "                                    <div class='dropdown-menu dropmenu-edit p-0'\n" +
            "                                         aria-labelledby='dropdownMenuButton'>\n" +
            "                                        <a class='dropdown-item' href='/user/posts/edit/ " + el.id + "'>Edit</a>\n" +
            "                                        <a class='dropdown-item btnDeletePosts' type='button' id='delete_" + el.id + "'>Delete</a>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <hr>\n" +
            "                    </div>"

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

})
