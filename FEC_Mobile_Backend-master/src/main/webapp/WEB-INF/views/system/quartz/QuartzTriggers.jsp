<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap/datagrid.css" />
<script src="${url}/static/js/bootstrap-datetimepicker.min.js"></script>
<link href="${url}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<!-- start title -->
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				<spring:message var="quartz_config" code="systemconfig.field.quartz.job"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"> <c:out value="${menu_admin }"></c:out></a> <span> > </span> <a onclick="backSystemConfig()"
						class="Color_back"> <c:out value="${quartz_config }"></c:out></a>
				</h4>
			</div>
		</div>
	</div>
</div>
<!-- end title -->

<!-- Start Content -->
<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span12 title_h2">
			<h2>
				<spring:message var="quartz_job_config" code="systemconfig.field.quartz.job"></spring:message>
				<c:out value="${quartz_job_config }"></c:out>
			</h2>
		</div>
	</div>

	<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
		<div class="row-fluid">
			<div class="span12" id="messageDiv">
				<ext:messages bean="${bean }"></ext:messages>
			</div>
		</div>

		<%-- <div class="row-fluid">
			<div class="accordion-group">
				<div class="accordion-heading">
					<div class="row-fluid">
						<div class="span2 title1">
							<h3>
								<spring:message code="mgm.config.title"></spring:message>
							</h3>
						</div>
						<div class="span1 unit_accordion" style="text-align: right;">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion2" href="#collapseOne"> <i
								class="bms-icon-accordion-down bms-icon-accordion"></i>
							</a>
						</div>
					</div>
				</div>
			</div>

			<spring:message var="msg_btn_search" code="button.search"></spring:message>

			<div id="collapseOne" class="accordion-body in collapse border-group">
				<div class="accordion-inner">
					<div class="input-area">

						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="schedName" cssInput="span10"
									labelCode="quartz.job.sched.name"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="triggerState"
									cssInput="span10" labelCode="quartz.job.trigger.state"></ext-form:input-text>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="triggerName" cssInput="span10"
									labelCode="quartz.job.trigger.name"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="triggerGroup"
									cssInput="span10" labelCode="quartz.job.trigger.group"></ext-form:input-text>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="jobName" cssInput="span10"
									labelCode="quartz.job.name"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="jobGroup"
									cssInput="span10" labelCode="quartz.job.group"></ext-form:input-text>
							</div>
						</div>
						<div class="row-fluid">
							<div class="control-group span6">
								<label class="control-label"> <spring:message
										code="quartz.job.trigger.type" />
								</label>
								<div class="controls">
									<form:select id="triggerType" cssClass="span10"
										path="triggerType">
										<form:option value="">
											<spring:message code="msg.select"></spring:message>
										</form:option>
									</form:select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- button -->
				<div class="text-center">

					<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
					<button type="button" onclick="" class="btn_search_general"
						name="search">${msg_buttonSearch }</button>
				</div>

			</div>
		</div> --%>

		<!-- Start Table -->
		<div class="row-fluid">
			<div class="row-fluid row-align">
				<div class="span12">
					<table class="table table-bordered table-hover out-tbl">
						<thead>
							<tr>
								<td colspan="12" style="padding: 0px; margin: 0px;">
									<div class="title_table row-fluid">
										<div class="span6 title">
											<h3>
												<spring:message code="msg.search.title"></spring:message>
											</h3>
										</div>
										<div class="span6">
											<ext:pagination bean="${bean}" maxPage="5" formId="search_form"></ext:pagination>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th></th>
								<th><spring:message code="quartz.job.sched.name" /></th>
								<th><spring:message code="quartz.job.trigger.name" /></th>
								<%-- 								<th><spring:message code="quartz.job.trigger.group" /></th> --%>
								<%-- 								<th><spring:message code="quartz.job.name" /></th> --%>
								<%-- 								<th><spring:message code="quartz.job.group" /></th> --%>
								<th><spring:message code="quartz.job.trigger.state" /></th>
								<th>Cron Expression</th>
								<%-- 								<th><spring:message code="quartz.job.trigger.type" /></th> --%>
								<th><spring:message code="quartz.job.prev.fire.time" /></th>
								<th><spring:message code="quartz.job.next.fire.time" /></th>
								<th><spring:message code="quartz.job.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${bean.listResult }" varStatus="i">
								<tr>
									<td class="text_center">${(bean.page-1)*bean.limit+i.index+1}</td>
									<td><c:out value="${item.schedName}"></c:out></td>
									<td><c:out value="${item.triggerName}"></c:out></td>
									<%-- 									<td><c:out value="${item.triggerGroup}"></c:out></td> --%>
									<%-- 									<td><c:out value="${item.jobName}"></c:out></td> --%>
									<%-- 									<td><c:out value="${item.jobGroup}"></c:out></td> --%>
									<td><c:out value="${item.triggerState}"></c:out></td>
									<td><c:out value="${item.cronExpression}"></c:out></td>
									<%-- 									<td><c:out value="${item.triggerType}"></c:out></td> --%>

									<c:choose>
										<c:when test="${item.prevFireTime != -1}">
											<jsp:useBean id="prevFireTime" class="java.util.Date" />
											<jsp:setProperty name="prevFireTime" property="time" value="${item.prevFireTime}" />
											<td><fmt:formatDate value="${prevFireTime}" pattern="${sessionScope.formatDate} HH:mm" /></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<jsp:useBean id="nextFireTime" class="java.util.Date" />
									<jsp:setProperty name="nextFireTime" property="time" value="${item.nextFireTime}" />
									<jsp:useBean id="now" class="java.util.Date" />
									<td><c:if test="${nextFireTime ge now}">
										</c:if>
											<fmt:formatDate value="${nextFireTime}" pattern="${sessionScope.formatDate} HH:mm" />
										
										</td>
									<td class="table-actions"><a onclick="editQuartz('${item.schedName}','${item.triggerName}')" title="Edit"> <i class="bms-icon-edit"></i></a>

										<c:if test="${item.triggerState == 'WAITING'}">
											<spring:message var="icon_play" code="icon.play" />
											<spring:message var="icon_pause" code="icon.pause" />
											<a class="playTriggerClass" title="${icon_play }" onclick="playTriggerButton('${item.triggerName}')" style="pointer-events: none;"> <i
												class="fe-icon-play" style="cursor: default; opacity: 0.2;"></i>
											</a>
											<a class="pauseTriggerClass" href="javascript:void(0)" onclick="pauseTriggerButton('${item.triggerName}')" title="${icon_pause }"
												id="pauseId"> <i class="fe-icon-pause"></i>
											</a>
										</c:if> <c:if test="${item.triggerState == 'PAUSED'}">
											<spring:message var="icon_play" code="icon.play" />
											<spring:message var="icon_pause" code="icon.pause" />
											<a class="playTriggerClass" title="${icon_play }" onclick="playTriggerButton('${item.triggerName}')"> <i class="fe-icon-play"></i>
											</a>
											<a class="pauseTriggerClass" href="javascript:void(0)" onclick="pauseTriggerButton('${item.triggerName}')" title="${icon_pause }"
												id="pauseId" style="pointer-events: none;"> <i class="fe-icon-pause" style="cursor: default; opacity: 0.2;"></i>
											</a>
										</c:if> <c:if test="${item.triggerState != 'WAITING' && item.triggerState != 'PAUSED'}">
											<spring:message var="icon_play" code="icon.play" />
											<spring:message var="icon_pause" code="icon.pause" />
											<a class="playTriggerClass" title="${icon_play }" onclick="playTriggerButton('${item.triggerName}')" style="pointer-events: none;"> <i
												class="fe-icon-play" style="cursor: default; opacity: 0.2;"></i>
											</a>
											<a class="pauseTriggerClass" href="javascript:void(0)" onclick="pauseTriggerButton('${item.triggerName}')" title="${icon_pause }"
												id="pauseId" style="pointer-events: none;"> <i class="fe-icon-pause" style="cursor: default; opacity: 0.2;"></i>
											</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- End Table -->

	</form:form>

</div>

<div id="previewDetailPopup" class="modal hide fade assetPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	data-width="1000"></div>


<!-- End COntent -->

<!-- Start Javascript -->
<script type="text/javascript">
  $(document).ready(function() {

    $("#previewDetailPopup").on('hide', function() {
      window.location.reload();
    });

  });

  function editQuartz(schedName, triggerName) {

    $.ajax({
      url: '${url}/system/quartz_job/loadQuartzEditPopup',
      cache: false,
      type: "GET",
      data: {
        schedName: schedName,
        triggerName: triggerName
      },
      async: true,
      success: function(resp) {
        $('#previewDetailPopup').html(resp);
        $('#previewDetailPopup').modal('show');
      }
    });

  }

  function saveCron() {

    $.ajax({
      url: '${url}/system/quartz_job/loadQuartzEditPopup',
      cache: false,
      type: "POST",
      data: $("#frm1").serialize(),
      async: true,
      success: function(resp) {
        $('#previewDetailPopup').html(resp);
        $('#previewDetailPopup').modal('show');
      }
    });

  }

  function playTriggerButton(triggerName) {
    window.location.href = '${url}/system/quartz_job/play?entity.triggerName=' + triggerName;
  }

  function pauseTriggerButton(triggerName) {
    window.location.href = '${url}/system/quartz_job/pause?entity.triggerName=' + triggerName;
  }

  function onRenew() {
    window.location.href = "${url}/system/quartz_job";
  }
</script>
<!-- End Javascript -->