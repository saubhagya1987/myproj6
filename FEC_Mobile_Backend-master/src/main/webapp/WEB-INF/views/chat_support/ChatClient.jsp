<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<div id="popup_container_chat" class="popup_container_chatlive">
	  
	  <div class="row-fluid">
	  	<div class="span12 unit-circle" id="img_admin_support1" >	  		
	  		<c:choose>
		  		<c:when test="${not empty bean.guestOnline.accountOnlineId}">
		  			<c:if test="${not empty bean.guestOnline.imgConnect }">
			  			<img class="img-circle" src="${url}/ajax/download?fileName=${bean.guestOnline.imgConnect}" width="85" height="85">
			  		</c:if>
			  		<c:if test="${empty bean.guestOnline.imgConnect }">
			  			<img class="img-circle" src="${url}/static/images/hinhthe.jpg" width="85" height="85">
			  		</c:if>
		  		</c:when>
	  			<c:otherwise>
	  				<img class="img-circle" src="${url}/static/images/hinhthe.jpg" width="85" height="85">
	  			</c:otherwise>
	  		</c:choose>
	  	</div>	  
	  </div>
	  <div class="close_livechat">
		  <h4>
		  <span style="margin-right:10px;color: white;cursor: pointer; font-size: 20px;" onclick="removeDivClient();">-</span>
		  <span style="margin-right:12px;color: white;cursor: pointer;" onclick="removeDivClientDelete();">x</span>
		  </h4>
	  </div>
	  <div class="row-fluid">
	  	<div class="span12 unit-circle" id="img_admin_support2">
	  		<c:choose>
		  		<c:when test="${not empty bean.guestOnline.accountOnlineId}">
			  		<h4 id="name_support_server">${bean.guestOnline.nameConnect}</h4>
			  		<i><spring:message code="chatsupport.expert.personal" /> </i>
		  		</c:when>
	  			<c:otherwise>
	  				<h4 id="name_support_server">&nbsp;</h4>
	  				<i><spring:message code="chatsupport.begin.waiting" /> </i>
	  			</c:otherwise>
	  		</c:choose>
	  	</div>	  
	  </div>
	  
	  <div id="popup_content_chat" class="popup_content_chatlive" >
		<ext:messages bean="${bean}"></ext:messages>
		<form:form method="POST" modelAttribute="bean" id="form_chat_support" cssClass="form-horizontal">
		<form:hidden path="guestOnline.guestOnlineId" id="client_guestOnlineId"/>
			
			<div class="input-area">
				<div id="chat_online_ct_message">
				<div id="chat_online_message" class="chat_online_message">
					${bean.guestOnline.messageContent}
				</div>
				</div>
				<div class="row-fluid"></div>
				<br>
				
				<div class="content_livechat1">
					<div class="text-center margin_bottom_require">
					<div class="row-fluid">
						<div class="span12">
							<spring:message var="place_holder_input" code="chatsupport.input.placeholder"></spring:message>
							<form:textarea  placeholder="${place_holder_input}" maxlength="500"  id="messageContent" path="messageContent" rows="2" onkeypress="keyPressClientChat(event)" cssClass="span12"/>
						</div>
					</div>
					</div>
				</div>
							
			</div>			
		</form:form>
	<script type="text/javascript">
	$(document).ready(function() {			
		setTimeout(function(){ reloadlist(); }, 3000);       
		
	});

	function removeDivClient(){
		$( "#popup_container_chat").empty();
		$("#popup_container_chat").html("");
		$("#popup_container_chat").remove();
		
		
	}

	function removeDivClientDelete(){
		var	guestOnlineId='${sessionScope.guestOnlineId}';
		if(guestOnlineId!=null && guestOnlineId!=''){
			$.ajax({
		        url: '${url}/chat_support_vccb/chat_client?action=delete',
		        cache: false,
		        data :$('#form_chat_support').serialize(),	
		        async: false,       
		        type: "POST",
		        success: function(data) {    
		        	$( "#popup_container_chat").empty();
		    		$("#popup_container_chat").html("");
		    		$("#popup_container_chat").remove();  	
		        }
		    });
		}
		
	}
	
	function keyPressClientChat(event) {
	    if (event.keyCode == 13 && $("#messageContent").val()!="" && $("#messageContent").val()!=null) {
	    	$.ajax({
	            url: '${url}/chat_support_vccb/chat_client',
	            cache: false,
	            data :$('#form_chat_support').serialize(),	
	            async: false,       
	            type: "POST",
	            success: function(data) {          
	            	 if($("#name_support_server").text()==""){
		            	 $("#img_admin_support1").html($(data).find("#img_admin_support1"));
			        	 $("#img_admin_support2").html($(data).find("#img_admin_support2"));    
	            	 }	            	 
	            	 $("#chat_online_ct_message").html($(data).find("#chat_online_message"));	            	 
	            	 var objDiv = document.getElementById("chat_online_message");
	            	 objDiv.scrollTop = objDiv.scrollHeight;
	            	 try{
	            		 event.preventDefault();
	            	 }catch (e) {
					 }
	            	 $("#messageContent").val(""); 
	            	 $("#messageContent").focus();      
	            }
	        });
	    }
	
	}
	function reloadlist(){
		//e.preventDefault();
		var	guestOnlineId='${sessionScope.guestOnlineId}';
		if(guestOnlineId!=null && guestOnlineId!=''){
			$.ajax({
		        url: '${url}/chat_support_vccb/chat_client?action=refesh',
		        cache: false,
		        data :$('#form_chat_support').serialize(),	
		        async: false,       
		        type: "POST",
		        success: function(data) { 
		        	 if($("#name_support_server").text()==""){
			         	 $("#img_admin_support1").html($(data).find("#img_admin_support1").html());
			        	 $("#img_admin_support2").html($(data).find("#img_admin_support2").html());		
		        	 }        	 
		        	 
		        	 $("#chat_online_ct_message").html($(data).find("#chat_online_message"));		        	 
		        	 var objDiv = document.getElementById("chat_online_message");
		        	 objDiv.scrollTop = objDiv.scrollHeight;		
		        	 var client_guestOnlineId= $(data).find("#client_guestOnlineId").val();     
		        	 if(client_guestOnlineId==null || client_guestOnlineId==""){
		        		 removeDivClient();
			         }else{			        	
				    	setTimeout(function(){ reloadlist(); }, 3000);       
				    	
				     } 
		        }
				
		    });
		}
	}
	</script>
	</div>
</div>
