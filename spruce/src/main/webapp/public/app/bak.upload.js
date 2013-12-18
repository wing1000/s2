$.blueimp.fileupload.prototype.processActions.duplicateImage = function(data, options) {
	if (data.canvas) {

		data.files.push(data.files[data.index]);
		data.cindex = data.files.length - 1;
		console.log({
			files : data.files
		});
		console.log(data.cindex + " , " + data.index + "," + data.files.length);

	}
	return data;
};
var map, marker, gmenu, geocoder;
var overlay;

$(function() {'use strict';

	// Change this to the location of your server-side upload handler:
	var url = "/upload/done";
	var uploadButton = $('<button class="btn btn-primary" id="start" type="button">	<i class="icon-upload icon-white"></i>Save</button>').prop('disabled', true).text('Processing...').on('click', function() {
		var $this = $(this), data = $this.data();
		$this.off('click').text('Abort').on('click', function() {
			$this.remove();
			data.abort();
		});
		data.submit().always(function() {
			$this.remove();
		});
	});

	$('#fileuploadForm').fileupload({
		url : url,
		dataType : 'json',
		forceIframeTransport : false, //When set “forceIframeTransport” is true, Drag&Drop can't work on chrome and firefox
		autoUpload : false,
		loadImageFileTypes : /(\.|\/)(jpe?g)$/i,
		loadImageMaxFileSize : 10240000, // 15MB
		// disableImageResize : false,
		disableImageResize : /Android(?!.*Chrome)|Opera/.test(window.navigator && navigator.userAgent),
		previewMaxWidth : 400,
		previewMaxHeight : 400,
		previewCrop : false,
		previewAsCanvas : true,
		imageCrop : false,
		processQueue : [{
			action : 'loadImage'
		}, {
			action : 'resizeImage',
			maxWidth : 1200,
			maxHeight : 1200
		}, {
			action : 'saveImage'
		}, {
			action : 'setImage'
		}
		// , {
		// action : 'duplicateImage'
		// }, {
		// action : 'resizeImage',
		// maxWidth : 300,
		// maxHeight : 300
		// }, {
		// action : 'saveImage'
		// }, {
		// action : 'duplicateImage'
		// }, {
		// action : 'resizeImage',
		// maxWidth : 100,
		// maxHeight : 100
		// }, {
		// action : 'saveImage'
		// }

		]

	}).bind('fileuploadadd', function(e, data) {
		$('#progress .bar').css('width', '0%');
		$("#preview > img").remove();
		var file = data.files[0];
		toForm(file);
		data.context = $('#content');
		$("#content").css("display", "block");
		$("#dropzone").css("padding", "0px 0px");
		$("#name").text(file.name);
		$("#size").text(formatFileSize(file.size));
		// $("#start").data(data);
		$("#start").find('button').remove();
		$("#start").append(uploadButton.clone(true).data(data));

		initializeMap();

		// $.each(data.files, function(index, file) {
		// var node = $('<p/>').append(
		// $('<span />').text(file.name));
		// if (!index) {
		// node.append('<br>').append(
		// uploadButton.clone(true).data(data));
		// }
		//
		// node.appendTo(data.context);
		// });
		window.loadImage(data.files[0], function(img) {
			$("#preview").append(img);
		}, {
			maxWidth : 560
		});

		// alert(data.files.length);
	}).bind('fileuploadprocessalways', function(e, data) {

		// var index = data.index, file = data.files[index], node =
		// $(data.context
		// .children()[index]);
		// if (file.preview) {
		// node.prepend('<br>').prepend(file.preview);
		// }
		// if (file.error) {
		// node.append('<br>').append(file.error);
		// }
		// // if (index + 1 === data.files.length) {
		// data.context.find('button').text('Upload').prop(
		// 'disabled', !!data.files.error);
		// }
		$("#start").find('button').text('Upload').prop('disabled', !!data.files.error);
	}).bind('fileuploadprogress', function(e, data) {
	}).bind('fileuploadprogressall', function(e, data) {
		var progress = parseInt(data.loaded / data.total * 100, 10);
		$('#progress .bar').css('width', progress + '%');
	}).bind('fileuploaddone', function(e, data) {
		// alert(data.result);
		// $.each(data.result.files, function(index, file) {
		// var link = $('<a>').attr('target', '_blank').prop(
		// 'href', file.url);
		// $(data.context.children()[index]).wrap(link);
		// });
		if (data.result.success == true) {
			notify("upload sucess.");
		} else {
			$("#start").find('button').text('Upload').prop('disabled', false);
			// $.each(data.result.files, function(index, file) {
			// var error = $('<span/>').text(file.error);
			// $(data.context.children()[index]).append('<br>').append(error);
			// $(data.context.children()[index]).append('<br>').append(error);
			// });
			$("#uploadError").css('display', "block");
			$('#progress .bar').css('width', '0%');
			notify("upload failure.", "error");
		}
	}).bind('fileuploadfail', function(e, data) {
		$.each(data.result.files, function(index, file) {
			var error = $('<span/>').text(file.error);
			$(data.context.children()[index]).append('<br>').append(error);
		});
	}).bind('fileuploadsubmit', function(e, data) {
		var formData = $('#fileuploadForm').serializeArray();
		data.formData = formData;

	});

	$("#close").click(function() {
		$("#content").css("display", "none");
		$("#dropzone").css("padding", "200px 0px");
		$("#dropzone").css("display", "block");
	});
});

function fillForm(p) {
	$("#title" + tempIndex).val(file.name.substring(0, index));

	//
	$("#taken_at" + tempIndex).val(data.DateTimeOriginal2);
	$("#taken_at_display" + tempIndex).val(data.DateTimeOriginal2);
	$("#make" + tempIndex).val(data.Make);
	$("#camera" + tempIndex).val(data.Model);
	$("#focus" + tempIndex).val(data.FocalLength2);
	$("#iso" + tempIndex).val(data.ISOSpeedRatings);
	$("#lens" + tempIndex).val($.trim(data.LensModel));
	$("#shutter" + tempIndex).val(data.ExposureTime2);
	$("#aperture" + tempIndex).val(data.FNumber2);
	$("#ev" + tempIndex).val(data.ev);

	$("#WhiteBalance" + tempIndex).val(data.WhiteBalance2);
	$("#Software" + tempIndex).val(data.Software);
	$("#Flash" + tempIndex).val(data.Flash2);
	$("#ColorSpace" + tempIndex).val(data.ColorSpace2);
	$("#MeteringMode" + tempIndex).val(data.MeteringMode2);
	$("#ExposureProgram" + tempIndex).val(data.ExposureProgram2);
	$("#ExposureMode" + tempIndex).val(data.ExposureMode2);
	//
	$("#desc" + tempIndex).val("");
	$("#tags" + tempIndex).val("");
	$("#category1" + tempIndex).val(0);
	$("#GPSLatitude" + tempIndex).val("");
	$("#GPSLongitude" + tempIndex).val("");
	$("#GPSAltitude" + tempIndex).val("");
}

function toForm(file) {
	ExifUtils.readExif(file, function(data) {
		console.info(data);
		if (!data) {
			data = {};
			data.DateTimeOriginal2 = "";
			data.Make = "";
			data.Model = "";
			data.FocalLength2 = "";
			data.ISOSpeedRatings = "";
			data.LensModel = "";
			data.ExposureTime2 = "";
			data.FNumber2 = "";
			data.ev = "";
			data.WhiteBalance = "";
			data.Software = "";
			data.Flash = "";
			data.ColorSpace2 = "";
			data.MeteringMode = "";

		}
		var tempIndex = 1;
		var isLatLng = false;
		if (data) {

			// alert(data.toSource());

			var index = file.name.lastIndexOf(".");
			$("#title" + tempIndex).val(file.name.substring(0, index));

			//
			$("#taken_at" + tempIndex).val(data.DateTimeOriginal2);
			$("#taken_at_display" + tempIndex).val(data.DateTimeOriginal2);
			$("#make" + tempIndex).val(data.Make);
			$("#camera" + tempIndex).val(data.Model);
			$("#focus" + tempIndex).val(data.FocalLength2);
			$("#iso" + tempIndex).val(data.ISOSpeedRatings);
			$("#lens" + tempIndex).val($.trim(data.LensModel));
			$("#shutter" + tempIndex).val(data.ExposureTime2);
			$("#aperture" + tempIndex).val(data.FNumber2);
			$("#ev" + tempIndex).val(data.ev);

			$("#WhiteBalance" + tempIndex).val(data.WhiteBalance2);
			$("#Software" + tempIndex).val(data.Software);
			$("#Flash" + tempIndex).val(data.Flash2);
			$("#ColorSpace" + tempIndex).val(data.ColorSpace2);
			$("#MeteringMode" + tempIndex).val(data.MeteringMode2);
			$("#ExposureProgram" + tempIndex).val(data.ExposureProgram2);
			$("#ExposureMode" + tempIndex).val(data.ExposureMode2);
			//
			$("#desc" + tempIndex).val("");
			$("#tags" + tempIndex).val("");
			$("#category1" + tempIndex).val(0);

			//data.WhiteBalance;
			//data.Software;
			//data.Flash
			//data.ColorSpace
			//data.MeteringMode 测光模式
			console.info(data);
			var aLat = data.GPSLatitude;
			var aLong = data.GPSLongitude;
			var latLng = false;

			if (aLat && aLong) {

				aLat[0] = ExifUtils.toNumber(aLat[0]);
				aLat[1] = ExifUtils.toNumber(aLat[1]);
				aLat[2] = ExifUtils.toNumber(aLat[2]);

				//console.info(aLat + "   --  " + aLong);
				aLong[0] = ExifUtils.toNumber(aLong[0]);
				aLong[1] = ExifUtils.toNumber(aLong[1]);
				aLong[2] = ExifUtils.toNumber(aLong[2]);
				var strLatRef = data.GPSLatitudeRef || "N";
				var strLongRef = data.GPSLongitudeRef || "W";
				var fLat = (aLat[0] + aLat[1] / 60 + aLat[2] / 3600) * (strLatRef == "N" ? 1 : -1);
				var fLong = (aLong[0] + aLong[1] / 60 + aLong[2] / 3600) * (strLongRef == "W" ? -1 : 1);
				var latLng = new google.maps.LatLng(fLat, fLong);
				map.panTo(latLng);
				if (marker) {
					marker.setPosition(latLng);
				} else {
					marker = new google.maps.Marker({
						position : latLng,
						map : map,
						title : 'Location of your photo '
					});
				}

				map.initLatLng = latLng;
				map.initZoom = map.getZoom();
				$("#GPSLatitude" + tempIndex).val(fLat);
				$("#GPSLongitude" + tempIndex).val(fLong);
				data.GPSLatitude2=fLat;
				data.GPSLongitude2=fLong;

				isLatLng = true;

			} else {
				$("#GPSLatitude" + tempIndex).val("");
				$("#GPSLongitude" + tempIndex).val("");
				$("#GPSAltitude" + tempIndex).val("");
			}
			var altitude = data.GPSAltitude;
			if (altitude) {
				altitude = ExifUtils.toNumber(altitude);
				$("#GPSAltitude" + tempIndex).val(altitude);
				data.GPSAltitude2=altitude;
			} else {
				$("#GPSAltitude" + tempIndex).val("");
				if (latLng) {
					requestElevation(latLng, tempIndex);
				}
			}
			//
			$("#exifAll").val(data);
		}
		if (!isLatLng && !marker) {
			marker = new google.maps.Marker({
				map : map,
				title : 'Location of your photo '
			});

		}
		map.marker = marker;
		map.tempIndex = tempIndex;
		map.marker.tempIndex = tempIndex;
	});

}

var elevator = new google.maps.ElevationService();

function placeMarker(map, latLng) {
	var tempIndex = map.tempIndex;
	if (latLng) {
		map.marker.setPosition(latLng);
		map.panTo(latLng);
		//console.info(latLng);
		$("#GPSLatitude" + tempIndex).val(latLng.lat());
		$("#GPSLongitude" + tempIndex).val(latLng.lng());
		requestElevation(latLng, tempIndex);
	} else {
		$("#GPSLatitude" + tempIndex).val("");
		$("#GPSLongitude" + tempIndex).val("");
		$("#GPSAltitude" + tempIndex).val("");
		$("#GPSOrigin" + tempIndex).val("");
	}

}

function getLatLngAddress(latlng) {
	geocoder.geocode({
		'latLng' : latlng
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[1]) {
				var address = results[1].formatted_address;

			} else {
				notify('No results found');
			}
		} else {
			notify('Geocoder failed due to: ' + status);
		}
	});
}

function requestElevation(latLng, tempIndex) {
	var locations = [];
	locations.push(latLng);
	var positionalRequest = {
		'locations' : locations
	}

	// Initiate the location request
	elevator.getElevationForLocations(positionalRequest, function(results, status) {
		if (status == google.maps.ElevationStatus.OK) {

			// Retrieve the first result
			if (results[0]) {
				var elevation = results[0].elevation;
				$("#GPSAltitude" + tempIndex).val(elevation);

			} else {
				notify("No results found");
			}
			//console.info(results);
		} else {
			notify("Elevation service failed due to: " + status);
		}
	});
}

var template = '';

function initializeMap() {
	if (map) {
		return;
	}
	var latlng = new google.maps.LatLng(30.619004797647808, 104.073486328125);

	var mapOptions = {
		center : latlng,
		zoom : 8,
		mapTypeId : google.maps.MapTypeId.TERRAIN
	};
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	geocoder = new google.maps.Geocoder();

	map.initLatLng = false;
	map.initZoom = map.getZoom();
	//
	gmenu = new contextMenu({
		map : map
	});
	gmenu.addItem('Mark Here', function(map, latLng) {
		placeMarker(map, latLng);
		$("#GPSOrigin" + map.tempIndex).val(1);
	});

	//gmenu.addSep();

	gmenu.addItem('Center Here', function(map, latLng) {
		map.panTo(latLng);
	});
	//gmenu.addSep();

	gmenu.addItem('Reset', function(map, latLng) {
		console.info(map);
		placeMarker(map, map.initLatLng);
		map.setZoom(map.initZoom);
		$("#GPSOrigin" + map.tempIndex).val("");
	});

}

