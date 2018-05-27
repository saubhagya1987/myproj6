<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/multiple-select.css" />
<script type="text/javascript"
	src="${url}/static/plugins/jquery.ajaxupload.js"></script>
<script type="text/javascript"
	src="${url}/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<script src="${url}/static/js/bootstrap-tagsinput.js"></script>
<link href="${url}/static/css/bootstrap-tagsinput.css" rel="stylesheet">

<script src="${url}/static/js/typeahead.jquery.min.js"></script>
<script src="${url}/static/js/bloodhound.min.js"></script>
<script src="${url}/static/js/bootstrap3-typeahead.js"></script>

<style>
<!--
.title_h2 {
	margin-left: 182px !important;
	
}
-->
</style>



<%-- <div><spring:message code="cms.title"></spring:message></div>	 --%>

<div class="container-fluid unit_bg_content">

	<form:form method="POST" modelAttribute="bean" action="${url}/contact/edit"
		cssClass="form-horizontal">
		<div class="title_h2">
			<h2>
				<spring:message var="edit_title" code="cms.title" />
				<c:out value="${edit_title}"></c:out>
			</h2>
		</div>
		<div class="input-area">
			<ext:messages bean="${bean}"></ext:messages>

			<!-- ROW 3 -->
			<div class="row-fluid">

				<div class="controls">
					<textarea cols="80" id="editor1" name="contact.content" rows="10">${bean.contact.content}</textarea>
					<ckfinder:setupCKEditor basePath="${url }/static/ckfinder/"
						editor="editor1" />
					<ckeditor:replace replace="editor1"
						basePath="${url}/static/ckeditor_4.4.3_full/" />
					<span class="help-inline"> <form:errors
							path="entity.content" cssClass="error"></form:errors>
					</span>
				</div>
			</div>
			<form:hidden path="contact.contactID" />
			<div class="row-fluid text-center margin_bottom_require">
				<spring:message var="save_btn_msg" code="button.save" />
				 <sec:authorize access="!hasAnyRole('R010')">
				<button type="submit" class="btn btn-info" id="general_save">${save_btn_msg}</button>
				</sec:authorize>
			</div>

		</div>

	</form:form>
</div>


<style>
.email_margin {
	margin-left: 0 !important;
}
</style>
