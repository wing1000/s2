(function (jQuery) {
    jQuery.fn.extend({
        /**
         * readonly
         */
        readonfy: function () {
            var $this = $(this);
            var val = $this.val();
            if (val == "") {
                $this.removeAttr("readonly");
            } else {
                $this.attr("readonly", "readonly");
            }
        }
    });
    jQuery.each([ "delete", "put" ], function (i, method) {
        jQuery[ method ] = function (url, data, callback, type) {
            // shift arguments if data argument was omitted
            if (jQuery.isFunction(data)) {
                type = type || callback;
                callback = data;
                data = undefined;
            }

            return jQuery.ajax({
                url: url,
                type: method,
                dataType: type,
                data: data,
                success: callback
            });
        };
    });
})(jQuery);

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/\{(\d+)\}/g, function () {
        var val = args[arguments[1]];
        return (!val) ? arguments[0] : val;
    });
};

function loadScript(url, cb) {
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.onload = script.onreadystatechange = function () {
        if (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") {
            cb();
            // Handle memory leak in IE
            script.onload = script.onreadystatechange = null;
        }
    };
    script.src = url + "?t=" + new Date().getTime();
    head.appendChild(script);
}

