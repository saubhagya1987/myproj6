<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<form:form method="POST" modelAttribute="bean" cssClass="form-horizontal" id="form_banner">
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel"><spring:message code="banner.image.list"/> </h3>
	</div>
	<div class="modal-body form-horizontal" id="bannerShow">
		<input type="hidden" value="${success }" id="rs11"/>	
		<%-- 				
		<div class="row-fluid">
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
		</div> --%>
		<div class="row-fluid">
			<div class="span10 offset2">
				<div>
					<div id="imageContainerShow" style="float: left;">
					    <a id="imgPickfilesShow" href="javascript:;"></a>
					</div>
					<div style="clear: both;"></div>
					<pre id="imageConsoleShow"  style="display: none"></pre>
					<div id="imageFilelistShow">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div id="showImage" style="float: left; position: relative;margin-left: 75px;"></div>
		</div>
		<input type="hidden" id="tagImageShow" name="lstImage" value="${bean.lstImage }" /> 
		
		<br/>
		<div class="text-right margin_bottom_require">
			<spring:message var="msg_buttonClose" code="button.close"></spring:message>
			<input type="button" class="btn_blue" data-dismiss="modal" aria-hidden="true" value="${msg_buttonClose}" onclick="closePOPUP();"/>
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
</style>	

<script type="text/javascript">
var uploadUrlShow = "${url}/ajax/uploadVideo";
$(document).ready(function() {
	
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
					        	
				        		loadSliderImage(lstImgShow,'${url}');
					        
								$("#" + imageConsoleShow).hide();
								$("#" + imageFileListShow).hide();
					    	};
	
	InitPlupload(imagePickfilesShow, imageContainerShow, uploadUrlShow, true, imageMaxFileSizeShow, imageMimeTypesShow, imageFileListShow, imageConsoleShow, 
									imageFileUploadedShow, imageUploadCompleteShow,'${url}');
	
	var imageShow = $("#tagImageShow").val();
	
	
	if(imageShow != ""){
		loadSliderImage(imageShow,'${url}',0);
	}
});
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

function loadSliderImage(lstImg, url) {
	var lstImgShow = $("#tagImageShow").val();
	
	var lengthImage = lstImgShow.split(',').length;
	var flag;
	if(lengthImage <= 1){
		flag = 1;
	}else{
		flag = 0;
	}
	ajaxUrl = url + '/ajax/showImage?flag=' + flag + '&&listFile=' + lstImg + '&removeImage=1';
	$.ajax({
		url : ajaxUrl,
		cache : false,
		success : function(result) {
			$("#showImage").html(result);

		}
	});
}
</script>