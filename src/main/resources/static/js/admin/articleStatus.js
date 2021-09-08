$(document).ready(function () {

    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    let type = parseInt($("#type").val());
    let keySearch = $("#keySearch");
    var errorTimeout;
    var successTimeOut;


    function error(message) {
        clearTime();
        $("#fail").html(fail + " " + message)
        $("#fail").fadeIn();
        errorTimeout = setTimeout(function () {
            $("#fail").fadeOut(500);
        }, 3000)
    }

    function success(message) {
        clearTime();
        $("#success").html(messSuccess + " " + message)
        $("#success").fadeIn();
        successTimeOut = setTimeout(function () {
            $("#success").fadeOut(500);
        }, 3000)
    }


    function clearTime() {
        clearTimeout(errorTimeout);
        clearTimeout(successTimeOut);
        $("#success").fadeOut();
        $("#fail").fadeOut();
    }

    $("#published").change(function (e) {
        if (parseInt($(this).val()) === 1) {
            $("#prioritizeBox").removeClass("d-none");
        } else {
            $("#prioritizeBox").addClass("d-none");
        }
    })
    $(document).on("click", ".btn-edit", function () {
        let id = $(this).attr("id").split("_")[1];
        getOne(id);

    })

    function getOne(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/post/" + id,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            writeData(res);
            $("#processing").removeClass("active");
        }).fail(function (res) {
            $("#processing").removeClass("active");
            error(res.responseJSON.message);
        })

    }

    function writeData(data) {
        $("#title").text(data.title);
        $("#id").val(data.id);
        let statusEl = $("#published option");

        for (let i = 0; i < statusEl.length; i++) {
            if (data.published === parseInt(statusEl.eq(i).val())) {
                statusEl.eq(i).attr("selected", "selected");
            } else {
                statusEl.eq(i).removeAttr("selected");
            }
        }

        if (parseInt(data.published) === 2) {
            $("#prioritizeBox").addClass("d-none");
        } else {
            $("#prioritizeBox").removeClass("d-none");
        }
        let proEl = $("#prioritize option")
        for (let i = 0; i < proEl.length; i++) {
            if (data.prioritize === parseInt(proEl.eq(i).val())) {
                proEl.eq(i).attr("selected", "selected");
            } else {
                proEl.eq(i).removeAttr("selected");
            }
        }
        $("#modalArticleStatus").modal("show")
    }

    $("#formPublished").on("submit", function (e) {
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        ajaxChangeStatus(data);
    })

    function ajaxChangeStatus(data) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/post/status",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res) {
            getData(getUrl(keySearch.val()));
            $("#modalArticleStatus").modal("hide");
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res) {
            $("#processing").removeClass("active");
            error(" " + res.responseJSON.message);
        })
    }

    function getData(url) {
        $("#processing").addClass("active");
        $.ajax({
            url: url,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            fetch(res);
            $("#processing").removeClass("active");
        }).fail(function (){
            $("#processing").removeClass("active");
        })
    }

    function fetch(data) {
        let res = "";
        let col;
        if (data.length > 0) {
            if (type === 1) {
                data.forEach(el => res += writePublic(el));
            } else if (type === 2) {
                data.forEach(el => res += writeUnlisted(el));
            } else if (type === 3) {
                data.forEach(el => res += writeAll(el));
            }

        } else {
            if (type === 1) {
                col = 6;
            } else if (type === 2) {
                col = 5;
            } else if (type === 3) {
                col = 6
            }
            res = " <td colspan='" + col + "'>\n" +
                "       <p style='color: grey; font-weight: bold; text-align: center'>There is nothing here!</p>\n" +
                "   </td>"
        }

        $("#mainDataBox").html(res);
    }

    function writePublic(el) {
        let lastEditDate = "";
        if (el.modifiedDate != null) {
            lastEditDate = "<td>" + formatDate(el.modifiedDate) + "</td>"
        } else {
            lastEditDate = "<td>" + formatDate(el.createdDate) + "</td>"
        }

        let prioritize = "";
        if (parseInt(el.prioritize) === 0) {
            prioritize = "Not set";
        } else {
            prioritize = el.prioritize;
        }

        let item = "<tr>\n" +
            "                                    <td style=\"width: 30rem\"><a href='/admin/post/" + el.id + "'>" + el.title + "</a>\n" +
            "                                    </td>\n" +
            "                                    <td><a href='/author/" + el.userAccount.id + "'>\n" + el.userAccount.userDetail.firstName +" " + el.userAccount.userDetail.lastName +
            "                                    </a></td>\n" + lastEditDate +
            "                                    <td>" + formatDate(el.publishedDate) + "</td>\n" +
            "                                    <td>" + prioritize + "</td>\n" +
            "                                    <td class='text-center' style=\"width: 10rem\">\n" +
            "                                        <button type=\"button\" id='edit_" + el.id + "' class=\"btn btn-warning btn-edit mb-2\">Edit</button>\n" +
            "                                        <button type=\"button\" id='delete_" + el.id + "' class=\"btn btn-danger btn-delete mb-2\">Delete</button>\n" +
            "                                    </td>\n" +
            "                                </tr>"

        return item;
    }

    function writeUnlisted(el) {
        let news = "";
        let lastEditDate = "";
        if (parseInt(el.news) === 1) {
            news = "news";
        }
        if (el.modifiedDate != null) {
            lastEditDate = "<td>" + formatDate(el.modifiedDate) + "</td>"
        } else {
            lastEditDate = "<td>" + formatDate(el.createdDate) + "</td>"
        }
        let item = "<tr class='" + news + "'>\n" +
            "              <td style=\"width: 30rem\"><a href='/admin/post/" + el.id + "'>" + el.title + "</a>\n" +
            "                                    </td>\n" +
            "                                   <td>" + el.topic.name + "</td>" +
            "                                    <td><a href='/author/" + el.userAccount.id + "'>\n" + el.userAccount.userDetail.firstName + el.userAccount.userDetail.lastName +
            "                                    </a></td>\n" + lastEditDate +
            "                                    <td class='text-center' style=\"width: 10rem\">\n" +
            "                                       <button type=\"button\" id='edit_" + el.id + "' class=\"btn btn-warning btn-edit mb-2\">Edit</button>\n" +
            "                                        <button type=\"button\" id='delete_" + el.id + "' class=\"btn btn-danger btn-delete mb-2\">Delete</button>\n" +
            "                                    </td>\n" +
            "              </tr>"

        return item;
    }

    function writeAll(el) {
        let news = "";
        let lastEditDate = "";
        if (el.modifiedDate != null) {
            lastEditDate = "<td>" + formatDate(el.modifiedDate) + "</td>"
        } else {
            lastEditDate = "<td>" + formatDate(el.createdDate) + "</td>"
        }
        if (parseInt(el.news) === 1) {
            news = "news";
        }
        let published = "";
        if (el.published === 1) {
            published = "<p class=\"bg-success text-center text-white m-0\" style=\"border-radius: 10px; font-size: .8rem; padding: 0 .5rem\">Published</p>";
        } else {
            published = "<p class=\"bg-secondary text-center text-white m-0\" style=\"border-radius: 10px; font-size: .8rem; padding: 0 .5rem\">Unlisted</p>"
        }

        let item = "<tr class='" + news + "'>\n" +
            "              <td style=\"width: 30rem\"><a href='/admin/post/" + el.id + "'>" + el.title + "</a>\n" +
            "                                    </td>\n" +
            "                                    <td>" + el.topic.name + "</td>\n" +
            "                                    <td><a href='/author/" + el.userAccount.id + "'>\n" + el.userAccount.userDetail.firstName + " " + el.userAccount.userDetail.lastName +
            "                                    </a></td>\n" + lastEditDate +
            "                                    <td>" + published + "</td>\n" +
            "                                    <td class=\"text-center\" style=\"width: 10rem\">\n" +
            "                                       <button type=\"button\" id='edit_" + el.id + "' class=\"btn btn-warning btn-edit mb-2\">Edit</button>\n" +
            "                                        <button type=\"button\" id='delete_" + el.id + "' class=\"btn btn-danger btn-delete mb-2\">Delete</button>\n" +
            "                                    </td>\n" +
            "                                </tr>"

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

/// search
    var myTimeOut;
    $("#keySearch").keyup(function () {
        clearTimeout(myTimeOut);
        myTimeOut = setTimeout(function () {
            getData(getUrl(keySearch.val()));
        }, 700)

    })

/// delete
    $(document).on("click", ".btn-delete", function () {
        let id = $(this).attr("id").split("_")[1];
        swalAlert(id);

    })


    function deleteArticle(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/post/delete/" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            getData(getUrl(keySearch.val()));
            $("#modalConfirmDelete").modal("hide");
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res) {
            getData(getUrl(keySearch.val()));
            $("#processing").removeClass("active");
            $("#modalConfirmDelete").modal("hide");
            error(" " + res.responseJSON.message);
        })
    }

    function swalAlert(id) {
        swal({
            title: "Are you sure?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    deleteArticle(id);
                }
            });
    }

    function getUrl(keySearch) {
        let url = "";
        if (type === 1) {
            url = "/api/admin/post/published?title=" + keySearch;
        } else if (type === 2) {
            url = "/api/admin/post/unlisted?title=" + keySearch;
        } else if (type === 3) {
            url = "/api/admin/post/all?title=" + keySearch;
        }
        return url;
    }


})