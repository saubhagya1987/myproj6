<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
   <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
 <!-- start menubottom --> 
 <div class="footer1">	
    <div class="footer">
    	<div class="container">	
    	<div class="row">
        <div class="span1">
        </div>
        <div class="span9">
        	  <ul class="menu-footer">
                <li><a href="${url}/homepage"><spring:message code="msg.footer.home"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="https://vietcapitalbank.com.vn/gioi-thieu-chung" target="_blank"><spring:message code="msg.footer.about"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="https://vietcapitalbank.com.vn/lien-he" target="_blank"><spring:message code="msg.footer.contact"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="javascript:loadSiteMap()"><spring:message code="msg.footer.site"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="javascript:void(0);" onclick="popupDetailFAQs('1');"><spring:message code="msg.footer.faqs"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="javascript:void(0);" onclick="popupDetailFAQs('2');"><spring:message code="msg.home.download"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="${url}/homepage/termcondition/view"><spring:message code="msg.footer.term"></spring:message></a></li>
              </ul>
        </div>
        <div class="span2 text-center" style="padding-top: 10px;">
      <%--   <a href="https://twitter.com/VietcapitalBank" target="_blank">
        	<img src="${url}/static/images/t.jpg" width="23" height="23">
        </a> 
        <a href="https://www.youtube.com/channel/UCb-T7-ADq-JvN7ELCDhZ_Ag" target="_blank">
        	<img src="${url}/static/images/y.jpg" width="23" height="23">
        </a>
        <a href="https://www.linkedin.com/company/viet-capital-bank" target="_blank">
        	<img src="${url}/static/images/i.jpg" width="23" height="23">
        </a>
        --%>
        <a href="https://plus.google.com/u/1/" target="_blank">
        	<img src="${url}/static/images/g.png" width="23" height="23">
        </a>
        <a href="https://www.facebook.com/nganhangbanviet" target="_blank">
        	<img src="${url}/static/images/f.jpg" width="23" height="23">
        </a>
        
        </div>        
      </div>
      <div class="row">
          <div class="span12 text-center">
          <p><span style="color: #ff0000;">Hotline: </span><span style="color: #ff0000; font-weight: bold;">1900 555 555</span><br>
          © 2015. <spring:message code="footer.copyright.vietcapitalbank"></spring:message> | Swift: VCBCVNVX<br>
          <spring:message code="footer.address"/> <br>
          F: (08) 62 638 668 | E: 1800555599@vietcapitalbank.com.vn | W: www.vietcapitalbank.com.vn</p>
          </div>
         <a href="#" id="back-to-top"> <img src="${url}/static/images/Scroll_Top.png" width="57" height="54"> </a>
      </div>
      </div>
    </div>   	
 </div>   
    <div id="popupFAQsId"></div>
	<!-- end bottom -->
	<script type="text/javascript">
		$(document).ready(function() {
			if ($('#back-to-top').length) {
			    var scrollTrigger = 100, // px
			        backToTop = function () {
			            var scrollTop = $(window).scrollTop();
			            if (scrollTop > scrollTrigger) {
			                $('#back-to-top').addClass('show');
			            } else {
			                $('#back-to-top').removeClass('show');
			            }
			        };
			    backToTop();
			    $(window).on('scroll', function () {
			        backToTop();
			    });
			    $('#back-to-top').on('click', function (e) {
			        e.preventDefault();
			        $('html,body').animate({
			            scrollTop: 0
			        }, 700);
			    });
			}
		});
		
		function popupDetailFAQs(strType) {
			ajaxUrl = '${url}/getFAQs?type='+strType;
			$.ajax({
				url : ajaxUrl,
				success : function(result) {
					$("#popupFAQsId").html(result);
					$("#popupFAQsId").modal('show');
				}
			});
		}
        function loadSiteMap(){
            window.location.href = '${url}/homepage/site_map';
        }
	</script>