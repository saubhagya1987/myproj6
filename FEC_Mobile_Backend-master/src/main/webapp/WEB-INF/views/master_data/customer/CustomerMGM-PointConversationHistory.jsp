<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<div class="accordion" id="accordion2">
	<div class="accordion-group">
		<div class="accordion-heading">

			<div class="row-fluid">
				<div class="span4 title2">
					<h3>
						<spring:message code="customer.mgm.pointConversationHistory.title"></spring:message>
					</h3>
				</div>
				<div class="span1 unit_accordion unit_bg2" style="text-align: right;">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne_MGM_FE"> <i
						class="bms-icon-accordion-down2 bms-icon-accordion"></i>
					</a>
				</div>
			</div>


		</div>
	</div>
	<form:form method="POST" id="search_history" cssClass="form-horizontal margin_bottom_form" modelAttribute="mgmPCHBean">
		<div class="row-fluid row-align" id="collapseOne_MGM_FE">
			<div class="span12">
				<table class="table table-bordered table-hover out-tbl">
					<thead>
						<tr>
							<td colspan="12" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title"></div>
									<div class="span6">
										<ext:pagination-ajax divId="MGM_PointConversationHistory" bean="${mgmPCHBean}" formId="search_history" maxPage="5"
											ajaxUrl="${url}/master_data/customer/viewMGMPointConversationHistory?id=${id }">
										</ext:pagination-ajax>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th></th>
							<th><spring:message code="mgm.fePoint.field.datetime" /></th>
							<th><spring:message code="mgm.fePoint.field.thePointHasChanged" /></th>
							<th><spring:message code="mgm.fePoint.field.theRemainingPoints" /></th>
							<th><spring:message code="mgm.fePoint.field.type" /></th>
							<th><spring:message code="mgm.fePoint.field.moneyWasConvert" /></th>

							<th><spring:message code="mgm.fePoint.field.dateConversionPoints" /></th>
							<th><spring:message var="msg_card_no" code="mgm.fePoint.field.contractNo" /> <ext:column-sort bean="${mgmPCHBean }" path="idCardNumber"
									formId="" fieldName="${msg_card_no }"></ext:column-sort></th>
							<th><spring:message var="msg_address" code="mgm.fePoint.field.dateOfNextPayment" /> <ext:column-sort bean="${mgmPCHBean }"
									path="addressNo" formId="" fieldName="${msg_address }"></ext:column-sort></th>
							<th><spring:message var="msg_loan" code="mgm.fePoint.field.accountMomo" /> <ext:column-sort bean="${mgmPCHBean }" path="oldUserId"
									formId="" fieldName="${msg_loan }"></ext:column-sort></th>
							<th><spring:message code="mgm.fePoint.field.status" /></th>
							<th class="table-actions"><spring:message code="mgm.fePoint.field.note" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="_item" items="${mgmPCHBean.listResult }" varStatus="i">
							<tr>
								<td class="w50_stt text_center">${(mgmPCHBean.page-1)*mgmPCHBean.limit+i.index+1}</td>
								<td class="text_center" nowrap><fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm:ss" value="${_item.submissionDate }" /></td>

								<c:if test="${_item.tranxType == 3}">
									<td style="color: blue;" class="text_center" nowrap>+ <c:out value="${_item.redeemPoint }"></c:out>
									</td>
								</c:if>
								<c:if test="${_item.tranxType != 3}">
									<c:if test="${_item.status == 5}">
										<td style="color: blue;" class="text_center" nowrap>+ <c:out value="${_item.redeemPoint }"></c:out>
										</td>
									</c:if>
									<c:if test="${_item.status != 5}">
										<td style="color: red;" class="text_center" nowrap>- <c:out value="${_item.redeemPoint }"></c:out>
										</td>
									</c:if>
								</c:if>

								<td class="text_right" nowrap><fmt:formatNumber value="${_item.remainingPoint}" pattern="${sessionScope.formatNumber}" /></td>
								<td class="text_center" nowrap><c:if test="${_item.tranxType == 1}">
										<spring:message code="mgm.fePoint.field.momo" />
									</c:if> <c:if test="${_item.tranxType == 2}">
										<spring:message code="mgm.fePoint.field.payment" />
									</c:if> <c:if test="${_item.tranxType == 3}">
										<spring:message code="mgm.fePoint.field.redeem" />
									</c:if></td>
									
								<td class="text_right" nowrap><fmt:formatNumber value="${_item.redeemPoint * _item.exchangeRate }" pattern="${sessionScope.formatNumber}" /></td>
								<td class="text_center" nowrap><fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm:ss" value="${_item.completionDate }" /></td>
								<td class="text_center" nowrap><c:out value="${_item.valueDescription }"></c:out></td>
								<td class="text_center" nowrap><fmt:formatDate type="both" pattern="dd/MM/yyyy" value="${_item.dateNextPayment }" /></td>
								<td class="text_center" nowrap><c:out value="${_item.partnerTransRefId }"> </c:out></td>
								<td class="text_center" nowrap>${mgmRedeemStatusMap[_item.status] }</td>
								<td class="text_center"><c:out value="${_item.remark }"></c:out></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="12" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title">
										<div class="pagination1 pagination1-small pagination1-right pagination1-left-total-css">
											<spring:message var="msg_Found" code="page.Found" />
											<spring:message var="msg_records" code="page.records" />
											<spring:message var="msg_page" code="page.page" />

											&nbsp;<span class="text">${msg_Found }</span><span class="number">${mgmPCHBean.total} </span><span class="text">${msg_records }</span><span
												class="number"> ${mgmPCHBean.totalPage}</span> <span class="text">${msg_page }</span>
										</div>
									</div>
									<div class="span6">
										<ext:pagination-ajax divId="MGM_PointConversationHistory" bean="${mgmPCHBean}" formId="search_history" maxPage="5"
											ajaxUrl="${url}/master_data/customer/viewMGMPointConversationHistory?id=${id }">
										</ext:pagination-ajax>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form:form>

</div>