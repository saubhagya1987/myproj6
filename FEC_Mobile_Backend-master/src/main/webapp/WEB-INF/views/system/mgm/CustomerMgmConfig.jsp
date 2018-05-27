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
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<spring:message var="msg_deleteItemQuestion"
	code="msg.alert.delete.questionitem"></spring:message>
	
<!-- start title -->
<div class="title_top">
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				<spring:message var="mgm_config"
					code="systemconfig.field.mgm.config"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"> <c:out
							value="${menu_admin }"></c:out></a> <span> > </span> <a
						onclick="backSystemConfig()" class="Color_back"> <c:out
							value="${mgm_config }"></c:out></a>
				</h4>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span12 title_h2">
			<h2>
				<spring:message var="mgm_config" code="systemconfig.field.mgm.config"></spring:message>
				<c:out value="${mgm_config }"></c:out>
			</h2>
		</div>
	</div>

	<form:form method="POST" id="search_form"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
<%-- 		<ext:messages bean="${bean}"></ext:messages> --%>

		<div class="row-fluid">
			<div class="accordion-group">
				<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span2 title1">
							<h3>
								<spring:message code="mgm.config.title"></spring:message>
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
								<ext-form:input-text path="entity.mgmPoint" cssInput="span10"
									labelCode="mgm.config.point"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="entity.xchangeValue" cssInput="span10"
									labelCode="mgm.config.xchange"></ext-form:input-text>
							</div>
						</div>
						<div class="row-fluid">
							<div class="control-group span6">
								<label class="control-label"> <spring:message
										code="mgm.config.value.type" />
								</label>
								<div class="controls">
									<form:select id="messageType" cssClass="span10"
										path="entity.valueType">
										<form:option value="">
											<spring:message code="msg.select"></spring:message>
										</form:option>
									</form:select>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="control-group span6">
								<ext-form:input-date path="entity.startDate"
 									id="var_startDate_id" value="" cssInput="span12" 
 									labelCode="cms.startDate" required="true" /> 
							</div>
							<div class="control-group span6">
								<ext-form:input-date path="entity.endDate"
 									id="var_endDate_id" value="" cssInput="span12" 
 									labelCode="cms.endDate" required="true" /> 
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
						
					<spring:message var="msg_buttonSave" code="button.save"></spring:message>
					<button type="button"
						onclick=""
						class="btn_search_general" name="search">${msg_buttonSave }</button>
				</div>

			</div>
		</div>
	</form:form>

</div>

<script type="text/javascript">
// 	$(document).ready(function() {
// 		$('#action').val("search");
// 		$('#customerPopup').bind('hide', function () {
// 			if($("#reloadyesorno").val()=="1"){
// 				document.location.href = "${url}/master_data/customer/list";
// 				/* $("#reloadyesorno").val(""); */
// 			}
// 		 });
// 	});
	function onRenew() {
		window.location.href = "${url}/system/mgm_config";
	}
</script>