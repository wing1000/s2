<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<ul class="nav nav-list">
	<li><h4>Profile</h4></li>
	<li ${Profile}><a href="${rctx.contextPath}/settings/profile">Profile</a></li>
	<li ${Camera}><a href="${rctx.contextPath}/settings/camera">Camera</a></li>
	<li ${Password}><a href="${rctx.contextPath}/settings/password">Password</a></li>
	<li ${Notifications}><a href="${rctx.contextPath}/settings/notification">Notifications</a></li>
	<li ${SocialMedia}><a href="${rctx.contextPath}/settings/notification">Social
			Media</a></li>
	<li ${Account}><a href="${rctx.contextPath}/settings/account">Account</a></li>
	<li><h4>Album</h4></li>
	<li ${Settings}><a href="#">Settings</a></li>
	<li ${Themes}><a href="#">Themes</a></li>
	<li><h4>Friends</h4></li>
	<li ${Following}><a href="#">Following</a></li>
	<li ${Followed}><a href="#">Followed </a></li>
</ul>