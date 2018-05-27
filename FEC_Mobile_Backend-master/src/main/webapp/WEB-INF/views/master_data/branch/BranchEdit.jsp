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
				code="${not empty bean.entity.branchid?'branch.view':'branch.view' }" />
			<c:out value="${edit_title }"></c:out>
		</h3>

	</div>
	
	<div class="modal-body">
		
		<div class="row-fluid">
			
		</div>
	
	<div class="row-fluid">
		<div class="span6">

			<label for="type" class="control-label"> <spring:message
					code="pos.province" />
			</label> <label for="type" class="control-label"><span
				class="required" style="color: green">${bean.entity.province} </span> </label>
		</div>


	</div>
	<div class="row-fluid">
		<div class="span6">

			<label for="type" class="control-label"> <spring:message
					code="pos.latitude" />
			</label> <label for="type" class="control-label"><span
				class="required" style="color: green">${bean.entity.latitude} </span> </label>
		</div>
		<div class="span6">

			<label for="type" class="control-label"> <spring:message
					code="pos.longitude" />
			</label> <label for="type" class="control-label"><span
				class="required" style="color: green">${bean.entity.longitude} </span> </label>
		</div>
	</div>

	<div class="modal-footer text_center"  style="background: White; width: 800px;">



		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		<button type="button" class="btn_search_general" data-dismiss="modal"
			aria-hidden="true">${msg_btn_close }</button>
	</div>
	</div>
	<form:hidden path="entity.branchid" />
	<div class="text_require"></div>
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
					url : '${url}/master_data/cms_category/cms_categoryEdit',
					data : $('#cms_category').serialize(),					
					type : 'POST',
					cache : false,
					success : function(data) {
						var succesorfail = $(data).find("#succesorfail").val();
						if (succesorfail == "succes") {

							/* $('#previewDetailPopup').modal('hide'); */
							document.location.href = "${url}/master_data/cms_category/list";
						} else {
							$("#popupBody").html(data);
						}
					}
				}); 

	}

	
</script>