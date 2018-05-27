<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<spring:message var="msg_deleteQuestion"
   code="msg.alert.delete.question"></spring:message>
<spring:message var="msg_warning" code="export.status.warning"></spring:message>
<script type="text/javascript">
   $(document).ready(function() {
   	$('#action').val("export");
   	$("#reset").click(function() {
   		reset();
   	})
   });
   function onExport() {
	   var valueSelect = $('#typeSelect option').filter(':selected').val();
		document.location.href = "${url}/system/exportFile/redeemTransactionPoint/export/?entity.tranxType="+valueSelect;
	   
	};
   function downloadFile(fileName) {
		   document.location.href = "${url}/system/exportFile/redeemTransactionPoint/download/?fileName="+fileName;   
	};
   function deleteAction(id,fileName) {
		confirmMessage('${msg_deleteQuestion}', function(result) {
			if (result) {
				document.location.href = "${url}/system/exportFile/redeemTransactionPoint/delete?entity.exportRedeemId="+id+"&&entity.fileName="+fileName;
			}
		});
	}
   function deleteCallBack(result) {
   	if (result) {
   		$('#action').val("delete");
   		$("#search_form").submit();
   	}
   }
   
</script>
<!-- start title -->
<div class="title_top" >
   <div class="container-fluid">
      <div class="row-fluid">
         <div class="span6">
            <spring:message var="menu_admin" code="menu.admin"></spring:message>
            <spring:message var="export_file" code="menu.system.export"></spring:message>
            <spring:message var="redeem_transaction_point" code="menu.system.redeemTransactionPoint"></spring:message>
            <h4 style="padding: 8px 0 0 10px;">
               <a onclick="back()" class="Color_back">
                  <c:out value="${menu_admin }"></c:out>
               </a>
               <span> > </span>
               <a onclick="" class="Color_back">
                  <c:out value="${export_file}"></c:out>
               </a>
               <span> > </span>
               <span class="Colorgray">
                  <c:out value="${redeem_transaction_point }"></c:out>
               </span>
            </h4>
         </div>
      </div>
   </div>
</div>
<!-- and title -->
<div class="container-fluid unit_bg_content">
   <div class="row-fluid">
      <div class="span6 title_h2 title">
      	 <spring:message var="msg_menu" code="menu.system.export"></spring:message>
         <spring:message var="msg_List" code="menu.system.redeemTransactionPoint"></spring:message>
         <h2>${msg_menu} : ${msg_List}</h2>
      </div>
   </div>
   <form:form method="POST" id="search_form_export"
      cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
      <div class="accordion" id="accordion2">
      </div>
      <div class="alert alert-error" id="addAlert" style="display: none">
		<strong></strong>
	  </div>
      <div class="accordion" id="accordion2">
         <ext:messages bean="${bean}"></ext:messages>
         <div class="row-fluid ">
            <div id="collapseOne" class="accordion-body collapse in border-group">
               <div class="accordion-inner">
                  <div class="input-area">
                     <div class="row-fluid">
                        <div class="span16 input-append" style="background: White">
                           <label class="control-label">
                              <spring:message code="export.file.type"></spring:message>
                           </label>
                           <div class="controls">
                              <form:select path="" cssClass="span6"
                                 itemValue="" id="typeSelect">
                                 <form:option value="0">select</form:option>
                                 <form:option value="1">Momo</form:option>
                                 <form:option value="2">Payment</form:option>
                              </form:select>
                              <form:hidden path="action" id="action" value="export" />
                              <spring:message var="msg_buttonExport" code="apply.now.export"></spring:message>
                              <button type="button"
                                 onclick="onExport()"
                                 class="btn_search_general" name="search" id="exportButton">${msg_buttonExport }</button>
                           </div>
                        </div>
                     </div>
                     <div class="accordion-inner">
                        <div class="input-area">
                           <div class="span12 input-append"></div>
                        </div>
                     </div>
                  </div>
               </div>
               <div id="previewDetailPopup" class="modal hide fade assetPopup"
                  tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                  aria-hidden="true" data-width="1000">
               </div>
               <div class="accordion-group">
                  <div class="accordion-heading">
                     <div class="row-fluid">
                        <div class="span2 title1">
                           <h3>
                              <spring:message code="export.file.listFile"></spring:message>
                           </h3>
                        </div>
                        <div class="span1 unit_accordion" style="text-align: right;">
                           <a class="accordion-toggle" data-toggle="collapse"
                              data-parent="#accordion2" href="#collapseOne_Export_Redeem"> 
                           <i class="bms-icon-accordion-down bms-icon-accordion"></i>
                           </a>
                        </div>
                     </div>
                  </div>
               </div>
               <!-- start table -->
               <div class="row-fluid row-align" id="collapseOne_Export_Redeem">
                  <div class="span12">
                     <table class="table table-bordered table-hover out-tbl">
                        <thead>
                           <tr>
                              <td colspan="11" style="padding: 0px; margin: 0px;">
                                 <div class="title_table row-fluid">
                                 	<div class="span6">
									</div>
                                    <div class="span6">
                                       <ext:pagination bean="${bean}" maxPage="5"
                                          formId="search_form_export"></ext:pagination>
                                    </div>
                                 </div>
                              </td>
                           </tr>
                           <tr>
                              <spring:message var="msg_stt" code="export.file.no"></spring:message>
                              <spring:message var="msg_type" code="export.file.type"></spring:message>
                              <spring:message var="msg_filename" code="export.file.fileName"></spring:message>
                              <spring:message var="msg_datetime" code="export.file.dateTime"></spring:message>
                              <spring:message var="msg_is_exporting" code="export.file.is.exporting"></spring:message>
                              <spring:message var="msg_status" code="export.file.status"></spring:message>
                              <spring:message var="msg_size" code="export.file.size"></spring:message>
                              <th class="w50_stt">${msg_stt}</th>
                              <th>${msg_type}</th>
                              <th>${msg_filename}</th>
                              <th>${msg_datetime}</th>
<%--                               <th>${msg_is_exporting}</th> --%>
                              <th>${msg_status}</th>
                              <th>${msg_size}</th>
                              <th class="table-actions">
                                 <spring:message code="actions" />
                              </th>
                           </tr>
                        </thead>
                        <tbody>
                           <c:set var="index" value="0"></c:set>
                           <c:forEach var="_redeemdata" items="${bean.listResult }" varStatus="i">
                              <tr id="tr${index}">
                                 <td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
                                 <td class="text_center">
                                    <c:out value="${_redeemdata.valueType }" />
                                 </td>
                                 <td>
                                    <c:out value="${_redeemdata.fileName }" />
                                 </td>

                                 <td class="text_center">
                                 	<fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm:ss" value="${_redeemdata.exportFileDate }" />
                                 </td>
<!--                                  <td class="text_center"> -->
<%--                                      <c:out value="${_redeemdata.is_exporting }" /> --%>
<!--                                  </td> -->
                                 <td class="text_center">
                                 <c:if test="${_redeemdata.statusProcess == 1 }">
                                 	<spring:message code="export.status.inprogress"></spring:message>
								 </c:if>
								 <c:if test="${_redeemdata.statusProcess == 2 }">
                                 	<spring:message code="export.status.completed"></spring:message>
								 </c:if>
                                 </td>
                                 <td class="text_center">
                                    <c:out value="${_redeemdata.sizeFile }" />
                                 </td>
                                 <td class="table-actions text_center">
                                 	<c:if test="${_redeemdata.statusProcess == 2 }">
	                                 	<spring:message var="icon_download" code="msg.home.download"></spring:message>
	                                    <spring:message var="icon_delete" code="icon.delete"></spring:message>
	                                    <a onclick="downloadFile('${_redeemdata.fileName}')" title="${icon_download }"> <i
	                                       class="bms-icon-export_sm"></i></a>
	                                    <a href="javascript:void(0)"
	                                       onclick="deleteAction('${_redeemdata.exportRedeemId }','${_redeemdata.fileName }')"
	                                       title="${icon_delete }"> <i class="bms-icon-delete"></i>
	                                    </a>
									</c:if>
                                 </td>
                              </tr>
                              <c:set var="index" value="${index + 1}"></c:set>
                           </c:forEach>
                           <tr>
                              <td colspan="11" style="padding: 0px; margin: 0px;">
                                 <div class="title_table row-fluid">
                                    <div class="span6 title">
                                       <div class="pagination1 pagination1-small pagination1-right pagination1-left-total-css" >
                                          <spring:message var="msg_Found" code="page.Found" />
                                          <spring:message var="msg_records" code="page.records" />
                                          <spring:message var="msg_page" code="page.page" />
                                          &nbsp;<span class="text">${msg_Found }</span><span class="number">${bean.total} </span><span class="text">${msg_records }</span><span class="number"> ${bean.totalPage}</span> <span class="text">${msg_page }</span>
                                       </div>
                                    </div>
                                    <div class="span6">
                                       <ext:pagination bean="${bean}" maxPage="5"
                                          formId="search_form_export"></ext:pagination>
                                    </div>
                                 </div>
                              </td>
                           </tr>
                           <tr>
                              <td colspan="6" style="padding: 0px; margin: 0px;"></td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
               <!-- and table -->
            </div>
         </div>
      </div>
   </form:form>
</div>