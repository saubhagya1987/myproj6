<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
 <li>
 	<c:choose>
 		<c:when test="${empty node.children }">
 			<c:set var="icon_node_cls" value="icon-folder-close icon-red"></c:set>
 		</c:when>
 		<c:otherwise>
 			<c:set var="icon_node_cls" value="icon-folder-open icon-blue"></c:set>
 		</c:otherwise>
 	</c:choose>
 	  <span><i class="${icon_node_cls}"></i><a onclick="openFilterDocument('${node.id}'); return false;">${node.text}</a></span>

 <c:if test="${not empty node.children }">
 		<ul>
		<c:forEach items="${node.children}" var="child">
		    <c:set var="node" value="${child}" scope="request"></c:set>
			<jsp:include page="RepositoryTree.jsp"/>
		</c:forEach>
		</ul>
	</c:if>
 </li>	
 