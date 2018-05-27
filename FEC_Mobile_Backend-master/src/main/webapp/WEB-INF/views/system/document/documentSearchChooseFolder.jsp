<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<form:form method="POST" modelAttribute="bean" id="documentChooseFolder"
	cssClass="form-horizontal">
	<div class="modal-header">
		<button type="button" id="choose_folder_close" class="close" data-dismiss="modal"
		aria-hidden="true">×</button>
		<h3><spring:message code="document.field.selectrepository"/> </h3>
	</div>
	<div class="modal-body">
		<div class="row-fluid">
				<div class="span12">
					<ext-form:display cssClass="muted currentfolder" icon="icon-folder-open" labelCode="repository.field.Currentfolder" value="${bean.physicalUrl}" ></ext-form:display>
					<form:hidden path="" id="urlfolder" name="urlfolder"/>
				</div>
			</div>
		<div class="row-fluid">
		<div class="span12">
			<div class="control-group">
							<label icon="icon-edit" class="control-label" for="entity.repositoryName"> 
								<spring:message var="folderName_msg" code="repository.field.name"></spring:message>
								${folderName_msg } 
							</label>
							<div class="controls">	
								<div class="input-append span10">
									
								    <input class="span10" id="folderName" name="folderName" type="text" required="required">
								   <button class="btn" type="button" id="btnAddFolder" >New folder</button>
							    </div>
							</div>
						</div>
			</div>
			</div>
		<div class="row-fluid">
			<div class="treeFolder">
					
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$('#btnAddFolder').click(function(){
		var values = $("#documentChooseFolder").serializeArray();
		values.push({
		name: "uniq_param",
		value: (new Date()).getTime()
		});
		values = jQuery.param(values);
		$.ajax({
		    url: '${url}/system/document/ajax_NewFolder?fragments=body',
		    data:  values,
		    dataType: 'text',
		    type: 'POST',
		    success : function(data){
		    	$("#modalFolder").html(data);
		    	ajaxUrl= '${url}/system/document/ajax_treeFolder?fragments=body';
				$.ajax({url:ajaxUrl,
					data : {
					'uniq_param' : (new Date()).getTime(),
					},
					success :function(result){
				    $(".treeFolder").html(result);
				    $('.treeFolder li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
				    $('.treeFolder li.parent_li > span').on('click', function (e) {
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
		
		return false;
	});
	$(function () {
		ajaxUrl= '${url}/system/document/ajax_treeFolder?fragments=body';
		$.ajax({url:ajaxUrl,
			data : {
			'uniq_param' : (new Date()).getTime(),
			},
			success :function(result){
		    $(".treeFolder").html(result);
		    $('.treeFolder li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Expand this branch');
		    $('.treeFolder li:has(ul)').find(' > ul > li').hide();
		    //$('.tree li:has(ul)').children('ul').attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
		    $('.treeFolder li.parent_li > span').on('click', function (e) {
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
	});
		$('#choose_folder').click(function(){
			
			$("#modalFolder").modal('hide');
			
			//return false;
		});
		
	</script>
</form:form>
<div class="modal-footer">

	<button id="choose_folder" class="btn btn-info"><spring:message code="msg.choose"/></button>
				<button id="choose_folder_close" class="btn btn-info" data-dismiss="modal" aria-hidden="true">
					<spring:message  code="button.close"/>
				</button>
</div>
