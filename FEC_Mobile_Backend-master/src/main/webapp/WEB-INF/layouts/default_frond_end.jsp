<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MOBILE MANAGEMENT SYSTEM</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content="width=device-width, initial-scale=1.0" name="viewport">	
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/datepicker.css"/>
		
	<link rel="stylesheet" type="text/css" href="${url}/static/css/font-awesome.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.loadmask.css"/>	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/vccb_style.css" />
	<link rel="stylesheet" type="text/css" href="${url}/static/css/non-responsive.css" />
	<link rel="stylesheet" type="text/css" href="${url}/static/css/card_vccb.css" />

		
	<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.loadmask.min.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.vi.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${url}/static/js/bootbox.js"></script>
    <script type="text/javascript" src="${url}/static/js/jshashtable-3.0.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.numberformatter.js"></script>

	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.horizontal.scroll.css" /> 
	<script type="text/javascript" src="${url}/static/css/js/jquery.horizontal.scroll.js"></script> 

 	<link rel="stylesheet" type="text/css" href="${url}/static/css/js/perfect-scrollbar.css" /> 
	<script type="text/javascript" src="${url}/static/css/js/perfect-scrollbar.js"></script> 
	
	<script type="text/javascript" src="${url}/static/js/jquery.fileDownload.js"></script>
	
	<c:if test="${sessionScope.localeSelect !='vi'}">
		<script type="text/javascript" src="${url}/static/js/jquery.multiple.select.js"></script>	
	</c:if>	
	<c:if test="${sessionScope.localeSelect =='vi'}">
		<script type="text/javascript" src="${url}/static/js/jquery.multiple.select.vi.js"></script>	
	</c:if>
	
	<script type="text/javascript" src="${url}/static/js/pdfobject.js"></script>
	<script type="text/javascript" src="${url}/static/js/tinymce/tinymce.min.js"></script>
	<script type="text/javascript" src="${url}/static/js/swfobject2.js"></script>	
	<script type="text/javascript" src="${url}/static/js/bms.js"></script>
	<script type="text/javascript" src="${url}/static/js/core.js"></script>
	
	<script type="text/javascript" src="${url}/static/plugins/jquery.ajaxupload.js"></script>
	<script type="text/javascript" src="${url}/static/js/plupload-2.1.2/plupload.full.min.js"></script>
	
<!-- hide 	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-responsive.css"/>
	
 end hide -->	
	
		
	 <!-- <style>
        .contentHolder { position:relative; margin:0px auto; padding:0px; width: 100%; min-height: 50px; overflow: hidden; }
        .spacer { text-align:center }
        h2 { text-align: center; }
      </style> -->

<!-- start srollbar -->
<%-- <link rel="stylesheet" type="text/css" href="${url}/static/css/scrollbar/style.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/scrollbar/jquery.mCustomScrollbar.css" />
<script type="text/javascript" src="${url}/static/css/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script> 	 --%>
<!-- <script>window.jQuery || document.write('<script type="text/javascript" src="${url}/static/css/scrollbar/jquery-1.11.0.min.js"><\/script>')</script>	 -->
<!-- <script>
		(function($){
			$(window).load(function(){
				
				$.mCustomScrollbar.defaults.theme="light-2"; //set "light-2" as the default theme
				
				$(".demo-y").mCustomScrollbar();
				
				$(".demo-x").mCustomScrollbar({
					axis:"x",
					advanced:{autoExpandHorizontalScroll:true}
				});
				
				$(".demo-yx").mCustomScrollbar({
					axis:"yx"
				});
				
				$(".scrollTo a").click(function(e){
					e.preventDefault();
					var $this=$(this),
						rel=$this.attr("rel"),
						el=rel==="content-y" ? ".demo-y" : rel==="content-x" ? ".demo-x" : ".demo-yx",
						data=$this.data("scroll-to"),
						href=$this.attr("href").split(/#(.+)/)[1],
						to=data ? $(el).find(".mCSB_container").find(data) : el===".demo-yx" ? eval("("+href+")") : href,
						output=$("#info > p code"),
						outputTXTdata=el===".demo-yx" ? data : "'"+data+"'",
						outputTXThref=el===".demo-yx" ? href : "'"+href+"'",
						outputTXT=data ? "$('"+el+"').find('.mCSB_container').find("+outputTXTdata+")" : outputTXThref;
					$(el).mCustomScrollbar("scrollTo",to);
					output.text("$('"+el+"').mCustomScrollbar('scrollTo',"+outputTXT+");");
				});
				
			});
		})(jQuery);
	</script> -->	
	<!-- end srollbar -->

<script type="text/javascript">

$(document).ready(function() {		
	var num = $('.banner-top1').height(); //number of pixels before modifying styles
	var fix = $('#menu_top_vccb').height();
	$(window).bind('scroll', function () {
	    if ($(window).scrollTop() > num) {
	        $('.table_top').addClass('fixed_table');
	    } else {
	        $('.table_top').removeClass('fixed_table');
	    }
	    if ($(window).scrollTop() > num) {
	        $('.title_top').addClass('fixed1');
	    } else {
	        $('.title_top').removeClass('fixed1');
	    }
	    if ($(window).scrollTop() > num) {
	        $('#menu_top_vccb').addClass('fixed');
	        $('.banner-top1').css('height',(num+fix)+'px');
	    } else {
	        $('#menu_top_vccb').removeClass('fixed');
	        $('.banner-top1').css('height',num+'px');
	    }
	}); 
	/* accordion("accordion2");
	accordion("accordion3");
	accordion("accordion4");
	accordion("accordion2_3");
	accordion("accordion2_2"); */

	  //blockUI
	  $(window).unload(function () {
           unblockUI();
       });
     
       $(".checkWaitLoading").click(function () {
       	blockUI();
       });
       
       // remove key press enter 
		$(document).keypress(function(e) {			
			if(e.which == 13 && $("textarea").is(":focus") == false) {  
		    	e.preventDefault();				
		    }
		});
       
		$(document).bind('keydown keyup', function(e) {
		      if(e.keyCode == 116) {//f5
		         window.location.href = document.URL;
		      }
		  });
		
		
		
	
});
//Block ui
function blockUI() {
    $("body").mask($("#loading").text());
};

//Un block ui
function unblockUI() {
	$("body").unmask();
};
</script>





<!--  <script>

      jQuery(document).ready(function ($) {
        "use strict";
        $('#Default').perfectScrollbar();
        $('#SuppressScrollX').perfectScrollbar({suppressScrollX: true});
        $('#SuppressScrollY').perfectScrollbar({suppressScrollY: true});
        $("#Default").find(".ps-scrollbar-x-rail").css({"opacity":0.5});
        $("#Default").find(".ps-scrollbar-y-rail").css({"opacity":0.5});
      });
    </script> -->

<!--[if IE 8]>
<link rel="stylesheet" type="text/css" href="${url}/static/css/ie8.css" /> 

<![endif]-->


<!-- 
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
 -->
 
 
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




function selectedValue() {
	// no need implement.
}
</script>
<script>
function fbshareCurrentPage(){
	window.open("https://www.facebook.com/sharer/sharer.php?u="+escape(window.location.href)+"&t="+document.title, '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');
	return false; 
}
</script>

</head>
<body>	
<div class="bodybody">	
	
	<span style="display: none" id="loading"><spring:message code="msg.loading" ></spring:message></span>

		<tiles:insertAttribute name="menu"/> 	
		<div id="eBody">
			<div class="unit_body">
			<tiles:insertAttribute name="body"/>
			</div>
				
		</div>			
		<div id="push"></div>
	
		<tiles:insertAttribute name="footer"/>
<jsp:include page="default_popup.jsp"></jsp:include>
<div id="popupBelowId" style="display: none;"></div>


	
<div id="chat_suppport"></div>
<script type="text/javascript">

(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.4&appId=164983673835187";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

$(document).ready(function() {	
	
	
	 $.ajax({
         url: '${url}/popup_below',
         cache: false,
         type: "GET",
         async: false,
         data: {
         	
         },
         success: function(data) { 
        	 $('#popupBelowId').html(data); 
	           if($("#showPopupBelow").val() == 'true'){
	        	   $('#popupBelowId').attr('style', '');
	       		   var timeDisplayPopupBelow = $("#timeDisplayPopupBelow").val();
		       		setTimeout(function(){			
		    			$('#popupBelowId').remove();
		    		}, timeDisplayPopupBelow);
	       	  }  
         }
     });
	 var accountOnlineId='${sessionScope.accountOnlineId}';
	 var guestOnlineId='${sessionScope.guestOnlineId}'; 
	 if(accountOnlineId!=null && accountOnlineId!=''){
		 openChatServer();
	 }else if(guestOnlineId!=null && guestOnlineId!=''){
		 openChatPopup();
	 }
	 
     

});
function openLoginPopup (){
	var	guestOnlineId='${sessionScope.guestOnlineId}';
	var accountOnlineId='${sessionScope.accountOnlineId}';
	if(accountOnlineId!=null && accountOnlineId!=''){
		openChatServer();
	}else if(guestOnlineId!=null && guestOnlineId!=''){
		openChatPopup();
	}else{		
		$.ajax({
	        url: '${url}/chat_support_vccb/login_client',
	        cache: false,
	        type: "GET",
	        async: false,       
	        success: function(data) { 
	        	 $('#chat_suppport').html(data);
	        }
	    });
	}
}

function openChatPopup (){
	$("#popup_container_chat").empty();
	$("#popup_container_chat").html("");
	$("#popup_container_chat").remove();
	$.ajax({
        url: '${url}/chat_support_vccb/chat_client',
        cache: false,
        type: "GET",
        async: false,       
        success: function(data) { 
        	 $('#chat_suppport').html(data);
        	 var objDiv = document.getElementById("chat_online_message");
        	 objDiv.scrollTop = objDiv.scrollHeight;
        }
    });
}

function openChatServer (){
	$.ajax({
        url: '${url}/chat_support_vccb/chat_server',
        cache: false,
        type: "GET",
        async: false,       
        success: function(data) { 
        	$('#chat_suppport').html(data);
       		$(".chat_online_message").each(function(){
				var id=$(this).attr("id");
				var objDiv = document.getElementById(id);
				objDiv.scrollTop = objDiv.scrollHeight;
       		});        	
        }
    });
}
function closePopupBelow(){
	$('#popupBelowId').remove();
}
</script>

</div>

</body>
</html>
