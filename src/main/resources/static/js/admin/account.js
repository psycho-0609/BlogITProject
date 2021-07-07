$(document).ready(function (){
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
    $(document).on("click",".btn-edit", function (){
        let id = $(this).attr("id").split("_")[1];
        if(id !== null && id !== undefined){
            findAccount(id);
        }
    })
    function findAccount(id){
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/account/" + id,
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
        $("#title").text(data.email);
        $("#id").val(data.id);
        let statusEl = $("#status option");

        for (let i = 0; i < statusEl.length; i++) {
            if (data.status === parseInt(statusEl.eq(i).val())) {
                statusEl.eq(i).attr("selected", "selected");
            } else {
                statusEl.eq(i).removeAttr("selected");
            }
        }

        $("#modalAccountStatus").modal("show")
    }



    $("#formStatus").on("submit",function (e){
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        changeStatus(data);
    })


    function changeStatus(data){
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/account/status",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res) {
            getData(keySearch.val());
            $("#modalAccountStatus").modal("hide")
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res) {
            getData();
            $("#processing").removeClass("active");
            error(res.responseJSON.message);
        })
    }

    function getData(keySearch){
        $.ajax({
            url: "/api/admin/account/getAll?email="+keySearch,
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            fetchData(res)
        }).fail(function (res) {
        })
    }

    function fetchData(data){
        let res = "";
        if(data.length > 0){
            data.forEach(el => res += write(el))
        }else{
            res = " <td colspan='5'>\n" +
                "       <p style='color: grey; font-weight: bold; text-align: center'>There is nothing here!</p>\n" +
                "   </td>"
        }
        $("#tBody").html(res);
    }

    function write(el){
        let status;
        if(parseInt(el.status) === 1){
            status = "<p class=\"bg-success text-center text-white m-0\" style=\"border-radius: 10px; font-size: .8rem; padding: 0 .5rem\">Active</p>"
        }else{
            status = "<p class=\"bg-secondary text-center text-white m-0\" style=\"border-radius: 10px; font-size: .8rem; padding: 0 .5rem\">Disable</p>"
        }
        let item = " <tr>\n" +
            "                                    <td>"+el.id+"</td>\n" +
            "                                    <td><a href='/author/"+el.userDetail.id+"'>" + el.userDetail.firstName + el.userDetail.lastName + "</a></td>\n" +
            "                                    <td>"+el.email+"</td>\n" +
            "                                    <td>" + status + "</td>\n" +
            "                                    <td style=\"width: 10rem\" class=\"text-center\">\n" +
            "                                        <button type=\"button\" id='edit_"+ el.id +"' class=\"btn btn-warning btn-edit\">Edit</button>\n" +
            "                                    </td>\n" +
            "                                </tr>"

        return item;
    }

    var myTimeOut;
    var keySearch = $("#keySearch")
    $("#keySearch").keyup(function () {
        clearTimeout(myTimeOut);
        myTimeOut = setTimeout(function () {
            getData(keySearch.val());
        }, 700)

    })

})