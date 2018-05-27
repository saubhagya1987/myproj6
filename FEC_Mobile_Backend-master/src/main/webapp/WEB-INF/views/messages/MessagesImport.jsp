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
	

	
<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
		$('#customerPopup').bind('hide', function () {
			if($("#reloadyesorno").val()=="1"){
				document.location.href = "${url}/master_data/customer/list";
				/* $("#reloadyesorno").val(""); */
			}
		 });
	});
	function setReloadyesorno(index){
		$("#reloadyesorno").val(index);
	}
	function downloadImportMessage(){
		$("#search_form").attr("action", "${url}/message/downloadImportMessage");
		$("#search_form").submit();
	}
	
	function openPopupUpload(){
		
		$("#fileData").click();
	}
	function setValue(){
		var temp = $( "#fileData" ).val();
		
		temp = temp.replace("C:\\fakepath\\", "")
		document.getElementById('fileName').value=temp; 
		importExcel();
	}
	function importExcel(){
	
		/* $("body").mask('<spring:message code="msg.loading"/>'); */
		$("#search_form").attr("action", "${url}/message/import");
		$("#search_form").submit();
	}
</script>
<style>



</style>

<!-- start title -->
<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form"
		modelAttribute="bean" enctype="multipart/form-data">
<div class="title_top">
	

	<div class="container-fluid">
		<div class="row-fluid">
			
			<div class="span6">
					<spring:message var="menu_customers" code="menu.customers"></spring:message>
					<spring:message var="customer_list" code="customer.list.title"></spring:message>
					<h4 style="padding: 8px 0 0 10px;">
					   <a onclick="back()" class="Color_back"> <c:out value="${menu_customers }"></c:out></a>
					   <span> > </span>
					   <span class="Colorgray"><c:out value="${customer_list }"></c:out></span>
					</h4>
			
			</div>
			<div class="span6">
				<div class="menu_images">
					
				</div>
				<div hidden="true">
				<form:input id="fileData" path="fileUpload" type="file" onchange="setValue();"/>
   				<input id="fileImportData" type="submit" />
				</div>
			</div>
			
			
		</div>
	</div>
</div>

<!-- and title -->
<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span12">
				<ext:messages bean="${bean}"></ext:messages>
			</div>
			<div class="span12 title_h2" style="margin-left: 5px;">
				<h2>
					<spring:message var="list_title" code="menu.message.import"></spring:message>
											<c:out value="${list_title }"></c:out>
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


					<div class="row-fluid" style="margin-top: 15px">
						<div class="span12" style="text-align: center;">
							<spring:message var="downloadTemp" code="message.import"></spring:message>
							<c:out value="${downloadTemp }"></c:out>
							<a href="javascript:void(0);" onclick="downloadImportMessage();"><img
								src="${url}/static/images/icon_acttact.png" /></a>
						</div>
							<div class="row-fluid" style="margin-top: 15px">
								<div class="span12" style="text-align: center;">
									<form:select id="messageType" cssClass="" path="messageType">
										<form:option value="">
											<spring:message code="message.import.null"></spring:message>
										</form:option>
										<c:forEach items="${bean.messageTypeDAO}" var="j">
											<form:option value="${j.code }">${j.name }</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="row-fluid search">
							<div class="span12" style="text-align: center;">
								<form:input id="fileName" path="pathFile" cssClass="span8" disabled="true" />
								<spring:message var="msg_buttonImport" code="pendingPayment.button.import"></spring:message>
								<sec:authorize access="!hasAnyRole('R010')">
								<button type="button" onclick="openPopupUpload();" class="btn_search_general"
									name="import">${msg_buttonImport }</button>
								</sec:authorize>
							</div>
						</div>

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
											<spring:message var="search_title" code="msg.search.title"></spring:message>
											<c:out value="${search_title }"></c:out>
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
<div id="customerPopup" class="modal hide fade feedbackPopup"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" data-width="800" style="width: 1100px; margin-left: -410px;height: 900px;">
	
</div>


