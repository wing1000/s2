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
<u:define name="title">Sprucy Camera Page</u:define>
<u:define name="head">
	<u:script src="/public/app/camera.js"></u:script>
	<u:script src="/public/blueimp/tmpl.min.js"></u:script>
</u:define>
</head>
<body>


	<u:define name="header">Camera</u:define>

	<u:define name="body">


		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span2 ">
					<div class="sidebar-nav ">
						<c:set var="Camera" scope="request">class="active"</c:set>
						<c:import url="SettingsMenu.jsp" />
					</div>
				</div>
				<div class="span9 well offset1">
					<div class="row-fluid">
						<div class="span8  ">

							<form class="form-horizontal">



								<div class="control-group">
									<button class="btn btn-mini" type="button" id="camera">Add
										Camera</button>
									<button class="btn btn-mini" type="button" id="lens">Add
										Lens</button>
									<button class="btn btn-mini" type="button" id="tripod">Add
										Tripod</button>
									<button class="btn btn-mini" type="button" id="filter">Add
										Filter</button>
								</div>
								<div class="control-group" id="forms">

									<c:forEach items="${cameras}" var="c" >
										<div>
											<div class="input-prepend span12">
												<span class="add-on"><i class="icon-${c.type}"></i></span> <input
													class=" span12" name="_${c.type}" id="_${c.type}"
													type="text" placeholder="${c.type}" value="${c.equipment}">
											</div>
										</div>
									</c:forEach>

									<div>
										<div class="input-prepend span12">
											<span class="add-on"><i class="icon-camera"></i></span> <input
												class=" span12" name="camera" id="camera" type="text"
												placeholder="Camera">
										</div>
									</div>
									<div>
										<div class="input-prepend span12">

											<span class="add-on"><i class="icon-eye-open"></i></span> <input
												class="span12" name="lens" id="lens" type="text"
												placeholder="Lens">
										</div>
									</div>
									<div>
										<div class="input-prepend span12">
											<span class="add-on"><i class="icon-glass"></i></span> <input
												class="span12 " name="tripod" id="tripod" type="text"
												placeholder="Tripod">
										</div>
									</div>
									<div>
										<div class="input-prepend span12">
											<span class="add-on"><i class="icon-filter"></i></span> <input
												class="span12 " name="filter" id="filter" type="text"
												placeholder="Filter">
										</div>
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

		<script id="camera_tml" type="text/x-tmpl">

						<div>
							<div class="input-prepend span12">
								<span class="add-on"><i class="icon-camera"></i></span> <input class=" span12" name="camera" id="camera" type="text" placeholder="Camera">
							</div>
						</div>
 
</script>
		<script id="lens_tml" type="text/x-tmpl">

						<div>
							<div class="input-prepend span12">

								<span class="add-on"><i class="icon-eye-open"></i></span> <input class="span12" name="lens" id="lens" type="text" placeholder="Lens">
							</div>
						</div>

 
</script>


		<script id="tripod_tml" type="text/x-tmpl">

						<div>
							<div class="input-prepend span12">
								<span class="add-on"><i class="icon-glass"></i></span> <input class="span12 " name="tripod" id="tripod" type="text" placeholder="Tripod">
							</div>
						</div>
 
</script>

		<script id="filter_tml" type="text/x-tmpl">

						<div>
							<div class="input-prepend span12">
								<span class="add-on"><i class="icon-filter"></i></span> <input class="span12 " name="filter" id="filter" type="text" placeholder="Filter">
							</div>
						</div>
 
</script>

	</u:define>



</body>
	</html>
</u:body>