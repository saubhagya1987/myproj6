<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<form:form method="POST" modelAttribute="bean" id="documentFilterView"
	cssClass="form-horizontal">
<table class="table table-bordered table-hover out-tbl" id="tblFilterViewDocument">
					<thead>
						<tr>
							<spring:message var="msg_filename" code="document.field.fileName"></spring:message>
							<spring:message var="msg_ReferenceType" code="document.field.referencetype"></spring:message>
							<spring:message var="msg_ReferenceNo" code="transaction.field.refrenceno"></spring:message>
							<spring:message var="msg_uploadedBy" code="document.field.uploadBy"></spring:message>
							<th><ext:column-sort bean="${bean}" fieldName="${msg_filename}"
									path="title" formId="search_form" /></th>
							<%-- <th><ext:column-sort bean="${bean}" fieldName="${msg_ReferenceType}"
									path="title" formId="search_form" /></th>
							<th><ext:column-sort bean="${bean}" fieldName="${msg_ReferenceNo}"
									path="title" formId="search_form" /></th>
							<th><ext:column-sort bean="${bean}" fieldName="${msg_uploadedBy}"
									path="title" formId="search_form" /></th> --%>
							<th class="table-actions" style="width:5%;"></th>		
						</tr>
					</thead>
					<tbody>
						<c:forEach var="_document" items="${bean.lstDocument }"
							varStatus="i">
							<tr>
								<td><i class="icon-file icon-red"></i> <a title="${_document.title }" target="blank" href="${url}/system/documentinfo/download?id=${_document.documentId }"><c:out value="${_document.title }"></c:out></a></td>
								<%-- <td>
									<c:if test="${not empty _document.documentSource}">										
										<a href = "${url}/${documentSourceLink[_document.documentSource]}?id=${_document.referenceId}" target="_blank"><spring:message code="${documentSource[_document.documentSource]}" /></a>
									</c:if>
								</td>
								<td>
									<c:if test="${not empty _document.documentSource}">
										<a href = "${url}/${documentSourceLink[_document.documentSource]}?id=${_document.referenceId}" target="_blank"><c:out value="${_document.referenceNo }"></c:out></a>
									</c:if>
									<c:if test="${empty _document.referenceNo}">
										<a href = "${url}/${documentSourceLink[_document.documentSource]}?id=${_document.referenceId}" target="_blank"><c:out value="${_document.referenceId }"></c:out></a>
									</c:if>
									
								</td> --%>
								<%-- <td><c:out value="${_document.account.fullName }"></c:out></td> --%>
								<td class="table-actions">
									<i class="bms-icon-view" onclick="openViewDetailPopup('${_document.documentId}')"></i>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</form:form>