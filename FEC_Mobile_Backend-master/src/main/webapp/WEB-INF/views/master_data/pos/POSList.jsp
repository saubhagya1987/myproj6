<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script type="text/javascript">
	$(document).ready(function() {
		$('#action').val("search");
		$("#reset").click(function() {
			reset();
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

	function showTextVal(showPopup,id) {
		$.ajax({
			url : '${url}/master_data/pos/posEdit',
			data : {
				addedIds : $('#addedIds').val(),
				codeSearchPopup : $('#codeSearchPopup').val(),
				posId : id
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
			$('#addedIds').val($('#equipIds').text());
		}
	}
	function backPOS() {
		document.location.href = "${url}/master_data/pos/list";
	}
	function back() {
		document.location.href = "";
	}
	function deleteAction(id) {
		confirmMessage('${msg_deleteQuestion}', function(result) {
			if (result) {
				document.location.href = "${url}/master_data/pos/delete?entity.posid="
						+ id;
			}
		});
	}
	function initAutocomplete() {	
		// Create the search box and link it to the UI element.
		var input = document.getElementById('pac-input');
		var autocomplete = new google.maps.places.Autocomplete(input); 	
		autocomplete.addListener('place_changed', function() {		
			initialize();
		});
	}
	function loadDAO() {	
		 $("#eBody").mask();
		  geocoder = new google.maps.Geocoder();	
		$.ajax({
			   url : '${url}/master_data/pos/checkListDAO',
			   data: {
			   },
			   type : "GET",				
			   async: false,			 
			   success : function(data) {			
				  
				   $.each(data, function(i, object) {
						    geocoder.geocode({ 'address': object.address_number }, function (results, status) { 					    	
						        if (status == google.maps.GeocoderStatus.OK) {	
						            var longitude=results[0].geometry.location.lng();
						            var latitude=results[0].geometry.location.lat();					         
						    
						              $.ajax({
						 			   url : '${url}/master_data/pos/saveValue?longitude='+longitude+'&latitude='+latitude+'&posid='+object.posid,						 			 
						 			  type : "POST",
						 			  async: false,						 		
						 			  complete : function() { 
						 				 
						 			 	 }
									  }); 
												
						          
						        } else {
						           // alert('Geocode was not successful for the following reason: ' + status);
						        	//console.log("2")	;
						        }
						    });
						    
						    
						
					});
				   
				   
			   	}
			  });
		setTimeout(function(){
			document.location.href = "${url}/master_data/pos/list?mess=SUCCESS";
			},1500)
	
	}

	
</script>
<script src="https://maps.googleapis.com/maps/api/js?libraries=places"></script>
<!-- start title -->
<div class="title_top">
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_navigation" code="menu.navigation"></spring:message>
			<spring:message var="menu_pos" code="pop.pop"></spring:message>
			<spring:message var="menu_pos_list" code="pop.list"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()"class="Color_back"><c:out value="${menu_navigation }"></c:out></a>
				<span> > </span>
				<a onclick="backPOS()"class="Color_back"><c:out value="${menu_pos }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_pos_list}"></c:out></span>
			</h4>
		</div>	
		
		<div class="span6">
				<div class="menu_images">
					<div class="span12 input-append" style="text-align: right;margin-top: 5px">
						<spring:message var="msg_buttonAdd" code="pos.save.pos.latu"></spring:message>
						<button type="button"onclick="javascript:loadDAO()"
								class="btn_search_general" name="search">${msg_buttonAdd }</button>
					</div>
				</div>
			</div>
		</div>
	</div>	
</div>
<!-- and title -->
<div class="container-fluid unit_bg_content">

		<div class="row-fluid" >
			<div class="span6 title_h2 title">
				<spring:message var="msg_List" code="pop.list"></spring:message>
				<h2 >${msg_List}</h2>
			</div>
		</div>

	
	<form:form method="POST" id="search_form_POS"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
		<ext:messages bean="${bean}"></ext:messages>
		<input id="pac-input" type="hidden" style="width:350px"/>
		<div class="row-fluid ">
		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid" >
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
		</div>

		<div id="collapseOne" class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
					<div class="row-fluid search">
						<div class="span12 input-append">
							<spring:message var="msg_pos" code="search.pos"></spring:message>
							<form:input path="searchField" cssClass="span4"
								placeholder="${msg_pos}" />
							<form:hidden path="action" id="action" value="search" />
							<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
							<button type="button"
								onclick="submitAndSetHiddenVal('search_form_POS',{'page':'1','maxPage':'5'})"
								class="btn_search_general" name="search">${msg_buttonSearch }</button>
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

			<div id="previewDetailPopup" class="modal hide fade assetPopup"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-width="1700">
				
				<div class="modal-body">
					<div class="row-fluid search">
						<input type="hidden" id="addedIds" value="${equipIds}" /> <input
							type="hidden" id="codeSearchPopup" class="span5" />


					</div>
					<div id="popupBody" class="row-fluid"
						style="background: White width:    800px; margin-left: 50px;"></div>
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
											formId="search_form_POS"></ext:pagination>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<spring:message var="msg_stt" code="hobby.Stt"></spring:message>
							<spring:message var="msg_pos_name" code="pop.branchPos"></spring:message>
							<spring:message var="msg_pos_province" code="pos.haveLongLa"></spring:message>
							<spring:message var="msg_pos_address" code="pos.address"></spring:message>
							<spring:message var="msg_status" code="SearchMap.payorpay"></spring:message>
							<spring:message var="msg_partnerName" code="pos.partnerName"></spring:message>
							<th class="w50_stt">${msg_stt}</th>
							<th><ext:column-sort bean="${bean }" path="branch_namepos"
									formId="search_form_POS" fieldName="${msg_partnerName}"></ext:column-sort>
							</th>
							<th><ext:column-sort bean="${bean }" path="branch_namepos"
									formId="search_form_POS" fieldName="${msg_pos_name}"></ext:column-sort>
							</th>
							<th><ext:column-sort bean="${bean }" path="province"
									formId="search_form_POS" fieldName="${msg_pos_province}"></ext:column-sort>

							</th>
							<th><ext:column-sort bean="${bean }" path="address_number"
									formId="search_form_POS" fieldName="${msg_pos_address}"></ext:column-sort>
							</th>
							<th>${msg_status}</th>
							<th class="table-actions"><spring:message code="hobby.Action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="index" value="0"></c:set>
						<c:forEach var="_pos" items="${bean.listResult }" varStatus="i">
							<tr id="tr${index}">
								<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
								<td><c:out value="${_pos.branch_namepos }" /></td>
								<td><c:out value="${_pos.pos_name }"></c:out> <input
									type="hidden" id="equipIds${index}" value="${_pos.posid}" />
									<c:set var="equipIds" value="${equipIds},${_pos.posid}"></c:set></td>
								<c:if test="${_pos.longitude!=null }">
								<td><c:out value="YES" /></td>
								</c:if>
								<c:if test="${_pos.longitude==null }">
								<td><c:out value="NO" /></td>
								</c:if>
								<td><c:out value="${_pos.address_number }" /></td>
								<td><c:out value="${_pos.buyOrPay }" /></td>
								<td class="table-actions">
								<spring:message var="icon_edit"
										code="icon.edit"></spring:message> <spring:message
										var="icon_view" code="icon.view"></spring:message> 
									<spring:message
										var="icon_delete" code="icon.delete"></spring:message> 	
								<a href="${url}/master_data/pos/editMap?posId=${_pos.posid}" title="${icon_edit }"> <i class="bms-icon-edit"></i>
										</a>
								<a onclick="showTextVal(true,${_pos.posid})"title="${icon_view }"> <i
							class="bms-icon-view"></i></a>
							<sec:authorize access="!hasAnyRole('R010')">
				<a href="javascript:void(0)"
									onclick="deleteAction('${_pos.posid }')"
									title="${icon_delete }"> <i class="bms-icon-delete"></i>
								</a>
				</sec:authorize>
				
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
											formId="search_form_POS"></ext:pagination>
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

