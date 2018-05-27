<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String" description="Path to binding"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="Label name"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String" description="Label code"%>
<%@ attribute name="helpLine" required="false" rtexprvalue="true" type="java.lang.String" description="Help line"%>
<%@ attribute name="helpLineCode" required="false" rtexprvalue="true" type="java.lang.String" description="Help line code"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required"%>
<%@ attribute name="cssInput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="disable" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Disable field"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Visible field"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Readonly field"%>
<%@ attribute name="cols" required="false" rtexprvalue="true" type="java.lang.Integer" description="Column"%>
<%@ attribute name="rows" required="false" rtexprvalue="true" type="java.lang.Integer" description="Row "%>
<%@ attribute name="icon" required="false" rtexprvalue="true" type="java.lang.String" description="icon"%>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="Id Field"%>
<%@ attribute name="isGrid" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Grid field"%>
<%@ attribute name="labelwidth" required="false" rtexprvalue="true" type="java.lang.String" description="label length"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
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
<c:choose>
<c:when test="${isGrid}">	
		<form:textarea path="${path }" cssClass="${cssInput}" cols="${cols }" rows="${rows }" htmlEscape="true" disabled="${disable}" id="${id }" readonly="${readonly }"/>
		<span class="help-inline">
			<c:if test="${not empty helpLine_msg }">
				<c:out value="${helpLine_msg }"></c:out>
			</c:if>
			<form:errors path="${path}" cssClass="error" ></form:errors>
		</span>
</c:when>
<c:otherwise>
	<div class="control-group">
				<label class="control-label" style="width:${labelwidth} " for="${path}">
				<c:if test="${ not empty icon }"><i class="${ icon}"></i></c:if>
					${label_msg } 
					<c:if test="${required }">
						<span class="required">*</span>
					</c:if>
				</label>
				<div class="controls">
					<form:textarea path="${path }" cssClass="${cssInput}" cols="${cols }" rows="${rows }" htmlEscape="true" disabled="${disable}" id="${id }" readonly="${readonly }"/>
					<span class="help-inline">
						<c:if test="${not empty helpLine_msg }">
							<c:out value="${helpLine_msg }"></c:out>
						</c:if>
						<form:errors path="${path}" cssClass="error" ></form:errors>
					</span>
				</div>
	</div>
</c:otherwise>
</c:choose>
</c:if>