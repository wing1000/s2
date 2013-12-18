<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<ul class="nav nav-list">
	<li><h4>Profile</h4></li>
	<li ${Profile}><a href="/settings/profile">Profile</a></li>
	<li ${Camera}><a href="/settings/camera">Camera</a></li>
	<li ${Password}><a href="/settings/password">Password</a></li>
	<li ${Notifications}><a href="/settings/notification">Notifications</a></li>
	<li ${SocialMedia}><a href="/settings/notification">Social
			Media</a></li>
	<li ${Account}><a href="/settings/account">Account</a></li>
	<li><h4>Album</h4></li>
	<li ${Settings}><a href="#">Settings</a></li>
	<li ${Themes}><a href="#">Themes</a></li>
	<li><h4>Friends</h4></li>
	<li ${Following}><a href="#">Following</a></li>
	<li ${Followed}><a href="#">Followed </a></li>
</ul>