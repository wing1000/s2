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
<u:define name="title">Sprucy Notifications Page</u:define>
<u:define name="head"></u:define>
</head>
<body>


	<u:define name="header">Notifications</u:define>

	<u:define name="body">


		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span2 ">
					<div class="sidebar-nav ">
						<c:set var="Notifications" scope="request">class="active"</c:set>
						<c:import url="SettingsMenu.jsp" />
					</div>
				</div>
				<div class="span9 well offset1">
					<div class="row-fluid">
						<div class="span8  ">

							<form class="form-horizontal">



								<div class="checkbox control-group">
									<label for="commented_on_your_wall"> <input
										checked="checked" id="commented_on_your_wall"
										name="commented_on_your_wall" type="checkbox" value="on" />
										Somebody comments on my wall
									</label>
								</div>
								<div class="checkbox control-group">
									<label for="commented_on_your_photo"> <input
										checked="checked" id="commented_on_your_photo"
										name="commented_on_your_photo" type="checkbox" value="on" />Somebody
										comments on my photo
									</label>
								</div>


								<div class="checkbox control-group">
									<label for="commented_on_your_story"> <input
										checked="checked" id="commented_on_your_story"
										name="commented_on_your_story" type="checkbox" value="on" />Somebody
										comments on my story
									</label>
								</div>


								<div class="checkbox control-group">
									<label for="added_to_favorites"> <input
										checked="checked" id="added_to_favorites"
										name="added_to_favorites" type="checkbox" value="on" />Somebody
										adds my photo to Favorites
									</label>
								</div>



								<div class="checkbox control-group">
									<label for="selected_by_editor"><input
										checked="checked" id="selected_by_editor"
										name="selected_by_editor" type="checkbox" value="on" />My
										photo is selected to Editors&#x27; Choice</label>
								</div>



								<div class="checkbox control-group">
									<label for="reached_up_or_pop"><input checked="checked"
										id="reached_up_or_pop" name="reached_up_or_pop"
										type="checkbox" value="on" />My photo becomes Upcoming or
										Popular</label>
								</div>


								<div class="checkbox control-group">
									<label for="you_are_followed"> <input checked="checked"
										id="you_are_followed" name="you_are_followed" type="checkbox"
										value="on" />Somebody starts following me
									</label>
								</div>




								<div class="checkbox control-group">
									<label for="got_comments_reply"> <input
										id="got_comments_reply" name="got_comments_reply"
										type="checkbox" value="on" />Somebody comments on a photo or
										story I am subscribed to
									</label>
								</div>

								<div class="checkbox control-group">
									<label for="buys_my_photo"> <input checked="checked"
										id="buys_my_photo" name="buys_my_photo" type="checkbox"
										value="on" />Somebody buys one of my photos
									</label>
								</div>


								<div class="checkbox control-group">
									<label for="newsletters"> <input checked="checked"
										id="newsletters" name="newsletters" type="checkbox" value="on" />News
										and updates
									</label>
								</div>


								<div class="checkbox control-group">
									<label for="account_updates"><input checked="checked"
										id="account_updates" name="account_updates" type="checkbox"
										value="on" />Account changes and updates</label>
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