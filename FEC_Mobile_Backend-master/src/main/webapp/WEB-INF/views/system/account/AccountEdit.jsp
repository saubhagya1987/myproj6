
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
	document.location.href = "${url}/system/account/list";
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
			<spring:message var="menu_system_account" code="menu.system.account"></spring:message>
			<spring:message var="menu_account_addnew_edit" code="${not empty bean.entity.id?'account.page.edit.title':'account.page.add.title' }"></spring:message>
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
					<h2><spring:message var="list_title" code="${not empty bean.entity.id?'account.page.edit.title':'account.page.add.title' }"></spring:message>
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
							
							<spring:message var="list_detail" code="account.page.edit.detail"></spring:message>
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
									<form:hidden path="entity.id" id="accountid"/>
									<form:hidden path="entity.ldap" id="ldapid"/>

									<ext-form:input-text path="entity.username" cssInput="span10" required="true"
									  id="usernameId"
										labelCode="account.field.username" readonly="${not empty bean.entity.id? true : false }"></ext-form:input-text>
									</div>	
									
									<div>
									<ext-form:input-text path="entity.fullName" cssInput="span10" labelCode="account.field.fullName" required="true" ></ext-form:input-text>
									</div>
									
									
									<div>
									<c:if test="${bean.entity.id==null}">
									<div>
												<div class="control-group">
													<label for="entity.password" class="control-label" >
														 <spring:message code="login.password"></spring:message> <span class="required">*</span>
													</label>
													<div class="controls">
													 <input style="display: none;">
													 <form:password path="entity.password" cssClass="span10 " id="passwordId" autocomplete="off" cssErrorClass="span10 errorfeborder" />
													</div>
												</div>
									</div>
											
									
									</c:if>
									<c:if test="${bean.entity.id!=null}">
									<div>
												<div class="control-group">
													<label for="entity.password" class="control-label">
														 <spring:message code="login.password"></spring:message> <span class="required">*</span>
													</label>
													<div class="controls">
													
													<form:input id="A_password" type="password" path="entity.username" cssClass="span10"  disabled="true"></form:input>
													</div>
												</div>
									</div>
									
									</c:if>
									
									
									
									
									
									<fmt:formatDate var="var_birthday"
										value="${bean.entity.birthday}"
										pattern="${sessionScope.formatDate}" />
									<ext-form:input-date path="entity.birthday"
										id="var_birthday_id" value="${var_birthday}"
										labelCode="account.field.birthday" />

									</div>
													
									<%-- <ext-form:input-combotree path="entity.departmentId"
										url="${pageContext.request.contextPath}/system/account/department/json_tree?id=${dept.departmentId }"
										required="true" labelCode="dept.page.list.title"
										id="departmentId"></ext-form:input-combotree>		--%>		
									<div> 
									<ext-form:input-text path="entity.email" cssInput="span10"
										disable="false" required="true"
										labelCode="account.field.email"></ext-form:input-text>
									</div>
																	
									
									
								</div>
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

												<form:hidden path="image" id="tagImage" />
											</div>
										</div>
									</div>
								
									
								</div>
							</div>
							
							
							
							
						</div>
						<div  class="span12">
							<div class="span6">
											<ext-form:input-text path="entity.mobile" cssInput="span10"
												disable="false"
												labelCode="account.field.mobile"></ext-form:input-text>
							</div>	
							
					</div>
					<div  class="span12">
							<div class="control-group">
				<label for="type" class="control-label"> <spring:message
						code="hobby.Status" />
				</label>
				<div class="controls">

					<form:select id="statusTable.statustableId" cssClass=""
						path="entity.statusTable.status_tableId">
						<c:forEach items="${bean.listStatusTable}" var="j">
							<form:option value="${j.status_tableId}">${j.status_text}</form:option>
						</c:forEach>
					</form:select>


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
	<div class="row-fluid">
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span2 title1">
							<h3>
							
							<spring:message var="list_detail_role" code="msg.role.account"></spring:message>
							<c:out value="${list_detail_role }"></c:out>
							</h3>
						</div>
						<div class="span1 unit_accordion" style="text-align: right;">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapseOne1"><i
								class="bms-icon-accordion-down bms-icon-accordion"></i></a>
						</div>
					</div>
				</div>
				
				<!-- ROLES -->
					<div id="collapseOne1" class="accordion-body collapse in">		
							
							<div class="accordion-inner">
								
							<div class="row-fluid">	
								<div class="span12">
	
									<table style="width: 100%;">
										<tr>
											<td style="width: 5%">&nbsp;</td>
											<td style="width: 40%; vertical-align: top;">
	
												<table class="table table-bordered table-hover out-tbl"
													id="tblLeftTeam">
													<thead>
														<tr>
															<td colspan="3" style="padding: 0px; margin: 0px;">
																<div class="title_table row-fluid">
																	<div class="span12 title"
																		style="text-align: right; padding-right: 10px;">
																		<h4>
																			<spring:message var="list_team"
																				code="team.page.edit.team"></spring:message>
																			<c:out value="${list_team }"></c:out>
																			<input type="button" id="removeTeam" value="  "
																				class="btn btn-addTeam" />
																		</h4>
																	</div>
	
																</div>
															</td>
														</tr>
														<tr>
															<th><input type="checkbox" name="chkAllTeamLeft"
																id="chkAllTeamLeft" value=""></th>
															<th><spring:message var="msg_teamcode"
																	code="team.field.code"></spring:message> ${msg_teamcode}</th>
															<th><spring:message var="msg_teamname"
																	code="team.field.name"></spring:message> ${msg_teamname}</th>
	
														</tr>
													</thead>
													<tbody>
	
														<c:forEach var="_teamleft" items="${left}" varStatus="i">
															<tr>
																<td class="text_center"><input type="checkbox" name="chkTeamLeft"
																	id="chkTeamLeft" value="${_teamleft.id}"> <input
																	type="hidden" name="TeamLeft" id="TeamLeft"
																	value="${_teamleft.id}"></td>
																<td><c:out value="${_teamleft.code}"></c:out></td>
																<td><c:out value="${_teamleft.name }"></c:out></td>
															</tr>
														</c:forEach>
													</tbody>
	
												</table>
											</td>
											<td style="width: 5%">&nbsp;</td>
											<td style="width: 40%; vertical-align: top;">
	
												<table class="table table-bordered table-hover out-tbl"
													id="tblRightTeam">
													<thead>
														<tr>
															<td colspan="3" style="padding: 0px; margin: 0px;">
																<div class="title_table row-fluid">
																	<div class="span12 title">
																		<h4>
																			<input type="button" id="addTeam" value="  "
																				class="btn btn-removeTeam" />
																			<spring:message var="list_noneteam"
																				code="team.page.edit.noneteam"></spring:message>
																			<c:out value="${list_noneteam }"></c:out>
																		</h4>
																	</div>
																</div>
															</td>
														</tr>
														<tr>
															<th><input type="checkbox" id="chkAllTeamRight"
																value=""></th>
															<th><spring:message var="msg_teamcode"
																	code="team.field.code"></spring:message> ${msg_teamcode}</th>
															<th><spring:message var="msg_teamname"
																	code="team.field.name"></spring:message> ${msg_teamname}</th>
	
														</tr>
													</thead>
													<tbody>
														<c:forEach var="_teamright" items="${right}" varStatus="i">
															<tr>
																<td class="text_center"><input type="checkbox" name="chkTeamRight"
																	id="chkTeamRight" value="${_teamright.id}"> <input
																	type="hidden" name="TeamRight" id="TeamRight"
																	value="${_teamright.id}"></td>
																<td><c:out value="${_teamright.code}"></c:out></td>
																<td><c:out value="${_teamright.name}"></c:out></td>
															</tr>
														</c:forEach>
													</tbody>
	
												</table>
											</td>
											<td style="width: 5%">&nbsp;</td>
										</tr>
									</table>
	
								</div>
								</div>
							</div>
					</div>						
					<!-- END ROLES -->

			</div>
		</div>
	</div>
	<!-- end Note history -->		
		
		
		
<!-- start Note history -->
	<%-- <div class="row-fluid">
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span10 title">
							<h3>
							
							
									<spring:message var="approval_info"
										code="account.approvalinfo.title"></spring:message>
									<c:out value="${approval_info }"></c:out>
							</h3>
						</div>
						<div class="span2" style="text-align: right;">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion2" href="#collapseOne2"><i
								class="bms-icon-accordion-up bms-icon-accordion"></i></a>
						</div>
					</div>
				</div>
				
				<!-- APPROVAL INFO -->
					<div id="collapseOne2" class="accordion-body collapse in">
											
							<div class="row-fluid">
								<div class="span12" id="approvalInfoList">
									<tiles:insertAttribute name="approvalinfo" ignore="true" /> 
								</div>
							</div>
							
					</div>
					<!-- END APPROVAL INFO -->

			</div>
		</div>
	</div> --%>
	<!-- end Note history -->			
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
						
					<!-- ROLES POSITION -->
					</div>

				</div>
			</div>
	</div>
	</div>
	</form:form>

</div>

<%-- </form:form> --%>
<!-- APPROVAL INFO -->
<%-- <div class="container-fluid">
	<div class="row-fluid">
		<div class="accordion" id="accordion">
			<div class="span3 title">
				<h3>
					<span class="icon-list icon-small"></span>
					<spring:message var="approval_info"
						code="account.approvalinfo.title"></spring:message>
					<c:out value="${approval_info }"></c:out>
				</h3>
			</div>

			<div class="row-fluid">
				<div class="span12" id="approvalInfoList">
					<tiles:insertAttribute name="approvalinfo" ignore="true" />
				</div>
			</div>
		</div>
	</div>
</div> --%>
<!-- END INFO -->

<!-- ROLES -->


<script type="text/javascript">
	function backList() {
		document.location.href = "list";
	}


	$("#stocktext").change(function(){
		$("#projecttext").val("");
		$("#projecttextprojectId").val("");
	});
	
	$(document).ready(
			function() {
				
				
				$("#chkAllTeamLeft").click(function() {
					$("input[name=chkTeamLeft]").each(function() {
						this.checked = $("#chkAllTeamLeft").is(':checked');
					});
				});
				$("input[name=chkTeamLeft]").click(function(){
		    		if(!$(this).is(":checked")){
		    			$("#chkAllTeamLeft").prop("checked",false)
		    		}
		    		var isTickAll = true;
		    		$("input[name=chkTeamLeft]").each(function(){
		        		if(!$(this).is(":checked")){
		        			isTickAll = false;
		        		}
		        	});
		    		
		    		if(isTickAll){
		        		$("#chkAllTeamLeft").prop("checked",true);
		        	}else{
		        		$("#chkAllTeamLeft").prop("checked",false);
		        	}
		    	});
				
				$("#chkAllTeamRight").click(function() {
					$("input[name=chkTeamRight]").each(function() {
						this.checked = $("#chkAllTeamRight").is(':checked');
					});
				});
				$("input[name=chkTeamRight]").click(function(){
		    		if(!$(this).is(":checked")){
		    			$("#chkAllTeamRight").prop("checked",false)
		    		}
		    		var isTickAll = true;
		    		$("input[name=chkTeamRight]").each(function(){
		        		if(!$(this).is(":checked")){
		        			isTickAll = false;
		        		}
		        	});
		    		
		    		if(isTickAll){
		        		$("#chkAllTeamRight").prop("checked",true);
		        	}else{
		        		$("#chkAllTeamRight").prop("checked",false);
		        	}
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
					if($("#chkAllTeamRight").is(':checked')){
						$("#chkAllTeamRight").prop("checked",false);
					}
					$("input[name=chkTeamLeft]").each(function() {
						this.checked = true;
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
					if($("#chkAllTeamLeft").is(':checked')){
						$("#chkAllTeamLeft").prop("checked",false);
					}
					$("input[name=chkTeamLeft]").each(function() {
						this.checked = true;
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
				InitPlupload(imagePickfiles1, imageContainer1, uploadUrl1, false,
						imageMaxFileSize1, imageMimeTypes1, imageFileList1,
						imageConsole1, imageFileUploaded1, imageUploadComplete1,
						'${url}');
				
				$("#projectId").parent().find(".ms-choice").attr("disabled","disabled");
				$('.multiselectComment').multipleSelect({
			        filter: true,
			        noMatchesFound: '${no_matches_found}'
			    });
				$("#accordion2 .ms-parent").mouseenter(function(){
					if ($("#collapseOne").attr("class") == "accordion-body in collapse") {
						$("#collapseOne").attr("class","accordion-body in");
					}
				});
				
				changedStock();
			});

/* 	$("#usernameId").change(function(){	
		alert('<spring:message code="account.field.username.nochange"></spring:message>');
	}); */

	$("#saveEdit").click(function(){		
		$("input[name=chkTeamLeft]").each(function() {
			this.checked = true;
		});
		var url = "${url}/system/account/edit?u_id="+$.now();
		$.ajax({
			url : url,
			data : $('#approval_info_form,#edit_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#usernameId").focus();
				var success = $(data).find("#rs11").val();
				if(success){
					location.href = "${url}/system/account/list?message=success";
				}else{
					$("#eBody").html(data);	
				}
			}
		});
	}); 

	function deleteImage(){
		$("#tagImage").val("");
		$("#imgProduct").attr("src","");
	}
	function changedStock(){
		var stockId = $("input[name='stockId']").val();
		var isSavedFail = $("input[name='isSavedFail']").val();
		if(isSavedFail == 'true'){
			$("#projectId option").remove();
			$.ajax({
				url: '${url}/system/getProjectByStockId',
				type: 'get',
				data: {'stockId':stockId},
				cache: false,
				success: function(data) {
					for (var i = 0; i < data.length; i++) {
						$("#projectId").append("<option value='"+data[i].projectId+"'>"+data[i].projectCode +" - "+ data[i].shortName+"</option>");
					}
					setTimeout(function(){	
						 if('${bean.projectId}' != '')
							$("#projectId").val('${bean.projectId}'); 
						$("#projectId").multipleSelect("refresh");
					},100);
					
					/* $("#transferForm").unmask("${loading}"); */
				}
			});	
		}
	}
</script>
