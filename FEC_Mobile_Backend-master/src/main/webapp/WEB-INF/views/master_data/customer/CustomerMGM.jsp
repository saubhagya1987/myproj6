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

<div class="accordion" id="accordionMGM">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="row-fluid">
				<div class="span2 title1">
					<h3>MGM</h3>
				</div>
				<div class="span1 unit_accordion" style="text-align: right;">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordionMGM" href="#collapseOne_MGM"> <i
						class="bms-icon-accordion-down bms-icon-accordion"></i>
					</a>
				</div>
			</div>
		</div>
	</div>

	<div id="collapseOne_MGM" class="accordion-body collapse in border-group">
		<div class="accordion-inner">
			<div class="input-area">
				<div class="row-fluid">
					<div class="span12">
						<tiles:insertAttribute name="MGM.ViewPoint" ignore="true" />
						<div id="MGM_ListFriends">
							<tiles:insertAttribute name="MGM.ListFriends" ignore="true" />
						</div>
						<div id="MGM_PointConversationHistory">
							<tiles:insertAttribute name="MGM.PointConversationHistory" ignore="true" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
