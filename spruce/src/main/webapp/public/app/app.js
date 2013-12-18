$(function () {
    if (window.bootbox)
        bootbox.setLocale("zh_CN");
    var selectpicker = $('.selectpicker');
    if (selectpicker)
        selectpicker.selectpicker();
    //
    $("#loginForm").submit(function () {

        var uid = $('#email').val();
        var pwd = $('#pwd').val();

        if (uid == '') {
            alert("Please type email!");
            return false;
        }
        if (pwd == '') {
            alert("Please type password!");
            return false;
        }
        var pwdmd5 = window.md5(pwd);
        $('#pwd').val(pwdmd5);
        $(this).ajaxSubmit({
            dataType: "json",
            success: function (responseText, statusText, xhr, $form) {
                alert('status: ' + statusText + '\n\nresponseText: \n' + responseText.username + '\n\nThe output div should have already been updated with the responseText.');
                window.location.reload();
            }
        });

        return false;
    });

    $("#onlyCategory1").change(function () {
        var c = $(this).val();
    });
});

function notify(text, type) {
    if (!type) {
        type = "info";
    }
    $.bootstrapGrowl(text, {
        ele: 'body', // which element to append to
        type: type, // (null, 'info', 'error', 'success')
        offset: {
            from: 'bottom',
            amount: 80
        }, // 'top', or 'bottom'
        align: 'right', // ('left', 'right', or 'center')
        width: 250, // (integer, or 'auto')
        delay: 4000,
        allow_dismiss: true,
        stackup_spacing: 10 // spacing between consecutively stacked growls.
    });
}

var gmap = false;
function initializeEditMap() {
    if (gmap) {
        return;
    }
    gmap = new fengfei.GMapV3(document.getElementById("map_canvas"));

    //
    gmap.gmenu = new contextMenu({
        map: gmap.map
    });
    gmap.gmenu.addItem('Mark Here', function (map, latLng) {
        gmap.markMarkerAndPanTo(latLng);
        $("#GPSOrigin" + gmap.tempIndex).val(1);
    });

    //gmenu.addSep();

    gmap.gmenu.addItem('Center Here', function (map, latLng) {
        gmap.panTo(latLng);
    });
    //gmenu.addSep();

    gmap.gmenu.addItem('Reset', function (map, latLng) {
        gmap.reset(function (latLng) {
            $("#GPSOrigin" + gmap.tempIndex).val("");
        });

    });

}
var License = {
    imgSrc: "/public/images/cc/{0}.png",
    values: {
        "1": "by-nc", "2": "by-nc-nd", "3": "by-nc-sa", "4": "by", "5": "by-nd", "6": "by-sa"
    },
    displayLicense: function (licenseKey, containerElement, imageClass, txtClass) {

        var container = $(containerElement);
        container.empty();

        //create link
        var a = $("<a/>");
        var href = "http://creativecommons.org/licenses/{0}/3.0/cn/";
        a.attr("href", href.replace("{0}", licenseKey));
        a.attr("target", "_target");
        //
        var cc = ["cc"].concat(licenseKey.split("-"));
        var txt = "";
        var imgContent = $("<div/>");//.css("text-align", "right");
        var content = imgContent.clone();
        content.addClass(txtClass ? txtClass : "");
        for (var i in cc) {
            var img = $('<img >'); //Equivalent: $(document.createElement('img'))
            img.attr('src', this.imgSrc.replace("{0}", cc[i]));
            img.addClass(imageClass ? imageClass : "image_64")
            //img.addClass("img");
            img.appendTo(imgContent);
            if (i > 0) {
                txt += " " + i18n.License.Cc[cc[i]];
            }
        }

        txt += " 3.0";
        content.text(txt);
        a.append(imgContent);
        a.append(content);
        //console.log(licenseKey+", "+txt);
        a.appendTo(container);

    }


}

