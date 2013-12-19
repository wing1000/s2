<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="u" uri="/hare-ui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<u:body template="/layout/layout.jsp" action="#">
`suppressNull on`
`import fengfei.ucm.entity.photo.*;import fengfei.fir.utils.Path;import java.util.*;import java.util.Map.Entry;`
`extends japidviews._layouts.Layout`
`args Photo photo,Rank rank,Map exif,boolean isFollow,boolean isFavorite,boolean isVote`
`set title: photo.title+" by "+photo.niceName`
`set header: "Show" `
`set keywords: photo.tags == null ? photo.title :  photo.tags`
`set description: photo.title+(photo.tags == null ? "" : (","+photo.tags))`

<html>
<head>
<meta charset="UTF-8">
<u:define name="title"><c:out value=""/></u:define>
<u:define name="head">
    <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDqPLY_onh61yMSw5KSVICJN-B5igbHCTs&sensor=false&language=zh-cn"></script>
    <script src="@{'/public/blueimp/tmpl.min.js'}" type="text/javascript"></script>
    <script src="@{'/public/app/map.js'}"></script>
    <script src="@{'/public/app/show.js'}"></script>
    <script src="@{'/public/app/follow.js'}"></script>
    <script src="@{'/public/app/comment.js'}"></script>

</u:define>
</head>
<body>
<div class="show_title">
    <div class="grid_row ">
        <div class="col col_16">
            <div class="column">
                <img class="img-rounded image_80 img_shadow"
                     src="${fengfei.spruce.utils.PhotoPathUtils.getUserPhotoDownloadPath(photo.idUser)}"/>
            </div>
            <div class="column">
                <div class="photo_title">
                    ${(photo.title==null || "".equals(photo.title.trim()))?i18n("Untitled"):photo.title}
                </div>


                <div class="user">

                    <span><i class="icon-user"></i> ${photo.user.niceName}</span>
                    <span><i class="icon-globe"></i> ${(photo.user.city==null || "".equals(photo.user.city.trim()))?i18n("unknown.city"):photo.user.city}</span>

                </div>
                <div>
                    <button class="follow_btn btn `if(isFollow){` btn-success`}else{`btn-info`}` btn-small  "
                            type="button" id="follow" toid="${photo.idUser}" isfollow="${isFollow}">
                        ${isFollow?i18n("following "):i18n("follow")}
                    </button>
                    <button class="btn btn-info btn-small  "
                            type="button" id="sendemail" toid="${photo.idUser}">
                        Hire Me
                    </button>
                </div>
            </div>


            <div class="column_right  ">
                <div class="btn-group rank">
                    <a id="vote_btn"
                            class="btn flat-btn `if(isVote){`disabled`}` vote_btn"
                            type="button"
                            isvote="${isVote}" title="`if(isVote){`&{thank.vote}`}else{`&{like}`}`">
                        <div class="icon-thumbs-up icon-white"></div>
                        <div>`if(isVote){`&{vote.voted}`}else{`&{vote.like}`}`</div>

                    </a>
                    <a id="favorite_btn" title="`if(isFavorite){`&{favorite.cancel}`}else{`&{favorite}`}`"
                            class="btn flat-btn favorite `if(isFavorite){`favorited`}else{`unfavorited`}` ico_favorite favorite_btn
                pull-right" type="button"
                            isfavorite="${isFavorite}">&nbsp;
                    </a>
                </div>
            </div>
            <div class="column_right rank_show" id="rankShow">
                `tag RankShow rank`
            </div>
        </div>


    </div>
</div>
<div class="show_photo ">
    <div class="grid_row">
        <!---->
        <div class="col col_16  ">
            <div class="center">
                <img src="${Path.getJpegDownloadPath(photo.idPhoto,0)}" class="img_shadow" alt="">
            </div>
        </div>
    </div>
</div>


<div class="grid_row show">


    <div class="col col_12  ">

        <div class="line6"></div>
        <div class="row-fluid ">
            <div class="span7">
                <button class="btn btn-mini" type="button">
                    &nbsp;&nbsp; &{i.wanna.buy.photo}&nbsp;&nbsp;
                </button>
            </div>
            <div class="span5">
                <!-- Baidu Button BEGIN -->
                <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
                    <a class="bds_tsina"></a>
                    <a class="bds_qzone"></a>
                    <a class="bds_tqq"></a>
                    <a class="bds_douban"></a>
                    <a class="bds_tqf"></a>
                    <a class="bds_sqq"></a>
                    <a class="bds_renren"></a>
                    <a class="bds_twi"></a>
                    <a class="bds_fbook"></a>
                    <a class="bds_msn"></a>
                    <span class="bds_more">&nbsp; &{more}</span>
                    <a class="shareCount"></a>
                </div>
                <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0"></script>
                <script type="text/javascript" id="bdshell_js"></script>
                <script type="text/javascript">
                    document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date() / 3600000)
                </script>
                <!-- Baidu Button END -->
            </div>
        </div>
        <div class="line10"></div>
        <div>
            <form class="form-horizontal" id="photoCommentForm" method="post">
                `if (session.get(Admin.SESSION_LOGIN_KEY) == null) {`

                <div class="line10"></div>
                <div class="line12"></div>
                <a href="/login">Login</a> or <a href="/signup">Sign up</a> to comment.
                <div class="line10"></div>
                <div class="line10"></div>
                `}else{`
                <textarea class="col_12 comment" rows="3" cols="" id="comment" name="comment"
                          placeholder="Add your comments..."></textarea>

                <div class="line3"></div>
                <input type="hidden" name="comment_count" id="comment_count"
                       value="${photo.commentCount}"/>

                `//if(!String.valueOf(photo.idUser).equals(session.get(Admin.SESSION_USER_ID_KEY))){`
                <button type="button" id="comment_btn" class="btn btn-info  btn-mini">
                    &{comment}
                </button>
                `//}`

                `}`
                <input type="hidden" name="id_photo" id="id_photo_x" value="${photo.idPhoto}"/>
                <input type="hidden" name="niceName" id="niceNameX" value="${photo.user.niceName}"/>
                <input type="hidden" name="photoIdUser" id="photoIdUserX" value="${photo.idUser}"/>

                <input type="hidden" name="category" id="categoryx" value="${photo.category}"/>

                <input type="hidden" name="id_user" id="id_user" value="${photo.idUser}"/>
                <input type="hidden" name="id_photo" id="id_photo" value="${photo.idPhoto}"/>
            </form>
        </div>

        <div class="line10"></div>
        <div id="comments_c">
            loading comments...
        </div>

    </div>
    <!-- user info -->
    <div class="col col_4 ">

        <div class="line6"></div>
        <div class="row-fluid border-bottom shadow rounded6">
            `if(photo.description!=null && !"".equals(photo.description)){`
            <div class="span12">
                <div id="description" class="photo_stats border-bottom">
                    ${photo.description}
                </div>
            </div>

            `}`

            `if(photo.tagList!=null){`
            <div class="span12">
                <div id="tags" class="photo_stats border-bottom">
                    `for(String tag:photo.tagList){`
                    <a class="badge tag" href="javascript:void(0)"> ${tag} </a>
                    `}`
                </div>
            </div>

            `}`

            <div class="span12">
                <div id="exif" class="exif_info ">
                    <ul>
                        `Set sets = exif.entrySet();`
                        `for (Object obj : sets) {`
                        `Entry entry=(Entry)obj;`
                        `String key=(String)entry.getKey();`
                        `String keyI18n=i18n(key);`
                        <li>
                            <small class="key">${keyI18n}</small>
                            <strong class="value">${entry.getValue()}</strong>
                        </li>
                        `}`
                    </ul>
                </div>
            </div>
        </div>

        <div class="row-fluid border-bottom show_map">

            <div class="span12">
                <div id="map_show_canvas" lat="${photo.GPSLatitude}" lng="${photo.GPSLongitude}"
                     class="show_map_canvas shadow rounded6"></div>
            </div>

            <div class="span12">
                <div id="address">

                </div>
            </div>
        </div>
    </div>

</div>
<script id="comments_tml" type="text/x-tmpl">

    <li class="media"><a class="pull-left" href="#"> <img width="64" height="64"
                                                          class="media-object">
    </a>

        <div class="media-body">
            <h5 class="media-heading border-bottom">{~%=o.username%} 1 minutes ago</h5>
            <small>{~%=o.content%}</small>

        </div>
    </li>

</script>