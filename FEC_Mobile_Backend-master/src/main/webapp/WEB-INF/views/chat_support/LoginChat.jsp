<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<div id="popup_container_login" class="popup_login_livechat">
	<div class="row-fluid">				
		<div class="span10">
			<h4 style="color: white; cursor: pointer; text-align: left; padding: 7px 0px 0px 15px;">
				<span style="font-weight: bold;"><spring:message code="chatsupport.title" /></span>				
			</h4>
		</div>
		<div class="span2">
			<h4 style="color: white; cursor: pointer; text-align: right; padding: 6px 11px 0px 0px;">				
				<span onclick="removeDivClientLogin();">x</span>	
			</h4>
		</div>
	</div>
	
	<div class="popup_login">
	<form:form method="POST" modelAttribute="bean" id="form_chat_support_login"
		cssClass="form-horizontal">
		
		<div class="input-area">
			<div class="row-fluid">	
				<div class="span11 offset1 text_login" >
				<p><spring:message code="chatsupport.title.1"/></p>
				<p><spring:message code="chatsupport.title.2"/></p>
				<p><spring:message code="chatsupport.title.3"/>,</p>
				<p><spring:message code="chatsupport.title.4"/>.</p>
				</div>
				
			</div>
			<div class="row-fluid">	
				<div class="span11 offset1">	
				<div id="errors_Chat_login" style="color: red;"></div>
				</div>		
			</div>
			<div class="row-fluid" style="margin-bottom: 3px;">	
				<div class="span3 offset1">								
					<label>
						<spring:message  code="account.field.fullName" /><span class="required">*</span>
					</label>					
				</div>
				<div class="span8">
					<form:input path="guestOnline.fullName" id="fullName_LoginChat" maxlength="30"  cssInput="span10"/>
				</div>			
			</div>
			
			<div class="row-fluid" style="margin-bottom: 3px;">	
				<div class="span3 offset1">								
					<label>
						<spring:message  code="account.field.email"   /><span class="required">*</span>
					</label>					
				</div>
				<div class="span8">
					<form:input path="guestOnline.email" id="email_LoginChat" maxlength="30" cssInput="span10"/>
				</div>			
			</div>
			
			<div class="row-fluid" style="margin-bottom: 3px;">	
				<div class="span3 offset1">								
					<label>
						<spring:message  code="account.field.mobile" /><span class="required">*</span>
					</label>					
				</div>
				<div class="span8">
					<form:input path="guestOnline.phone" id="phone_LoginChat" cssInput="span10"/>
				</div>			
			</div>
			
			<div class="row-fluid" style="margin-bottom: 3px;">	
				<div class="span4 offset8">
					<spring:message var="chatsupport_chat" code="chatsupport.begin.chat"></spring:message>
					<input onclick="loginClientChat();" value=" ${chatsupport_chat}" class="btn-info2_login"/> 
				</div>			
			</div>

		</div>
		
	</form:form>
	
	<script type="text/javascript">
	function loginClientChat(){
		var check=true;
		$("#errors_Chat_login").text("");
		var regex = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
		if ($("#fullName_LoginChat").val().trim()==""){
			check = false;
			$("#errors_Chat_login").text('<spring:message  code="chatsupport.not.emptu.fullname" />');
		} else if( $("#email_LoginChat").val().trim()==""){
			check = false;
			$("#errors_Chat_login").text('<spring:message  code="chatsupport.not.emptu.email" />');
		}else if($("#phone_LoginChat").val().trim()=="" ){
			check = false;
			$("#errors_Chat_login").text('<spring:message  code="chatsupport.not.emptu.sdt" />');
		}else if(!regex.test($("#email_LoginChat").val().trim())){
			$("#errors_Chat_login").text('<spring:message  code="chatsupport.not.wrong.email" />');
			check = false;
		}
		if(check==true){
			$.ajax({
		        url: '${url}/chat_support_vccb/login_client',
		        cache: false,
		        type: "POST",
		        data :$('#form_chat_support_login').serialize(),	
		        async: false,       
		        success: function(data) { 	
		        	 $('#chat_suppport').html(data);
		        }
		    });
		}
	}
	function removeDivClientLogin(){
		var element = document.getElementById("popup_container_login");
		element.outerHTML = "";
		delete element;
	}
	</script>
	</div>
</div>





