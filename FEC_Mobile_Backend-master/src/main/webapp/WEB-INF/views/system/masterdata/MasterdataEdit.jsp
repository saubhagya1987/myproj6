<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>

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
<form:form method="POST" id="hobby_edit" cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">



	<div class="modal-header">


		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
		<h3 id="myModalLabel">
			<spring:message var="edit_title" code="${not empty bean.entity.masterdataId?'Masterdata.edit':'Masterdata.new' }" />
			<c:out value="${edit_title }"></c:out>
		</h3>

	</div>

	<div class="modal-body">
		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid">

			<div class="span6">
				<input type="hidden" name="succesorfail" id="succesorfail" value="${bean.succesorfail}">
				<%-- ${bean.succesorfail} --%>
			</div>
			<div class="span6"></div>

		</div>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:input-text path="entity.name" required="true" cssInput="width_area" labelCode="hobby.Name"></ext-form:input-text>
			</div>

		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="control">
					<div class="span12" >
						<ext-form:input-text path="entity.description" cssInput="width_area" labelCode="hobby.Description"></ext-form:input-text>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="modal-footer text_center" style="background: White">

		<spring:message var="msg_btn_store" code="banner.btn.store"></spring:message>
		<button type="button" class="btn_search_general" id="saveHobby" name="action" onclick="submitPopup();" value="${msg_btn_store }">${msg_btn_store }</button>


		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		<button type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true">${msg_btn_close }</button>
	</div>
	<form:hidden path="entity.masterdataId" />
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
			url : '${url}/system/masterdata/MasterdataEdit',
			data : $('#hobby_edit').serialize(),
			dataType : 'html',
			type : 'POST',
			cache : false,
			success : function(data) {
				console.log(data);
				$("#previewDetailPopup").html(data);
				setReloadyesorno(1);
				/* var succesorfail = $(data).find("#succesorfail").val();
				if (succesorfail == "succes") {
					$('#search_form_Hobby').submit();
					document.location.href = "${url}/master_data/hobby/list";
				} else {
					$("#popupBody").html(data);
				} */
			}
		});

	}
	function deleteItems() {
		confirmMessage('${msg.save.success}', deleteCallBack);
	}
	function deleteCallBack(result) {
		if (result) {
			$("#hobby_edit").submit();
		}
	}
	function load() {

		document.location.href = "${url}/system/masterdata/list";

	}

	
</script>