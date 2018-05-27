<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>


<div class="accordion" id="accordionMGM">
	<div class="accordion-group">
		<div class="accordion-heading">
			<div class="row-fluid">
				<div class="span4 title2">
					<h3>MGM - List friends</h3>
				</div>
				<div class="span1 unit_accordion unit_bg2" style="text-align: right;">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#collapseOne_listFriends" href="#collapseOne_listFriends"> <i
						class="bms-icon-accordion-down2 bms-icon-accordion"></i>
					</a>
				</div>
			</div>
		</div>
	</div>

	<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form" modelAttribute="mgmSCBean">
		<div id="collapseOne_listFriends" class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">

					<div class="row-fluid">
						<!-- start table -->
						<div class="row-fluid row-align">
							<div class="span12">
								<table class="table table-bordered table-hover out-tbl">
									<thead>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6"></div>
													<div class="span6">
														<ext:pagination-ajax divId="MGM_ListFriends" bean="${mgmSCBean}" formId="search_form" maxPage="5"
															ajaxUrl="${url}/master_data/customer/viewMGMListFriend?id=${id }">
														</ext:pagination-ajax>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th></th>
											<th><spring:message code="mgm.list.friend.fullname" /></th>
											<th><spring:message code="mgm.list.friend.cell.phone" /></th>
											<th><spring:message code="mgm.list.friend.id.card.no" /></th>
											<th><spring:message code="mgm.list.friend.status" /></th>
											<th><spring:message code="mgm.list.friend.date.was.invited" /></th>
											<th><spring:message code="mgm.list.friend.invite.method" /></th>
											<th><spring:message code="mgm.list.friend.date.to.become.loans" /></th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${fn:length(mgmSCBean.listResult) > 0}">
											<c:forEach var="_item" items="${mgmSCBean.listResult}" varStatus="i">
												<tr>
													<fmt:formatDate value="${_item.submitedDate}" pattern="${sessionScope.formatDate}" var="submitedDate" />
													<fmt:formatDate value="${_item.validatedDate}" pattern="${sessionScope.formatDate}" var="validatedDate" />
													<td class="w50_stt text_center">${(mgmSCBean.page-1)*mgmSCBean.limit+i.index+1}</td>
													<td class="text_left"><c:out value="${_item.contactName }"/> </td>
													<td class="text_center">${_item.contactPhone }</td>
													<td class="text_center">${_item.nationalId }</td>
													<td class="text_center">${mgmSCStatusLst[_item.status ] }</td>
													<td class="text_center">${submitedDate }</td>
													<td class="text_center">${mgmSCInviteMethodLst[_item.inviteMethod]}</td>
													<td class="text_center">${validatedDate }</td>
												</tr>
											</c:forEach>
										</c:if>
										<tr>
											<td colspan="12" style="padding: 0px; margin: 0px;">
												<div class="title_table row-fluid">
													<div class="span6 title">
														<div class="pagination1 pagination1-small pagination1-right pagination1-left-total-css">
															<spring:message var="msg_Found" code="page.Found" />
															<spring:message var="msg_records" code="page.records" />
															<spring:message var="msg_page" code="page.page" />

															&nbsp;<span class="text">${msg_Found }</span><span class="number">${mgmSCBean.total} </span><span class="text">${msg_records }</span><span
																class="number"> ${mgmSCBean.totalPage}</span> <span class="text">${msg_page }</span>
														</div>
													</div>
													<div class="span6">
														<ext:pagination-ajax divId="MGM_ListFriends" bean="${mgmSCBean}" formId="search_form" maxPage="5"
															ajaxUrl="${url}/master_data/customer/viewMGMListFriend?id=${id }">
														</ext:pagination-ajax>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<!-- and table -->
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>
