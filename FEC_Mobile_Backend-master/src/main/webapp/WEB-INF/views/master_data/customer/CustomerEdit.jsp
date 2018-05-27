<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<form:form method="POST" modelAttribute="bean" cssClass="form-horizontal" id="form_customer">
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel"><spring:message var="edit_title"
						code="${not empty bean.entity.customerId?'customer.edit.title':'customer.add.title' }"/>
					<c:out value="${edit_title }"></c:out></h3>
</div>
	<div class="modal-body form-horizontal" id="customerShow">
	<input type="hidden" value="${success }" id="rs11"/>	
	<span id="errors_Feedback" class="error"></span>
			<div class="row-fluid">
			<ext:messages bean="${bean}"></ext:messages>
				<div class="span12"style="height: 500px;width:98%;overflow-y: auto !important;">
	
					<div class="span6">
						<ext-form:input-text path="entity.firstName" cssInput="span10" labelCode="customer.field.first.name" required="true" ></ext-form:input-text>
						<input type="hidden" name="succesorfail" id="succesorfail" value="${bean.succesorfail}">
						<ext-form:input-text path="entity.middleName" cssInput="span10" labelCode="customer.field.middle.name" ></ext-form:input-text>
						<ext-form:input-text path="entity.lastName" cssInput="span10" labelCode="customer.field.last.name" required="true"></ext-form:input-text>
						<fmt:formatDate var="var_birthday" value="${bean.entity.birthday}"	pattern="${sessionScope.formatDate}"  />
						<ext-form:input-date3 path="entity.birthday" id="var_birthday_id" value="${var_birthday}" cssInput="span12" labelCode="customer.field.birthday" required="true" />
						
						<c:if test="${ bean.entity.customerId!= null}">
						<ext-form:input-text path="entity.idCardNumber"  cssInput="span10 initNumber"  labelCode="customer.field.card.id.no"  required="true" disable="true" id="entity_idCardNumber"></ext-form:input-text>
						</c:if>
						<c:if test="${bean.entity.customerId == null}">
						<ext-form:input-text path="entity.idCardNumber"  cssInput="span10 initNumber"  labelCode="customer.field.card.id.no"  required="true" id="entity_idCardNumber"></ext-form:input-text>
						</c:if>
							<%-- <c:if test="${bean.entity.customerId==null}">
									<div>
												<div class="control-group">
													<label for="entity.password" class="control-label" >
														 <spring:message code="login.password"></spring:message> <span class="required">*</span>
													</label>
													<div class="controls">
													 <input style="display: none;">
													 <form:password path="entity.password" cssClass="span10 " id="passwordId" autocomplete="off" cssErrorClass="span10 errorfeborder" />
													</div>
												</div>
									</div>
											
									
						</c:if>
						
						<c:if test="${bean.entity.customerId!=null && bean.entity.password==null}">
									<div>
												<div class="control-group">
													<label for="entity.password" class="control-label" >
														 <spring:message code="login.password"></spring:message> <span class="required">*</span>
													</label>
													<div class="controls">
													 <input style="display: none;">
													 <form:password path="entity.password" cssClass="span10 " id="passwordId1" autocomplete="off" cssErrorClass="span10 errorfeborder" />
													</div>
												</div>
									</div>
											
									
						</c:if>
						
								 --%>	
							
						<ext-form:input-text path="entity.cellPhone"  cssInput="span10 initNumber"  labelCode="customer.field.cell.phone"  required="true"  id="entity_phone"></ext-form:input-text>			
						<ext-form:input-text path="entity.emailAddress" cssInput="span10" labelCode="customer.field.email"></ext-form:input-text>
						<ext-form:input-text path="entity.addressNo" cssInput="span10" labelCode="customer.field.address.no"></ext-form:input-text>
						<ext-form:input-text path="entity.street" cssInput="span10" labelCode="customer.field.address.street"></ext-form:input-text>
						<ext-form:input-text path="entity.ward" cssInput="span10" labelCode="customer.field.address.ward"></ext-form:input-text>
						<ext-form:input-text path="entity.district" cssInput="span10" labelCode="customer.field.address.district"></ext-form:input-text>
						<ext-form:input-text path="entity.province" cssInput="span10" labelCode="customer.field.address.province" required="true"></ext-form:input-text>
						
						<div class="control-group">
							<label class="control-label">
								<spring:message code="customer.field.status" />
							</label>
							<div class="controls">
								<form:select path="entity.status" class="span10" itemValue="languageId">
									<form:option value="1"><spring:message code="customer.field.status.active"></spring:message> </form:option>
									<form:option value="-1"><spring:message code="customer.field.status.inactive"></spring:message></form:option>
								</form:select> 
							</div>									
						</div>
						<c:if test="${bean.entity.customerId!=null }"> 
						<div class="control-group">
						<label class="control-label">
								<spring:message code="customer.messages.field.created.date" />
							</label>
							<div class="controls">
						 
						<div class="controls " style="margin-left: 1px;">
						<fmt:formatDate var="var_addDate" value="${bean.entity.createDate}" pattern="${sessionScope.formatDate} HH:mm" />
									 <c:out value="${var_addDate }"> </c:out>
            				</div>
						</div>
					</div>
					</c:if> 
					</div>
					<div class="span6">
						<div class="control-group">
							<label for="type" class="control-label"> <spring:message
									code="account.image"></spring:message>
							</label>
							<div class="controls">
								<div class="span6" style="width:175px;">
								<div class="border_img_2" style="height: 165px;">
									<img id="imgProduct" style="height:100%;">
								</div>
								</div>
									<spring:message var="icon_view" code="icon.view"></spring:message>
									<spring:message var="icon_edit" code="icon.edit"></spring:message>
									<spring:message var="icon_clone" code="icon.clone"></spring:message>
									<spring:message var="icon_delete" code="icon.delete"></spring:message>
								<div class="span2"  style="margin:146px 0 30px">
									<div id="imageContainer" style="float: left; position: relative;">
										<a id="imgPickfiles" href="javascript:void(0)" title="${icon_edit }"
											style="width: 20px; height: 80px; background-color: yellow;"><i
											class="bms-icon-edit"></i></a>
									</div>
									<a onclick="deleteImage();" title="${icon_delete }" ><i class="bms-icon-delete"></i></a>
									

									<div style="clear: both;"></div>

									<pre id="imageConsole" style="display: none"></pre>

									<div id="imageFilelist">Your browser doesn't have
										Flash, Silverlight or HTML5 support.</div>

									<form:hidden path="entity.imagePath" id="tagImage" />
								</div>
							</div>
						</div>
					</div>	
			    </div>
			</div>
			<input id="oldUserId" type="hidden" value="${bean.entity.oldUserId}" />
			<form:hidden path="entity.oldUserId" />
			<form:hidden path="entity.customerId" />
			<c:if test="${ bean.entity.customerId!= null && bean.entity.password!=null}">
			<form:hidden path="entity.password" />
			</c:if>
			<div class="text-right margin_bottom_require" style="margin-right: 450px;">
				<spring:message code="button.save" var="save"></spring:message>
				<sec:authorize access="!hasAnyRole('R010')">
				<input onclick="return editCustomerOnclick();" value="${save}"  type="button" class="btn_search_general" />
				</sec:authorize>
				
				<spring:message var="msg_buttonClose" code="button.close"></spring:message>
				<input  type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true" value="${msg_buttonClose}" onclick="closePOPUP();"/>
			</div>
		<div class="text_require">
		<span class="required">*</span>
		<spring:message var="required" code="msg.required"></spring:message>
		<c:out value="${required }"></c:out>
	</div>
	</div>
</form:form>	
<style>
.modal-body {
    max-height: 800px !important;
}
.modal.fade.in {
    top: 0% !important;
}

button.close {
    background: #018345 none repeat scroll 0 0;
}


.close:hover, .close:focus {
    background: #018345 none repeat scroll 0 0;
}
</style>
<script type="text/javascript">
var formatNumber = "###";
$(document).ready(function() {
var idold = $("#oldUserId").val();
console.log(idold);
if(idold != ''){
	$('#customerShow input').attr("readonly",true);
}


	// IMAGE account
	var image = $("#tagImage").val();
	if (image != "") {
		$("#imgProduct").attr("src",
				"${url}/ajax/download?fileName=" + image);
	}

	//init image uploader
	var uploadUrl = "${url}/ajax/uploadTemp";
	var imagePickfiles = 'imgPickfiles';
	var imageContainer = 'imageContainer';
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png"
	} ];
	var imageFileList = 'imageFilelist';
	var imageConsole = 'imageConsole';
	var imageFileUploaded = function(up, file, info) {
		$("#tagImage").val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#tagImage").val();

		$("#imgProduct").attr("src",
				"${url}/ajax/download?fileName=" + lstImg);
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false,
			imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete,
			'${url}'); 
	
	
	 var groupPrice = $(".initNumber");
		if(groupPrice.length > 0){
			var i = 0;
			while(i < groupPrice.length){
				$(groupPrice[i]).attr("onkeypress", "return isNumberKey(event)");
				i = i + 1;
			}
		} 
});
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : evt.keyCode;
	   if (charCode != 46 && charCode > 31 && ( charCode < 48 || charCode > 57 ))
	      return false;

	   return true;
	};

function initNumer(tag1){		
	$(tag1).blur(function() {		
	   $(this).parseNumber({format:formatNumber, locale:"${sessionScope.localeSelect}"});
	   $(this).formatNumber({format:formatNumber, locale:"${sessionScope.localeSelect}"});
	});
}

function deleteImage(){
	$("#tagImage").val("");
	$("#imgProduct").attr("src","");
}

function editCustomerOnclick(){

	var isError = false;

	$("#passwordId1").attr("class", "span10");
	if($("#passwordId1").val() == null|| $("#passwordId1").val() == ''){
		$("#passwordId1").attr("class", "span10  errorfeborder");
		isError = true;
	}
	if($("#passwordId").val() != null ||$("#passwordId1").val() == null){
		isError = false;
	}
	if(isError == false){
	
	$.ajax({
         url: '${url}/master_data/customer/edit',
         cache: false,
         type: "POST",
         data:$('#form_customer').serialize(),
         success: function(data) { 
        	 $("#customerPopup").html(data);
        	 setReloadyesorno(1);
         }
     });
	}
}
</script>