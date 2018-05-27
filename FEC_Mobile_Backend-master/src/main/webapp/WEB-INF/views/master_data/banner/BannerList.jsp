<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<spring:message var="msg_deleteItemQuestion"
	code="msg.alert.delete.questionitem"></spring:message>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>	
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/jcarousel.css" />
<script type="text/javascript" src="${url}/static/js/jcarousel.jquery.js"></script>
<script type="text/javascript" src="${url}/static/js/jcarousel.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
	});
	
	function deleteItems(){
		confirmMessage('${msg_deleteQuestion}',deleteCallBack);
	}
	function newItem() {
		document.location.href = "${url}/master_data/banner/edit";
	}
	function viewItem() {
		document.location.href = "${url}/master_data/banner/view";
	}
	function deleteItem(id){
		confirmMessage('${msg_deleteItemQuestion}',function(result){
			if(result)
				document.location.href = "${url}/master_data/banner/delete?id="+id;
		});
	}
		
	function popupEditCustomer(id) {
		ajaxUrl = '${url}/master_data/banner/edit?id='+id;
		$.ajax({
			url : ajaxUrl,
			success : function(result) {
				$("#customerPopup").html(result);
				$("#customerPopup").modal('show');
			}
		});
	}

	function popupViewCustomer(id) {
		ajaxUrl = '${url}/master_data/banner/view?id='+id;
		$.ajax({
			url : ajaxUrl,
			success : function(result) {
				$("#customerPopup").html(result);
				$("#customerPopup").modal('show');
			}
		});
	}
	
	
	function closePOPUP(){
		 $("#customerPopup").modal('hide');		
	}
	function viewImage(id){
		ajaxUrl = '${url}/master_data/banner/view_image?id='+id;
		$.ajax({
			url : ajaxUrl,
			success : function(result) {
				$("#bannerPopup").html(result);
				$("#bannerPopup").modal('show');
			}
		});
	}
	function onRenew() {
		window.location.href = "${url}/master_data/banner/list";
	}

	function backBanner() {
		document.location.href = "${url}/master_data/banner/list";
	}
	function back() {
		document.location.href = "";
	}
</script>
<!-- start title -->
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_banners" code="menu.banners"></spring:message>
			<spring:message var="menu_banner_list" code="banner.list.title"></spring:message>
			<h4  style="padding: 8px 0 0 10px;">
				<a onclick="onRenew()" class="Color_back"><c:out value="${menu_banners }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_banner_list }"></c:out></span>
			</h4>
		</div>	
		
		<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>
						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>
						<sec:authorize access="!hasAnyRole('R010')">
				<li class="new"><a href="javascript:;" onclick="newItem()"><span class="new_text"></span></a></li>
				</sec:authorize>
						
					</ul>
				</div>
			</div>
</div>
</div>	
</div>
<!-- and title -->
<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="list_title" code="banner.list.title"></spring:message>
											<c:out value="${list_title }"></c:out>
				</h2>
			</div>			
		</div>
	
	<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form"
		modelAttribute="bean">

		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid ">
		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="search.area"></spring:message>
						</h3>
					</div>
					
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse"
										data-parent="#accordion2" href="#collapseOne"><i
										class="bms-icon-accordion-down bms-icon-accordion"></i></a>
					</div>
					
					
				</div>
			</div>
		</div>
		<spring:message var="msg_btn_search" code="button.search"></spring:message>
		<div id="collapseOne" class="accordion-body in collapse border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid">						
						<div class="span12">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">
										<spring:message code="banner.field.category.list" />
									</label>
									<div class="controls">
										<form:select path="category" class="span10" >
											<form:option value=""><spring:message code="msg.all"/> </form:option>
											<c:forEach items="${categoryBanner}" var="item">
											 	<form:option value="${item.key}">${item.value}</form:option>
											</c:forEach>
										</form:select>
									</div>	
								</div>	
							</div>
							<div class="span6">
								<ext-form:input-text path="bannerCode" cssInput="span10" labelCode="banner.field.code"></ext-form:input-text>
							</div>
						</div>	
						<div class="row-fluid">		
						<div class="span12">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">
										<spring:message code="customer.field.status" />
									</label>
									<div class="controls">
										<form:select path="status" class="span10">
											<form:option value=""><spring:message code="msg.all"></spring:message> </form:option>
											<form:option value="1"><spring:message code="customer.field.status.active"></spring:message> </form:option>
											<form:option value="-1"><spring:message code="customer.field.status.inactive"></spring:message></form:option>
										</form:select> 
									</div>									
								</div>
							</div>
						</div>
						</div>
					</div>	
				</div>
			</div>
		
		<div class="text-center">
		<spring:message var="apply_now_renew" code="apply.now.renew"></spring:message>
			<input type="button" class="btn_review" onclick="onRenew()"name="renew" value="${apply_now_renew }" style="margin: 6px" />
			
			<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
			<input type="hidden" id="action" name="action" value="search" />
			<button type="button" onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})" class="btn_search_general" name="search">${msg_buttonSearch }</button>
		</div>
		</div>
		</div>
		<!-- start table -->
		<div class="row-fluid row-align">
			<div class="span12 " >
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
							<th><spring:message code="banner.field.banner" /></th>
							<th>
								<spring:message var="msg_category" code="banner.field.category.list" />
								<ext:column-sort bean="${bean }" path="category" formId="search_form" fieldName="${msg_category }"></ext:column-sort>
							</th>
							<th>
								<spring:message var="msg_code" code="banner.field.code" />
								<ext:column-sort bean="${bean }" path="bannerCode" formId="search_form" fieldName="${msg_code }"></ext:column-sort>	
							</th>
							<th><spring:message var="msg_active_from" code="banner.field.active.from" /> 
								<ext:column-sort bean="${bean }" path="activeFromDate" formId="search_form" fieldName="${msg_active_from }"></ext:column-sort>
							</th>
							<th><spring:message var="msg_active_to" code="banner.field.active.to" /> 
								<ext:column-sort bean="${bean }" path="activeToDate" formId="search_form" fieldName="${msg_active_to }"></ext:column-sort>
							</th>
							<th><spring:message code="customer.field.status" /> </th>
							<th class="table-actions"><spring:message code="actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="_item" items="${bean.listResult }"	varStatus="i">
							<tr>
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
								<td class="img_table_w">
									<c:if test="${_item.imagePath != null && _item.imagePath != ''}">
										<img src='${url}/ajax/download?fileName=${_item.imagePath}' onclick="popupSlideshow('${_item.bannerId}')">
									</c:if>
								</td>
								<td> 
									<c:out value="${_item.category }"></c:out>
								</td>
								<td> 
									<c:out value="${_item.bannerCode }"></c:out>
								</td>
								<td class="text_center" >
									<fmt:formatDate value="${_item.activeFromDate}" pattern="${sessionScope.formatDate} HH:mm" var="activeFromDate"/>
									<c:out value="${activeFromDate }"></c:out>
								</td>
								<td class="text_center">
									<fmt:formatDate value="${_item.activeToDate}" pattern="${sessionScope.formatDate} HH:mm" var="activeToDate"/>
									<c:out value="${activeToDate }"></c:out>
								</td>
								<td class="text_center" >
									<c:if test="${_item.status == 1}">
										<spring:message code="customer.field.status.active" />	
									</c:if>	
									<c:if test="${_item.status == -1}">
										<spring:message code="customer.field.status.inactive" />	
									</c:if>	
								</td>
								<td class="table-actions">
								<spring:message var="icon_edit"
										code="icon.edit"></spring:message> <spring:message
										var="icon_view" code="icon.view"></spring:message> 
									<sec:authorize access="!hasAnyRole('R010')">
				<a href="${url}/master_data/banner/edit?id=${_item.bannerId }" title="${icon_edit }"><i class="bms-icon-edit"></i></a> 
				</sec:authorize>
									<a href="${url}/master_data/banner/view?id=${_item.bannerId }"title="${icon_view }" ><i class="bms-icon-view"></i></a> 
									
									<%-- <a href="${url}/master_data/banner/view?id=${_item.bannerId }" title="${icon_view}"><i class="bms-icon-view"></i></a> --%>
									<%-- <c:if test="${!_item.active }">
										<a href="#" onclick="deleteItem('${_item.bannerId }')" ><i class="bms-icon-delete"></i></a>
									 </c:if>  --%>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="11" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title">
										<div class="pagination1 pagination1-small pagination1-right pagination1-left-total-css" >
											<spring:message var="msg_Found" code="page.Found" />
											<spring:message var="msg_records" code="page.records" />
											<spring:message var="msg_page" code="page.page" />
										
											&nbsp;<span class="text">${msg_Found }</span><span class="number">${bean.total} </span><span class="text">${msg_records }</span><span class="number"> ${bean.totalPage}</span> <span class="text">${msg_page }</span>
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
		</form:form>	
</div>

<div id="bannerPopup" class="modal hide fade bannerPopup"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" data-width="700" style="width: 800px; margin-left: -270px;height: 440px;">
	
</div>

<div id="popupSlideShow" style="width: 1000px;height: 560px; left: 50%; margin-left: -500px; margin-top: -45px;" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="projectModalLabel" aria-hidden="true" data-width="1000"></div>

<script type="text/javascript">

function popupSlideshow(id){
	$.ajax({
		url : '${url}/master_data/banner/getPopupSlideshow',				
		data: {bannerId: id},
		type : 'GET',
		cache : false,				
		success : function(result) {
			$("#popupSlideShow").html(result);				
			$("#popupSlideShow").modal('show');	
			
			/* setTimeout(function(){
				$(".navigation li").eq(elm).click();	
			},300) ; */
		}
	});		
}
</script>
