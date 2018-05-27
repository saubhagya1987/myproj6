<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"  ></spring:message>
<script>
	$(document).ready(function() {
		$("#reset").click(function() {
			reset();
		});
		$("#chkDeleteAll").click(function() {
			$("input[name=chkDelete]").each(function() {
				this.checked = $("#chkDeleteAll").is(':checked');
			});
		});
		$("#comboDate").datepicker();
		document.getElementById("action").value = "search";
		$("#del").click(function() {
			if (!confirm("${msg_deleteQuestion}")) {
				return false;
			}
			document.getElementById("action").value = "delete";
			$("#search_form").submit();
		});
		$("#new").click(function() {
			document.location.href = "${url}/system/team/edit";
		});

	});
	function deleteAction(id) {
		confirmMessage('${msg_deleteQuestion}', function(result) {
			if (result) {
				document.location.href = "${url}/system/team/delete?entity.id="
						+ id;
			}
		});
	}
	
</script>

<script>
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
		document.location.href = "${url}/system/team/edit";
	}
	function backTeam() {
		document.location.href = "${url}/system/team/list/";
	}
	function back() {
		document.location.href ;
	}
</script>
<div class="title_top">
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_admin" code="menu.admin"></spring:message>
			<spring:message var="menu_system_team" code="menu.system.team"></spring:message>
			<spring:message var="menu_system_team_list" code="team.page.list.title"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"><c:out value="${menu_admin }"></c:out></a>
				<span> > </span>
				<a onclick="backTeam()" class="Color_back"><c:out value="${menu_system_team }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_system_team_list }"></c:out></span>
			</h4>
		</div>
		
			<div class="span6">
				<div class="menu_images">
					<spring:message var="icon_add" code="icon.add"></spring:message>
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>
						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>
					<!-- 	<li class="delete"><a href="javascript:deleteItem()" title="Delete"></a></li> -->
						<li class="new"><a href="javascript:newItem()" title="${icon_add}"></a></li>
					</ul>
				</div>
			</div>
</div>
</div>
</div>

<div class="show-grid" fluid="true">
	<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="list_title" code="team.page.list.title"></spring:message>
					<c:out value="${list_title }"></c:out>
				</h2>

			</div>		
		</div>
	
	<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid">
			<div class="accordion" id="accordion2">
				<form:form method="POST" id="search_form" cssClass="form-horizontal"
					modelAttribute="bean" acceptCharset="UTF-8">
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
									<div class="span12"></div>
									<div class="row-fluid">
										<div class="span6">
											<form:hidden path="entity.id" />
											<ext-form:input-text path="entity.code" cssInput="span10"
												labelCode="team.field.code"></ext-form:input-text>
										</div>
										<div class="span6">
											<ext-form:input-text path="entity.name" cssInput="span10"
												labelCode="team.field.name"></ext-form:input-text>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
										<ext-form:input-text path="entity.description"
											cssInput="width_area" labelCode="team.field.description"></ext-form:input-text>
										</div>
									</div>
								</div>

							</div>
						</div>
						<div class="text-center">
							<input type="hidden" id="action" name="action" value="search" />
							<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

							<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
							<button type="button" onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})" class="btn_search_general" name="search">${msg_buttonSearch }</button>
							<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>

						</div>
					</div>
					</div>
				</form:form>
			</div>

			<div class="row-fluid" style="margin-bottom:20px;">
				<div class="span12 bg_round_table">
					<div id="search_result_table">
						<table class="table table-bordered table-hover out-tbl">
							<thead>
								<tr>
									<td colspan="6" style="padding: 0px; margin: 0px;">
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
									<!-- <th class="w50_stt"><input type="checkbox"
										name="chkDeleteAll" id="chkDeleteAll" /></th> -->
									<th class="w50_stt">#</th>
									<th><spring:message var="msg_code" code="team.field.code"></spring:message>
									     	${msg_code}</th>
									<th><spring:message var="msg_name" code="team.field.name"></spring:message>
										${msg_name}</th>
									<th><spring:message var="msg_desc"
											code="team.field.description"></spring:message>${msg_desc}</th>
									<th><spring:message var="msg_action" code="actions" /> <c:out
											value="${msg_action }"></c:out></th>
								</tr>
							</thead>
							<spring:message var="icon_view" code="icon.view"></spring:message>
							<spring:message var="icon_edit" code="icon.edit"></spring:message>
							<spring:message var="icon_clone" code="icon.clone"></spring:message>
							<spring:message var="icon_delete" code="icon.delete"></spring:message>
							<tbody>
								<c:if test="false">
									    <tr>
									     <td colspan="20" style="text-align: center"><spring:message
									       code="msg.no.data" /></td>
									    </tr>
								 </c:if>
								<c:if test="${fn:length(bean.listResult) > 0}">
								<c:forEach var="_team" items="${bean.listResult }" varStatus="i">
									<tr>
										<%-- <td class="text_center w50_stt"><input type="checkbox"
											name="chkDelete" value="${_team.id }"></td> --%>
										<td class="text_center w50_stt">${(bean.page-1)*bean.limit+i.index+1}</td>
										<td><c:out value="${_team.code }"></c:out></td>
										<td><c:out value="${_team.name }"></c:out></td>
										<td><c:out value="${_team.description }"></c:out></td>									
										<td class="table-actions"  ><%-- <a
											href="${url}/system/team/view?id=${_team.id }"><i
												class="bms-icon-view"></i></a> --%> <a 
											href="${url}/system/team/edit?id=${_team.id }"><i
												class="bms-icon-edit" title="${icon_edit}"></i></a> <a
											href="javascript:deleteAction('${_team.id}')" title="${icon_delete}"> <i
												class="bms-icon-delete"></i></a></td>
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


		</div>
	</div>
</div>


