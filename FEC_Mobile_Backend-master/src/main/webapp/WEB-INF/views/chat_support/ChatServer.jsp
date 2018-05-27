<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<form:form method="POST" modelAttribute="bean" id="form_chat_support" cssClass="form-horizontal">
<form:hidden id="listRemoveServer" path="listRemove"/>
<form:hidden id="listReplaceServer" path="listReplace"/>
<form:hidden id="listAddServer" path="listAdd"/>
<c:set var="css_chat" value="10"></c:set>
<div id="popup_container_all_chat_all">
<c:forEach var="item" items="${bean.guestOnlines}" varStatus="i">
<div guestOnlineId="${item.guestOnlineId}" id="popup_container_chat_${item.guestOnlineId}" style="height: 380px;width: 360px;position: fixed;bottom:0px;background-color: #004b8f;right: ${css_chat}px;">
	  <form:hidden id="guestOnlineId_${item.guestOnlineId}" cssClass="inputguestOnline" path="guestOnlines[${i.index}].guestOnlineId"/>
	  <c:set var="css_chat" value="${css_chat+370}"></c:set>
	  <h3 style="margin-left:10px;color: white;cursor: pointer;"> </h3>
	  
	  		<div class="row-fluid">
	  		<div class="span11" style="overflow: hidden;">
	  			<h4><div style="margin-left:10px;color: white;cursor: pointer;">${item.fullName }-${item.email }</div></h4>
	  		</div>
	  		<div class="span1">
	  			<h4><span style="float:left;color: white;cursor: pointer;" onclick="removeDivClientDelete('${item.guestOnlineId}');">X </span> </h4>
	  		</div>
	  		</div>
	 
	  <div guestOnlineId="${item.guestOnlineId}" id="popup_content_chat_${item.guestOnlineId}" style="margin:25px 15px 15px 10px;height: 320px;width: 340px;background-color: white;">
		<ext:messages bean="${bean}"></ext:messages>
			<div class="input-area">
				<div id="chat_online_ct_message_${item.guestOnlineId}">
				<div class="chat_online_message" id="chat_online_message_${item.guestOnlineId}" style="margin-left:10px;width: 320px;height: 210px;overflow-y:auto;">
					${item.messageContent}
				</div>
				</div>
				<div class="row-fluid"></div>
				<br>
				<div style="margin-left:10px;width: 320px;height: 210px;overflow-y:auto;">
				<div class="text-center margin_bottom_require">
				<div class="row-fluid">
					<div class="span12">
						<spring:message var="place_holder_input" code="chatsupport.input.placeholder"></spring:message>
						<form:textarea placeholder="${place_holder_input}" maxlength="500"  id="messageContent_${item.guestOnlineId}" path="guestOnlines[${i.index}].messageContentShow" rows="3" onkeypress="keyPressServerChat(event,'${item.guestOnlineId}')" cssClass="span12 inputguestOnline2"/> 
					</div>
				</div>
				</div>
				</div>			
			</div>	
	</div>
</div>
</c:forEach>
</div>
<script type="text/javascript">	
	$(document).ready(function() {	
		
		setTimeout(function(){ reloadlistServer(); }, 3000);        
		
	  	
	});
	function removeDivClientDelete(index){
		$.ajax({
	        url: '${url}/chat_support_vccb/chat_server?action=delete&guestOnlineId='+index,
	        cache: false,
	        data :$('#form_chat_support').serialize(),	
	        async: false,       
	        type: "POST",
	        success: function(data) { 
				$("#popup_container_chat_"+index).empty();
				$("#popup_container_chat_"+index).html("");
				$("#popup_container_chat_"+index).remove();  
				var tempcss=10;
				$( "div[id^='popup_container_chat_']" ).each(function(index) {
					$(this).css("right", tempcss);
					var guestOnlineId=$(this).attr("guestOnlineId");
					$("#guestOnlineId_"+guestOnlineId).attr("name", "guestOnlines["+index+"].guestOnlineId" );
					$("#messageContent_"+guestOnlineId).attr("name", "guestOnlines["+index+"].messageContentShow" );
					tempcss=tempcss+370;
			    });
	        }
			
	    });
	}
	
	function reloadlistServer(){
		var	acountOnlineId='${sessionScope.accountOnlineId}';
		if(acountOnlineId!=null && acountOnlineId!=''){
			$.ajax({
		        url: '${url}/chat_support_vccb/chat_server?action=refesh',
		        cache: false,
		        data :$('#form_chat_support').serialize(),	
		        async: true,       
		        type: "POST",
		        success: function(data) { 
			        var listRemoveServer=$(data).find("#listRemoveServer").val();   
		        	var listAddServer=$(data).find("#listAddServer").val();	
		        	$(listRemoveServer).remove();
		        	var tempcss=10;
		        	$( "div[id^='popup_container_chat_']" ).each(function(index) {
						$(this).css("right", tempcss);
						var guestOnlineId=$(this).attr("guestOnlineId");
						$("#chat_online_message_"+guestOnlineId).html($(data).find("#chat_online_message_"+guestOnlineId).html());
						$("#guestOnlineId_"+guestOnlineId).attr("name", "guestOnlines["+index+"].guestOnlineId" );
						$("#messageContent_"+guestOnlineId).attr("name", "guestOnlines["+index+"].messageContentShow" );
						tempcss=tempcss+370;
				    });
		        	$(data).find(listAddServer).each(function(index) {
		        		$("#popup_container_all_chat_all").append($(this));
		        	});   

		        
		    		setTimeout(function(){ reloadlistServer(); }, 3000);        
		    		
			           
		        	  	       	 
		        }
				
		    });
		}
	} 
	function keyPressServerChat(event,guestOnlineId) {
	    if (event.keyCode == 13 && $("#messageContent_"+guestOnlineId).val()!="" && $("#messageContent_"+guestOnlineId).val()!=null) {
	    	$.ajax({
	            url: '${url}/chat_support_vccb/chat_server_client?guestOnlineId='+guestOnlineId,
	            cache: false,
	            data :$('#form_chat_support').serialize(),	
	            async: false,       
	            type: "POST",
	            dataType: "json",
	            success: function(data) {              
	            	 $("#chat_online_message_"+guestOnlineId).html(data.messageContent);
	            	 var objDiv = document.getElementById("chat_online_message_"+guestOnlineId);
	            	 objDiv.scrollTop = objDiv.scrollHeight;
	            	 try{
	            		 event.preventDefault();
	            	 }catch (e) {
					 }
	            	 $("#messageContent_"+guestOnlineId).val(""); 
	            	 $("#messageContent_"+guestOnlineId).focus();      
	            }
	        });
	    }
	
	}
	</script> 
</form:form>
