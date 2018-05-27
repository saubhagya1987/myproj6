
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/multiple-select.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />

<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="${url }/static/ckfinder/ckfinder.js"></script>
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
				<spring:message var="menu_cms_addnew_edit" code="${not empty bean.entity.cmsId?'cms.edit':'cms.new' }"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="backCMS()" class="Color_back"><c:out value="${menu_cms }"></c:out></a> <span> > </span> <a onclick="backCMS()" class="Color_back"><c:out
							value="${menu_cms }"></c:out></a> <span> > </span> <a onclick="backList()" class="Color_back"><c:out value="${menu_cms_list }"></c:out></a> <span>
						> </span> <span class="Colorgray"><c:out value="${menu_cms_addnew_edit }"></c:out></span>
				</h4>
			</div>
		</div>
	</div>
</div>
<div class="container-fluid unit_bg_content">

	<div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message var="list_detail" code="cms.edit"></spring:message>
				<c:out value="${list_detail }"></c:out>
			</h2>
		</div>
	</div>

	<form:form method="POST" id="edit_form" cssClass="form-horizontal" autocomplete="false" modelAttribute="bean" action="new">
		<!-- vung search -->
		<!-- start Note history -->
		<input type="hidden" value="${success }" id="rs11" />
		<div class="row-fluid">
			<div class="accordion" id="accordion2">
				<div class="accordion-group">
					<div id="collapseOne" class="accordion-body in collapse">

						<div class="accordion-inner">
							<div class="row-fluid">
								<div></div>
								<div class="span12">
									<div>
										<div>
											<ext-form:input-text path="entity.title" cssInput="width_area_unit title" required="true" labelCode="cms.title" uppercase="true"
												id="entity_title"></ext-form:input-text>
										</div>
									</div>
								</div>
								<div class="span12">

									<div class="span6">

										<div class="control-group">

											<label for="type" class="control-label"> <spring:message code="cms.category" /> &nbsp;<span class="required">*</span>
											</label>
											<div class="controls">

												<form:select id="category" name="category" cssClass="multiselectComment span7 catelory" path="cmsCategoryIds">
													<c:forEach items="${bean.listCMSCategoryByStock}" var="j">
														<form:option value="${j.cms_categoryId}">${j.code}-${j.name}</form:option>
													</c:forEach>
												</form:select>
											</div>


										</div>

										<div class="control-group">

											<label for="type" class="control-label"> <spring:message code="cms.categoryHobby" />
											</label>
											<div class="controls">

												<form:select id="hobby" name="hobby" cssClass="multiselectComment span7 hobby" path="cmsHobbyIds">
													<c:forEach items="${bean.listCMSHobbyByStock}" var="j">
														<form:option value="${j.hobbyId}">${j.code}- ${j.name}</form:option>
													</c:forEach>
												</form:select>
											</div>


										</div>


										<div class="control-group">
											<label class="control-label" for="entity.groupcms"> Group </label>
											<div class="controls">
												<form:select path="entity.groupcms">
													<c:forEach items="${hotline}" var="j">
														<form:option value="${j.name}">${j.name}</form:option>
													</c:forEach>
												</form:select>
												<span class="help-inline entity_Region_regionId_msg"> </span>
											</div>
										</div>
										<div class="control-group">
											<div>
												<ext-form:input-text path="entity.tag" cssInput="span7" id="entity_tag" labelCode="cms.tag"></ext-form:input-text>
											</div>
										</div>

										<div class="control-group">

											<label for="type" class="control-label"> <spring:message code="cms.status" />
											</label>
											<div class="controls">

												<form:select id="statusTable.statustableId" cssClass="" path="entity.statusTable.status_tableId">
													<c:forEach items="${bean.listStatusTable}" var="j">
														<form:option value="${j.status_tableId}">${j.status_text}</form:option>
													</c:forEach>
												</form:select>


											</div>
										</div>

										<div class="control-group">
											<div>
												<ext-form:input-text path="entity.url" cssInput="span7" id="entity_url" labelCode="systemconfig.field.url"></ext-form:input-text>
											</div>
										</div>

										<div class="control-group">
											<div>
												<fmt:formatDate var="var_startDate" value="${bean.entity.startDate}" pattern="${sessionScope.formatDate} HH:mm" />
												<ext-form:input-date-time path="entity.startDate" id="var_startDate_id" inputId="var_startDate" value="${var_startDate}"
													labelCode="cms.startDate" />
											</div>
										</div>


										<div class="control-group">
											<div>
												<fmt:formatDate var="var_endDate" value="${bean.entity.endDate}" pattern="${sessionScope.formatDate} HH:mm" />
												<ext-form:input-date-time path="entity.endDate" id="var_endDate_id" inputId="var_endDate" value="${var_endDate}" labelCode="cms.endDate" />
											</div>
										</div>

										<div class="control-group">
											<label for="entity.ordercms" class="control-label"> <spring:message code="banner.order"></spring:message>&nbsp;
											</label>
											<div class="controls">
												<fmt:formatNumber var="ordercms" value="${bean.entity.ordercms}" pattern="${sessionScope.formatNumber}" maxIntegerDigits="9" />
												<input type="text" id="ordercms" onchange="myFormatNumber('ordercms')" class="text_right number" value="${ordercms}"
													name="entity.ordercms" /> <span class="help-inline"> </span>
											</div>
										</div>




									</div>
									<div class="span6">

										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.image"></spring:message>
											</label>
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

												<div>

													<div id="imageContainer" style="float: left; position: relative; padding-left: 20px;">
														<a id="imgPickfiles" href="javascript:void(0)" title="${icon_edit }" style="width: 20px; height: 80px; background-color: yellow;"><i
															class="bms-icon-edit"></i></a>
													</div>
													<a onclick="deleteImage();" title="${icon_delete }"><i class="bms-icon-delete"></i></a>


													<div style="clear: both;"></div>

													<pre id="imageConsole" style="display: none"></pre>

													<div id="imageFilelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>

													<form:hidden path="entity.image" id="tagImage" />
												</div>


											</div>

										</div>

										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.imageLong"></spring:message>
											</label>
											<div class="controls">

												<div>
													<div class="border_img_2">
														<img id="imgProduct1" style="height: 100%;">
													</div>
												</div>

												<spring:message var="icon_view" code="icon.view"></spring:message>
												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_clone" code="icon.clone"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>

												<div>

													<div id="imageContainer1" style="float: left; position: relative; padding-left: 20px;">
														<a id="imgPickfiles1" href="javascript:void(0)" title="${icon_edit }" style="width: 20px; height: 80px; background-color: yellow;"><i
															class="bms-icon-edit"></i></a>
													</div>
													<a onclick="deleteImage1();" title="${icon_delete }"><i class="bms-icon-delete"></i></a>


													<div style="clear: both;"></div>

													<pre id="imageConsole1" style="display: none"></pre>

													<div id="imageFilelist1">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>

													<form:hidden path="entity.imageLong" id="tagImage1" />
												</div>


											</div>

										</div>


										<div class="control-group">
											<label for="type" class="control-label"> <spring:message code="cms.imageLong.EN" />
											</label>
											<div class="controls">
												<div class="border_img_2">
													<img id="imgProduct2" style="height: 100%;" src="${url}/ajax/download?fileName=${bean.entity.image_long_en}">
												</div>
												<div>
													<div id="imageContainer2" style="float: left; position: relative; padding-left: 20px;">
														<a id="imgPickfiles2" href="javascript:void(0)" title="${icon_edit }" style="width: 20px; height: 80px; background-color: yellow;"><i
															class="bms-icon-edit"></i></a>
													</div>
													<a onclick="deleteImage2();" title="${icon_delete }"><i class="bms-icon-delete"></i></a>
													<div style="clear: both;"></div>
													<pre id="imageConsole2" style="display: none"></pre>
													<div id="imageFilelist2">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
													<form:hidden path="entity.image_long_en" id="tagImage2" />
												</div>
											</div>
										</div>









									</div>
									<div class="row-fluid">
										<div class="span11">
											<ext-form:input-textarea path="entity.shareComment" id="sharecomment" cssInput="width_area_unit" labelCode="cms.sharecomment">
											</ext-form:input-textarea>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span11">
											<ext-form:input-textarea path="entity.shortDescription" id="entity_shortDescription" cssInput="width_area_unit" labelCode="cms.short">
											</ext-form:input-textarea>
										</div>
									</div>

									<div class="row-fluid">
										<label for="type" class="control-label" style="width: 145px"> <spring:message code="cms.content" /> <span class="required">*</span>
										</label>
										<div class="span9 title_h2" style="width: 819px; padding-left: -40px;">

											<textarea cols="10" id="editor1" name="entity.content" rows="">${bean.entity.content}</textarea>
											<ckeditor:replace replace="editor1" basePath="${url}/static/ckeditor_4.4.3_full/" />

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
		<div class="row-fluid text-center">
			<div class="accordion" id="accordion">
				<div class="accordion-group">
					<input type="hidden" name="action" value="edit" />
					<spring:message var="save_btn_msg" code="button.save"></spring:message>
					<spring:message var="return_btn_msg" code="button.return"></spring:message>

					<sec:authorize access="!hasAnyRole('R010')">
						<input type="button" onclick="validatorForm()" value="${save_btn_msg}" class="btn_search_general" />
						<c:if test="${bean.entity.messageId eq null }">
							<button type="button" class="btn_search_general" id="btnPushMessageAndSave">Push message and save</button>
						</c:if>
					</sec:authorize>
				</div>
			</div>
		</div>
		<form:hidden path="entity.cmsId" />
		<input type="hidden" name="isPushWhenSave" id="isPushWhenSave">
		<input type="hidden" name=pushTitle id="pushTitle">
		<input type="hidden" name=pushDescription id="pushDescription">

	</form:form>

</div>

<div id="previewDetailPopup" class="modal hide fade assetPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	data-width="1000"></div>


<script type="text/javascript">
  function backList() {
    document.location.href = "${url}/master_data/cms/list";
  }
  var formatNumber = "${sessionScope.formatNumber}";

  $(document).ready(
          function() {

            try {
              CKFinder.setupCKEditor(null, {
                basePath: '${url}/static/ckfinder/',
                rememberLastFolder: false
              });
            } catch (err) {

            }

            $("#btnPushMessageAndSave").click(function() {
              //Show popup push message
              $.ajax({
                url: '${url}/master_data/cms/loadPushCMSPopup',
                cache: false,
                type: "GET",
                data: {
                  pushTitle: $("#entity_title").val(),
                  pushDescription: $("#entity_shortDescription").val(),
                  'entity.cmsId': '${bean.entity.cmsId}'
                },
                async: false,
                success: function(resp) {
                  $('#previewDetailPopup').html(resp);
                  $('#previewDetailPopup').modal({
                    backdrop: 'static',
                    keyboard: false
                  })
                }
              });

            });

            $("#chkAllTeamLeft").click(function() {
              $("input[name=chkTeamLeft]").each(function() {
                this.checked = $("#chkAllTeamLeft").is(':checked');
              });
            });
            $("#chkAllTeamRight").click(function() {
              $("input[name=chkTeamRight]").each(function() {
                this.checked = $("#chkAllTeamRight").is(':checked');
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
              $("#imgProduct").attr("src", "${url}/ajax/download?fileName=" + image);
            }

            //init image uploader
            var uploadUrl = "${url}/ajax/uploadTemp";
            var imagePickfiles = 'imgPickfiles';
            var imageContainer = 'imageContainer';
            var imageMaxFileSize = '7mb';
            var imageMimeTypes = [{
              title: "Image files",
              extensions: "jpg,bmp,png"
            }];
            var imageFileList = 'imageFilelist';
            var imageConsole = 'imageConsole';
            var imageFileUploaded = function(up, file, info) {
              $("#tagImage").val(cutString(info.response));
            };

            var imageUploadComplete = function(up, files) {
              var lstImg = $("#tagImage").val();

              $("#imgProduct").attr("src", "${url}/ajax/download?fileName=" + lstImg);
              $("#" + imageConsole).hide();
              $("#" + imageFileList).hide();
            };
            InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList, imageConsole,
                    imageFileUploaded, imageUploadComplete, '${url}');

            // IMAGE signature
            var image1 = $("#tagImage1").val();
            if (image1 != "") {
              $("#imgProduct1").attr("src", "${url}/ajax/download?fileName=" + image1);
            }

            //--------------------------------------------------------------
            //init image uploader
            var uploadUrl1 = "${url}/ajax/uploadTemp";
            var imagePickfiles1 = 'imgPickfiles1';
            var imageContainer1 = 'imageContainer1';
            var imageMaxFileSize1 = '7mb';
            var imageMimeTypes1 = [{
              title: "Image files",
              extensions: "jpg,bmp,png"
            }];
            var imageFileList1 = 'imageFilelist1';
            var imageConsole1 = 'imageConsole1';
            var imageFileUploaded1 = function(up, file, info) {
              $("#tagImage1").val(cutString(info.response));
            };

            var imageUploadComplete1 = function(up, files) {
              var lstImg1 = $("#tagImage1").val();

              $("#imgProduct1").attr("src", "${url}/ajax/download?fileName=" + lstImg1);
              $("#" + imageConsole1).hide();
              $("#" + imageFileList1).hide();
            };
            InitPlupload(imagePickfiles1, imageContainer1, uploadUrl1, false, imageMaxFileSize1, imageMimeTypes1, imageFileList1, imageConsole1,
                    imageFileUploaded1, imageUploadComplete1, '${url}');

            //--------------------------------------------------------------
            //init image uploader
            var uploadUrl2 = "${url}/ajax/uploadTemp";
            var imagePickfiles2 = 'imgPickfiles2';
            var imageContainer2 = 'imageContainer2';
            var imageMaxFileSize2 = '7mb';
            var imageMimeTypes2 = [{
              title: "Image files",
              extensions: "jpg,bmp,png"
            }];
            var imageFileList2 = 'imageFilelist2';
            var imageConsole2 = 'imageConsole2';
            var imageFileUploaded2 = function(up, file, info) {
              $("#tagImage2").val(cutString(info.response));
            };

            var imageUploadComplete2 = function(up, files) {
              var lstImg2 = $("#tagImage2").val();

              $("#imgProduct2").attr("src", "${url}/ajax/download?fileName=" + lstImg2);
              $("#" + imageConsole2).hide();
              $("#" + imageFileList2).hide();
            };
            InitPlupload(imagePickfiles2, imageContainer2, uploadUrl2, false, imageMaxFileSize2, imageMimeTypes2, imageFileList2, imageConsole2,
                    imageFileUploaded2, imageUploadComplete2, '${url}');

            //--------------------------------------------------------------

            $('.multiselectComment').multipleSelect({
              filter: true,
              noMatchesFound: '${no_matches_found}'
            });
            $("#accordion2 .ms-parent").mouseenter(function() {
              if ($("#collapseOne").attr("class") == "accordion-body in collapse") {
                $("#collapseOne").attr("class", "accordion-body in");
              }
            });
          });
  function myFormatNumber(id) {
    console.log(id);
    $("#" + id).parseNumber({
      format: formatNumber,
      locale: "${sessionScope.localeSelect}"
    });
    $("#" + id).formatNumber({
      format: formatNumber,
      locale: "${sessionScope.localeSelect}"
    });
  }
  $("#usernameId").change(function() {
    alert('<spring:message code="account.field.username.nochange"></spring:message>');
  });

  $("#saveEdit").click(function() {
    $("input[name=chkTeamLeft]").each(function() {
      this.checked = true;
    });
    var url = "${url}/system/account/edit?u_id=" + $.now();
    $.ajax({
      url: url,
      data: $('#approval_info_form,#edit_form').serialize(),
      dataType: 'text',
      type: 'POST',
      cache: false,
      success: function(data) {
        $("#usernameId").focus();
        var success = $(data).find("#rs11").val();
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
  function deleteImage1() {
    $("#tagImage1").val("");
    $("#imgProduct1").attr("src", "");
  }
  function deleteImage2() {
    $("#tagImage2").val("");
    $("#imgProduct2").attr("src", "");
  }

  function validatorForm() {

    $("span.error").text("");
    var isError = false;
    var editor_valUndefined = CKEDITOR.instances.editor1.document.getBody().getChild(0);

    $("#catelory").attr("class", "multiselectComment span7");
    if ($("#category").val() == null) {
      $(".catelory").attr("class", "span7 errorfeborder multiselectComment ms-parent");
      isError = true;
    }

    $("#entity_title").attr("class", "width_area_unit");
    if ($("#entity_title").val() == '') {
      $("#entity_title").attr("class", "width_area_unit errorfeborder");
      isError = true;
    }

    /* $("#entity_tag").attr("class", "span7");
    if($("#entity_tag").val() == ''){
    	$("#entity_tag").attr("class", "span7 errorfeborder");
    	isError = true;
    } */
    /* $("#entity_shortDescription").attr("class", "width_area_unit");
    if($("#entity_shortDescription").val() == ''){
    	$("#entity_shortDescription").attr("class", "width_area_unit errorfeborder");
    	isError = true;
    } */

    if (editor_valUndefined == undefined) {
      $("#cke_editor1").attr("class", "cke_1 cke cke_reset cke_chrome cke_editor_editor1 cke_ltr cke_browser_webkit errorfeborder");
      isError = true;
    }

    var editor_val = CKEDITOR.instances.editor1.document.getBody().getChild(0).getText();

    $("#cke_editor1").attr("class", "cke_1 cke cke_reset cke_chrome cke_editor_editor1 cke_ltr cke_browser_webkit");
    if (editor_val == '') {
      $("#cke_editor1").attr("class", "cke_1 cke cke_reset cke_chrome cke_editor_editor1 cke_ltr cke_browser_webkit errorfeborder");
      isError = true;
    }

    if (isError == false) {
      $("#edit_form").submit();
    }
    //return true;
  }

  function pushBroadcast() {

    $("#isPushWhenSave").val(1);
    $("#pushTitle").val($("#pushTitlePopup").val());
    $("#pushDescription").val($("#pushDescriptionPopup").val());

    validatorForm();

    //// CongDT : dont remove below code
    //     $.ajax({
    //       url: '${url}/master_data/cms/loadPushCMSPopup',
    //       cache: false,
    //       type: "POST",
    //       data: $("#frmPush").serialize(),
    //       async: true,
    //       success: function(resp) {
    //         $('#previewDetailPopup').html(resp);
    //       }
    //     });

  }
</script>
