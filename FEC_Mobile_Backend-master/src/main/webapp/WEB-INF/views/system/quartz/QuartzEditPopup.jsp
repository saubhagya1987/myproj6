<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<form:form method="POST" id="frm1" cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
		<h3 id="myModalLabel">Edit Scheduler</h3>
	</div>
	<div class="modal-body">
		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:display label="Scheduler Name" value="${bean.schedName }"></ext-form:display>
				<form:hidden path="schedName"/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:display label="Trigger Name" value="${bean.triggerName }"></ext-form:display>
				<form:hidden path="triggerName"/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:input-text path="cronExpression" label="Cron Expression"/>
			</div>
		</div>
	</div>
	<div class="modal-footer text_center" style="background: White">
		<spring:message var="msg_btn_close" code="banner.btn.close"/>
		<button type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true">${msg_btn_close }</button>
		<button type="button" class="btn_search_general" id="btnSaveCron" onclick="saveCron()">Save</button>
	</div>
</form:form>