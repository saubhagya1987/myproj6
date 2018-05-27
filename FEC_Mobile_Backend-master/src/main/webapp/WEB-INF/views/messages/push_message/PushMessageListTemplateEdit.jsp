<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<form:form method="POST" id="push_message_edit" cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">
			<spring:message var="edit_title" code="push.message.schedule.edit" />
			<c:out value="${edit_title }"></c:out>
		</h3>
	</div>
	<ext:messages bean="${bean}"></ext:messages>
	<input type="hidden" value="${success }" id="rs11" />
	<div class="modal-body">
		<div class="row-fluid">
			<label class="span12 radio inline"><form:radiobutton path="optionType" value="1" id="radioSendNow"/>Send now</label>
		</div>
		<div class="row-fluid">
			<label class="span2 radio inline"><form:radiobutton path="optionType" value="0" id="radioSetCalendar"/>Set calendar</label>
			<div class="settime" style="position: absolute; margin-left: 100px;">
				<fmt:formatDate var="var_schedule" value="${bean.entity.schedule}" pattern="${sessionScope.formatDate} HH:mm" />
				<ext-form:input-date-time path="entity.schedule" id="userDate" inputId="userDate" value="${var_schedule}" />
			</div>
			
<!-- 			<div class="span3"> -->
<%-- 				<fmt:formatDate var="dateValue1" value="${bean.entity.schedule}" pattern="yyyy-MM-dd" /> --%>
<%-- 				<form:input type="date" path="dateSchedule" value="${dateValue1 }" /> --%>
<!-- 			</div> -->
<!-- 			<div class="span3"> -->
<%-- 				<fmt:formatDate var="dateValue2" value="${bean.entity.schedule}" pattern="HH:mm" /> --%>
<%-- 				<form:input type="time" path="timeSchedule" value="${dateValue2 }" /> --%>
<!-- 			</div> -->

		</div>
	</div>
	<div class="modal-footer text_center" >
		<spring:message var="msg_btn_save" code="banner.btn.store"></spring:message>
		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		
		<button type="button" class="btn_search_general" id="saveMessageTemplate"
			name="action" onclick="submitPopup();">${msg_btn_save}</button>
		<button type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true">${msg_btn_close }</button>
	</div>
	
	<form:hidden path="entity.templateId" />
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
		$("#eBody").mask('Processing...');
		$.ajax({
			url : '${url}/push_message/list_template/edit',
			data : $('#push_message_edit').serialize(),
			dataType : 'html',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#eBody").unmask('Processing...');
				$("#previewDetailPopup").html(data);
				
				var checkSuccess = $("#rs11").val();
				if (checkSuccess != "") {
					$('#saveMessageTemplate').css({'pointer-events':'none', 'cursor':'default', 'opacity': '0.6'});
				}
				
				setReloadyesorno(1);
			}
		});
	}
	
	function deleteItems() {
		confirmMessage('${msg.save.success}', deleteCallBack);
	}
	function deleteCallBack(result) {
		if (result) {
			$("#push_message_edit").submit();
		}
	}

	
</script>