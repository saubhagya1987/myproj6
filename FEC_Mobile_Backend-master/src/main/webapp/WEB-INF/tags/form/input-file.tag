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
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="id name"%>
<%@ attribute name="dataurl" required="false" rtexprvalue="true" type="java.lang.String" description="data-url"%>
<%@ attribute name="icon" required="false" rtexprvalue="true" type="java.lang.String" description="icon"%>
<%@ attribute name="errorpath" required="false" rtexprvalue="true" type="java.lang.String" description="errorpath"%>
<%@ attribute name="accept" required="false" rtexprvalue="true" type="java.lang.String" description="accept file"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.fileupload-ui.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.fileupload.css"/>
<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty disable }"><c:set var="disable" value="false"></c:set> </c:if>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>

<c:choose >
	<c:when test="${disable }">
		<c:set var="disableTag" value="disabled='true'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="disableTag" value=""></c:set>
		
	</c:otherwise>
</c:choose>

<c:choose >
	<c:when test="${ not empty dataurl }">
		<c:set var="dataurlTag" value="data-url='${dataurl }'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="dataurlTag" value=""></c:set>
		
	</c:otherwise>
</c:choose>
<c:choose >
	<c:when test="${ not empty errorpath }">
		<c:set var="errortag" value="${errorpath }"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="errortag" value="${path }"></c:set>
		
	</c:otherwise>
</c:choose>
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
			<c:if test="${ not empty icon }"><i class="${icon}"></i></c:if>
				${label_msg } 
				<c:if test="${required }">
					<span class="required">*</span>
				</c:if>
			</label>
			<div class="controls">
			<input type="text" readOnly id="${id }path"/>
			<span class="btn btn-info fileinput-button" ng-class="{disabled: disabled}">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>${label_msg}...</span>
                    
               
				<input name="${path }[]" type="file" id="${id }" onchange="$('#${id }path').val(this.value);" ${disableTag } ${dataurlTag} multiple accept=${accept} />
				 </span>
				
				<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
				<span class="help-inline ${error_clss}_msg">
					<c:if test="${not empty helpLine_msg }">
						<c:out value="${helpLine_msg }"></c:out>
					</c:if>
					
					<form:errors path="${errortag}" cssClass="error" ></form:errors>
					
				</span>
			</div>
</div>
</c:if>