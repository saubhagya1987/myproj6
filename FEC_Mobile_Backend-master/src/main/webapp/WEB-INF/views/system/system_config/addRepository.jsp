<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<table id="tblRoot" class="table table-bordered table-hover out-tbl">
	<thead>
		<tr>
			<th></th>
			<th><spring:message
									code="repository.system.name"></spring:message></th>
			<th><spring:message
									code="repository.system.uploadfolder"></spring:message></th>
			<th><spring:message
									code="repository.system.total"></spring:message></th>
			<th><spring:message
									code="repository.system.free"></spring:message></th>
			<th><spring:message
									code="repository.system.status"></spring:message></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="lstRoot" items="${lstRoot }" varStatus="i">
			<tr>
				<td class="w50_stt text_center">${i.index +1 }</td>
				<td><c:out value="${lstRoot.repositoryName }"></c:out></td>
				<td><c:out value="${lstRoot.folderName }"></c:out></td>
				<td><c:out value="${lstRoot.totalSpace }"></c:out></td>
				<td><c:out value="${lstRoot.freeSpace }"></c:out></td>
				<td class="text_center"> <a href="#addRepository" id="activeRep_${lstRoot.repositoryId }" onclick="doActive(${lstRoot.repositoryId });">
				<c:choose>
					<c:when test="${lstRoot.active }">
						<spring:message
									code="team.field.enabled.active"></spring:message>
					</c:when>
					<c:otherwise>
					<spring:message
									code="team.field.enabled.deactive"></spring:message>
					</c:otherwise>
				</c:choose>
				</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td class="w50_stt text_center"></td>
			<td><input type="text"  id="repositoryName" 
				style="height: 15px;"></td>
			<td><input type="text" id="folderName" 
				style="height: 15px;"></td>
			<td class="text_center"><c:out value="-"></c:out></td>
			<td class="text_center"><c:out value="-"></c:out></td>
			<td class="text_center"><c:out value="-"></c:out></td>
		</tr>
		<tr>
			<td colspan="6" class="w50_stt text_center">
				<button type="button" id="save" class="btn btn-info "
					onclick="doAjaxPost()">Add</button>
			</td>
		</tr>

	</tfoot>
</table>
