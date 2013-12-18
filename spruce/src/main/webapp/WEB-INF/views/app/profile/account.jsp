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
<u:define name="title">Sprucy Account Page</u:define>
<u:define name="head"></u:define>
</head>
<body>


	<u:define name="header">Account</u:define>

	<u:define name="body">



		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span2 ">
					<div class="sidebar-nav ">
						<c:set var="Account" scope="request">class="active"</c:set>
						<c:import url="SettingsMenu.jsp" />
					</div>
				</div>
				<div class="span9 well offset1">
					<div class="row-fluid">
						<div class="span8  ">

							<form class="form-horizontal">
								<span id="qqLoginBtn"></span>
								<script type="text/javascript">
									QC.Login({
										btnId : "qqLoginBtn" //���밴ť�Ľڵ�id
									});
								</script>

								<div id="wb_connect_btn"></div>
								<script type="text/javascript">
									WB2.anyWhere(function(W) {
										W.widget.connectButton({
											id : "wb_connect_btn",
											type : '3,2',
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