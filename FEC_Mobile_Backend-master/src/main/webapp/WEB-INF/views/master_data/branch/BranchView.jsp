<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<!-- start title -->
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>

					<span class="icon-bullhorn icon-medium"></span>
					<spring:message var="view_title" code="region.view.title"></spring:message>
					${view_title}
				</h2>
			</div>
			<div class="span6">
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="return_btn_msg" code="button.return"></spring:message>
						<li class="back"><a href="javascript:backList()"><span
								class="back_text">${return_btn_msg}</span></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<ext:messages bean="${bean}"></ext:messages>
<spring:message var="msg_regionCode" code="region.field.code"></spring:message>
<spring:message var="msg_name" code="region.field.name"></spring:message>
<div class="container-fluid">
	<div class="form-horizontal">
		<div class="input-area">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
		
							<label class="control-label"> ${msg_regionCode } </label>
							<div class="controls text_color">
								<label class="checkbox"> ${bean.entity.regionCode} </label>
		
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
		
							<label class="control-label"> ${msg_name } </label>
							<div class="controls text_color">
								<label class="checkbox"> ${bean.entity.name} </label>
		
							</div>
						</div>
		
					</div>
				</div>
		</div>
	
	
	</div>
</div>	
<script type="text/javascript">
function backList(){
	  document.location.href="list";
}
</script>