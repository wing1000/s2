<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="u" uri="/hare-ui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<u:body template="/layout/layout.jsp">
	<html>
<head>


<u:define name="title">Sprucy Login Page</u:define>
<meta charset="UTF-8">
<u:define name="head">
	<style type="text/css">
.input_control_group {
	margin-bottom: 4px;
}
</style>
	<script
		src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=1166443256"
		type="text/javascript" charset="utf-8"></script>
</u:define>
</head>
<body>

	<u:define name="header">Login</u:define>
	<u:define name="body">
		<div id="login" class="container-960 login">
			<div class="row-fluid">
				<div class="span5">
					<h3>Login to your account</h3>
					<div class="control-group">
						<div class="controls">
							<div id="wb_connect_btn" class="btn"></div>
							<script type="text/javascript">
								WB2.anyWhere(function(W) {
									W.widget.connectButton({
										id : "wb_connect_btn",
										type : '4,2',
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
							</script>
						</div>
					</div>
				</div>
				<div class="span6">
					<form class="form-horizontal" method="post"
						action="${rctx.contextPath}/login${cpage}">
						<div class="control-group">
							No account yet? <a href="${rctx.contextPath}/signup">Sign up</a>
						</div>
						<c:if test="${not empty error}">
							<div class="alert alert-error control-group">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								${error}
							</div>
						</c:if>
			
						<div class="input_control_group">Login with your username or
							email</div>
						<div class="input_control_group">

							<input type="text" name="email" id="inputEmail"
								placeholder="Email or Username" value="${email}" />
						</div>
						<div class="input_control_group">
							<input type="password" name="password" id="inputPassword"
								placeholder="Password" />
						</div>
						<div class="input_control_group">
							<small class="left terms"><a href="/recover" target="_">Can't
									access your account?</a></small>
							<button type="submit" class="btn">Login</button>
						</div>


					</form>
				</div>
			</div>

		</div>

	</u:define>


</body>
	</html>
</u:body>