<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="welcome" code="welcome" />

<spring:message var="menu_master_department"
	code="menu.master.department"></spring:message>

<spring:message var="menu_term" code="msg.footer.term"></spring:message>


<style>
.unit_bg_content {
	margin-top: 158px;
}
.unit_bg_content_user_list{
margin-top: 212px;
}
</style>


<!-- start menutop and banner top -->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="banner-top">
		<div class="container-fluid">

			<div class="row-fluid">
				<div class="span4">
					<img src="${url}/static/images/logo1.png" width="269" height="75">
				</div>
				<div class="span4">
					<h2 style="padding: 25px 0 0 0; color: #ff0000;">Mobile
						Management System</h2>
				</div>


				<!--                 <div class="span2"> -->
				<!--                     <form class="navbar-form navbar-left" role="search"> -->
				<!--                         <div class="form-group"> -->
				<%--                             <spring:message var="box_search" code="msg.search.box"></spring:message> --%>
				<!--                             <input type="text" class="form-control" -->
				<%--                                 placeholder="${box_search}"> --%>
				<!--                         </div> -->
				<!--                     </form> -->
				<!--                 </div> -->


				<div class="span4 unit-right">
					<%--                     <span class="icon-map-marker"></span><span>&nbsp;<spring:message --%>
					<%--                             code="msg.location" /></span> --%>



					<c:url value='/userProfile/edit' var="userProfile_url"></c:url>
					<c:url value='/j_spring_security_logout' var="log_out_url"></c:url>

					<div class="row-fluid">
						<div class="span9">
							<span><span>Welcome: </span><a href="${userProfile_url } "
								style="color: #666"> <c:out
										value="${sessionScope['scopedTarget.userProfile'].fullName}" />
							</a> </br> <span> My account |&nbsp;</span> <a style="color: #666"
								href="${log_out_url } "><spring:message code="log.out" /> </a>
							</span> </br> <span style="color: #666">Version: 3.0</span>
						</div>
						<div class="span3">

							<img class="img-circle"
								src="${url}/${sessionScope['scopedTarget.userProfile'].imgPath}"
								width="55" height="55">
						</div>
					</div>

					<!--                     <a -->
					<%--                         id="langvi" onclick="changeLang('${url}', 'vi');" --%>
					<!--                         href="javascript:void(0);"> <img -->
					<%--                         src="${url}/static/images/vi.png" width="18" height="12">&nbsp; --%>
					<!--                     </a>  -->
					<%--                     <a id="langen" onclick="changeLang('${url}', 'en');" --%>
					<!--                         href="javascript:void(0);"> <img -->
					<%--                         src="${url}/static/images/en.png" width="18" height="12"> --%>
					<!--                     </a> -->
				</div>

			</div>

		</div>
	</div>
	<div class="menu-top_backend">
		<div class="container-fluid">
			<!-- Note that the .navbar-collapse and .collapse classes have been removed from the #navbar -->
			<div id="navbar">
				<ul class="nav navbar-nav">

					<li role="presentation"><a role="menuitem" tabindex="-1"
						href="${url}/index"><spring:message code="menu.home" /> </a></li>
					<sec:authorize access="hasAnyRole('R001', 'R002' ,'R012')">
						<li class="dropdown" id="menu_customer"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.customers" /> <b
								class="caret"></b></a>
							<ul id="menu_message" class="dropdown-menu" role="menu"
								aria-labelledby="drop2">
								<sec:authorize access="hasAnyRole('R001', 'R002')">
									<li role="presentation"><a role="menuitem" tabindex="-1"
										href="${url}/master_data/customer/list"><spring:message
												code="menu.customers" /> </a></li>
								</sec:authorize>
								<sec:authorize access="hasAnyRole('R001', 'R002')">
									<li role="presentation"><a role="menuitem" tabindex="-1"
										href="${url}/master_data/customer/init"><spring:message
												code="menu.customer.import" /> </a></li>
								</sec:authorize>
								<sec:authorize access="hasAnyRole('R001', 'R012')">
									<li role="presentation"><a role="menuitem" tabindex="-1"
										href="${url}/master_data/hobby/list"><spring:message
												code="customer.hobby.title" /> </a></li>
								</sec:authorize>

							</ul></li>
					</sec:authorize>


					<sec:authorize access="hasAnyRole('R001', 'R003' ,'R013')">
						<li class="dropdown" id="menu_master_message"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.messages.notification" />
								<b class="caret"></b></a>
							<ul id="menu_message" class="dropdown-menu" role="menu"
								aria-labelledby="drop2">
								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001' ,'R013')">
										<a role="menuitem" tabindex="-1"
											href="${url}/message/follow_up"><spring:message
												code="menu.messages.follow.up" /> </a>
									</sec:authorize> <sec:authorize access="hasAnyRole('R001', 'R003')">
										<a role="menuitem" tabindex="-1"
											href="${url}/message/dashboard/list"><spring:message
												code="menu.messages.dashboard" /> </a>
									</sec:authorize> <sec:authorize access="hasAnyRole('R001', 'R003')">
										<a role="menuitem" tabindex="-1"
											href="${url}/message/dashboard/importlist"><spring:message
												code="message.import.list" /> </a>
									</sec:authorize> <sec:authorize access="hasAnyRole('R001', 'R003')">
										<li role="presentation"><a role="menuitem" tabindex="-1"
											href="${url}/message/init"><spring:message
													code="menu.message.import" /> </a></li>
									</sec:authorize> <!-- Push message --> <sec:authorize
										access="hasAnyRole('R001', 'R003')">
										<li class="dropdown-submenu pull-left" role="presentation">
											<a role="menuitem" tabindex="-1" href="#"> <spring:message
													code="push.message" />
										</a>
											<ul class="dropdown-menu supmenu-w2" role="menu"
												style="left: 90%">
												<li role="presentation"><a role="menuitem"
													tabindex="-1" href="${url}/push_message/init"> <spring:message
															code="push.message.import" />
												</a></li>
												<li role="presentation"><a role="menuitem"
													tabindex="-1" href="${url}/push_message/list_template">
														<spring:message code="push.message.list.template" />
												</a></li>
											</ul>
										</li>
									</sec:authorize></li>
							</ul></li>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('R001', 'R005','R014','R015')">
						<li class="dropdown" id="menu_loan_request_data"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.loan.request" /> <b
								class="caret"></b></a>
							<ul id="menu3" class="dropdown-menu" role="menu"
								aria-labelledby="drop2">
								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R005')">
										<a role="menuitem" tabindex="-1" href="${url}/apply_now/list"><spring:message
												code="menu.submmited.loan.request" /> </a>
									</sec:authorize></li>
								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R005','R014')">
										<a role="menuitem" tabindex="-1" href="${url}/loan/list"><spring:message
												code="loan.caculator" /> </a>
									</sec:authorize></li>
								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R005','R015')">
										<a role="menuitem" tabindex="-1" href="${url}/loan/listdetail"><spring:message
												code="menu.loan.calculator.setup" /> </a>
									</sec:authorize></li>
							</ul></li>
					</sec:authorize>

					<sec:authorize access="hasAnyRole('R001','R004')">
						<li role="presentation"><a role="menuitem" tabindex="-1"
							href="${url}/contract/list"><spring:message
									code="menu.mobile.banking" /> </a></li>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('R001', 'R006')">
						<li role="presentation"><a role="menuitem" tabindex="-1"
							href="${url}/master_data/banner/list"><spring:message
									code="menu.banners" /> </a></li>
					</sec:authorize>

					<sec:authorize access="hasAnyRole('R001', 'R007')">
						<li class="dropdown" id="menu_navigation_data"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.navigation" /> <b
								class="caret"></b></a>
							<ul id="menu3" class="dropdown-menu" role="menu"
								aria-labelledby="drop2">

								<%-- <li role="presentation"><a role="menuitem" tabindex="-1"
                                 href="${url}/master_data/branch/list"><spring:message
                                         code="branch.list" /> </a></li> --%>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/master_data/pos/list"><spring:message
											code="pop.pop" /> </a></li>
								<%-- <li role="presentation"><a role="menuitem" tabindex="-1"
                                 href="${url}/master_data/searchmap/serach"><spring:message
                                         code="SearchMap.SearchMap" /> </a></li>         --%>
							</ul></li>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('R001', 'R008','R016','R017')">
						<li class="dropdown" id="menu_master_data"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.cms" /> <b class="caret"></b></a>
							<ul id="menu3" class="dropdown-menu" role="menu"
								aria-labelledby="drop2">

								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R008')">
										<a role="menuitem" tabindex="-1"
											href="${url}/master_data/cms/list"><spring:message
												code="menu.cms" /> </a>
									</sec:authorize></li>
								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R016')">
										<a role="menuitem" tabindex="-1"
											href="${url}/master_data/contact/show"><spring:message
												code="contact.show" /> </a>
									</sec:authorize></li>
								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R017')">
										<a role="menuitem" tabindex="-1"
											href="${url}/master_data/cms_category/list"><spring:message
												code="cmscategory.cmscategory" /> </a>
									</sec:authorize></li>
							</ul></li>
					</sec:authorize>
					<sec:authorize access="hasAnyRole('R001', 'R008','DOC','HOP','HOF', 'CRO','UH','CA','CSA','FS')">
						<li class="dropdown" id="menu_master_contract_data"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.contract" /> <b
								class="caret"></b></a>
							<ul id="menu.contract_repossession" class="dropdown-menu"
								role="menu" aria-labelledby="drop2">

								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R008','DOC','UH','CRO','HOF','CA','CSA','FS')">
										<a role="menuitem" tabindex="-1"
											href="${url}/contract/repossession/view"><spring:message
												code="menu.contract.repossession" /> </a>
									</sec:authorize></li>

								<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R002', 'DOC', 'HOP', 'HOF', 'CRO', 'FS', 'CA')">
										<a role="menuitem" tabindex="-1"
											href="${url}/contract/termination/view/"><spring:message
												code="menu.contract.termination" /> </a>
									</sec:authorize></li>
									
									
									<%-- <li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R002', 'DOC', 'CRO', 'HOP', 'HOF', 'FS', 'CA')">
										<a role="menuitem" tabindex="-1"
											href="${url}/contract/termination/downloadAttachment/1">Attachment 1</a>
									</sec:authorize></li>
									
									
									<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R002', 'DOC', 'CRO', 'HOP', 'HOF', 'FS', 'CA')">
										<a role="menuitem" tabindex="-1"
											href="${url}/contract/termination/downloadAttachment/2">Attachment 2</a>
									</sec:authorize></li>
									
									
									<li role="presentation"><sec:authorize
										access="hasAnyRole('R001', 'R002', 'DOC', 'CRO', 'HOP', 'HOF', 'FS', 'CA')">
										<a role="menuitem" tabindex="-1"
											href="${url}/contract/termination/downloadAttachment/3">Attachment 3</a>
									</sec:authorize></li> --%>

							</ul></li>
					</sec:authorize>


					<sec:authorize access="hasAnyRole('R001', 'R009')">
						<li role="presentation"><a role="menuitem" tabindex="-1"
							href="${url}/dashboard/view"><spring:message
									code="menu.report" /> </a></li>
					</sec:authorize>

					<sec:authorize access="hasAnyRole('R001')">
						<li class="dropdown" id="menu_system"><a
							class="dropdown-toggle" role="button" data-toggle="dropdown"
							href="#"><spring:message code="menu.admin" /> <b
								class="caret"></b></a>
							<ul id="menu2" class="dropdown-menu" role="menu"
								aria-labelledby="drop2">
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/account/list/"><spring:message
											code="menu.system.account" /> </a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/team/list/"><spring:message
											code="menu.system.team" /></a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/system_config"><spring:message
											code="systemconfig.field.systemconfig" /></a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/user/list/"><spring:message
											code="menu.system.user" /> </a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/cms/record/list/"><spring:message
											code="menu.system.record" /> </a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/role/list/"><spring:message
											code="menu.system.role" /> </a></li>
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/module/list/"><spring:message
											code="menu.system.module" /> </a></li>
								<!-- MGM config -->
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/mgm_config"><spring:message
											code="systemconfig.field.mgm.config" /></a></li>

								<!-- Job quartz -->
								<li role="presentation"><a role="menuitem" tabindex="-1"
									href="${url}/system/quartz_job/"><spring:message
											code="systemconfig.field.quartz.job" /></a></li>

								<li class="dropdown-submenu pull-left role="presentation"><a
									role="menuitem" tabindex="-1"
									href="${url}/system/masterdata/list/"><spring:message
											code="menu.system.masterdata" /> </a>
									<ul class="dropdown-menu supmenu-w2" role="menu">
										<li role="presentation"><a role="menuitem" tabindex="-1"
											href="${url}/system/masterdataDetail/list/"><spring:message
													code="menu.system.masterdataDetail" /> </a></li>
									</ul></li>
								<li class="dropdown-submenu pull-left role="presentation">
									<a role="menuitem" tabindex="-1" href="#"> <spring:message
											code="menu.system.export" />
								</a>
									<ul class="dropdown-menu supmenu-w2" role="menu">
										<li role="presentation"><a role="menuitem" tabindex="-1"
											href="${url}/system/exportFile/redeemTransactionPoint/list/"><spring:message
													code="menu.system.redeemTransactionPoint" /> </a></li>
										<li role="presentation"><a role="menuitem" tabindex="-1"
											href="${url}/system/exportFile/dataToVTiger/list/"><spring:message
													code="menu.system.dataToVTiger" /> </a></li>
									</ul>
								</li>
					</sec:authorize>

				</ul>

			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</nav>
<!-- end menutop and banner top -->

<!-- and menutop -->
<script type="text/javascript">
	$(document).ready(function() {
		accordion("accordion");
		accordion("accordion2");
		var varURL = (document.URL).replace("http://", "");
		var varURLsplit = varURL.split("/");
		var menuCurrent = varURLsplit[2];

	});
	function onCompleteBD() {
		if ($("#stockOpenTextMenuequipmentId").val() != ""
				&& $("#stockOpenTextMenuequipmentId").val() != undefined) {
			window.location.href = '${url}/equipmentProfileFull?id='
					+ $("#stockOpenTextMenuequipmentId").val();
		}
	}
</script>
