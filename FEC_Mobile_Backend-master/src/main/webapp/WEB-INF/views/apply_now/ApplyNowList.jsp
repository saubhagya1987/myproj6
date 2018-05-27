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
	function popupShowInfo(id) {
		ajaxUrl = '${url}/apply_now/show?id=' + id;
		$.ajax({
			url : ajaxUrl,
			success : function(data) {
				$("#popupBody").html(data);
				$("#applyNowPopup").modal('show');
			}
		});
	}
	function onRenew() {
		window.location.href = "${url}/apply_now/list";
	}
	function ExportToExcel() {
		$("#search_form").attr("action", "${url}/apply_now/exportCSV");
		$("#search_form").submit();
		$("#search_form").attr("action", "${url}/apply_now/list/");
	}
</script>

<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_apply_now_loan_request"
					code="apply.now.loan.requests"></spring:message>
				<spring:message var="menu_submit_loan_request"
					code="apply.now.submitted.loan.requests"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="onRenew()" class="Color_back"><c:out
							value="${menu_apply_now_loan_request }"></c:out></a> <span> >
					</span> <span class="Colorgray"><c:out
							value="${menu_submit_loan_request }"></c:out></span>
				</h4>
			</div>

		</div>
	</div>
</div>


<div class="container-fluid unit_bg_content">

	<div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message var="list_title"
					code="apply.now.submitted.loan.requests"></spring:message>
				<c:out value="${list_title }"></c:out>
			</h2>

		</div>

	</div>

	<div class="row-fluid">

		<div class="well_content row-fluid">
			<ext:messages bean="${bean}"></ext:messages>
			<form:form method="POST" id="search_form" cssClass="form-horizontal"
				modelAttribute="bean" acceptCharset="UTF-8">
				<div class="row-fluid">
<!-- 					<div class="accordion" id="accordion2"> -->
						<div class="accordion-group">
							<div class="accordion-heading">
								<div class="row-fluid">
									<div class="span2 title1">
										<h3>

											<spring:message code="apply.now.search.criteria"></spring:message>
										</h3>

									</div>
									<div class="span1 unit_accordion" style="text-align: right;">
										<a class="accordion-toggle" data-toggle="collapse"
											data-parent="#accordion2" href="#collapseOne"> <i
											class="bms-icon-accordion-down bms-icon-accordion"></i>
										</a>
									</div>
								</div>
							</div>
						</div>
						<div id="collapseOne"
							class="accordion-body collapse in border-group">
							<div class="accordion-inner">
								<div class="row-fluid">
									<div class="span8"></div>
									<div class="span12">
										<div class="span6">
											<div class="control-group">
												<label class="control-label"> <spring:message
														code="apply.now.status" />
												</label>
												<div class="controls">
													<form:select path="status" class="span10"
														itemValue="languageId">
														<form:option value="0">
															<spring:message code="apply.now.all"></spring:message>
														</form:option>
														<c:forEach items="${categoryApplyNow}" var="item">
															<form:option value="${item.key}">
																<spring:message code="${item.value}"></spring:message>
															</form:option>
														</c:forEach>

													</form:select>
												</div>
											</div>
										</div>
										
										<div class="span6">
												<ext-form:input-number id="aaa" path="applyNowID" cssInput="span5" labelCode="apply.now.no" ></ext-form:input-number>
										</div>
									</div>
									<div class="span12">
										<div class="span6">
											<fmt:formatDate var="dateValue" value="${bean.submitedFrom}"
												pattern="${sessionScope.formatDate}" />

											<ext-form:input-date path="submitedFrom" value="${dateValue}"
												labelCode="apply.now.submitted.from" id="fromDate"></ext-form:input-date>
										</div>
										<div class="span6">
											<fmt:formatDate var="dateValue1" value="${bean.submitedTo}"
												pattern="${sessionScope.formatDate}" />
											<ext-form:input-date path="submitedTo" value="${dateValue1}"
												labelCode="apply.now.submitted.to" id="toDate"></ext-form:input-date>
										</div>
									</div>
								</div>



							</div>

							<div class="text-center">
								<input type="hidden" id="action" name="action" value="search" />

								<spring:message var="apply_now_renew" code="apply.now.renew"></spring:message>
								<input type="button" class="btn_review" onclick="onRenew()"
									name="renew" value="${apply_now_renew }"
									style="margin-right: 6px" />
								<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
								<button type="button" onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})" class="btn_search_general" name="search">${msg_buttonSearch }</button>
								<spring:message var="apply_now_export" code="apply.now.export"></spring:message>
								<input type="button" class="btn_search_general"
									onclick="ExportToExcel()" name="export"
									value="${apply_now_export }" />

							</div>

						</div>

<!-- 					</div> -->
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

														<spring:message var="search_title"
															code="apply.now.search.result"></spring:message>
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
										<spring:message var="msg_name" code="apply.now.customer.name"></spring:message>
										<spring:message var="msg_phone" code="apply.now.cell.phone"></spring:message>
										<spring:message var="msg_submitted"
											code="apply.now.submitted.date"></spring:message>
										<spring:message var="msg_exported"
											code="apply.now.exported.date"></spring:message>

										<th><spring:message code="apply.now.no"></spring:message></th>
										<th><ext:column-sort bean="${bean }"
												fieldName="${msg_name}" path="fullName" formId="search_form" /></th>
										<th><ext:column-sort bean="${bean }"
												fieldName="${msg_phone}" path="cellPhone"
												formId="search_form" /></th>
										<th><ext:column-sort bean="${bean }"
												fieldName="${msg_submitted}" path="submittedDate"
												formId="search_form" /></th>
										<th><ext:column-sort bean="${bean }"
												fieldName="${msg_exported}" path="exportedDate"
												formId="search_form" /></th>
										<th><spring:message code="apply.now.export.status"></spring:message></th>
										<th><spring:message var="msg_action"
												code="apply.now.action" /> <c:out value="${msg_action }"></c:out></th>
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
										<c:forEach var="_applyNow" items="${result }" varStatus="i">
											<tr>
												<c:set var="size" value="${size}"></c:set>


												<%-- <td class="text_center">${i.index+1+size}</td> --%>

												<td class="text_center">${_applyNow[0]}</td>
												<td><c:out value="${_applyNow[1] }"></c:out></td>
												<td class="text_center"><c:out value="${_applyNow[2] }"></c:out></td>
												<td class="text_center"><fmt:formatDate
														pattern="${sessionScope.formatDate} HH:mm"
														value="${_applyNow[5]}" /></td>
												<td class="text_center"><fmt:formatDate
														pattern="${sessionScope.formatDate} HH:mm"
														value="${_applyNow[4]}" /></td>

												<c:if test="${_applyNow[6]==1}">
													<td>New</td>
												</c:if>
												<c:if test="${_applyNow[6]==2}">
													<td>Export</td>
												</c:if>
												<spring:message var="icon_view" code="icon.view"></spring:message>
												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_clone" code="icon.clone"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>
												<td class="table-actions"><a href="javascript:;"
													onclick="popupShowInfo('${_applyNow[0]}')"
													title="${icon_view}"><i class="bms-icon-view"></i></a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>

							</table>

						</div>
						<div class="title_table row-fluid">
							<div class="span6 title">
								<div
									class="pagination1 pagination1-small pagination1-right pagination1-left-total-css">
									<spring:message var="msg_Found" code="page.Found" />
									<spring:message var="msg_records" code="page.records" />
									<spring:message var="msg_page" code="page.page" />

									&nbsp;<span class="text">${msg_Found }</span><span
										class="number">${bean.total} </span><span class="text">${msg_records }</span><span
										class="number"> ${bean.totalPage}</span> <span class="text">${msg_page }</span>
								</div>
							</div>
							<div class="span6">
								<ext:pagination bean="${bean}" maxPage="5" formId="search_form"></ext:pagination>
							</div>
						</div>
					</div>
				</div>
			</form:form>

		</div>
	</div>
</div>
<div class="previewPopup margin_bottom_require">
	<div id="applyNowPopup" class="modal hide fade assetPopup"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-width="1000">
		<div class="modal-header">
				
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="myModalLabel">
				<spring:message var="msg_requestdetail_view" code="apply.now.submitted.loan.request.detail"></spring:message>
				${msg_requestdetail_view}
			</h3>
		</div>
		<div class="modal-body">
			<div id="popupBody" class="row-fluid"></div>
		</div>

	</div>
</div>

