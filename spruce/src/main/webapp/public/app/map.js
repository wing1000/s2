window.fengfei = window.fengfei || {};

(function() {

	fengfei.GMapV3 = function(el, lat, lng) {
		if (!el) {
			return;
		}
		this.geocoder = new google.maps.Geocoder();
		this.isGPS = true;
		this.latLng = false;
		this.initLatLng = false;
		if (lat & lng) {
			this.latLng = new google.maps.LatLng(lat, lng);
		} else {
			lat = el.getAttribute("lat");
			lng = el.getAttribute("lng");
		}
		if (lat != null && lat != "" && lat != 0 && lng != null && lng != "" && lng != 0) {
			this.latLng = new google.maps.LatLng(lat, lng);
			this.initLatLng = this.latLng;
		} else {
			this.latLng = new google.maps.LatLng(30.619004797647808, 104.073486328125);
			//this.latLng = new google.maps.LatLng(0, 0);
			this.isGPS = false;
			this.initLatLng = this.latLng;
		}

		var mapOptions = {
			center : this.latLng,
			zoom : 8,
			mapTypeId : google.maps.MapTypeId.TERRAIN
		};

		this.map = new google.maps.Map(el, mapOptions);
		this.marker = false;
		if (this.isGPS) {
			this.marker = new google.maps.Marker({
				map : this.map,
				title : 'Location of your photo '
			});
		}

		var elevator = new google.maps.ElevationService();

		this.initZoom = this.map.getZoom();
		if (this.isGPS && this.marker) {
			this.marker.setPosition(this.latLng);
		}
		this.panTo = function(latLng) {
			if (latLng) {
				this.map.panTo(latLng);
			}
		}
		this.reset = function(fn) {
			this.markMarkerAndPanTo(this.initLatLng);

			this.map.setZoom(this.initZoom);
			if (fn) {
				if (this.isGPS)
					fn(this.initLatLng);
				else
					fn(false);
			}
		}
		this.markMarkerAndPanTo = function(latLng) {
			if (!latLng) {
				return;
			}
			this.latLng = latLng;
			this.map.panTo(latLng);
			if (!this.marker) {
				this.marker = new google.maps.Marker({
					map : this.map,
					title : 'Location of your '
				});
			}
			this.marker.setPosition(latLng);

		}
		this.requestCurrentElevation = function(callback) {
			return this.requestElevation(this.latLng, callback);

		}
		this.requestElevation = function(latLng, callback) {
			if (!latLng) {
				latLng = this.latLng;
			}
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
						if (callback) {
							callback(elevation, results);
						}

					} else {
						notify("No results found");
					}
					//console.info(results);
				} else {
					notify("Elevation service failed due to: " + status);
				}
			});
		}
		this.getCurrentLatLngAddress = function(cb) {
			return this.getLatLngAddress(this.latLng, cb);

		}
		this.getLatLngAddress = function(latLng, cb) {
			if (latLng && latLng.lat() == 0 && latLng.lng() == 0) {
				return;
			}
			this.geocoder.geocode({
				'latLng' : latLng
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					if (results[0]) {
						var address = results[0].formatted_address;
						if (cb) {
							cb(address, results);
						}

					} else {
						notify('No results found');
					}
				} else {
					notify('Geocoder failed due to: ' + status);
				}
			});
		}

		this.getLatLngByAddress = function(address, cb) {
			if (!address || "" == address) {
				address = "Chengdu, China";
			}
			this.geocoder.geocode({
				'address' : address
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					var myLatLng = results[0].geometry.location;
					if (cb) {
						cb(myLatLng, results);
					}
					//results数组里有很多有用的信息，包括坐标和返回的标准位置信息
				} else {
					alert(interGeoAnalysisFailed);
				}
			});

		}
		var that = this;

		var SinaUrl = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";
		var MaxmindUrl = "http://j.maxmind.com/app/geoip.js";
		var Url126 = "http://ip.ws.126.net/ipquery";
		var QQUrl = "http://fw.qq.com/ipaddress";
		//{"ret":1,"start":"125.69.0.0","end":"125.69.159.255","country":"\u4e2d\u56fd","province":"\u56db\u5ddd","city":"\u6210\u90fd","district":"","isp":"\u7535\u4fe1","type":"","desc":""}
		this.getLocalLatLng = function() {

			loadScript("http://j.maxmind.com/app/geoip.js", function() {
				if ( typeof (geoip_longitude) != "undefined" && typeof (geoip_latitude) != "undefined") {
					that.latLng = new google.maps.LatLng(geoip_latitude(), geoip_longitude());
					that.initLatLng = that.latLng;
					that.map.panTo(that.latLng);
				}
			});

		}
		this.toLocalLatLng = function() {
			if (!this.isGPS) {
				this.getLocalLatLng();
			}
		}
		this.gmenu = false;
		/***
		 * gmap.initMapContextMenu([{name:"",callback:function(){map, latLng}},{},{}]);
		 */
		this.initMapContextMenu = function() {
			this.gmenu = new contextMenu({
				map : this.map
			});
		}
		this.addContextMenuSep = function() {
			if (!this.gmenu) {
				this.initMapContextMenu();
			}
			this.gmenu.addSep();
		}
		this.addContextMenuItem = function(name, callback) {
			if (!this.gmenu) {
				this.initMapContextMenu();
			}
			this.gmenu.addItem(name, callback);
		}
	}
})();
