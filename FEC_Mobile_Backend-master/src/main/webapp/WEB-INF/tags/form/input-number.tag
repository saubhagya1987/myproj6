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
<%@ attribute name="isGrid" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Grid field"%>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.String" description="value field"%>
<%@ attribute name="format" required="false" rtexprvalue="true" type="java.lang.Boolean" description="use format or not"%>
<%@ attribute name="tooltip" required="false" rtexprvalue="true" type="java.lang.String" description="display tooltip"%>
<%@ attribute name="tooltipCss" required="false" rtexprvalue="true" type="java.lang.String" description="class tooltip"%>
<%@ attribute name="tooltipLabel" required="false" rtexprvalue="true" type="java.lang.String" description="label tooltip"%>
<%@ attribute name="csswidth" required="false" rtexprvalue="true" type="java.lang.String" description="width"%>
<%@ attribute name="isVND" required="false" rtexprvalue="true" type="java.lang.Boolean" description="use for VND"%>
<%@ attribute name="cssStyle" required="false" rtexprvalue="true" type="java.lang.String" description="style"%>
<%@ attribute name="allowZero" required="false" rtexprvalue="true" type="java.lang.Boolean" description="allow 0"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
	
<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty helpLineId }"><c:set var="helpLineId" value=""></c:set> </c:if>
<c:if test="${empty format }"><c:set var="format" value="true"></c:set> </c:if>
<c:if test="${empty maxlength }"><c:set var="maxlength" value=""></c:set> </c:if>
<c:if test="${empty disable }"><c:set var="disable" value="false"></c:set> </c:if>
<c:if test="${empty isVND }"><c:set var="isVND" value="false"></c:set> </c:if>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>
<c:if test="${empty readonly }"><c:set var="readonly" value="false"></c:set> </c:if>
<c:if test="${empty csswidth }"><c:set var="csswidth" value="100"></c:set> </c:if>

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

<c:if test="${format }">
<%-- 	<fmt:formatNumber var="value" pattern="#,###.##" value="${value }" /> --%>
	<c:if test="${isVND }">
<%-- 	<fmt:formatNumber var="value" pattern="#,###" value="${value }" /> --%>
	</c:if>
</c:if>
<c:choose>

<c:when test="${isGrid}">
				<c:if test="${not empty tooltip }">
					<div class="${tooltipCss }" data-toggle="tooltip" data-placement="top" title="<h5> ${tooltipLabel} = ${tooltip} </h5>" >
				</c:if>
				<form:input path="${path }" disabled="${disable }" readonly="${readonly }" maxlength="${maxlength}" value="${value}" id="${id }" cssStyle="width:${csswidth}px" cssClass="${cssInput } text_right" autocomplete="false"/>
				<c:if test="${not empty tooltip }">
					</div>
				</c:if>
				
				<!-- error input -->
				<c:if test="${not empty errorid}">
					<spring:message var="error_input_msg" code="transfer.inputnumber.error"></spring:message>
					<span class="required pointer" id="${errorid}" title="${error_input_msg }" style="display: none;"><img src="${url}/static/images/bonus-icon.png" width="18px"></img></span>
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

</c:when>
<c:otherwise>
<div class="control-group">
			
			<label class="control-label" for="${path}">
				${label_msg } 
				<c:if test="${required }">
					<span class="required" >*</span>
				</c:if>
			</label>
			
			<div class="controls">
				<c:if test="${not empty tooltip }">
					<div class="${tooltipCss }" data-toggle="tooltip" data-placement="top" title="<h5> ${tooltipLabel} = ${tooltip} </h5>" >
				</c:if>
				<form:input path="${path }" cssClass="${cssInput}" disabled="${disable }"   cssErrorClass="${cssInput} errorfeborder" readonly="${readonly }" maxlength="${maxlength}" autocomplete="false"
				id="${id }" cssStyle="${cssStyle}"/>
				<c:if test="${not empty tooltip }">
					</div>
				</c:if>
				<!-- error input -->
				<c:if test="${not empty errorid}">
					<spring:message var="error_input_msg" code="transfer.inputnumber.error"></spring:message>
					<span class="required pointer" id="${errorid}" title="${error_input_msg }" style="display: none;"><img src="${url}/static/images/bonus-icon.png" width="18px"></span>
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
</c:otherwise>
</c:choose>

</c:if>
<script type="text/javascript">
$(document).ready(function() {	
	if("${isVND}"=="true"){
		var vnd=$("#${id }").val();		
		vnd=$.parseNumber(vnd,{format:"####.##",locale:"${sessionScope.localeSelect}"});
		$("#${id }").val(vnd);
		$("#${id }").formatNumber({format:"####",locale:"${sessionScope.localeSelect}"});
		
	}
});
			$("#${id }").change(function() {
				if("${format}"=="true"){
				   $(this).parseNumber({format:"#,###.##", locale:"${sessionScope.localeSelect}"});
				   $(this).formatNumber({format:"#,###.##", locale:"${sessionScope.localeSelect}"});
				}
				if("${isVND}"=="true"){
					   $(this).parseNumber({format:"####", locale:"${sessionScope.localeSelect}"});
					   $(this).formatNumber({format:"####", locale:"${sessionScope.localeSelect}"});
					}
				if("${allowZero}"=="true"){
					   $(this).parseNumber({format:"#,##0.##", locale:"${sessionScope.localeSelect}"});
					   $(this).formatNumber({format:"#,##0.##", locale:"${sessionScope.localeSelect}"});
				}
			});
			   //$('#${id }').parseNumber({format:"#,###.##", locale:"${sessionScope.localeSelect}"});
			   //$('#${id }').formatNumber({format:"#,###.##", locale:"${sessionScope.localeSelect}"});

</script>