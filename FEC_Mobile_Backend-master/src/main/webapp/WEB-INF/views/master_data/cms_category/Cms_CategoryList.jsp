<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg_deleteQuestion" code="msg.alert.delete.question"></spring:message>
<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
		$("#reset").click(function() {
			reset();
		});
		$('#previewDetailPopup').bind('hide', function () {
			if($("#reloadyesorno").val()=="1"){
				document.location.href = "${url}/master_data/cms_category/list";
				/* $("#reloadyesorno").val(""); */
			}
		 });
	});
	function deleteCallBack(result) {
		if (result) {
			$('#action').val("delete");
			$("#search_form").submit();
		}
	}
	function deleteItem() {
		confirmMessage('${msg_deleteQuestion}', deleteCallBack);
	}
	function newItem() {
		document.location.href = "${url}/master_data/cardtype/edit";
	}
	function deleteAction(id) {confirmMessage('${msg_deleteQuestion}',
				function(result) {
					if (result) {
						document.location.href = "${url}/master_data/cardtype/delete?entity.cardTypeId="
								+ id;
					}
				});
	}

	function showEquipPopup() {
		$('#previewDetailPopup').modal('show');
	}

	function showTextVal(showPopup,id) {
		$.ajax({
			url : '${url}/master_data/cms_category/cms_categoryEdit',
			data : {
				addedIds : $('#addedIds').val(),
				codeSearchPopup : $('#codeSearchPopup').val(),
				cms_categoryId : id
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

	function setReloadyesorno(index){
		$("#reloadyesorno").val(index);
	}

	function showTextValView(showPopup,id) {
		$.ajax({
			url : '${url}/master_data/cms_category/cms_categoryView',
			data : {
				addedIds : $('#addedIds').val(),
				codeSearchPopup : $('#codeSearchPopup').val(),
				cms_categoryId : id
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

	function backCategory() {
		document.location.href = "${url}/master_data/cms_category/list";
	}
	function back() {
		document.location.href = "";
	}
	
</script>
<!-- start title -->
<div class="title_top" >
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_cms" code="menu.cms"></spring:message>
			<spring:message var="menu_cms_category" code="cms.category"></spring:message>
			<spring:message var="menu_cms_category_list" code="cmscategory.list"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"> <c:out value="${menu_cms }"></c:out></a>
				<span> > </span>
				<a onclick="backCategory()" class="Color_back"> <c:out value="${menu_cms_category}"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_cms_category_list}"></c:out></span>
			</h4>
		</div>	
		
		<div class="span6" >
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>

						<sec:authorize access="!hasAnyRole('R010')">
							<li class="new"><a onclick="showTextVal(true)" title="Add new"><span class="new_text"></span></a></li>
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
			<div class="span6 title_h2 title">
				<spring:message var="msg_List" code="cmscategory.list"></spring:message>
				<h2>${msg_List}</h2>
			</div>
			</div>
	
	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" id="search_form_CMSCategory" cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
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
			<input id="reloadyesorno"  type="hidden"/>
		<div id="collapseOne" class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid search" style="background: White">
						<div class="span12 input-append">
							<spring:message var="msg_Category" code="search.cms.Category"></spring:message>
							<form:input path="searchField" cssClass="span3" placeholder="${msg_Category}" />
							<form:hidden path="action" id="action" value="search" />
							<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
							<button type="button" onclick="submitAndSetHiddenVal('search_form_CMSCategory',{'page':'1','maxPage':'5'})" class="btn_search_general" name="search">${msg_buttonSearch }</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		</div>

		<div id="previewDetailPopup" class="modal hide fade assetPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-width="1700px">
			<div class="modal-body" style="margin-left: 50px;">
			</div>

		</div>





		<!-- start table -->
		<div class="row-fluid row-align">
			<div class="span12">
				<table class="table table-bordered table-hover out-tbl">
					<thead>
						<tr>
							<td colspan="11" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title">
									<h3>
											<spring:message code="pop.result"></spring:message>
										</h3>
										</div>
									<div class="span6">
										<ext:pagination bean="${bean}" maxPage="5"
											formId="search_form_CMSCategory"></ext:pagination>
									</div>
								</div>
							</td>
						</tr>
						<tr>
						
							<spring:message var="msg_stt" code="hobby.Stt"></spring:message>
							<spring:message var="msgcms_category_Code" code="hobby.Code"></spring:message>
							<spring:message var="msg_name" code="hobby.Name"></spring:message>
							<spring:message var="msg_description" code="hobby.Description"></spring:message>
							<spring:message var="msg_status" code="hobby.Status"></spring:message>
							<th class="w50_stt">${msg_stt}</th>
							<th><ext:column-sort bean="${bean }" path="code" formId="search_form_CMSCategory" fieldName="${msgcms_category_Code}"></ext:column-sort></th>
							<th><ext:column-sort bean="${bean }" path="name" formId="search_form_CMSCategory" fieldName="${msg_name}"></ext:column-sort></th>
							<th><ext:column-sort bean="${bean }" path="description" formId="search_form_CMSCategory" fieldName="${msg_description}"></ext:column-sort></th>
							<th>${msg_status}</th>
							<th class="table-actions"><spring:message code="actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="index" value="0"></c:set>
						<c:forEach var="cms_category" items="${bean.listResult }" varStatus="i">
							<tr id="tr${index}">
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>

								<td><c:out value="${cms_category.code }"></c:out> <input type="hidden" id="equipIds${index}" value="${cms_category.cms_categoryId}" /> <c:set var="equipIds"
										value="${equipIds},${cms_category.cms_categoryId}"></c:set></td>
								<td><c:out value="${cms_category.name }" /></td>
								<td><c:out value="${cms_category.description }" /></td>

								<c:if test="${cms_category.statusTable.status_tableId==1}">
									<spring:message var="msg_Active" code="status.Active"></spring:message>
									<td class="text_center"><c:out value="${msg_Active}" /></td>


								</c:if>

								<c:if test="${cms_category.statusTable.status_tableId==2}">
									<spring:message var="msg_Inactive" code="status.Inactive"></spring:message>
									<td class="text_center"><c:out value="${msg_Inactive}" /></td>


								</c:if>
								<td class="table-actions">
								<spring:message var="icon_edit"
										code="icon.edit"></spring:message> <spring:message
										var="icon_view" code="icon.view"></spring:message> 
								<sec:authorize access="!hasAnyRole('R010')">
				<a onclick="showTextVal(true,${cms_category.cms_categoryId})" title="${icon_edit }"> 
								<i class="bms-icon-edit"></i></a>
				</sec:authorize>
								
								 <a onclick="showTextValView(true,${cms_category.cms_categoryId})" title="${icon_view }"> <i class="bms-icon-view"></i></a>
							</tr>
							<c:set var="index" value="${index + 1}"></c:set>
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
										<ext:pagination bean="${bean}" maxPage="5" formId="search_form_CMSCategory"></ext:pagination>
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

