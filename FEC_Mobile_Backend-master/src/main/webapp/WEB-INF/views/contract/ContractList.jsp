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

	$(document).ready(function() {
		$('#action').val("search");
	});

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
		window.location.href = "${url}/contract/list";
	}
</script>

<script>
	
</script>
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_mobile" code="contract.mobile"></spring:message>
				<spring:message var="menu_contract_list" code="contract.list"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="onRenew()" class="Color_back"><c:out value="${menu_mobile }"></c:out></a>
					<span> > </span>
					<span class="Colorgray"><c:out value="${menu_contract_list }"></c:out></span>
				</h4>
			</div>
		</div>
	</div>
</div>


<div class="container-fluid unit_bg_content">

	<div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message var="list_title" code="contract.list"></spring:message>
				<c:out value="${list_title }"></c:out>
			</h2>

		</div>
	</div>

	<div class="well_content row-fluid">
		<ext:messages bean="${bean}"></ext:messages>
		<form:form method="POST" id="search_form" cssClass="form-horizontal"
			modelAttribute="bean" acceptCharset="UTF-8">
			<div class="row-fluid ">			
				<div class="accordion-group">
					<div class="accordion-heading">
						<div class="row-fluid">
							<div class="span2 title1">
								<h3>

									<spring:message code="contract.search.criteria"></spring:message>
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
										<ext-form:input-text path="contractnumber"
											cssInput="span10" labelCode="contract.no"></ext-form:input-text>
									</div>
<!-- 									<div class="span6"> -->
<%-- 										<ext-form:input-text path="fullname" --%>
<%-- 											cssInput="span10" labelCode="contract.customer.full.name"></ext-form:input-text> --%>
<!-- 									</div> -->
									<div class="span6">
										<ext-form:input-text path="cellphone"
											cssInput="span10" labelCode="contract.customer.cell.phone"></ext-form:input-text>
									</div>
								</div>
								<div class="span12">
									<div class="span6">
										<ext-form:input-text path="customeridcard"
											cssInput="span10" labelCode="contract.customer.id.card.no"></ext-form:input-text>
									</div>
								</div>
<!-- 									<div class="span12"> -->
<!-- 										<div class="span6"> -->
<%-- 											<fmt:formatDate var="fromDate" value="${bean.fromDate}" --%>
<%-- 												pattern="${sessionScope.formatDate}" />  --%>

<%-- 											<ext-form:input-date path="fromDate" value="${fromDate}" --%>
<%-- 								 				labelCode="contract.fromdate" id="fromDate"></ext-form:input-date> --%>
<!-- 										</div> -->
										
<!-- 										<div class="span6"> -->
<%-- 												<fmt:formatDate var="toDate" value="${bean.toDate}" --%>
<%-- 								 						pattern="${sessionScope.formatDate}" />  --%>
								 						
<%-- 												<ext-form:input-date path="toDate" value="${toDate}"  --%>
<%-- 								 					labelCode="contract.todate" id="toDate"></ext-form:input-date>  --%>
<!-- 										</div> -->
<!-- 									</div> -->
							</div>



						</div>

						<div class="text-center">
							<input type="hidden" id="action" name="action" value="search" />

							<spring:message var="contract_renew" code="contract.renew"></spring:message>
							<input type="button" class="btn_review" onclick="onRenew()" style="margin: 6px"
								name="renew" value="${contract_renew }" />
							<spring:message var="contract_search" code="contract.search"></spring:message>
							<input type="submit" class="btn_search_general" name="search"
								value="${contract_search}" />
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
									<td colspan="10" style="padding: 0px; margin: 0px;">
										<div class="title_table row-fluid">
											<div class="span6 title">
												<h3>

													<spring:message var="search_title"
														code="contract.search.result"></spring:message>
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
									<spring:message var="msg_app_id" code="contract.app.id"></spring:message>
									<spring:message var="msg_contract_no" code="contract.no"></spring:message>
									<spring:message var="msg_customer_name"
										code="contract.customer.name"></spring:message>
									<spring:message var="msg_customer_id_card"
										code="contract.customer.id.card.no"></spring:message>
									<spring:message var="msg_customer_cell_phone"
										code="contract.customer.cell.phone"></spring:message>

									<th><spring:message code="contract.number"></spring:message></th>
									<th><ext:column-sort bean="${bean}"
											fieldName="${msg_app_id}" path="appID" formId="search_form" /></th>
									<th><ext:column-sort bean="${bean }"
											fieldName="${msg_contract_no}" path="contractNumber"
											formId="search_form" /></th>
									<th><ext:column-sort bean="${bean }"
											fieldName="${msg_customer_name}" path="customerName"
											formId="search_form" /></th>
									<th><ext:column-sort bean="${bean }"
											fieldName="${msg_customer_id_card}"
											path="customerID_CardNumber" formId="search_form" /></th>
									<th><ext:column-sort bean="${bean }"
											fieldName="${msg_customer_cell_phone}"
											path="customer.cellPhone" formId="search_form" /></th>
									<th><spring:message code="contract.status"></spring:message></th>
									<th><spring:message var="msg_action" code="contract.action" /> <c:out
											value="${msg_action }"></c:out></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="contract" items="${bean.listResult}"
									varStatus="i">
									<tr>
										<td class="text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
										<td><c:out value="${contract.appID}"></c:out></td>
										<td style="color: #009933"><c:out value="${contract.contractNum}"></c:out></td>
										<td style="color: #009933"><c:out value="${contract.customerName}"></c:out></td>
										<td><c:out value="${contract.customerIDCardNum}"></c:out></td>
										<td><c:out value="${contract.customerCellPhone}"></c:out></td>
										<td>
										 	<c:if test="${contract.contractStatus == 'A'}">
												<spring:message code="customer.field.status.active" />
											</c:if>
											<c:if test="${contract.contractStatus == 'X'}">
												<spring:message code="customer.field.status.inactive" />
											</c:if> 
												<c:if test="${contract.contractStatus == 'C'}">
												<spring:message code="status.close" />
											</c:if> 
										</td>
										<spring:message var="icon_view" code="icon.view"></spring:message>
										<spring:message var="icon_clone" code="icon.clone"></spring:message>
										<spring:message var="icon_delete" code="icon.delete"></spring:message>
										<td class="table-actions"><a
											href="${url}/contract/view?id=${contract.customerID}&contractNum=${contract.contractNum}"
											title="${icon_view}"><i class="bms-icon-view"></i></a></td>
									</tr>
								</c:forEach>
								<%-- <tr>
									<td colspan="12" style="padding: 0px; margin: 0px;">
										<div class="title_table row-fluid">
											<div class="span6 title">
												<div
													class="pagination1 pagination1-small pagination1-right pagination1-left-total-css">
													<spring:message var="msg_Found" code="page.Found" />
													<spring:message var="msg_records" code="page.records" />
													<spring:message var="msg_page" code="page.page" />

													&nbsp;<span class="text">${msg_Found }</span><span
														class="number">${bean.total} </span><span class="text">${msg_records }</span><span
														class="number"> ${bean.totalPage}</span> <span
														class="text">${msg_page }</span>
												</div>
											</div>
											<div class="span6">
												<ext:pagination bean="${bean}" maxPage="5"
													formId="search_form"></ext:pagination>
											</div>
										</div>
									</td>
								</tr> --%>
							</tbody>

						</table>

					</div>
				</div>
			</div>
		</form:form>

	</div>
</div>

