<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bms_style.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-responsive.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/font-awesome.css"/>
<%-- <link rel="stylesheet" type="text/css" href="${url}/static/css/conteccons_style.css" /> --%>
<link rel="stylesheet" type="text/css" href="${url}/static/css/css/demo.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/css/style1.css"/>

<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${url}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${url}/static/css/js/modernizr.custom.86080.js"></script><title>Coteccons Management</title>
</head>

<body class="ebody">
<div class="site-wrapper">
  <div class="site-wrapper-inner">
  	
    <div class="cover-container">
		<%-- <div class="logo1">
        	<img src="${url}/static/images/Logo_large.png" alt=""/>
        </div>
        <div style="width:100%;">
        	<img class="img_icon" src="${url}/static/images/icon2.png" alt="" width="30%" />
        </div>
         --%>
        <div class="wrap-login">
           <h2 style="padding-top: 20px;">HỆ THỐNG</h2>
            <h2 style="color: #fff;font-size: 30px;">QUẢN LÝ THIẾT BỊ</h2>
			
        	<div class="login"><h1 style="color: red;font-size: 18px;">Xin lỗi, hệ thống đang gặp sự cố </h1>
        	 <button type="submit" class="btn btn-info" style="width:80px;" onclick="location.href='${url}'">Trở lại</button>
        	</div>
        </div>
    </div>
  </div>
</div>
</body>
</html>
