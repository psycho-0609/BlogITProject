$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    var errorTimeout;
    var successTimeOut;
    let articleId = $("#articleId").val();
    const keySearch = window.location.search;

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
        if(id !== undefined && id !== ""){
            ajaxDelete(id);
        }
    })

    function ajaxDelete(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/report/delete/" + id,
            method: "delete",
            type: "json"
        }).done(function (res) {
            getData();
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res){
            getData();
            $("#processing").removeClass("active");
            error(res.responseJSON.message);
        })
    }

    function getData() {
        $.ajax({
            url: "/api/admin/report/getAll" + keySearch,
            method: "get",
            type: "json"
        }).done(function (res) {
            fetchData(res);
        })
    }

    function fetchData(data) {
        let res = "";
        if (data.reports.length > 0) {
            data.reports.forEach(el => res += write(el))
        }
        $("#mainDataBox").html(res);
    }

    function write(el) {
        let item = "<tr>\n" +
            "                                    <td>" + el.reasonReport.name + "</td>\n" +
            "                                    <td>" + el.comment + "</td>\n" +
            "                                    <td>" + formatDate(el.createdDate) + "</td>\n" +
            "                                    <td style=\"width: 10rem\" class=\"text-center\">\n" +
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
})