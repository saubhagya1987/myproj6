<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>

<form:form method="POST" id="loan_detail_edit"
	cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">

	<div class="modal-header">


		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">
			<spring:message var="edit_title"
				code="${not empty bean.entity.loanDetailID?'loan.detail.edit':'loan.detail.addNew' }" />
			<c:out value="${edit_title }"></c:out>
		</h3>

	</div>

	<div class="modal-body">
		<div class="row-fluid">
			<ext:messages bean="${bean}"></ext:messages>
			<input type="hidden" name="succesorfail" id="succesorfail"
				value="${bean.succesorfail}">

		</div>
		<div class="span11" style="overflow: auto; max-height: 280px;">


			<div class="span6"></div>

			<div class="row-fluid">
				<div class="span12">
					<div class="span6">

						<label for="type" class="control-label"> <spring:message
								code="loan.condition.category" />
						</label>
						<div class="controls">
							<form:select id="category" path="entity.loan.condition_category"
								class="span10" itemValue="languageId">
								<c:forEach items="${loanCategory}" var="item">
									<form:option value="${item.key}">${item.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label for="type" class="control-label"> <spring:message
									code="loan.detail.condition" />
							</label>
							<div class="controls">
								<form:select id="conditionnameID" path="entity.loan.loanID"
									class="span10">
									<c:forEach var="loan_detail" items="${bean.listLoan}"
										varStatus="i">
										<form:option value="${loan_detail.loanID}">${loan_detail.condition_name}</form:option>
									</c:forEach>
								</form:select>

							</div>
						</div>
					</div>
				</div>


				<div class="row-fluid">
					<div class="span12">
						<div class="span6">

							<ext-form:input-number path="entity.minamount" required="true"
								cssInput="span10" labelCode="loan.detail.min.amount"
								maxlength="20" id="entity_minamount"></ext-form:input-number>
						</div>
						<div class="span6">

							<ext-form:input-number path="entity.maxamount" required="true"
								cssInput="span10" labelCode="loan.detail.max.amount"
								maxlength="20" id="entity_maxamount"></ext-form:input-number>
						</div>
					</div>
				</div>

				<div class="row-fluid">
					<div class="span12">
						<div class="span6">

							<ext-form:input-number path="entity.amountslide" required="true"
								cssInput="span10" labelCode="loan.detail.amount.slide"
								maxlength="20" id="entity_amountslide"></ext-form:input-number>
						</div>
						<div class="span6"></div>
					</div>
				</div>




				<div class="row-fluid" style="display: none">
					<div class="span12">
						<div class="span6">

							<fmt:formatNumber var="amt" value="${ 1}" pattern="${numPattern}" />
							<ext-form:input-number path="entity.mintenure" required="true"
								cssInput="numberText span10" labelCode="loan.detail.min.tenure"
								value="${amt}" id="entity_mintenure"></ext-form:input-number>
						</div>
						<div class="span6">
							<fmt:formatNumber var="amt" value="${ 0}" pattern="${numPattern}" />
							<ext-form:input-number path="entity.maxtenure" required="true"
								cssInput="numberText span10" labelCode="loan.detail.max.tenure"
								value="${amt}" id="entity_maxtenure"></ext-form:input-number>
						</div>
					</div>
				</div>
				<div class="row-fluid" style="display: none">
					<div class="span12">
						<div class="span6">

							<fmt:formatNumber var="num" value="${ 0}" pattern="${numPattern}" />
							<ext-form:input-number path="entity.tenureperslide"
								required="true" cssInput="numberText span10"
								labelCode="loan.detail.tenure.per.slide" maxlength="20"
								value="${num}" id="entity_tenureperslide"></ext-form:input-number>
						</div>
						<div class="span6"></div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<ext-form:input-text path="entity.tenure" cssInput="width_area"
												required="true" labelCode="loan.detail.tenure.tenure" 
											id="entity_tenure" ></ext-form:input-text>
					</div>
				</div>
				

				<div class="modal-footer text_center" style="background: White">
					<spring:message var="msg_btn_save" code="btn.save"></spring:message>
					<button type="button" class="btn_search_general" id="saveLoan"
						style="margin: 6px" name="action" onclick="submitPopup();"
						value="${msg_btn_save}">${msg_btn_save}</button>


					<spring:message var="msg_btn_close" code="btn.close"></spring:message>
					<button type="button" class="btn_search_general"
						data-dismiss="modal" aria-hidden="true">${msg_btn_close}</button>
				</div>
			</div>
		</div>
	</div>

	<form:hidden path="entity.loan.loanID" />
	<form:hidden path="entity.loanDetailID" />
</form:form>
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name=checkall]").click(function() {
			$("input[name=checkbox]").each(function() {
				this.checked = $("input[name=checkall]").is(':checked');
			});
		});
	});

	function submitPopup(showPopup, id) {
		$.ajax({
			url : '${url}/loan/loandetailEdit',
			data : $('#loan_detail_edit').serialize(),
			dataType : 'html',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#previewDetailPopup").html(data);
				setReloadyesorno(1);
				//				var succesorfail = $(data).find("#succesorfail").val();
				// 				if (succesorfail == "succes") {
				// 					$('#search_form_Loan').submit();
				// 					/* $('#previewDetailPopup').modal('hide'); */
				// 					document.location.href = "${url}/loan/listdetail";
				// 				} else {
				// 					$("#previewDetailPopup").html(data);
				// 					setReloadyesorno(1);
				// 				}
			}
		});

	}
	function deleteItems() {
		confirmMessage('${msg.save.success}', deleteCallBack);
	}
	function deleteCallBack(result) {
		if (result) {
			$("#loan_detail_edit").submit();
		}
	}

	$("#category").change(
			function() {
				var url = '${url}/loan/listCatagory?category='+ $("#category").val();
				if($("#conditionnameID").val()!='' && $("#conditionnameID").val()!=null){
					url = '${url}/loan/listCatagory?category='+ $("#category").val()+'&id=' + $("#conditionnameID").val();
				}				
				$("#conditionnameID option").remove();
				$.ajax({
					url : url,
					dataType : 'json',
					type : 'GET',
					cache : false,
					success : function(data) {
						$.each(data, function(i, object) {
							if (object.loanID == null) {
								$("#conditionnameID").append(
										"<option value=''>" + "</option>");
							} else {
								$("#conditionnameID").append(
										"<option value='"+object.loanID+"'>"
												+ object.condition_name
												+ "</option>");
							}
						});
					}
				});
			});

	function formatNumber(obj) {
		var number = obj.parseNumber({
			format : "${numPattern}",
			locale : "${sessionScope.localeSelect}"
		});

		// If price < 0 then set value = 0
		if (number < 0) {
			obj.val(1);
			return;
		}

		obj.formatNumber({
			format : "${numPattern}",
			locale : "${sessionScope.localeSelect}"
		});

	}

	$(document).ready(function() {
		$(".numberText").change(function() {
			formatNumber($(this));
		});
	});

	
</script>