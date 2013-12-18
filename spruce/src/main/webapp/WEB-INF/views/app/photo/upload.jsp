<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="u" uri="/hare-ui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<u:body template="/layout/layout.jsp" action="#">
	<html>
<head>
<meta charset="UTF-8">
<u:define name="title">Sprucy Upload Page</u:define>
<u:define name="head">
	<style type="text/css">
<!--
#dropbox {
	height: 200px;
}

.left {
	text-align: left;
	padding: 0px;
	padding-right: 4px;
	margin: 0px;
	width: 50px;
	*width: 50px;
	margin: 0px;
}

.right_line {
	border-right: 1px solid #dddddd;
}

.non-border {
	border: 0px;
}

.div200 {
	width: 200px;
}

.magin_left {
	margin-left: 0px;
}
-->
</style>
	<!-- Bootstrap CSS fixes for IE6 -->
	<!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
	<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
	<u:style src="/public/blueimp/css/jquery.fileupload-ui.css"></u:style>
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<u:script src="/public/blueimp/js/vendor/jquery.ui.widget.js"></u:script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<u:script src="/public/blueimp/load-image.min.js"></u:script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<u:script src="/public/blueimp/canvas-to-blob.min.js"></u:script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<u:script src="/public/blueimp/js/jquery.iframe-transport.js"></u:script>
	<!-- The basic File Upload plugin -->
	<u:script src="/public/blueimp/js/jquery.fileupload.js"></u:script>
	<!-- The File Upload processing plugin -->
	<u:script src="/public/blueimp/js/jquery.fileupload-process.js"></u:script>
	<!-- The File Upload image resize plugin -->
	<u:script src="/public/blueimp/js/jquery.fileupload-resize-limit.js"></u:script>
	<!-- The File Upload validation plugin -->
	<u:script src="/public/blueimp/js/jquery.fileupload-validate.js"></u:script>
	<u:script src="/public/js/binaryajax.js"></u:script>
	<u:script src="/public/js/exif.js"></u:script>
	<u:script src="/public/app/ExifUtils.js"></u:script>

	<u:script src="/public/app/upload.js"></u:script>
	<script type="text/javascript">
		var url = "${rctx.contextPath}/upload/done";
	</script>

</u:define>
</head>
<body>

	<u:define name="header">Home</u:define>

	<u:define name="body">


		<form id="fileuploadForm" action="${rctx.contextPath}/upload/done"
			method="POST" enctype="multipart/form-data">

			<span class="btn btn-success fileinput-button"> <i
				class="icon-plus icon-white"></i> <span>Add files...</span> <!-- The file input field used as target for the file upload widget -->
				<input id="fileupload" type="file" name="file">
			</span>

			<p></p>
			<!-- The global progress bar -->

			<!-- The container for the uploaded files -->
			<div id="files">

				<table class="table table-striped non-border span12" id="content"
					style="display: none">
					<thead>
						<tr class=" ">
							<td colspan="3" class="non-border">
								<div id="progress"
									class="progress progress-success progress-striped">
									<div class="bar"></div>
								</div>
							</td>
						</tr>
					</thead>
					<tbody data-target="#modal-gallery" data-toggle="modal-gallery"
						class="files">


						<tr>
							<td class="right_line">
								<div class="span4 magin_left">
									<div id="preview" class="  img-rounded "></div>
									<p>

										file name: <span id="name"></span>
									</p>
									<p>
										source size: <span id="size"></span>

									</p>
								</div>
							</td>
							<td class="right_line">
								<div class="span3 magin_left">
									<div class="block">
										<label for="title1">Title</label> <input type="text"
											placeholder="title" maxlength="100" id="title1" value=""
											name="title" class=" input-width "> <input
											type="hidden" placeholder="ID" maxlength="100" id="id1"
											value="" name="id" class=" input-width ">
									</div>
									<div class="block">
										<label for="desc1">Description</label>
										<textarea class=" input-width " id="desc1"
											placeholder="Description" name="desc"></textarea>
									</div>
									<div class="block">
										<select name="category" id="category1"
											class="category select-width">
											<optgroup label="Category"></optgroup>
											<option value="10">Abstract</option>
											<option value="11">Animals</option>
											<option value="5">Black and White</option>
											<option value="1">Celebrities</option>
											<option value="9">City and Architecture</option>
											<option value="15">Commercial</option>
											<option value="16">Concert</option>
											<option value="20">Family</option>
											<option value="14">Fashion</option>
											<option value="2">Film</option>
											<option value="24">Fine Art</option>
											<option value="23">Food</option>
											<option value="3">Journalism</option>
											<option value="8">Landscapes</option>
											<option value="12">Macro</option>
											<option value="18">Nature</option>
											<option value="4">Nude</option>
											<option value="7">People</option>
											<option value="19">Performing Arts</option>
											<option value="17">Sport</option>
											<option value="6">Still Life</option>
											<option value="21">Street</option>
											<option value="26">Transportation</option>
											<option value="13">Travel</option>
											<option value="22">Underwater</option>
											<option value="27">Urban Exploration</option>
											<option value="25">Wedding</option>
											<option selected="selected" value="0">Uncategorized</option>
										</select>
									</div>

									<div class="block">
										<label for="tags1">Tags</label>
										<textarea rows="" cols="" placeholder="Tags (divide by comma)"
											class="tags" name="tags" id="tags1"></textarea>
									</div>
									<div class="checkbox">
										<label for="adult1"><input type="checkbox" value="1"
											id="adult1" name="adult">For adult content?</label>
									</div>
									<div class="checkbox">
										<label for="copyright1"><input type="checkbox"
											value="1" id="copyright1" name="copyright" checked="checked">Owns
											the copyright?</label>
									</div>
								</div>
							</td>
							<td>
								<div class="span3 magin_left">

									<div class="inline input-prepend">
										<span class="add-on"><span class=" span1 left">Camera
										</span></span> <input type="text" placeholder="Camera" value=""
											class="Camera" id="camera1" name="camera"> <input
											type="hidden" placeholder="Make" value="" class="make"
											id="make1" name="make">
									</div>
									<div class="inline input-prepend">
										<span class="add-on"><span class=" span1 left">Lens</span>
										</span> <input type="text" placeholder="Lens" value="" class="lens"
											id="lens1" name="lens">
									</div>


									<div class="inline input-prepend">
										<span class="add-on"><span class=" span1 left">Focus
										</span></span> <input type="text" placeholder="Focal Length" value=""
											class="focal-length" id="focus1" name="focus">
									</div>
									<div class="inline input-prepend">
										<span class="add-on "><span class=" span1 left">Shutter
										</span></span> <input type="text" placeholder="Shutter Speed" value=""
											class="shutter-speed" id="shutter1" name="shutter">
									</div>
									<div class="inline input-prepend">
										<span class="add-on"><span class=" span1 left">Aperture</span></span>
										<input type="text" placeholder="Aperture" value=""
											class="aperture" id="aperture1" name="aperture">
									</div>
									<div class="inline input-prepend last">
										<span class="add-on"><span class=" span1 left">ISO
										</span></span> <input type="text" placeholder="ISO/Film" value=""
											class="iso" id="iso1" name="iso">
									</div>
									<div class="inline input-prepend last">
										<span class="add-on"><span class=" span1 left">EV</span></span>
										<input type="text" placeholder="Exposure Compensation"
											value="" class="iso" id="ev1" name="ev">
									</div>
									<div class="inline input-prepend">
										<span class="add-on"><span class=" span1 left">
												Taken </span></span> <input type="hidden" value="" id="taken_at1"
											name="taken_at"> <input type="text"
											placeholder="Date Taken" value="" class="date"
											id="taken_at_display1">
									</div>

								</div>
							</td>
						</tr>
						<tr class="template-upload fade in">

							<td colspan="2"></td>

							<td><span class="start" id="start"> </span> <!-- 			 <span class="cancel" id="Cancel">

						<button class="btn btn-warning">
							<i class="icon-ban-circle icon-white"></i> <span>Cancel</span>
						</button>


				</span> --></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>


	</u:define>



</body>
	</html>
</u:body>