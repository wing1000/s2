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
<u:define name="title">Sprucy Password Page</u:define>
<u:define name="head"></u:define>
</head>
<body>


	<u:define name="header">Change Password</u:define>

	<u:define name="body">



		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span2 ">
					<div class="sidebar-nav ">
						<c:set var="Password" scope="request">class="active"</c:set>

						<c:import url="SettingsMenu.jsp" />
					</div>
				</div>
				<div class="span9 well offset1">
					<div class="row-fluid">
						<div class="span8  ">

							<form class="form-horizontal" action="/settings/password/done"
								method="post">
								<c:if test="${not empty error }">
									<div class="alert alert-error">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										${error}
									</div>

								</c:if>
								<c:if test="${not empty success }">
									<div class="alert alert-success">
										<button type="button" class="close" data-dismiss="success">&times;</button>
										${success}
									</div>

								</c:if>



								<div class="control-group">
									<label class="control-label" for="oldPassword">Old
										Password</label>
									<div class="controls">
										<input type="password" id="oldPassword" name="oldPassword"
											class="span12" placeholder="Old Password">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="newPassword">New
										Password</label>
									<div class="controls">
										<input type="password" id="newPassword" name="newPassword"
											class="span12" placeholder="new Password">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="reNewPassword">Repeat
										New Password</label>
									<div class="controls">
										<input type="password" id="reNewPassword" name="reNewPassword"
											class="span12" placeholder="Repeat New Password">
									</div>
								</div>



								<div class="control-group">
									<div class="controls">
										<button type="submit" class="btn">Save change</button>
									</div>
								</div>

							</form>


						</div>
						<div class="span5"></div>
					</div>



				</div>
			</div>

		</div>


	</u:define>



</body>
	</html>
</u:body>