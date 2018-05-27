
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



<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	<spring:message var="title_list" code="account.change.password"></spring:message>
	<h3 >${title_list}</h3>
</div>
<div class="modal-body">
   <ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" id="popup_form" cssClass="form-horizontal" modelAttribute="bean">
		<div class="row-fluid">
		</div>

		<div class="span6">
			<div class="control-group">
				<label for="passwordOld" class="control-label"> <spring:message code="account.old.passsword"></spring:message>
				</label>
				<div class="controls">
					<input style="display: none;">
					<form:password path="passwordOld" cssClass="span3" id="passwordId" autocomplete="off" />
					<span class="help-inline"> <form:errors path="passwordOld" cssClass="error"></form:errors>
					</span>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label for="passwordNew" class="control-label"> <spring:message code="account.new.passsword"></spring:message>
				</label>
				<div class="controls">
					<input style="display: none;">
					<form:password path="passwordNew" cssClass="span3" id="passwordId2" autocomplete="off" />
					<span class="help-inline"> <form:errors path="passwordNew" cssClass="error"></form:errors>
					</span>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label for="passwordEnter" class="control-label"> <spring:message code="account.re.passsword"></spring:message>
				</label>
				<div class="controls">
					<input style="display: none;">
					<form:password path="passwordEnter" cssClass="span3" id="passwordId3" autocomplete="off" />
					<span class="help-inline"> <form:errors path="passwordEnter" cssClass="error"></form:errors>
					</span>
				</div>
			</div>
		</div>

		<div class="row-fluid">
			<!-- start table -->
				<div id="tableListEquipment">
					<div class="row-fluid">
						
					</div>
				</div>
			<!-- end table -->
		</div>		
	</form:form>
</div>
<div class="modal-footer text_center">
	<spring:message var="save_btn_msg" code="button.save"></spring:message>
	<input type="submit" value="${save_btn_msg}" id="saveEdit" class="btn btn-info" onclick="savePassword();"/>
	<spring:message var="return_btn_msg" code="button.return"></spring:message>
</div>			
<script type="text/javascript">
function savePassword(){
		var url = "";		
		url= '${url}/user/changePassword';		
		$.ajax({
			url: url,
			type: 'POST',
			data : $('#popup_form').serialize(),	
			success: function(data) {
				$("#changePasswordId").html(data);
				$("#changePasswordId").modal('show');
			}
		});
	}
</script>