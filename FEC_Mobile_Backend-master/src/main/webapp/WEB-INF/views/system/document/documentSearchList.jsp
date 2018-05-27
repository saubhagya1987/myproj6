<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<spring:message var="msg_deleteItemQuestion"
	code="msg.alert.delete.questionitem"></spring:message>

<!-- start title -->
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message var="document_title" code="menu.transaction.document"></spring:message>
					<c:out value="${document_title }"></c:out>
				</h2>
			</div>
			
		</div>
	</div>
</div>

<!-- and title -->

<div class="container-fluid unit_bg_content">
	<ext:messages bean="${beanDoc}"></ext:messages>
	<form:form method="POST" id="search_form" cssClass="form-horizontal"
		modelAttribute="beanDoc">
		<!-- start search -->
		<div class="row-fluid">
			<div class="span12 search">
				<div class="input-append">
					<form:input path="searchField" style="width: 400px;" />
					<form:hidden path="action" id="action" value="search" />
					<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
					<button type="submit"  class="btn btn-info" onclick="searchGT()" name="search">${msg_buttonSearch }</button>
				</div>
			</div>
		</div>
		<!-- and search -->
		<!-- start table -->
		<div class="row-fluid">
			<div class="span4">
				<table class="table table-bordered table-hover out-tbl">
					<thead>
						<tr>
							<td colspan="6" style="padding: 0px; margin: 0px;">
								<div class="title_table_document row-fluid">
									<div class="span12 title">
										<div class="menu_images">
											<ul style="float: right;">										
												<li class="newFolder">
													<a onclick="openAddFolderPopup(null);return false">
													<span class="new_text"></span></a>
													<input type="hidden" id="hidRepositoryID" value="" name="hidRepositoryID" />
												</li>
												<li class="editFolder">
													<a onclick="openAddFolderPopup(true);return false">
													<span class="new_text"></span></a>
												</li>
										
											</ul>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</thead>
				</table>
				<!-- Tree repository -->
				<div class="tree">
					
				</div>
				
			</div>
			<div class="span8">
				<table class="table table-bordered table-hover out-tbl" id="tblFilterViewDocument">
					<thead>
						<tr>
							<spring:message var="msg_filename" code="document.field.fileName"></spring:message>
							<spring:message var="msg_ReferenceType" code="document.field.referencetype"></spring:message>
							<spring:message var="msg_ReferenceNo" code="transaction.field.refrenceno"></spring:message>
							<spring:message var="msg_uploadedBy" code="document.field.uploadBy"></spring:message>
							<th><ext:column-sort bean="${beanDoc}" fieldName="${msg_filename}"
									path="title" formId="search_form" /></th>
							<%-- <th><ext:column-sort bean="${beanDoc}" fieldName="${msg_ReferenceType}"
									path="title" formId="search_form" /></th> --%>
							<%-- <th><ext:column-sort bean="${beanDoc}" fieldName="${msg_ReferenceNo}" 
									path="title" formId="search_form" /></th> --%>
							<%-- <th><ext:column-sort bean="${beanDoc}" fieldName="${msg_uploadedBy}"
									path="title" formId="search_form" /></th> --%>
							<th class="table-actions" style="width:5%;"></th>		
						</tr>
					</thead>
					<tbody>
						<c:forEach var="_document" items="${beanDoc.listResult }"
							varStatus="i">
							<tr>
								<td>
									<c:choose>
								 		<c:when test="${empty beanDoc.searchField }">
								 			<i class="icon-file icon-red"></i> <a target="blank" title="${_document.title }" href="${url}/system/documentinfo/download?id=${_document.documentId }">
															<c:out value="${_document.title }"></c:out></a>
								 		</c:when>
								 		<c:otherwise>
								 			<i class="icon-file"></i> <a  title="${_document.title }" onclick="openViewDetailPopup('${_document.documentId}');return false;">
															<c:out value="${url}/${_document.repository.folderName }/${_document.fileName }"></c:out></a>
								 		</c:otherwise>
								 	</c:choose>
									</td>
								<%-- <td>
									<c:if test="${not empty _document.documentSource}">
										<c:if test="${not empty documentSource[_document.documentSource]}">
											<a href = "${url}/${documentSourceLink[_document.documentSource]}
											?id=${_document.referenceId}" target="_blank">
											<spring:message code="${documentSource[_document.documentSource]}" /></a>
										</c:if>
									</c:if>
								</td> --%>
								<%-- <td>
									<c:choose>
										<c:when test="${not empty _document.referenceNo}">									
											<a href = "${url}/${documentSourceLink[_document.documentSource]}?id=${_document.referenceId}" target="_blank"><c:out value="${_document.referenceNo }"></c:out></a>
										</c:when>
										<c:otherwise>									
											<a href = "${url}/${documentSourceLink[_document.documentSource]}?id=${_document.referenceId}" target="_blank"><c:out value="${_document.referenceId }"></c:out></a>
										</c:otherwise>
									</c:choose>
								</td> --%>
								<%-- <td><c:out value="${_document.account.fullName }"></c:out></td> --%>
								<td class="table-actions">
									<i class="bms-icon-view" onclick="openViewDetailPopup('${_document.documentId}')"></i>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="11" style="padding: 0px; margin: 0px;">
								<div class="title_table row-fluid">
									<div class="span6 title">&nbsp;</div>
									<div class="span6">
										<ext:pagination bean="${beanDoc}" maxPage="5"
											formId="search_form"></ext:pagination>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- and table -->
		
	</form:form>
</div>
<div id="modalDocument" class="modal hide fade" tabindex="-1"
	role="dialog" aria-labelledby="modalDocumentLabel" aria-hidden="true"
	data-width="800px" style="display: block; width: 793px;"></div>
<style type="text/css">
	.tree {
	    min-height:20px;
	    margin-bottom:20px;
	    background-color:#fbfbfb;
	    border:1px solid #999;
	    -webkit-border-radius:4px;
	    -moz-border-radius:4px;
	    border-radius:4px;
	    -webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    -moz-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    height:425px;
	    overflow: scroll;
	}
	.tree li {
	    list-style-type:none;
	    margin:0;
	    padding:5px 5px 0 5px;
	    position:relative
	}
	.tree li::before, .tree li::after {
	    content:'';
	    left:-20px;
	    position:absolute;
	    right:auto
	}
	.tree li::before {
	    border-left:1px solid #999;
	    bottom:50px;
	    height:100%;
	    top:0;
	    width:1px
	}
	.tree li::after {
	    border-top:1px solid #999;
	    height:20px;
	    top:25px;
	    width:25px
	}
	.tree li span {
	    -moz-border-radius:5px;
	    -webkit-border-radius:5px;
	    border:1px solid #999;
	    border-radius:5px;
	    display:inline-block;
	    padding:3px 8px;
	    text-decoration:none
	}
	.tree li.parent_li>span {
	    cursor:pointer
	}
	
	.tree>ul>li::before, .tree>ul>li::after {
	    border:0
	}
	.tree li:last-child::before {
	    height:30px
	}
	.tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span {
	    background:#eee;
	    border:1px solid #94a0b4;
	    color:#000
	}
	.tree li.parent_li span:focus, .tree li.parent_li span:active {
    background: #333; 

    }  
   
}

</style>
	<style type="text/css">
	.treeFolder {
	    min-height:20px;
	    margin-bottom:20px;
	    background-color:#fbfbfb;
	    border:1px solid #999;
	    -webkit-border-radius:4px;
	    -moz-border-radius:4px;
	    border-radius:4px;
	    -webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    -moz-box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.05);
	    height:300px;
	    overflow: scroll;
	}
	.treeFolder li {
	    list-style-type:none;
	    margin:0;
	    padding:5px 5px 0 5px;
	    position:relative
	}
	.treeFolder li::before, .treeFolder li::after {
	    content:'';
	    left:-20px;
	    position:absolute;
	    right:auto
	}
	.treeFolder li::before {
	    border-left:1px solid #999;
	    bottom:50px;
	    height:100%;
	    top:0;
	    width:1px
	}
	.treeFolder li::after {
	    border-top:1px solid #999;
	    height:20px;
	    top:25px;
	    width:25px
	}
	.treeFolder li span {
	    -moz-border-radius:5px;
	    -webkit-border-radius:5px;
	    border:1px solid #999;
	    border-radius:5px;
	    display:inline-block;
	    padding:3px 8px;
	    text-decoration:none
	}
	.treeFolder li.parent_li>span {
	    cursor:pointer
	}
	
	.treeFolder>ul>li::before, .treeFolder>ul>li::after {
	    border:0
	}
	.treeFolder li:last-child::before {
	    height:30px
	}
	.treeFolder li.parent_li>span:hover, .treeFolder li.parent_li>span:hover+ul li span {
	    background:#eee;
	    border:1px solid #94a0b4;
	    color:#000
	}
	.treeFolder li.parent_li span:focus, .treeFolder li.parent_li span:active {
    background: #333; 

    }  
   
}

</style>
<script type="text/javascript">
	
	$(function () {
		ajaxUrl= '${url}/system/document/ajax_treeRepository?fragments=body';
		$.ajax({url:ajaxUrl,
			data : {
			'uniq_param' : (new Date()).getTime(),
			}
			,success :function(result){
		    $(".tree").html(result);
		    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Expand this branch');
		    $('.tree li:has(ul)').find(' > ul > li').hide();
		    //$('.tree li:has(ul)').children('ul').attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
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
	});
	function openViewDetailPopup(id){
		if(id==null)
		  ajaxUrl= '${url}/system/document/ajax_detail_view?fragments=body';
		else
		  ajaxUrl= '${url}/system/document/ajax_detail_view?fragments=body&entity.documentId='+id;
		
		$.ajax({url:ajaxUrl,
			data : {
			'uniq_param' : (new Date()).getTime(),
			},
			success :function(result){
		    $("#modalDocument").html(result);
		    $("#modalDocument").modal('show');
		  }});
	}
	function openAddFolderPopup(edit){
		  var hid = $('#hidRepositoryID').val();
		  if(edit == null){
			  ajaxUrl= '${url}/system/document/ajax_add_folder?fragments=body&hid=' + hid;
		  }else{
			  ajaxUrl= '${url}/system/document/ajax_add_folder?fragments=body&hid=' + hid + '&edit=true';
		  }
		
		$.ajax({url:ajaxUrl,
			data : {
			'uniq_param' : (new Date()).getTime(),
			},
			success :function(result){
		    $("#modalDocument").html(result);
		    $("#modalDocument").modal('show');
		    
		  }});
	}
	
  function searchGT() {
    	 $('input:hidden[name="page"]').val('1');
   	}
	
</script>
