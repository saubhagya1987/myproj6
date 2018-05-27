<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<spring:message var="msg_deleteItemQuestion"
	code="msg.alert.delete.questionitem"></spring:message>



<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
		$('#customerPopup').bind('hide', function() {
			if ($("#reloadyesorno").val() == "1") {
				document.location.href = "${url}/master_data/customer/list";
				/* $("#reloadyesorno").val(""); */
			}
		});
	});
	function setReloadyesorno(index) {
		$("#reloadyesorno").val(index);
	}
	function deleteItems() {
		confirmMessage('${msg_deleteQuestion}', deleteCallBack);
	}
	function newItem() {
		document.location.href = "${url}/master_data/customer/edit";
	}
	function deleteItem(id) {
		confirmMessage(
				'${msg_deleteItemQuestion}',
				function(result) {
					if (result)
						document.location.href = "${url}/master_data/customer/delete?id="
								+ id;
				});
	}

	function popupAddCustomer() {
		ajaxUrl = '${url}/master_data/customer/edit';
		$.ajax({
			url : ajaxUrl,
			success : function(result) {
				$("#customerPopup").html(result);
				$("#customerPopup").modal('show');
			}
		});
	}

	function popupEditCustomer(id) {
		ajaxUrl = '${url}/master_data/customer/edit?id=' + id;
		$.ajax({
			url : ajaxUrl,
			success : function(result) {
				$("#customerPopup").html(result);
				$("#customerPopup").modal('show');
			}
		});
	}

	function closePOPUP() {
		$("#customerPopup").modal('hide');
	}
	function backCustomer() {
		document.location.href = "${url}/master_data/customer/list";
	}
	function back() {
		document.location.href = "";
	}
	function onRenew() {
		window.location.href = "${url}/master_data/customer/list";
	}
</script>
<style>
</style>

<!-- start title -->
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_customers" code="menu.customers"></spring:message>
				<spring:message var="customer_list" code="customer.list.title"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"> <c:out
							value="${menu_customers }"></c:out></a> <span> > </span> <span
						class="Colorgray"><c:out value="${customer_list }"></c:out></span>
				</h4>

			</div>
			<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>
						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>
						<sec:authorize access="!hasAnyRole('R010')">
							<li class="new"><a href="javascript:;"
								onclick="popupAddCustomer()"><span class="new_text"></span></a></li>
						</sec:authorize>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- and title -->
<div class="container-fluid unit_bg_content">

	<div class="row-fluid">
		<div class="span12 title_h2">
			<h2>
				<spring:message var="list_title" code="customer.list.title"></spring:message>
				<c:out value="${list_title }"></c:out>
			</h2>
		</div>

	</div>



	<form:form method="POST" id="search_form"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
		<ext:messages bean="${bean}"></ext:messages>

		<div class="row-fluid">
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
								data-parent="#accordion2" href="#collapseOne"> <i
								class="bms-icon-accordion-down bms-icon-accordion"></i>
							</a>
						</div>
					</div>
				</div>
			</div>

			<spring:message var="msg_btn_search" code="button.search"></spring:message>

			<div id="collapseOne" class="accordion-body in collapse border-group">
				<div class="accordion-inner">
					<div class="input-area">

						<input id="reloadyesorno" type="hidden" />
						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="customerName" cssInput="span10"
									labelCode="customer.field.customer.name"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="cellPhone" cssInput="span10"
									labelCode="customer.field.cell.phone"></ext-form:input-text>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="cardNo" cssInput="span10"
									labelCode="customer.field.card.no"></ext-form:input-text>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label"> <spring:message
											code="customer.field.status" />
									</label>
									<div class="controls">
										<form:select path="status" class="span10"
											itemValue="languageId">
											<form:option value="">
												<spring:message code="msg.all"></spring:message>
											</form:option>
											<form:option value="1">
												<spring:message code="customer.field.status.active"></spring:message>
											</form:option>
											<form:option value="-1">
												<spring:message code="customer.field.status.inactive"></spring:message>
											</form:option>
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="shearAddress" cssInput="span10"
									labelCode="customer.field.address"></ext-form:input-text>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label"> <spring:message
											code="Customers.loan" />
									</label>
									<div class="controls">
										<form:select path="shearLoan" class="span10"
											itemValue="languageId">
											<form:option value="">
												<spring:message code="msg.all"></spring:message>
											</form:option>
											<form:option value="1">
												<spring:message code="Customers.loan.have"></spring:message>
											</form:option>
											<form:option value="-1">
												<spring:message code="Customers.loan.not"></spring:message>
											</form:option>
										</form:select>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				<!-- button -->
				<div class="text-center">
					<spring:message var="apply_now_renew" code="apply.now.renew"></spring:message>
					<input type="button" class="btn_review" onclick="onRenew()"
						name="renew" value="${apply_now_renew }" style="margin: 6px" /> <input
						type="hidden" id="action" name="action" value="search" />
					<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
					<button type="button"
						onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})"
						class="btn_search_general" name="search">${msg_buttonSearch }</button>
				</div>

			</div>
		</div>

		<div class="row-fluid">
			<!-- start table -->
			<div class="row-fluid row-align">
				<div class="span12">
					<table class="table table-bordered table-hover out-tbl">
						<thead>
							<tr>
								<td colspan="12" style="padding: 0px; margin: 0px;">
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
								<th><spring:message code="msg.no" /></th>
								<th><spring:message
										code="customer.list.field.brief.information" /></th>
								<th><spring:message var="msg_card_no"
										code="customer.field.card.id.no" /> <ext:column-sort
										bean="${bean }" path="idCardNumber" formId="search_form"
										fieldName="${msg_card_no }"></ext:column-sort></th>
								<th><spring:message var="msg_address"
										code="customer.field.address" /> <ext:column-sort
										bean="${bean }" path="addressNo" formId="search_form"
										fieldName="${msg_address }"></ext:column-sort></th>
								<th><spring:message var="msg_loan" code="Customers.loan" />
									<ext:column-sort bean="${bean }" path="oldUserId"
										formId="search_form" fieldName="${msg_loan }"></ext:column-sort>
								</th>
								<th><spring:message code="customer.field.status" /></th>
								<th class="table-actions"><spring:message code="actions" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="_item" items="${bean.listResult }" varStatus="i">
								<tr>
									<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>

									<td>
										<div class="row-fluid">
											<div class="span2">
												<c:if test="${empty _item.imagePath }">
													<img src="${url}/static/images/avata_c5.gif" height="70px"
														width="70px" />
												</c:if>
												<c:if test="${not empty _item.imagePath }">
													<img src='${url}/ajax/download?fileName=${_item.imagePath}'
														height="70px" width="70px">
												</c:if>
											</div>
											<div class="span3">
												<fmt:formatDate value="${_item.birthday}"
													pattern="${sessionScope.formatDate}" var="birthday" />
												<spring:message code="customer.field.customer.name"></spring:message>
												: <br />
												<spring:message code="customer.field.birthday"></spring:message>
												: <br />
												<spring:message code="customer.field.cell.phone"></spring:message>
												:
											</div>
											<div class="span7" style="color: #009933; text-align: left;">
												<c:out value="${_item.fullName }"></c:out>
												<br />
												<c:out value="${birthday }"></c:out>
												<br />
												<c:out value="${_item.cellPhone }"></c:out>
											</div>
										</div>

									</td>
									<td><c:out value="${_item.idCardNumber }"></c:out></td>
									<td><c:out value="${_item.address }"></c:out></td>
									<td><c:out value="${_item.oldUserId }"></c:out></td>
									<td class="text_center"><c:if test="${_item.status == 1}">
											<spring:message code="customer.field.status.active" />
										</c:if> <c:if test="${_item.status == -1}">
											<spring:message code="customer.field.status.inactive" />
										</c:if></td>
									<td class="table-actions"><spring:message var="icon_edit"
											code="icon.edit"></spring:message> <spring:message
											var="icon_view" code="icon.view"></spring:message> <a
										href="${url}/master_data/customer/view?id=${_item.customerId }"
										title="${icon_view}"><i class="bms-icon-view"></i></a> <sec:authorize
											access="!hasAnyRole('R010')">

											<a href="javascript:;"
												onclick="popupEditCustomer('${_item.customerId }')"
												title="${icon_edit}"><i class="bms-icon-edit"></i></a>
										</sec:authorize> <%-- <c:if test="${!_item.active }">
										<a href="#" onclick="deleteItem('${_item.customerId }')" ><i class="bms-icon-delete"></i></a>
									 </c:if>  --%></td>
								</tr>
							</c:forEach>
							<tr>
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
													class="number"> ${bean.totalPage}</span> <span class="text">${msg_page }</span>
											</div>
										</div>
										<div class="span6">
											<ext:pagination bean="${bean}" maxPage="5"
												formId="search_form"></ext:pagination>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- and table -->
		</div>
	</form:form>
</div>

<div id="customerPopup" class="modal hide fade feedbackPopup"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" data-width="800"
	style="width: 1100px; margin-left: -410px; height: 900px;"></div>


