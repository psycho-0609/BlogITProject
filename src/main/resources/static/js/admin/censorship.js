$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
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

    ///// admin role
    const articleId = $("#articleOfArticle").val();
    $(document).on("click", "#censorship", function () {
        getOne(articleId);

    })

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

    function getArticle(id) {
        $.ajax({
            url: "/api/admin/post/" + id,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            writeStatus(res)
        })
    }

    function writeStatus(res) {
        let result = "";
        if (res.published === 1) {
            result = " <div class=\"status published\">\n" +
                "      published\n" +
                "     </div>"
        }else{
            result = "<div class=\"status unlisted\">\n" +
                "          unlisted\n" +
                "        </div>"
        }
        $("#statusBox").html(result)
    }

    $("#published").change(function (e) {
        if (parseInt($(this).val()) === 1) {
            $("#prioritizeBox").removeClass("d-none");
        } else {
            $("#prioritizeBox").addClass("d-none");
        }
    })

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
            $("#modalArticleStatus").modal("hide");
            $("#processing").removeClass("active");
            getArticle(articleId);
            success(" " + res.message);
        }).fail(function (res) {
            $("#processing").removeClass("active");
            error(" " + res.responseJSON.message);
        })
    }

    $(document).on("click", "#deleteArticle", function () {
        swalAlert(articleId);

    })


    function deleteArticle(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/post/delete/" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            location.href = "/admin/post/allPosts?page=1";
        }).fail(function (res) {
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
})