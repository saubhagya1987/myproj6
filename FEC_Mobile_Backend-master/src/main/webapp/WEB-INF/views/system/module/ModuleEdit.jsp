
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
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js">



</script>
<script>
	function blackAccountList() {
	document.location.href = "${url}/system/module/list";
	}
	function blackAccount() {
	document.location.href ;
	}
</script>	
	<div class="title_top">
		<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_admin" code="menu.admin"></spring:message>
			<spring:message var="menu_system_account" code="menu.system.module"></spring:message>
			<spring:message var="menu_account_addnew_edit" code="${not empty bean.entity.id?'account.page.edit.module':'account.page.add.module' }"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="blackAccount()" class="Color_back"><c:out value="${menu_admin }"></c:out></a>
				<span> > </span>
				<a onclick="blackAccountList()" class="Color_back"><c:out value="${menu_system_account }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_account_addnew_edit }"></c:out></span>
			</h4>
		</div>		
		
	</div>
		</div>		
	</div>

<div class="container-fluid unit_bg_content">

			<div class="row-fluid">
				<div class="span6 title_h2">
					<h2><spring:message var="list_title" code="${not empty bean.entity.id?'account.page.edit.module':'account.page.add.module' }"></spring:message>
						<c:out value="${list_title}"></c:out>
					</h2>
				</div>			
			</div>
		
	<form:form method="POST" id="edit_form" cssClass="form-horizontal" autocomplete="false"
	modelAttribute="bean" action="edit">
	<ext:messages bean="${bean}"></ext:messages>
		<!-- vung search -->
<!-- start Note history -->
	<input type="hidden" value="${success }" id="rs11"/>
	<div class="row-fluid">
	<div class="accordion" id="accordion2">
	</div>
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span2 title1">
							<h3>
							
							<spring:message var="list_detail" code="module.page.edit.detail"></spring:message>
							<c:out value="${list_detail }"></c:out>
							</h3>
						</div>
						<div class="span1 unit_accordion" style="text-align: right;">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion2" href="#collapseOne"><i
								class="bms-icon-accordion-down bms-icon-accordion"></i></a>
						</div>
					</div>
				</div>
				
				<div id="collapseOne" class="accordion-body in collapse">
					
					<div class="accordion-inner">
						<div class="row-fluid">
							<div class="span8"></div>
							<div class="span12">
								<div class="span6">
									<div>
									<form:hidden path="entity.id" id="moduleid"/>
									</div>	
									
									 <div>
									<ext-form:input-text path="entity.name" cssInput="span10" id="modulenameId"  labelCode="module.field.name" required="true" maxlength="40"></ext-form:input-text>
									</div> 
									
									<div>
									<ext-form:input-text path="entity.description" cssInput="span10" labelCode="module.description" required="true" maxlength="60"></ext-form:input-text>
									</div>
									
					
									
									
								</div>
						
							</div>
							
							
							
							
						</div>
					
				</div>

			</div>
		</div>
	</div>
	</div>
	
	
	<div class="row-fluid text-center">			
		<div class="accordion" id="accordion">
			<div class="accordion-group">
				<input type="hidden" name="action" value="edit" />
				<spring:message var="save_btn_msg" code="button.save"></spring:message>
				<input type="button" value="${save_btn_msg}" id="saveEdit"
					class="btn_search_general" />
				<spring:message var="return_btn_msg" code="button.return"></spring:message>
					
			</div>
		</div>
	</div>
	<div class="row-fluid">
	<div class="container-fluid">
			<div class="accordion" id="accordion">
				<div class="accordion-group">
					<div id="search_filter_body" class="accordion-body collapse in">
					
					</div>

				</div>
			</div>
	</div>
	</div>
	</form:form>

</div>



<script type="text/javascript">
	function backList() {
		document.location.href = "list";
	}


	
	$(document).ready(
			function() {
				
				
		

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
				
				InitPlupload(imagePickfiles, imageContainer, uploadUrl, false,
						imageMaxFileSize, imageMimeTypes, imageFileList,
						imageConsole, imageFileUploaded, imageUploadComplete,
						'${url}');
				
				// IMAGE signature
				var image1 = $("#tagImage1").val();
		
				//init image uploader
				var uploadUrl1 = "${url}/ajax/uploadTemp";
				var imagePickfiles1 = 'imgPickfiles1';
				var imageContainer1 = 'imageContainer1';
				var imageMaxFileSize1 = '7mb';
			
				var imageFileList1 = 'imageFilelist1';
				var imageConsole1 = 'imageConsole1';
			
				InitPlupload(imagePickfiles1, imageContainer1, uploadUrl1, false,
						imageMaxFileSize1, imageMimeTypes1, imageFileList1,
						imageConsole1, imageFileUploaded1, imageUploadComplete1,
						'${url}');
				
			
			});

	$("#saveEdit").click(function(){		
		$("input[name=chkTeamLeft]").each(function() {
			this.checked = true;
		});
		var url = "${url}/system/module/edit?u_id="+$.now();
		$.ajax({
			url : url,
			data : $('#approval_info_form,#edit_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#modulenameId").focus();
				var success = $(data).find("#rs11").val();
				if(success){
					location.href = "${url}/system/module/list?message=success";
				}else{
					$("#eBody").html(data);	
				}
			}
		});
	}); 


</script>
