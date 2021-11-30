$(document).ready(function () {
    var myTimeOut;
    $("#keySearch").keyup(function () {
        clearTimeout(myTimeOut);
        myTimeOut = setTimeout(function () {
            getData();
        }, 700)

    })

    function getData() {
        $.ajax({
            url: "/api/admin/comment/getAll?title=" + $("#keySearch").val(),
            method: "get",
            dataType: "json"
        }).done(function (res) {
            fetch(res);
        }).fail(function () {

        })
    }

    function fetch(data) {
        let res = "";
        if (data.length <= 0) {
            res = " <tr>\n" +
                "       <td colspan=\"5\">\n" +
                "               <p style='color: grey; font-weight: bold; text-align: center'>There is nothing\n" +
                "                here!</p>\n" +
                "        </td>\n" +
                "   </tr>"
        } else {
            data.forEach(el => res += write(el));
        }

        $("#tBody").html(res);
    }

    function write(el) {
        let res = "<tr>\n" +
            "                                    <td>" + el.article.id + "</td>\n" +
            "                                    <td><a href='/admin/comment/detail?postId=" + el.article.id + "'>" + el.article.title + "</a> </td>\n" +
            "                                    <td><a href='/author/" + el.article.userAccount.id + "?page=1'>" + el.article.userAccount.userDetail.firstName + " " + el.article.userAccount.userDetail.lastName + "</a></td>\n" +
            "                                    <td>" + el.totalComment + "</td>\n" +
            "                                    <td style=\"width: 10rem\" class=\"text-center\">\n" +
            "                                        <a href='/admin/comment/detail?postId=" + el.article.id + "' class=\"btn btn-warning btn-edit\">Detail</a>\n" +
            "                                    </td>\n" +
            "                                </tr>"
        return res;

    }

})