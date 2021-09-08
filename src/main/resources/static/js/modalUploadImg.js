$(document).ready(function(){
    let image = $("#imgPreview");
    let video = $("#videoPreview")
    $('#modalUploadImg').modal({
        backdrop:"static",
        show:false
    })

    $("#file").change(function (event) {
        // console.log(event.target.files[0]);
        image.attr("src", URL.createObjectURL(event.target.files[0]));
    })

    $("#btnModalImg").click(function () {
        $("#modalUploadImg").modal("show");
    })
    $("#btnApply").click(function () {
        $("#modalUploadImg").modal("hide");
        console.log($("#file")[0].files[0]);
    })
    $("#btnCancel").click(function () {
        $("#file").val("");
        $("#modalUploadImg").modal("hide");
        image.attr("src", "");
        console.log($("#file")[0].files[0]);
    })

// video
    $('#modalUploadVideo').modal({
        backdrop:"static",
        show:false
    })

    $("#fileVideo").change(function (event) {
        // console.log(event.target.files[0]);
        console.log("video")
        video.attr("src", URL.createObjectURL(event.target.files[0]));
        console.log(URL.createObjectURL(event.target.files[0]))
    })

    $("#btnModalVideo").click(function () {
        $("#modalUploadVideo").modal("show");
    })

    $("#btnApplyVideo").click(function () {
        $("#modalUploadVideo").modal("hide");
        console.log($("#fileVideo")[0].files[0]);
    })

    $("#btnCancelVideo").click(function () {
        $("#fileVideo").val("");
        $("#modalUploadVideo").modal("hide");
        video.attr("src", "");
        console.log($("#file")[0].files[0]);
    })
})