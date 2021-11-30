$(document).ready(function (){
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";
    const keySearch = window.location.search;
    let errorTimeout;
    let successTimeOut;
    function clearTime() {
        clearTimeout(errorTimeout);
        clearTimeout(successTimeOut);
        $("#success").fadeOut();
        $("#fail").fadeOut();
    }
    function error(message){
        clearTime();
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        errorTimeout = setTimeout(function () {
            $("#fail").fadeOut(500);
        }, 3000)
    }
    function success(message){
        clearTime();
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        successTimeOut = setTimeout(function () {
            $("#success").fadeOut(500);
        }, 3000)
    }



    $(document).on("click", ".btn-delete-fav", function () {
        let id = $(this).attr('id').split("_")[1];
        if (id !== undefined || id !== "") {
            deleteFunc(parseInt(id));
        }

    })

    function deleteFunc(id) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/readLater/delete/" + id,
            method: "delete",
            dataType: 'json'
        }).done(function (res) {
            getData();
            $("#processing").removeClass("active");
            success(" Delete successfully");
        }).fail(function (res) {
            $("#processing").removeClass("active");
            getData();
            error(" " + res.responseJSON.message);
        })
    }

    let container = $("#historyMainContent");

    function getData(){
        $.ajax({
            url:'/api/user/readLater/posts'+keySearch,
            method:'get',
            dataType: 'json'
        }).done(function (res){
            if(res.bookmark.length <= 0){
                $("#btnDeleteAll").addClass("d-none")
            }
            fetchData(res);

        }).fail(function (){

        })
    }

    function fetchData(data){
        let res = "";
        if (data.bookmark.length <= 0) {
            res = "<div class=\"mt-4\">\n" +
                "       <p style=\"color: grey; font-weight: bold; text-align: center\">There is nothing here!</p>\n" +
                "  </div>"
        } else {

            data.bookmark.forEach(el => {
                res += write(el);
            })
        }
        container.html(res);
    }

    function write(el){
        let publicDate = formatDate(el.article.publishedDate);
        let item = " <div  class='card card-content'>\n" +
            "                            <div class='row no-gutters'>\n" +
            "                                <div class='col-md-5'>\n" +
            "                                    <a href='/posts/"+  el.article.id +"'> <img src='"+ el.article.imagePath + "' alt=\"...\"></a>\n" +
            "                                </div>\n" +
            "                                <div class=\"col-md-7\">\n" +
            "                                    <div class=\"card-body card-original\">\n" +
            "                                        <p class='card-original-cate'><a  href='/posts/topic/"+ el.article.topic.id +"'>" + el.article.topic.name+"</a>\n" +
            "                                        </p>\n" +
            "                                        <h5 class='card-title card-original-title'><a\n" +
            "                                                href='/posts/" + el.article.id +"'>" + el.article.title + "</a></h5>\n" +
            "                                        <div class='card-original-author'>\n" +
            "                                            <span><i class='fas fa-calendar-day'></i>"+ publicDate +"</span>\n" +
            "                                            <a href='/author/" +el.article.userAccount.id + "?page=1'><span class=''><i class='fas fa-user'></i></span>\n" +
            "                                                " + el.article.userAccount.userDetail.firstName +
            "                                                " + el.article.userAccount.userDetail.lastName +
            "                                            </a>\n" +
            "                                            <div class='dropdow dropMenuBox'>\n" +
            "                                                <button style='outline: none;' class='btn-drop-menu' type='button'\n" +
            "                                                        id='dropdownMenu' data-toggle='dropdown' aria-haspopup='true'\n" +
            "                                                        aria-expanded='false'>\n" +
            "                                                    <i class='fas fa-ellipsis-h'></i>\n" +
            "                                                </button>\n" +
            "                                                <div class='dropdown-menu drop-menu' style='padding: 0;'\n" +
            "                                                     aria-labelledby='dropdownMenu'>\n" +
            "                                                    <hr style='margin: 0;'>\n" +
            "                                                    <a class='dropdown-item btn-delete-fav' style='font-size: .8rem;'\n" +
            "                                                       id='delete_" + el.id +"' type=\"button\">Delete</a>\n" +
            "                                                </div>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                        <p class='card-original-short'>" + el.article.shortDescription +"</p>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>"


        return item;
    }

    function formatDate(data) {
        let date = new Date(data);
        let formatted_date = date.getDay() + "-" + date.getMonth() + "-" + date.getFullYear() + " " + date.getHours()+":"+date.getMinutes();
        return formatted_date;
    }

    $("#btnDeleteAll").click(function (){
        deleteAll();
    })

    function deleteAll(){
        $("#processing").addClass("active");
        $.ajax({
            url:'/api/user/readLater/deleteAll',
            method:'delete'
        }).done(function (){
            getData();
            $("#processing").removeClass("active");
            success(" Delete successfully");
        }).fail(function (){
            $("#processing").removeClass("active");
            error(" " + res.responseJSON.message);
        })
    }
})