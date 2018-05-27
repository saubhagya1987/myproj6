<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<div class="row-fluid">
	<div class="span6">
		<div class="span4"></div>
		<div class="span4" style="font-weight: bold;">
			<li>
			<spring:message code="mgm.view.point.total.friends" />
			:
			</li>
		</div>
		<div class="span4" style="color: #008345; font-weight: bold;">
			<fmt:formatNumber value="${customerPointSummary.TOTAL_FRIENDS}" pattern="${sessionScope.formatNumber}" />
			friends
		</div>
	</div>
	<div class="span6">
		<div class="span6" style="font-weight: bold;">
			<li><spring:message code="mgm.view.point.total.points" />
			:</li>
		</div>
		<div class="span4" style="color: #008345; font-weight: bold;">
			<fmt:formatNumber value="${customerPointSummary.TOTAL_POINTS}" pattern="${sessionScope.formatNumber}" />
			points
		</div>
	</div>
<!-- 	<div class="span6"> -->
<!-- 		<div class="span6" style="font-weight: bold;"> -->
<%-- 			<li><spring:message code="mgm.view.point.remaining.points.previous" /> --%>
<!-- 			: -->
<!-- 			</li> -->
<!-- 		</div> -->
<!-- 		<div class="span4" style="color: #008345; font-weight: bold;"> -->
<%-- 			<fmt:formatNumber value="${customerPointSummary.POINTS_OF_PREVIOUS_TERM}" pattern="${sessionScope.formatNumber}" /> --%>
<!-- 			points -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>

<div class="row-fluid">
	<div class="span6">
		<div class="span4"></div>
		<div class="span4" style="font-weight: bold;">
			<li><spring:message code="mgm.view.point.friends.valid" />
			:</li>
		</div>
		<div class="span4" style="color: #008345; font-weight: bold;">
			<fmt:formatNumber value="${customerPointSummary.FRIENDS_VALID}" pattern="${sessionScope.formatNumber}" />
			friends
		</div>
	</div>
	<div class="span6">
		<div class="span6" style="font-weight: bold;">
			<li><spring:message code="mgm.view.point.total.points.has.converted" />
			:</li>
		</div>
		<div class="span4" style="color: #008345; font-weight: bold;">
			<fmt:formatNumber value="${customerPointSummary.TOTAL_POINTS_HAS_CONVERTED}" pattern="${sessionScope.formatNumber}" />
			points
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="span6">
		<div class="span4"></div>
		<div class="span4" style="font-weight: bold;">
			<li><spring:message code="mgm.view.point.friends.became.loans" />
			:</li>
		</div>
		<div class="span4" style="color: #008345; font-weight: bold;">
			<fmt:formatNumber value="${customerPointSummary.FRIENDS_BECAME_LOANS}" pattern="${sessionScope.formatNumber}" />
			friends
		</div>
	</div>
	<div class="span6">
		<div class="span6" style="font-weight: bold;">
			<li><spring:message code="mgm.view.point.remaining.points" />
			:</li>
		</div>
		<div class="span4" style="color: #008345; font-weight: bold;">
			<fmt:formatNumber value="${customerPointSummary.THE_REMAINING_POINTS}" pattern="${sessionScope.formatNumber}" />
			points
		</div>
	</div>
</div>



