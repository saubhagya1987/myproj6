<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<style>
.required {
	color: #008000 !important;
}
</style>
<ext:messages bean="${bean}"></ext:messages>
<form:form method="POST" modelAttribute="bean" cssClass="form-horizontal">

	<div class="row-fluid">
		<div class="span12">
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.custommer.name" />
				</label> <label for="type" class="control-label"><span class="required"><c:out value="${bean.entity.fullName}" /></span> </label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.id.card.no" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.idCardNumber}</span> </label>
			</div>
		</div>
	</div>


	<div class="row-fluid">
		<div class="span12">
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.custommer.id" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.customer.customerId}</span> </label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.city" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.city}</span> </label>
			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span12">
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.email" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.email}</span> </label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.cell.phone" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.cellPhone}</span> </label>
			</div>
		</div>
	</div>




	<div class="row-fluid">
		<div class="span12">
			<div class="span6">
				<fmt:formatNumber var="loanAmount" value="${bean.entity.loanAmount}" pattern="${sessionScope.formatNumber}" />
				<label for="type" class="control-label"> <spring:message code="apply.now.loan.Amount" />
				</label> <label for="type" class="control-label"><span class="required"> <c:if test="${loanAmount!=null}">
							<c:out value="${loanAmount}" /> VND</c:if>
				</span> </label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.product" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.product}</span> </label>
			</div>

		</div>
	</div>






	<div class="row-fluid">
		<div class="span12">
			<div class="span6">
				<fmt:formatNumber var="monthlyIncome" value="${bean.entity.monthlyIncome}" pattern="${sessionScope.formatNumber}" />
				<label for="type" class="control-label"> <spring:message code="apply.now.monthly.income" />
				</label> <label for="type" class="control-label"><span class="required"> <c:if test="${monthlyIncome!=null}">
							<c:out value="${monthlyIncome}" /> VND </c:if></span> </label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message code="apply.now.loan.tenure" />
				</label> <label for="type" class="control-label"><span class="required"> <c:if test="${bean.entity.loanTenure!=null}">
							<c:out value="${bean.entity.loanTenure}" /> months</c:if></span> </label>
			</div>

		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">

			<div class="span6">
				<label for="type" class="control-label"> <spring:message code="apply.now.address" />
				</label> <label for="type" class="control-label"><span class="required"><c:out value="${bean.entity.address}" /> </span> </label>
			</div>
			<div class="span6">
				<label for="type" class="control-label"> <spring:message code="customer.emi" />
				</label> <label for="type" class="control-label"><span class="required">${bean.entity.emi}</span> </label>
			</div>

		</div>
	</div>

	<div class="modal-footer text_center">
		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		<button type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true">${msg_btn_close }</button>
	</div>



</form:form>
