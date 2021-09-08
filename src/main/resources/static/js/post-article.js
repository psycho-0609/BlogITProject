$(document).ready(function () {
    var editor = CKEDITOR
        .replace(
            'content',
            {
                height: '31rem',
                placeholder: 'Write the content of your article here :))',
                filebrowserBrowseUrl: '/ckfinder/ckfinder.html',
                filebrowserImageBrowseUrl: '/ckfinder/ckfinder.html?type=Images',
                filebrowserFlashBrowseUrl: '/ckfinder/ckfinder.html?type=Flash',
                filebrowserUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                filebrowserImageUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                filebrowserFlashUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });


    /*Avatar start*/
    document.getElementById("submit").addEventListener('click', function () {
        console.log(editor.getData());

        // editor.setData(data)


    })

    let messTitleEmpty = "Title is required";
    let messStatusEmpty = "Status is required";
    let messTopicEmpty = "Topic is required";
    let messShortDesEmpty = "Short description is required";
    let messContentEmpty = "Content is required";

    let fail = "<i class=\"fas fa-times\"></i> ";
    let messSuccess = "<i class=\"fas fa-check\"></i>";

    function validateData() {
        let count = 0;
        if ($("#title").val() === "" || $("#title").val() === undefined) {
            $("#errorTitle").text(messTitleEmpty);
        } else {
            $("#errorTitle").text("");
            count++;
        }

        if ($("#status").val() === "" || $("#status").val() === undefined) {
            $("#errorStatus").val(messStatusEmpty);
        } else {
            $("#errorStatus").val("");
            count++;
        }

        if ($("#topic").val() === "" || $("#topic").val() === undefined) {
            $("#errorTopic").text(messTopicEmpty);
        } else {
            $("#errorTopic").text("");
            count++;
        }

        if ($("#shortDescription").val() === "" || $("#shortDescription").val() === undefined) {
            $("#errorShortDes").text(messShortDesEmpty);
        } else {
            $("#errorShortDes").text("");
            count++;
        }

        if (editor.getData() === "" || editor.getData() === undefined) {
            $("#errorContent").text(messContentEmpty);
        } else {
            $("#errorContent").text("");
            count++;
        }

        console.log(count);
        if (count === 5) {
            return true;
        }
        return false;

    }

    $("#formSubmit").on('submit', function (e) {
        e.preventDefault();
        let data = {};
        let formData = $("#formSubmit").serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        data["content"] = editor.getData();
        validateData(data);
        let form = new FormData();
        let imgFile = $("#file")[0].files[0];
        let videoFile = $("#fileVideo")[0].files[0];
        let id = $("#id").val();
        form.append("id", id);
        form.append("shortDescription", data.shortDescription);
        form.append("title", data.title);
        form.append("content", data.content);
        form.append("status", data.status);
        form.append("topicId", data.topicId);
        if (imgFile !== undefined) {
            form.append("image", imgFile);
        }
        if (videoFile !== undefined) {
            form.append("video", videoFile);
        }
        console.log(form);
        if(validateData() === true){
            processData(id, form);
        }

        // console.log(id);


        // data["base64"] = "";
        // data["file"] = "";
        // data["content"] = editor.getData();
        // if (file !== undefined) {
        //     let reader = new FileReader();
        //     reader.onload = function (e) {
        //         data["base64"] = e.target.result;
        //         data["file"] = file.name;
        //         processData(data)
        //     };
        //     reader.readAsDataURL(file);
        // } else {
        //     processData(data);
        // }


    })

    function processData(id,form) {
        // console.log(validateData(data))

        if(id !== "" && id !== undefined && id !== null){
            updateArticle(form);
        }else{
            uploadArticle(form);
        }

    }

    function uploadArticle(data) {
        $("#processing").addClass("active");
        console.log(data);
        $.ajax({
            url: "/api/user/posts/add",
            type: "post",
            contentType: false,
            data: data,
            dataType: 'json',
            processData:false,

        }).done(function (response) {
            $("#processing").removeClass("active");
            console.log(response);
            $("#title").val("");
            $("#file").val("");
            $("#shortDescription").val("");
            editor.setData("");
            $("#defaultValueSelect").attr("selected", "selected");
            success(" Create success")
        }).fail(function (e) {
            $("#processing").removeClass("active");
            console.log("fail");
            errorMessage(res.responseJSON.message);
        })
    }

    function updateArticle(data) {
        $("#processing").addClass("active");
        $.ajax({
            url: "/api/user/posts/update",
            type: "put",
            contentType: false,
            data: data,
            dataType: 'json',
            processData:false

        }).done(function (response) {
            $("#processing").removeClass("active");
            console.log(response);
            success(" Update success")
        }).fail(function (res) {
            $("#processing").removeClass("active");
            console.log("fail");
            errorMessage(res.responseJSON.message);
        })

    }

    let errorTimeout;
    let successTimeOut;

    function clearTime() {
        clearTimeout(errorTimeout);
        clearTimeout(successTimeOut);
        $("#success").fadeOut();
        $("#fail").fadeOut();
    }

    function errorMessage(message) {
        clearTime();
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        errorTimeout = setTimeout(function () {
            $("#fail").fadeOut(3000);
        }, 1500)
    }

    function success(message) {
        clearTime();
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        successTimeOut = setTimeout(function () {
            $("#success").fadeOut(3000);
        }, 1500)
    }

})