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
<u:define name="title">Sprucy Show Page</u:define>
<u:define name="head"></u:define>
</head>
<body>


	<u:define name="header">Show</u:define>

	<u:define name="body">


		<div class="alert">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>Warning!</strong> Best check yo self, you're not looking too
			good.
		</div>



		<ul class="previews">

			<c:forEach items="${refreshs}" var="refresh">
				<li class=""><a href="${rctx.contextPath}/show/${refresh.idPhoto}"
					target="photo_view" class="preview">
						<div class="caption">
							<div class="title">${refresh.title}</div>
							<div class="info">${refresh.userName}</div>
							<div class="rating rollover">99.6</div>
						</div> <img src="${rctx.contextPath}${refresh.path}" alt="">
				</a></li>
			</c:forEach>
		</ul>

		<div class="pagination  pagination-centered">
			<ul>
				<li><a href="#">Prev</a></li>
				<li class="disabled"><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">Next</a></li>
			</ul>
		</div>

	</u:define>



</body>
	</html>
</u:body>