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
<u:define name="title">Sprucy Settting Page</u:define>
<u:define name="head"></u:define>
</head>
<body>



	<u:define name="header">Information</u:define>

	<u:define name="body">


		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span2 ">
					<div class="sidebar-nav ">
						<c:set var="Profile" scope="request">class="active"</c:set>
						<c:import url="SettingsMenu.jsp" />
					</div>
				</div>
				<div class="span9 well offset1">
					<div class="row-fluid">
						<div class="span12  ">

							<c:if test="${not empty error }">
								<div class="alert alert-error">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									${error}
								</div>

							</c:if>
							<form class="form-horizontal" action="/settings/profile/done"
								method="post">



								<div class="control-group ">
									<label class="control-label" for="gender0">Gender</label>
									<div class="controls ">
										<label class="radio inline"> <input type="radio"
											id="gender1" name="gender" value="1"
											${user.gender==1?"checked":""}> Male
										</label> <label class="radio inline"> <input type="radio"
											id="gender2" name="gender" value="2"
											${user.gender==2?"checked":""}> Female
										</label> <label class="radio inline"> <input type="radio"
											id="gender0" name="gender" value="0"
											${(user.gender==null
									|| user.gender==0)?"checked":""}>
											Not specified
										</label>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="username">Username</label>
									<div class="controls">
										<input type="text" id="username1" name="username1"
											class="span9" placeholder="Username" value="${user.userName}"
											disabled="disabled"> <input type="hidden"
											id="username" name="username" class="span9"
											value="${user.userName}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="email">Email</label>
									<div class="controls">
										<input type="text" id="email1" name="email1" class="span9"
											placeholder="Email" value="${user.email}" disabled="disabled">
										<input type="hidden" id="email" name="email" class="span9"
											placeholder="Email" value="${user.email}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="nicename">Nice Name</label>
									<div class="controls">
										<input type="text" id="nicename" name="nicename" class="span9"
											placeholder="Nice Name" value="${user.niceName}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="birthday">Birthday</label>
									<div class="controls">
										<input type="text" id="birthday" name="birthday" class="span9"
											placeholder="Birthday" value="${user.birthday}">
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="phone">Phone</label>
									<div class="controls">
										<input type="text" id="phone" name="phone" class="span9"
											placeholder="Phone" value="${user.phoneNum}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="firstname">First Name</label>
									<div class="controls">
										<input type="text" id="firstname" name="firstname"
											class="span9" placeholder="First Name"
											value="${user.firstName}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="lastname">Last Name</label>
									<div class="controls">
										<input type="text" id="lastname" name="lastname" class="span9"
											placeholder="Last Name" value="${user.lastName}">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="location">Location</label>
									<div class="controls">
										<input id="city" name="city" class="span9" placeholder="City"
											type="text" value="${user.city}" /> <input class="span9"
											id="state" name="state" placeholder="State" type="text"
											value="${user.state}" /> <input id="country" name="country"
											class="span9" placeholder="Country" type="text"
											value="${user.country}" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="about">about you</label>
									<div class="controls">
										<textarea id="about" name="about" class="span9">${user.about}</textarea>
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