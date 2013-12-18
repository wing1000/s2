$(function () {
    var photoCommentForm = $("#photoCommentForm");
    photoCommentForm.find("#comment_btn").click(function () {
        var value = photoCommentForm.find("#comment").val();
        if (value == "") {
            photoCommentForm.find("#comment").addClass("comment_error");
            return;
        }

        $.post("/comment/done", photoCommentForm.serialize(), function (data) {

            if (data.status == "Success") {
                // append({
                // username : "",
                // content : $("#comment").val()
                // });
                var ct = photoCommentForm.find("#comment_count").val();
                var t = parseInt(ct) + 1;
                photoCommentForm.find("#comment_count").val(t);
                var id = $("#id_photo").val();
                var idUser = $("#id_user").val();

                loadComments(id, idUser, 1, 0, t);
                $("#comment").val("");
                rankReShow(id);

            } else {
                notify(data.msg, "error");
            }

        }, "json");
    });

    photoCommentForm.find("#comment").click(function () {
        $(this).removeClass("comment_error");
    });
    if (photoCommentForm) {
        loadComments($("#id_photo").val(), $("#id_user").val(), 1, 0, $("#comment_count").val());
    }
});

function append(data) {
    $("#comments").prepend(tmpl("comments_tml", data));
}

function loadComments(id, id_user, cp, page, total) {
    //console.info(id + " " + id_user + " " + cp + " " + page + " " + total);
    $.get("/comments?" + Math.random(), {
        id: id,
        id_user: id_user,
        cp: cp,
        p: page,
        t: total
    }, function (data) {
        $("#comments_c").html(data);

        $("#pagination a").click(function () {
            var p = $(this).attr("page");
            var disabled = $(this).attr("disabled");
            if (!disabled) {
                loadComments($("#id_photo").val(), $("#cp").val(), p, $("#comment_count").val());
            }

        });
    }, "html");
}