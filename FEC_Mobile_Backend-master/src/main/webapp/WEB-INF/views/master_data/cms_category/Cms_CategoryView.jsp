<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<form:form method="POST" id="cms_category"
	cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
	
	<div class="modal-header">


		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h3 id="myModalLabel">
			<spring:message var="edit_title"
				code="${not empty bean.entity.cms_categoryId?'cmscategory.view':'cmscategory.AddNew' }" />
			<c:out value="${edit_title }"></c:out>
		</h3>

	</div>
	
	
	<div class="modal-body">
		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid">

			<div class="span6">
				<ext-form:input-text path="entity.code" required="true"
					cssInput="span14" labelCode="hobby.Code" uppercase="true"
					id="entity_code"disable="true"></ext-form:input-text>
				<input type="hidden" name="succesorfail" id="succesorfail"
					value="${bean.succesorfail}">
				<%-- ${bean.succesorfail} --%>
			</div>


			<div class="span6">

				<ext-form:input-text path="entity.name" required="true"
					cssInput="span10" labelCode="hobby.Name" uppercase="true"
					id="entity_name" disable="true"></ext-form:input-text>
			</div>
		</div>
	
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label for="type" class="control-label"> <spring:message
						code="hobby.Status" />
				</label>
				<div class="controls" style="width: 10px;">

					<form:select id="statusTable.statustableId" cssClass=""
						path="entity.statusTable.status_tableId"style="width: 260px;" disabled="True">
						<c:forEach items="${bean.listStatusTable}" var="j">
							<form:option value="${j.status_tableId}">${j.status_text}</form:option>
						</c:forEach>
					</form:select>


				</div>
			</div>
		</div>


	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="control-group">
				<ext-form:input-text path="entity.description" required="false"
					cssInput="width_area" labelCode="team.field.description" uppercase="true"
					id="entity_name" disable="true"></ext-form:input-text>
					
				</div>
			</div>
		</div>
	</div>

	<div class="modal-footer text_center" style="background: White">



		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		<button type="button" class="btn_search_general" data-dismiss="modal"
			aria-hidden="true">${msg_btn_close }</button>
	
	</div>
	</div>
	<form:hidden path="entity.cms_categoryId" />
</form:form>
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name=checkall]").click(function() {
			$("input[name=checkbox]").each(function() {
				this.checked = $("input[name=checkall]").is(':checked');
			});
		});
	});

	function submitPopup(showPopup, id) {
		$.ajax({
					url : '${url}/master_data/cms_category/cms_categoryView',
					data : $('#cms_category').serialize(),
					dataType : 'html',
					type : 'POST',
					cache : false,
					success : function(data) {
						$("#previewDetailPopup").html(data);
						/*	var succesorfail = $(data).find("#succesorfail").val();
						if (succesorfail == "succes") {

							 $('#previewDetailPopup').modal('hide'); 
							document.location.href = "${url}/master_data/cms_category/list";
						} else {
							$("#previewDetailPopup").html(data);
						}*/
					}
				});

	}

	
</script>