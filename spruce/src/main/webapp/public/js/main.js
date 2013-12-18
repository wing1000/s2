function Auth() {
	
}

function checkWeibo() {
	var isLogin = WB2.checkLogin();
	if (isLogin) {

	} else {
		WB2.anyWhere(function(W) {
			W.widget.connectButton({
				id : "wb_connect_btn",
				type : '2,2',
				callback : {
					login : function(o) {
						alert(o.screen_name)
					},
					logout : function() {
						alert('logout');
					}
				}
			});
		});
	}
}
function checkQQ() {

}