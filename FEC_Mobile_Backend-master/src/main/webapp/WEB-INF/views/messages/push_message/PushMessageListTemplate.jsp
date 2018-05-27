<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
</script>

<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form"
		modelAttribute="bean">
		
<!-- show menu -->
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			
			<div class="span6">
				<spring:message var="notification" code="menu.messages.notification"></spring:message>
				<spring:message var="push_message" code="push.message"></spring:message>
				<spring:message var="list_template" code="push.message.list.template"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"><c:out value="${notification }" /></a> 
					<span> > </span> 
					<span class="Colorgray"><c:out value="${push_message }" /></span> 
					<span> > </span> 
					<span class="Colorgray"><c:out value="${list_template }" /></span>
				</h4>
			</div>
		</div>
	</div>
</div>

<!-- content -->
<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span12 title_h2">
			<h2>
				<spring:message code="push.message.list.template" />
			</h2>
		</div>
	</div>
	<ext:messages bean="${bean}"></ext:messages>
	<div class="row-fluid">
		<div class="accordion-group">
		<input id="reloadyesorno"  type="hidden"/>
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="button.import"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
			</div>
		</div>
		
		<spring:message var="msg_btn_search" code="button.search"></spring:message>

		<div id="collapseOne" class="accordion-body in collapse border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">
						<div class="span6">
							<ext-form:input-text path="templateSubject" cssInput="span10" labelCode="push.message.import.template.subject" placeholder="Input subject"></ext-form:input-text>
						</div>
						<div class="span6">
							<div class="control-group">
										<label class="control-label">
											<spring:message code="push.message.import.message.type" />
										</label>
								<div class="controls">
									<form:select id="messageType" cssClass="" path="messageType">
										<form:option value=""><spring:message code="msg.all"></spring:message> </form:option>
										<c:forEach var="j" items="${messageType }">
											<form:option value="${j.code }">${j.name }</form:option>
										</c:forEach>
									</form:select>
								</div>									
							</div>
						</div>
					</div>	
					<div class="row-fluid">
						<div class="span6">
							<fmt:formatDate var="dateFromSelected" value="${bean.formSendDate}" pattern="${sessionScope.formatDate}" />
							<ext-form:input-date path="formSendDate" placeholder="dd/mm/yyyy" id="dateFrom" value="${dateFromSelected }" labelCode="customer.messages.field.created.date.from"></ext-form:input-date>
						</div>
						<div class="span6">
							<fmt:formatDate var="dateFromSelected" value="${bean.toSendDate}" pattern="${sessionScope.formatDate}" />
							<ext-form:input-date path="toSendDate" placeholder="dd/mm/yyyy" id="dateto" value="${dateFromSelected }" labelCode="customer.messages.field.created.date.to"></ext-form:input-date>
						</div>
					</div>	
					<br />
					<div class="row-fluid text-center">
						<spring:message var="apply_now_renew" code="apply.now.renew" />
						<spring:message var="msg_buttonSearch" code="button.search" />
						<input type="button" class="btn_review" onclick="onRenew()" name="" value="${apply_now_renew }" style="margin: 6px" />
						<button type="button" onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})" class="btn_search_general" name="search">${msg_buttonSearch }</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="previewDetailPopup" class="modal hide fade assetPopup"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-width="1000">
	</div>
		
	<div class="row-fluid">
		<!-- start table -->
		<div class="row-fluid row-align">
			<div class="span12">
				<table class="table table-bordered table-hover out-tbl">
					<thead>
						<tr>
							<td colspan="12" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title">
										<h3>
											<spring:message code="msg.search.title"></spring:message>
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
							<th></th>
							<th>
								<spring:message code="push.message.import.template.subject" />
							</th>
							<th>
								<spring:message code="push.message.import.message.type" />
							</th>
							<th>
								<spring:message code="push.message.file.name" />
							</th>
							<th>
								<spring:message code="push.message.schedule" />
							</th>
							<th>
								<spring:message code="push.message.actions" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${bean.listResult}" varStatus="i">
							<tr>
								<td class="text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
								<td>
									<c:out value="${item.templateSubject}"></c:out>
								</td>
								<td class="text_center type" data-type="${item.messageType}">												
									${listMess[item.messageType] }
								</td>
								<td>
									<a href="javascript:void(0);" onclick="downloadFileName('${item.templateId }');">
									<h5 style="color: blue; font-style: italic; font-weight: bold;">
										<c:out value="${item.fileName }"></c:out>
									</h5>
								</a>
								</td>
								<td class="text_center"><fmt:formatDate
									value="${item.schedule}" pattern="${sessionScope.formatDate} hh:mm a" />
								</td>
								<td class="table-actions">
									<c:if test="${item.schedule < currentDate && item.status == true}">
										<spring:message var="icon_edit" code="icon.edit" /> 
										<spring:message var="icon_delete" code="icon.delete" /> 
										<a onclick="showTextVal(true,${item.templateId})" title="${icon_edit }" style="pointer-events: none;">
											<i class="bms-icon-edit" style="cursor: default; opacity: 0.6;"></i>
										</a>
										<a href="javascript:void(0)" onclick="deleteAction('${item.templateId }')" title="${icon_delete }" style="pointer-events: none;"> 
											<i class="bms-icon-delete" style="cursor: default; opacity: 0.6;"></i>
										</a>
									</c:if>
									<c:if test="${item.schedule >= currentDate && item.status == false}">
										<spring:message var="icon_edit" code="icon.edit" /> 
										<spring:message var="icon_delete" code="icon.delete" /> 
										<a onclick="showTextVal(true,${item.templateId})" title="${icon_edit }">
											<i class="bms-icon-edit"></i>
										</a>
										<a href="javascript:void(0)" onclick="deleteAction('${item.templateId }')" title="${icon_delete }"> 
											<i class="bms-icon-delete"></i>
										</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="12" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title" >
										<div class="pagination1 pagination1-small pagination1-right pagination1-left-total-css" >
											<spring:message var="msg_Found" code="page.Found" />
											<spring:message var="msg_records" code="page.records" />
											<spring:message var="msg_page" code="page.page" />
										
											&nbsp;
											
										</div>
									</div>
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
		<!-- and table -->
	</div>
</div>
</form:form>	

<spring:message var="msg_deleteQuestion" code="msg.alert.delete.question"></spring:message>
<script>
$(document).ready(function() {
	$('#previewDetailPopup').bind('hide', function () {
		if($("#reloadyesorno").val()=="1"){
			document.location.href = "${url}/push_message/list_template";
		}
	 });
});

function onRenew() {
	window.location.href = "${url}/push_message/list_template";
}

function deleteAction(id) {
	confirmMessage('${msg_deleteQuestion}', function(result) {
		if (result) {
			document.location.href = "${url}/push_message/list_template/delete?entity.templateId="+ id;
		}
	});
}

function showTextVal(showPopup,id) {
	$.ajax({
		url : '${url}/push_message/list_template/edit',
		data : {
			addedIds : $('#addedIds').val(),
			codeSearchPopup : $('#codeSearchPopup').val(),
			templateId : id
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

function downloadFileName(id) {
	$("#search_form").attr("action", "${url}/push_message/list_template/download?entity.templateId="+ id);
	$("#search_form").submit();
}

function setReloadyesorno(index){
	$("#reloadyesorno").val(index);
}
</script>