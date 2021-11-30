$(document).ready(function () {
    let stompClient;
    const url = window.location.origin;

    function connectSocket() {
        // $("#contentBox").html("");
        console.log("connect to chat")
        let socket = new SockJS(url + '/notification');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log("connected to: " + frame);
            stompClient.subscribe("/topic/notification/toAdmin", function (response) {
                $("#noNotify").css("display","none");
                let data = JSON.parse(response.body);
                console.log(data);
                if( !isNaN(parseInt($("#counterNew").text()))){
                    $("#counterNew").text(parseInt($("#counterNew").text()) + 1)
                }else{
                    $("#counterNew").text("1")
                }

                $("#notificationCenter").prepend(writeNotification(data));

            });

        });
    }

    connectSocket();

    function ajaxGetAllNotification(){
        $.ajax({
            url:"/api/admin/notification/getAll",
            method:"get",
            dataType:"json"
        }).done(function (res){
            fetchData(res);
        })
    }

    function fetchData(data){
        let res = "";
        if(data.totalNew > 0){
            $("#counterNew").text(data.totalNew)
        }else{
            $("#counterNew").text("")
        }
        if(data.listNotification !==  null && data.listNotification.length > 0){
            data.listNotification.forEach(el => res += writeNotification(el))
        }else{
            res = "<p id='noNotify'  class='text-center' style='color: #aeaeae'>There is nothing here !</p>"
        }
        $("#notificationCenter").html(res);
    }

    function writeNotification(notification) {
        let content = ""
        if(notification.status === 1){
            content = "<span class=\"font-weight-bold text-content\">" + notification.content + "</span>\n"
        }else{
            content = "<span class=\"text-content\">" + notification.content + "</span>\n"

        }
        let res = " <a class=\"dropdown-item d-flex align-items-center btn-link\" id='" + notification.id + "' href='" + notification.url + "'>\n" +
            "                                    <div class=\"mr-3\">\n" +
                                                    getIcon(notification.type)+
            "                                    </div>\n" +
            "                                    <div>\n" +
            "                                        <div class=\"small text-gray-500\">" + formatDate(notification.createdDate) + "</div>\n" +
            "                                        " + content +
            "                                    </div>\n" +
            "                                </a>"

        return res;
    }
    ajaxGetAllNotification();

    $(document).on("click",".btn-link",function (e){
        e.preventDefault();
        let notifyId = $(this).attr("id");
        let url = $(this).attr("href");
        if(notifyId !== null && notifyId !== undefined && notifyId !== "" && url !== undefined && url !== ""){
           let formData = new FormData();
           formData.append("id",notifyId);
            updateStatus(formData,url);
        }


    })

    function updateStatus(data, url){
        $.ajax({
            url:"/api/admin/notification/updateStatus",
            method:"post",
            contentType: false,
            processData:false,
            data: data,
            dataType: 'json'
        }).done(function (res){
            let domain = window.location.origin;
            location.href = domain + url;
        })
    }

    $("#btnMarkAll").click(function (){
        readAll();
    })
    function readAll(){
        $.ajax({
            url:"/api/admin/notification/readAll",
            method:"put",
            dataType:"json"
        }).done(function (){
            ajaxGetAllNotification();
        })
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

    function getIcon(type){
        let icon = "";
        if(type === 2){
            icon = "<div class=\"icon-circle bg-danger\">\n" +
            "              <i class=\"fas fa-flag text-white\"></i>\n" +
        "            </div>\n";
        }else{
            icon = "<div class=\"icon-circle bg-primary\">\n" +
            "               <i class=\"fas fa-file-alt text-white\"></i>\n" +
        "            </div>\n"
        }
        return icon;
    }

})