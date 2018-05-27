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
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />

<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script>
	$(document).ready(
					function() {
						$("#chkDeleteAll").click(
								function() {
									$("input[name=chkDelete]").each(
											function() {
												this.checked = $(
														"#chkDeleteAll").is(
														':checked');
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
						$("#new").click(
										function() {
											document.location.href = "${url}/system/account/edit";
										});

						$("#stockCombobox").change(
										function() {
											$("#projectCombobox option").remove();
											$.ajax({
														url : '${url}/system/account/project_json_codeOrNameAndStockListId?parent='
																+ $("#stockCombobox").val(),
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
		document.location.href = "${url}/system/account/edit";
	}
	function importItem() {
		document.location.href = "${url}/system/account/listsync/";
	}
	
</script>

<script>
function blackAccount() {
	document.location.href = "${url}/system/account/list";
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
			<spring:message var="menu_system_account" code="menu.system.account"></spring:message>
			<spring:message var="menu_system_account_list" code="account.page.list.title"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"><c:out value="${menu_admin }"></c:out></a>
				<span> > </span>
				<a onclick="blackAccount()" class="Color_back"><c:out value="${menu_system_account }"></c:out></a>
				<span> > </span>
				 <span class="Colorgray"><c:out value="${menu_system_account_list }"></c:out></span>
			</h4>
		</div>
		
		<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_Import" code="button.import"></spring:message>
						<c:if test="${checkLdapSyn == 1 }">
							<li class="import"><a href="javascript:importItem()"
								title="${msg_Import}"><span class="new_text"></span></a></li>
						</c:if>
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>
						<li class="new"><a href="javascript:newItem()"
							title="${msg_buttonAdd }"><span class="new_text"></span></a></li>


					</ul>
				</div>
			</div>
		
</div>
	</div>
</div>

<div class="container-fluid unit_bg_content">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="list_title" code="account.page.list.title"></spring:message>
					<c:out value="${list_title }"></c:out>
				</h2>

			</div>			
		</div>
	
	<div class="row-fluid">

		<div class="well_content row-fluid">

			<form:form method="POST" id="search_form" cssClass="form-horizontal"
				modelAttribute="bean" acceptCharset="UTF-8">
				<ext:messages bean="${accountBean}"></ext:messages>
				<div class="row-fluid ">				
					<div class="accordion-group">
						<div class="accordion-heading">
							<div class="row-fluid">
								<div class="span2 title1">
									<h3>

										<spring:message code="search.area"></spring:message>
									</h3>

								</div>
								<div class="span1 unit_accordion" style="text-align: right;">
									<a class="accordion-toggle" data-toggle="collapse"
										data-parent="#accordion2" href="#collapseOne"><i
										class="bms-icon-accordion-down bms-icon-accordion"></i></a>
								</div>
							</div>
						</div>
						<div id="collapseOne" class="accordion-body collapse in border-group">
							<div class="accordion-inner">
								<div class="row-fluid">
									<div class="span8"></div>
									<div class="span12">
										<div class="span6">
											<form:hidden path="entity.id" />
											<ext-form:input-text path="entity.username" cssInput="span10"
												labelCode="account.field.username"></ext-form:input-text>
										</div>
										<div class="span6">
											<ext-form:input-text path="entity.email" cssInput="span10"
												labelCode="account.field.email"></ext-form:input-text>
										</div>
									</div>
									<div class="span12">
										<div class="span6">
											<ext-form:input-text path="entity.fullName" cssInput="span10"
												labelCode="account.field.fullName"></ext-form:input-text>
										</div>
										<div class="span6">
											<%-- 											<fmt:formatDate var="dateValue" value="${bean.entity.birthday}" --%>
											<%-- 												pattern="${sessionScope.formatDate}" /> --%>
											<ext-form:input-date path="entity.birthday"
												labelCode="account.field.birthday" id="comboDate"></ext-form:input-date>
										</div>
									</div>
									<div class="span12">
									<div class="span6">
									<div class="control-group">
									<label for="type" class="control-label"> <spring:message
										code="cms.status" />
									</label>
									<div class="control-group" >

									<form:select id="statustableId" cssClass="span4" style="margin-left: 10px;" path="status">
									<form:option value="-1"><spring:message code="msg.all"/> </form:option>
										<c:forEach items="${bean.listStatusTable}" var="j">
											<form:option value="${j.status_tableId}">${j.status_text}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>


						</div>
					</div>
									
									
								</div>

							</div>

							<div class="text-center">
								<input type="hidden" id="action" name="action" value="search" />

								<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
								<button type="button" onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})" class="btn_search_general" name="search">${msg_buttonSearch }</button>

							</div>

						</div>
					</div>
				
				</div>
				<div class="row-fluid">
					<div class="span12 bg_round_table">
						<div id="search_result_table">
							<table class="table table-bordered table-hover out-tbl">
								<thead>
									<tr>
										<td colspan="9" style="padding: 0px; margin: 0px;">
											<div class="title_table row-fluid">
												<div class="span6 title">
													<h3>

														<spring:message var="search_title" code="msg.search.title"></spring:message>
														<c:out value="${search_title }"></c:out>
													</h3>
												</div>
												<div class="span6">
													<ext:pagination bean="${bean}" maxPage="5"
														formId="search_form"></ext:pagination>
												</div>
											</div>
										</td>
									</tr>
									<tr>

										<th>#</th>
										<th><spring:message var="msg_username"
												code="account.field.username"></spring:message>${msg_username}</th>
										<th><spring:message code="account.field.fullName"></spring:message></th>
										<th><spring:message var="msg_birthday"
												code="account.field.birthday"></spring:message>${msg_birthday}</th>
										<th><spring:message code="account.field.email"></spring:message></th>
										<th><spring:message code="account.ldap"></spring:message></th>
										<th><spring:message var="msg_status" code="cms.status"></spring:message>
										${msg_status}</th>
										<th><spring:message var="msg_action" code="actions" /> <c:out
												value="${msg_action }"></c:out></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="false">
										<tr>
											<td colspan="20" style="text-align: center"><spring:message
													code="msg.no.data" /></td>
										</tr>
									</c:if>
									<c:if test="${fn:length(result) > 0}">
										<c:forEach var="_account" items="${result }" varStatus="i">
											<tr>

												<td class="text_center">${i.index+1}</td>
												<td><c:out value="${_account[2] }"></c:out></td>
												<td><c:out value="${_account[5] }"></c:out></td>
												<td class="text_center"><fmt:formatDate type="both"
														pattern="${sessionScope.formatDate}"
														value="${_account[4]}" /></td>
												<td><c:out value="${_account[1] }"></c:out></td>
												<td><c:out value="${_account[6] }"></c:out></td>
												<spring:message var="icon_view" code="icon.view"></spring:message>
												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_clone" code="icon.clone"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>
												
												<td class="text_center"><c:out value="${_account[7] }"></c:out></td>
												<%-- <c:if test="${_account[4]==1}">
												<spring:message var="msg_Active" code="status.Active"></spring:message>
												<td><c:out value="${msg_Active}" /></td>


												</c:if>

												<c:if test="${_cms.statusTable.status_tableId==2}">
												<spring:message var="msg_Inactive" code="status.Inactive"></spring:message>
												<td><c:out value="${msg_Inactive}" /></td>


												</c:if> --%>
								
												<td class="table-actions"><a
													href="${url}/system/account/edit?id=${_account[0] }"
													title="${icon_edit}"><i class="bms-icon-edit"></i></a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>

							</table>

						</div>
						<div>
							<ext:pagination bean="${bean}" maxPage="10" formId="search_form"></ext:pagination>
						</div>
					</div>
				</div>
			</form:form>

		</div>
	</div>
</div>


