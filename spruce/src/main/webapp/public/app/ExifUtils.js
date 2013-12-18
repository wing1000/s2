var ExifUtils = {};
ExifUtils.takenDate = function(a) {
	var b = a.split(" ");
	return b[0] = b[0].replace(/:/gi, "-"), b[0] + " " + b[1]
};
ExifUtils.takenDateToHuman = function(a) {
	date = new Date(a.replace(/-/gi, " "));
	switch (date.getMonth()) {
		case 0:
			m = "January";
			break;
		case 1:
			m = "February";
			break;
		case 2:
			m = "March";
			break;
		case 3:
			m = "April";
			break;
		case 4:
			m = "May";
			break;
		case 5:
			m = "June";
			break;
		case 6:
			m = "July";
			break;
		case 7:
			m = "August";
			break;
		case 8:
			m = "September";
			break;
		case 9:
			m = "October";
			break;
		case 10:
			m = "November";
			break;
		case 11:
			m = "December"
	}
	return m + " " + date.getDate() + ", " + date.getFullYear()
};
ExifUtils.readExif = function(file, callback) {
	var data = {};

	if (window.FileReader && FileReader.prototype.readAsBinaryString) {

		var fileReader = new FileReader();
		fileReader.onload = function(e) {
			var contents = e.target.result;
			// alert(contents);
			var oFile = new BinaryFile(contents);
			var a = EXIF.readFromBinaryFile(oFile);
			if (a) {
				// console.info(a);
				var g = "";
				//typeof a.ExposureTime != "undefined" && (a.ExposureTime < 1 ? g = "1/" +Math.round(  1 /a.ExposureTime) : g = Math.floor(a.ExposureTime));
				var h = "", j = "";
				typeof a.DateTimeOriginal != "undefined" ? h = a.DateTimeOriginal : typeof a.DateTimeDigitized != "undefined" && ( h = a.DateTimeDigitized), h != "" && ( h = ExifUtils.takenDate(h), j = ExifUtils.takenDateToHuman(h));
				var ev = "";
				var eb = a.ExposureBias;
				// if ( typeof eb != "undefined") {
				// ev = eb < 0 ? "-" : "+";
				// if (eb == 0) {
				// ev = "0";
				// } else if (eb >= 1 || eb <= -1) {
				// ev += Math.floor(eb);
				// } else {
				// ev += "1/" + Math.abs(1 / eb);
				// }
				// }

				a.MakerNote = [];
				a.UserComment = [];
				data = a;
				data.ExposureTime2 = g;
				data.DateTimeOriginal2 = h;
				data.DateTimeOriginal3 = j;
				data.ev = ev;

				if (data.Lens && data.Lens instanceof Array) {
					var lens = data.Lens;
					var l1 = lens[0].numerator / lens[0].denominator;
					var l2 = lens[1].numerator / lens[1].denominator;
					var l3 = lens[2].numerator / lens[2].denominator;
					var l4 = lens[3].numerator / lens[3].denominator;
					data.Lens2 = l1 + "-" + l2 + "mm f/" + l3 + "-" + l4;
					if (!data.LensModel) {
						data.LensModel = data.Lens2;
					}
				}
				//http://gvsoft.homedns.org/exif/makernote-nikon-type2.html#0x0083
				if (data.LensType) {
					var value = data.LensType;
					var bits = [];
					if ((value & 1) == 1)
						bits.push("MF");
					else
						bits.push("AF");

					if ((value & 2) == 2)
						bits.push("D");

					if ((value & 4) == 4)
						bits.push("G");

					if ((value & 8) == 8)
						bits.push("VR");

					data.LensType2 = bits.join(" ");
				}
				console.info(data);
				data.FocalLength2 = ExifUtils.toNumber(a.FocalLength);
				data.iso = a.ISOSpeedRatings;
				data.FNumber2 = ExifUtils.toNumber(a.FNumber);
				data.ExposureTime2 = ExifUtils.toHumanFraction(data.ExposureTime);
				data.ev = ExifUtils.toSimpleHumanFraction(a.ExposureBias);
				data.ExposureBias2 = ExifUtils.toZero(a.ExposureBias);

 
			}

			callback(data);
		}
		fileReader.readAsBinaryString(file);

	}

}
ExifUtils.toSimpleHumanFraction = function(val) {
	if ( val instanceof Number) {
		if (val == 0 || val.denominator == 0) {
			return 0;
		} else{
            return val.numerator + "/" + val.denominator;
        }

//        if (val.numerator == -1 || val.numerator == 1) {
//
//			return val.numerator + "/" + val.denominator;
//		} else {
//			return val;
//		}
	} else {
		return ExifUtils.toHumanFraction(val);
	}
}
// fileReader.readAsArrayBuffer (file);
ExifUtils.toHumanFraction = function(val) {
	if ( val instanceof Number) {
		if (val == 0 || val.denominator == 0) {
			return 0;
		}
		if (val.denominator > val.numerator) {
			if (val.numerator == 1 || val.numerator == -1) {
				return val.numerator + "/" + val.denominator;
			} else {
				return (val.numerator >= 0 ? "" : "-") + "1/" + Math.abs(Math.round(val.denominator / val.numerator));
			}

		} else {
			return val.toFixed(0);
		}
	}
	if (val && val.indexOf("/")) {
		var vals = val.split("/");
		var n1 = parseInt(vals[0]);
		var n2 = parseInt(vals[1]);
		if (n1 == 0) {
			return 0;
		}
		if (n1 != 1) {
			n2 = Math.round(n2 / n1);
		} else {
			return val;
		}
		return "1/" + n2;
	}
	return val;
}
ExifUtils.toNumber = function(val) {
	if ( val instanceof Number) {
		return val;
	}
	if (val && val.indexOf("/")) {
		var vals = val.split("/");
		var n1 = parseInt(vals[0]);
		if (n1 == 0) {
			return 0;
		}
	}
	return eval('(' + val + ')')
}
ExifUtils.toZero = function(val) {
	if ( val instanceof Number) {
		if (val.denominator == 0) {
			return 0;
		}
		return val.toFixed(2);
	}
	//console.info( typeof val);
	if ( typeof val == "number") {
		return val.toFixed(2);
	}
	if (val && val instanceof String && val.indexOf("/")) {
		var vals = val.split("/");
		var n1 = parseInt(vals[0]);
		if (n1 == 0) {
			return 0;
		}
	}
	return val;
}
var formatFileSize = function(bytes) {
	if ( typeof bytes !== 'number') {
		return '';
	}
	if (bytes >= 1000000000) {
		return (bytes / 1000000000).toFixed(2) + ' GB';
	}
	if (bytes >= 1000000) {
		return (bytes / 1000000).toFixed(2) + ' MB';
	}
	return (bytes / 1000).toFixed(2) + ' KB';
};
