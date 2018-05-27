<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
<script src="${url}/static/js/vendor/jquery.ui.widget.js"></script>
<script src="${url}/static/js/jquery.iframe-transport.js"></script>
<script src="${url}/static/js/jquery.fileupload.js"></script>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>

<link rel="stylesheet" type="text/css"
	href="${url}/static/css/tagmanager.css" />
<script type="text/javascript" src="${url}/static/js/tagmanager.js"></script>


<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<!-- Modal -->

<div class="row-fluid">
	<div class="span12">
		<form:form method="POST" modelAttribute="documentbean"
			id="upload_form"
			action="${pageContext.request.contextPath}/system/documentinfo/edit"
			cssClass="form-horizontal" enctype="multipart/form-data">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<c:choose>
					<c:when test="${not empty documentbean.entity.documentId }">
						<spring:message var="msg_header" code="document.edit"></spring:message>
						<h3 id="myModalLabel">${msg_header}</h3>
					</c:when>
					<c:otherwise>
						<spring:message var="msg_header" code="document.add"></spring:message>
						<h3 id="myModalLabel">${msg_header}</h3>
					</c:otherwise>
				</c:choose>

			</div>
			<ext:messages bean="${documentbean}"></ext:messages>
			<div class="alert alert-error" id="alert">
				<strong></strong>
			</div>
			<spring:message var="msg_saving" code="document.save.to"></spring:message>
			<spring:message var="msg_browse" code="document.browse.link"></spring:message>
			<spring:message var="msg_title" code="document.title.val"></spring:message>
			<div class="modal-body">
				<div class="row-fluid">
					<c:if test="${documentbean.entity.documentId == null}">
						<div class="control-group">
							<spring:message var="msg_selectrepository"
								code="document.field.selectrepository"></spring:message>

							<div class="">

								<ext-form:input-combotree icon="icon-folder-open"
									path="repositoryId"
									url="${pageContext.request.contextPath}/system/documentinfo/json_tree_expense_category"
									required="true" labelCode="document.field.selectrepository"
									id="repositoryId"></ext-form:input-combotree>

							</div>
						</div>
					</c:if>
					<c:if test="${documentbean.entity.documentId != null}">
						<ext-form:display value="${repositoryName }"
							labelCode="document.field.selectrepository"></ext-form:display>
						<form:hidden path="repositoryId" id="repositoryId11" />

					</c:if>

				</div>
				<div class="row-fluid">
					<c:choose>
						<c:when test="${not empty documentbean.entity.documentId }">
							<div class="control-group">
								<spring:message var="msg_fileName"
									code="document.field.fileName"></spring:message>
								<label class="control-label"> <i class="icon-edit"></i>${msg_fileName
									}
								</label>
								<div class="controls">
									<label class="checkbox">
										${documentbean.entity.fileName} </label>
									<div style="display: none">
										<ext-form:input-file icon="icon-file" path="entity.fileUpload"
											id="fileUpload" dataurl="${url}/ajax/uploadDoc"
											required="true" cssInput="span10"
											labelCode="document.field.selectfile"
											errorpath="entity.fileName"></ext-form:input-file>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<ext-form:input-file icon="icon-file" path="entity.fileUpload"
								id="fileUpload" dataurl="${url}/ajax/uploadDoc" required="true"
								cssInput="span10" labelCode="document.field.selectfile"
								errorpath="entity.fileName"></ext-form:input-file>
						</c:otherwise>
					</c:choose>


				</div>


				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text icon="icon-edit" path="entity.title"
							id="title" required="true" cssInput="span10"
							labelCode="document.field.title"></ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<ext-form:input-text icon="icon-edit" path="entity.keywords"
						placeholder="Tags" cssInput="span10 tm-input"
						labelCode="document.field.keywords"></ext-form:input-text>

				</div>
				<div class="row-fluid">
					<ext-form:input-textarea icon="icon-edit" path="entity.bComment"
						cssInput="span10" labelCode="document.field.comment"></ext-form:input-textarea>

				</div>
				<form:hidden path="parentPage" />
				<form:hidden path="entity.documentSource" />
				<form:hidden path="entity.referenceId" />
				<form:hidden path="entity.documentId" />
				<input type="hidden" value="${documentInfoIdTemp }"
					id="documentIdEdit" />
				<form:hidden path="entity.referenceNo" />
				<input type="hidden" value="${param.visible }" name="visible" />
			</div>
			<!-- <div id="progress" class="progress">
				<div class="bar" style="width: 0%;"></div>
			</div> -->

			<div class="modal-footer">


				<c:choose>
					<c:when test="${not empty documentbean.entity.documentId }">
						<spring:message var="msg_buttonAdd" code="button.savechanges"></spring:message>
					</c:when>
					<c:otherwise>
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>
					</c:otherwise>
				</c:choose>

				<button type="button" id="add" class="btn btn-info"
					>${msg_buttonAdd }</button>

				<spring:message var="msg_buttonClose" code="button.close"></spring:message>
				<button class="btn btn-info" data-dismiss="modal" id="btnClose"
					aria-hidden="true">${msg_buttonClose}</button>
			</div>
		</form:form>
	</div>
</div>

<script type="text/javascript">
	$('#alert').hide();
	var $j = jQuery.noConflict();
	var prefilledTags = '${documentbean.entity.keywords}'.split(",");
	$j(".tm-input").tagsManager({
		prefilled : prefilledTags
	});
	$j("#add").click(function() {
						var fileName = null;
						if($('#title').val()==''){
							//$('#alert').show().find('strong').text('${msg_title}');
						}else{
							//$('#alert').hide();
							fileName = $j("#fileUpload").val();
						
						if ('${documentbean.entity.documentId}' != '') {
							//alert('${documentbean.entity.documentId}');
							var values = $("#upload_form").serializeArray();
							values.push({
								name : "entity.fileName",
								value : '${documentbean.entity.fileName}'
							});
							values.push({
								name : "entity.fileSize",
								value : '${documentbean.entity.fileSize}'
							});
							values.push({
								name : "entity.fileType",
								value : '${documentbean.entity.fileType}'
							});
							values
									.push({
										name : "entity.physicalFileName",
										value : '${documentbean.entity.physicalFileName}'
									});
							values.push({
								name : "uniq_param",
								value : (new Date()).getTime()
							});
							values = jQuery.param(values);

							console.log(values);
							$j.ajax({
										url : '${url}/system/documentinfo/edit?fragments=body',
										data : values,
										dataType : 'text',
										type : 'POST',
										success : function(data) {
											var id =($(data).find("#documentIdEdit").val());													
											updateListDocumentArrayId(id);
											var listArray=returnListDocumentArrayId();													
											$j("#myModal").html(data);
											var ajaxUrl = '${url}/system/documentinfo/ajaxlist?listDocumentArrayId='+listArray+'&fragments=body&entity.documentSource=${documentbean.entity.documentSource}&entity.referenceId=${documentbean.entity.referenceId}&visible=${param.visible}&checkPR=${param.checkPR}&levelPR=${param.levelPR}';
											$j.ajax({
														url : ajaxUrl,
														data : {
															'uniq_param' : (new Date())
																	.getTime(),
														},
														success : function(
																result) {
															$j("#uploaded-files").html(result);

														}
											});
										}
									});
						}
						$('#btnClose').click();
						}
					})

	$j(function() {
		$j('#fileUpload').fileupload({
							forceIframeTransport : true,
							dataType : "json",
							datatype : "json",
							add : function(e, data) {
								$j('#add').click(function() {
									data.submit();
								});
							},
							done : function(e, data) {

								if (data.result) {

									var values = $j("#upload_form")
											.serializeArray();
									values.push({
										name : "entity.fileName",
										value : data.result.fileName
									});
									values.push({
										name : "entity.fileSize",
										value : data.result.fileSize
									});
									values.push({
										name : "entity.fileType",
										value : data.result.fileType
									});
									values.push({
										name : "entity.physicalFileName",
										value : data.result.physicalFileName
									});
									values.push({
										name : "uniq_param",
										value : (new Date()).getTime()
									});
									values = jQuery.param(values);
									$j.ajax({
												url : '${url}/system/documentinfo/edit?fragments=body',
												data : values,
												dataType : 'text',
												type : 'POST',
												success : function(data) {													
													var id =($(data).find("#documentIdEdit").val());													
													updateListDocumentArrayId(id);
													var listArray=returnListDocumentArrayId();													
													$j("#myModal").html(data);
													var ajaxUrl = '${url}/system/documentinfo/ajaxlist?listDocumentArrayId='+listArray+'&fragments=body&entity.documentSource=${documentbean.entity.documentSource}&entity.referenceId=${documentbean.entity.referenceId}&visible=${param.visible}&checkPR=${param.checkPR}&levelPR=${param.levelPR}';
													$j.ajax({
																url : ajaxUrl,
																data : {
																	'uniq_param' : (new Date())
																			.getTime(),
																},
																success : function(
																		result) {
																	$j("#uploaded-files").html(result);

																}
													});
												}
											});
								}
							},

							progressall : function(e, data) {
								var progress = parseInt(data.loaded
										/ data.total * 100, 10);
								$j('#progress .bar').css('width',
										progress + '%');
							},

						});
	});
	function comboTree(currentNodeId) {
		var urlCombotree = '${url}' + '/system/repository/json_tree';
		if (currentNodeId == null)
			currentNodeId = '';
		urlCombotree += '?currentNode=' + currentNodeId;
		$('#repositoryId').combotree({
			url : urlCombotree,
			method : 'get'
		});

	}
	
	$(document).ready(function() {
		
		if ('${documentbean.entity.documentId}' != '') {
			if($("#upload_form").find("#title").val() != ""){
				$("#upload_form").find("#add").show();
			}
		}else{
			if($("#upload_form").find("#fileUploadpath").val() != "" && $("#upload_form").find("#title").val() != ""){
				$("#upload_form").find("#add").show();
			}else{
				$("#upload_form").find("#add").hide();
			}
			var acc = $("#fileUploadpath").val();
			$("#fileUploadpath").val(acc.replace("C:\\fakepath\\", ""));
		}

		$("#upload_form").find("#fileUpload,#title").change(function(){		
			if ('${documentbean.entity.documentId}' != '') {
				if($("#upload_form").find("#title").val() != ""){
					$("#upload_form").find("#add").show();
				}
			}else{
				if($("#upload_form").find("#fileUploadpath").val() != "" && $("#upload_form").find("#title").val() != ""){
					$("#upload_form").find("#add").show();
				}else{
					$("#upload_form").find("#add").hide();
				}
				var acc = $("#fileUploadpath").val();
				$("#fileUploadpath").val(acc.replace("C:\\fakepath\\", ""));
			}
		});
		
		comboTree('${bean.entity.repositoryId}');
		$('#repositoryId').combotree('setValue', '${bean.parentId}');
	});
</script>
