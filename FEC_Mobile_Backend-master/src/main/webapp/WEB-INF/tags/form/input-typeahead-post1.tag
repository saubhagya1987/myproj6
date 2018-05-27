<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String" description="Path to binding"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="Label name"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String" description="Label code"%>
<%@ attribute name="helpLine" required="false" rtexprvalue="true" type="java.lang.String" description="Help line"%>
<%@ attribute name="helpLineCode" required="false" rtexprvalue="true" type="java.lang.String" description="Help line code"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required"%>
<%@ attribute name="cssInput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="disable" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Disable field"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Visible field"%>
<%@ attribute name="dataprovide" required="false" rtexprvalue="true" type="java.lang.String" description="Typeahead"%>
<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" description="Url ajax"%>
<%@ attribute name="parentControl" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control is parent text and parent id"%>
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="Id"%>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.lang.String" description="name"%>
<%@ attribute name="separate" required="true" rtexprvalue="true" type="java.lang.String" description="separate"%>
<%@ attribute name="defaultvalue" required="false" rtexprvalue="true" type="java.lang.String" description="defaultvalue"%>
<%@ attribute name="match" required="false" rtexprvalue="true" type="java.lang.Boolean" description="match"%>
<%@ attribute name="matchmsg" required="false" rtexprvalue="true" type="java.lang.String" description="matchmsg"%>
<%@ attribute name="labelControl" required="false" rtexprvalue="true" type="java.lang.String" description="label control"%>
<%@ attribute name="displayLabelControl" required="false" rtexprvalue="true" type="java.lang.String" description="display label control"%>
<%@ attribute name="displayLabelControl1" required="false" rtexprvalue="true" type="java.lang.String" description="display label control 1"%>
<%@ attribute name="displayLabelControl2" required="false" rtexprvalue="true" type="java.lang.String" description="display label control 2"%>
<%@ attribute name="text" required="true" rtexprvalue="true" type="java.lang.String" description="text control"%>
<%@ attribute name="inputControl" required="false" rtexprvalue="true" type="java.lang.String" description="input control"%>
<%@ attribute name="inputControl1" required="false" rtexprvalue="true" type="java.lang.String" description="input control 1"%>
<%@ attribute name="inputControl2" required="false" rtexprvalue="true" type="java.lang.String" description="input control 2"%>
<%@ attribute name="hiddenControl" required="false" rtexprvalue="true" type="java.lang.String" description="hidden control"%>
<%@ attribute name="hiddenId" required="false" rtexprvalue="true" type="java.lang.String" description="hidden id"%>
<%@ attribute name="functionUpdate" required="false" rtexprvalue="true" type="java.lang.String" description="function update"%>
<%@ attribute name="cssHidden" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="isGrid" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Grid field"%>
<%@ attribute name="displayvalue" required="false" rtexprvalue="true" type="java.lang.String" description="displayvalue field"%>
<%@ attribute name="hiddenvalue" required="false" rtexprvalue="true" type="java.lang.String" description="hiddenvalue field"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.String" description="readonly"%>
<%@ attribute name="parentControl1" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control next is parent text and parent id"%>
<%@ attribute name="parentControl2" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control next is parent text and parent id"%>
<%@ attribute name="parentControl3" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control next is parent text and parent id"%>
<%@ attribute name="removeAttribute" required="false" rtexprvalue="true" type="java.lang.String" description="Remove attribute when select"%>
<%@ attribute name="restID1" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID2" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID3" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="csswidth" required="false" rtexprvalue="true" type="java.lang.String" description="width"%>
<%@ attribute name="param1" required="false" rtexprvalue="true" type="java.lang.String" description="Parameter in request"%>
<%@ attribute name="param2" required="false" rtexprvalue="true" type="java.lang.String" description="Parameter in request"%>
<%@ attribute name="updateUnmatch" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Update value hiddent when unmatch"%>
<c:set var="urleP" value="${pageContext.request.contextPath}"></c:set>
<c:if test="${empty csswidth }"><c:set var="csswidth" value="100"></c:set> </c:if>
<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>

<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty disable }"><c:set var="disableTag" value="false"></c:set> </c:if>
<c:set var="tagreadonly" value=""></c:set> 
<c:if test="${readonly}"><c:set var="tagreadonly" value="readonly"></c:set> </c:if>
<c:if test="${empty readonly}"><c:set var="tagreadonly" value="false"></c:set> </c:if>
<c:if test="${empty separate }"><c:set var="separate" value="-"></c:set> </c:if>
<c:set var="displaykeys" value="${fn:split(displayLabelControl, separate)}" />
<c:set var="displaykeys1" value="${fn:split(displayLabelControl1, separate)}" />
<c:set var="displaykeys2" value="${fn:split(displayLabelControl2, separate)}" />
<c:set var="displaynames" value="${fn:split(names, separate)}" />
<c:if test="${empty param1}"><c:set var="param1" value=""></c:set> </c:if>
<c:if test="${empty param2}"><c:set var="param2" value=""></c:set> </c:if>

<c:choose >
	<c:when test="${disable }">
		<c:set var="disableTag" value="disabled='true'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="disableTag" value=""></c:set>
		
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${fn:length(defaultvalue)==1}">
		<c:set var="defaultvalue" value="${param[text] }"></c:set>
	</c:when>
	<c:otherwise>
			<c:if test="${fn:indexOf(defaultvalue,separate)==0}">
				<c:set var="defaultvalue" value="${fn:substring(defaultvalue,1,fn:length(defaultvalue)) }"></c:set>
			</c:if>
	</c:otherwise>
</c:choose>
<c:set var="hiddenId" value="${hiddenId }"></c:set>
<c:if test="${ empty hiddenId }"> <c:set var="hiddenId" value="${id}"></c:set></c:if>
<c:set var="displayLabelControl_msg" value="${displayLabelControl}"></c:set>
<c:set var="displayLabelControl_msg1" value="${displayLabelControl1}"></c:set>
<c:set var="displayLabelControl_msg2" value="${displayLabelControl2}"></c:set>

<c:if test="${ empty displayLabelControl }"> <c:set var="displayLabelControl_msg" value="${id}"></c:set></c:if>
<c:choose >
	<c:when test="${not empty defaultvalue }">
		<c:set var="defaultvalueTag" value="value='${defaultvalue }'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="defaultvalueTag" value="${param[text] }"></c:set>
		
	</c:otherwise>
</c:choose>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>

	<c:if test="${ not empty label }"> <c:set var="label_msg" value="${label}"></c:set></c:if>
	<c:if test="${ not empty labelCode }">
		<spring:message var="label_msg1" code="${labelCode}"></spring:message>
		 <c:set var="label_msg" value="${label_msg1}"></c:set>
	</c:if>

	<c:if test="${ not empty helpLine }"> <c:set var="helpLine_msg" value="${helpLine}"></c:set></c:if>
	<c:if test="${ not empty helpLineCode }">
		 <spring:message var="helpLine_msg1" code="${helpLineCode}"></spring:message>
		 <c:set var="helpLine_msg" value="${helpLine_msg1}"></c:set>
	</c:if>
<c:if test="${visible }">

<c:choose>
<c:when test="${isGrid}">
					<div id="scrollable-dropdown-menu">
					<input type="text" data-provide="${dataprovide }" ${tagreadonly } autocomplete="off" ${defaultvalueTag} name="${text }" id="${text }" 
				 ${disableTag } class="${cssInput}" style="width:${csswidth}px" value="${displayvalue}"/>
				 </div>
				<form:hidden path="${path }" id="${text }${id }" class="${cssHidden}" value="${hiddenvalue}"/>
				
				<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
				<span class="help-inline ${error_clss}_msg">
					<c:if test="${not empty helpLine_msg }">
						<c:out value="${helpLine_msg }"></c:out>
					</c:if>
					<form:errors path="${path}" cssClass="error" ></form:errors>
					<c:choose >
						<c:when test="${match}">
					<span id="${text}${id}error" class="error"></span>
					</c:when>
					</c:choose>
				</span>
			
</c:when>
<c:otherwise>
<div class="control-group">
	
			<label class="control-label" for="${path}">
											${label_msg } 
										<c:if test="${required }">
										<span class="required">*</span>
									</c:if>
								</label>
								
			<div class="controls">
				
			
								
									<table style="width: 90%;">
										<tr>
											<td style="width: 40%; vertical-align: top;">
												<table class="table table-bordered table-hover out-tbl" id="tblLeftRole">
													<thead>
														<tr>
															<td colspan="3" style="padding: 0px; margin: 0px;">
																<div id="title_table row-fluid">
																<div class="span12 title">
																<input type="text" data-provide="${dataprovide }" 
																autocomplete="off" ${tagreadonly } ${defaultvalueTag} 
																name="${text }" id="${text }" class="${cssInput}" ${disableTag }/>
															</div></div>
															<form:hidden path="${path }" id="${text }${id }" class="${cssHidden}" />
															</td>
														</tr>
														<tr>
															<th width="30"><input type="checkbox" name="chkAllRoleLeft" id="chkAllRoleLeft" value=""></th>
															<th width="120"><spring:message var="msg_productcode" code="master.product.field.code"></spring:message>
																${msg_productcode}</th>
															<th><spring:message var="msg_productname" code="master.product.name"></spring:message>
																${msg_productname}</th>

														</tr>
													</thead>
													<tbody>
														<tr>
															<td colspan="3" style="padding: 0px;border-left: 0px">
																<div style=" max-height: 200px; overflow: auto">
																	<table id="trLeft" width="100%"></table>
																</div>
															</td>
														</tr>
													</tbody>

												</table>

											</td>
											<td style="width: 2%">&nbsp;</td>
											<td style="width: 40%; vertical-align: top;">
												<table class="table table-bordered table-hover out-tbl" id="tblRightRole">
													<thead>
														<tr>
															<td colspan="3" style="padding: 0px; margin: 0px;">
																<div class="title_table row-fluid">
																	<div class="span12 title">
																		
																	</div>
																</div>
															</td>
														</tr>
														<tr>
															<th width="30"><input type="checkbox" id="chkAllRoleRight" value="" checked="checked"></th>
															<th width="120"><spring:message var="msg_productcode" code="master.product.field.code"></spring:message>
																${msg_productcode}</th>
															<th><spring:message var="msg_productname" code="master.product.name"></spring:message>
																${msg_productname}</th>

														</tr>
													</thead>
													<tbody>
														<tr>
															<td colspan="3" style="padding: 0px;border-left: 0px">
																<div style=" max-height: 200px; overflow: auto">
																	<table id="trRight" width="100%">
																	<c:forEach var="_lst" items="${bean.lstProducts}" varStatus="i">
																	<c:if test="${_lst!=null && _lst.productId!=null }">
																		<tr>
																			<td width='30' class="text_center" >
																				<input type="checkbox" name="chkRoleRight" id="right${i.index}" checked="checked" value="${_lst.productId}" onclick="del(${i.index})">
																				<input type="hidden" id="codeR-${i.index}" name="lstProducts[${i.index}].productCode" value="${_lst.productCode}"/>
																				<input type="hidden" id="nameR-${i.index}" name="lstProducts[${i.index}].name" value="${_lst.name}"/>
																				<input type="hidden" id="idR-${i.index}" name="lstProducts[${i.index}].productId" value="${_lst.productId}"/>
																			</td>
																			<td width='120'><c:out value="${_lst.productCode}"></c:out></td>
																			<td><c:out value="${_lst.name}"></c:out></td>
																		</tr>
																		</c:if>
																	</c:forEach>
																	</table>
																	
																</div>
															</td>
														</tr>
													</tbody>

												</table>

											</td>
											<td style="width: 2%">&nbsp;</td>
										</tr>

									</table>

								
							</div>
</div>
</c:otherwise>
</c:choose>



</c:if>
<c:if test="${ not empty matchmsg}">
	<spring:message var="match_msg" code="${matchmsg}"></spring:message>
</c:if>
<script language="javascript">
var indexRight = 0;
var indexLeft = 0;
$(document).ready(function(){
	indexRight = ${bean.lstProducts.size()};
	$("#chkAllRoleLeft").click(function() {
			this.checked = false;
		$("input[name=chkRoleLeft]").each(function() {
			this.click();
		});
	});
	$("#chkAllRoleRight").click(function() {
		this.checked = true;
		$("input[name=chkRoleRight]").each(function() {
			this.click();
		});
	});
	$("#${text }").typeahead({
	  source: function(query, process) {
	        $.ajax({
	        	url:'${url}',
	        	data: $("#search_form").serialize(),
	        	type: 'POST',
	        	cache:false,
	        	/* async : false, */
	        	success:function(result){
	        		var a="";
	        		indexLeft=0;
	        		if (result!=null) {
	        			$.each(result, function(i, object) {
		        			a= a+ "<tr>"
		        					+"<td width='30' class='text_center' valign='top'>"
		        						+"<input type='checkbox' id='left"+i+"' name='chkRoleLeft' value='"+object.productId+"' onclick='appProd("+i+")'/>"
		        						+"<input type='hidden' id='idL-"+i+"' value='"+object.productId+"'>"
		        						+"<input type='hidden' id='nameL-"+i+"' value='"+object.name+"'>"
		        						+"<input type='hidden' id='codeL-"+i+"' value='"+object.productCode+"'>"
		        					+"</td>"
		        					+"<td width='120'>"+object.productCode+"</td>"
		        					+"<td>"+object.name+"</td>"
		        				+"</tr>";
		        			indexLeft++;
			     	     });
					}
	        		
	        		$("#trLeft").html(a);
	        	}
	        })
	    },
	    
	});
	

});

function appProd(index){
	$("#nameL-"+index).attr("name","lstProducts["+indexRight+"].name");
	$("#nameL-"+index).attr("id","nameR-"+indexRight);
	$("#codeL-"+index).attr("name","lstProducts["+indexRight+"].productCode");
	$("#codeL-"+index).attr("id","codeR-"+indexRight);
	$("#idL-"+index).attr("name","lstProducts["+indexRight+"].productId");
	$("#idL-"+index).attr("id","idR-"+indexRight);
	
	var row = $("#left"+index).closest("tr");
	row.detach();
	$("#trRight").append(row);
	$("#left"+index).attr("onclick","del("+indexRight+")");
	$("#left"+index).attr("name","chkRoleRight");
	$("#left"+index).checked = true;
	$("#left"+index).attr("id","right"+indexRight);
	indexRight++;
}
function del(index){
	$("#nameR-"+index).attr("name","");
	$("#nameR-"+index).attr("id","nameL-"+indexLeft);
	$("#codeR-"+index).attr("name","");
	$("#codeR-"+index).attr("id","codeL-"+indexLeft);
	$("#idR-"+index).attr("name","");
	$("#idR-"+index).attr("id","idL-"+indexLeft);
	
	var row = $("#right"+index).closest("tr");
	row.detach();
	$("#trLeft").append(row);
	$("#right"+index).attr("onclick","appProd("+indexLeft+")");
	$("#right"+index).attr("name","chkRoleLeft");
	$("#right"+index).checked = false;
	$("#right"+index).attr("id","left"+indexLeft);
	indexLeft++;
}

</script>