<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<!--start of account-upload-file-->
<style>
.account-upload-file{
	width:488px;
	height:auto;
	padding:8px 10px;
	/*border:1px dashed #cccccc;
	border-radius:3px;*/
	float:left;
	background:#008345;
	}
.upload-file{
	width:362px;
	float:left;
	margin:0px 0px 0px;
	}
.upload-file p{
	color:#fff;
	float:left;
	margin:0px;
	padding:0px;
	font-size:15px;
	
	}
.upload-user{
	width:111px!important;
	height:24px;
	box-shadow:0px 0px 3px #333;
	color:#fff;
	font-size:14px;
	border:none;
	background:#9f9f9f;
	border-radius:4px;
	font-weight:normal;
	margin:0px 0px 0px 8px;
	float:right;
	}
.choosen-file{
	width: auto;
    float: right;
	}
.choosen-file p{
	color:#fff;
	float:left;
	margin:0px;
	padding:0px;
	font-size:13px;
	}


.filebutton {
    overflow:hidden;
    position:relative;
    background-color:#fff;
	/*width:173px;
	height:35px;*/
	box-shadow:0px 0px 3px #333;
	color:#008345;
	font-size:14px;
	font-weight:normal;
	border:none;
	border-radius:4px;
	margin:0px 0px 0px 8px;
	padding:2px 21px;
	font-family:Verdana, Geneva, sans-serif;
	float:right;
}

.filebutton span input {
    z-index: 999;
    line-height: 0;
    font-size: 50px;
    position: absolute;
    top: -2px;
    left: -700px;
    opacity: 0;
    filter: alpha(opacity = 0);
    -ms-filter: "alpha(opacity=0)";
    cursor: pointer;
    _cursor: hand;
    margin: 0;
    padding:0;
}
.mL0{
	margin:13px 0px 0px 0px!important;
	}
.mL1{
	margin-left:0px!important;
	float:right;
	}
.upload-txt{
	position:relative;
	top:3px;
	height:auto!important;
	}
.new_text{
	background:url(pimgpsh_fullsize_distr.png) no-repeat;
	float:right;
}
#noFileChoosen{
    color: #FFF;
    margin: 0px 0px 0px 12px;
    position: relative;
    top: 3px;
}

</style>

 <!--end of account-upload-file-->
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script>
	$(document).ready(function() {
		$("#chkDeleteAll").click(function() {
			$("input[name=chkDelete]").each(function() {
				this.checked = $("#chkDeleteAll").is(':checked');
				});
			});
			document.getElementById("action").value = "search";
			$("#del").click(function() {
				if (!confirm("${msg_deleteQuestion}")) {
					return false;
				}
				document.getElementById("action").value = "delete";
				$("#search_form").submit();
			});
			$("#new").click(function() {
				document.location.href = "${url}/system/user/edit";
			});

			$("#stockCombobox").change(function() {
				$("#projectCombobox option").remove();
				$.ajax({url : '${url}/system/user/project_json_codeOrNameAndStockListId?parent='+ $("#stockCombobox").val(),
					dataType : 'json',
					type : 'GET',
					cache : false,
					success : function(data) {
						$.each(data,function(i,object) {
							if (object.projectId == null) {
								$("#projectCombobox").append("<option value=''>"+ "</option>");
							} else {
								$("#projectCombobox").append("<option value='"+object.projectId+"'>"+ object.name+ "</option>");
							}
						});
					}
				});
			});

	});
	function deleteCallBack(result) {
		if (result) {
			$('#action').val("delete");
			$("#search_form").submit();
		}
	}
	function deleteItem() {
		confirmMessage('${msg_deleteQuestion}', deleteCallBack);
	}
	function newItem() {
		document.location.href = "${url}/system/user/edit";
	}
	function importItem() {
		document.location.href = "${url}/system/user/listsync/";
	}
</script>

<script>
	function blackUser() {
		document.location.href = "${url}/system/user/list";
	}
	function black() {
		document.location.href;
	}
	function submitUploadUser(){
		var x = document.getElementById("usersExcel");
	    var txt = "";
	    if ('files' in x) {
	        if (x.files.length == 0) {
	            txt = "Select one file to upload.";
	            alert(txt);
	            return false;
	        }
	    }
	}
	function uploadUser(inputFile){		    	        
       		var sFileName = inputFile.value;
        	var _validFileExtensions = [".xls", ".xlsx"]; 
        	var sizeinbytes = document.getElementById('usersExcel').files[0].size;
		    var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
		    fSize = sizeinbytes;
			i=0;
			/* if(sFileName.length > 20){
				document.getElementById("noFileChoosen").innerHTML = 'No File Choosen';
                alert("File name is to long, Please rename!")
                return false;				
			} */
			while(fSize>900){
				fSize/=1024;
				i++;
			}
			
			if((fSExt[i] == 'MB' && (Math.round(fSize*100)/100)>3) || fSExt[i] == 'GB'){
				inputFile.value = "";
                document.getElementById("noFileChoosen").innerHTML = 'No File Choosen';
                alert("You can upload maximum 3 MB file");
                return false;
				
			}
        	var blnValid = false;
            for (var j = 0; j < _validFileExtensions.length; j++) {
                var sCurExtension = _validFileExtensions[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    blnValid = true;
                    var uploadedFileName = sFileName.split('\\').pop().split('/').pop();            
                    document.getElementById("noFileChoosen").innerHTML = uploadedFileName.substr(0, 7) +"...."+uploadedFileName.substr(-9);//sFileName.split('\\').pop().split('/').pop();
                    break;
                }
            }
            if (!blnValid) {
                inputFile.value = "";
                document.getElementById("noFileChoosen").innerHTML = 'No File Chosen';
                alert("Sorry, File is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                return false;
            }	        
	    
	}
</script>
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				<spring:message var="menu_system_user" code="menu.system.user"></spring:message>
				<spring:message var="menu_system_user_list" code="user.page.list.title"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"><c:out value="${menu_admin }"></c:out></a><span> > </span>
					<a onclick="blackUser()" class="Color_back"><c:out value="${menu_system_user }"></c:out></a> <span> > </span>
					<span  class="Colorgray"><c:out value="${menu_system_user_list }"></c:out></span>
				</h4>
			</div>

			<div class="span6 mL1">
				<div class="menu_images">					
					<ul style="width:586px;">
						<spring:message var="msg_Import" code="button.import"></spring:message>
						<c:if test="${checkLdapSyn == 1 }">
							<li class="import"><a href="javascript:importItem()"
								title="${msg_Import}"><span class="new_text"></span></a></li>
						</c:if>
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>
						<li class="new" style = "float:right!important; margin:7px 0px 0px;">
							
							<a href="javascript:newItem()" title="${msg_buttonAdd }"><span class="new_text"></span></a>
						</li>
					</ul>
				</div>
			</div>
			<div class="span12 mL0">
				<div class="menu_images">					
					<ul style="float: left;">
						
						
						
						<li class="new" style="width:505px;">
							<form id="command" action="${url}/system/user/upload" method="post" enctype="multipart/form-data">
                            
                            <!--start of account-upload-file-->
                            
							<div class="account-upload-file">
							<div class="upload-file" >
							<p><span class="upload-txt">Upload User:</span>
							<label class="filebutton">
							Choose File
							<span><input type="file" name="usersExcel" id="usersExcel" onchange="uploadUser(this)"></span>
							</label>
							</p>
							<span id="noFileChoosen">No File Chosen</span><span id="fileName"></span>
							</div>
							<div class="choosen-file">
							<p><input type="submit" value="Upload" id="uploadButton" class="upload-user" onclick="return submitUploadUser()"/></p>
							</div>
							</div>

 <!--end of account-upload-file-->
								<!-- Upload User: <input name="usersExcel" id="usersExcel" type="file"><input type="submit" value="Upload" onclick="uploadUser()"> -->
							</form>
							
						</li>
					</ul>
				</div>
			</div>
			

		</div>
	</div>
</div>

<div class="container-fluid unit_bg_content_user_list">
	<div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message var="list_title" code="user.page.list.title"></spring:message>
				<c:out value="${list_title }"></c:out>
			</h2>

		</div>
	</div>

	<div class="row-fluid">

		<div class="well_content row-fluid">

			<form:form method="POST" id="search_form" cssClass="form-horizontal"
				modelAttribute="bean" acceptCharset="UTF-8">
				<ext:messages bean="${accountBean}"></ext:messages>
				<div class="row-fluid ">
					<div class="accordion-group">
						<div class="accordion-heading">
							<div class="row-fluid">
								<div class="span2 title1">
									<h3>

										<spring:message code="search.area"></spring:message>
									</h3>

								</div>
								<div class="span1 unit_accordion" style="text-align: right;">
									<a class="accordion-toggle" data-toggle="collapse"
										data-parent="#accordion2" href="#collapseOne"><i
										class="bms-icon-accordion-down bms-icon-accordion"></i></a>
								</div>
							</div>
						</div>
						<div id="collapseOne"
							class="accordion-body collapse in border-group">
							<div class="accordion-inner">
								<div class="row-fluid">
									<div class="span8"></div>
									<div class="span12">
										<div class="span6">
											<form:hidden path="entity.id" />
											<ext-form:input-text path="entity.fullName" cssInput="span10" labelCode="user.field.fullName"></ext-form:input-text>
										</div>
										<div class="span6">
											<ext-form:input-text path="entity.email" cssInput="span10" labelCode="user.field.email"></ext-form:input-text>
										</div>
									</div>
									<div class="span12">
										<div class="span6">
											<ext-form:input-text path="entity.userCode" cssInput="span10" labelCode="user.field.userCode"></ext-form:input-text>
										</div>
										<div class="span6">
											<ext-form:input-text path="entity.mobile" cssInput="span10" labelCode="user.field.mobile"></ext-form:input-text>
										</div>
									</div>
								</div>
							</div>
							<div class="text-center">
								<input type="hidden" id="action" name="action" value="search" />
								<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
								<button type="button"
									onclick="submitAndSetHiddenVal('search_form',{'page':'1','maxPage':'5'})"
									class="btn_search_general" name="search">${msg_buttonSearch }</button>
							</div>
						</div>
					</div>

				</div>
				<c:if test="${fn:length(result) > 0}">
				<div class="row-fluid">
					<div class="span12 bg_round_table">
						<div id="search_result_table">
							<table class="table table-bordered table-hover out-tbl">
								<thead>
									<tr>
										<td colspan="9" style="padding: 0px; margin: 0px;">
											<div class="title_table row-fluid">
												<div class="span6 title">
													<h3>

														<spring:message var="search_title" code="msg.search.title"></spring:message>
														<c:out value="${search_title }"></c:out>
													</h3>
												</div>
												<div class="span6">
													<ext:pagination bean="${bean}" maxPage="5"
														formId="search_form"></ext:pagination>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<th><spring:message code="system.field.serial"></spring:message></th>
										<th><spring:message code="user.field.userCode"></spring:message></th>
										<th><spring:message code="user.field.fullName"></spring:message></th>										
										<th><spring:message code="user.field.email"></spring:message></th>
										<th><spring:message code="user.field.regionCode"></spring:message></th>
										<th><spring:message code="user.field.provinceCode"></spring:message></th>
										<th><spring:message code="user.field.lineManager"></spring:message></th>										
										<th><spring:message var="msg_action" code="actions" /> <c:out value="${msg_action }"></c:out></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="false">
										<tr>
											<td colspan="20" style="text-align: center"><spring:message code="msg.no.data" /></td>
										</tr>
									</c:if>
									
										<c:forEach var="_user" items="${result }" varStatus="i">
											<%-- <tr> ${_user[0]} | ${_user[1]} | ${_user[2]} | ${_user[3]} | ${_user[4]} | ${_user[5]} | ${_user[6]} | ${_user[7]} | ${_user[8]} | ${_user[9]}</tr> --%>
											<tr>
												<td class="text_center">${i.index+1}</td>
											<c:choose>
											    <c:when test="${fn:length(_user[9]) > 0}">
											        <td><c:out value="${_user[9] }"></c:out></td>
											    </c:when>    
											    <c:otherwise>
											       <td><c:out value="-"></c:out></td>
											    </c:otherwise>
											</c:choose>
												<td><c:out value="${_user[5] }"></c:out></td>
												<td><c:out value="${_user[1] }"></c:out></td>
																							<c:choose>
											    <c:when test="${fn:length(_user[10]) > 0}">
											        <td><c:out value="${_user[10] }"></c:out></td>
											    </c:when>    
											    <c:otherwise>
											       <td><c:out value="-"></c:out></td>
											    </c:otherwise>
											</c:choose>
												
												<spring:message var="icon_view" code="icon.view"></spring:message>
												<spring:message var="icon_edit" code="icon.edit"></spring:message>
												<spring:message var="icon_clone" code="icon.clone"></spring:message>
												<spring:message var="icon_delete" code="icon.delete"></spring:message>
												<%-- <td class="text_center"><c:out value="${_user[11] }"></c:out></td> --%>
												<c:choose>
											    <c:when test="${fn:length(_user[11]) > 0}">
											        <td><c:out value="${_user[11] }"></c:out></td>
											    </c:when>    
											    <c:otherwise>
											       <td><c:out value="-"></c:out></td>
											    </c:otherwise>
											</c:choose>
												<%-- <td class="text_center"><c:out value="${_user[8] }"></c:out></td>		 --%>		
																							<c:choose>
											    <c:when test="${fn:length(_user[8]) > 0}">
											        <td><c:out value="${_user[8] }"></c:out></td>
											    </c:when>    
											    <c:otherwise>
											       <td><c:out value="-"></c:out></td>
											    </c:otherwise>
											</c:choose>							
												<td class="table-actions">
													<a href="${url}/system/user/edit?id=${_user[0]}" title="${icon_edit}"><i class="bms-icon-edit"></i></a>
													<%-- <a href="${url}/system/user/delete?id=${_user[0]}" title="Delete"> <i class="bms-icon-delete"></i></a> --%>
												</td>												
											</tr>
										</c:forEach>
									
								</tbody>
							</table>
						</div>
						<div>
							<ext:pagination bean="${bean}" maxPage="10" formId="search_form"></ext:pagination>
						</div>
					</div>
				</div>
				</c:if>
			</form:form>
		</div>
	</div>
</div>


