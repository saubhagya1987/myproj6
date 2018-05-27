<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="bean" required="true" rtexprvalue="true" type="vn.com.unit.fe_credit.bean.MessageBean" description="MessageBean support binding"%>
<%@ attribute name="localized" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Localized field"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="ext" %>
<div class="messages">
						<c:forEach items="${bean.messages }" var="message" >
							<div class="alert 
									${message.status=='error'?'alert-error':''} 
									${message.status=='success'?'alert-success':''} 
									${message.status=='info'?'alert-info':''} 
									fade in">
								<button class="close" data-dismiss="alert" type="button">×</button>
								<c:if test="${localized}"><spring:message code="${message.message}"/></c:if>
								<c:if test="${not localized}">${message.message}</c:if>
							</div>
						</c:forEach>
</div>