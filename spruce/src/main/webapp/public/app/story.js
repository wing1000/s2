window.locale = {
	"fileupload" : {
		"errors" : {
			"maxFileSize" : "File is too big",
			"minFileSize" : "File is too small",
			"acceptFileTypes" : "Filetype not allowed",
			"maxNumberOfFiles" : "Max number of files exceeded",
			"uploadedBytes" : "Uploaded bytes exceed file size",
			"emptyResult" : "Empty file upload result"
		},
		"error" : "Error",
		"start" : "Start",
		"cancel" : "Cancel",
		"destroy" : "Delete"
	}
};
$.blueimp.fileupload.prototype.processActions.duplicateImage = function(data, options) {
	if (data.canvas) {
		data.files.push(data.files[data.index]);
	}
	return data;
};
var imageIndex = 0;
$(function() {'use strict';

	var url = "/story/done";

	$('#fileuploadForm').fileupload({
		url : url,
		dataType : 'json',
		limitMultiFileUploads : 5,
		forceIframeTransport : true,
		sequentialUploads : false,
		limitConcurrentUploads : 1,
		uploadTemplateId : 'template-upload',
		downloadTemplateId : false,
		dropZone : $('#fileuploadForm'),
		autoUpload : false,
		loadImageFileTypes : /(\.|\/)(jpe?g)$/i,
		loadImageMaxFileSize : 10240000, // 15MB
		// disableImageResize : false,
		disableImageResize : /Android(?!.*Chrome)|Opera/.test(window.navigator && navigator.userAgent),
		previewMaxWidth : 520,
		previewMaxHeight : 520,
		previewCrop : false,
		previewAsCanvas : true,
		imageCrop : false,
		imageMaxWidth : 1200,
		// The maximum height of resized images:
		imageMaxHeight : 1200,
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
		// ,
		// {action: 'duplicateImage'},
		// {
		// action: 'resizeImage',
		// maxWidth: 700,
		// maxHeight: 500
		// },
		// {action: 'saveImage'}
		//,
		// {action: 'duplicateImage'},
		// {
		// action: 'resizeImage',
		// maxWidth: 300,
		// maxHeight: 200
		// },
		// {action: 'saveImage'}
		]
	}).bind('fileuploadadd', function(e, data) {
		data.exifs = [];
		readExif(data);

	}).bind('fileuploadadded', function(e, data) {

		for (var i = 0; i < data.files.length; i++) {
			var exif = data.exifs[i];
			console.log(data);
			toForm(exif);
		}

	}).bind('fileuploadprocessalways', function(e, data) {

	}).bind('fileuploaddone', function(e, data) {

	}).bind('fileuploadfail', function(e, data) {

	}).bind('fileuploadsubmit', function(e, data) {
		var formData = $('#fileuploadForm').serializeArray();
		data.formData = formData;
		//var inputs = data.context.find(':input');
		//data.formData = inputs.serializeArray();

	});

});
function readExif(fileData) {
	fileData.imageIndex = imageIndex;
	for (var i = 0; i < fileData.files.length; i++) {
		var file = fileData.files[i];
		var index = i;
		var fileName = file.name;
		var imgIndex = imageIndex;
		ExifUtils.readExif(file, function(data) {
			//console.log(index);
			data.fileName = fileName;
			data.imageIndex = imgIndex;
			fileData.exifs[index] = data;
			//console.log(fileData.exifs[index]);
		});
		fileData.files[i] = file;
		imageIndex++;

	}
}

function toForm(data) {
	if (data) {

		// alert(data.toSource());
		var tempIndex = data.imageIndex;
		var index = data.fileName.lastIndexOf(".");
		$("#title" + tempIndex).val(data.fileName.substring(0, index));
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

		$("#WhiteBalance" + tempIndex).val(data.WhiteBalance);
		$("#Software" + tempIndex).val(data.Software);
		$("#Flash" + tempIndex).val(data.Flash);
		$("#ColorSpace" + tempIndex).val(data.ColorSpace2);
		$("#MeteringMode" + tempIndex).val(data.MeteringMode);

		//data.WhiteBalance;
		//data.Software;
		//data.Flash
		//data.ColorSpace
		//data.MeteringMode 测光模式

		//
		console.log($("#iso" + tempIndex));
		console.log(data);
		$("#desc" + tempIndex).val("");
		$("#tags" + tempIndex).val("");
		$("#category1" + tempIndex).val(0);
	}

}
