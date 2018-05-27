<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String" description="Path to binding"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="Label name"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String" description="Label code"%>
<%@ attribute name="helpLine" required="false" rtexprvalue="true" type="java.lang.String" description="Help line"%>
<%@ attribute name="helpLineCode" required="false" rtexprvalue="true" type="java.lang.String" description="Help line code"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required"%>
<%@ attribute name="cssInput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="disable" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Disable field"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Visible field"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Read Only field"%>
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="id field"%>
<%@ attribute name="helpLineId" required="false" rtexprvalue="true" type="java.lang.String" description="Help line id"%>
<%@ attribute name="errorid" required="false" rtexprvalue="true" type="java.lang.String" description="Error id"%>
<%@ attribute name="maxlength" required="false" rtexprvalue="true" type="java.lang.Integer" description="Max length"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
	
<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty helpLineId }"><c:set var="helpLineId" value=""></c:set> </c:if>
<c:if test="${empty maxlength }"><c:set var="maxlength" value=""></c:set> </c:if>
<c:if test="${empty disable }"><c:set var="disable" value="false"></c:set> </c:if>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>
<c:if test="${empty readonly }"><c:set var="readonly" value="false"></c:set> </c:if>

	<c:if test="${ not empty label }"> <c:set var="label_msg" value="${label}"></c:set></c:if>
	<c:if test="${ not empty labelCode }">
		<spring:message var="label_msg1" code="${labelCode}"></spring:message>
		 <c:set var="label_msg" value="${label_msg1}"></c:set>
	</c:if>

	<c:if test="${ not empty helpLine }"> <c:set var="helpLine_msg" value="${helpLine}"></c:set></c:if>
	<c:if test="${ not empty helpLineCode }">
		 <spring:message var="helpLine_msg1" code="${helpLineCode}"></spring:message>
		 <c:set var="helpLine_msg" value="${helpLine_msg1}"></c:set>
	</c:if>
<c:if test="${visible }">
<div class="control-group">
	
			<label class="control-label" for="${path}">
				${label_msg } 
				<c:if test="${required }">
					<span class="required">*</span>
				</c:if>
			</label>
			<div class="controls">
				<form:input path="${path }" cssClass="${cssInput}" disabled="${disable }" readonly="${readonly }" maxlength="${maxlength}"
				id="${id }"/>
				<!-- error input -->
				<c:if test="${not empty errorid}">
					<spring:message var="error_input_msg" code="transfer.inputnumber.error"></spring:message>
					<span class="required pointer" id="${errorid}" title="${error_input_msg }" style="display: none;">!!!</span>
				</c:if>
				<!-- end error -->
				<c:if test="${disable}">
				<form:hidden path="${path}" cssClass="${cssInput}"/>
				</c:if>
				<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
				<span class="help-inline ${error_clss}_msg" id="${helpLineId}">
					<c:if test="${not empty helpLine_msg }">
						<c:out value="${helpLine_msg }"></c:out>
					</c:if>
					<form:errors path="${path}" cssClass="error" ></form:errors>
				</span>
			</div>
</div>
</c:if>
<script type="text/javascript">
			$("#${id }").blur(function() {
				   $(this).parseNumber({format:"#.###.##", locale:"${sessionScope.localeSelect}"});
				   $(this).formatNumber({format:"#.###.##", locale:"${sessionScope.localeSelect}"});
			});
			   $('#${id }').parseNumber({format:"#.###.##", locale:"${sessionScope.localeSelect}"});
			   $('#${id }').formatNumber({format:"#.###.##", locale:"${sessionScope.localeSelect}"});

</script>