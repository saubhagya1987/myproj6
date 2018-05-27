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
<%@ attribute name="data" required="false" rtexprvalue="true" type="java.lang.Object" description="Data Object"%>
<%@ attribute name="itemLable" required="false" rtexprvalue="true" type="java.lang.String" description="Field label for user data"%>
<%@ attribute name="emptyOption" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Has emty option"%>
<%@ attribute name="itemCode" required="false" rtexprvalue="true" type="java.lang.String" description="Field Value for user data"%>
<%@ attribute name="from" required="false" rtexprvalue="true" type="java.lang.Integer" description="From Value"%>
<%@ attribute name="to" required="false" rtexprvalue="true" type="java.lang.Integer" description="To Value"%>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="Id Field"%>
<%@ attribute name="isGrid" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Grid field"%>
<%@ attribute name="csswidth" required="false" rtexprvalue="true" type="java.lang.String" description="width"%>
<%@ attribute name="onchange" required="false" rtexprvalue="true" type="java.lang.String" description="on change"%>
<%@ attribute name="multiple" required="false" rtexprvalue="true" type="java.lang.Boolean" description="multiple"%>
<%@ attribute name="css_style" required="false" rtexprvalue="true" type="java.lang.String" description="Css Style"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>
<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty disable }"><c:set var="disable" value="false"></c:set> </c:if>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>
<c:if test="${empty emptyOption }"><c:set var="emptyOption" value="false"></c:set> </c:if>
<c:if test="${empty id }"><c:set var="id" value=""></c:set> </c:if>
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

					<c:if test="${ visible}">
						<form:select path="${path }" cssClass="${cssInput}" onchange="${onchange}" disabled="${disable }" id="${id }" cssStyle="width:${csswidth}px" multiple="${multiple }" >
							<c:choose>
								<c:when test="${not empty from and not empty to }">
									<c:if test="${emptyOption }">
										<option value=""><spring:message code="msg.choose"/></option>
										</c:if>
									<c:forEach begin="${from }" end="${to }" var="item">
										
										
										<form:option value="${item }" ></form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty itemCode}">
											<c:if test="${emptyOption }">
											<option value=""><spring:message code="msg.choose"/></option>
											</c:if>
											<form:options items="${data}" itemLabel="${itemLable}" 
												itemValue="${itemCode}" />
										</c:when>
										<c:otherwise>
											<c:if test="${emptyOption }">
											<option value=""><spring:message code="msg.choose"/></option>
											</c:if>
											<form:options items="${data}" itemLabel="${itemLable}"
												 />
										</c:otherwise>
									</c:choose>
									
								</c:otherwise>
							</c:choose>
						</form:select>
					</c:if>
					<c:if test="${disable}">
					<form:hidden path="${path}"/>
					</c:if>
					<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
					<span class="help-inline ${error_clss}_msg">
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
						<span class="required">*</span>
					</c:if>
				</label>
				<div class="controls">
					<c:if test="${ visible}">
						<form:select path="${path }" cssClass="${cssInput}" onchange="${onchange}" disabled="${disable }" id="${id }"  multiple="${multiple }" 
							cssStyle="${css_style}">
							<c:choose>
								<c:when test="${not empty from and not empty to }">
									<c:if test="${emptyOption }">
										<option value=""><spring:message code="msg.choose"/></option>
										</c:if>
									<c:forEach begin="${from }" end="${to }" var="item">
										
										
										<form:option value="${item }" ></form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty itemCode}">
											<c:if test="${emptyOption }">
											<option value=""><spring:message code="msg.choose"/></option>
											</c:if>
											<form:options items="${data}" itemLabel="${itemLable}" 
												itemValue="${itemCode}" />
										</c:when>
										<c:otherwise>
											<c:if test="${emptyOption }">
											<option value=""><spring:message code="msg.choose"/></option>
											</c:if>
											<form:options items="${data}" itemLabel="${itemLable}"
												 />
										</c:otherwise>
									</c:choose>
									
								</c:otherwise>
							</c:choose>
						</form:select>
					</c:if>
					<c:if test="${disable}">
					<form:hidden path="${path}"/>
					</c:if>
					<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
					<span class="help-inline ${error_clss}_msg">
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