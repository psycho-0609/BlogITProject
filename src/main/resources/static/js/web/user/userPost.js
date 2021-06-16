$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    let statusType = parseInt($("#type").val());
    let container = $("#mainContentPosts");
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

    $(document).on('click', '.btnDeletePosts', function () {
        let id = $(this).attr("id").split("_")[1];
        if (id !== undefined && id !== "") {
            deletePost(id);
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
            errorMessage(res.responseJSON.message);
        })
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

        let item = "<div id='mainContentPosts' class='card card-content mb-0'>\n" +
            "                        <div class='card-body p-0 card-original'>\n" +
            "                            <p class='card-original-cate' style='color: red'>" + el.topic.name + " </p>\n" +
            "                            <h5 class='card-title card-original-title'><a href='/posts/" + el.id + "'>" + el.title + "</a></h5>\n" + published +
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
        let formatted_date = date.getDay() + "-" + date.getMonth() + "-" + date.getFullYear() + " " + date.getHours()+":"+date.getMinutes();
        return formatted_date;
    }
})
