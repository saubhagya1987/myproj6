<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<form:form method="POST" modelAttribute="bean" id="documentDetails"
	cssClass="form-horizontal">


	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">×</button>
		<h3>
		<spring:message code="document.view"/> </h3>
	</div>
	<div class="modal-body align-left" style="height:360px;">
			<div class="row-fluid">
				<div class="span3">
				<ext-form:display cssClass="text-info" labelCode="document.detail.documentinfo"></ext-form:display>
				</div>
				<div class="span9 line_bg" >
				 &nbsp;
				 </div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<ext-form:display cssClass="muted" icon="icon-folder-open" labelCode="document.detail.folder" value="${bean.entity.repository.folderName}" ></ext-form:display>
				</div>
			</div>
			<div class="row-fluid">
				<ext-form:display icon="icon-file" labelCode="document.field.fileName" value="${bean.entity.fileName}" ></ext-form:display>
			</div>
			<div class="row-fluid">
				<ext-form:display icon="icon-book" labelCode="document.field.title" value="${bean.entity.title}" ></ext-form:display>
			</div>
			<div class="row-fluid">
				<ext-form:display icon="icon-tags" labelCode="document.field.keywords" value="${bean.entity.keywords}" ></ext-form:display>
			</div>
			<div class="row-fluid">
				<ext-form:input-textarea cssInput="span11" icon="icon-comment" disable="true" path="entity.bComment" labelCode="document.field.comment">${bean.entity.bComment}</ext-form:input-textarea>
			</div>
			<div class="row-fluid">
				<div class="span6">
					<fmt:formatDate value="${bean.entity.uploadDate}" pattern="dd/MM/yyyy hh:mm" var="uploadDate"/>
					<ext-form:display cssClass="muted" icon="icon-calendar" labelCode="document.field.uploadedDate"  value="${uploadDate}" ></ext-form:display>
				</div>
				<div class="span6">
					<fmt:formatDate value="${bean.entity.modifiedDate}" pattern="dd/MM/yyyy hh:mm" var="modifiedDate"/>
					<ext-form:display cssClass="muted" icon="icon-calendar" labelCode="document.field.modifieddate" value="${modifiedDate}" ></ext-form:display>
				</div>
			</div>
			<%-- <div class="row-fluid">
				<ext-form:display cssClass="muted" icon="icon-user" labelCode="document.field.uploadBy" value="${bean.entity.account.fullName}" ></ext-form:display>
			</div> --%>
			<div class="row-fluid">
				<ext-form:display cssClass="text-info" labelCode="document.detail.referenceinfo"></ext-form:display>
			</div>
			<%-- <div class="row-fluid">
				<c:if test="${not empty bean.entity.documentSource}">
					<spring:message var="documentSourceName" code="${documentSource[bean.entity.documentSource]}" />
					<ext-form:display labelCode="document.field.referencetype" value="${documentSourceName}" ></ext-form:display>
				</c:if>
				
			</div> --%>
			<div class="row-fluid">
				<c:choose>
					<c:when test="${not empty bean.entity.referenceNo}">
						<ext-form:display labelCode="document.field.referenceno" value="${bean.entity.referenceNo}" ></ext-form:display>
					</c:when>
					<c:otherwise>
						<ext-form:display labelCode="document.field.referenceno" value="${bean.entity.referenceId}" ></ext-form:display>
					</c:otherwise>
				</c:choose>
				
			</div>
			<div class="row-fluid">
				<c:if test="${not empty bean.entity.documentSource}">
					<ext-form:display labelCode="document.field.referencedesc" value="${bean.entity.referenceDescription}" ></ext-form:display>
				</c:if>
			</div>
			
	</div>
</form:form>
<div class="modal-footer">

	<spring:message var="msg_buttonClose" code="button.close"></spring:message>
	<button class="btn btn-info" data-dismiss="modal" aria-hidden="true">${msg_buttonClose}</button>
</div>
