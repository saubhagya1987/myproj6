<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ext"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="bean" required="false" rtexprvalue="true" type="vn.com.unit.fe_credit.bean.DocumentInfoBean" description="DocumentInfoBean support binding"%>
<%@ attribute name="documentSource" required="true" rtexprvalue="true" type="java.lang.Integer" description="documentSource"%>
<%@ attribute name="referenceNo" required="false" rtexprvalue="true" type="java.lang.String" description="referenceNo"%>
<%@ attribute name="referenceId" required="true" rtexprvalue="true" type="java.lang.Integer" description="referenceId"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="visible"%>
<%@ attribute name="levelPR" required="false" rtexprvalue="true" type="java.lang.String" description="levelPR 1(Requester) 2(approve) 3(buyer) 4(no all)"%>
<%@ attribute name="checkPR" required="false" rtexprvalue="true" type="java.lang.String" description="checkPR 1"%>
<c:if test="${empty visible }"><c:set var="visible" value="false"></c:set> </c:if>
<c:if test="${empty levelPR }"><c:set var="levelPR" value="4"></c:set> </c:if>
<c:if test="${empty checkPR }"><c:set var="checkPR" value="0"></c:set> </c:if>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-modal.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-modal-bs3patch.css"/>


<div class="row-fluid">
	<div class="span12">
		<table class="table table-bordered table-hover out-tbl">

			<thead>
				<tr>
					<td colspan="8" style="padding: 0px; margin: 0px;">
						<div class="title_table row-fluid">
							<div class="span6 title">
								<h3></span>
									<spring:message var="document_list" code="document.list"></spring:message>
									<c:out value="${document_list }"></c:out>
								</h3>
							</div>
							<div class="span6 add_table">
								<spring:message var="payment_attach" code="payment.attach"></spring:message>
								<c:if test="${visible}">
								<span style="float: right;padding-right: 10px"> <input
									type="button" class="btn btn-info" value="${payment_attach }" class="draft" id="btnAddFile2"/>
								</span>
								</c:if>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<spring:message var="msg_doctitle" code="document.field.title"></spring:message>
					<spring:message var="msg_modifieddate"
						code="document.field.modifieddate"></spring:message>
					<spring:message var="msg_size" code="document.field.size"></spring:message>
					<spring:message var="msg_type" code="document.field.type"></spring:message>
					<spring:message var="msg_link" code="document.field.link"></spring:message>
					<spring:message var="msg_createdby" code="receipt.createdby"></spring:message>
					

					<th>${msg_doctitle}</th>
					<th>${msg_createdby}</th>
					<th>${msg_modifieddate}</th>
					<th>${msg_size}</th>
					<th>${msg_type}</th>
					<th>${msg_link}</th>
					<th class="table-actions text_center"><spring:message code="actions"></spring:message></th>
				</tr>
			</thead>
			<tbody id="uploaded-files2">
				
			</tbody>
		</table>
	</div>
</div>
<div id="myModal2" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-width="760">
		</div>
<div id="viewModal2" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="viewModalLabel" aria-hidden="true" data-width="760">
		</div>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script language="javascript">
var count=0;
$('#btnAddFile2').click(function(){
	openPopup(null);
})

function openPopupView(id){
	if(id==null)
	  ajaxUrl= '${url}/transaction/documentinfo/ajaxview?fragments=body&u_id='+$.now()+'&entity.documentSource=${documentSource}&entity.referenceId=${referenceId}&visible=${visible}';
	else
	  ajaxUrl= '${url}/transaction/documentinfo/ajaxview?fragments=body&u_id='+$.now()+'&entity.documentSource=${documentSource}&entity.referenceId=${referenceId}&visible=${visible}&id='+id;
	$.ajax({url:ajaxUrl,success:function(result){
	    $("#viewModal2").html(result);
	    $("#viewModal2").modal('show');
	  }});
}
var listDocumentTempId=$("#listDocumentTempId").val();
var ajaxUrl= '${url}/transaction/documentinfo/ajaxlist?listDocumentArrayId='+listDocumentTempId+'&fragments=body&u_id='+$.now()+'&entity.documentSource=${documentSource}&entity.referenceId=${referenceId}&visible=${visible}&checkPR=${checkPR}&levelPR=${levelPR}';
$.ajax({url:ajaxUrl,success:function(result){
	$("#listDocumentTempId").val($(result).find("#listDocumentArrayId2").val());
    $("#uploaded-files2").html(result);
 }});

function updateListDocumentArrayId(val){
    var listDocumentTempId = $("#listDocumentTempId").val()+"";
	if(listDocumentTempId!=""){
		$("#listDocumentTempId").val(listDocumentTempId+","+val);
	}else{
		$("#listDocumentTempId").val(val);
	}
}

function returnListDocumentArrayId(){
	return $("#listDocumentTempId").val();
}

</script>
