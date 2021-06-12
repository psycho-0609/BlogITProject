$(document).ready(function (){
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

    function validateData(){
        let count = 0;
        if($("#title").val() === "" || $("#title").val() === undefined){
            $("#errorTitle").text(messTitleEmpty);
        }else{
            $("#errorTitle").text("");
            count++;
            console.log(count);
        }

        if($("#status").val() === "" || $("#status").val() === undefined){
            $("#errorStatus").val(messStatusEmpty);
        }else{
            $("#errorStatus").val("");
            count++;
            console.log(count);
        }

        if($("#topic").val() === "" || $("#topic").val() === undefined){
            $("#errorTopic").text(messTopicEmpty);
        }else{
            $("#errorTopic").text("");
            count++;
            console.log(count);
        }

        if ($("#shortDescription").val() === "" || $("#shortDescription").val() === undefined){
            $("#errorShortDes").text(messShortDesEmpty);
        }else{
            $("#errorShortDes").text("");
            count++;
            console.log(count);
        }

        if(editor.getData() === "" || editor.getData() === undefined){
            $("#errorContent").text(messContentEmpty);
        }else{
            $("#errorContent").text("");
            count++;
            console.log(count);
        }

        console.log(count);
        if(count === 5){
            return true;
        }
        return false;

    }

    $("#formSubmit").on('submit', function (e) {
        e.preventDefault();
        let data = {};
        let id = $("#id").val();
        console.log(id);

        let formData = $("#formSubmit").serializeArray();
        console.log(formData);
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        let file = $("#file")[0].files[0];
        data["base64"] = "";
        data["file"] = "";
        data["content"] = editor.getData();
        if (file !== undefined) {
            let reader = new FileReader();
            reader.onload = function (e) {
                data["base64"] = e.target.result;
                data["file"] = file.name;
                processData(data)
            };
            reader.readAsDataURL(file);
        } else {
            processData(data);
        }


    })

    function processData(data) {
        console.log(validateData(data))
        if(validateData(data) === true){
            if (data.id !== "") {
                updateArticle(data);
            } else {
                uploadArticle(data);
            }
        }

    }

    function uploadArticle(data) {
        $("#processing").addClass("active");
        console.log(data);
        $.ajax({
            url: "/api/user/posts/add",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'

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
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'json'

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

    function errorMessage(message){
        $("#fail").html(fail + message)
        $("#fail").fadeIn();
        setTimeout(function () {
            $("#fail").fadeOut(3000);
        }, 1500)
    }
    function success(message){
        $("#success").html(messSuccess + message)
        $("#success").fadeIn();
        setTimeout(function () {
            $("#success").fadeOut(3000);
        }, 1500)
    }
})