<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<form:form method="POST" id="edit_form" cssClass="form-horizontal"
	modelAttribute="bean" action="edit">
	<div class="title_top">
		<div class="container-fluid">
		<div class="row-fluid">
		
		<div class="span6">
			<spring:message var="menu_admin" code="menu.admin"></spring:message>
			<spring:message var="menu_system_team" code="menu.system.team"></spring:message>
			<spring:message var="menu_team_addnew_edit" code="${not empty bean.entity.id?'team.page.edit.tooltip':'team.page.add.title' }"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"><c:out value="${menu_admin }"></c:out></a>
				<span> > </span>
				<a onclick="backTeam()" class="Color_back"><c:out value="${menu_system_team }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_team_addnew_edit }"></c:out></span>
			</h4>
		</div>
		
			<%-- <div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				<spring:message var="menu_system_team" code="menu.system.team"></spring:message>
				<spring:message var="menu_team_addnew_edit" code="${not empty bean.entity.id?'team.page.edit.tooltip':'team.page.add.title' }"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<c:out value="${menu_admin }"></c:out>
					<span> > </span>
					<c:out value="${menu_system_team }"></c:out>
					<span> > </span>
					<c:out value="${menu_team_addnew_edit }"></c:out>
				</h4>
			</div> --%>
			<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="icon_back" code="icon.back"></spring:message>
						<spring:message var="return_btn_msg" code="button.return"></spring:message>
						
					</ul>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="container-fluid unit_bg_content">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>

					<spring:message var="list_title"
						code="${not empty bean.entity.id?'team.page.edit.title':'team.page.add.title' }"></spring:message>
					<c:out value="${list_title }"></c:out>
				</h2>
			</div>
			
		</div>
		<!-- Input values and Save values area -->
		<div class="row-fluid">
			<div class="span12">
				<!-- vung search -->
				<div class="accordion" id="accordion2">
	</div>
				<div class="accordion" id="accordion2">
					<ext:messages bean="${bean}"></ext:messages>
					<div class="accordion-group">
						<div class="accordion-heading">
							<div class="row-fluid">
								<div class="span3 title1">
									<h3>
										<spring:message code="banner.general.info.title"/>
									</h3>
								</div>
								<div class="span1 unit_accordion" style="text-align: right;">
									<a class="accordion-toggle" data-toggle="collapse"
												data-parent="#accordion2" href="#collapseOne"><i
												class="bms-icon-accordion-down bms-icon-accordion"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div id="collapseOne" class="accordion-body collapse in border-group">
						<div class="accordion-inner">
							<div class="input-area">
								<div class="row-fluid">
									<div class="span6">
										<form:hidden path="entity.id" />
										<form:hidden path="entity.type" />
										<form:hidden path="entity.enabled" />
										<ext-form:input-text path="entity.code" cssInput="span10"
											required="true" uppercase="true" id="teamCode"
											labelCode="team.field.code"></ext-form:input-text>
									</div>
									<div class="span6">
										<ext-form:input-text path="entity.name" cssInput="span10"
											required="true" labelCode="team.field.name"></ext-form:input-text>
									</div>
								</div>
								<div class="row-fluid">

									<ext-form:input-text path="entity.description"
										cssInput="width_area" labelCode="team.field.description"></ext-form:input-text>

								</div>
							</div>
						</div>
					</div>			
				</div>
			</div>
		</div>
		
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span3 title1">
							<h3>
								<spring:message code="role.page.list.title"/>
							</h3>
						</div>
						<div class="span1 unit_accordion" style="text-align: right;">
							<a class="accordion-toggle" data-toggle="collapse"
										data-parent="#accordion2" href="#collapseOne_2"><i
										class="bms-icon-accordion-down bms-icon-accordion"></i></a>
						</div>
					</div>
				</div>
			</div>
			<div id="collapseOne_2" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="span12">
							<table style="width: 100%;">
								<tr>
									<td style="width: 5%">&nbsp;</td>
									<td style="width: 40%; vertical-align: top;">
										<table class="table table-bordered table-hover out-tbl"
											id="tblLeftRole">
											<thead>
												<tr>
													<td colspan="3" style="padding: 0px; margin: 0px;">
														<div class="title_table row-fluid">
															<div class="span12 title"
																style="text-align: right; padding-right: 10px;">
																<h4>
																	<spring:message var="list_role"
																		code="role.page.edit.role"></spring:message>
																	<c:out value="${list_role }"></c:out>
																	<input type="button" id="removeRole" value="  "
																		class="btn btn-addTeam" />
																</h4>
															</div>
														</div>
													</td>
												</tr>
												<tr>
													<th><input type="checkbox" name="chkAllRoleLeft"
														id="chkAllRoleLeft" value=""></th>
													<th><spring:message var="msg_rolecode"
															code="role.field.code"></spring:message>
														${msg_rolecode}</th>
													<th><spring:message var="msg_rolename"
															code="role.field.name"></spring:message>
														${msg_rolename}</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="_roleleft" items="${left}" varStatus="i">
													<tr>
														<td style="text-align: center;"><input
															type="checkbox" name="chkRoleLeft" id="chkRoleLeft"
															value="${_roleleft.id}"> <input type="hidden"
															name="TeamLeft" id="TeamLeft" value="${_roleleft.id}"></td>
														<td><c:out value="${_roleleft.code}"></c:out></td>
														<td><c:out value="${_roleleft.name }"></c:out></td>
													</tr>
												</c:forEach>
											</tbody>

										</table>

									</td>
									<td style="width: 5%">&nbsp;</td>
									<td style="width: 40%; vertical-align: top;">
										<table class="table table-bordered table-hover out-tbl"
											id="tblRightRole">
											<thead>
												<tr>
													<td colspan="3" style="padding: 0px; margin: 0px;">
														<div class="title_table row-fluid">
															<div class="span12 title">
																<h4>
																	<input type="button" id="addRole" value="  "
																		class="btn btn-removeTeam" />
																	<spring:message var="list_nonerole"
																		code="role.page.edit.nonerole"></spring:message>
																	<c:out value="${list_nonerole }"></c:out>

																</h4>
															</div>
														</div>
													</td>
												</tr>
												<tr>
													<th><input type="checkbox" id="chkAllRoleRight"
														value=""></th>
													<th><spring:message var="msg_rolecode"
															code="role.field.code"></spring:message>
														${msg_rolecode}</th>
													<th><spring:message var="msg_rolename"
															code="role.field.name"></spring:message>
														${msg_rolename}</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach var="_roleright" items="${right}" varStatus="i">
													<tr>
														<td class="text_center"><input type="checkbox"
															name="chkRoleRight" id="chkRoleRight"
															value="${_roleright.id}"> <input type="hidden"
															name="RoleRight" id="RoleRight"
															value="${_roleright.id}"></td>
														<td><c:out value="${_roleright.code}"></c:out></td>
														<td><c:out value="${_roleright.name}"></c:out></td>
													</tr>
												</c:forEach>
											</tbody>

										</table>

									</td>
									<td style="width: 5%">&nbsp;</td>
								</tr>



							</table>
							<div class="text-center">
								<input type="hidden" name="action" value="edit" />
								<spring:message var="save_btn_msg" code="button.save"></spring:message>
								<button type="submit" id="saveEdit" class="btn_search_general">${save_btn_msg}</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>				
	</div>
</form:form>


<script type="text/javascript">
	$(document).ready(function() {

		$("#chkAllRoleLeft").click(function() {
			$("input[name=chkRoleLeft]").each(function() {
 				this.checked = $("#chkAllRoleLeft").is(':checked');
			});
		});
		$("input[name=chkRoleLeft]").click(function(){
    		if(!$(this).is(":checked")){
    			$("#chkAllRoleLeft").prop("checked",false)
    		}
    		var isTickAll = true;
    		$("input[name=chkRoleLeft]").each(function(){
        		if(!$(this).is(":checked")){
        			isTickAll = false;
        		}
        	});
    		if(isTickAll){
        		$("#chkAllRoleLeft").prop("checked",true);
        	}else{
        		$("#chkAllRoleLeft").prop("checked",false);
        	}
    	});
		
		$("#chkAllRoleRight").click(function() {
			$("input[name=chkRoleRight]").each(function() {
	   			this.checked = $("#chkAllRoleRight").is(':checked');
			});
		});
		$("input[name=chkRoleRight]").click(function(){
    		if(!$(this).is(":checked")){
    			$("#chkAllRoleRight").prop("checked",false)
    		}
    		var isTickAll = true;
    		$("input[name=chkRoleRight]").each(function(){
        		if(!$(this).is(":checked")){
        			isTickAll = false;
        		}
        	});
    		
    		if(isTickAll){
        		$("#chkAllRoleRight").prop("checked",true);
        	}else{
        		$("#chkAllRoleRight").prop("checked",false);
        	}
    	});
		
		$("#return").click(function() {
			document.location.href = "list";
		});
		$("#saveEdit").click(function() {
			$("input[name=chkRoleLeft]").each(function() {
				this.checked = true;
			});

		});
		$("#addRole").click(function() {
			$("input[name=chkRoleRight]").each(function() {
				if (this.checked) {
					this.checked = false;
					var row = $(this).closest("tr");
					var table = $(this).closest("table");
					row.detach();
					if (table.is("#tblRightRole")) {
						$("#tblLeftRole").append(row);
					}
					// row.fadeOut();
					row.fadeIn();
					this.name = "chkRoleLeft";
					this.id = "chkRoleLeft";
				}

			});
			if($("#chkAllRoleRight").is(':checked')){
				$("#chkAllRoleRight").prop("checked",false);
			}
		});
		$("#removeRole").click(function() {
			$("input[name=chkRoleLeft]").each(function() {
				if (this.checked) {
					this.checked = false;
					var row = $(this).closest("tr");
					var table = $(this).closest("table");
					row.detach();
					if (table.is("#tblLeftRole")) {
						$("#tblRightRole").append(row);
					}
					// row.fadeOut();
					row.fadeIn();
					this.name = "chkRoleRight";
					this.id = "chkRoleRight";
				}

			});
			if($("#chkAllRoleLeft").is(':checked')){
				$("#chkAllRoleLeft").prop("checked",false);
			}
		});
		//Javascript for Expense Category action
		$("#chkAllCategoryLeft").click(function() {
			$("input[name=chkCategoryLeft]").each(function() {
				this.checked = $("#chkAllCategoryLeft").is(':checked');
			});
		});
		$("#chkAllCategoryRight").click(function() {
			$("input[name=chkCategoryRight]").each(function() {
				this.checked = $("#chkAllCategoryRight").is(':checked');
			});
		});
		$("#addCategory").click(function() {
			$("input[name=chkCategoryRight]").each(function() {
				if (this.checked) {
					this.checked = false;
					var row = $(this).closest("tr");
					var table = $(this).closest("table");
					row.detach();
					if (table.is("#tblRightCategory")) {
						$("#tblLeftCategory").append(row);
					}
					// row.fadeOut();
					row.fadeIn();
					this.name = "chkCategoryLeft";
					this.id = "chkCategoryLeft";
				}

			});
		});
		$("#removeCategory").click(function() {
			$("input[name=chkCategoryLeft]").each(function() {
				if (this.checked) {
					this.checked = false;
					var row = $(this).closest("tr");
					var table = $(this).closest("table");
					row.detach();
					if (table.is("#tblLeftCategory")) {
						$("#tblRightCategory").append(row);
					}
					// row.fadeOut();
					row.fadeIn();
					this.name = "chkCategoryRight";
					this.id = "chkCategoryRight";
				}
			});
		});

	});
	function backList() {
		document.location.href = "list";
	}

	function checkBoxChange(nodeId, tag, checkName) {
		checkBoxChangeChild(nodeId, tag, checkName);

		//dandt: neu check all cac nut con thi check nut cha
		//neu bo check nut con thi bo check nut cha
		// 		var c = $(tag).attr("class").split(checkName);
		// 		if(c.length == 2){
		// 			var parentId = c[1];
		// 			var countChecked = 0;
		// 			var countUnCheck = 0;
		// 			var sum = $("input[class=" + checkName + parentId + "]").length;
		// 			$("input[class=" + checkName + parentId + "]").each(function() {
		// 				if(this.checked == true){
		// 					countChecked++;
		// 				}else{
		// 					countUnCheck++;
		// 				}
		// 			});

		// 			if(countChecked == sum){
		// 				$("input[value=" + parentId + "]").each(function() {
		// 					if(this.name == checkName){
		// 						this.checked = true;
		// 					}
		// 				});
		// 			}else{// if(countUnCheck == sum){
		// 				$("input[value=" + parentId + "]").each(function() {
		// 					if(this.name == checkName){
		// 						this.checked = false;
		// 					}
		// 				});
		// 			}
		// 		}

		//check nut cha
		if ($(tag).is(':checked') == true) {
			checkParentByChild(tag, checkName);
		} else { // uncheck
			uncheckParentByChild(tag, checkName);
		}
	}

	function checkParentByChild(tag, checkName) {
		var c = $(tag).attr("class").split(checkName);
		if (c.length == 2) {
			var parentId = c[1];
			$("input[value=" + parentId + "]").each(function() {
				if (this.name == checkName) {
					this.checked = $(tag).is(':checked');
					checkParentByChild(this, checkName);
				}
			});

		}
	}

	function uncheckParentByChild(tag, checkName) {

		var c = $(tag).attr("class").split(checkName);

		if (c.length == 2) {
			var parentId = c[1];
			if (parentId != '') { // la root
				$("input[value=" + parentId + "]").each(function() {
					if (!hasChildChecked(this, checkName)) {
						this.checked = false;
						uncheckParentByChild(this, checkName);
					} else {
						return false;
					}
				});
			}

		}
	}

	// kiem tra co ton tai con duoc check
	function hasChildChecked(parent, checkName) {
		var hasChecked = false;
		$("input[class=" + checkName + parent.value + "]").each(function() {
			if ($(this).is(':checked') == true) {
				hasChecked = true;
				return false;
			}
		});
		return hasChecked;
	}

	function checkBoxChangeChild(nodeId, tag, checkName) {
		$("input[class=" + checkName + nodeId + "]").each(function() {
			this.checked = $(tag).is(':checked');
			if ($("input[class=" + checkName + this.value + "]").length > 0) {
				checkBoxChangeChild(this.value, this, checkName);
			}
		});
	}
	function backTeam() {
		document.location.href = "${url}/system/team/list/";
	}
</script>