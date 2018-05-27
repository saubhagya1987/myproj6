<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message code="receipt.edit" var="receipt_edit"></spring:message>
<spring:message code="receipt.add" var="receipt_add"></spring:message>
<spring:message code="receipt.delete" var="receipt_delete"></spring:message>	
<spring:message code="button.view" var="receipt_view"></spring:message>	
<c:forEach var="_document" items="${bean.lstDocument }"
varStatus="i">
<tr>
	
	<td><c:out value="${_document.title }"></c:out></td>
	<td><c:out value="${_document.uploadBy }"></c:out></td>
	<td class="text_center"><fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm:ss"
			value="${_document.modifiedDate }" /></td>
	<td>
		<fmt:formatNumber type="number" maxFractionDigits="0"
													maxIntegerDigits="19" value="${_document.fileSize}" /> KBs
	</td>
	<td><c:out value="${_document.fileType}"></c:out></td>
	<td><a
		href="${url}/system/documentinfo/download?id=${_document.documentId }"><c:out
				value="${_document.repository.repositoryName}/${_document.fileName }"></c:out></a></td>
	<td class="table-actions text_center"><a title="${receipt_view }"
		href="#viewModal" onclick="openPopupView(${_document.documentId })"><i
			class="bms-icon-view" ></i></a>
		<c:if test="${param.visible }">
		<c:if test="${_document.level == 2 }">
			<a title="${receipt_edit }"
			href="#myModal" onclick="openPopup(${_document.documentId })"
										><i
				class="bms-icon-edit draft"></i></a> 
			
				<a title="${receipt_delete }"
			href="javascript:deleteDoc(${_document.documentId })"><i
				class="bms-icon-delete draft"></i></a>
		</c:if>
		</c:if>		
	</td>
</tr>
</c:forEach>
<tr style="display: none;">
	<td>
		<input name="listDocumentArrayId" type="text" id="listDocumentArrayId" value="${listDocumentArrayId}">
	</td>
</tr>