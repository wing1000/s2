$(function () {
    //$('#tags1').tokenfield();
    if (_edit_photo) {
        fillForm(_edit_photo);
    }
});

function fillForm(p) {
    var tempIndex = 1;
    $("#id" + tempIndex).val(p.idPhoto);
    $("#id_photo" + tempIndex).val(p.idPhoto);
    $("#title" + tempIndex).val(p.title);

    //
    $("#taken_at" + tempIndex).val(p.dateTimeOriginal).readonfy();
    $("#taken_at_display" + tempIndex).val(p.dateTimeOriginal).readonfy();
    $("#make" + tempIndex).val(p.make).readonfy();
    $("#camera" + tempIndex).val(p.model).readonfy();
    $("#focus" + tempIndex).val(p.focus).readonfy();
    $("#iso" + tempIndex).val(p.iso).readonfy();
    $("#lens" + tempIndex).val(p.lens).readonfy();
    $("#shutter" + tempIndex).val(p.shutter).readonfy();
    $("#aperture" + tempIndex).val(p.aperture).readonfy();
    $("#ev" + tempIndex).val(p.ev).readonfy();

    $("#WhiteBalance" + tempIndex).val(p.WhiteBalance);
    $("#Software" + tempIndex).val(p.Software);
    $("#Flash" + tempIndex).val(p.Flash);
    $("#ColorSpace" + tempIndex).val(p.ColorSpace);
    $("#MeteringMode" + tempIndex).val(p.MeteringMode);
    $("#ExposureProgram" + tempIndex).val(p.ExposureProgram);
    $("#ExposureMode" + tempIndex).val(p.ExposureMode);
    //
    $("#desc" + tempIndex).val(p.description);
    $("#tags" + tempIndex).val(p.tags);
    //	console.info(p.tags);
    //$("#tags" + tempIndex).tokenfield('setTokens',p.tags);
    $("#GPSLatitude" + tempIndex).val(p.GPSLatitude);
    $("#GPSLongitude" + tempIndex).val(p.GPSLongitude);
    $("#GPSAltitude" + tempIndex).val(p.GPSAltitude);
    $("#GPSOrigin" + tempIndex).val(p.GPSOrigin);
    //
    $("#category" + tempIndex).selectpicker('val', p.category);

    var licenseKey = License.values[p.license ];
    License.displayLicense(licenseKey, "cc", "image_64", "license_by");
    if (p.idSet && p.idSet > 0) {
        $("#dir" + tempIndex).selectpicker('val', p.idSet);
    }
    if (google) {
        initializeEditMap();
        if (p.GPSLatitude != 0 && p.GPSLongitude != 0) {
            var latLng = new google.maps.LatLng(p.GPSLatitude, p.GPSLongitude);
            gmap.markMarkerAndPanTo(latLng);
        }
        gmap.toLocalLatLng();
    }
    // $("#category" + tempIndex).val(p.category);
}