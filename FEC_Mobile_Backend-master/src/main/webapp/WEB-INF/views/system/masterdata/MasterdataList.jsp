<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
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
		$('#previewDetailPopup').bind('hide', function () {
			if($("#reloadyesorno").val()=="1"){
				document.location.href = "${url}/system/masterdata/list";
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

	
	
	function showEquipPopup() {
		$('#previewDetailPopup').modal('show');
	}
	function setReloadyesorno(index){
		$("#reloadyesorno").val(index);
	}
	function showTextVal(showPopup,id) {
		$.ajax({
			url : '${url}/system/masterdata/MasterdataEdit',
			data : {
				addedIds : $('#addedIds').val(),
				codeSearchPopup : $('#codeSearchPopup').val(),
				masterdataId : id
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

	function backHobby() {
		document.location.href = "${url}/system/masterdata/list";
	}
	function back() {
		document.location.href = "";
	}

	function deleteAction(id) {
		confirmMessage('${msg_deleteQuestion}', function(result) {
			if (result) {
				document.location.href = "${url}/system/masterdata/delete?entity.masterdataId="
						+ id;
			}
		});
	}

	function blackAccount() {
		document.location.href = "${url}/system/masterdata/list";
	}
</script>
<!-- start title -->
<div class="title_top" >
<div class="container-fluid">
		<div class="row-fluid">
		<div class="span6">
		<spring:message var="menu_admin" code="menu.admin"></spring:message>
			<spring:message var="menu_system_masterdata" code="menu.system.masterdata"></spring:message>
			<spring:message var="menu_system_masterdata_list" code="menu.system.masterdataList"></spring:message>
		<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"><c:out value="${menu_admin }"></c:out></a>
				<span> > </span>
				<a onclick="blackAccount()" class="Color_back"><c:out value="${menu_system_masterdata }"></c:out></a>
				<span> > </span>
				 <span class="Colorgray"><c:out value="${menu_system_masterdata_list }"></c:out></span>
			</h4>
		</div>
	<div class="span6" >
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>


						<li class="new"><a onclick="showTextVal(true)"
							title="Add new"><span class="new_text"></span></a></li>
						
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
				<spring:message var="msg_List" code="menu.system.masterdataList"></spring:message>
				<h2>${msg_List}</h2>

			</div>

			
		</div>
	
	<form:form method="POST" id="search_form_masterdata"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
		<div class="accordion" id="accordion2">
			<ext:messages bean="${bean}"></ext:messages>

			<div class="accordion-group"></div>
<input id="reloadyesorno"  type="hidden"/>
			<div class="accordion-inner">

				<div class="input-area">
					<div class="span12 input-append"></div>

				</div>
			</div>

		</div>

		<div id="previewDetailPopup" class="modal hide fade assetPopup"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-width="1000">
			
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
											formId="search_form_masterdata"></ext:pagination>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<spring:message var="msg_stt" code="hobby.Stt"></spring:message>
							<spring:message var="msg_name" code="hobby.Name"></spring:message>
							<spring:message var="msg_description" code="hobby.Description"></spring:message>
							<th class="w50_stt">${msg_stt}</th>
							<th>${msg_name}</th>
							<th>${msg_description}</th>
							<th class="table-actions"><spring:message code="actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="index" value="0"></c:set>
						<c:forEach var="_masterdata" items="${bean.listResult }" varStatus="i">
							<tr id="tr${index}">
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>

								<td><c:out value="${_masterdata.name }" /></td>
								<td><c:out value="${_masterdata.description }" /></td>
								<td class="table-actions">
								<spring:message var="icon_edit" code="icon.edit"></spring:message> 
								<spring:message var="icon_delete" code="icon.delete"></spring:message> 
								<a onclick="showTextVal(true,${_masterdata.masterdataId})"title="${icon_edit }"> <i
										class="bms-icon-edit"></i></a>
								<a href="javascript:void(0)"
									onclick="deleteAction('${_masterdata.masterdataId }')"
									title="${icon_delete }"> <i class="bms-icon-delete"></i>
								</a>
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
											formId="search_form_masterdata"></ext:pagination>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="padding: 0px; margin: 0px;"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- and table -->
	</form:form>
</div>

