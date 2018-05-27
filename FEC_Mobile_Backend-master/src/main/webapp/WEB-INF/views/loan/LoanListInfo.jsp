
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<ext:messages bean="${bean}"></ext:messages>
<form:form method="POST" modelAttribute="bean"
	cssClass="form-horizontal">

	<div class="row-fluid">
		<div class="span12">
			<div class="span6">

				<label for="type" class="control-label"> <spring:message
						code="loan.condition.name" />
				</label> <label for="type" class="control-label"><span
					class="loan_Detail">${bean.entity.condition_name}</span> </label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message
						code="loan.condition.category" />
				</label> <label for="type" class="control-label"><span
					class="loan_Detail">${bean.entity.condition_category}</span> </label>
			</div>
		</div>
	</div>


	<div class="row-fluid">
		<div class="span12">
			<div class="span6">

				<label for="type" class="control-label"> <spring:message
						code="loan.condition.value" />
				</label> <label for="type" class="control-label"><span
					class="loan_Detail">${bean.entity.condition_value}</span> </label>
			</div>
		</div>
	</div>




	<div class="modal-footer text_center">
		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		<button type="button" class="btn_search_general" data-dismiss="modal"
			aria-hidden="true">${msg_btn_close }</button>
	</div>



</form:form>
