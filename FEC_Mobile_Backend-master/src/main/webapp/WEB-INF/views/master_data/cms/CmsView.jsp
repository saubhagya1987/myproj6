
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/multiple-select.css"/>
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<script type="text/javascript">

function backCMS() {
	document.location.href = "";
	}
	function backCMSAddNew() {
	document.location.href = "${url}/master_data/cms/new";
	}

</script>
	


<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_cms" code="menu.cms"></spring:message>
			<spring:message var="menu_cms_list" code="cms.list"></spring:message>
			<spring:message var="menu_cms_addnew_edit" code="${not empty bean.entity.cmsId?'cms.view':'cms.new' }"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backCMS()"class="Color_back"><c:out value="${menu_cms }"></c:out></a>
				<span> > </span>
				<a onclick="backCMS()"class="Color_back"><c:out value="${menu_cms }"></c:out></a>
				<span> > </span>
				<a onclick="backList()"class="Color_back"><c:out value="${menu_cms_list }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_cms_addnew_edit }"></c:out></span>
			</h4>
		</div>
</div>
</div>
</div>
<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
				<spring:message var="list_detail" code="cms.view"></spring:message>
									<c:out value="${list_detail }"></c:out>
				</h2>
			</div>
		</div>

	<form:form method="POST" id="edit_form" cssClass="form-horizontal"
		autocomplete="false" modelAttribute="bean" action="new">		
		<!-- vung search -->
		<!-- start Note history -->
		<input type="hidden" value="${success }" id="rs11" />
		<div class="row-fluid">
			<div class="accordion" id="accordion2">
				<div class="accordion-group">
					<div id="collapseOne" class="accordion-body in collapse">

						<div class="accordion-inner">
							<div class="row-fluid">
								<div class="span12">
											<label  style="margin-left: 36px;"  for="type" class="control-label"><spring:message code="cms.title" /> </label>
											 <label style="margin-left: 10px;" for="type" class="span9"><span class="required" style="color: green">${bean.entity.title} </span> </label> 
								</div>
								<div class="span12">

									<div class="span6">

										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.category" /> </label>
											<label style="margin-left: 10px;" for="type" class="span6"><span class="required" style="color: green">${bean.CMSView} </span> </label> 
										</div>	

										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.categoryHobby" /></label>
											<label style="margin-left: 10px;" for="type" class="span6"><span class="required" style="color: green">${bean.CMSHobbyView} </span> </label> 
										</div>	


										<div class="control-group">
										
										<label for="type" class="control-label"> <spring:message code="cms.group" /></label>
										<label for="type" class="span6"><span class="required" style="color: green">${bean.entity.groupcms} </span> </label>
											 
											 
										</div>
										<div class="control-group">
										<label for="type" class="control-label"> <spring:message code="cms.tag" /></label>
											 <label for="type" class="span6"><span class="required" style="color: green">${bean.entity.tag} </span> </label>
											 
										</div>

										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.status" /></label>
											<c:if test="${bean.entity.statusTable.status_tableId==1}">
											 <label for="type" class="span6"><span class="required" style="color: green">Activated</span> </label>
											 </c:if>
											 <c:if test="${bean.entity.statusTable.status_tableId==2}">
											 <label for="type" class="span6"><span class="required" style="color: green">Inactivated</span> </label>
											 </c:if>
										</div>
										
										<div class="control-group">
										
										<label for="type" class="control-label"> <spring:message code="systemconfig.field.urlparam" /></label>
										<label for="type" class="span6"><span class="required" style="color: green">${bean.entity.url} </span> </label>
											 
											 
										</div>
										
										<div class="control-group">
										
										<label for="type" class="control-label"> <spring:message code="cms.startDate" /></label>
										<fmt:formatDate var="var_startDate" value="${bean.entity.startDate}" pattern="${sessionScope.formatDate} HH:mm" />
											<label for="type" class="span6"><span class="required" style="color: green">${var_startDate} </span> </label>
											
											
											 
											 
										</div>


										<div class="control-group">

											<label for="type" class="control-label"> <spring:message code="cms.endDate" /></label>
											<fmt:formatDate var="var_endDate" value="${bean.entity.endDate}" pattern="${sessionScope.formatDate} HH:mm"  />
											<label for="type" class="span6"><span class="required" style="color: green">${var_endDate} </span> </label>


										</div>
										
										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="banner.order" /></label>
											<label for="type" class="span6"><span class="required" style="color: green">${bean.entity.ordercms} </span> </label>
										</div>
										
										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.sharecomment" /></label>
											<label for="type" class="span6"><span class="required" style="color: green">${bean.entity.shareComment} </span> </label>
										</div>


									</div>
									<div class="span6">

										<div class="control-group">
										<label for="type" class="control-label"> <spring:message code="cms.image" /></label>
											<div class="controls">
												
												<div>
													<div class="border_img_2">
														<img id="imgProduct" style="height: 100%;">
													</div>
												</div>
												<spring:message var="icon_view" code="icon.view"></spring:message>
												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_clone" code="icon.clone"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>
												
												<div >
													
													<div id="imageContainer"
														style="float: left; position: relative; padding-left: 20px;">
													</div>

													<form:hidden path="entity.image" id="tagImage" />
												</div>
											
											
											</div>
											</div>
											<div class="row-fluid" >
												<div class="span12">

												
												</div>

											</div>
											<div class="control-group">
												<label for="type" class="control-label"> <spring:message code="cms.imageLong" /></label>
												<div class="controls">
													<div class="border_img_2">
														<img id="imgProduct1" style="height: 100%;">
													</div>
												</div>
												<spring:message var="icon_view" code="icon.view"></spring:message>
												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_clone" code="icon.clone"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>
												
												<div >
													
													<div id="imageContainer1"
														style="float: left; position: relative; padding-left: 20px;">
													</div>

													<form:hidden path="entity.imageLong" id="tagImage1" />
												</div>
											
											
											</div>
											
										</div>


									</div>

									<div class="row-fluid">
									<div class="span12">
											<label style="margin-left: 32px;" class="control-label"> <spring:message code="cms.short" /></label>
											 <label style="margin-left: 10px;" for="type" class="span9"><span class="required" style="color: green">${bean.entity.shortDescription} </span> </label> 
									</div>

									</div>

									<div class="row-fluid">
									
									<div class="span12">
											<label style="margin-left: 32px;" for="type" class="control-label"> <spring:message code="cms.content" /></label>
											 <label  style="margin-left: 10px;" for="type" class="span9"><span class="required" style="color: green">${bean.entity.content} </span> </label> 
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

		<!-- start Note history -->
		<!-- end Note history -->



		<!-- end Note history -->
		<form:hidden path="entity.cmsId" />
	</form:form>

</div>



<script type="text/javascript">
	function backList() {
		document.location.href = "${url}/master_data/cms/list";
	}

	$(document).ready(function() {
		 
						$("#chkAllTeamLeft").click(
								function() {
									$("input[name=chkTeamLeft]").each(
											function() {
												this.checked = $(
														"#chkAllTeamLeft").is(
														':checked');
											});
								});
						$("#chkAllTeamRight").click(
								function() {
									$("input[name=chkTeamRight]").each(
											function() {
												this.checked = $(
														"#chkAllTeamRight").is(
														':checked');
											});
								});
						$("#addTeam").click(function() {
							$("input[name=chkTeamRight]").each(function() {
								if (this.checked) {
									this.checked = false;
									var row = $(this).closest("tr");
									var table = $(this).closest("table");
									row.detach();
									if (table.is("#tblRightTeam")) {
										$("#tblLeftTeam").append(row);
									}
									//  row.fadeOut();
									row.fadeIn();
									this.name = "chkTeamLeft";
									this.id = "chkTeamLeft";
								}

							});
						});
						$("#removeTeam").click(function() {
							$("input[name=chkTeamLeft]").each(function() {
								if (this.checked) {
									this.checked = false;
									var row = $(this).closest("tr");
									var table = $(this).closest("table");
									row.detach();
									if (table.is("#tblLeftTeam")) {
										$("#tblRightTeam").append(row);
									}
									//row.fadeOut();
									row.fadeIn();
									this.name = "chkTeamRight";
									this.id = "chkTeamRight";
								}

							});
						});

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
						InitPlupload(imagePickfiles, imageContainer, uploadUrl,
								false, imageMaxFileSize, imageMimeTypes,
								imageFileList, imageConsole, imageFileUploaded,
								imageUploadComplete, '${url}');

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
						InitPlupload(imagePickfiles1, imageContainer1,
								uploadUrl1, false, imageMaxFileSize1,
								imageMimeTypes1, imageFileList1, imageConsole1,
								imageFileUploaded1, imageUploadComplete1,
								'${url}');

						$('.multiselectComment').multipleSelect({
							filter : true,
							noMatchesFound : '${no_matches_found}'
						});
						$("#category").multipleSelect("disable");	
						$("#accordion2 .ms-parent")
								.mouseenter(
										function() {
											if ($("#collapseOne").attr("class") == "accordion-body in collapse") {
												$("#collapseOne").attr("class",
														"accordion-body in");
											}
										});
					});

	$("#usernameId")
			.change(
					function() {
						alert('<spring:message code="account.field.username.nochange"></spring:message>');
					});

	$("#saveEdit")
			.click(
					function() {
						$("input[name=chkTeamLeft]").each(function() {
							this.checked = true;
						});
						var url = "${url}/system/account/edit?u_id=" + $.now();
						$
								.ajax({
									url : url,
									data : $('#approval_info_form,#edit_form')
											.serialize(),
									dataType : 'text',
									type : 'POST',
									cache : false,
									success : function(data) {
										$("#usernameId").focus();
										var success = $(data).find("#rs11")
												.val();
										if (success) {
											location.href = "${url}/system/account/list?message=success";
										} else {
											$("#eBody").html(data);
										}
									}
								});
					});

	function deleteImage() {
		$("#tagImage").val("");
		$("#imgProduct").attr("src", "");
	}

	function validatorForm(){
		
		$("span.error").text("");
		var isError = false;
		var editor_valUndefined = CKEDITOR.instances.editor1.document.getBody().getChild(0);
		if(editor_valUndefined==undefined){
			$("#cke_editor1").attr("class", "cke_1 cke cke_reset cke_chrome cke_editor_editor1 cke_ltr cke_browser_webkit errorfeborder");
			isError = true;
			}
		var editor_val = CKEDITOR.instances.editor1.document.getBody().getChild(0).getText() ;
		
		$("#catelory").attr("class", "multiselectComment span7");
		if($("#category").val() == null){
			$(".catelory").attr("class", "span7 errorfeborder");
			isError = true;
		}

		$("#entity_title").attr("class", "width_area_unit");
		if($("#entity_title").val() == ''){
			$("#entity_title").attr("class", "width_area_unit errorfeborder");
			isError = true;
		}

		$("#entity_tag").attr("class", "span7");
		if($("#entity_tag").val() == ''){
			$("#entity_tag").attr("class", "span7 errorfeborder");
			isError = true;
		}
		$("#entity_shortDescription").attr("class", "width_area_unit");
		if($("#entity_shortDescription").val() == ''){
			$("#entity_shortDescription").attr("class", "width_area_unit errorfeborder");
			isError = true;
		}	

										
		 $("#cke_editor1").attr("class", "cke_1 cke cke_reset cke_chrome cke_editor_editor1 cke_ltr cke_browser_webkit");
		if(editor_val.val() == ''){
			$("#cke_editor1").attr("class", "cke_1 cke cke_reset cke_chrome cke_editor_editor1 cke_ltr cke_browser_webkit errorfeborder");
			isError = true;
		}
		 
		if(isError == false){
			$("#edit_form").submit();
		}
		//return true;
	}
</script>
