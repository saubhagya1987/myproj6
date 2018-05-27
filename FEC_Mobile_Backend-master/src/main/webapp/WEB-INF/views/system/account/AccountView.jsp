<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />

<form:form method="POST" id="edit_form" cssClass="form-horizontal"
	modelAttribute="bean" action="edit">
	<div class="title_top">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span6 title_h2">
					<h2>

						<spring:message var="list_title" code="account.page.view.title"></spring:message>
						<c:out value="${list_title }"></c:out>
					</h2>
				</div>
				<div class="span6">
					<div class="menu_images">
						<ul style="float: right;">
							<spring:message var="edit_btn_msg" code="button.edit"></spring:message>
							<spring:message var="return_btn_msg" code="button.return"></spring:message>
							<li class="edit"><a href="javascript:EditList()"></a></li>
							<li class="back"><a href="javascript:backList()"></a></li>


						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br />
	<div class="container-fluid unit_bg_content">

		<ext:messages bean="${bean}"></ext:messages>
		<!-- vung search -->

		<div class="accordion" id="accordion">
			<div class="accordion-group">


				<div id="search_filter_body" class="accordion-body collapse in">

					<div class="row-fluid border_bottom_red">
						<div class="span3 title">
							<h4>

								<spring:message var="list_detail"
									code="account.page.edit.detail"></spring:message>
								<c:out value="${list_detail }"></c:out>
							</h4>
						</div>
					</div>

					<div class="accordion-inner">
						<div class="row-fluid">


							<div class="span6">
								<form:hidden path="entity.id" />
								<ext-form:input-text path="entity.username" cssInput="span8"
									disable="true" labelCode="account.field.username"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="entity.email" cssInput="span8"
									disable="true" labelCode="account.field.email"></ext-form:input-text>
							</div>
						</div>
						
						<div class="row-fluid">

						
								<div class="span6">
									<ext-form:input-text path="entity.mobile" cssInput="span8"
										disable="true" labelCode="account.field.mobile"></ext-form:input-text>
								</div>
								<div class="span6">
									<ext-form:input-text path="entity.birthday" cssInput="span8"
										disable="true" labelCode="account.field.birthday"></ext-form:input-text>
								</div>
						</div>
						<div class="row-fluid">
								<div class="span6">
									<ext-form:input-combotree path="dept.departmentId"
										url="${pageContext.request.contextPath}/master_data/department/json_tree?id=${dept.departmentId }"
										required="false" labelCode="dept.page.list.title"
										id="departmentId"></ext-form:input-combotree>
								</div>
								<div class="span6">
									<ext-form:display value="${userstatus}" cssClass="span8"
										labelCode="payment.status"></ext-form:display>
								</div>
						</div>
						


					</div>
				</div>

			</div>
		</div>
		<div class="container-fluid">

			<ext:messages bean="${bean}"></ext:messages>
			<!-- vung search -->

			<div class="accordion" id="accordion">
				<div class="accordion-group">


					<div id="search_filter_body" class="accordion-body collapse in">

						<div class="row-fluid border_bottom_red">
							<div class="span3 title">
								<h4>

									<spring:message var="list_Roledetail"
										code="team.page.list.title"></spring:message>
									<c:out value="${list_Roledetail }"></c:out>
								</h4>
							</div>
						</div>


						<div class="accordion-inner">
							<div class="span12">
								<div class="span4">
									<table class="table table-striped table-hover table-bordered">
										<thead>
											<tr>
												<td colspan="3" style="padding: 0px; margin: 0px;">
													<div class="title_table row-fluid">
														<div class="span12 title">
															<h5>
																<spring:message var="list_team"
																	code="team.page.edit.team"></spring:message>
																<c:out value="${list_team }"></c:out>
															</h5>
														</div>
													</div>
												</td>
											</tr>
											<tr>

												<th><spring:message var="msg_teamcode"
														code="team.field.code"></spring:message> ${msg_teamcode}</th>
												<th><spring:message var="msg_teamname"
														code="team.field.name"></spring:message> ${msg_teamname}</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach var="_teamleft" items="${left}" varStatus="i">
												<tr>

													<td><c:out value="${_teamleft.code}"></c:out></td>
													<td><c:out value="${_teamleft.name }"></c:out></td>
												</tr>
											</c:forEach>
										</tbody>

									</table>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function backList() {
			document.location.href = "list";
		}
		function EditList() {
			document.location.href = "edit?id="
					+
	<%=request.getParameter("id")%>
		;

		}
	</script>
</form:form>
