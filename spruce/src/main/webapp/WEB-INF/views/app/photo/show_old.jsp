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
<u:define name="head">
	<script src="@{'/public/app/follow.js'}"></script>
</u:define>
</head>
<body>


	<u:define name="header">Show</u:define>

	<u:define name="body">


		<div class="row-fluid">

			<div class="span9 ">
				<div>
					<img src="${photo.path}" class="img-rounded0" alt="">
				</div>
				<div class="line6"></div>
				<div class="row-fluid ">
					<div class="span7">
						<button class="btn btn-mini" type="button">&nbsp;&nbsp; I
							want to buy this photo! &nbsp;&nbsp;</button>
					</div>
					<div class="span5">
						<!-- JiaThis Button BEGIN -->
						<div class="jiathis_style">
							<a class="jiathis_button_qzone"></a> <a
								class="jiathis_button_tsina"></a> <a class="jiathis_button_tqq"></a>
							<a class="jiathis_button_weixin"></a> <a
								class="jiathis_button_renren"></a> <a
								class="jiathis_button_xiaoyou"></a> <a
								class="jiathis_button_tianya"></a> <a
								class="jiathis_button_ishare"></a> <a
								href="http://www.jiathis.com/share"
								class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
							<a class="jiathis_counter_style"></a>
						</div>
						<script type="text/javascript"
							src="http://v3.jiathis.com/code/jia.js?uid=1363333029194573"
							charset="utf-8"></script>
						<!-- JiaThis Button END -->
					</div>
				</div>
				<div class="line10"></div>
				<div>
					<form class="form-horizontal">

						<textarea class="span12" rows="3" cols="" id="comment"
							name="comment" placeholder="Add your comments..."></textarea>
						<div class="line3"></div>

						<button type="button" class="btn btn-info  btn-mini">Comment</button>
					</form>
				</div>
				<div class="line10"></div>
				<div>
					<ul class="media-list">
						<li class="media"><a class="pull-left" href="#"> <img
								width="64" height="64" class="media-object"
								src="http://acdn.500px.net/1705009/4bd01a2c6181e48be20efd32105239b316e22d54/1.jpg?3">
						</a>
							<div class="media-body">
								<h5 class="media-heading border-bottom">Media heading</h5>
								<small>Cras sit amet nibh libero, in gravida nulla.
									Nulla vel metus scelerisque ante sollicitudin commodo. Cras
									purus odio, vestibulum in vulputate at, tempus viverra turpis.
									Fusce condimentum nunc ac nisi vulputate fringilla. Donec
									lacinia congue felis in faucibus.</small>

							</div></li>
						<li class="media"><a class="pull-left" href="#"> <img
								width="64" height="64" class="media-object"
								src="http://acdn.500px.net/1705009/4bd01a2c6181e48be20efd32105239b316e22d54/1.jpg?3">
						</a>
							<div class="media-body">
								<h5 class="media-heading border-bottom">Media heading</h5>
								<small>Cras sit amet nibh libero, in gravida nulla.
									Nulla vel metus scelerisque ante sollicitudin commodo. Cras
									purus odio, vestibulum in vulputate at, tempus viverra turpis.
									Fusce condimentum nunc ac nisi vulputate fringilla. Donec
									lacinia congue felis in faucibus.</small>

							</div></li>
					</ul>

				</div>
				<div class="line6"></div>
				<div class="pagination pagination-centered">
					<ul>
						<li><a href="#">Prev</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">Next</a></li>
					</ul>
				</div>
			</div>
			<!-- user info -->
			<div class="span3 ">
				<div class="row-fluid stat ">
					<div class="span6">
						<img width="90" height="90"
							src="http://acdn.500px.net/1705009/4bd01a2c6181e48be20efd32105239b316e22d54/1.jpg?3">
					</div>
					<div class="span6">
						<p>David</p>
						<p>
							<button
								class="btn `if(isFollow){` btn-success`}else{`btn-info`}` btn-mini  "
								type="button" id="follow" toid="${photo.idUser}" isfollow="true">${isFollow?"Following":"Follow"}</button>
						</p>
					</div>
				</div>
				<div class="line6"></div>
				<div class="row-fluid stat border-bottom">
					<div class="span5">
						<div class="photo-stats">${rank.score}</div>
					</div>
					<div class="span7 ">
						<div class=" photo-stats border-left">
							<ul class="stats">

								<li class="views" title="Views"><strong>${rank.view}</strong><small
									class="faded">Views</small></li>

								<li class="votes" title="Votes" id="photo_rating_total_votes">
									<strong class="votes_count">${rank.vote}</strong><small
									class="faded">Votes</small>
								</li>

								<li class="favs" title="Favs" id="photo_rating_favs"><strong
									class="favorites_count">${rank.favorite}</strong><small
									class="faded">Favorites</small></li>

							</ul>
						</div>
					</div>

				</div>
				<div class="row-fluid stat ">
					<div class="span5">
						<div class="photo-stats">99.9</div>
					</div>
					<div class="span7 ">
						<div class="photo-stats  border-left">
							<ul class="stats">
								<li><small class="faded">Highest Pulse</small></li>
								<li><small class="faded">2013-05-24</small></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="line6"></div>

				<div class="row-fluid  ">
					<button class="btn btn-success span9  btn-mini " type="button">Like</button>

					<button class="btn span3 btn-mini" type="button">
						<i class="icon-heart "></i>
					</button>


				</div>
				<div class="line6"></div>
				<div class="row-fluid border-bottom  ">
					<div class="span12">
						<div id="desc" class="photo-stats border-bottom">${photo.description}</div>
					</div>
					<div class="span12">
						<div id="tags" class="photo-stats border-bottom">
							<span>${photo.tags}<i class=" icon-tags"></i>tags1
							</span> <span><i class=" icon-tags"></i>tags2</span> <span><i
								class=" icon-tags"></i>tags3</span>
						</div>
					</div>
					<div class="span12">
						<div id="exif" class=" photo-stats ">
							<ul class="stats exif">
								`Set
								<Entry> sets = exif.entrySet();` `for (Object obj :
								sets) {` `Entry entry=(Entry)obj;`
								<li><small>${entry.getKey()}</small><strong class="faded">${entry.getValue()}</strong></li>
								`}` 
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</u:define>



</body>
	</html>
</u:body>