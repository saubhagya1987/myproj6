<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<c:set var="parentClss" value=""></c:set>
<c:set var="parentChk" value=""></c:set>
<c:if test="${not empty node.parent}">
	<c:set var="parentClss" value="treegrid-parent-${node.parent }"></c:set>
	<c:set var="parentChk" value="${node.parent }"></c:set>
</c:if>
<tr class="treegrid-${node.id } ${parentClss}" >
	<td width="20px;" class="text_center">
		<input type="checkbox" name="${chkName}" id="${chkName}" value="${node.id}" class="${chkName}${parentChk}" onchange="checkBoxChange(${node.id}, this, '${chkName}')"> 
	</td>
	<c:choose>
	  <c:when test="${levelItem == 0 }">
	    <td> 
			${node.code}
		</td>
	  </c:when>
	  <c:otherwise>
	    <td style="padding-left: ${levelItem*30}px;"> 
			
			${node.code}
		</td>
	  </c:otherwise>
	</c:choose>
	
	<td> ${node.text}</td>
</tr>
	<c:if test="${not empty node.children }">
		<c:set var="levelItem" value="${levelItem + 1}" scope="request"></c:set>
		
		<c:forEach items="${node.children}" var="child">
		    <c:set var="node" value="${child}" scope="request"></c:set>
			<jsp:include page="TeamExpenseCategoryListTreeGrid.jsp"/>
		</c:forEach>
		
		<c:set var="levelItem" value="${levelItem - 1}" scope="request"></c:set>
	</c:if>
	
