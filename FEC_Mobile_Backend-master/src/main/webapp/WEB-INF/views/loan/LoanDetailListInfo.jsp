
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
						code="loan.condition.category" />
				</label> <label for="type" class="control-label"><span class="loan_Detail">${bean.entity.loan.condition_category}</span></label>
			</div>
			<div class="span6">

				<label for="type" class="control-label"> <spring:message
						code="loan.detail.condition" />
				</label> <label for="type" class="control-label"><span class="loan_Detail">${bean.entity.loan.condition_name}</span></label>
			</div>
		</div>
	</div>


	<div class="row-fluid">
		<div class="span12">
			<div class="span6">
			
				<fmt:formatNumber var="minamount" value="${bean.entity.minamount}"
					pattern="${sessionScope.formatNumber}" />
				<label for="type" class="control-label"> <spring:message
						code="loan.detail.min.amount" />
				</label> <label for="type" class="control-label"><span class="loan_Detail"><c:out value="${minamount}" /> VND</span> </label>
			</div>
			<div class="span6">
			
				<fmt:formatNumber var="maxamount" value="${bean.entity.maxamount}"
					pattern="${sessionScope.formatNumber}" />
				<label for="type" class="control-label"> <spring:message
						code="loan.detail.max.amount" />
				</label> <label for="type" class="control-label"><span class="loan_Detail"><c:out value="${maxamount}" /> VND</span> </label>

			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span12">			
			<div class="span6">
				<fmt:formatNumber var="amountslide" value="${bean.entity.amountslide}"
					pattern="${sessionScope.formatNumber}" />
				<label for="type" class="control-label"> <spring:message
						code="loan.detail.amount.slide" />
				</label> <label for="type" class="control-label"><span class="loan_Detail"><c:out value="${amountslide}" /> VND</span> </label>
			</div>
		</div>
	</div>

	<div class="row-fluid" style="display: none">
		<div class="span12">
			<div class="span6">

				<label for="type" class="control-label"> <spring:message
						code="loan.detail.min.tenure" />
				</label> <label for="type" class="control-label"><span class="loan_Detail">${bean.entity.mintenure} months</span> </label>
			</div>
			<div class="span6">
				<label for="type" class="control-label"> <spring:message
						code="loan.detail.max.tenure" />
				</label> <label for="type" class="control-label"><span
				 class="loan_Detail">${bean.entity.maxtenure} months</span> </label>
			</div>
		</div>
	</div>

	<div class="row-fluid"style="display: none">
		<div class="span12">
			<div class="span6">
				<label for="type" class="control-label"> <spring:message
						code="loan.detail.tenure.per.slide" />
				</label> 
				<label for="type" class="control-label"><span
					class="loan_Detail">${bean.entity.tenureperslide} months</span> </label>
			</div>
		</div>
	</div>
	<div class="row-fluid">
	<div class="span12">
		<label   for="type" class="control-label"><spring:message code="loan.detail.tenure.tenure" /> </label>
		<label for="type"  class="control-label"><span class="loan_Detail" style="color: green">${bean.entity.tenure} </span> </label> 
								</div>
	</div>
	<div class="modal-footer text_center">
		<spring:message var="msg_btn_close" code="banner.btn.close"></spring:message>
		<button type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true">Close</button>
	</div>



</form:form>
