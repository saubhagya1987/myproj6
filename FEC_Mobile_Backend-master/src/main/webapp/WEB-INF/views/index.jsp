
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap-modal.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap-modal-bs3patch.css" />


<div class="container-fluid unit_bg_content" style="margin-top: 120px;">
	<form:form method="POST" id="edit_form" cssClass="form-horizontal">
		<%-- <ext:messages bean="${bean}"></ext:messages> --%>
		<!-- start Note history -->
		<div class="row-fluid">
			<div class="span12 title_h3">
				<h3>
					<spring:message var="menu_home" code="menu.home"></spring:message>
					<%-- <c:out value="${menu_home}"></c:out> --%>
				</h3>
			</div>
		</div>
		<div class="row-fluid">
			
			<div class="home_content">
	 			<sec:authorize access="hasAnyRole('R001', 'R002')">
				<div class="img">
					<a href="${url}/master_data/customer/list"><img src="${url}/static/images/customer.png"
						class="img_customer"></a>
					<div class="desc"><spring:message code="menu.customers"></spring:message></div>
				</div>
				</sec:authorize>
					 <sec:authorize access="hasAnyRole('R001', 'R003')">
				<div class="img">
					<a href="${url}/message/dashboard/list"><img src="${url}/static/images/message_and_notification.png"
						class="img_notification"></a>
					<div class="desc">Messages</div>
				</div>
				</sec:authorize>
				 <sec:authorize access="hasAnyRole('R001', 'R005')">
				<div class="img">
					<a href="${url}/apply_now/list"><img src="${url}/static/images/loan_request.png"
						class="img_loan"></a>
					<div class="desc">Loan Request</div>
				</div>
				</sec:authorize>
				 <sec:authorize access="hasAnyRole('R001', 'R004')">
				<div class="img">
					<a href="${url}/contract/list"><img src="${url}/static/images/mobile_banking.png"
						class="img_mobile_banking"></a>
					<div class="desc">Mobile Banking</div>
				</div>
			</sec:authorize>
			 <sec:authorize access="hasAnyRole('R001', 'R006')">
				<div class="img">
					<a href="${url}/master_data/banner/list"><img src="${url}/static/images/banner.png"
						class="img_banner"></a>
					<div class="desc">Banners</div>
				</div>
				</sec:authorize>
				 <sec:authorize access="hasAnyRole('R001', 'R007')">
				<div class="img">
					<a href="${url}/master_data/pos/list"><img src="${url}/static/images/navigation.png"
						class="img_navigation"></a>
					<div class="desc">Navigation</div>
				</div>
				</sec:authorize>
				 <sec:authorize access="hasAnyRole('R001', 'R008')">
				<div class="img">
					<a href="${url}/master_data/cms/list"><img src="${url}/static/images/cms.png" class="img_cms"></a>
					<div class="desc">CMS</div>
				</div>
				</sec:authorize>
				 <sec:authorize access="hasAnyRole('R001', 'R009')">
				<div class="img">
					<a href="${url}/dashboard/view"><img src="${url}/static/images/reports.png"
						class="img_report"></a>
					<div class="desc">Reports</div>
				</div>
					</sec:authorize>
						 <sec:authorize access="hasAnyRole('R001')">
				<div class="img">
					<a href="${url}/system/account/list/"><img src="${url}/static/images/admin.png" class="img_admin"></a>
					<div class="desc">Admin</div>
				</div>
						</sec:authorize>
				<sec:authorize access="hasAnyRole('R001','HOP','CRO','FS','CA','R008','DOC','UH','CRO','HOF','CS*')">
					<div class="img">
						<a href="${url}/contract/repossession/view"><img src="${url}/static/images/contract.png"
							class="img_mobile_banking"></a>
						<div class="desc">Contract Repossession</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasAnyRole('R001','HOP','CRO','FS','CA','R008','DOC','UH','CRO','HOF','CS*')">
					<div class="img">
						<a href="${url}/contract/termination/view/"><img src="${url}/static/images/contract.png"
							class="img_mobile_banking"></a>
						<div class="desc">Contract Termination</div>
					</div>
				</sec:authorize>
			</div>
			
		</div>


	</form:form>

</div>