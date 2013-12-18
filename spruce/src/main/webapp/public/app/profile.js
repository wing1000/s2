var jcrop_api;
$(function() {

	$('#upload_head').fineUploader({

		request : {
			endpoint : '/profile/upload',
			params : {}
		},
		multiple : false,
		validation : {
			allowedExtensions : ['jpeg', 'jpg'],
			sizeLimit : 1024000
			// 50 kB = 50 * 1024 bytes
		},
		classes : {
			buttonHover : '',
			buttonFocus : '',
			success : 'alert alert-success',
			fail : 'alert alert-error'
		},
		text : {
			uploadButton : 'Upload'
		},
		showMessage : function(message) {
			$('#upload_message').html('<div class="alert"><button type="button" class="close" data-dismiss="alert">&times;</button><strong>' + message + '</strong></div>');
		}
	}).on('complete', function(event, id, fileName, data) {
		if (data.success) {
			var path1 = data.path1 + "?" + Math.random();
			$("#head_img").attr("src", path1);
			$("#user_photo").val(data.isHeadPhoto);

			//
			var path0 = data.path0 + "?" + Math.random();
			var userPhoto = $('#ora_user_photo');
			userPhoto.attr("src", path0);
			jcrop_api.setImage(path0);
			jcrop_api.setOptions({
				aspectRatio : 1,
				setSelect : [0, 0, 100, 100]
			});
			// $("#viewUserPhoto").width(userPhoto.width() +
			// 40).height(userPhoto.height() + 40);

			$('#userPhotoModal').modal('show');
		} else {
			notify("upload fail!");
		}
	}).on("upload", function(event, id, filename) {
		$(this).fineUploader('setParams', {
			"user_photo" : $("#user_photo").val()
		}, id);
	}).on('error', function(event, id, name, reason) {
		$(".qq-upload-list li").prepend('<button type="button" class="close" data-dismiss="alert">&times;</button>');
	});
	$(".qq-upload-button").addClass("btn btn-default btn-small").removeClass("qq-upload-button").after('  <a	href="#userPhotoModal" role="button" class="btn btn-default btn-small"	data-toggle="modal">Crop Photo</a>');

	$(".qq-upload-list li").prepend('<button type="button" class="close" data-dismiss="alert">&times;</button>');
	var userPhoto = $('#ora_user_photo');
	userPhoto.Jcrop({
		onChange : showCoords,
		onSelect : showCoords,
		aspectRatio : 1,
		//maxSize : [300, 300],
		//minSize : [64, 64],
		aspectRatio:1,
		setSelect : [0, 0, 200, 200]
	}, function() {
		jcrop_api = this;
		jcrop_api.animateTo([0, 0, 200, 200]);
	});

	$("#SaveUserPhoto").click(function() {
		$.post("/settings/profile/crop/done", $("#cropForm").serialize(), function(data) {
			if (data.status == "Success") {
				var path1 = data.path1 + "?" + Math.random();
				$("#head_img").attr("src", path1);

				var path0 = data.path0 + "?" + Math.random();
				jcrop_api.setImage(path0);

				$('#userPhotoModal').modal('hide');
			}
		}, "json");
	});

	$('#birthday').datepicker({
		format : 'yyyy-mm-dd',
        defaultStartDate:"1980-02-02",
		language : "zh-CN"
	});
});
function showCoords(c) {
	$('#x1').val(c.x);
	$('#y1').val(c.y);
	$('#x2').val(c.x2);
	$('#y2').val(c.y2);
	$('#w').val(c.w);
	$('#h').val(c.h);
};