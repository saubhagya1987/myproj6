<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>eProcurement Management</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content="width=device-width, initial-scale=1.0" name="viewport">	
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/datepicker.css"/>
	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-responsive.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/font-awesome.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.loadmask.css"/>	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/eprocument_style.css" />
	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.horizontal.scroll.css" /> 
	
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.loadmask.min.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootbox.js"></script>
    <script type="text/javascript" src="${url}/static/js/bms.js"></script>
    <script type="text/javascript" src="${url}/static/js/jshashtable-3.0.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.numberformatter.js"></script>
	
	
	<script type="text/javascript" src="${url}/static/css/js/jquery.horizontal.scroll.js"></script> 
	


<!--[if lt IE 8]>
<link rel="stylesheet" type="text/css" href="${url}/static/css/ie8.css" /> 
<script type="text/javascript" src="${url}/static/css/js/jquery.js"></script>  
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE8.js"></script>
<script type="text/javascript" src="${url}/static/css/js/html5shiv.js"></script>
<script  src="https://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js*"></script>

<![endif]-->



 	<script type="text/javascript">
      jQuery(document).ready(function ($) {
        "use strict";
        $('#Default').perfectScrollbar();
        $('#LongThumb').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb2').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb3').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb4').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb5').perfectScrollbar({minScrollbarLength:100});
        
      });
    </script>

    <script type="text/javascript">
    	var eprocurementLocale='${sessionScope.localeSelect}';
    </script>
    
    <tiles:insertAttribute name="header" ignore="true"/> 				

<script type="text/javascript">
	// show tab from URL
	$(function () {
		   var activeTab = $('[href=' + location.hash + ']');
		   activeTab && activeTab.tab('show');
		});
</script>

<script type="text/javascript">
var num = 50; //number of pixels before modifying styles

$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.title_top').addClass('fixed1');
    } else {
        $('.title_top').removeClass('fixed1');
    }
});
$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.menu_top').addClass('fixed');
    } else {
        $('.menu_top').removeClass('fixed');
    }
});
</script>

</head>
<body>	
	
	<div id="wrap">	
		<div id="eBody">	
			<tiles:insertAttribute name="body"/>	
		</div>			
		<div id="push"></div>
	</div>
		
</body>
</html>
