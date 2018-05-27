<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<style type="text/css">
	.font-bold {
		font-weight: bold;
	    background-color: hsla(200, 100%, 40%, 0.45);
	}

</style>

<div class="title_top">
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_customer" code="menu.master_data.customer"></spring:message>
			<spring:message var="customer_list" code="customer.list.title"></spring:message>
			<spring:message var="menu_customer_detail" code="customer.view.title"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backList()"class="Color_back"><c:out value="${menu_customer }"></c:out></a>
				<span> > </span>
				<a onclick="backList()"class="Color_back"><c:out value="${customer_list }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_customer_detail }"></c:out></span>
			</h4>
		</div>		
</div>
</div>
</div>
<div class="container-fluid unit_bg_content">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message code="customer.view.title"></spring:message>
				</h2>
			</div>
			
		</div>

	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" modelAttribute="bean"
		cssClass="form-horizontal">
		<div class="accordion" id="accordion2">
		</div>
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.detail.title"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion1" href="#collapseOne_1" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				
					
				</div>
			</div>
			<div id="collapseOne_1" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.first.name"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.firstName}&nbsp;</label>
									</div>
								</div>
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.middle.name"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.middleName}&nbsp;</label>
									</div>
								</div>
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.last.name"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.lastName}&nbsp;</label>
									</div>
								</div>
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.email"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.emailAddress}&nbsp;</label>
									</div>
								</div>
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.address.no"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.addressNo}&nbsp;</label>
									</div>
								</div>
								
								
								
							</div>
							<div class="span6">
								<div class="control-group1">
									<label style="line-height: 29px;" for="type" class="control-label"> <spring:message
											code="account.image"></spring:message>
									</label>
									<div class="controls">
										<div class="span6" style="width: 175px;">
											<div class="border_img_2" style="height: 165px; width:210px ">
												<img id="imgProduct" style="height: 100%;">
											</div>
										</div>
										<spring:message var="icon_view" code="icon.view"></spring:message>
										<spring:message var="icon_edit" code="icon.edit"></spring:message>
										<spring:message var="icon_clone" code="icon.clone"></spring:message>
										<spring:message var="icon_delete" code="icon.delete"></spring:message>
										<div class="span2" style="margin: 36px 0 30px">
											<%-- <div id="imageContainer" style="float: left; position: relative;">
													<a id="imgPickfiles" href="javascript:void(0)" title="${icon_edit }"
														style="width: 20px; height: 80px; background-color: yellow;"><i
														class="bms-icon-edit"></i></a>
												</div> --%>
											<%-- 	<a onclick="deleteImage();" title="${icon_delete }" ><i class="bms-icon-delete"></i></a> --%>

											<div style="clear: both;"></div>

											<pre id="imageConsole" style="display: none"></pre>

											<div id="imageFilelist"></div>

											<form:hidden path="entity.imagePath" id="tagImage" />
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="row-fluid">
							<div class="span6">
								<div class="control-group1">
									<label  class="control-label"> <spring:message
											code="customer.field.address.street"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.street}</label>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.birthday"></spring:message>
									</label>
									<div class="controls">
										<fmt:formatDate var="var_birthday"
											value="${bean.entity.birthday}"
											pattern="${sessionScope.formatDate}" />
										<label style="line-height: 29px;">${var_birthday}</label>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.address.ward"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.ward}</label>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.card.id.no"></spring:message>
									</label>
									<div class="controls">
										<label style="line-height: 29px;">${bean.entity.idCardNumber}</label>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
						<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.address.district"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.district}</label>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.cell.phone"></spring:message>
									</label>
									<div class="controls">

										<label style="line-height: 29px;">${bean.entity.cellPhone}</label>
									</div>
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.address.province"></spring:message>
									</label>
									<div class="controls">
										<label style="line-height: 29px;">${bean.entity.province}</label>
									</div>
								</div>
							</div>
							
							
							
							<div class="span6">
								<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.field.status"></spring:message>
									</label>
									<div class="controls">
										<c:if test="${bean.entity.status == 1}">
											<label style="line-height: 29px;"><spring:message
													code="customer.field.status.active" /></label>
										</c:if>
										<c:if test="${bean.entity.status == -1}">
											<label style="line-height: 29px;"><spring:message
													code="customer.field.status.inactive" /></label>
										</c:if>

									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
						<div class="span6">
						<div class="control-group1">
									<label class="control-label"> <spring:message
											code="customer.messages.field.created.date"></spring:message>
									</label>
									<div class="controls">
									<label style="line-height: 29px;">
									<fmt:formatDate var="var_addDate" value="${bean.entity.createDate}" pattern="${sessionScope.formatDate} HH:mm" />
									 <c:out value="${var_addDate }"> </c:out>
									</label>
									</div>
								</div>
						</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		<tiles:insertDefinition name="master_data.customer.view.template.MGM" ignore="true"/>

		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.hobby.title"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne_2" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				
				</div>
			</div>
			<div id="collapseOne_2" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span12 scoll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<h3></h3>
													</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th><spring:message code="msg.no" /></th>
											<th><spring:message code="customer.hobby.field.name" /></th>
											<th><spring:message
													code="customer.hobby.field.checked.date" /></th>
											<th><spring:message code="customer.field.status" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="_item" items="${bean.customerHobbyLst }"
											varStatus="i">
											<tr>
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${_item.name }"></c:out></td>
												<td class="text_center"><fmt:formatDate
														var="var_checkedDate" value="${_item.checkedDate}"
														pattern="${sessionScope.formatDate}" /> <c:out
														value="${var_checkedDate }"></c:out></td>
												<td class="text_center"><input type="checkbox"
													<c:if test="${_item.status == 1}"> checked</c:if>
													disabled="disabled"></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.wish.title"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion3" href="#collapseOne_3" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				
					
				</div>
			</div>
			<div id="collapseOne_3" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span12 scoll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<h3></h3>
													</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th><spring:message code="msg.no" /></th>
											<th><spring:message code="customer.wish.field.name" /></th>
											<th><spring:message code="customer.wish.field.add.date" /></th>
											<th><spring:message code="customer.wish.category" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="_wish" items="${bean.customerWishLst }"
											varStatus="i">
											<tr>
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${_wish.wishes }"></c:out></td>
												<td class="text_center"><fmt:formatDate
														var="var_addDate" value="${_wish.addDate}"
														pattern="${sessionScope.formatDate}" /> <c:out
														value="${var_addDate }"></c:out></td>
												<td><c:out value="${_wish.category }"></c:out></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.messages.title"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion4" href="#collapseOne_4" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				</div>
			</div>
			<div id="collapseOne_4" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span12">
							<div class="scoll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<h3>
															<spring:message code="customer.messages.title" />
														</h3>
													</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th><spring:message code="msg.no" /></th>
											<th><spring:message
													code="customer.messages.field.subject" /></th>
											<th><spring:message
													code="customer.messages.field.created.date" /></th>
											<th><spring:message
													code="customer.messages.field.read.date" /></th>
											<th><spring:message code="customer.messages.field.type" />
											</th>
											<th><spring:message
													code="customer.messages.field.comments.reply" /></th>
											<th><spring:message
													code="customer.messages.field.attachment" /></th>
											<th><spring:message code="customer.field.status" /></th>
											<th><spring:message code="actions" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="_messges" items="${messgesLstMap }" varStatus="i">
										
											<c:set var="isBold" value=""/>
											<c:if test="${_messges.status == 1  }">
												<c:set var="isBold" value="font-bold"/>
											</c:if>
											<tr class="${isBold }">
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${_messges.title }"></c:out></td>
												<td class="text_center">
													<fmt:formatDate value="${_messges.datetime}" pattern="${sessionScope.formatDate}" /> 
												</td>
												<td class="text_center">
													<fmt:formatDate value="${_messges.readDate}" pattern="${sessionScope.formatDate}" />
												</td>
												<td class="text_center type" data-type="${_messges.type}">												
													${listMessType[_messges.type] }
												</td>
												<td class="text_right"><c:out value="${_messges.numberOfReplies -1 }"></c:out>
												</td>
												<td>
													<c:if test="${hasAttachment >0 }">
														<a href="${url}/message/dashboard/view?messageId=${_messges.messageID}"><i class="icon-unlink"></i></a>
													</c:if>
												</td>
												<td class="text_center type" data-type="${_messges.status}">
														<c:choose> <c:when test="${_messges.status == 1}"> New </c:when>
														<c:when test="${_messges.status == 5}"> Closed </c:when>
														<c:when test="${_messges.status == 7}"> Canceled </c:when>
														<c:otherwise> Sent </c:otherwise>
														</c:choose>
												</td>
												<td> <a href="${url}/message/dashboard/view?messageId=${_messges.messageID}"><i class="bms-icon-view"></i></a> </td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								</div>
								<div class="scoll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<h3>
															<spring:message code="customer.contract.messages.title" />
														</h3>
													</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th><spring:message code="msg.no" /></th>
											<th><spring:message
													code="customer.messages.field.subject" /></th>
											<th><spring:message
													code="customer.contract.messages.field.date.time" /></th>
											<th><spring:message
													code="customer.contract.messages.field.contract.number" />
											</th>
											<th><spring:message
													code="customer.messages.field.comments.reply" /></th>
											<th><spring:message
													code="customer.messages.field.attachment" /></th>
											<th><spring:message code="customer.field.status" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="_cmessges"
											items="${bean.messgesLstContract }" varStatus="i">
											<tr>
												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${_cmessges.subject }"></c:out></td>
												<td class="text_center"><fmt:formatDate
														var="var_createdDate" value="${_cmessges.createdDate}"
														pattern="${sessionScope.formatDate}" /> <c:out
														value="${var_createdDate }"></c:out></td>
												<td class="text_center"></td>
												<td><c:out value="${_cmessges.commentsCount }"></c:out>
												</td>
												<td><a href="#"><i class="icon-unlink"></i></a></td>
												<td>
													<%-- <c:if test="${_cmessges.status == 0}">
														<spring:message code="customer.field.status.active" />	
													</c:if>	
													<c:if test="${_cmessges.status == 1}">
														<spring:message code="customer.field.status.inactive" />	
													</c:if>	
													<c:if test="${_cmessges.status == 2}">
														<spring:message code="customer.field.status.active" />	
													</c:if>	
													<c:if test="${_cmessges.status == 3}">
														<spring:message code="customer.field.status.inactive" />	
													</c:if>	
													<c:if test="${_cmessges.status == 4}">
														<spring:message code="customer.field.status.active" />	
													</c:if>	
													<c:if test="${_cmessges.status == 5}">
														<spring:message code="customer.field.status.inactive" />	
													</c:if>	
													<c:if test="${_cmessges.status == 6}">
														<spring:message code="customer.field.status.active" />	
													</c:if>	
													<c:if test="${_cmessges.status == 7}">
														<spring:message code="customer.field.status.inactive" />	
													</c:if>	
													<c:if test="${_cmessges.status == -2}">
														<spring:message code="customer.field.status.inactive" />	
													</c:if>	 --%>
												</td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.logs"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion5" href="#collapseOne_5" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				</div>
			</div>
			<div id="collapseOne_5" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span12 scoll">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<!-- 													<div class="span6 title"> -->
													<!-- 														<h3> -->
													<%-- 															<spring:message code="customer.messages.title" /> --%>
													<!-- 														</h3> -->
													<!-- 													</div> -->
													<div class="span6">
														<ext:pagination bean="${bean}" maxPage="5"
															formId="search_form"></ext:pagination>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th><spring:message
													code="customer.field.title.activity.no" /></th>
											<th><spring:message
													code="customer.field.title.activity.log.code" /></th>
											<th><spring:message
													code="customer.field.title.activity.log.date" /></th>
											<th><spring:message
													code="customer.field.title.activity.despritption" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="_activityLogs" items="${bean.customerActivityLogs }"
											varStatus="i">
											<tr>

												<td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
												<td><c:out value="${_activityLogs.logCode}"></c:out></td>
												<td class="text_center"><fmt:formatDate
														var="var_createdDate" value="${_activityLogs.logDate}"
														pattern="${sessionScope.formatDateTime}" /> <c:out
														value="${var_createdDate }"></c:out></td>
												<td><spring:message code="${_activityLogs.logCode}" /></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">&nbsp;</div>

												</div>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="span6">
									<ext:pagination bean="${bean}" maxPage="5" formId="search_form"></ext:pagination>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		 <c:if test="${ empty bean.addrestmap}">
			</c:if>
			<c:if test="${not empty bean.addrestmap}">
		<div class="accordion" id="accordion2">
			<div class="accordion-group">
				<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="customer.location"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion5" href="#collapseOne_6" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
						</a>
					</div>
				</div>
				
				</div>
			</div>			
				<input id="pac-input"  style="width:350px" type="hidden" value="${bean.addrestmap }" />
				
			<div id="collapseOne_6" class="accordion-body collapse in border-group">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid">
							<div class="span12 scoll">
								<div class="span12">
									<div id="map_canvas" style="height: 250px"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		</c:if>
	</form:form>
</div>
<!-- <script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&callback=initAutocomplete"
	async defer></script> -->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script type="text/javascript">


			$(document).ready(function() {
					
					// IMAGE account			
				var image = $("#tagImage").val();
						if (image != "") {
							$("#imgProduct").attr("src",
									"${url}/ajax/download?fileName=" + image);
						}
								
				//init image uploader
				var uploadUrl = "${url}/ajax/uploadTemp";
				var imagePickfiles = 'imgPickfiles';
				var imageContainer = 'imageContainer';
				var imageMaxFileSize = '7mb';
				var imageMimeTypes = [ {
					title : "Image files",
					extensions : "jpg,bmp,png"
				} ];
				var imageFileList = 'imageFilelist';
				var imageConsole = 'imageConsole';
				var imageFileUploaded = function(up, file, info) {
					$("#tagImage").val(cutString(info.response));
				};

				var imageUploadComplete = function(up, files) {
					var lstImg = $("#tagImage").val();

					$("#imgProduct").attr("src",
							"${url}/ajax/download?fileName=" + lstImg);
					$("#" + imageConsole).hide();
					$("#" + imageFileList).hide();
				};
				InitPlupload(imagePickfiles, imageContainer, uploadUrl, false,
						imageMaxFileSize, imageMimeTypes, imageFileList,
						imageConsole, imageFileUploaded, imageUploadComplete,
						'${url}');
				initialize();
			});

			function back() {
				document.location.href = "";
			}
				function backList() {
					document.location.href = "${url}/master_data/customer/list";
				}
	$('.cb_isprimary').change(function(event) {
		if($(this).is(':checked')){
			$(this).val('true')
		}else{
			$(this).val('false')
		}
	});

	$('.cb_type').change(function(event) {

	});
	



// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.
// This example adds a search box to a map, using the Google Place Autocomplete
// feature. People can enter geographical searches. The search box will return a
// pick list containing a mix of places and predicted search terms.

$("#pac-input").change(function() {
	var value=	$("#pac-input").val();
	if(value=='' || value==null){
		resetInput();
	}
});

function resetInput(){
	$('.latitude').val('');
	$('.longitude').val('');
	$("#administrative_area_level_2").val('');
	$("#administrative_area_level_1").val('');
}

function initAutocomplete() {
	
}


$("#pac-input").click(function(){
	
    //focus 'to' field
});
/* 	$("#pac-input").change(function() {
	if($("#administrative_area_level_2").val() == 'District'){
		$("#branchName").attr("readonly","readonly");
		$("#buyOrPay").attr("disabled","disabled");
	}else{
		$("#branchName").removeAttr("readonly");
		 $("#buyOrPay").removeAttr("disabled");
	}
	
}); */

function initialize() {
    geocoder = new google.maps.Geocoder();

    var address = document.getElementById('pac-input').value;
     
    geocoder.geocode({ 'address': address }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var myOptions = {
                zoom: 18,
                center: results[0].geometry.location,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
           
            var iconBase = '${url}/static/images/mapgoogle.png';
            var marker = new google.maps.Marker({ position: results[0].geometry.location,map:map });
            marker.setAnimation(google.maps.Animation.BOUNCE);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}
</script>

<style>
.scoll
{
max-height: 250px;
 overflow: auto;   
}
</style>