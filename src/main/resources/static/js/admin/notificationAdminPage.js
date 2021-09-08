$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i> ";
    let errorTimeout;
    function clearTime() {
        clearTimeout(errorTimeout);
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
    function ajaxGetAllNotification() {
        $.ajax({
            url: "/api/admin/notification/getAll",
            method: "get",
            dataType: "json"
        }).done(function (res) {
            fetchData(res);
        })
    }

    ajaxGetAllNotification();

    function fetchData(data) {
        let res = "";
        if (data.listNotification !== null && data.listNotification.length > 0) {
            data.listNotification.forEach(el => res += writeNotification(el))
        } else {
            res = "<p id='noNotify' class='text-center' style='color: #aeaeae'>There is nothing here !</p>"
        }
        $("#notificationBox").html(res);
    }

    function writeNotification(el) {
        let content = "";
        if(el.status === 1){
            content = "<p class='font-weight-bold' style='color: inherit'>" + el.content + "</p>"
        }else{
            content = "<p class='' style='color: inherit'>" + el.content + "</p>"
        }

        let res = "                <div class=\"notification-item\">\n" +
            "                    <a href='" + el.url + "' class=\"notification-detail\">\n" +
            "                        <div class=\"mr-3\">\n" +
            getIcon(el.type) +
            "                        </div>\n" +
            "                        <div class=\"notify-infor\">\n" +
            "                            <div class=\"date\">" + formatDate(el.createdDate) + "</div>\n" +
            "                            <div>\n" +
                                                content +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </a>\n" +
            "                    <div>\n" +
            "                        <button class=\"btn-action btn-delete-notification\" id='" + el.id + "'><i class=\"fas fa-trash\"></i></button>\n" +
            "                    </div>\n" +
            "                </div>\n"
        return res;
    }

    function getIcon(type) {
        let icon = "";
        if (type === 2) {
            icon = "<div class=\"icon-circle bg-danger\">\n" +
                "              <i class=\"fas fa-flag text-white\"></i>\n" +
                "            </div>\n";
        } else {
            icon = "<div class=\"icon-circle bg-primary\">\n" +
                "               <i class=\"fas fa-file-alt text-white\"></i>\n" +
                "            </div>\n"
        }
        return icon;
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

    $(document).on("click", ".btn-delete-notification", function () {
        let id = $(this).attr("id");
        if (id !== null && id !== undefined && id !== '') {
            deleteOne(id);
        }
    })


    function ajaxDeleteAll() {
        $.ajax({
            url: "/api/admin/notification/delete/all",
            method: 'delete',
            dataType: 'json'
        }).done(function (res) {
            ajaxGetAllNotification();
        }).fail(function (res){
            errorMessage(res.responseJSON.message)
        })
    }

    function deleteOne(id) {
        $.ajax({
            url: '/api/admin/notification/delete/' + id,
            method: 'delete',
            dataType: 'json'
        }).done(function () {
            ajaxGetAllNotification();
        })
    }

    $("#deleteAll").click(function () {
        swalAlertDeleteAll();
    })
    $("#markAsRead").click(function () {
        readAll();
    })

    function readAll() {
        $.ajax({
            url: "/api/admin/notification/readAll",
            method: "put",
            dataType: "json"
        }).done(function () {
            ajaxGetAllNotification();
        }).fail(function (res){
            errorMessage(res.responseJSON.message)
        })
    }

    function swalAlertDeleteAll() {
        swal({
            title: "Are you sure?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    ajaxDeleteAll();
                }
            });
    }
})