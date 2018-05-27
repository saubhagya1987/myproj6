<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<<style>
<!--
	.control-group1 .control-label {
		padding-top: 0px !important;
	}
-->
</style>
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_mobile" code="contract.mobile"></spring:message>
				<spring:message var="menu_contract_list" code="contract.list"></spring:message>
				<spring:message var="menu_contract_detail" code="contract.detail"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="backList()" class="Color_back"><c:out
							value="${menu_mobile }"></c:out></a> <span> > </span> <a
						onclick="backList()" class="Color_back"><c:out
							value="${menu_contract_list }"></c:out></a> <span> > </span> <span
						class="Colorgray"><c:out value="${menu_contract_detail }"></c:out></span>
				</h4>
			</div>

		</div>
	</div>
</div>
<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message code="contract.detail"></spring:message>
			</h2>
		</div>
	</div>

	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" modelAttribute="bean"
		cssClass="form-horizontal">

		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="contract.info"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_1"> <i
							class="bms-icon-accordion bms-icon-accordion-down"></i>
						</a>
					</div>
				</div>
			</div>
		</div>

		<div id="collapseOne_1"
			class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.application.id"></spring:message>
								</label>
								<div class="controls">

									<label>${bean.contractdetailApi.APP_ID}</label>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.date"></spring:message>
								</label>
								<div class="controls">
 									<fmt:formatDate var="var_createddate" value="${bean.contractdetailApi.ACTIVE_DATE}" pattern="${sessionScope.formatDate} HH:mm" /> 
									<label>${var_createddate}</label>
								</div>
							</div>
						</div>
					</div>

					<div class="row-fluid">
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.no"></spring:message>
								</label>
								<div class="controls">
									<label>${bean.contractdetailApi.CONTRACT_NUMBER}</label>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group1">
								<%-- 								<fmt:formatNumber var="loanAmount" --%>
								<%--  									value="${bean.entity.loandetail.loanamount}" --%>
								<%--  									pattern="${sessionScope.formatNumber}" />  --%>
								<label class="control-label"> <spring:message
										code="contract.info.loan.amount"></spring:message>
								</label>
								<div class="controls">
									<%-- 									<label for="type"><c:out value="${loanAmount}" /></label> --%>
								</div>
							</div>
						</div>
					</div>

					<div class="row-fluid">
						<div class="span6">
							<div class="control-group1">
								<fmt:formatNumber var="creditAmount"
									value="${bean.contractdetailApi.CREDIT_AMOUNT}"
									pattern="${sessionScope.formatNumber}" />
								<label class="control-label"> <spring:message
										code="contract.info.credit.amount"></spring:message>
								</label>
								<div class="controls">
									<label for="type"><c:out value="${creditAmount}" /></label>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.interest.rate"></spring:message>
								</label>
								<div class="controls">
									<%-- 									<c:if --%>
									<%-- 										test="${not empty bean.entity.loandetail.loan.condition_value}"> --%>
									<%-- 										<span><label>${bean.entity.loandetail.loan.condition_value} %</label></span> --%>
									<%-- 									</c:if> --%>
								</div>
							</div>
						</div>
					</div>


					<div class="row-fluid">
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.status"></spring:message>
								</label>
								<div class="controls">
									<c:if
										test="${bean.contractdetailApi.CONTRACT_STATUS == 'A'}">
										<label><spring:message
												code="customer.field.status.active" /></label>
									</c:if>
									<c:if
										test="${bean.contractdetailApi.CONTRACT_STATUS == 'C'}">
										<label><spring:message
												code="customer.field.status.inactive" /></label>
									</c:if>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.product.code"></spring:message>
								</label>
								<div class="controls">
									<%-- 									<label>${bean.entity.loandetail.loan.condition_category}</label> --%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>



		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.info" />
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_6"> <i
							class="bms-icon-accordion bms-icon-accordion-down"></i>
						</a>
					</div>
				</div>
			</div>
		</div>

		<div id="collapseOne_6"
			class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">
					
						<div class="span6">
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.fullname"></spring:message>
								</label>
								<div class="controls">
									<label class="control-label">${bean.contractdetailApi.CUSTOMER_NAME}&nbsp;</label>
								</div>
							</div>
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.cellphone"></spring:message>
								</label>
								<div class="controls">

									<label class="control-label">${bean.customer.cellPhone}</label>
								</div>
							</div>
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.email"></spring:message>
								</label>
								<div class="controls">
									<label class="control-label">${bean.customer.emailAddress}</label>
								</div>
							</div>

							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.id.card.no"></spring:message>
								</label>
								<div class="controls">
									<label class="control-label">${bean.contractdetailApi.CUSTOMER_ID_CARD_NUMBER}</label>
								</div>
							</div>
							<div class="control-group1">
								<label class="control-label"> <spring:message
										code="contract.info.address"></spring:message>
								</label>
								<div class="controls">			
														
									 <label class="control-label" style="width: 100%" >${bean.customer.addressNo} 
									${bean.customer.street} ${bean.customer.ward} 
									${bean.customer.district} ${bean.customer.province} 
									</label>
									
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="row-fluid">
								<label for="type" class="control-label"></label>
								<div class="controls">
									<div class="span6" style="width: 175px;">
										<div class="border_img_2" style="height: 165px; width: 210px">
											<img id="imgProduct" style="height: 100%;">
										</div>
									</div>
									<spring:message var="icon_view" code="icon.view"></spring:message>
									<spring:message var="icon_edit" code="icon.edit"></spring:message>
									<spring:message var="icon_clone" code="icon.clone"></spring:message>
									<spring:message var="icon_delete" code="icon.delete"></spring:message>
								
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="menu.installments" />
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_2"> <i
							class="bms-icon-accordion bms-icon-accordion-down"></i>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div id="collapseOne_2"
			class="accordion-body collapse in border-group">
			<div style="text-align: center">
				<h4 style="font-weight: 700; margin-bottom: 12px; margin-top: 12px">
					<spring:message code="menu.next.installments" />
				</h4>
			</div>
			<div class="title_table1 row-fluid">
				<div class="title_installment">
					<label for="type" class="next-installment"> <spring:message
							code="installment.detail.duedate" />
					</label> <label for="type" class="next-installment"><span
						class="required">
						<fmt:formatDate var="var_createddate" value="${bean.contractdetailApi.DUE_DATE}" pattern="${sessionScope.formatDate} HH:mm" />
						<c:out	value="${var_createddate}" /></span></label>

					<fmt:formatNumber var="amount"
						value="${bean.contractdetailApi.NEXT_INSTALLMENT_AMOUNT}"
						pattern="${sessionScope.formatNumber}" />
					<label for="type" class="next-installment"> <spring:message
							code="installment.detail.amount" />
					</label> <label for="type" class="next-installment"><span
						class="required"><c:out value="${amount}" /> </span> <span
						style="color: black;">VNĐ</span></label>
				</div>
			</div>
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">
						<div class="span12">
							<div class="scroll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<h3>
															<spring:message code="menu.installments.detail" />
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
											<th><spring:message code="installment.detail.duedate" /></th>
											<th><spring:message code="installment.detail.amount" /></th>
											<th><spring:message code="installment.detail.paiddate" /></th>
											<th><spring:message code="installment.detail.status" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="installment"
											items="${contractinstallmentapi.listResult }" varStatus="i">
											<tr>
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td class="text_center">
												<fmt:formatDate var="dueDate" value="${installment.dueDate}" pattern="${sessionScope.formatDate} HH:mm" />
												<c:out value="${dueDate}"></c:out></td>
												<fmt:formatNumber var="amount" value="${installment.amount}"
													pattern="${sessionScope.formatNumber}" />
												<td style="text-align: right;"><c:out value="${amount}"></c:out></td>
												<td class="text_center">
												<fmt:formatDate var="paymentDate" value="${installment.paymentDate}" pattern="${sessionScope.formatDate} HH:mm" />
												<c:out	value="${paymentDate}"></c:out></td>
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
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
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
					</div>
				</div>
			</div>
		</div>

		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="menu.payment" />
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_3"> <i
							class="bms-icon-accordion bms-icon-accordion-down"></i>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div id="collapseOne_3"
			class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">
						<div class="span12">
							<div class="scroll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<h3>
															<spring:message code="payment.history.title" />
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
											<th><spring:message code="payment.transaction.code" /></th>
											<th><spring:message code="payment.date" /></th>
											<th><spring:message code="payment.amount" /></th>
											<th><spring:message code="payment.description" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="payment" items="${paymentapi.listResult }"
											varStatus="i">
											<tr>
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${payment.transactionCode }"></c:out></td>
												<td class="text_center">
												<fmt:formatDate var="paymentdate" value="${payment.paymentdate}" pattern="${sessionScope.formatDate} HH:mm" />
												<c:out	value="${paymentdate}"></c:out></td>
												<fmt:formatNumber var="amount" value="${payment.amount}"
													pattern="${sessionScope.formatNumber}" />
												<td style="text-align: right;"><c:out value="${amount}"></c:out></td>
												<td style="text-align: right;"><c:out
														value="${payment.note }"></c:out></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
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
					</div>
				</div>
			</div>
		</div>

		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="menu.messages" />
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_4"> <i
							class="bms-icon-accordion bms-icon-accordion-down"></i>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div id="collapseOne_4"
			class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">
						<div class="span12">
							<div class="scroll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<th><spring:message code="msg.no" /></th>
											<th><spring:message
													code="customer.messages.field.subject" /></th>
											<th><spring:message code="message.sent.date" /></th>
											<th><spring:message
													code="customer.messages.field.comments.reply" /></th>
											<th><spring:message
													code="customer.messages.field.attachment" /></th>
											<th><spring:message code="customer.field.status" /></th>
											<th><spring:message code="message.action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="messges" items="${bean.messgesLst }"
											varStatus="i">
											<tr>
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${messges.subject }"></c:out></td>
												<td class="text_center"><fmt:formatDate
														var="var_createdDate" value="${messges.createdDate}"
														pattern="${sessionScope.formatDate}" /> <c:out
														value="${var_createdDate }"></c:out></td>
												<td style="text-align: right;"><c:out
														value="${messges.commentsCount }"></c:out></td>
												<td><a href="#"><i class="icon-unlink"></i></a></td>
												<td><c:if test="${messges.status == 0}">
														<spring:message code="status.sent" />
													</c:if> <c:if test="${messges.status == 1}">
														<spring:message code="status.new" />
													</c:if> <c:if test="${messges.status == 2}">
														<spring:message code="status.assigned" />
													</c:if> <c:if test="${messges.status == 3}">
														<spring:message code="status.re.assigned" />
													</c:if> <c:if test="${messges.status == 4}">
														<spring:message code="status.replied" />
													</c:if> <c:if test="${messges.status == 5}">
														<spring:message code="status.move.to.close" />
													</c:if> <c:if test="${messges.status == 6}">
														<spring:message code="status.close" />
													</c:if> <c:if test="${messges.status == 7}">
														<spring:message code="status.move.to.cancelling" />
													</c:if> <c:if test="${_messges.status == -2}">
														<spring:message code="status.cancel" />
													</c:if></td>
												<td></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
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
					</div>
				</div>
			</div>
		</div>

		<!-- 		<div class="accordion-group"> -->
		<!-- 			<div class="accordion-heading"> -->
		<!-- 				<div class="row-fluid"> -->
		<!-- 					<div class="span2 title1"> -->
		<!-- 						<h3> -->
		<%-- 							<spring:message code="menu.activity.logs" /> --%>
		<!-- 						</h3> -->
		<!-- 					</div> -->
		<!-- 					<div class="span1 unit_accordion" style="text-align: right;"> -->
		<!-- 						<a class="accordion-toggle" data-toggle="collapse" -->
		<!-- 							data-parent="#accordion2" href="#collapseOne_5"> <i -->
		<!-- 							class="bms-icon-accordion bms-icon-accordion-down"></i> -->
		<!-- 						</a> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 		</div> -->
		<!-- 		<div id="collapseOne_5" -->
		<!-- 			class="accordion-body collapse in border-group"> -->
		<!-- 			<div class="accordion-inner"> -->
		<!-- 				<div class="input-area"> -->
		<!-- 					<div class="row-fluid"> -->
		<!-- 						<div class="span12"> -->
		<!-- 							<table class="table table-bordered table-hover out-tbl"> -->
		<!-- 								<thead> -->
		<!-- 									<tr> -->
		<%-- 										<th><spring:message --%>
		<%-- 												code="customer.field.title.activity.no" /></th> --%>
		<%-- 										<th><spring:message code="activity.log.code" /></th> --%>
		<%-- 										<th><spring:message code="activity.log.date" /></th> --%>
		<%-- 										<th><spring:message code="activity.description" /></th> --%>
		<!-- 									</tr> -->
		<!-- 								</thead> -->
		<!-- 								<tbody> -->
		<%-- 									<c:forEach var="activityLogs" items="${bean.activityLogs }" --%>
		<%-- 										varStatus="i"> --%>
		<!-- 										<tr> -->

		<%-- 											<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td> --%>
		<%-- 											<td><c:out value="${activityLogs.logCode}"></c:out></td> --%>
		<%-- 											<td class="text_center"><fmt:formatDate --%>
		<%-- 													var="var_createdDate" value="${activityLogs.logDate}" --%>
		<%-- 													pattern="${sessionScope.formatDateTime}" /> <c:out --%>
		<%-- 													value="${var_createdDate }"></c:out></td> --%>
		<%-- 											<td><c:out value="${activityLogs.descritption}"></c:out></td> --%>
		<!-- 										</tr> -->
		<%-- 									</c:forEach> --%>
		<!-- 									<tr> -->
		<!-- 										<td colspan="12" style="padding: 0px; margin: 0px;"> -->
		<!-- 											<div class="title_table row-fluid"> -->
		<!-- 												<div class="span6 title">&nbsp;</div> -->

		<!-- 											</div> -->
		<!-- 										</td> -->
		<!-- 									</tr> -->
		<!-- 								</tbody> -->
		<!-- 							</table> -->
		<!-- 							<div class="span6"> -->
		<%-- 								<ext:pagination bean="${bean}" maxPage="5" formId="search_form"></ext:pagination> --%>
		<!-- 							</div> -->
		<!-- 						</div> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 		</div> -->
	</form:form>
</div>
<script type="text/javascript">
	function backList() {
		document.location.href = "${url}/contract/list";
	}

	$(document).ready(
			function() {

				// IMAGE account
				var image = $("#tagImage").val();
				if (image != "") {
					$("#imgProduct").attr("src",
							"${url}/ajax/download?fileName=" + image);
				}

				//init image uploader
				var uploadUrl = "${url}/ajax/uploadTemp";
				var imagePickfiles = 'imgPickfiles';
				var imageContainer = 'imageContainer';
				var imageMaxFileSize = '7mb';
				var imageMimeTypes = [ {
					title : "Image files",
					extensions : "jpg,bmp,png"
				} ];
				var imageFileList = 'imageFilelist';
				var imageConsole = 'imageConsole';
				var imageFileUploaded = function(up, file, info) {
					$("#tagImage").val(cutString(info.response));
				};

				var imageUploadComplete = function(up, files) {
					var lstImg = $("#tagImage").val();

					$("#imgProduct").attr("src",
							"${url}/ajax/download?fileName=" + lstImg);
					$("#" + imageConsole).hide();
					$("#" + imageFileList).hide();
				};
				InitPlupload(imagePickfiles, imageContainer, uploadUrl, false,
						imageMaxFileSize, imageMimeTypes, imageFileList,
						imageConsole, imageFileUploaded, imageUploadComplete,
						'${url}');
			});
</script>
