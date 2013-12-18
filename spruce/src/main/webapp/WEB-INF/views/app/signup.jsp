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
<u:define name="title">Sprucy Sign Up Page</u:define>
<u:define name="head">
	<style type="text/css">
.control-group label.error {
	margin-left: 10px;
	width: auto;
	display: inline;
	padding-left: 9px;
	color: #b94a48;
	border: none;
}

.control-group p {
	padding: 4px;
}

.control-group label {
	padding-right: 9px;
	width: 200px;
	min-width: 200px;
	border: none;
}

.control-group input.error,.control-group select.error,.control-group textarea.error
	{
	color: #b94a48;
	border-color: #b94a48;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}

.control-group .error:focus,.control-group input.error:focus,.control-group select.error:focus,.control-group textarea.error:focus
	{
	border-color: #953b39;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px
		#d59392;
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #d59392;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #d59392;
}
</style>

	<script src="@{'/public/js/jquery.validate.js'}" type="text/javascript"></script>
	<script src="@{'/public/js/additional-methods.js'}"
		type="text/javascript"></script>
	<script
		src="@{'/public/js/jquery.validate.localization/messages_cn.js'}"
		type="text/javascript"></script>
	<script src="@{'/public/app/signup.js'}" type="text/javascript"></script>
</u:define>
</head>
<body>
	<u:define name="header">Sign Up</u:define>
	<u:define name="body">

		<div class="container">
			<div id="login" class="span12  login">

				<form action="${rctx.contextPath}/signup/done" method="post" id="signUpForm"
					class="form">
					<c:if test="${not empty error}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							${error}
						</div>
					</c:if>


					<p class="control-group ">
						<label for="username">User Name</label> <input id="username"
							name="username" type="text" placeholder="User Name"
							value="${up.userName}" />
					</p>
					<p class="control-group ">
						<label for="email">Email</label> <input id="email" name="email"
							type="email" placeholder="Email" value="${up.email}" />
					</p>
					<p class="control-group ">
						<label for="password">Password</label> <input id="password"
							name="password" type="password" placeholder="Password" />
					</p>
					<p class="control-group ">
						<label for="confirm_password">Confirm Password</label> <input
							id="confirm_password" name="confirm_password" type="password"
							placeholder="Confirm Password" />
					</p>

					<p class="control-group ">

						<label for="agree" class="checkbox inline"> <input
							type="checkbox" class="checkbox" id="agree" name="agree"
							checked="checked" /> Please agree to our policy
						</label>

					</p>

					<p>
						<input type="submit" value="Submit" class="btn" />
					</p>


				</form>

			</div>
		</div>
	</u:define>



</body>
	</html>
</u:body>