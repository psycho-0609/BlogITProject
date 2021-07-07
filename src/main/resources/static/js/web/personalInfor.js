$(document).ready(function () {
    let image = $("#imgPreview");
    $('#modalUploadImg').modal({
        show: false
    })

    $("#file").change(function (e) {
        image.attr("src", URL.createObjectURL(event.target.files[0]));
    })

    $("#btnModalImg").click(function () {
        $("#modalUploadImg").modal("show");
    })

    $("#btnCancel").click(function () {
        $("#file").val("");
        $("#modalUploadImg").modal("hide");
        image.attr("src", "");
        console.log($("#file")[0].files[0]);
    })
    let errorTimeout;
    function errorMessage(message) {
        clearTimeout(errorTimeout);
        $("#fail").fadeOut();
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        errorTimeout = setTimeout(function () {
            $("#fail").fadeOut(500);
        }, 3000)
    }

    $("#btnApply").click(function () {
        let data = {};
        data["base64"] = "";
        data["fileName"] = "";
        let file = $("#file")[0].files[0];
        if (file !== undefined) {
            $("#imageEmptyMess").text("");
            let reader = new FileReader();
            reader.onload = function (e) {
                data["base64"] = e.target.result;
                data["fileName"] = file.name;
                updateImage(data)
            };
            reader.readAsDataURL(file);
        } else {
            $("#imageEmptyMess").text("Please select image");
        }

    })

    function updateImage(data) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/image",
            type: "put",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'
        }).done(function () {
            $("#processing").removeClass("active");
            location.reload();
        }).fail(function () {
            $("#processing").removeClass("active");
            console.log("fail");
        })
    }

    //update user information
    let errorMessFirstName = "First name is required";
    let errorMessLastName = "Last name is required";
    let errorMessDob = "Birthday name is required";
    let errorMessPhone = "Phone number name is required";
    let errorMessPhoneLength = "The length of phone number must be 10";
    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";

    function validateInformation(data) {
        let count = 0;
        if (data.firstName === undefined || data.firstName === "") {
            $("#errorMessFirstName").text(errorMessFirstName);
        } else {
            $("#errorMessFirstName").text("");
            count++;
        }
        if (data.lastName === undefined || data.lastName === "") {
            $("#errorMessLastName").text(errorMessLastName);
        } else {
            $("#errorMessLastName").text("");
            count++;
        }
        if (data.dob === undefined || data.dob === "") {
            $("#errorMessDob").text(errorMessDob);
        } else {
            $("#errorMessDob").text("");
            count++;
        }
        if (data.phone === undefined || data.phone === "") {
            $("#errorMessPhone").text(errorMessPhone);
        } else {
            if (data.phone.length != 10) {
                $("#errorMessPhone").text(errorMessPhoneLength);
            } else {
                $("#errorMessPhone").text("");
                count++;
            }

        }

        if (count === 4) {
            return true;
        }
        return false;
    }


    function updateInformation(data) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/personalInfor",
            type: "put",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'

        }).done(function (response) {
            $("#processing").removeClass("active");
            location.reload();
        }).fail(function (res) {
            $("#processing").removeClass("active");
            console.log("fail");
            errorMessage(res.responseJSON.message);
        })
    }

    $("#formUpdateInfor").on('submit', function (e) {
        e.preventDefault();
        let data = {};
        let formData = $(this).serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        if (validateInformation(data) === true) {
            updateInformation(data);
        }
    })

})