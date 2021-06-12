$(document).ready(function(){
    let image = $("#imgPreview");
    $('#modalUploadImg').modal({
        backdrop:"static",
        show:false
    })

    $("#file").change(function (e) {
        // console.log(event.target.files[0]);
        image.attr("src", URL.createObjectURL(event.target.files[0]));
    })

    $("#btnModalImg").click(function () {
        $("#modalUploadImg").modal("show");
    })
    $("#btnApply").click(function () {
        $("#modalUploadImg").modal("hide");
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
})