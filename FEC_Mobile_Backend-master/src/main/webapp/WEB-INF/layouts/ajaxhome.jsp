<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />	
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bms_style.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-responsive.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/font-awesome.css"/>
	
	<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js"></script>
     <script type="text/javascript" src="${url}/static/js/bootbox.js"></script>
    <script type="text/javascript" src="${url}/static/js/bms.js"></script>
    
</head>
<body>
		<tiles:insertAttribute name="aa" ignore="true"/> 				
			
		<tiles:insertAttribute name="body" ignore="true"/> 				
			
</body>
</html>