$(document).ready(function (){
    let stompClient;
    const url = window.location.origin;
    let userId =$("#userId").val();
    function connectToChat(){
        console.log("connect to chat")
        let socket = new SockJS(url + '/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log("connected to: " + frame);
            stompClient.subscribe("/topic/messages/toUser/" + userId, function (response) {
                let data = JSON.parse(response.body);
                console.log(data);
                $("#contentBox").append(writeOtherMessage(data));
                scrollTop();
            });

        });
    }
    function sendMsg(data) {
        stompClient.send("/app/chat/admin", {}, JSON.stringify(data));
    }

    function getHistoryMess(){
        $.ajax({
            url:"/api/user/chat/history",
            method:"get",
            dataType:"json"
        }).done(function (res){
            writeInforUser(res.user);
            writeHistoryMessage(res.messages);
        })
    }


    function writeInforUser(data){
        $("#imgUser").attr("src",data.thumbnail);
        $("#nameUser").text(data.firstName +" "+ data.lastName);
    }

    function writeHistoryMessage(data){
        let res = ""
        if(data.length>0){
            data.forEach(el => res += writeItemMess(el))
        }
        $("#contentBox").html(res);
        scrollTop();
    }

    function writeItemMess(el){
        let res = ""
        if(el.fromId === parseInt(userId)){
            res = writeMyMessage(el)
        }else{
            res = writeOtherMessage(el);
        }
        return  res;
    }

    function writeMyMessage(data){
        return  "<li class=\"clearfix\">\n" +
            "                                            <div class=\"message-data align-right\">\n" +
            "                                                <span class=\"message-data-time\">"+ formatDate(data.createdDate) +"</span>\n" +
            "                                            </div>\n" +
            "                                            <div class=\"message other-message float-right\">\n" +
            "                                                <p class=\"message-user\">\n" + data.content +
            "                                                </p>\n" +
            "                                            </div>\n" +
            "                                        </li>"
    }

    function writeOtherMessage(data){
        return  "<li>\n" +
            "                                            <div class=\"message-data\">\n" +
            "                                                <span class=\"message-data-time\">"+ formatDate(data.createdDate) +"</span>\n" +
            "                                            </div>\n" +
            "                                            <div class=\"message my-message\">\n" +
            "                                                <p class=\"message-user\">\n" + data.content +
            "                                                </p>\n" +
            "                                            </div>\n" +
            "                                        </li>"
    }

    function scrollTop(){
        $("#chatHistory").scrollTop(parseInt($("#contentBox").css("height").replaceAll("px","")));
    }

    function getCurrentTime() {
        let date = new Date();
        return date.toISOString();
    }
    // disable btn when content is empty
    function disableBtnSend(){
        if($("#messageContent").val() === undefined || $("#messageContent").val()=== ""){
            $("#sendBtn i").css("color","#94C2ED");
        }else{
            $("#sendBtn i").css("color","rgb(21, 21, 248)");
        }
    }


    $("#messageContent").keyup(function (){
        disableBtnSend();
    })

    $("#formSubmit").on("submit",function (e){
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        data["fromAccountId"] =userId;
        data["createdDate"] = getCurrentTime();
        if(data['content'] !== ""){
            sendMsg(data);
            $("#contentBox").append(writeMyMessage(data));
            scrollTop();
        }
        $("#messageContent").val("");
    })
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

    connectToChat(userId);
    disableBtnSend();
    getHistoryMess();
})