<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script src="${url}/static/js/bootstrap-tag-cloud.js"></script>
<link href="${url}/static/css/bootstrap-tag-cloud.css" rel="stylesheet">
<script src="${url}/static/js/bootstrap-timepicker.js"></script>
<link href="${url}/static/css/bootstrap-timepicker.min.css"
	rel="stylesheet">
<spring:message var="system_1" code="systemconfig.field.systemconfig"></spring:message>
<spring:message var="system_2" code="systemconfig.field.systemparam"></spring:message>
<spring:message var="system_3"
	code="systemconfig.field.transactionparam"></spring:message>
<spring:message var="system_4" code="systemconfig.field.urlparam"></spring:message>
<spring:message var="system_5" code="systemconfig.field.respository"></spring:message>
<spring:message var="system_6" code="systemconfig.field.asset"></spring:message>
<spring:message var="system_7" code="systemconfig.field.process"></spring:message>

<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<c:out value="${system_1 }"></c:out>
				</h2>
			</div>
		</div>
	</div>
</div>
<div class="container-fluid unit_bg_content">
	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" cssClass="form-horizontal"
		modelAttribute="bean">
		<div class="accordion-inner">
			<div class="input-area">
				<div class="row-fluid border_bottom_red">

					<div class="title">
						<h3>
							<c:out value="${system_2 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.pageRow"> <spring:message
									code="systemconfig.field.pagerow"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.pageRow" cssClass="span10"
									itemValue="entity.pageRow">
									<form:option value="5">5</form:option>
									<form:option value="10">10</form:option>
									<form:option value="15">15</form:option>
									<form:option value="20">20</form:option>
									<form:option value="25">25</form:option>
									<form:option value="30">30</form:option>
									<form:option value="50">50</form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.date"> <spring:message
									code="systemconfig.field.date"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.date" cssClass="span10"
									itemValue="entity.date">
									<form:option value="dd/MM/yyyy">dd/mm/yyyy</form:option>
									<form:option value="dd-MM-yyyy">dd-mm-yyyy</form:option>
									<form:option value="MM/yyyy">mm/yyyy</form:option>
									<form:option value="MM-yyyy">mm-yyyy</form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.defaultLanguage">
								<spring:message code="systemconfig.field.defaultlanguage"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.defaultLanguage" cssClass="span10">
									<form:option value="vi">
										<spring:message code="systemconfig.field.vi" />
									</form:option>
									<form:option value="en">
										<spring:message code="systemconfig.field.en" />
									</form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-number-date id="value2" required="true"
							labelCode="systemconfig.field.fromyear" cssInput="span10"
							path="entity.formYear" maxlength="4"></ext-form:input-number-date>

					</div>
					<div class="span6">
						<ext-form:input-number-date id="value3" required="true"
							labelCode="systemconfig.field.toyear" cssInput="span10"
							path="entity.toYear" maxlength="4">
						</ext-form:input-number-date>
					</div>
				</div>

				<div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_4 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text required="true"
							labelCode="systemconfig.field.urldef" cssInput="span10"
							path="entity.urlDefault">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailHost"
							required="true" cssInput="span10" path="entity.emailHost"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailPort"
							required="true" cssInput="span10" path="entity.emailPort">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailadres"
							required="true" cssInput="span10" path="entity.emailDefault"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailname"
							required="true" cssInput="span10" path="entity.emailDefaultName">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailPassword"
							id="emailPassword" required="true" cssInput="span10"
							path="entity.emailPassword"></ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6" style="margin-left: 175px">
						<input type="checkbox" style="margin: 0 0 0 5px"
							onchange="document.getElementById('emailPassword').type = this.checked ? 'text' : 'password'">
						<span>&nbsp;Show</span>
						<script type="text/javascript">
							document.getElementById('emailPassword').type = 'password';
						</script>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.userldap"
							required="true" cssInput="span10" path="entity.userldap">
						</ext-form:input-text>
					</div>
					<div class="span6">
						<label class="control-label"> 
							<spring:message code="systemconfig.field.pswldap"/>
						</label>
						<div class="controls">
							<input type="password" class="span10" name="entity.pswldap" >
						</div>
					</div>
				</div>
				
				<div class="row-fluid" style="display: none;">
					<div class="control-group">
						<label class="control-label"> <spring:message
								code="systemconfig.field.emailBatch"></spring:message> <span
							class="required">*</span>
						</label>
						<div class="controls">
							<div class="span6">
								<div class="row-fluid">
									<label class="radio inline"> <form:radiobutton
											path="entity.emailOption" value="1" /> <spring:message
											code="systemconfig.field.emailSchedule"></spring:message>
									</label>

									<div class="bootstrap-timepicker" id="tag-info">
										<input id="timepicker1" type="text" class="input-small span6">
										<button class="btn btn-info" id="addTime" type="button"
											style="margin-left: 5px;">
											Add <i class="icon-plus"></i>
										</button>
									</div>

									<ul id="tag-cloud">

									</ul>
									<form:hidden path="entity.emailSchedule" id="emailSchedule" />

									<script type="text/javascript">
										$('#timepicker1').timepicker({
											minuteStep : 5,
											showInputs : false,
											disableFocus : true
										});
									</script>
								</div>
								<div class="row-fluid">
									<form:checkbox path="entity.checkLimmitEmail" value="1" />
									<label class="checkbox inline"> <spring:message
											code="systemconfig.field.limitEmail"></spring:message> <ext-form:input-number
											path="entity.limmitEmail" isGrid="true" id="limmitEmail"
											csswidth="30" cssInput="text-right"></ext-form:input-number>
									</label>
								</div>
							</div>
							<div class="span6">
								<label class="radio inline"> <form:radiobutton
										path="entity.emailOption" value="0" />
									<spring:message code="systemconfig.field.SendImmediately"></spring:message>
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid border_bottom_red" style="display: none;">
					<div class="title">
						<h3>
							<c:out value="${system_6 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid" style="display: none;">
					<div class="span6">
						<ext-form:input-text
							labelCode="systemconfig.field.unused.longtime" required="true"
							cssInput="span10" path="entity.notUsedLongTime"></ext-form:input-text>
					</div>
					<div class="span6"></div>
				</div>

				<div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_5 }"></c:out>
						</h3>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.tempfolder"
							required="true" cssInput="span10" path="entity.tempfolder">
						</ext-form:input-text>
					</div>
					<%-- <div class="span6">
						<ext-form:input-text
							labelCode="systemconfig.field.upload.dommain" required="true"
							cssInput="span10" path="entity.uploadDomain"></ext-form:input-text>
					</div> --%>
					
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.uploadfolder"
							required="true" cssInput="span10" path="entity.repository"></ext-form:input-text>
					</div>
				</div>
				
				<div id="addRepository" >
				
						<jsp:include page="addRepository.jsp" />
				</div>
				<%-- <div class="row-fluid border_bottom_red">

					<div class="title">
						<h3>
							<c:out value="${system_7 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processPr"> <spring:message
									code="purchase.requisition.title"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processPr" cssClass="span10"
									itemValue="entity.processPr">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processPr" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processPo"> <spring:message
									code="po.menu"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processPo" cssClass="span10"
									itemValue="entity.processPo">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processPo" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processTransfer"> <spring:message
									code="asset.transfer.menu"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processTransfer" cssClass="span10"
									itemValue="entity.processTransfer">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processTransfer" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processPayment"> <spring:message
									code="payment.title"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processPayment" cssClass="span10"
									itemValue="entity.processPayment">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processPayment" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processStationary"> <spring:message
									code="sr.title"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processStationary" cssClass="span10"
									itemValue="entity.processStationary">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processStationary" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processWriteOff"> <spring:message
									code="WriteOff.menu"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processWriteOff" cssClass="span10"
									itemValue="entity.processWriteOff">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processWriteOff" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="processAudit"> <spring:message
									code="AuditAsset.menu"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.processAudit" cssClass="span10"
									itemValue="entity.processAudit">
									<form:option value="">---NONE---</form:option>
									<c:forEach var="item" items="${lstProcess }">
										<form:option value="${item.processId }">${item.name }</form:option>
									</c:forEach>
								</form:select>
								<span class="help-inline"> <form:errors
										path="entity.processAudit" cssClass="error"></form:errors>
								</span>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<ext-form:input-number labelCode="po.limit.convert"
								cssInput="span10" path="entity.convertingAmountToPO"
								id="convertingAmountToPO"></ext-form:input-number>

						</div>
					</div>
				</div> --%>
				<div class="row-fluid text-center">
					<button type="submit" id="save" class="btn btn-info">
						<spring:message code="systemconfig.field.submit" />
					</button>
				</div>
			</div>
		
		</div>
		<spring:message code="team.field.enabled.active" var="message_active"/>
		<spring:message code="team.field.enabled.deactive" var="message_inactive"/>
		<input type="hidden" value="${message_active }" id="message_active"/>
		<input type="hidden" value="${message_inactive }" id="message_inactive"/>
	</form:form>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				var values = $('#emailSchedule').val().split(',');
				for ( var i in values) {
					if (values[i] != '') {
						$('<li class="tag-cloud">' + values[i] + '</li>')
								.appendTo("#tag-cloud");
					}
				}
				$('#addTime').click(function() {
					var value = ',';
					$('#tag-cloud li').each(function() {
						if (value.indexOf(',' + $(this).html() + ',') != -1) {
							$(this).remove();
						} else
							value += $(this).html() + ',';
					});
					$('#emailSchedule').val(value);
				});
			});

	function doAjaxPost() {
		// get the form values  
		var repositoryName = $('#repositoryName').val();
		var folderName = $('#folderName').val();

		$.ajax({
			type : "POST",
			url : "${url}/system/addRepository",
			data : "repositoryName=" + repositoryName + "&folderName=" + folderName,
			success : function(response) {
				if (response.status == "SUCCESS") {
					$('#repositoryName').val('');
					$('#folderName').val('');
					$('#error').hide('slow');
					$('#info').show('slow');
					var rowCount = $('#tblRoot tr').length;
					var message_active = $('#message_active').val();
					var message_inactive = $('#message_inactive').val();
					alert(message_active);
					var tdActive = "<td class='text_center'><a href='#'>"+message_inactive+"</a></td>"
					if(response.result.active == true) {
						tdActive = "<td class='text_center'><a href='#'>"+message_active+"</a></td>"
					} 
					$("#tblRoot > tbody").append(
							"<tr><td  class='w50_stt text_center'>" + rowCount
									+ "</td><td>" + response.result.repositoryName
									+ "</td><td>" + response.result.folderName
									+ "</td><td>" + response.result.totalSpace
									+ "</td><td>" + response.result.freeSpace
									+ "</td>" + tdActive
									+ "</tr>");
					$("#addRepository").load();
				} else {
					errorInfo = "";					
					$('#error').html(errorInfo);
					$('#info').hide('slow');
					$('#error').show('slow');
				}
				
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	
	}
	function doActive(id) {
		var message_active = $('#message_active').val();
		var message_inactive = $('#message_inactive').val();
		$.ajax({
			type : "POST",
			url : "${url}/system/activeRepository",
			data : "id=" + id,
			success : function(response) {
				var tdActive = message_inactive;
				if(response.result.active == true) {
					tdActive = message_active;
				} 
				$("#activeRep_"+id).html(tdActive);
				
				
			},
			error : function(e) {
				
			}
		});
	}
</script>

