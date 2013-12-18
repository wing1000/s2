$(function () {
    $("#follow").click(follow);
    $("#follow").mouseover(
        function () {
            var isfollow = $(this).attr("isfollow");
            if (isfollow == "true") {
                $(this).removeClass("btn-success").addClass("btn-danger")
                    .text(i18n.unfollow);
            }
        }).mouseout(
        function () {
            var isfollow = $(this).attr("isfollow");
            if (isfollow == "true") {
                $(this).addClass("btn-success").removeClass("btn-danger")
                    .text(i18n.following);
            }
        });
});

function follow() {
    var $this = $(this);
    $this.unbind("click");
    var toid = $this.attr("toid");
    var isfollow = $this.attr("isfollow");

    if (isfollow == "true") {
        $.get("/unfollow/" + toid, function (data) {
            if (data.status == "Success") {
                $this.removeClass("btn-success").addClass("btn-info").text(
                    i18n.follow);
                $this.attr("isfollow", "false");
            } else {
                notify(data.msg, "error");
            }
            $this.click(follow);
        }, "json");
    } else {
        $.get("/follow/" + toid, function (data) {
            if (data.status == "Success") {
                $this.addClass("btn-success").removeClass("btn-info").text(
                    i18n.following);
                $this.attr("isfollow", "true");
            } else {
                notify(data.msg, "error");
            }
            $this.click(follow);
        }, "json");
    }
}