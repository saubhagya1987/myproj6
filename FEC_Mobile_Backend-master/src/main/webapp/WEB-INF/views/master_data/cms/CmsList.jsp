<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
		$("#reset").click(function() {
			reset();
		});

		$(".checkboxstatus").click(function(){
			var id= $(this).attr("key");
				$.ajax({
					url : '${url}/master_data/cms/updateActiviti',
					type : 'GET',
					data : {
						id :id,
						checked: $(this).is(":checked")
					},
				});
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

	function showTextVal() {
		$.ajax({
			url : '${url}/master_data/cms/list',
			data : {
				addedIds : $('#statustableId').val(),

			},
		});
	}

	

	function onRenew() {
		window.location.href = "${url}/master_data/cms/list";
	}

	function backCMS() {
		document.location.href = "${url}/master_data/cms/list";
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
			<spring:message var="menu_cms_list" code="cms.list"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()"class="Color_back"><c:out value="${menu_cms }"></c:out></a>
				<span> > </span>
				<a onclick="backCMS()"class="Color_back"><c:out value="${menu_cms}"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_cms_list}"></c:out></span>
			</h4>
		</div>
		
		<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>

						<sec:authorize access="!hasAnyRole('R010')">
				<li class="new"><a href="${url}/master_data/cms/new"> </a></li>
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
				<spring:message var="msg_List" code="cms.list"></spring:message>
				<h2 style="color: green">${msg_List}</h2>
			</div>			
		</div>
	
	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" id="search_form_CMS"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
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

		<div id="collapseOne" class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">

									<label for="type" class="control-label"> <spring:message code="cms.category" />
									</label>

									<div class="control">
										<form:select id="category.categoryId" cssClass="" path="category" style="margin-left:10px">
											<form:option value="-1">
												<spring:message code="msg.all" />
											</form:option>
											<c:forEach items="${bean.listCMSCategory}" var="j">
												<form:option value="${j.cms_categoryId}">${j.code}</form:option>
											</c:forEach>
										</form:select>

									</div>
								</div>
							</div>
							<div class="span6">
									<ext-form:input-text path="tag" cssInput="span7" labelCode="cms.tag"></ext-form:input-text>
							</div>
						</div>

						<div class="row-fluid">

							<div class="span6">
							
								<fmt:formatDate var="dateValue" value="${bean.createDayfrom}" pattern="${sessionScope.formatDate}" />
								<ext-form:input-date path="createDayfrom" value="${dateValue}"
									labelCode="customer.messages.field.created.date.from" id="dateValue"></ext-form:input-date>
							</div>


							<div class="span6">
								<fmt:formatDate var="dateValue" value="${bean.createDayto}" pattern="${sessionScope.formatDate}" />
								<ext-form:input-date path="createDayto" value="${dateValue}" labelCode="customer.messages.field.created.date.to"
									id="createDayto"></ext-form:input-date>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label for="type" class="control-label"> <spring:message code="cms.status" />
									</label>
									<div class="control-group">

										<form:select id="statustableId" cssClass="" path="status"  style="margin-left:10px">
											<form:option value="-1">
												<spring:message code="msg.all" />
											</form:option>
											<c:forEach items="${bean.listStatusTable}" var="j">
												<form:option value="${j.status_tableId}">${j.status_text}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>

						</div>
						<div class="row-fluid">
						<div class="span10 input-append">
							<form:hidden path="action" id="action" value="search" />
							<div class="text-center">
								<spring:message var="apply_now_renew" code="apply.now.renew"></spring:message>
								<input type="button" class="btn_review" onclick="onRenew()"
									name="renew" value="${apply_now_renew }" style="margin: 6px;margin-left: 170px;"  />

								<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
								<button type="button"
									onclick="submitAndSetHiddenVal('search_form_CMS',{'page':'1','maxPage':'5'})"
									class="btn_search_general" name="search" style="margin: 6px">${msg_buttonSearch}</button>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
</div>


		<div class="accordion" id="accordion2">

			<div class="accordion-group"></div>

			<div class="accordion-inner">

				<div class="input-area">
					<div class="span12 input-append"></div>

				</div>
			</div>

		</div>

		<div class="previewPopup margin_bottom_require">
			<div id="previewDetailPopup" class="modal hide fade assetPopup"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-width="1000" style="background: green">
				<div class="modal-footer text_center" style="background: green">
					<div class="span2">
						<spring:message var="msg_CMS_addNew" code="cmscategory.AddNew"></spring:message>
						<h3 style="color: green">${msg_CMS_addNew}</h3>

					</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">x</button>
				</div>
				<div class="modal-body">
					<div class="row-fluid search">
						<input type="hidden" id="addedIds" value="${equipIds}" /> <input
							type="hidden" id="codeSearchPopup" class="span5" />


					</div>
					<div id="popupBody" class="row-fluid"></div>
				</div>

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
									<h3 >
											<spring:message code="pop.result"></spring:message>
										</h3></div>
									<div class="span6">
										<ext:pagination bean="${bean}" maxPage="5"
											formId="search_form_CMS"></ext:pagination>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<spring:message var="msg_stt" code="hobby.Stt"></spring:message>
							<spring:message var="msg_cms_Code" code="cms.image"></spring:message>
							<spring:message var="msg_name" code="cms.category"></spring:message>
							<spring:message var="msg_description" code="cms.group"></spring:message>
							<spring:message var="msg_title" code="cms.title"></spring:message>
							<spring:message var="msg_status" code="cms.status"></spring:message>
							<spring:message var="msg_createdDay" code="customer.messages.field.created.date"></spring:message>
							<spring:message var="msg_startDay" code="cms.startDate"></spring:message>
							<spring:message var="msg_endDay" code="cms.endDate"></spring:message>
							<th>${msg_cms_Code}</th>
							<th>
							<ext:column-sort
									bean="${bean }" path="cmsCategory.name" formId="search_form_CMS"
									fieldName="${msg_name}"></ext:column-sort>
							</th>
							<th>
							<ext:column-sort
									bean="${bean }" path="groupcms" formId="search_form_CMS"
									fieldName="${msg_description}"></ext:column-sort>
							
							</th>
							<th>${msg_title}</th>
							
							<th>${msg_createdDay}</th>
							<th>${msg_startDay}</th>
							<th>${msg_endDay}</th>
							<th>${msg_status}</th>
							<th class="table-actions"><spring:message code="actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="index" value="0"></c:set>
						<c:forEach var="_cms" items="${bean.listResult }" varStatus="i">
							<tr id="tr${index}">


								<td class="text_center"><img
									src='${url}/ajax/download?fileName=${_cms.image}'
									height="100px" width="200px"></td>
								<!-- lay code cua category hien thi khi biet ma -->

								<%-- <c:forEach var="_listCategory" items="${bean.listCMSCategory }"
									varStatus="i">
									<c:if
										test="${_listCategory.cms_categoryId==_cms.cmsCategory.cms_categoryId }">

										<td><c:out value="${_listCategory.code }" /></td>
									</c:if>
								</c:forEach> --%>
								<td><c:out value="${_cms.cmsCategoryList }" /></td>

								<td><c:out value="${_cms.groupcms }" /></td>


								<td><c:out value="${_cms.title }" /></td>
								<td class="text_center">
								<fmt:formatDate value="${_cms.createDate }" pattern="${sessionScope.formatDate} " var="createDate"/>
									<c:out value="${createDate }"></c:out>
								</td >
								<td class="text_center">
								<fmt:formatDate value="${_cms.startDate  }" pattern="${sessionScope.formatDate} " var="startDate"/>
									<c:out value="${startDate }"></c:out>
								</td>
								<td class="text_center">
								<fmt:formatDate value="${_cms.endDate  }" pattern="${sessionScope.formatDate} " var="endDate"/>
									<c:out value="${endDate }"></c:out>
								</td>

								<c:if test="${_cms.statusTable.status_tableId==1}">
									<spring:message var="msg_Active" code="status.Active"></spring:message>
									<td>
									 <input name="checkbox3" class="checkboxstatus" key="${_cms.cmsId }" type="checkbox" id="checkboxstatus_${_cms.cmsId }" checked="checked" />
									</td>
	

								</c:if>

								<c:if test="${_cms.statusTable.status_tableId==2}">
									<spring:message var="msg_Inactive" code="status.Inactive"></spring:message>
									<td>
									 <input name="checkbox3" class="checkboxstatus" key="${_cms.cmsId }" type="checkbox" id="checkboxstatus_${_cms.cmsId }"/>
									</td>


								</c:if>


								<td class="table-actions"><spring:message var="icon_edit"
										code="icon.edit"></spring:message> <spring:message
										var="icon_view" code="icon.view"></spring:message> 
										<sec:authorize access="!hasAnyRole('R010')">
										<a href="${url}/master_data/cms/new?cmsId=${_cms.cmsId }" title="${icon_edit }"> <i class="bms-icon-edit"></i>
										</a>
									</sec:authorize>
				 <a href="${url}/master_data/cms/view?cmsId=${_cms.cmsId }"
									title="${icon_view }"> <i class="bms-icon-view"></i>
								</a></td>
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
										<ext:pagination bean="${bean}" maxPage="5"
											formId="search_form_CMS"></ext:pagination>
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

