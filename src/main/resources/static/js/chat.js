$(document).ready(function (){
    let stompClient;
    let newMessages = new Map();
    let toId;
    const url = 'http://localhost:8081';
    // user id
    let sendFromId = $("#userId").val();
    function connectToChat(id){
        $("#contentBox").html("");
        console.log("connect to chat")
        let socket = new SockJS(url + '/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log("connected to: " + frame);
            stompClient.subscribe("/topic/messages/toAdmin", function (response) {

                let data = JSON.parse(response.body);
                console.log(data)
                if(parseInt(toId) === data.fromId){
                    $("#contentBox").append(writeOtherMessage(data));
                    scrollTop();
                }else{
                    console.log("not user")
                }
                // console.log(data);


            });

        });
    }

    function sendMsg(data) {
        stompClient.send("/app/chat/" + toId, {}, JSON.stringify(data));

    }

    $("#formSubmit").on("submit",function (e){
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        data["fromAccountId"] = sendFromId;
        data["createdDate"] = getCurrentTime();
        console.log(data);
        console.log(getCurrentTime());
        if(data['content'] !== ""){
            sendMsg(data);
            $("#contentBox").append(writeMyMessage(data));
            scrollTop();
        }
        $("#messageContent").val("");

    })

// disable btn when content is empty
    function disableBtnSend(){
        if($("#messageContent").val() === undefined || $("#messageContent").val()=== ""){
            $("#sendBtn i").css("color","#94C2ED");
        }else{
            $("#sendBtn i").css("color","rgb(21, 21, 248)");
        }
    }
    disableBtnSend();

    $("#messageContent").keyup(function (){
        disableBtnSend();
    })


    function getCurrentTime() {
        let date = new Date();
        return date.toISOString();
    }
    /// get user
    function ajaxGetUser(name){
        if(name === undefined || name === null){
            name = "";
        }
        $.ajax({
            url:"/api/admin/chat/getUser?name="+name,
            method:"get",
            dataType:"json"
        }).done(function (res){
            fetchGetUser(res);
        })
    }

    function fetchGetUser(data){
        let res = "";
        if(data != null && data.length > 0){
            data.forEach(el => res += write(el))
        }else{
            res = "<li class='item-user'><p class='text-center'>No user found</p></li>"
        }

        $("#listUser").html(res);
    }

    function write(el){
        let res = " <li class=\"item-user\" id='user_"+el.id+"'>\n" +
            "                                        <img alt=\"avatar\" class=\"avatar\" src='" + el.thumbnail +"'\n" +
            "                                             >\n" +
            "                                        <div class=\"char-user\">\n" +
            "                                            <div class=\"name\">"+el.firstName +" " + el.lastName + "</div>\n" +
            "\n" +
            "                                        </div>\n" +
            "                                    </li>"
        return res;
    }
    ajaxGetUser("");
    var myTimeOut;
    $("#searchUser").keyup(function (){
        clearTimeout(myTimeOut);
        myTimeOut = setTimeout(function () {
            ajaxGetUser($("#searchUser").val());
        }, 700)

    })

    // select user to chat
    $(document).on("click",".item-user", function (){
        let id = $(this).attr("id").split("_")[1];
        toId = id;
        if(id !== undefined && id!== "" && id !== null){
            connectToChat(id);
            historyMessage(id);
            $("#displaySelect").css("display","none")
            $("#mainChat").css("display","block");
        }

    })

///history message
    function historyMessage(id){
        $.ajax({
            url:"/api/admin/chat/history?userId="+id,
            method: "get",
            dataType: "json"
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

        if(el.fromId === parseInt(sendFromId)){
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


/// format date
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