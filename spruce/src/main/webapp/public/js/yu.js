/*
 兼容于ie,firefox,netscape的等比例图片本地预览的javascript实现
 author:semovy@gmail.com
 date:14:39 上午 2007-10-9
 @param:targetImg string id 待显示等比例调整过的目标元素的id字符串
 @param:imgSrc string src 等处理的图片源路径字符串
 @param:fitWidth int 等显示图片的最大宽度
 @param:fitHeight int 等显示图片的最大高度
 */
function resizeImage(targetImg, imgSrc, fitWidth, fitHeight) {
	var imgSrc = "file:///" + imgSrc.replace(/\\/g, "/");// 本地路径c:\a.jpg,而ff,ns不支持,所以替换成file:///c:/a.jpg这种形式
	var img = document.getElementById(targetImg);// 获取目标图片元素容器
	var tempImg = new Image();// 建立临时图片对象
	tempImg.src = imgSrc;// 给临时图片对象赋予图片源
	var scale = 1.0;// 图片度高比例因子.
	var width = 0, height = 0;

	/*
	 * firefox实现了complete属性，而ie实现了complete属性和readyState属性 但是两者对属性的定义好像不同：
	 * firefox： 一个图像被下载完毕，complete属性就是true，没有下载完毕则为false
	 * ie：一个图像没有被下载完毕，则readyState属性为uninitialized,complete属性是false.当下载完毕时，
	 * readyState为complete，而如果此时图片还没有显示，complete为false,显示以后(display:block)此属性才变成true
	 */

	if (document.all)// 如果是ie
	{
		if (tempImg.readyState == 'complete') {
			width = tempImg.width;// 获取源图片宽,高
			height = tempImg.height;
		}
	} else
		(tempImg.complete)// fire fox ,netscape
	{
		width = tempImg.width;
		height = tempImg.height;
	}
	scale = width / height;// 宽度比例因子
	if (width > fitWidth)// 等比例调整
	{
		width = fitWidth;
		height = width / scale;
		if (height > fitHeight) {
			height = fitHeight;
			width = height * scale;
		}
	}
	if (height > fitHeight) {
		height = fitHeight;
		width = height * scale;
	}
	img.width = width;// 调整后的宽,高
	img.height = height;
	img.src = imgSrc;
	img.style.display = "";// 显示图片
}
function setImagePreview() {
	var docObj = document.getElementById("doc");
	var fileName = docObj.value;
	var imgObjPreview = document.getElementById("preview");
	if (docObj.files && docObj.files[0]) {
		// 火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		imgObjPreview.style.width = '150px';
		imgObjPreview.style.height = '120px';
		imgObjPreview.src = docObj.files[0].getAsDataURL();
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("localImag");
		// 必须设置初始大小
		localImagId.style.width = "150px";
		localImagId.style.height = "120px";
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}

// #imghead
// {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
// <input type="file" onchange="previewImage(this)" />
function previewImage(file) {
	var MAXWIDTH = 100;
	var MAXHEIGHT = 100;
	var div = document.getElementById('preview');
	if (file.files && file.files[0]) {
		div.innerHTML = '<img id=imghead>';
		var img = document.getElementById('imghead');
		img.onload = function() {
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			img.width = rect.width;
			img.height = rect.height;
			img.style.marginLeft = rect.left + 'px';
			img.style.marginTop = rect.top + 'px';
		}
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.src = evt.target.result;
		}
		reader.readAsDataURL(file.files[0]);
	} else {
		var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
		file.select();
		var src = document.selection.createRange().text;
		div.innerHTML = '<img id=imghead>';
		var img = document.getElementById('imghead');
		img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
		var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
				img.offsetHeight);
		status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
		div.innerHTML = "<div id=divhead style='width:" + rect.width
				+ "px;height:" + rect.height + "px;margin-top:" + rect.top
				+ "px;margin-left:" + rect.left + "px;" + sFilter + src
				+ "\"'></div>";
	}
}
function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		top : 0,
		left : 0,
		width : width,
		height : height
	};
	if (width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		} else {
			param.width = Math.round(width / rateHeight);
			param.height = maxHeight;
		}
	}

	param.left = Math.round((maxWidth - param.width) / 2);
	param.top = Math.round((maxHeight - param.height) / 2);
	return param;
}
// #preview{filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
function handleFiles(files) {
	if (typeof FileReader == "undefined") {
		if ("Microsoft Internet Explorer" == navigator.appName) {
			var preview = document.getElementById("preview");
			preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = document
					.getElementById("fileInput").value;
			document.getElementById("image").style.display = "none";
		} else {
			alert("Not Support!");
		}
	} else {
		for ( var i = 0; i < files.length; i++) {
			var file = files[i];
			var imageType = /image.*/;
			// 通过type属性进行图片格式过滤
			if (!file.type.match(imageType)) {
				continue;
			}
			// 读入文件
			var reader = new FileReader();
			reader.onload = function(e) {
				var result = document.getElementById("preview");
				result.innerHTML = '<img src="' + this.result + '" />';
			}
			reader.readAsDataURL(file);
		}
	}
}
