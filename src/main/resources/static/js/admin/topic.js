$(document).ready(function () {
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";

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

    $("#formEditTopic").on("submit", function (e) {
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if (validateData(data) === true) {
            ajaxEdit(data);
        }

    })

    function validateData(data) {
        let count = 0;
        if (data.name === undefined || data.name === "") {
            $("#nameMessage").text("Name is required");
        } else {
            $("#nameMessage").text("");
            count++;
        }
        if (data.shortDescription === undefined || data.shortDescription === "") {
            $("#shortMessage").text("Short description is required");
        } else {
            if (data.shortDescription.length > 250) {
                $("#shortMessage").text("Short description must be less than 250 character");
            } else {
                $("#shortMessage").text("");
                count++;
            }

        }

        if (count === 2) {
            return true;
        }
        return false;
    }

    function ajaxEdit(data) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/category/edit",
            method: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function (res) {
            getData();
            $("#modalEditTopic").modal("hide");
            clearData();
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res) {
            $("#processing").removeClass("active");
            error(" " + res.responseJSON.message);

        })
    }

    function getData() {
        $.ajax({
            url: "/api/admin/category/getAll",
            method: "get",
            dataType: 'json'
        }).done(function (res) {
            console.log(res);
            fetchData(res)
        }).fail(function (res) {
            error(" " + res.responseJSON.message);

        })
    }

    function fetchData(data) {
        console.log(data);
        let res = "";
        if (data.length > 0) {
            data.forEach(el => res += write(el));
        } else {
            res = "<tr>\n" +
                "         <td colspan=\"4\">\n" +
                "                 <p style='color: grey; font-weight: bold; text-align: center'>There is nothing\n" +
                "                  here!</p>\n" +
                "          </td>\n" +
                "  </tr>"
        }
        $("#tBody").html(res);
    }

    function write(el) {
        let item = " <tr>\n" +
            "                <td id='id_" + el.id + "'>" + el.id + "</td>\n" +
            "                <td id='name_" + el.id + "'>" + el.name + "</td>\n" +
            "                <td id='short_" + el.id + "'>" + el.shortDescription + "</td>\n" +
            "                <td style=\"width: 10rem\">\n" +
            "                      <button  class=\"btn btn-warning btn-edit\" id='edit_" + el.id + "'>Edit</button>\n" +
            "                      <button  class=\"btn btn-danger btn-delete\" id='delete_" + el.id + "'>Delete</button>\n" +
            "                </td>\n" +
            "           </tr>"

        return item;
    }

    $(document).on("click", ".btn-edit", function () {
        let parEl = $(this).closest("tr");
        let tdEl = parEl.find("td");
        $("#id").val(tdEl.eq(0).text());
        $("#name").val(tdEl.eq(1).text())
        $("#shortDescription").val(tdEl.eq(2).text())
        $("#modalEditTopic").modal("show");
        $("#titleForm").text("Update category")
    })


    $("#btnCancel").click(function () {
        clearData();
    })
    $("#btnX").click(function () {
        clearData()
    })
    $("#btnCreate").click(function () {
        $("#titleForm").text("New category")
        clearData();
    })

    function clearData() {
        $("#id").val("");
        $("#name").val("")
        $("#shortDescription").val("")
    }

    $(document).on("click",".btn-delete", function (){
        let id = $(this).attr("id").split("_")[1];
        $("#modalEditTopic").modal("hide");
        $("#modalConfirmDelete").modal("show");
        swalAlert(id);
    })



    function swalAlert(id) {
        swal({
            title: "Are you sure?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    deleteTopic(id);
                }
            });
    }

    function deleteTopic(id){
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/admin/category/delete/"+id,
            method:"delete",
            dataType: 'json'
        }).done(function (res){
            getData();
            $("#modalConfirmDelete").modal("hide");
            $("#processing").removeClass("active");
            success(" " + res.message);
        }).fail(function (res){
            getData();
            $("#processing").removeClass("active");
            $("#modalConfirmDelete").modal("hide");
            error(" " + res.responseJSON.message);
        })
    }
})