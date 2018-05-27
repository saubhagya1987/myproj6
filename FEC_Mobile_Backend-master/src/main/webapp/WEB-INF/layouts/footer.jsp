<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
   <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
 <!-- start menubottom -->  	
    <div class="footer">
    	<div>	
    	<!-- 
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
                <li><a href="#"><spring:message code="msg.footer.site"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="#"><spring:message code="msg.footer.faqs"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="#"><spring:message code="msg.home.download"></spring:message></a></li>
                <li><a>|</a></li>
                <li><a href="${url}/homepage/termcondition/view"><spring:message code="msg.footer.term"></spring:message></a></li>
              </ul>
        </div>
        <div class="span2 text-center" style="padding-top: 10px;">
        <img src="${url}/static/images/t.jpg" width="23" height="23">&nbsp;<img src="${url}/static/images/y.jpg" width="23" height="23">
        <img src="${url}/static/images/f.jpg" width="23" height="23">&nbsp;<img src="${url}/static/images/i.jpg" width="23" height="23">
        </div>        
      </div>
       -->
      <div class="row-fluid">
          <div class="text-center">
          <p></p>
          <p style="margin: 0px;"><img src="${url}/static/images/img_footer_24.png" width="30" height="30"><span>(08) 39 333 888</span> <img src="${url}/static/images/img_footer_ac.png" width="30" height="30"><span>User Guide   |  Version 01.01</span></p>
          
          <p>Copyright 2015 © FE Credit. All rights reserved.</p>
         
          </div>
      </div>
      </div>
    </div>   		
	<!-- end bottom -->