<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
	
<form:form method="POST" id="approval_info_form"
	cssClass="form-horizontal" modelAttribute="bean">
	<table class="table table-bordered table-hover out-tbl">
		<thead>
			<tr>
				<th><spring:message code="account.process.name"></spring:message></th>
				<th style="max-width: 60px; width: 60px;"><spring:message
						code="process.step"></spring:message></th>
			<%-- 	<th><spring:message code="account.amount.from"></spring:message></th>
				<th><spring:message code="account.amount.to"></spring:message></th> --%>
				<th class="table-actions"><spring:message code="actions" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="_process" items="${bean.processList }" varStatus="i">
				<tr id="process_${i.index}" class="info">
					<td colspan="2"><form:hidden
							path="processList['${i.index}'].processId"
							id="processid_${i.index}" /> 
							
							<form:hidden path="processList['${i.index}'].name"/>
							<form:hidden path="processList['${i.index}'].code"/>
							<form:hidden path="processList['${i.index}'].effectiveDate"/>
							<form:hidden path="processList['${i.index}'].stepNumber"/>
							
							
							${_process.name }</td>
					<td class="text_center"><form:select path=""
							style="width: 60px !important;text-align: center;" class="cbbBox">
							<form:option value="">Add</form:option>
							<c:forEach var="c"
								items="${bean.processList[i.index].stepList }">
								<form:option value="${c}">${c}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<c:forEach var="_info"
					items="${bean.processList[i.index].approvalInfoList }"
					varStatus="j">
					<tr id="info_${i.index}_${j.index}">
						<td>
							<form:hidden path="processList['${i.index}'].approvalInfoList['${j.index}'].id"/>
							<form:hidden path="processList['${i.index}'].approvalInfoList['${j.index}'].stepName"/>
							<form:hidden path="processList['${i.index}'].approvalInfoList['${j.index}'].step"/>
							
							${_info.stepName}
							
						</td>
						<td class="text_center">
							${_info.step}
						</td>
						<%-- <td><ext-form:input-number
								path="processList['${i.index}'].approvalInfoList['${j.index}'].fromAmount"
								id="from_${i.index }_${j.index }" isGrid="true"
								value="${_info.fromAmount}"></ext-form:input-number></td>
						<td><ext-form:input-number
								path="processList['${i.index}'].approvalInfoList['${j.index}'].toAmount"
								id="to_${i.index }_${j.index }" isGrid="true"
								value="${_info.toAmount}"></ext-form:input-number></td> --%>
						<td class="text_center">
							<spring:message var="msg_delete" code="button.delete" />
							<i class="bms-icon-delete" title="${msg_delete }" onclick="deleteBoxApprove('${i.index }','${j.index }')"></i>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</form:form>
<script type="text/javascript">
$(document).ready(function() {
	//add box approve
	$(".cbbBox").each(function(index ) {
		var processid = $('#processid_'+index).val();
		$(this).change(function(){
			$.ajax({
				url : '${url}/system/account/add_box_approve_ajax?step='+$(this).val()+'&processId='+processid,
				data : $('#edit_form,#approval_info_form').serialize(),
				dataType : 'text',
				type : 'POST',
				cache : false,
				success : function(data) {
					$("#approvalInfoList").html(data);
				}
			});
		});
	});
});
//delete box approve
function deleteBoxApprove(i,j){
	$.ajax({
		url : '${url}/system/account/delete_box_approve_ajax?processindex='+i+'&approvalinfoindex='+j,
		data : $('#edit_form,#approval_info_form').serialize(),
		dataType : 'text',
		type : 'POST',
		cache : false,
		success : function(data) {
			$("#approvalInfoList").html(data);
		}
	});
}
</script>




