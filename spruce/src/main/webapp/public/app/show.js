$(function () {
    $("#vote_btn").click(vote);
    $("#favorite_btn").click(favorite);
    var gmap = new fengfei.GMapV3(document.getElementById("map_show_canvas"));
    gmap.getCurrentLatLngAddress(function (address, results) {
        if (gmap.isGPS) {
            $("#address").text(address);
        }

    });
    gmap.toLocalLatLng();

});

function rankReShow(id) {
    $.get("/show/rank/" + id, function (data) {
        $("#rankShow").html(data);
    }, "html");

}

function vote() {
    var id = $("#id_photo").val();
    var btn = $(this);
    var isvote = $(this).attr("isvote");
    var category = $("#categoryx").val();
    var niceName = $("#niceNameX").val();
    var photoIdUser = $("#photoIdUserX").val();

    if (isvote != "true") {
        $.post("/vote/" + id, {
            "category": category,
            "niceName": niceName,
            "photoIdUser": photoIdUser
        }, function (data) {
            if (data.status == "Success") {
                btn.removeClass("btn-success").addClass("disabled").text(i18n.Thank.vote);
                rankReShow(id);
                btn.attr("isvote",true);
            } else {
                notify(data.msg, i18n.Server.error);
            }

        }, "json");
    }

}

function favorite() {
    var $this = $(this);
    $this.unbind("click");
    var id = $("#id_photo").val();
    var isfavorite = $this.attr("isfavorite");
    var category = $("#category").val();
    var niceName = $("#niceNameX").val();
    var photoIdUser = $("#photoIdUserX").val();


    if (isfavorite == "true") {
        $.post("/unfavorite/" + id, {
            "photoIdUser": photoIdUser
        }, function (data) {
            if (data.status == "Success") {
                $this.removeClass("favorited").addClass("unfavorited");
                $this.attr("isfavorite", "false");
                $this.attr("title", i18n.favorite);
                rankReShow(id);
            } else {
                notify(data.msg, i18n.Server.error);
            }
            $this.click(favorite);

        }, "json");
    } else {
        $.post("/favorite/" + id, {
            "category": category,
            "niceName": niceName,
            "photoIdUser": photoIdUser
        }, function (data) {
            if (data.status == "Success") {
                $this.removeClass("unfavorited").addClass("favorited");

                $this.attr("isfavorite", "true");
                $this.attr("title", i18n.Favorite.cancel);
                rankReShow(id);
            } else {
                notify(data.msg, i18n.Server.error);
            }
            $this.click(favorite);
        }, "json");
    }
}

