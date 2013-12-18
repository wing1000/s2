var currentManagedItemUrl;
var currentManagedItem;

$(function () {
    initManageMenu();
    $(".manage #manage_menu a").first().trigger("click");

});
function initManageMenu() {
    $(".manage #manage_menu a").each(function (index) {
        $(this).click(function () {
            var url = $(this).attr("src");
            if (url != currentManagedItemUrl) {
                $.get(url, function (data) {
                    $("#manage_contaner").html(data);
                    currentManagedItemUrl = url;

                }, "html");
                currentManagedItem = $(this);
                $(this).parent().parent().find("li").each(function (i) {
                    $(this).removeClass("active");
                });
                $(this).parent().addClass("active");

            }

        });
    });
}

function addPhotoSetEvent() {
    var nameSet = $("#set1").val();
    var idSet = $("#idSet1").val();
    if (nameSet == "") {
        notify("name is not empty.");
        $("#set1").addClass("error");
        return;
    }

    $.post("/photo/manage/dir/done", {
        name: nameSet,
        id: idSet
    }, function (data) {
        $("#manage_contaner").html(data);
        getDirNavPage();

    }, "html");

}

function deletePhotoSetEvent() {
    var nameSet = $("#set1").val();
    var idSet = $("#idSet1").val();
    onDeletePhotoSet(nameSet, idSet);

}

function onDeletePhotoSet(name, idSet) {
    bootbox.confirm(i18n.Dir.Delete.confirm.replace("{0}",name), function (result) {
        if (result) {
            $.post("/photo/manage/dir/delete", {
                id: idSet
            }, function (data) {
                $("#manage_contaner").html(data);
                getDirNavPage();
            }, "html");
        }
    });
}

function getDirNavPage() {
    $.get("/photo/manage/dir/nav", function (data) {
        $("#manage_menu li.setItem").each(function (i) {
            $(this).remove();
        });
        $("#manage_menu").append(data);
        initManageMenu();
    }, "html");
}

function setPhotoSetValue(el) {
    var set = $(el);
    var id = set.attr("id_set");
    var name = set.attr("name_set");
    $("#set1").val(name);
    $("#idSet1").val(id);
    set.parent().parent().find("tr").each(function (i) {
        $(this).removeClass("info");
    });
    set.parent().addClass("info");
    $("#SetSaveButton").text("Edit");
}

function resetPhotoSet() {
    $("#SetSaveButton").text("Save");
    $("#set1").val("");
    $("#idSet1").val("");
}

function deletePhoto(idPhoto, el_id) {
    bootbox.confirm(i18n.Photo.Delete.confirm, function (result) {
        if (result) {
            $.post("/photo/" + idPhoto + "/delete", {
                id: idPhoto
            }, function (data) {
                if (data.success) {
                    var easeName = "easeOutQuint";
                    $("#" + el_id).delay(500).animate({
                        opacity: "hide"
                    }, 1000, easeName);
                } else {
                    notify("Delete fail!");
                }

            }, "json");
        }
    });
}

function editPhoto(idPhoto, idUser) {
    //console.info(idPhoto + "," + idUser);
    var url = "/edit/" + idPhoto;
    window.open(url, "photo_view_single", "channelmode=yes,directories=0,toolbar=0,location=0,width=1230,height=700");
    return false;

}