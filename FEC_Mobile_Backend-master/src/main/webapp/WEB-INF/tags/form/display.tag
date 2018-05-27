<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="Label name"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String" description="Label code"%>
<%@ attribute name="cssOutput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="disable" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Disable field"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Visible field"%>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.String" description="Value of field"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Class of field"%>
<%@ attribute name="icon" required="false" rtexprvalue="true" type="java.lang.String" description="icon"%>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="ID"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
<c:set var="label_msg" value=""></c:set>
<c:if test="${empty disable }"><c:set var="disable" value="false"></c:set> </c:if>
<c:if test="${empty cssClass }"><c:set var="cssClass" value=""></c:set> </c:if>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>
	<c:if test="${ not empty label }"> <c:set var="label_msg" value="${label}"></c:set></c:if>
	<c:if test="${ not empty labelCode }">
		<spring:message var="label_msg1" code="${labelCode}"></spring:message>
		 <c:set var="label_msg" value="${label_msg1}"></c:set>
	</c:if>

<c:if test="${visible }">
<div class="control-group">
			<label class="control-label" for="${path}">
			<c:if test="${ not empty icon }"><i class="${ icon}"></i></c:if>
				${label_msg } 
				<c:if test="${required }">
					<span class="required">(*)</span>
				</c:if>
			</label>
			<div class="controls">
			<label class="checkbox ${cssClass} text_color" id="${id }">
				${value }
				</label>
			</div>
</div>
</c:if>