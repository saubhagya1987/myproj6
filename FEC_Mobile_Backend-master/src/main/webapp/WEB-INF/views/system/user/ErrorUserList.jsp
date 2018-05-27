<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />

<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script>
	$(document).ready(function() {
		$("#chkDeleteAll").click(function() {
			$("input[name=chkDelete]").each(function() {
				this.checked = $("#chkDeleteAll").is(':checked');
				});
			});
			document.getElementById("action").value = "search";
			$("#del").click(function() {
				if (!confirm("${msg_deleteQuestion}")) {
					return false;
				}
				document.getElementById("action").value = "delete";
				$("#search_form").submit();
			});
			$("#new").click(function() {
				document.location.href = "${url}/system/user/edit";
			});

			$("#stockCombobox").change(function() {
				$("#projectCombobox option").remove();
				$.ajax({url : '${url}/system/user/project_json_codeOrNameAndStockListId?parent='+ $("#stockCombobox").val(),
					dataType : 'json',
					type : 'GET',
					cache : false,
					success : function(data) {
						$.each(data,function(i,object) {
							if (object.projectId == null) {
								$("#projectCombobox").append("<option value=''>"+ "</option>");
							} else {
								$("#projectCombobox").append("<option value='"+object.projectId+"'>"+ object.name+ "</option>");
							}
						});
					}
				});
			});

	});
	function deleteCallBack(result) {
		if (result) {
			$('#action').val("delete");
			$("#search_form").submit();
		}
	}
	function deleteItem() {
		confirmMessage('${msg_deleteQuestion}', deleteCallBack);
	}
	function newItem() {
		document.location.href = "${url}/system/user/edit";
	}
	function importItem() {
		document.location.href = "${url}/system/user/listsync/";
	}
</script>

<script>
	function blackUser() {
		document.location.href = "${url}/system/user/list";
	}
	function black() {
		document.location.href;
	}
</script>

<style>
.success_msg{
width:100%;
display:table;
margin:151px 0px 26px 10px;
padding:0px;

}

.errorTablelist td{
font-size:11px;
}
</style>

<div class="title_top ">
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span6">
			<spring:message var="menu_admin" code="menu.admin"></spring:message>
			<spring:message var="menu_system_user" code="menu.system.user"></spring:message>
			<spring:message var="menu_system_user_list" code="user.page.list.title"></spring:message>
		</div>
	</div>
</div>
</div>

<c:if test="${fn:length(accounts) > 0}">
<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="well_content row-fluid">
			<form:form method="POST" id="search_form" cssClass="form-horizontal" modelAttribute="bean" acceptCharset="UTF-8">
				<ext:messages bean="${userBean}"></ext:messages>				
				<div class="row-fluid">
					<div class="span12 bg_round_table">
						<div id="search_result_table">
							<table class="table table-bordered table-hover out-tbl errorTablelist ">
								<thead>									
									<tr>
										<th><spring:message code="system.field.serial"></spring:message></th>
										<th><spring:message code="user.field.userCode"></spring:message></th>
										<th><spring:message code="user.field.fullName"></spring:message></th>										
										<th><spring:message code="user.field.mobile"></spring:message></th>
										<th><spring:message code="user.field.birthday"></spring:message></th>
										<th><spring:message code="user.field.email"></spring:message></th>										
										<th><spring:message code="user.field.regionCode"></spring:message></th>
										<th><spring:message code="user.field.provinceCode"></spring:message></th>
										<th><spring:message code="user.field.joiningDate"></spring:message></th>
										<th><spring:message code="user.field.lineManager"></spring:message></th>
										
										<th><spring:message code="user.field.status"></spring:message></th>
										<th><spring:message code="user.field.gender"></spring:message></th>
										<th><spring:message code="user.field.onBoardPosition"></spring:message></th>
										<th><spring:message code="user.field.position"></spring:message></th>
										<th><spring:message code="user.field.typeOfSalesAgent"></spring:message></th>
										<th><spring:message code="user.field.firstName"></spring:message></th>
										<th><spring:message code="user.field.lastName"></spring:message></th>
										<th><spring:message code="user.field.officeNumber"></spring:message></th>
																				
										<th><spring:message code="user.field.errorMessage"></spring:message></th>								
									</tr>
								</thead>
								<tbody>
									<c:if test="false">
										<tr>
											<td colspan="20" style="text-align: center"><spring:message code="msg.no.data" /></td>
										</tr>
									</c:if>									
										<c:forEach var="_account" items="${accounts}" varStatus="i">
											<tr>
												<td class="text_center">${i.index+1}</td>
												<td><c:out value="${_account.userCode }"></c:out></td>
												<td><c:out value="${_account.fullName }"></c:out></td>
												<td><c:out value="${_account.mobile }"></c:out></td>
												<td><fmt:formatDate pattern="dd/MM/yyyy" value="${_account.birthday }" /><%-- <c:out value="${_account.birthday }"></c:out> --%></td>
												<td><c:out value="${_account.email }"></c:out></td>
												<td><c:out value="${_account.regionCode }"></c:out></td>												
												<td><c:out value="${_account.provinceCode }"></c:out></td>
												<td><fmt:formatDate pattern="dd/MM/yyyy" value="${_account.joiningDate }" /><%-- <c:out value="${_account.joiningDate }"></c:out> --%></td>
												<td><c:out value="${_account.lineManager }"></c:out></td>
												
												<td><c:out value="${_account.status }"></c:out></td>
												<td><c:out value="${_account.gender }"></c:out></td>
												<td><c:out value="${_account.onBoardPosition }"></c:out></td>
												<td><c:out value="${_account.position }"></c:out></td>
												<td><c:out value="${_account.typeOfSalesAgent }"></c:out></td>
												<td><c:out value="${_account.firstName }"></c:out></td>
												<td><c:out value="${_account.lastName }"></c:out></td>
												<td><c:out value="${_account.officeNumber }"></c:out></td>
												
												<td><c:out value="${_account.errorMessage }"></c:out></td>
											</tr>
										</c:forEach>									
								</tbody>
							</table>
						</div>						
					</div>
				</div>
			</form:form>
		</div>
	</div>
	Above users could not uploaded due to defined error.
</div>

</c:if>
<c:if test="${fn:length(accounts) == 0}">
<div class="success_msg ">${successMessage}</div>
</c:if>
