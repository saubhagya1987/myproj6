<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message code="wait.loadding" var="loadding"></spring:message>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/jcarouselconnected.css" />

<script type="text/javascript" src="${url}/static/js/jcarouselconnected.js"></script>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">×</button>
	<h3><spring:message code="banner.image.list"/></h3>
</div>
<div class="modal-body" style="position: relative; overflow: unset;padding: 0px !important;">
	
	<div class="wrapperconnected">
		<div class="connected-carousels">
			<div class="stage">
				<div data-jcarousel="true" class="carousel carousel-stage">
					<ul >
						<c:forEach items="${bannerImageLst}" var="item" varStatus="i">
							<li style="text-align: center;width: 600px"><img
								src="${url}/ajax/download?fileName=${item.imageFileName}"
								alt="" style="max-height: 350px;"></li>
						</c:forEach>

					</ul>
				</div>
				<a data-jcarouselcontrol="true" href="#" class="prev prev-stage"><span>‹</span></a>
				<a data-jcarouselcontrol="true" href="#"
					class="next next-stage inactive"><span>›</span></a>
			</div>

			<div class="navigation">
			<c:if test="${bannerImageLst.size()>5 }">
				<a data-jcarouselcontrol="true" href="#"
					class="prev prev-navigation">‹</a> <a data-jcarouselcontrol="true"
					href="#" class="next next-navigation inactive">›</a>
					</c:if>
				<div data-jcarousel="true" class="carousel carousel-navigation">
					<ul >
						<c:forEach items="${bannerImageLst}" var="item" varStatus="i">
							<li class="" data-jcarouselcontrol="true"><img
								src="${url}/ajax/download?fileName=${item.imageFileName}"
								alt="" height="38px" width="38px" style="height: 38px !important;"></li>
						</c:forEach>
					</ul>
				</div>
				
			</div>
		</div>
	</div>
	<div class="text-right margin_bottom_require">
		<spring:message var="msg_buttonClose" code="button.close"></spring:message>
		<input type="button" class="btn_search_general" style="margin-right: 470px;" data-dismiss="modal" aria-hidden="true" value="${msg_buttonClose}" onclick="closePOPUP();"/>
	</div>
</div>

<style>
button.close {
    background: #018345 none repeat scroll 0 0;
}


.close:hover, .close:focus {
    background: #018345 none repeat scroll 0 0;
}
</style>
