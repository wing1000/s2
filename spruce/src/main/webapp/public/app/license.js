/**
 * User: Tietang
 * Date: 13-9-6
 * Time: 下午9:48
 */

$(function () {
    setLicenseDefaultValue();
    initLicenseRadio();
});
function initLicenseRadio() {
    $("#licenseForm").find(":radio").click(function () {
        var $this = $(this);
        var licenseKey = "by";
        var checkedValue = [];
        $("#licenseForm").find("input:checked").each(function (j) {
            var val = $(this).val();
            if (val == "nc") {
                checkedValue[0] = val;
            }
            if (val == "nd" || val == "sa") {
                checkedValue[1] = val;
            }
        });
        for (var a in checkedValue) {
            licenseKey += "-" + checkedValue[a];
        }
       // console.log(checkedValue);
        License.displayLicense(licenseKey, $("#cc"), "image_64", "license_by");
    });
}
function setLicenseDefaultValue() {
    var licenseLastValue = $("#licenseLastValue").val();
    var licenseKey = License.values[licenseLastValue];
    License.displayLicense(licenseKey,  $("#licenseForm").find("#cc"), "image_64", "license_by");
    var subKeys = licenseKey.split("-");
    for (var i = 1; i < subKeys.length; i++) {
        var subKey = subKeys[i];
        $("#" + subKey).attr("checked", true);
    }
}