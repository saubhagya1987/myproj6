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
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_banners" code="menu.banners"></spring:message>
			<spring:message var="menu_banner_list" code="banner.list.title"></spring:message>
			<spring:message var="menu_banner_addnew" code="${not empty bean.entity.bannerId?'banner.edit.title':'menu.banner.addnew' }"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"><c:out value="${menu_banners }"></c:out></a>
				<span> > </span>
				<a onclick="backList()" class="Color_back"><c:out value="${menu_banner_list }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_banner_addnew }"></c:out></span>
			</h4>
		</div>	
</div>
</div>	
</div>
<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="edit_title" code="${not empty bean.entity.bannerId?'banner.edit.title':'banner.add.title' }"></spring:message>
					${edit_title }
				</h2>
			</div>		
		</div>

	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" modelAttribute="bean"
		cssClass="form-horizontal" id="banner_form">
		
		<div class="accordion" id="accordion2">
	</div>
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				
				<div class="row-fluid">
					<div class="span3 title1">
						<h3>
							<spring:message code="banner.general.info.title"/>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_2" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				
				</div>
			</div>
			<div id="collapseOne_2" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">
										<spring:message code="banner.field.category" />&nbsp;<span class="required">*</span>
									</label>
									<div class="controls">
										<form:select path="entity.category" class="span10" id="categoryId">
											<form:option value=""><spring:message code="msg.choose"/> </form:option>
											<c:forEach items="${categoryBanner}" var="item">
											 	<form:option value="${item.key}">${item.value}</form:option>
											</c:forEach>
										</form:select>
										<span class="help-inline">	
											<form:errors path="entity.category" cssClass="error" ></form:errors>
										</span>
									</div>	
								</div>
							</div>
							<div class="span6">
								<ext-form:input-text path="entity.bannerCode" cssInput="span10" labelCode="banner.field.code" required="true" id="bannerCodeId"></ext-form:input-text>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span6">
								<fmt:formatDate var="var_activefrom" value="${bean.entity.activeFromDate}"	pattern="${sessionScope.formatDate} HH:mm" />
								<ext-form:input-date-time path="entity.activeFromDate" id="var_activefrom_id" inputId="var_activefrom" value="${var_activefrom}" labelCode="banner.field.active.from.date" required="true"/>
							</div>
							<div class="span6">
								<fmt:formatDate var="var_activeto" value="${bean.entity.activeToDate}"	pattern="${sessionScope.formatDate} HH:mm" />
								<ext-form:input-date-time path="entity.activeToDate" id="var_activeto_id" inputId="var_activeto" value="${var_activeto}" labelCode="banner.field.active.to.date"/>
							</div>		
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label for="entity.stateOffices" class="control-label">
										<spring:message code="banner.field.slidePeriod"></spring:message>&nbsp; 
									</label>
									<div class="controls">	
										<fmt:formatNumber var="slidePeriod" value="${bean.entity.slidePeriod}" pattern="${sessionScope.formatNumber}" maxIntegerDigits="9" />
										<input type="text" id="slidePeriod" onchange="myFormatNumber('slidePeriod')" class="text_right number" style="width: 140px;"  
											value="${slidePeriod}" name="entity.slidePeriod" />&nbsp;<spring:message code="banner.msg.millisecond"></spring:message>
										<span class="help-inline">	
											<form:errors path="entity.slidePeriod" cssClass="error" ></form:errors>
										</span>
									</div>	
								</div>
							</div>	
							<div class="span6">
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
							</div>
								
						</div>
						
						
						
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<label for="entity.orderbanner" class="control-label">
										<spring:message code="banner.order"></spring:message>&nbsp; 
									</label>
									<div class="controls">	
										<fmt:formatNumber var="orderbanner" value="${bean.entity.orderbanner}" pattern="${sessionScope.formatNumber}" maxIntegerDigits="9" />
										<input type="text" id="orderbanner" onchange="myFormatNumber('orderbanner')" class="text_right number" style="width: 140px;"  
											value="${orderbanner}" name="entity.orderbanner" />
										<span class="help-inline">	
										</span>
									</div>	
								</div>
							</div>	
						</div>
						
						
						<div class="row-fluid">
							<ext-form:input-text path="entity.title" cssInput="width_area" labelCode="banner.field.title"></ext-form:input-text>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span3 title1">
						<h3>
							<spring:message code="banner.images.title"/>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion3" href="#collapseOne_3" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				
					
				</div>
			</div>
			<div id="collapseOne_3" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span6">
							<div class="span10 offset1">
								<div>
									<div id="imageContainerShow" style="float: left;">
									    <a id="imgPickfilesShow" href="javascript:;"><img src="${url}/static/images/bt_upload.png" width="112" height="33"></a>
									    <span id="imageError" class="help-inline error"></span>
									</div>
									<div style="clear: both;"></div>
									<pre id="imageConsoleShow"  style="display: none"></pre>
									<div id="imageFilelistShow">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
								</div>
							</div>
						</div>
						<div class="row-fluid" id="div_lstImage">
							<div id="showImage" style="float: left; position: relative;margin-left: 95px;"></div>
						</div>
						<input type="hidden" id="tagImageShow" name="lstImage" value="${bean.lstImage }" />
						<input type="hidden" id="tagImageLinkShow_Total" name="lstImageLink" value="${bean.lstImageLink }"/>
						<input type="hidden" id="checkDate" name="checkDate" value="${bean.checkDateTo}"/>
					</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		
		<div class="text-center">
			<form:hidden path="entity.bannerId"/>
			<spring:message var="save_btn_msg" code="button.save"></spring:message>
			<sec:authorize access="!hasAnyRole('R010')">
				<input type="button" onclick="validatorForm()" value="${save_btn_msg}" class="btn_search_general" />
				</sec:authorize>
				
		</div>	
	</form:form>
</div>
<spring:message var="msg_error" code="banner.image.is.require"></spring:message>
<script type="text/javascript">
function backList(){
	  document.location.href="list";
}
function back(){
	  document.location.href;
}
var formatNumber = "${sessionScope.formatNumber}";
var uploadUrlShow = "${url}/ajax/uploadVideo";
$(document).ready(function() {
	
	/* $("#var_activefrom_id").change(function(){
		debugger;
        var fromDate = $("#var_activefrom_id").datetimepicker( "getDate" );
           if(!$("#var_activefrom").val()){
                $("#var_activeto_id").datetimepicker("setStartDate", 0);
                return;
           }

           var toDate = $("#var_activeto_id").datetimepicker( "getDate" );
           if(toDate < fromDate){
                $("#var_activeto").val("");
           }
           $("#var_activeto_id").datetimepicker("setStartDate", fromDate);
     }); */
	
	//init image uploader
	var imagePickfilesShow = 'imgPickfilesShow';
	var imageContainerShow = 'imageContainerShow';
	var imageMaxFileSizeShow = '7mb';
	var imageMimeTypesShow = [{title : "Image files", extensions : "jpg,bmp,png"}];
    var imageFileListShow = 'imageFilelistShow';
    var imageConsoleShow = 'imageConsoleShow';
	var imageFileUploadedShow = function(up, file, info){
								var lstImgShow = $("#tagImageShow").val();
								var newLstImgShow = findOneItem(lstImgShow, cutString(info.response));
								$("#tagImageShow").val(newLstImgShow);
		                    };
	var imageUploadCompleteShow = function(up, files){
					        	var lstImgShow = $("#tagImageShow").val();
					        	
								loadSliderImage_b(lstImgShow,'${url}',0);
								$("#" + imageConsoleShow).hide();
								$("#" + imageFileListShow).hide();
					    	};
	
	InitPlupload(imagePickfilesShow, imageContainerShow, uploadUrlShow, true, imageMaxFileSizeShow, imageMimeTypesShow, imageFileListShow, imageConsoleShow, 
									imageFileUploadedShow, imageUploadCompleteShow,'${url}');
	
	var imageShow = $("#tagImageShow").val();
	
	
	if(imageShow != ""){
		loadSliderImage_b(imageShow,'${url}',0);
	}
	if($("#checkDate").val() == 'true'){
		$("#var_activeto").attr("class", "span12 errorfeborder");
		$("#var_activefrom").attr("class", "span12 errorfeborder");
		
	}
});
function myFormatNumber(id){
	$("#" + id).parseNumber({format:formatNumber, locale:"${sessionScope.localeSelect}"});
	$("#" + id).formatNumber({format:formatNumber, locale:"${sessionScope.localeSelect}"});
}
function deleteImage(){
	$("#tagImage").val("");
	$("#imgProduct").attr("src","");
}
function findOneItem(lst, item) {
	// neu tag co gia tri thi cong them vao
	if (lst != "") {
		var arr = lst.split(",");
		var found = false;
		for ( var j = 0; j < arr.length; ++j) {
			if (arr[j] == item) {
				found = true;
			}
		}
		// neu obj.responses[i] chua co trong lst
		if (found == false) {
			lst += "," + item; // cong item vao cuoi lst
		}
	} else { // neu tag chua co gia tri thi gan = responses
		lst = item;
	}

	return lst;
}

function validatorForm(){
	var list = "";
	$("#div_lstImage li input").each(function(){
		list += $(this).val() +" @@@";
	});
	$("span.error").text("");
	var isError = false;
	var error = '${msg_error}';
	$("#categoryId").attr("class", "span10");
	$("#bannerCodeId").attr("class", "span10");
	$("#var_activefrom").attr("class", "span10");
	
	if(list != ""){
		list = list.substring(0, list.length -3);
		$("#tagImageLinkShow_Total").val(list);
		
		//$("#banner_form").submit();
	}else{
		$("#imageError").text(error);
		isError = true;
	}
	if($("#categoryId").val() == ''){
		$("#categoryId").attr("class", "span10 errorfeborder");
		isError = true;
	}
	if($("#bannerCodeId").val() == ''){
		$("#bannerCodeId").attr("class", "span10 errorfeborder");
		isError = true;
	}
	if($("#var_activefrom").val() == ''){
		$("#var_activefrom").attr("class", "span12 errorfeborder");
		isError = true;
	}
	if(isError == false){
		$("#banner_form").submit();
	}
	//return true;
}

function loadSliderImage_b(lstImg, url, flag,id) {
	var lstImageLinkShow = $("#tagImageLinkShow_Total").val();	
	ajaxUrl = url + '/ajax/showImage?flag=' + flag + '&&listFile=' + lstImg + '&&id=' + id +'&&lstImageLinkShow=' + lstImageLinkShow;
	$.ajax({
		url : ajaxUrl,
		cache : false,
		success : function(result) {
			$("#showImage").html(result);

		}
	});
}

function backList() {
	document.location.href = "${url}/master_data/banner/list";
}
</script>
