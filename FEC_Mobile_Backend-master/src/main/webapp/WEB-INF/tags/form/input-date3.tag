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
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="Id Field"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required"%>
<%@ attribute name="cssInput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="disable" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Disable field"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Visible field"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Read Only field"%>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.String" description="Value of field"%>
<%@ attribute name="inputId" required="false" rtexprvalue="true" type="java.lang.String" description="Id input"%>
<%@ attribute name="eventFunction" required="false" rtexprvalue="true" type="java.lang.String" description="Change date function"%>
<%@ attribute name="eventName" required="false" rtexprvalue="true" type="java.lang.String" description="Change date name"%>
<%@ attribute name="minViewMode" required="false" rtexprvalue="true" type="java.lang.Integer" description="minViewMode bootstrap"%>
<%@ attribute name="format" required="false" rtexprvalue="true" type="java.lang.String" description="format date picker bootstrap"%>
<%@ attribute name="isGrid" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Grid field"%>
<%@ attribute name="csswidth" required="false" rtexprvalue="true" type="java.lang.String" description="width"%>
<%@ attribute name="inputHidenId" required="false" rtexprvalue="true" type="java.lang.String" description="Id input"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty disable }"><c:set var="disable" value="false"></c:set> </c:if>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>
<c:if test="${empty readonly }"><c:set var="readonly" value="false"></c:set> </c:if>
<c:if test="${empty id }"><c:set var="id" value=""></c:set> </c:if>
<c:if test="${empty disable}"> <c:set var="disable" value="false"></c:set></c:if>
<c:if test="${empty format}"> <c:set var="format" value="${fn:toLowerCase(sessionScope.formatDate)}"></c:set></c:if>
<c:if test="${empty csswidth }"><c:set var="csswidth" value="100"></c:set> </c:if>

<c:choose>
	<c:when test="${empty disable or !disable}"><c:set value="" var="disableTxt"></c:set> </c:when>
	<c:otherwise> <c:set value="disabled='disabled'" var="disableTxt"></c:set> </c:otherwise>
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


<c:choose>
<c:when test="${isGrid}">

				<div class="input-append date" id="${id}" data-date="${value}"
													data-date-format="${ format}">
													<form:input path="${path}"  value="${value}" id="${inputId }" class="span12"  cssErrorClass="span12 errorfeborder"/>
												<%-- 	<input class="span12 ${cssInput }" size="16" type="text" 
														name="${path}" value="${value}" ${disableTxt} id="${inputId }" style="width: ${csswidth}px"> --%> <span class="add-on"><i
														class="icon-calendar"></i></span>
														
				</div>
				<c:if test="${disable}">
			<%-- 	<input type="hidden" id="${inputHidenId }" name="${path}" value="${value}"  /> --%>
				</c:if>
				<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
				<span class="help-inline ${error_clss}_msg">
					<c:if test="${not empty helpLine_msg }">
						<c:out value="${helpLine_msg }"></c:out>
					</c:if>
					</span>
			 <c:set var="function" value=".on('${eventName}',"> </c:set>
			 <c:if test="${not empty eventFunction}">
			 	<c:set var="function" value="${function } ${eventFunction})"/>
			 </c:if>
			<c:if test="${!disable}">
				<script type="text/javascript">
				$("#${id}").datepicker({
					language: '${sessionScope.localeSelect}',
					format: '${format}',
					autoclose:true,weekStart: 1,
				 <c:if test="${not empty minViewMode}">
				 	minViewMode: ${minViewMode}
				 </c:if>
				})${not empty eventFunction?function:''};	

				$(".input-append.date input").focus(function(){
					$(this).parent().attr("data-date","");					
				});
						
				</script>
			</c:if>
			
</c:when>
<c:otherwise>
<div class="control-group">
	
			<label class="control-label" for="${path}">
				${label_msg } 
				<c:if test="${required }">
					<span class="required">*</span>
				</c:if>
			</label>
			<div class="controls">
				<div class="input-append date" id="${id}" data-date="${value}"
													data-date-format="${ format}">
													<form:input path="${path}"  value="${value}" id="${inputId }"cssClass="${cssInput}"  cssErrorClass="span12 errorfeborder"/>
													<%-- <input class="span12" size="8" type="text"
														name="${path}" value="${value}" ${disableTxt} id="${inputId }" > --%> <span class="add-on"><i
														class="icon-calendar"></i></span>
				</div>
				<c:if test="${disable}">
				</c:if>
				<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
				<br>
				<span class="help-inline ${error_clss}_msg">
					<c:if test="${not empty helpLine_msg }">
						<c:out value="${helpLine_msg }"></c:out>
					</c:if>
					<form:errors path="${path}" cssClass="error" ></form:errors>
				</span>
			</div>
			 <c:set var="function" value=".on('${eventName}',"> </c:set>
			 <c:if test="${not empty eventFunction}">
			 	<c:set var="function" value="${function } ${eventFunction})"/>
			 </c:if>
			<c:if test="${!disable}">
				<script type="text/javascript">
				$("#${id}").datepicker({
					language: '${sessionScope.localeSelect}',
					format: '${format}',
					autoclose:true,weekStart: 1,
				 <c:if test="${not empty minViewMode}">
				 	minViewMode: ${minViewMode}
				 </c:if>
				})${not empty eventFunction?function:''};		

				$(".input-append.date input").focus(function(){
					$(this).parent().attr("data-date","");					
				});
					
				</script>
			</c:if>
			
</div>
</c:otherwise>
</c:choose>

</c:if>