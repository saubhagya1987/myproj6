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
<spring:message code="wait.loadding" var="loadding"></spring:message>
<script>
	$(document).ready(function() {
		
	});
	function deleteItem() {
		confirmMessage('${msg_deleteQuestion}', deleteCallBack);
	}
	function importAccount(index){
		$("#search_form").mask("${loadding}");
		$.ajax({
			url : "${url}/system/account/importsync?index="+index,
			data : $('#search_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache: false,
			success : function(data) {	
				$("#search_form").unmask();	
				document.location.href = "${url}/system/account/listsync?message=success&searchFieldOld="+$("#searchFieldId").val();	
			}
		});
	}
	function importAccountYes(index){
		$("#search_form").mask("${loadding}");
		$.ajax({
			url : "${url}/system/account/importsync?index="+index+"&auto=yes",
			data : $('#search_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache: false,
			success : function(data) {
				$("#search_form").unmask();		
				document.location.href = "${url}/system/account/edit?id="+ data+"&searchField="+$("#searchFieldId").val();	
			}
		});
	}

	function importAccountALLImportindex(){
		$("#search_form").mask("${loadding}");
		$.ajax({
			url : "${url}/system/account/importsyncAll",
			data : $('#search_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache: false,
			success : function(data) {
				$("#search_form").unmask();		
				document.location.href = "${url}/system/account/listsync?message=success";	
			}
		});
	}
	
	function backList() {
		document.location.href = "${url}/system/account/list";
	}
</script>

<script>
	
</script>
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="list_title" code="account.page.list.title"></spring:message>
					<c:out value="${list_title }"></c:out> (LDAP)
				</h2>

			</div>
			<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="return_btn_msg" code="button.return"></spring:message>
					<!-- 	<li class="back"><a href="javascript:backList()" title="Back"></a></li> -->
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>


<div class="container-fluid unit_bg_content">

	<div class="row-fluid">

		<div class="well_content row-fluid">
			<ext:messages bean="${bean}"></ext:messages>
			<form:form method="POST" id="search_form" cssClass="form-horizontal"
				modelAttribute="bean" >
				
				<div class="accordion" id="accordion2">
					<div class="accordion-group">
						<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span10 title">
							<h3>
								
								<spring:message code="search.area"></spring:message>
							</h3>

						</div>
						<div class="span2" style="text-align: right;">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion2" href="#collapseOne"><i
								class="bms-icon-accordion-down bms-icon-accordion"></i></a>
						</div>
					</div>
				</div>
						<div id="collapseOne" class="accordion-body collapse in">
							<div class="accordion-inner">
								<div class="row-fluid">
									<div class="span8"></div>
									<div class="span12">
									<div class="span2">
									</div>
									<div class="span6">
											<form:hidden path="entity.id" />
											<ext-form:input-text path="searchField" cssInput="span10" required="true" id="searchFieldId"
												labelCode="account.field.fullName"></ext-form:input-text>
									</div>	
									</div>								
								</div>
								
							</div>
							
							<div class="text-center">
									<input type="hidden" id="action" name="action" value="search" />

									<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
									<input type="submit" class="btn btn-info" name="search"
										value="${msg_buttonSearch }" />
	
							</div>
							
						</div>
					</div>
				</div>

				<div id="search_result_table">
					<table class="table table-bordered table-hover out-tbl">
						<thead>
							<tr>
								<td colspan="8" style="padding: 0px; margin: 0px;">
									<div class="title_table row-fluid">
										<div class="span6 title">
											<h3>

											</h3>
										</div>
										<div class="span6" style="text-align: right; padding-top: 5px;">
											<spring:message var="msg_import_all" code="msg.import.all"></spring:message>
											<c:if test="${bean.accounts.size() > 0}">
											<input type="button" value="${msg_import_all}"  onclick="importAccountALLImportindex();" class="btn btn-info" />
											</c:if>
										</div>
									</div>
								</td>
							</tr>
							<tr>

								<th>#</th>
								<th><spring:message 
										code="account.field.username"></spring:message> </th>
								<th><spring:message 
										code="account.field.fullName"></spring:message> </th>
								<%-- <th><spring:message 
										code="account.field.birthday"/></th> --%>
								<th><spring:message 
										code="account.field.mobile"></spring:message> </th>
								<th><spring:message
										code="account.field.email"></spring:message> </th>
								<th><spring:message var="msg_action" code="actions" /> <c:out
										value="${msg_action }"></c:out></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(bean.accounts) == 0}">
								<tr>
											<td colspan="20" style="text-align: center"><spring:message
													code="msg.no.data" /></td>
										</tr>
							</c:if>
							<c:forEach var="_account" items="${bean.accounts }"
								varStatus="i">
								<tr>

									<td class="text_center">${i.index+1}</td>
									<td><form:input path="accounts[${i.index}].username" cssClass="span12" readonly="true" /> </td>
									<td><form:input path="accounts[${i.index}].fullName" cssClass="span12" readonly="true"/></td>
								<%-- 	<td class="text_center"><fmt:formatDate type="both"
											pattern="${sessionScope.formatDate}"
											value="${_account.birthday}" /></td> --%>
									<td><form:input path="accounts[${i.index}].mobile" cssClass="span12" readonly="true"/></td>
									<td><form:input path="accounts[${i.index}].email" cssClass="span12" readonly="true"/></td>
									<td class="table-actions"><a href="javascript:void(0);" onclick="importAccount('${_account.username}');"><i class="bms-icon-add"></i></a> 
										<a href="javascript:void(0);" onclick="importAccountYes('${_account.username}');"><i	class="bms-icon-edit"></i></a> 
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</form:form>

		</div>
	</div>
</div>


