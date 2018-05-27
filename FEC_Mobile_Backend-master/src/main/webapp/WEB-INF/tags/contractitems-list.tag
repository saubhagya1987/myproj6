<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ext"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="bean" required="true" rtexprvalue="true" type="vn.com.unit.fe_credit.bean.ContractItemsBean" description="ContractItemsBean support binding"%>
<%@ attribute name="parentPage" required="true" rtexprvalue="true" type="java.lang.String" description="redirect to page"%>
<%@ attribute name="referenceId" required="true" rtexprvalue="true" type="java.lang.Integer" description="referenceId"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-modal.css"/>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-modal-bs3patch.css"/>
	<link rel="stylesheet" type="text/css" href="${url}/static/css/tagmanager.css"/>
    <script type="text/javascript" src="${url}/static/js/tagmanager.js"></script>
<div class="row-fluid">
	<div class="span12" style="padding: 10px 0 0 0">
		<table class="table table-bordered table-hover out-tbl">
			<thead>
				<tr>
					<td colspan="8" style="padding: 0px; margin: 0px;">
						<div class="title_table row-fluid">
							<div class="span6 title">
								<h3><span class="icon-list icon-small"></span>
									<spring:message var="list_goods"
											code="transaction.field.goodsdetail"></spring:message>
										<c:out value="${list_goods }"></c:out>
								</h3>
							</div>							
							<div class="span6 add_table">
							
							<c:if test="${var_readonly}">
								<span style="float: right;"> Add <input class="add_icon"
									type="button" value="+" id="btnAddFile1"  />
								</span>
								</c:if>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<spring:message var="msg_name" code="transaction.field.assetname"></spring:message>
						<spring:message var="msg_UOM" code="transaction.field.uom"></spring:message>
						<spring:message var="msg_quantity"
							code="transaction.field.quantity"></spring:message>
						<spring:message var="msg_unitprice"
							code="transaction.field.unitprice"></spring:message>
						<spring:message var="msg_amount" code="transaction.field.amount"></spring:message>
						
						<th>${msg_name}</th>
						<th>${msg_UOM}</th>
						<th>${msg_quantity}</th>
						<th>${msg_unitprice}</th>
						<th>${msg_amount}</th>
						<th class="table-actions"></th>
				</tr>
			</thead>
			<tbody id="contract-items">
				
			</tbody>
			<tbody id="contract-items">
				
			</tbody>
		</table>
	</div>
</div>
<div id="myModal1" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-width="760">
		</div>
		<div id="viewModal1" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="viewModalLabel" aria-hidden="true" data-width="760">
		</div>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>
<script language="javascript">


$('#btnAddFile1').click(function(){
	openPopupItems(null);
})
function openPopupItems(id){
	if(id==null)
	  ajaxUrl= '${url}/transaction/contract/items/ajax?fragments=body&parentPage=${parentPage}&entity.transactions.transactionId=${referenceId}';
	else
	  ajaxUrl= '${url}/transaction/contract/items/ajax?fragments=body&parentPage=${parentPage}&entity.transactions.transactionId=${referenceId}&id='+id;
	
	$.ajax({url:ajaxUrl,success:function(result){
	    $("#myModal1").html(result);
	    $("#myModal1").modal('show');
	  }});
}

function openPopupViewItems(id){
	if(id==null)
	  ajaxUrl= '${url}/transaction/contract/items/ajaxview?fragments=body&parentPage=${parentPage}&entity.transactions.transactionId=${referenceId}';
	else
	  ajaxUrl= '${url}/transaction/contract/items/ajaxview?fragments=body&parentPage=${parentPage}&entity.transactions.transactionId=${referenceId}&id='+id;
	$.ajax({url:ajaxUrl,success:function(result){
	    $("#viewModal1").html(result);
	    $("#viewModal1").modal('show');
	  }});
}
var ajaxUrl= '${url}/transaction/contract/items/ajaxlist?fragments=body&entity.transactions.transactionId=${referenceId}';
$.ajax({url:ajaxUrl,success:function(result){
    $("#contract-items").html(result);
  }});
  
$(document).ready(function() {
	$('#action').val("search");
	$("#chkDeleteAll").click(function() {
		$("input[name=ids]").each(function() {
			this.checked = $("#chkDeleteAll").is(':checked');
		});
	});
	
});
function deleteItems(id){
	confirmMessage('${msg_deleteQuestion}',function(result){
		if(result){
			var ajaxUrl='${url}/transaction/contract/items/ajaxdelete?fragments=body&entity.transactions.transactionId=${referenceId}&id='+id;
			$.ajax({url:ajaxUrl,success:function(result){
			    $("#contract-items").html(result);
			  }});
		}
	});
	
}
/*
$('#btnAddFile1').click(function(){
	var ajaxUrl= '${url}/transaction/contract/items/ajax?fragments=body&parentPage=${parentPage}&entity.transactions.transactionId=${referenceId}';
	$.ajax({url:ajaxUrl,success:function(result){
	    $("#myModal1").html(result);
	  }});
})
var ajaxUrl= '${url}/transaction/contract/items/ajaxlist?entity.transactions.transactionId=${referenceId}';
$.ajax({url:ajaxUrl,success:function(result){
    $("#contract-items").html(result);
  }});
  
$(document).ready(function() {
	$('#action').val("search");
	$("#chkDeleteAll").click(function() {
		$("input[name=ids]").each(function() {
			this.checked = $("#chkDeleteAll").is(':checked');
		});
	});
	jQuery(".tm-input").tagsManager();
});
	*/
</script>
<script type="text/javascript" src="${url}/static/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${url}/static/js/bootstrap-modal.js"></script>