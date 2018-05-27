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

<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form"
		modelAttribute="bean" enctype="multipart/form-data">
		
<!-- show menu -->
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			
			<div class="span6">
				<spring:message var="notification" code="menu.messages.notification"></spring:message>
				<spring:message var="push_message" code="push.message"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"><c:out value="${notification }" /></a> 
					<span> > </span> 
					<span class="Colorgray"><c:out value="${push_message }" /></span> 
					<span> > </span> 
					<span class="Colorgray"><spring:message code="push.message.import"></spring:message></span>
				</h4>
			</div>
			<div class="span6">
				<div hidden="true">
					<form:input id="fileData" path="fileUpload" type="file" onchange="setValue();"/>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- content -->
<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span12" id="messageDiv">
			<ext:messages bean="${bean }"></ext:messages>
		</div>
		<div class="span12 title_h2">
			<h2>
				<spring:message code="push.message.import.title" />
			</h2>
		</div>
	</div>
		
	<div class="row-fluid">
		<div class="accordion-group">
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
						<div class="span10">
							<ext-form:input-text path="templateSubject" id="templateSubject" cssInput="span10" labelCode="push.message.import.template.subject" placeholder="Input subject"></ext-form:input-text>
						</div>
					</div>	
					<div class="row-fluid">
						<div class="control-group span10">
							<label class="control-label">
								<spring:message code="push.message.import.message.type" />
							</label>
							<div class="controls">
								<form:select id="messageType" cssClass="span10"
									path="messageType">
									<form:option value=""><spring:message code="msg.select"></spring:message> </form:option>
										<c:forEach items="${bean.messageTypeDao}" var="j">
											<form:option value="${j.code }">${j.name }</form:option>
										</c:forEach>
								</form:select>
							</div>									
						</div>
					</div>	
					<div class="row-fluid">
						<div class="control-group span10">
							<label class="control-label">
								<spring:message code="push.message.import.select" />
							</label>
							<div class="controls">
								<form:input id="fileName" path="pathFile" cssClass="span10" disabled="true" placeholder="File name"/>
								<button type="button" onclick="openPopupUpload();" class="btn_search_general" name="import" style="margin-left: 6px">
									<spring:message code="push.message.import.button" />
								</button>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="control-group span10">
							<label class="control-label">
							</label>
							<div class="controls">
								<a href="javascript:void(0);" onclick="downloadImportMessage();">
									<h5 style="color: blue; font-style: italic; font-weight: bold;">
										<spring:message code="push.message.import.alert" />
									</h5>
								</a>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="control-group span10">
							<label class="control-label">
								<spring:message code="push.message.import.option.send" />
							</label>
							<div class="controls">
								<label class="span3 radio inline"><form:radiobutton path="optionType" value="1" checked="checked" onclick="hideTime()" id="sendnow" />Send now</label>
								<label class="span2 radio inline"><form:radiobutton path="optionType" value="0" onclick="showTime()" id="radioCalendar" />Set calendar</label>
								<div class="settime" style="position: absolute; margin-left: 141px;">
								
									<fmt:formatDate var="var_schedule" value="${bean.entity.schedule}"	pattern="${sessionScope.formatDate} HH:mm" />
									<ext-form:input-date-time path="entity.schedule" id="dateSchedule" inputId="dateScheduleText" value="${var_schedule}" />
							
								
<%-- 									<label class="span3"><form:input type="date" path="dateSchedule" style="width:166px;" id="userDate" max="3000-12-31" min="1980-01-01" /></label> --%>
<%-- 									<label class="span2"><form:input type="time" path="timeSchedule" style="width:124px;" id="userTime" /></label> --%>
								</div>
							</div>
						</div>
					</div>
					<br />
					<div class="row-fluid text-center">
						<spring:message var="apply_now_renew" code="apply.now.renew" />
						<spring:message var="msg_buttonSave" code="button.save" />
						<input type="button" class="btn_review" onclick="onRenew()" name="" value="${apply_now_renew }" style="margin: 6px" />
						<input type="button" class="btn_search_general" onclick="onSave()" name="" value="${msg_buttonSave }" />
					</div>
				</div>
			</div>
		</div>
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
									<%-- <div class="span6">
										<ext:pagination bean="bean" maxPage="5"
											formId="search_form"></ext:pagination>
									</div> --%>
								</div>
							</td>
						</tr>
						<tr>
							<th><spring:message code="msg.no"/></th>
							<th>
								<spring:message code="customer.field.card.no" />
							</th>
							<th>
								<spring:message code="customer.field.cell.phone" />
							</th>
							<th>
							<spring:message code="customer.messages.field.subject" />
							</th>
							<th>
							<spring:message code="cms.content" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="_item" items="${bean.messageImportLst }"	varStatus="i">
							<tr>
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
								
								</td>
								<td>
									<c:out value="${_item.customer.idCardNumber }"></c:out>
								</td>
								<td>
									<c:out value="${_item.cellphone }"></c:out>
								</td>
								<td>
									<c:out value="${_item.subject }"></c:out>
								</td>
								<td>
									<c:out value="${_item.content }"></c:out>
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

<script>
$(document).ready(function($) {
	if ($('#sendnow').attr('checked') == 'checked') {
		hideTime();
	}
});

/* function checkDate() {
	var inputDate = $('#userDate').val();
	if(!validateDate(inputDate)) {
        alert('Invalid Date');
    }
}

function validateDate(dtValue)
{
	var dtRegex = new RegExp(/\b\d{4}[\/-]\d{1,2}[\/-]\d{1,2}\b/);
	return dtRegex.test(dtValue);
} */



  function onSave() {

    $("#messageDiv").html("");
	var isHasError = false;
    
    if ($("#templateSubject").val().length === 0) {
      isHasError = true;
      $("#messageDiv")
              .append(
                      '<div class="alert alert-error fade in"> <button class="close" data-dismiss="alert" type="button">×</button> Template subject should not be empty. </div>');
    }

    if ($("#messageType").val().length === 0) {
      isHasError = true;
      $("#messageDiv")
              .append(
                      '<div class="alert alert-error fade in"> <button class="close" data-dismiss="alert" type="button">×</button> Messages Type is not null </div>');
    }
  	
   	if($("#radioCalendar").is(":checked")){
   		$.ajax({
			url : '${url}/push_message/checkDateScheduleText?entity.schedule=' + $("#dateScheduleText").val(),
			cache : false,
			type : "POST",
			async : false,
			success : function(resp) {
				if(resp.status != 'success'){
					isHasError = true;
					 $("#messageDiv")
		              .append(
		                      '<div class="alert alert-error fade in"> <button class="close" data-dismiss="alert" type="button">×</button>'+resp.message+'</div>');
				}
			}
		});
   	}
   
   
    if(isHasError){
      return;
    }

    $("#search_form").submit();

  }

  function onRenew() {
    window.location.href = "${url}/push_message/init";
  }

  function openPopupUpload() {
    $("#fileData").click();
  }

  function setValue() {
    var temp = $("#fileData").val();

    temp = temp.replace("C:\\fakepath\\", "")
    document.getElementById('fileName').value = temp;
  }

  function downloadImportMessage() {
    window.location.href = "${url}/push_message/downloadImportMessage";
    // 	$("#search_form").attr("action", "${url}/push_message/downloadImportMessage");
    // 	$("#search_form").submit();
  }

  function showTime() {
    $(".settime").show();
  }

  function hideTime() {
    $(".settime").hide();
  }

  /* function submitForm() {
   $("#search_form").attr("action", "${url}/push_message/import");
   $("#search_form").submit();
   } */
</script>