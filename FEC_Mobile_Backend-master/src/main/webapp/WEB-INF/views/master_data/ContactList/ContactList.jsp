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

<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_cms" code="menu.cms"></spring:message>
			<spring:message var="menu_contact_list" code="contact.list"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backList()" class="Color_back"><c:out value="${menu_cms }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_contact_list}"></c:out></span>
			</h4>
		</div>
</div>
</div>
</div>
<div class="container-fluid unit_bg_content">
	<form:form method="POST" modelAttribute="bean" action="${url}/master_data/contact/show"
		cssClass="form-horizontal">
		<div class="row-fluid">
		<div class="title_h2">
			<h2 style="color: green">
				<spring:message var="edit_title" code="contact.list" />
				<c:out value="${edit_title}"></c:out>
			</h2>
		</div>
		</div>
		<div class="input-area">
			<ext:messages bean="${bean}"></ext:messages>
			<!-- ROW 3 -->
			<div class="row-fluid">			
					<textarea cols="80" id="editor1" name="entity.content" rows="10">${bean.entity.content}</textarea>
					<ckfinder:setupCKEditor basePath="${url }/static/ckfinder/"
						editor="editor1" />
					<ckeditor:replace replace="editor1"
						basePath="${url}/static/ckeditor_4.4.3_full/" />
					<span class="help-inline"> <form:errors
							path="entity.content" cssClass="error"></form:errors>
					</span>				
			</div>
			<form:hidden path="entity.contactID" />
			
	
				<div class="text-center" style="margin-top: 10px">
					<input type="hidden" name="action" value="edit" />
					<spring:message var="save_btn_msg" code="button.save"></spring:message>
					<sec:authorize access="!hasAnyRole('R010')">
				<input type="submit" value="${save_btn_msg}" id="saveEdit"
						class="btn_search_general" />
				</sec:authorize>
					
				</div>		
		</div>
	</form:form>
</div>
<script type="text/javascript">

	function backList() {
		document.location.href = "${url}/master_data/contact/show";
	}


</script>
