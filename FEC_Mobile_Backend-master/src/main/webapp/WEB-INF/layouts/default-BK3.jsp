<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>VCCB</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content="width=device-width, initial-scale=1.0" name="viewport">	
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
	
	<link rel="stylesheet" type="text/css" href="${url}/static/css3/bootstrap.min.css"/>
		
	<link rel="stylesheet" type="text/css" href="${url}/static/css3/MyStyle.css" />
	
	<link rel="stylesheet" type="text/css" href="${url}/static/css3/non-responsive.css"/>

	
	<script type="text/javascript" src="${url}/static/js3/jquery.js"></script>
	<script type="text/javascript" src="${url}/static/js3/bootstrap.min.js"></script>



</head>
<body>	
	<span style="display: none" id="loading"><spring:message code="msg.loading" ></spring:message></span>

		<tiles:insertAttribute name="menu"/> 	
		<div id="eBody">	
			<tiles:insertAttribute name="body"/>	
		</div>			
		<div id="push"></div>
	
		<tiles:insertAttribute name="footer"/> 

</body>
</html>
