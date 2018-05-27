<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
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
				document.location.href = "${url}/loan/list";
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
	
	function backList() {
		document.location.href = "${url}/loan/list";
	}

	function deleteAction(id) {
		confirmMessage(
				'${msg_deleteQuestion}',
				function(result) {
					if (result) {
						document.location.href = "${url}/master_data/cardtype/delete?entity.cardTypeId="
								+ id;
					}
				});
	}

	function showEquipPopup() {
		$('#previewDetailPopup').modal('show');
	}

	function setReloadyesorno(index){
		$("#reloadyesorno").val(index);
	}
	
	function showTextVal(showPopup,id) {
		$.ajax({
			url : '${url}/loan/loanEdit',
			data : {
				addedIds : $('#addedIds').val(),
				codeSearchPopup : $('#codeSearchPopup').val(),
				loanID : id
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
		ajaxUrl = '${url}/loan/show?id=' + id;
		$.ajax({
			url : ajaxUrl,
			success : function(data) {
				$("#popupBody").html(data);
				$("#loanPopup").modal('show');
			}
		});
	}
</script>
<!-- start title -->
<div class="title_top">

	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_loan_request" code="menu.loan.request"></spring:message>
			<spring:message var="menu_loan_caculator" code="loan.caculator"></spring:message>
			<spring:message var="menu_loan_condition_list" code="loan.List"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backList()" class="Color_back"><c:out value="${menu_loan_request }"></c:out></a>
				<span> > </span>
				<a onclick="backList()" class="Color_back"><c:out value="${menu_loan_caculator }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_loan_condition_list }"></c:out></span>
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
				<spring:message var="msg_List" code="loan.List"></spring:message>
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




		<!-- start table -->
		<div class="row-fluid row-align">
			<div class="span12">
				<table class="table table-bordered table-hover out-tbl">
					<thead>
						<tr>
							<spring:message var="msg_stt" code="loan.STT"></spring:message>
							<spring:message var="msg_loan_category"
								code="loan.condition.category"></spring:message>
							<spring:message var="msg_loan_name" code="loan.condition.name"></spring:message>
							<spring:message var="msg_loan_value" code="loan.condition.value"></spring:message>
							<th class="w50_stt">${msg_stt}</th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_category}" path="condition_category"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_name}" path="condition_name"
									formId="search_form_Loan" /></th>
							<th><ext:column-sort bean="${bean }"
									fieldName="${msg_loan_value} (%)" path="condition_value"
									formId="search_form_Loan" /></th>
							<th class="table-actions"><spring:message code="loan.action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="index" value="0"></c:set>
						<c:forEach var="_loan" items="${bean.listResult }" varStatus="i">
							<tr id="tr${index}">
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>

								<td><c:out value="${_loan.condition_category }"></c:out> <input
									type="hidden" id="equipIds${index}" value="${_loan.loanID}" />
									<c:set var="equipIds" value="${equipIds},${_loan.loanID}"></c:set></td>
								<td><c:out value="${_loan.condition_name }" /></td>
								<td class="text_right"><c:out value="${_loan.condition_value }" /></td>
								<td class="table-actions">
								 <spring:message
										var="icon_view" code="icon.view"></spring:message> 
								<a onclick="popupShowInfo(${_loan.loanID})" title="${icon_view}"><i
										class="bms-icon-view"></i></a> 
										<spring:message var="icon_edit" code="icon.edit"></spring:message>
								<sec:authorize access="!hasAnyRole('R010')">										
										<a onclick="showTextVal(true,${_loan.loanID})" title="${icon_edit }"> <i class="bms-icon-edit"></i></a>
								</sec:authorize>

								</td> 
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
	<div id="loanPopup" class="modal hide fade assetPopup" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-width="1000">
		<div class="modal-header">

			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="myModalLabel">
				<spring:message var="msg_loan_view" code="loan.view"></spring:message>
				${msg_loan_view}
			</h3>

		</div>
		<div class="modal-body">
			<div id="popupBody" class="row-fluid"></div>
		</div>

	</div>
</div>

