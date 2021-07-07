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

    $(document).on("click", ".btn-delete", function () {
        let id = $(this).attr("id").split("_")[1];
        if (id !== undefined && id != null && !isNaN(id)) {
            swalAlert(id);
        }
    })

    function ajaxDelete(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/report/delete?postId=" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            getData();
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res) {
            getData();
            $("#processing").removeClass("active");
            error(res.responseJSON.message);

        })
    }

    function getData() {
        $.ajax({
            url: "/api/admin/report",
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            fetchData(res);
        }).fail(function () {

        })
    }

    function fetchData(data) {
        let res = "";
        if (data.length > 0) {
            data.forEach(el => res += write(el));
        } else {
            res = " <td colspan='5'>\n" +
                "       <p style='color: grey; font-weight: bold; text-align: center'>There is nothing here!</p>\n" +
                "   </td>"
        }

        $("#mainDataBox").html(res);
    }

    function write(el) {
        let news = el.news === 1 ? "news" : "";
        let item = "<tr class='" + news + "'>\n" +
            "                                    <td style=\"width: 30rem\"><a href='/admin/post/" + el.article.id + "'>" + el.article.title + "</a>\n" +
            "                                    </td>\n" +
            "                                    <td>" + el.article.topic.name + "</td>\n" +
            "                                    <td><a th:href=\"@{'/author/'+${report.article.userAccount.id}}\">\n" +
            "                                        " + el.article.userAccount.userDetail.firstName + "\n" +
            "                                        " + el.article.userAccount.userDetail.lastName + "\n" +
            "                                    </a></td>\n" +
            "                                    <td>" + el.count + "</td>\n" +
            "                                    <td style=\"width: 11rem\" class=\"text-center\">\n" +
            "                                        <a href='/admin/report/all?postId= " + el.article.id + "' class=\"btn btn-warning mb-2\">Detail</a>\n" +
            "                                        <button type=\"button\" id='delete_" + el.article.id + "' class=\"btn btn-danger btn-delete mb-2\">Delete</button>\n" +
            "                                    </td>\n" +
            "                                </tr>"

        return item;
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
                    ajaxDelete(id);
                }
            });
    }



})