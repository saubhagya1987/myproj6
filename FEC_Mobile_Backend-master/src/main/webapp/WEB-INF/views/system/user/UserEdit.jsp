<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<script>
	function blackUserList() {
		document.location.href = "${url}/system/user/list";
	}
	function blackUser() {
		document.location.href;
	}
</script>
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				<spring:message var="menu_system_user" code="menu.system.user"></spring:message>
				<spring:message var="menu_user_addnew_edit" code="${not empty bean.entity.id?'user.page.edit.title':'user.page.add.title' }"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="blackUser()" class="Color_back"><c:out value="${menu_admin }"></c:out></a><span> > </span>
					<a onclick="blackUserList()" class="Color_back"><c:out value="${menu_system_user }"></c:out></a><span> > </span>
					<span class="Colorgray"><c:out value="${menu_user_addnew_edit }"></c:out></span>
				</h4>
			</div>		
		</div>
	</div>		
</div>

<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span6 title_h2">
			<h2>
				<spring:message var="list_title"
					code="${not empty bean.entity.id?'user.page.edit.title':'user.page.add.title' }"></spring:message>
				<c:out value="${list_title}"></c:out>
			</h2>
		</div>
	</div>

	<form:form method="POST" id="edit_form" cssClass="form-horizontal" autocomplete="false" modelAttribute="bean" action="edit">
		<ext:messages bean="${bean}"></ext:messages>
		<!-- start Note history -->
		<input type="hidden" value="${success }" id="rs11" />
		<div class="row-fluid">
			<div class="accordion" id="accordion2"></div>
			<div class="accordion" id="accordion2">
				<div class="accordion-group">
					<div class="accordion-heading">
						<div class="row-fluid">
							<div class="span2 title1">
								<h3>
									<spring:message var="list_detail" code="user.page.edit.detail"></spring:message>
									<c:out value="${list_detail }"></c:out>
								</h3>
							</div>
							<div class="span1 unit_accordion" style="text-align: right;">
								<a class="accordion-toggle" data-toggle="collapse"
									data-parent="#accordion2" href="#collapseOne"> <i
									class="bms-icon-accordion-down bms-icon-accordion"></i></a>
							</div>
							
							<c:if test="${bean.entity.id != null}" >
							<div class="span4" style=" float:right">
							<div class="unlock-area" >
							<a href="${url}/system/user/unlock?email=${bean.entity.email}&id=${bean.entity.id}" >
							<div class="unlock-left" id ="unlockUser12">
							<p>Unlock User:</p>
							<img src="${url}/static/images/unlock-icon.png" alt="" />
							</div>
							</a>
<%-- 					<a href="${url}/system/user/clearDevice?email=${bean.entity.email}&id=${bean.entity.id}" >
							<div class="clear-right" >
							<p>Clear Device:</p>
							<img src="${url}/static/images/clean-icon.png" alt=""/>
							</div>
							</a> --%>
							
					<!-- <div class="clear-right" > -->
						<!-- 	<p>Clear Device:</p> -->
						<%-- 	<img src="${url}/static/images/clean-icon.png" alt=""/> --%>
							<div class="controls">
									<form:select style="background-image:url(${url}/static/images/clean-icon.png); " id="deviceId"  required="true"  Class="selectBox1 clear-right  " path="entity.gender" onchange="javascript:handleSelect(this)">
									<form:option value="NONE" hidden ="true"> Clear Device:</form:option>
									<form:option  value="${url}/system/user/clearDevice?email=${bean.entity.email}&id=${bean.entity.id}&appId=FECOL">OnMove</form:option>
									<form:option value="${url}/system/user/clearDevice?email=${bean.entity.email}&id=${bean.entity.id}&appId=FEDSA">SalesHub</form:option>
									</form:select>
								</div>
					<!-- </div> -->
								
							 
							
							</div>
							</div>
							</c:if>
							
						</div>
					</div>
					<div id="collapseOne" class="accordion-body in collapse">
						<div class="accordion-inner">
							<div class="row-fluid">
								<div class="span8"></div>
								<div class="span12">
									<div class="span6">
										<div>
											<form:hidden path="entity.id" id="userid" />
											 <form:hidden path="entity.statusTable.status_tableId" id="status_tableId" />
											<%-- <form:hidden path="entity.ldap" id="ldapid" /> --%>
											<%-- <ext-form:input-text path="entity.username" cssInput="span10" required="true" id="usernameId" labelCode="account.field.username"
												readonly="${not empty bean.entity.id? true : false }"></ext-form:input-text> --%>
										</div>

										<div>          
											<ext-form:input-text path="entity.userCode" cssInput="span10" required="true" labelCode="user.field.userCode"></ext-form:input-text>
										</div>

										<div>
											<ext-form:input-text path="entity.fullName" cssInput="span10" required="true" labelCode="user.field.fullName"></ext-form:input-text>
										</div>

										<div>
											<ext-form:input-text id="entityEmail" path="entity.email" cssInput="span10" required="true" labelCode="user.field.email"></ext-form:input-text>
										</div>

										<div>
											<ext-form:input-text path="entity.mobile" cssInput="span10" required="true" labelCode="user.field.mobile"></ext-form:input-text>
										</div>

										<div>
											<fmt:formatDate var="var_birthday" value="${bean.entity.birthday}" pattern="${sessionScope.formatDate}" />
											<ext-form:input-date path="entity.birthday" id="var_birthday_id" value="${var_birthday}"  required="true" labelCode="user.field.birthday" />
										</div>
										
									    <div>
											<fmt:formatDate var="var_joiningDate" value="${bean.entity.joiningDate}" pattern="${sessionScope.formatDate}" />
											<ext-form:input-date path="entity.joiningDate" id="var_joining_id" value="${var_joiningDate}"  required="true"  labelCode="user.field.joiningDate" />
										</div> 
										<div>
											<ext-form:input-text path="entity.lineManager" cssInput="span10" required="true" labelCode="user.field.lineManager"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.regionCode" cssInput="span10" required="true" labelCode="user.field.regionCode"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.provinceCode" cssInput="span10" required="true" labelCode="user.field.provinceCode"></ext-form:input-text>
										</div>
										
										<%-- <div>
											<ext-form:input-text path="entity.status" cssInput="span10" required="true" labelCode="user.field.status"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.gender" cssInput="span10" required="true" labelCode="user.field.gender"></ext-form:input-text>
										</div> --%>
										
										<div>
											<ext-form:input-text path="entity.onBoardPosition" cssInput="span10" required="true" labelCode="user.field.onBoardPosition"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.position" cssInput="span10" required="true" labelCode="user.field.position"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.typeOfSalesAgent" cssInput="span10" required="true" labelCode="user.field.typeOfSalesAgent"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.firstName" cssInput="span10" required="true" labelCode="user.field.firstName"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.lastName" cssInput="span10" required="true" labelCode="user.field.lastName"></ext-form:input-text>
										</div>
										
										<div>
											<ext-form:input-text path="entity.officeNumber" cssInput="span10" required="true" labelCode="user.field.officeNumber"></ext-form:input-text>
										</div>
										
									</div>									
								</div>
							</div>
						</div>
						<div class="span12">
							<div class="control-group">
								<label for="type"   class="control-label"> <spring:message
										code="user.field.gender" />
								</label>
								<div class="controls">
									<form:select id="genderId"  required="true"  cssClass="" path="entity.gender">
										<c:forEach items="${bean.genderList}" var="j">
											<form:option value="${j.desc}">${j.desc}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div> 
						<div class="span12">
							<div class="control-group">
								<label for="type"   class="control-label"> <spring:message
										code="user.field.status" />
								</label>
								<div class="controls">
									<form:select id="statusId"  required="true"  cssClass="" path="entity.status">
										<c:forEach items="${bean.statusList}" var="j">
											<form:option value="${j.desc}">${j.desc}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div> 
						 <div class="span12">
							<div class="control-group">
								<label for="type"   class="control-label"> <spring:message
										code="user.accessFlag" />
								</label>
								<div class="controls">
									<form:select id="accesFlagId"  required="true"  cssClass="" path="entity.accessFlag">
										<c:forEach items="${bean.userAccessFlag}" var="j">
											<form:option value="${j.intValue}">${j.desc}</form:option>
										</c:forEach>
									</form:select>
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
							<spring:message var="list_detail_role" code="msg.role.user"></spring:message>
							<c:out value="${list_detail_role }"></c:out>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;">
						<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1">
						<i class="bms-icon-accordion-down bms-icon-accordion"></i></a>
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
																	<spring:message var="list_team" code="team.page.edit.team"></spring:message>
																	<c:out value="${list_team }"></c:out>
																	<input type="button" id="removeTeam" value="  " class="btn btn-addTeam" />
																</h4>
															</div>
														</div>
													</td>
												</tr>
												<tr>
													<th><input type="checkbox" name="chkAllTeamLeft" id="chkAllTeamLeft" value=""></th>
													<th><spring:message var="msg_teamcode" code="team.field.code"></spring:message> ${msg_teamcode}</th>
													<th><spring:message var="msg_teamname" code="team.field.name"></spring:message> ${msg_teamname}</th>
												</tr>
											</thead>
											<tbody>

												<c:forEach var="_teamleft" items="${left}" varStatus="i">
													<tr>
														<td class="text_center"><input type="checkbox" name="chkTeamLeft" 
														id="chkTeamLeft" value="${_teamleft.id}"> 
														<input type="hidden" name="TeamLeft" id="TeamLeft" value="${_teamleft.id}"></td>
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
													<th><input type="checkbox" id="chkAllTeamRight" value=""></th>
													<th><spring:message var="msg_teamcode" code="team.field.code"></spring:message> ${msg_teamcode}</th>
													<th><spring:message var="msg_teamname" code="team.field.name"></spring:message> ${msg_teamname}</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="_teamright" items="${right}" varStatus="i">
													<tr>
														<td class="text_center"><input type="checkbox" name="chkTeamRight" id="chkTeamRight"
															value="${_teamright.id}"> <input type="hidden" name="TeamRight" id="TeamRight" value="${_teamright.id}"></td>
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
				<div class="row-fluid text-center">
	<!-- <div class="accordion" id="accordion">
		<div class="accordion-group"> -->
			<input type="hidden" name="action" value="edit" />
			<spring:message var="save_btn_msg" code="button.save"></spring:message>
			<input type="button" value="${save_btn_msg}" id="saveEdit" class="btn_search_general" />
			<spring:message var="return_btn_msg" code="button.return"></spring:message>

		<!-- </div>
	</div> -->
</div>
			</div>
			<!-- END ROLES -->

		</div>
	</div>
</div>
<!-- end Note history -->



<!-- start Note history -->

<!-- end Note history -->

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
<script type="text/javascript">
	function backList() {
		document.location.href = "list";
	}

	$("#stocktext").change(function() {
		$("#projecttext").val("");
		$("#projecttextprojectId").val("");
	});

	$(document)
			.ready(
					function() {

						$("#chkAllTeamLeft").click(
								function() {
									$("input[name=chkTeamLeft]").each(
											function() {
												this.checked = $(
														"#chkAllTeamLeft").is(
														':checked');
											});
								});
						$("input[name=chkTeamLeft]").click(function() {
							if (!$(this).is(":checked")) {
								$("#chkAllTeamLeft").prop("checked", false)
							}
							var isTickAll = true;
							$("input[name=chkTeamLeft]").each(function() {
								if (!$(this).is(":checked")) {
									isTickAll = false;
								}
							});

							if (isTickAll) {
								$("#chkAllTeamLeft").prop("checked", true);
							} else {
								$("#chkAllTeamLeft").prop("checked", false);
							}
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
						$("input[name=chkTeamRight]").click(function() {
							if (!$(this).is(":checked")) {
								$("#chkAllTeamRight").prop("checked", false)
							}
							var isTickAll = true;
							$("input[name=chkTeamRight]").each(function() {
								if (!$(this).is(":checked")) {
									isTickAll = false;
								}
							});

							if (isTickAll) {
								$("#chkAllTeamRight").prop("checked", true);
							} else {
								$("#chkAllTeamRight").prop("checked", false);
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
							if ($("#chkAllTeamRight").is(':checked')) {
								$("#chkAllTeamRight").prop("checked", false);
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
							if ($("#chkAllTeamLeft").is(':checked')) {
								$("#chkAllTeamLeft").prop("checked", false);
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

						$("#projectId").parent().find(".ms-choice").attr(
								"disabled", "disabled");
						$('.multiselectComment').multipleSelect({
							filter : true,
							noMatchesFound : '${no_matches_found}'
						});
						$("#accordion2 .ms-parent")
								.mouseenter(
										function() {
											if ($("#collapseOne").attr("class") == "accordion-body in collapse") {
												$("#collapseOne").attr("class",
														"accordion-body in");
											}
										});

						changedStock();
					});

	/* 	$("#usernameId").change(function(){	
	 alert('<spring:message code="account.field.username.nochange"></spring:message>');
	 }); */

	$("#saveEdit").click(function() {
		$("input[name=chkTeamLeft]").each(function() {
			this.checked = true;
		});
		console.log("getting now ");
		console.log($.now())
		var url = "${url}/system/user/edit?u_id=" + $.now();
		$.ajax({
			url : url,
			data : $('#approval_info_form,#edit_form').serialize(),
			dataType : 'text',
			type : 'POST',
			cache : false,
			success : function(data) {
				$("#usernameId").focus();
				var success = $(data).find("#rs11").val();
				if (success) {
					location.href = "${url}/system/user/list?message=success";
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
	function changedStock() {
		var stockId = $("input[name='stockId']").val();
		var isSavedFail = $("input[name='isSavedFail']").val();
		if (isSavedFail == 'true') {
			$("#projectId option").remove();
			$.ajax({
				url : '${url}/system/getProjectByStockId',
				type : 'get',
				data : {
					'stockId' : stockId
				},
				cache : false,
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						$("#projectId").append(
								"<option value='"+data[i].projectId+"'>"
										+ data[i].projectCode + " - "
										+ data[i].shortName + "</option>");
					}
					setTimeout(function() {
						if ('${bean.projectId}' != '')
							$("#projectId").val('${bean.projectId}');
						$("#projectId").multipleSelect("refresh");
					}, 100);

					/* $("#transferForm").unmask("${loading}"); */
				}
			});
		}
	}
	
	$("#unlockUser").click(function() {
		var x = document.getElementById("entityEmail");
		console.log("unlock clicked "+x.value);
		 var url = "${url}/system/user/unlock?email="+x.value;
		$.ajax({
			url : url,
		//	data : $('#approval_info_form,#edit_form').serialize(),
			dataType : 'text',
			type : 'GET',
			cache : false,
			success : function(data) {
				console.log("unlock data ");
				console.log(data)
				//$("#usernameId").focus();
				var success = $(data).find("#rs11").val();
				if (success) {
					alert("success");
					location.href = "${url}/system/user/list?message=success";
				} else {
					alert("failure");
					$("#eBody").html(data);
				}
			}
		}); 
	});
	
	function handleSelect(elm)
	{
	window.location = elm.value;
	}
	
</script>
