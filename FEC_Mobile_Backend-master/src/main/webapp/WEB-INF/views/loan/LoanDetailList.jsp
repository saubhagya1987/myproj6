<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
		$("#reset").click(function() {
			reset();
		});
		$('#previewDetailPopup').bind('hide', function () {
			if($("#reloadyesorno").val()=="1"){
				document.location.href = "${url}/loan/listdetail";
			}
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

	function showEquipPopup() {
		$('#previewDetailPopup').modal('show');
	}
	
	function setReloadyesorno(index){
		$("#reloadyesorno").val(index);
	}
	
	function showTextVal(showPopup,id) {	
		$.ajax({
			url : '${url}/loan/loandetailEdit',
			data : {
				addedIds : $('#addedIds').val(),
				codeSearchPopup : $('#codeSearchPopup').val(),
				loanDetailID : id ,
			},
			dataType : 'html',
			type : 'GET',
			cache : false,
			success : function(data) {
				$("#previewDetailPopup").html(data);
				$('#previewDetailPopup').modal('show');

			}
		});
		
			
		var ids = $('#addedIds').val();
		if (ids == "") {
			$('#addedIds').val($('#equipIds').text())
		}
	}
	
	function popupShowInfo(id) {
		ajaxUrl = '${url}/loan/showSetup?id=' + id;
		$.ajax({
			url : ajaxUrl,
			success : function(data) {
				$("#popupBody").html(data);
				$("#loandetailPopup").modal('show');
			}
		});
	}
	
	function backList() {
		document.location.href = "";
	}
	
	
</script>

<style>
<!--
.span2 {
	width: 182px !important;
}
-->
</style>

<!-- start title -->
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_loan_request" code="menu.loan.request"></spring:message>
			<spring:message var="menu_loan_caculator_setup" code="menu.loan.calculator.setup"></spring:message>
			<spring:message var="menu_loan_calculator_setup_list" code="loan.detail.list"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backList()" class="Color_back"><c:out value="${menu_loan_request }"></c:out></a>
				<span> > </span>
				<a onclick="backList()" class="Color_back"><c:out value="${menu_loan_caculator_setup }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_loan_calculator_setup_list }"></c:out></span>
			</h4>
		</div>
		
		<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>


						<li class="new"><a onclick="showTextVal(true)"
							title="Add new"><span class="new_text"></span></a></li>
					</ul>
				</div>
			</div>
</div>
</div>
</div>
<!-- and title -->
<div class="container-fluid unit_bg_content">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<spring:message var="msg_List" code="loan.detail.list"></spring:message>
				<h2>${msg_List}</h2>

			</div>			
		</div>

	<form:form method="POST" id="search_form_Loan"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
		<div class="accordion" id="accordion2">
			<ext:messages bean="${bean}"></ext:messages>
			<input id="reloadyesorno"  type="hidden"/>
			<div class="accordion-group"></div>

			<div class="accordion-inner">

				<div class="input-area">
					<div class="span12 input-append"></div>

				</div>
			</div>

		</div>


		<div id="previewDetailPopup" class="modal hide fade assetPopup"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true" data-width="1000"></div>



		<div class="row-fluid row-align">
			<div class="span12">
				<table class="table table-bordered table-hover out-tbl">
					<thead>
						<tr>
							<spring:message var="msg_stt" code="loan.detail.STT"></spring:message>
							<spring:message var="msg_loan_category"
								code="loan.detail.category"></spring:message>
							<spring:message var="msg_loan_detail_condition"
								code="loan.detail.condition"></spring:message>
							<spring:message var="msg_loan_detail_min_amount"
								code="loan.detail.min.amount"></spring:message>
							<spring:message var="msg_loan_detail_max_amount"
								code="loan.detail.max.amount"></spring:message>
							<spring:message var="msg_loan_detail_amount_slide"
								code="loan.detail.amount.slide"></spring:message>
							<spring:message var="msg_loan_detail_min_tenure"
								code="loan.detail.tenure.tenure"></spring:message>
							<spring:message var="msg_loan_detail_max_tenure"
								code="loan.detail.max.tenure"></spring:message>
							<spring:message var="msg_loan_detail_tenure_per_side"
								code="loan.detail.tenure.per.slide"></spring:message>
							<th class="w50_stt">${msg_stt}</th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_category}" path="loan.condition_category"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_detail_condition}" path="loan.condition_name"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_detail_min_amount}" path="minamount"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_detail_max_amount}" path="maxamount"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_detail_amount_slide}" path="amountslide"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_detail_min_tenure}" path="tenure"
									formId="search_form_Loan" /></th>
							<th class="table-actions"><spring:message
									code="loan.detail.action" /></th>
						</tr>
					</thead>

					<tbody>
						<c:set var="index" value="0"></c:set>
						<c:forEach var="loan_detail" items="${bean.listResult }"
							varStatus="i">
							<tr id="tr${index}">
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>

								<td><c:out value="${loan_detail.loan.condition_category }"></c:out>
									<input type="hidden" id="equipIds${index}"
									value="${loan_detail.loanDetailID}" /> <c:set var="equipIds"
										value="${equipIds},${loan_detail.loanDetailID}"></c:set></td>
								<td><c:out value="${loan_detail.loan.condition_name }" /></td>

								<fmt:formatNumber var="minamount"
									value="${loan_detail.minamount}"
									pattern="${sessionScope.formatNumber}" />
								<td class="text_right"><c:out value="${minamount}" /></td>

								<fmt:formatNumber var="maxamount"
									value="${loan_detail.maxamount}"
									pattern="${sessionScope.formatNumber}" />
								<td class="text_right"><c:out value="${maxamount}" /></td>

								<fmt:formatNumber var="amountslide"
									value="${loan_detail.amountslide}"
									pattern="${sessionScope.formatNumber}" />
								<td class="text_right"><c:out value="${amountslide}" /></td>
								<td class="text_right"><c:out value="${loan_detail.tenure }" /></td>
								<td class="table-actions">
								<spring:message var="icon_edit"
										code="icon.edit"></spring:message> <spring:message
										var="icon_view" code="icon.view"></spring:message> 
										<a onclick="popupShowInfo(${loan_detail.loanDetailID})" title="${icon_view}"><i
										class="bms-icon-view"></i></a><sec:authorize access="!hasAnyRole('R010')"><a
									onclick="showTextVal(true,${loan_detail.loanDetailID})"title="${icon_edit}"> <i
										class="bms-icon-edit"></i></a></sec:authorize></td>
							</tr>
							<c:set var="index" value="${index + 1}"></c:set>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>


		<!-- and table -->
	</form:form>
</div>
<div class="previewPopup margin_bottom_require">
	<div id="loandetailPopup" class="modal hide fade assetPopup" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-width="1000">
		<div class="modal-header">

			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="myModalLabel">
				<spring:message var="msg_loandetail_view" code="loan.detail.view"></spring:message>
				${msg_loandetail_view}
			</h3>

		</div>
		<div class="modal-body">
			<div id="popupBody" class="row-fluid"></div>
		</div>

	</div>
</div>
