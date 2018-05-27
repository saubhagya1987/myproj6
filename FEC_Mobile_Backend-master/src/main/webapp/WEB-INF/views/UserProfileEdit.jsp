
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-modal.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-modal-bs3patch.css"/>



	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="list_title" code="account.page.edit.title"></spring:message>
					<%-- <c:out value="${list_title}"></c:out> --%>
				</h2>
			</div>
			<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<c:if test="${not empty lockeduser and lockeduser }">
							<spring:message var="msg_buttonUnLock" code="button.unlock"></spring:message>
							<li class="unlock" id="unlock"><a
								href="${url}/system/account/unlockuser?id=${param.id}"><span
									class="adjust_text"> ${msg_buttonUnLock}</span></a></li>
						</c:if>

					</ul>

				</div>
			</div>
		</div>
	</div>






<div class="container-fluid unit_bg_content">
	<form:form method="POST" id="edit_form" cssClass="form-horizontal"
		modelAttribute="bean" action="edit">
		<ext:messages bean="${bean}"></ext:messages>
		<!-- start Note history -->
		<input type="hidden" value="${success }" id="rs11" />
		<div class="row-fluid">
						<div class="row-fluid">
							<div class="span10 title">
								<h3>

									<spring:message var="list_detail"
										code="account.page.edit.detail"></spring:message>
									<c:out value="${list_detail }"></c:out>  
								</h3>
							</div>
						</div>

					<div id="collapseOne" class="accordion-body collapse in">

						<div class="accordion-inner">
							<div class="row-fluid">
								<div class="span8"></div>
								<div class="span12">
									<div class="span6">
										<div>
											<form:hidden path="entity.id" id="accountid" />

											<ext-form:display cssClass="text_color"
												labelCode="account.field.username"
												value="${bean.entity.username}"></ext-form:display>
										</div>

										<div>
											<ext-form:input-text path="entity.fullName" cssInput="span10"
												disable="false" labelCode="account.field.fullName"></ext-form:input-text>
										</div>

										<div>
											<fmt:formatDate var="var_birthday"
												value="${bean.entity.birthday}"
												pattern="${sessionScope.formatDate}" />
											<ext-form:input-date path="entity.birthday"
												id="var_birthday_id" value="${var_birthday}"
												labelCode="account.field.birthday" />
										</div>

										<div>
											<ext-form:display cssClass="text_color"
												labelCode="account.field.email" value="${bean.entity.email}"></ext-form:display>
										</div>	
										<div>
											<ext-form:input-text path="entity.mobile" cssInput="span10"
												disable="false" labelCode="account.field.mobile"></ext-form:input-text>
										</div>
									</div>
									<div class="span6">

										<div class="control-group">
											<label for="type" class="control-label"> <spring:message
													code="account.image"></spring:message>
											</label>
											<div class="controls">
												<div class="span6" style="width: 175px;">
													<div class="border_img_2" style="height: 165px;">
														<img id="imgProduct" style="height: 100%;">
													</div>
												</div>

												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>

												<div class="span2" style="margin: 146px 0 30px">
													<div id="imageContainer"
														style="float: left; position: relative;">
														<a id="imgPickfiles" href="javascript:void(0)" title="${icon_edit}"
															style="width: 20px; height: 80px; background-color: yellow;"><i
															class="bms-icon-edit"></i></a>
													</div>
													<a onclick="deleteImage();" title="${icon_delete}" ><i class="bms-icon-delete"></i></a>


													<div style="clear: both;"></div>

													<pre id="imageConsole" style="display: none"></pre>

													<div id="imageFilelist">Your browser doesn't have
														Flash, Silverlight or HTML5 support.</div>

													<form:hidden path="image" id="tagImage" />
												</div>
											</div>
										</div>
										<div class="control-group">
											<label for="type" class="control-label"> 
											</label>
											<div class="controls">
												<div class="span6" style="width: 175px;">			
														<c:if test="${bean.entity.ldap==false}">									
														<a  onclick="changePassword();" ><spring:message code="account.change.password" /></a>
														</c:if>	
													
												</div>

											</div>
										</div>
										

									</div>
								</div>

							</div>
						</div>
					</div>

				</div>

		<!-- end Note history -->
		<div class="row-fluid text-center">
			<div class="accordion" id="accordion">
				<div class="accordion-group">
					<input type="hidden" name="action" value="edit" />
					<spring:message var="save_btn_msg" code="button.save"></spring:message>
					<input type="submit" value="${save_btn_msg}" id="saveEdit"
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

							<!-- ROLES POSITION -->
						</div>

					</div>
				</div>
			</div>
		</div>
	</form:form>

</div>
<div id="changePasswordId" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="projectModalLabel" style="top: 10% !important;"  aria-hidden="true" data-width="800"></div>
<script type="text/javascript">
	$(document).ready(
			function() {

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

				// IMAGE signature
				var image1 = $("#tagImage1").val();
				if (image1 != "") {
					$("#imgProduct1").attr("src",
							"${url}/ajax/download?fileName=" + image1);
				}

				//init image uploader
				var uploadUrl1 = "${url}/ajax/uploadTemp";
				var imagePickfiles1 = 'imgPickfiles1';
				var imageContainer1 = 'imageContainer1';
				var imageMaxFileSize1 = '7mb';
				var imageMimeTypes1 = [ {
					title : "Image files",
					extensions : "jpg,bmp,png"
				} ];
				var imageFileList1 = 'imageFilelist1';
				var imageConsole1 = 'imageConsole1';
				var imageFileUploaded1 = function(up, file, info) {
					$("#tagImage1").val(cutString(info.response));
				};

				var imageUploadComplete1 = function(up, files) {
					var lstImg1 = $("#tagImage1").val();

					$("#imgProduct1").attr("src",
							"${url}/ajax/download?fileName=" + lstImg1);
					$("#" + imageConsole1).hide();
					$("#" + imageFileList1).hide();
				};
				InitPlupload(imagePickfiles1, imageContainer1, uploadUrl1,
						false, imageMaxFileSize1, imageMimeTypes1,
						imageFileList1, imageConsole1, imageFileUploaded1,
						imageUploadComplete1, '${url}');

			});
	$("#saveEdit").click(function() {
		var url = "${url}/system/userProfile/save";
		$.ajax({
			url : url,
			data : $('#edit_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#edit_form").html(data);
			}
		});
	});
	function deleteImage(){
		$("#tagImage").val("");
		$("#imgProduct").attr("src","");
	}

	function changePassword(){
		var url = "";		
		url= '${url}/user/changePassword';		
		$.ajax({
			url: url,
			type: 'GET',
			async: false,
			success: function(data) {
				$("#changePasswordId").html(data);
				$("#changePasswordId").modal('show');
			}
		});
	}
</script>
