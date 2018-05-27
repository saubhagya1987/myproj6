<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<form:form method="POST" id="edit_form" cssClass="form-horizontal"
	modelAttribute="bean" action="edit">
	<div class="title_top">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span6 title_h2">
					<h2>
						
						<spring:message var="list_title" code="team.page.view.title"></spring:message>
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

	<div class="container-fluid unit_bg_content">

		<ext:messages bean="${bean}"></ext:messages>
		<!-- vung search -->

		<div class="accordion" id="accordion">
			<div class="accordion-group">


				<div id="search_filter_body" class="accordion-body collapse in">
					
					<div class="row-fluid border_bottom_red">
					<div class="span3 title">
						<h4>
							
							<spring:message var="list_detail" code="team.page.edit.detail"></spring:message>
							<c:out value="${list_detail }"></c:out>
						</h4>
					</div>
					</div>
					
					<div class="accordion-inner">
						
							
							<div class="row-fluid">
								<div class="span6">
									<form:hidden path="entity.id" />
									<ext-form:input-text path="entity.code" cssInput="span10"
										disable="true" labelCode="team.field.code"></ext-form:input-text>
								</div>
								<div class="span6">
									<ext-form:input-text path="entity.name" cssInput="span10"
										disable="true" labelCode="team.field.name"></ext-form:input-text>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<ext-form:input-text path="entity.description" cssInput="width_area"
										disable="true" labelCode="team.field.description"></ext-form:input-text>
								</div>
							</div>

						

					</div>
				</div>

			</div>
		</div>
		
		
		<div class="accordion" id="accordion">
			<div class="accordion-group">


				<div id="search_filter_body" class="accordion-body collapse in">
					
					<div class="row-fluid border_bottom_red">
					<div class="span3 title">
						<h3>
							
							<spring:message var="list_Roledetail" code="role.page.list.title"></spring:message>
							<c:out value="${list_Roledetail }"></c:out>
						</h3>
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
															<spring:message var="list_role"
																code="role.page.edit.role"></spring:message>
															<c:out value="${list_role }"></c:out>
														</h5>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th><spring:message var="msg_rolecode"
													code="role.field.code"></spring:message> ${msg_rolecode}</th>
											<th><spring:message var="msg_rolename"
													code="role.field.name"></spring:message> ${msg_rolename}</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="_roleLeft" items="${left}" varStatus="i">
											<tr>

												<td><c:out value="${_roleLeft.code}"></c:out></td>
												<td><c:out value="${_roleLeft.name }"></c:out></td>
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


</form:form>
<script type="text/javascript">
function backList(){
	  document.location.href="list";
}
function EditList(){
	document.location.href="edit?id="+<%= request.getParameter("id") %>;
	
}
</script>