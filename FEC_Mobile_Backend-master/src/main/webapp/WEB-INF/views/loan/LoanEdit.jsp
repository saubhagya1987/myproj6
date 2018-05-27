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
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<form:form method="POST" id="loan_edit" cssClass="form-horizontal"
	modelAttribute="bean">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">
			<spring:message var="edit_title"
				code="${not empty bean.entity.loanID?'loan.edit':'loan.addNew' }" />
			<c:out value="${edit_title }"></c:out>
		</h3>

	</div>

	<div class="modal-body">

		<div class="row-fluid">
			<ext:messages bean="${bean}"></ext:messages>
			<input type="hidden" name="succesorfail" id="succesorfail"
				value="${bean.succesorfail}">

		</div>

		<div class="span6"></div>

		<div class="span11" style="overflow: auto; max-height: 280px;">

			<div class="row-fluid">
				<div class="control-group">
					<ext-form:input-text path="entity.condition_name" cssInput="span11"
						required="true" labelCode="loan.condition.name"></ext-form:input-text>
				</div>

			</div>
			<div class="row-fluid">
				<div class="span6">
					<div class="control-group">
						<label for="type" class="control-label"> <spring:message
								code="loan.condition.category" />
						</label>
						<div class="controls">
							<form:select path="entity.condition_category" class="span10"
								itemValue="languageId">
								<c:forEach items="${loanCategory}" var="item">
									<form:option value="${item.key}">${item.value}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
				
				<div class="span6">
					<fmt:formatNumber var="num" value="" pattern="${numPattern}" />
					<div class="control-group" style="margin-right: 20px">
						<ext-form:input-number path="entity.condition_value"
							required="true" labelCode="loan.value" maxlength="3"
							cssInput="numberText span10" value="${num}" id="condition_value"></ext-form:input-number>
					</div>
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
	<form:hidden path="entity.loanID" />
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
			url : '${url}/loan/loanEdit',
			data : $('#loan_edit').serialize(),
			dataType : 'html',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#previewDetailPopup").html(data);
				setReloadyesorno(1);
				// 				var succesorfail = $(data).find("#succesorfail").val();
				// 				if (succesorfail == "succes") {
				// 					$('#search_form_Loan').submit();
				// 					/* $('#previewDetailPopup').modal('hide'); */
				// 					document.location.href = "${url}/loan/list";
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
			$("#loan_edit").submit();
		}
	}

	function formatNumber(obj) {
		var number = obj.parseNumber({
			format : "${numPattern}",
			locale : "${sessionScope.localeSelect}"
		});

		// If price < 0 then set value = 0
		if (number < 0) {
			obj.val(" ");
			return;
		}

		if (number < " ") {
			obj.val(" ");
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