<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<style type="text/css">
.bootstrap-datetimepicker-widget {
	z-index: 9999;
}
</style>

<form:form method="POST" id="frmPush" cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
		<h3 id="myModalLabel">Push message configuration</h3>
	</div>
	
	<form:hidden path="entity.cmsId" id="pushCmsId"/>
	
	<div class="modal-body">
		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:input-text path="pushTitle" id="pushTitlePopup" label="Subject" required="true" cssInput="span12" maxlength="500" readonly="true"/>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:display label="Message type" cssOutput="span12" value="CMS" />
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<ext-form:input-textarea path="pushDescription" id="pushDescriptionPopup" label="Content" rows="2" cssInput="span12" required="true" readonly="true" />
			</div>
		</div>

		<!-- 		<div class="row-fluid"> -->
		<!-- 			<div class="span12"> -->
		<!-- 				<div class="control-group"> -->
		<!-- 					<div class="control-label"> -->
		<!-- 						<label> -->
		<!-- 							Option send <span class="required">*</span> -->
		<!-- 						</label> -->
		<!-- 					</div> -->
		<!-- 					<div class="controls"> -->
		<!-- 						<div class="row-fluid" style="margin-top: 5px;"> -->
		<!-- 							<label> -->
		<%-- 								<form:radiobutton path="pushOption" title="Send now" id="pushOptionSendNow" value="1" /> --%>
		<!-- 								Send now -->
		<!-- 							</label> -->
		<!-- 							<br> -->
		<!-- 						</div> -->
		<!-- 						<div class="row-fluid" style="z-index: 9999;"> -->
		<!-- 							<label> -->
		<%-- 								<form:radiobutton path="pushOption" title="Set calendar" id="pushOptionSetCalendar" value="2" /> --%>
		<!-- 								Set Calendar -->
		<%-- 								<ext-form:input-date-time path="pushDate" id="pushDate" isGrid="true" /> --%>
		<!-- 							</label> -->

		<!-- 						</div> -->
		<%-- 						<form:errors path="pushOption" cssClass="error" /> --%>
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 		</div> -->
		<!-- 		<div class="row-fluid"> -->
		<!-- 			<div class="span12"> -->
		<!-- 				<div class="control-group"> -->
		<!-- 					<div class="control-label"> -->
		<!-- 						<label> -->
		<!-- 							Send to <span class="required">*</span> -->
		<!-- 						</label> -->
		<!-- 					</div> -->
		<!-- 					<div class="controls"> -->
		<!-- 						<div class="row-fluid" style="margin-top: 5px;"> -->
		<!-- 							<label> -->
		<%-- 								<form:radiobutton path="pushRecipientType" title="All users by device ID" id="pushRecipientTypeALL" value="1" /> --%>
		<!-- 								All users by device ID -->
		<!-- 							</label> -->
		<!-- 							<br> -->
		<!-- 						</div> -->
		<!-- 						<div class="row-fluid"> -->
		<!-- 							<label> -->
		<%-- 								<form:radiobutton path="pushRecipientType" title="List customer below" id="pushRecipientTypeUploadList" value="2" /> --%>
		<!-- 								List customer below -->
		<!-- 							</label> -->
		<!-- 							<div id="divFile" class="hidden"> -->
		<!-- 								<input id="fileName" name="fileName" class="span10" placeholder="File name" disabled="disabled" type="text" value=""> -->
		<!-- 								<button type="button" id="btnChooseFile" class="btn_search_general" name="import" style="margin-left: 6px">Import</button> -->
		<!-- 								<input type="file" name="fileData" id="fileData" class="hidden"> -->
		<!-- 							</div> -->
		<!-- 						</div> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<%-- 				<form:errors path="pushRecipientType" cssClass="error" /> --%>
		<!-- 			</div> -->
		<!-- 		</div> -->

	</div>
	<div class="modal-footer text_center" style="background: White">
		<spring:message var="msg_btn_close" code="banner.btn.close" />
		<button type="button" class="btn_search_general" data-dismiss="modal" aria-hidden="true">${msg_btn_close }</button>
		<button type="button" class="btn_search_general" id="btnPushBroadcast" onclick="pushBroadcast()">Push</button>
	</div>
</form:form>

<script type="text/javascript">
  $(document).ready(function() {

    $("#btnChooseFile").click(function() {
      $("#fileData").click();
    });
    $("#fileData").change(function() {
      if ($("#fileData").val().length > 0) {
        $("#fileName").val($("#fileData").val().split("fakepath\\")[1]);
      }
    });

    $("input[name=pushOption]").click(function() {
      if ($("#pushOptionSetCalendar").is(":checked")) {
        $("#pushDate").show();
      } else {
        $("#pushDate").hide();
      }
    });
    $("#pushDate").hide();
    <c:if test="${bean.pushOption == 2 }">
    $("#pushDate").show();
    </c:if>

    $("input[name=pushRecipientType]").click(function() {
      if ($("#pushRecipientTypeUploadList").is(":checked")) {
        $("#divFile").removeClass("hidden");
      } else {
        $("#divFile").addClass("hidden");
      }
    });
    $("#divFile").addClass("hidden");
    <c:if test="${bean.pushRecipientType == 2 }">
    $("#divFile").removeClass("hidden");
    </c:if>

  });
  
  
  
  
</script>



