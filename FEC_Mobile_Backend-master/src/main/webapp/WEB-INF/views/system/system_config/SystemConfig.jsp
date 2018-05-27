<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script src="${url}/static/js/bootstrap-tag-cloud.js"></script>
<link href="${url}/static/css/bootstrap-tag-cloud.css" rel="stylesheet">
<script src="${url}/static/js/bootstrap-timepicker.js"></script>

<script>
function backSystemConfig() {
		document.location.href = "${url}/system/system_config";
	}
	function back() {
		document.location.href = "";
	}
</script>	
	
<link href="${url}/static/css/bootstrap-timepicker.min.css"
	rel="stylesheet">
<spring:message var="system_1" code="systemconfig.field.systemconfig"></spring:message>
<spring:message var="system_2" code="systemconfig.field.systemparam"></spring:message>
<spring:message var="system_3"
	code="systemconfig.field.transactionparam"></spring:message>
<spring:message var="system_4" code="systemconfig.field.urlparam"></spring:message>
<spring:message var="system_5" code="systemconfig.field.respository"></spring:message>
<spring:message var="system_6" code="systemconfig.field.asset"></spring:message>
<spring:message var="system_7" code="systemconfig.field.process"></spring:message>
<spring:message var="system_8" code="systemconfig.field.banner"></spring:message>
<spring:message var="system_9" code="systemconfig.field.chat"></spring:message>
<spring:message var="system_10" code="systemconfig.field.config.apply.form"></spring:message>

<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_admin" code="menu.admin"></spring:message>
			<spring:message var="menu_system_config" code="systemconfig.field.systemconfig"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()" class="Color_back"> <c:out value="${menu_admin }"></c:out></a>
				<span> > </span>
				<a onclick="backSystemConfig()" class="Color_back"> <c:out value="${menu_system_config }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_system_config }"></c:out></span>
			</h4>
		</div>	
</div>
</div>	
</div>
<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<c:out value="${system_1 }"></c:out>
				</h2>
			</div>
		</div>
	
	<ext:messages bean="${bean}"></ext:messages>
	<form:form method="POST" cssClass="form-horizontal"
		modelAttribute="bean">		
			<div class="row-fluid">
				<div class="row-fluid border_bottom_red">

					<div class="title">
						<h3>
							<c:out value="${system_2 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.pageRow"> <spring:message
									code="systemconfig.field.pagerow"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.pageRow" cssClass="span10"
									itemValue="entity.pageRow">
									<form:option value="5">5</form:option>
									<form:option value="10">10</form:option>
									<form:option value="15">15</form:option>
									<form:option value="20">20</form:option>
									<form:option value="25">25</form:option>
									<form:option value="30">30</form:option>
									<form:option value="50">50</form:option>
								</form:select>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.pageRow"> <spring:message
 									code="systemconfig.min.time.pushmessage"></spring:message>
							</label>
							<div class="controls">
								<form:input type="number" path="entity.minTimePushmessage" min="3" style="width: 50px;" />
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.date"> <spring:message
									code="systemconfig.field.date"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.date" cssClass="span10"
									itemValue="entity.date">
									<form:option value="dd/MM/yyyy">dd/mm/yyyy</form:option>
									<form:option value="dd-MM-yyyy">dd-mm-yyyy</form:option>						
								</form:select>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label">
								<spring:message code="systemconfig.auto.export.mgm"></spring:message>
							</label>
							<div class="controls">
								<form:checkbox path="entity.autoExportMgm" value="on" style="margin-top: 5px;" />
							</div>
						</div>
					</div>
				</div>
				 <div class="row-fluid" style="display: none">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.defaultLanguage">
								<spring:message code="systemconfig.field.defaultlanguage"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.defaultLanguage" cssClass="span10">
									<form:option value="vi">
										<spring:message code="systemconfig.field.vi" />
									</form:option>
									<form:option value="en">
										<spring:message code="systemconfig.field.en" />
									</form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div> 
				 <div class="row-fluid"  style="display: none">
					<div class="span6">
						<ext-form:input-number-date id="value2" required="true"
							labelCode="systemconfig.field.fromyear" cssInput="span10"
							path="entity.formYear" maxlength="4"></ext-form:input-number-date>

					</div>
					<div class="span6">
						<ext-form:input-number-date id="value3" required="true"
							labelCode="systemconfig.field.toyear" cssInput="span10"
							path="entity.toYear" maxlength="4">
						</ext-form:input-number-date>
					</div>
				</div> 
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-number id="value4" required="true"
							labelCode="SystemConfig.activateCodeExpiredDate" cssInput="span10"
							path="entity.activateCodeExpiredDate" maxlength="4"></ext-form:input-number>

					</div>
					<div class="span6">
						<ext-form:input-number id="value5" required="true"
							labelCode="SystemConfig.resetPasswordExpiredDate" cssInput="span10"
							path="entity.resetPasswordExpiredDate" maxlength="4">
						</ext-form:input-number>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-number id="value6" required="true"
							labelCode="SystemConfig.activateCodeDate" cssInput="span10"
							path="entity.activateCodeDate" maxlength="4"></ext-form:input-number>
					</div>
					<div class="span6">
						<ext-form:input-number id="value7" required="true"
							labelCode="SystemConfig.getCodeDate" cssInput="span10"
							path="entity.getCodeDate" maxlength="4"></ext-form:input-number>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="SystemConfig.dao.driverclass"
							required="true" cssInput="span10" path="entity.driverclass"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="SystemConfig.dao.jdbcurl"
							required="true" cssInput="span10" path="entity.jdbcurl">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="SystemConfig.dao.userkh"
							required="true" cssInput="span10" path="entity.userkh">
						</ext-form:input-text>
					</div>
					
					<div class="span6">
						<ext-form:input-text labelCode="SystemConfig.dao.passworldkh"
							id="passworldkh" required="true" cssInput="span10"
							path="entity.passworldkh"></ext-form:input-text>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6" style="margin-left: 763px">
						<input type="checkbox" style="margin: 0 0 0 5px"
							onchange="document.getElementById('passworldkh').type = this.checked ? 'text' : 'password'">&nbsp;Show
						
						
						<script type="text/javascript">
							document.getElementById('passworldkh').type = 'password';
						</script>
					</div>
				</div>
				
				<div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_4 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text required="true"
							labelCode="systemconfig.field.urldef" cssInput="span10"
							path="entity.urlDefault">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailHost"
							required="true" cssInput="span10" path="entity.emailHost"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailPort"
							required="true" cssInput="span10" path="entity.emailPort">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailadres"
							required="true" cssInput="span10" path="entity.emailDefault"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailname"
							required="true" cssInput="span10" path="entity.emailDefaultName">
						</ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.emailPassword"
							id="emailPassword" required="true" cssInput="span10"
							path="entity.emailPassword"></ext-form:input-text>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6" style="margin-left: 175px">
						<input type="checkbox" style="margin: 0 0 0 5px"
							onchange="document.getElementById('emailPassword').type = this.checked ? 'text' : 'password'">&nbsp;Show
						
						
						<script type="text/javascript">
							document.getElementById('emailPassword').type = 'password';
						</script>
					</div>
				</div>
				
				 <sec:authorize access="hasAnyRole('R011')">
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.ldapdomain"
							required="true" cssInput="span10" path="entity.ldapdomain">
						</ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.ldapurl"
							required="true" cssInput="span10" path="entity.ldapurl">
						</ext-form:input-text>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.ldapsearch"
							required="true" cssInput="span10" path="entity.ldapsearch">
						</ext-form:input-text>
					</div>
					<div class="span6">
					
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.userldap"
							required="true" cssInput="span10" path="entity.userldap">
						</ext-form:input-text>
					</div>
					<div class="span6">
						<label class="control-label"> 
							<spring:message code="systemconfig.field.pswldap"/>
						</label>
						<div class="controls">
							<input type="password" class="span10" name="entity.pswldap" >
						</div>
					</div>
				</div>
				</sec:authorize>
				
				
				<div class="row-fluid" style="display: none;">
					<div class="control-group">
						<label class="control-label"> <spring:message
								code="systemconfig.field.emailBatch"></spring:message> <span
							class="required">*</span>
						</label>
						<div class="controls">
							<div class="span6">
								<div class="row-fluid">
									<label class="radio inline"> <form:radiobutton
											path="entity.emailOption" value="1" /> <spring:message
											code="systemconfig.field.emailSchedule"></spring:message>
									</label>

									<div class="bootstrap-timepicker" id="tag-info">
										<input id="timepicker1" type="text" class="input-small span6">
										<button class="btn btn-info" id="addTime" type="button"
											style="margin-left: 5px;">
											Add <i class="icon-plus"></i>
										</button>
									</div>

									<ul id="tag-cloud">

									</ul>
									<form:hidden path="entity.emailSchedule" id="emailSchedule" />

									<script type="text/javascript">
										$('#timepicker1').timepicker({
											minuteStep : 5,
											showInputs : false,
											disableFocus : true
										});
									</script>
								</div>
								<div class="row-fluid">
									<form:checkbox path="entity.checkLimmitEmail" value="1" />
									<label class="checkbox inline"> <spring:message
											code="systemconfig.field.limitEmail"></spring:message> <ext-form:input-number
											path="entity.limmitEmail" isGrid="true" id="limmitEmail"
											csswidth="30" cssInput="text-right"></ext-form:input-number>
									</label>
								</div>
							</div>
							<div class="span6">
								<label class="radio inline"> <form:radiobutton
										path="entity.emailOption" value="0" />
									<spring:message code="systemconfig.field.SendImmediately"></spring:message>
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid border_bottom_red" style="display: none;">
					<div class="title">
						<h3>
							<c:out value="${system_6 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid" style="display: none;">
					<div class="span6">
						<ext-form:input-text
							labelCode="systemconfig.field.unused.longtime" required="true"
							cssInput="span10" path="entity.notUsedLongTime"></ext-form:input-text>
					</div>
					<div class="span6"></div>
				</div>

				<div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_5 }"></c:out>
						</h3>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.tempfolder"
							required="true" cssInput="span10" path="entity.tempfolder">
						</ext-form:input-text>
					</div>
					<%-- <div class="span6">
						<ext-form:input-text
							labelCode="systemconfig.field.upload.dommain" required="true"
							cssInput="span10" path="entity.uploadDomain"></ext-form:input-text>
					</div> --%>
					
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.uploadApplynow.edit"
							required="true" cssInput="span10" path="entity.uploadApplynow"></ext-form:input-text>
					</div>
				</div>
				
				
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.uploadfolder"
							required="true" cssInput="span10" path="entity.repository"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.redeem.path"
							required="" cssInput="span10" path="entity.redeemPath"></ext-form:input-text>
					</div>
				</div>	
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.vtiger.path"
							required="" cssInput="span10" path="entity.vTigerPath"></ext-form:input-text>
					</div>
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.vtiger.path.other"
							required="" cssInput="span10" path="entity.vTigerPathOther"></ext-form:input-text>
					</div>
				</div>	
				<div class="row-fluid">
					<div class="span6">
						<ext-form:input-text labelCode="systemconfig.field.push.message.schedule.path"
							required="" cssInput="span10" path="entity.pushMessageFollowSchedule"></ext-form:input-text>
					</div>
				</div>	
				
				<%-- <div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_8 }"></c:out>
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerCardType"> <spring:message
									code="systemconfig.field.banner.card.type"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.bannerCardType" cssClass="span10" itemValue="entity.bannerCardType">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>		
					</div> --%>
					<%-- <div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerCardCategory"> <spring:message
									code="systemconfig.field.banner.card.category"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.bannerCardCategory" cssClass="span10" itemValue="entity.bannerCardCategory">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div> --%>
				<%-- </div>	
				
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerQuickCompare"> <spring:message
									code="systemconfig.field.banner.quick.compare"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.bannerQuickCompare" cssClass="span10" itemValue="entity.bannerQuickCompare">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>		
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerFullCompare"> <spring:message
									code="systemconfig.field.banner.full.compare"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.bannerFullCompare" cssClass="span10" itemValue="entity.bannerFullCompare">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerApplyForm"> <spring:message
									code="systemconfig.field.banner.apply.form"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.bannerApplyForm" cssClass="span10" itemValue="entity.bannerApplyForm">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>		
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerSendRequest"> <spring:message
									code="systemconfig.field.banner.send.request"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.bannerSendRequest" cssClass="span10" itemValue="entity.bannerSendRequest">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>		
					</div>
				</div>					
				<div class="row-fluid">					
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerRewardBenefit"> <spring:message
									code="systemconfig.field.banner.reward.benefit"></spring:message>(<spring:message code="titlebenefits.field.type.benefit" />)
							</label>
							<div class="controls">
								<form:select path="entity.bannerRewardBenefit" cssClass="span10" itemValue="entity.bannerRewardBenefit">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerRewardBenefitPlatium"> <spring:message
									code="systemconfig.field.banner.reward.benefit"></spring:message>(<spring:message code="titlebenefits.field.type.platium" />)
							</label>
							<div class="controls">
								<form:select path="entity.bannerRewardBenefitPlatium" cssClass="span10" itemValue="entity.bannerRewardBenefitPlatium">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.bannerRewardBenefitT365"> <spring:message
									code="systemconfig.field.banner.reward.benefit"></spring:message>(<spring:message code="benefitsdetail.365"/>)
							</label>
							<div class="controls">
								<form:select path="entity.bannerRewardBenefitT365" cssClass="span10" itemValue="entity.bannerRewardBenefitT365">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<c:forEach items="${lstBanner }" var="item">
										<form:option value="${item.bannerId }">${item.nameShow }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_9 }"></c:out>
						</h3>
					</div>
				</div>
				
				<div class="row-fluid"> --%>
		<%-- 			<div class="span6">
						<div class="control-group">
							<label for="entity.timeReceive" class="control-label">
								<spring:message code="systemconfig.field.time.receive"></spring:message> 
							</label>
							<div class="controls">	
								<fmt:formatNumber var="timeReceive" value="${bean.entity.timeReceive}" pattern="${sessionScope.formatNumber}" />
								<input type="text" id="timeReceive" onchange="myFormatNumber('timeReceive')" class="text_right number span10"  
									value="${timeReceive}" name="entity.timeReceive" />
							</div>	
						</div>
					</div> --%>
					<%-- <div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.numberSupport"> <spring:message
									code="systemconfig.field.number.support"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.numberSupport" cssClass="span10"
									itemValue="entity.numberSupport">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<form:option value="1">1</form:option>
									<form:option value="2">2</form:option>
									<form:option value="3">3</form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div> --%>	
				
				<%-- <div class="row-fluid border_bottom_red">
					<div class="title">
						<h3>
							<c:out value="${system_10 }"></c:out>
						</h3>
					</div>
				</div>
				
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="entity.configApplyForm"> <spring:message
									code="systemconfig.field.config.apply.form"></spring:message>
							</label>
							<div class="controls">
								<form:select path="entity.configApplyForm" cssClass="span10"
									itemValue="entity.configApplyForm">
									<form:option value=""><spring:message code="msg.choose"/> </form:option>
									<form:option value="1"><spring:message code="systemconfig.field.apply.form.basic"/></form:option>
									<form:option value="2"><spring:message code="systemconfig.field.apply.form.advance"/></form:option>
								</form:select>
							</div>
						</div>
					</div>
				</div>	 --%>
				
				<div class="row-fluid text-center">
					<button type="submit" id="save" class="btn_search_general">
						<spring:message code="systemconfig.field.submit" />
					</button>
				</div>
			</div>
		
		
		<spring:message code="team.field.enabled.active" var="message_active"/>
		<spring:message code="team.field.enabled.deactive" var="message_inactive"/>
		<input type="hidden" value="${message_active }" id="message_active"/>
		<input type="hidden" value="${message_inactive }" id="message_inactive"/>
	</form:form>
</div>
<script type="text/javascript">
	var formatNumber = "${sessionScope.formatNumber}";
	$(document).ready(
			function() {
				var values = $('#emailSchedule').val().split(',');
				for ( var i in values) {
					if (values[i] != '') {
						$('<li class="tag-cloud">' + values[i] + '</li>')
								.appendTo("#tag-cloud");
					}
				}
				$('#addTime').click(function() {
					var value = ',';
					$('#tag-cloud li').each(function() {
						if (value.indexOf(',' + $(this).html() + ',') != -1) {
							$(this).remove();
						} else
							value += $(this).html() + ',';
					});
					$('#emailSchedule').val(value);
				});
			});

	function myFormatNumber(id){
		$("#" + id).parseNumber({format:formatNumber, locale:"${sessionScope.localeSelect}"});
		$("#" + id).formatNumber({format:formatNumber, locale:"${sessionScope.localeSelect}"});
	}
	function initNumber(id){
		$("#" + id).blur(function() {
			myFormatNumber(id);
		});
	}
	
	/* function doAjaxPost() {
		// get the form values  
		var repositoryName = $('#repositoryName').val();
		var folderName = $('#folderName').val();

		$.ajax({
			type : "POST",
			url : "${url}/system/addRepository",
			data : "repositoryName=" + repositoryName + "&folderName=" + folderName,
			success : function(response) {
				if (response.status == "SUCCESS") {
					$('#repositoryName').val('');
					$('#folderName').val('');
					$('#error').hide('slow');
					$('#info').show('slow');
					var rowCount = $('#tblRoot tr').length;
					var message_active = $('#message_active').val();
					var message_inactive = $('#message_inactive').val();
					alert(message_active);
					var tdActive = "<td class='text_center'><a href='#'>"+message_inactive+"</a></td>"
					if(response.result.active == true) {
						tdActive = "<td class='text_center'><a href='#'>"+message_active+"</a></td>"
					} 
					$("#tblRoot > tbody").append(
							"<tr><td  class='w50_stt text_center'>" + rowCount
									+ "</td><td>" + response.result.repositoryName
									+ "</td><td>" + response.result.folderName
									+ "</td><td>" + response.result.totalSpace
									+ "</td><td>" + response.result.freeSpace
									+ "</td>" + tdActive
									+ "</tr>");
					$("#addRepository").load();
				} else {
					errorInfo = "";					
					$('#error').html(errorInfo);
					$('#info').hide('slow');
					$('#error').show('slow');
				}
				
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	
	}
	function doActive(id) {
		var message_active = $('#message_active').val();
		var message_inactive = $('#message_inactive').val();
		$.ajax({
			type : "POST",
			url : "${url}/system/activeRepository",
			data : "id=" + id,
			success : function(response) {
				var tdActive = message_inactive;
				if(response.result.active == true) {
					tdActive = message_active;
				} 
				$("#activeRep_"+id).html(tdActive);
				
				
			},
			error : function(e) {
				
			}
		});
	} */
</script>

