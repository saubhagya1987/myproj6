<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set> 
<spring:message var="contactbank_errors" code="creditcard.contactbank.request.errors" />
<spring:message var="contactbank_email" code="creditcard.contactbank.request.email" />
<spring:message var="signup_success" code="creditcard.contactbank.signup.success" />
<spring:message var="feedback_success" code="feedback.success" />

<div class="fixed_left_Register"  onclick="creditcardpopup()">
	<div class="text_register">
	<a ><label id="label_${sessionScope.localeSelect}" style="font-weight: 600;"><spring:message code="msg.home.registry"/></label></a>
	</div>
	<div class="bg_register">
	<a><span><img src="${url}/static/images/img-registerNow.png" /></span></a>
	</div>
	
</div>
<div class="fixed_left_Feedback" onclick="feedbackPopup()">
	<div class="text_Feedback">
		<a><label id="label_${sessionScope.localeSelect}" style="font-weight: 600;"><spring:message code="msg.home.feedback"/></label></a>
	</div>
	
	<div class="bg_Feedback">
		
	<a><span ><img src="${url}/static/images/img-feedback.png" /></span></a>
	</div>
</div>

<div class="fixed_right">	
    <a><label> 
   	 <img src="${url}/static/images/facebook-icon.png" style="width: 32px; height: 30px" onclick="javascript:fbshareCurrentPage()" />    	
    </label></a>	
</div>
<div class="fixed_right_chat">	
	<c:if test="${empty sessionScope.accountOnlineId }">
	<a><label><img src="${url}/static/images/icon-chatline.png" style="width: 32px;height: 30px" onclick="javascript:openLoginPopup()"/></label></a>
	</c:if>
</div>
	 	 
<div class="previewPopup margin_bottom_require">
	<div id="creditcardPopup" class="modal hide fade assetPopup"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-width="600" style="width: 600px; margin-left: -80px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="myModalLabel"><spring:message code="creditcard.registration.form" /></h3>
		</div>
		<div class="modal-body form-horizontal" id="creditcardShow">
			<form:form method="POST" modelAttribute="bean" cssClass="form-horizontal" id="form_creditcard_contactbank">
				<div class="row-fluid">
					<div class="span5">
						<table style="height: 30px"><tr><td><input type="radio" value="1" name="registration" checked="checked"></td><td><span class="clh5"><spring:message code="creditcard.registration.direct" /></span></td></tr></table>
				    </div>
				    <div class="span7">
				    	<select id="lst_card" class="span12"  name="entity.cardId">
				    		<option value=""></option>
				    	</select>
				    	<div id="errors_cardId" class="error"></div>
				    </div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<table style="height: 30px"><tr><td><input type="radio" value="2" name="registration"></td><td><span class="clh5"><spring:message code="creditcard.registration.bank" /></span></span></td></tr></table>
				    </div>
				</div>
				<div class="row-fluid div90">
					<span id="errors_register" class="error"></span>
				</div>
				<div class="row-fluid div90">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="creditcard.card" />
							</label>
							<div class="controls">
								<select id="lst_card_ContactBank" class="span12" disabled="disabled" name="cCCB_CardId">
						    		<option value=""></option>
						    	</select>
								<!-- <input type="text" class="span12 register" name="entity.cardId" disabled="disabled" id="cardId_ContactBank"> -->
							</div>
						</div>
				    </div>
				</div>
				<div class="row-fluid div90">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="creditcard.fullname" />
							</label>
							<div class="controls">
								<input type="text" class="span12 register" name="entity.fullName" disabled="disabled" id="fullName_ContactBank">
							</div>
						</div>
				    </div>
				</div>
				<div class="row-fluid div90">
					<div class="span12">
						<div class="control-group">
						<label class="control-label">
							<spring:message code="account.field.mobile" />
						</label>
						<div class="controls">
							<input type="text" class="span12 register" name="entity.phone" disabled="disabled" id="phone_ContactBank">
						</div>
						</div>
				    </div>
				</div>
				<div class="row-fluid div90">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="creditcard.email" />
							</label>
							<div class="controls">
								<input type="text" class="span12 register"  name="entity.email" disabled="disabled" id="email_ContactBank">
							</div>
					    </div>
				    </div>
				</div>
				<div class="row-fluid div90">
					<div class="span12">
						<div class="control-group">
						<label class="control-label">
							<spring:message code="creditcard.current.location" />
						</label>
						<div class="controls">
							<input type="text" class="span12 register"  name="entity.address" disabled="disabled" id="address_ContactBank">
						</div>
						</div>
				    </div>
				</div>
				<div class="text-right margin_bottom_require">
					<spring:message code="creditcard.next" var="_next"></spring:message>
					<a id="aNext"><input id="nextContactBank" onclick="nextOnclick()" type="button" value="${_next}" class="btn_blue" /></a>
				</div>
			</form:form>
		</div>
	</div>
	<!-- feedback pupup -->
	<div id="feedbackPopup" class="modal hide fade feedbackPopup"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-width="600" style="width: 600px;margin-left: -80px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="myModalLabel"><spring:message code="feedback.title" /></h3>
		</div>
		<div class="modal-body form-horizontal" id="feedbackShow">
			<form:form method="POST" modelAttribute="bean_fb" cssClass="form-horizontal" id="form_feedback">	
				<div class="row-fluid">
					<span id="errors_Feedback" class="error"></span>
				</div>			
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="feedback.fullname" />
							</label>
							<div class="controls">
								<input type="text" class="span10" name="entity.fullName" id="fullName_Feedback">
							</div>
						</div>
				    </div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group">
						<label class="control-label">
							<spring:message code="feedback.phone" />
						</label>
						<div class="controls">
							<input type="text" class="span10" name="entity.phone" id="phone_Feedback">
						</div>
						</div>
				    </div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="feedback.email" />
							</label>
							<div class="controls">
								<input type="text" class="span10"  name="entity.email" id="email_Feedback">
							</div>
					    </div>
				    </div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="feedback.comment" />
							</label>
							<div class="controls">								
								​<textarea name="entity.comment" id="comment_Feedback" rows="4" class="span10" ></textarea>
							</div>
					    </div>
				    </div>
				</div>
				<div class="text-right margin_bottom_require">
					<spring:message code="feedback.send" var="_feed"></spring:message>
					<!-- <a id="sendFeedback"></a> -->
					<input onclick="return sendFeedbackOnclick();" value="${_feed}"  type="button" class="btn_blue" />
				</div>
			</form:form>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {	
	$.ajax({
        url: '${url}/json_lst_card',
        cache: false,
        type: "POST",
        dataType : 'json',
        success: function(data) { 
        	$.each(data, function(i, object) {
        		var option = jQuery('<option/>', {
    				value: object.cardId,
    				text: object.nameShow,
    			});		
    			$("#lst_card").append(option);
        	});
        }
    });
	$("input[name='registration']").click(function(){
		if ($(this).val() == 1) {
			$(".register").attr("disabled","disabled");
		}else{
			$(".register").removeAttr("disabled");
		}
	});
	$('input:radio').change(function() {
		var selectedVal = "";
		var selected = $("input[type='radio'][name='registration']:checked");
		if (selected.length > 0) {
		    selectedVal = selected.val();
		}
		if(selectedVal==1){
			$(".div90").css("display","none");
			$("#lst_card").css("display","block");
			$("#errors_register").text("");
		}
		if(selectedVal==2){
			$(".div90").css("display","block");
			$("#lst_card").css("display","none");
			
			$.ajax({
		        url: '${url}/json_lst_card_active',
		        cache: false,
		        type: "POST",
		        dataType : 'json',
		        success: function(data) { 
		        	$('#lst_card_ContactBank option').remove();
		        	$.each(data, function(i, object) {
		        		var option = jQuery('<option/>', {
		    				value: object.cardId,
		    				text: object.nameShow,
		    			});		
		    			$("#lst_card_ContactBank").append(option);
		        	});
		        }
		    });
			$("#lst_card_ContactBank").removeAttr("disabled");
			$("#errors_cardId").text("");
		}
	});
	
	var selectedVal = "";
	var selected = $("input[type='radio'][name='registration']:checked");
	if (selected.length > 0) {
	    selectedVal = selected.val();
	}
	if(selectedVal==1){//${url}/creditcard/register
		$(".div90").css("display","none");
	}
	if(selectedVal==2){
		$(".div90").css("display","block");
	}
});

function creditcardpopup(cardTypeId){
	$("#lst_card").val(cardTypeId); 
	$("#creditcardPopup").modal('show');
}
function feedbackPopup(){
	$("#feedbackPopup").modal('show');
}

function nextOnclick(){
	if($("input[name='registration']:checked").val() == 1){
		if ($("#lst_card").val() != "") {
			document.location.href = "${url}/creditcard/register?cardId="+$("#lst_card").val();
		}else{
			$("#errors_cardId").text('${contactbank_errors}');
		}
		
		/* document.location.href = "${url}/creditcard/register?id="+$("#lst_card").val(); */
	}else {
		var check = true;
		var re = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
		
		if ($("#fullName_ContactBank").val().trim()=="" || $("#phone_ContactBank").val().trim()==""
			|| $("#email_ContactBank").val().trim()=="" || $("#address_ContactBank").val().trim()==""
			|| $("#lst_card_ContactBank").val().trim()=="") {
			$("#errors_register").text('${contactbank_errors}');
			check = false;
		}else if(!re.test($("#email_ContactBank").val().trim())){
			$("#errors_register").text('${contactbank_email}');
			check = false;
		}
		if (check) {
			$.ajax({
		         url: '${url}/json_contact_bank',
		         cache: false,
		         type: "POST",
		         data:$('#form_creditcard_contactbank').serialize(),
		         success: function(data) { 
		        	if (data) {
						$("#creditcardShow").html("${signup_success}");
					}
		         }
		     });
		}else{
			
		}
		 
	}
}
function sendFeedbackOnclick(){
		
		var re = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
		var check = true;
		if ($("#fullName_Feedback").val().trim()=="" || $("#phone_Feedback").val().trim()==""
			|| $("#email_Feedback").val().trim()=="" || $("#comment_Feedback").val().trim()=="") {
			$("#errors_Feedback").text('${contactbank_errors}');
			check = false;
		}else if(!re.test($("#email_Feedback").val().trim())){
			$("#errors_Feedback").text('${contactbank_email}');
			check = false;
		}
		if (check) {
			$.ajax({
		         url: '${url}/json_feedback',
		         cache: false,
		         type: "POST",
		         data:$('#form_feedback').serialize(),
		         success: function(data) { 
		        	if (data) {
						$("#feedbackShow").html("${feedback_success}");
					}
		         }
		     });
		}else{
			
		}
		return check;
}
</script>
