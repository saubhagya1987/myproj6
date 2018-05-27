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
<div class="title_top">

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

<div class="container-fluid unit_bg_content">
	<%-- <div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message var="list_title" code="user.page.list.title"></spring:message>
				<c:out value="${list_title }"></c:out>
			</h2>

		</div>
	</div> --%>

	<div class="row-fluid">

		<div class="well_content row-fluid">

			<form:form method="POST" id="search_form" cssClass="form-horizontal"
				modelAttribute="bean" acceptCharset="UTF-8">
				<ext:messages bean="${userBean}"></ext:messages>
				<div class="row-fluid ">
					<div class="accordion-group">						
					</div>

				</div>
				<div class="row-fluid">
					<div class="span12 bg_round_table">
						<div id="search_result_table">
							<table class="table table-bordered table-hover out-tbl">
								<thead>
									<tr>
										<th><spring:message code="system.field.serial"></spring:message></th>
										<th><spring:message code="record.field.contractId"></spring:message></th>
										<%-- <th><spring:message code="record.field.personContacted"></spring:message></th> --%>										
										<th><spring:message code="record.field.contactMode"></spring:message></th>
										<th><spring:message code="record.field.contactPlace"></spring:message></th>
										<th><spring:message code="record.field.contactDate"></spring:message></th>										
										<th><spring:message code="record.field.contactedWith"></spring:message></th>
										<th><spring:message code="record.field.latlong"></spring:message></th>
										<%-- <th><spring:message code="record.field.attachmentType"></spring:message></th> --%>
										
									</tr>
								</thead>
								<tbody>
									<c:if test="false">
										<tr>
											<td colspan="20" style="text-align: center"><spring:message code="msg.no.data" /></td>
										</tr>
									</c:if>
									<c:if test="${fn:length(result) > 0}">
										<c:forEach var="_record" items="${result }" varStatus="i">
											<tr>
												<td class="text_center">${i.index+1}</td>
												<td><c:out value="${_record.contractId }"></c:out></td>
												<%-- <td><c:out value="${_record.personContacted }"></c:out></td> --%>
												<td><c:out value="${_record.contactMode }"></c:out></td>
												<td><c:out value="${_record.contactPlace }"></c:out></td>
												<td><c:out value="${_record.contactDate }"></c:out></td>
												<td><c:out value="${_record.personContacted }"></c:out></td>
												<td><c:out value="${_record.checkIn }"></c:out></td>
												
												
												<%-- <td>
												
													<a class="thumbnail" href="../attachment/${_record.id }">
                      <img style="height: 200px;width: 200px;margin-left:1px;" src="../attachment/${_record.id}" alt="Id">
                      </a>
												
												</td> --%>
												
												
																				
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
						<%-- <div>
							<ext:pagination bean="${bean}" maxPage="10" formId="search_form"></ext:pagination>
						</div> --%>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>


