<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="u" uri="/hare-ui-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<div>
    <ul class="media-list" id="comments">

        <c:forEach items="comments" var="c">
            <li class="media">
                <a class="pull-left" href="/to/${c.idUser}">
                    <div class="media-object"><img
                            class=" img-rounded image_64"
                            src="${fengfei.spruce.utils.PhotoPathUtils.getUserPhotoDownloadPath(c.idUser)}">
                    </div>
                </a>

                <div class="media-body">
                    <div class="media-heading border-bottom">
                        <strong><a href="/to/${c.idUser}">${c.niceName}</a></strong>
                        <small>  &nbsp;&nbsp;${c.sinceTime}</small>
                    </div>
                    <small>${c.content}</small>
                </div>
            </li>
        </c:forEach>
    </ul>

</div>
<div class="line6"></div>
<c:if test="${fn:length(pages)>1}">
    <div class="pagination pagination-centered" id="pagination">
        <input type="hidden" name="cp" id="cp" value="${cp}"/>
        <input type="hidden" name="ct" id="ct" value="${ct}"/>
        <ul>
            <li
                ${cp==1?"class=\"disabled\"":""}>
                <a href="javascript:void()" page="-1" ${cp==1?"disabled":""}>Prev</a>
            </li>
            `for(String page: pages){int p=Integer.parseInt(page);`
            <li
                ${cp==p?"class=\"active\"":""}>
                <a href="javascript:void()" page="${p-cp}" ${cp==p?"disabled":""}>${p}</a>
            </li>
            `}`
            <li
                ${cp==ct?"class=\"disabled\"":""}>
                <a href="javascript:void()" page="1" ${cp==ct?"disabled":""}>Next</a>
            </li>
        </ul>
    </div>
</c:if>
</div>
