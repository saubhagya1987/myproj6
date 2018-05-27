<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<c:set var="entityid" value=""></c:set>
<form:form method="POST" modelAttribute="bean" id="documentAddFolder"
	cssClass="form-horizontal">
	<c:if test="${not empty bean.entity.repositoryId}">
		<c:set var="entityid" value="bean.entity.getRepositoryId"></c:set>
	</c:if>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">×</button>
		<h3><spring:message code="document.field.addfolder"/> </h3>
	</div>
	<div class="alert alert-error" id="addAlert" style="display: none">
		<strong></strong>
	</div>
	<div class="modal-body">
			<ext:messages bean="${bean}"></ext:messages>
			<div class="row-fluid">
				<div class="span12">
					<ext-form:display cssClass="muted" icon="icon-folder-open" labelCode="repository.field.Currentfolder" value="${bean.entity.parent.folderName}" ></ext-form:display>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<ext-form:input-text icon="icon-edit" path="entity.repositoryName" required="true"
						cssInput="span5" labelCode="repository.field.name"></ext-form:input-text>
				</div>
			</div>
			<div class="row-fluid">
				<div class="control-group">
							<label icon="icon-edit" class="control-label" for="entity.transferType" > 
								<spring:message var="folder_msg" code="repository.field.physicalfolder"></spring:message>
								${folder_msg } 
								<span class="required">*</span>
							</label>
							<div class="controls">	
								<div class="input-append span10">									
								    <%-- <input class="span10" id="fileImport" type="text" value="${fullyearbean.documentInfo.fileName }" readonly="readonly"> --%>
								    <input name="folderName" class="span10" id="linkFolder" type="text" required="required" readonly="readonly">
									
								   <button onclick="openChooseFolderPopup();return false" class="btn btn-info" type="button" id="btnChooseFolder" >Browser</button>
							    
							    </div>
							</div>
						</div>
			</div>
			<div class="row-fluid" style="clear: both">
				<span class="required">*</span>
				<spring:message var="required" code="msg.required"></spring:message>
				<c:out value="${required }"></c:out>
			</div>
			<form:hidden path="entity.parent.repositoryId"/>
			<form:hidden path="entity.repositoryId"/>
	</div>
		<spring:message var="msg_physical" code="msg.reponsitory.physical"></spring:message>
	<script type="text/javascript">
	$(document).ready(function() {
		
		// set foldername in EDIT case
		if('${entityid}' != ""){
			$("#linkFolder").val('${bean.entity.folderName}'); 
			$("#btnChooseFolder").attr("disabled","disabled");
		}
		
		$('#detail_save').click(function(){
			var values = $("#documentAddFolder").serializeArray();
			values.push({
			name: "uniq_param",
			value: (new Date()).getTime()
			});
			values = jQuery.param(values);
			if($("#linkFolder").val()==null ||$("#linkFolder").val()==""){
				//$("#addAlert").css({ 'display': "block" });
				$('#addAlert').show().find('strong').text('${msg_physical}');
			}else{
				
				$.ajax({
				    url: '${url}/system/document/ajax_add_folder?fragments=body',
				    data: values,
				    dataType: 'text',
				    type: 'POST',
				    success : function(data){
				    	$("#modalDocument").html(data);
				    	ajaxUrl= '${url}/system/document/ajax_treeRepository?fragments=body';
						$.ajax({url:ajaxUrl,
							data : {
							'uniq_param' : (new Date()).getTime(),
							},
							success :function(result){
						    $(".tree").html(result);
						    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
						    $('.tree li.parent_li > span').on('click', function (e) {
						        var children = $(this).parent('li.parent_li').find(' > ul > li');
						        if (children.is(":visible")) {
						            children.hide('fast');
						            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
						        } else {
						            children.show('fast');
						            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
						        }
						        e.stopPropagation();
						        
						    });
						  }});			    	 
				    }
				  });
			}
			
			return false;
		});
		
	});
	function openChooseFolderPopup(){
		  ajaxUrl= '${url}/system/document/ajax_choose_folder?fragments=body';
		
		$.ajax({url:ajaxUrl,
			data : {
			'uniq_param' : (new Date()).getTime(),
			},
			success :function(result){
		    $("#modalFolder").html(result);
		    $("#modalFolder").modal('show');
		  }});
	}
		
	</script>
</form:form>
<div class="modal-footer">

	<button id="detail_save" class="btn btn-info"><spring:message code="button.save"/></button>
				<button id="detail_close" class="btn btn-info" data-dismiss="modal" aria-hidden="true">
					<spring:message  code="button.close"/>
				</button>
</div>
<div id="modalFolder" class="modal hide fade" tabindex="-1"
	role="dialog" aria-labelledby="modalFolderLabel" aria-hidden="true"></div>