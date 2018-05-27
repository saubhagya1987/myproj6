<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>
<%@ taglib prefix="botDetect" uri="botDetect"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<%-- <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
	<%
		response.sendRedirect("");
	%>
</sec:authorize> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/font-awesome.css" />
<%-- <link rel="stylesheet" type="text/css" href="${url}/static/css/conteccons_style.css" /> --%>

<link rel="stylesheet" type="text/css" href="${url}/static/css/non-responsive.css" />
<link rel="stylesheet" type="text/css" href="${url}/static/css/vccb_style.css" />

<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${url}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${url}/static/css/js/modernizr.custom.86080.js"></script>
<title>MOBILE MANAGEMENT SYSTEM</title>
<script type="text/javascript">
	$(document).ready(function() {
		$('#username').focus();
	});
</script>

<style>
.form_login {
	border: 1px solid #007c3d;
	background-color: #007c3d;
	height: 143px;
	padding: 32px;
}

.input_login {
	height: 30px;
	margin-right: 7px;
	width: 188px;
	padding-left: 1px;
	padding-right: 7px;
	position: relative;
	top: 12px;
	top: -226px;
	left: 140px;
}

@
-moz-document url-prefix (){ .input_login {
	margin-right: 13px;
}

}
.login_homepage {
	float: right;
	margin-right: -143px;
}

.login_text {
	position: relative;
	left: -30px;
	top: 31px;
	color: white;
}

.btn_login {
	background: #c41b1e none repeat scroll 0 0;
	border: 0 none;
	color: #fff;
	left: 127px;
	padding: 5px;
	position: relative;
	top: -235px;
	<c:if test="${IS_ACTIVE_CAPTCHA }">
		top: -144px;
	</c:if>
}
</style>
</head>

<body style="padding: 0px;" class="bodylogin">

	<div class="title_login_system">
		<h2>MOBILE MANAGEMENT SYSTEM</h2>
	</div>

	<div class="wrap-login">

		<div class="login">
			<form action="<c:url value='j_spring_security_check' />" method="post">
				<spring:message code="login.username" var="username"></spring:message>
				<spring:message code="login.password" var="password"></spring:message>
				<span id="errormsg" class="error"> <c:if test="${not empty param.reason}">
						<spring:message code="${param.reason}" />
					</c:if>

				</span>

				<div style="position: relative;">
					<h4 class="login_text">Login</h4>
					<img src="${url}/static/images/login_home.png" class="login_homepage" /> <input id="username" type="text" placeholder="username"
						name="j_username" autocomplete="off" class="input_login"> </input> <br> <input id="password" type="password" placeholder="password"
						name="j_password" autocomplete="off" class="input_login"></input> <br> 
						
						<c:if test="${IS_ACTIVE_CAPTCHA }">
							<div style="position: absolute; left: 228px; top: 160px;">
								<botDetect:captcha id="springFormCaptcha" codeLength="5" imageWidth="172" imageStyles="graffiti, graffiti2" />
							</div>

							<div style="position: absolute; left: 89px; top: 445px;">
								<input id="captchaCodeTextBox" class="input_login" type="text" name="captchaCodeTextBox" value="${message.captchaCodeTextBox}" /><br>
									<div class="validationDiv"></div>
							</div>
						</c:if> 
						
						<br>
					<button type="submit" class="btn_login">Login</button>
				</div>
			</form>
		</div>

	</div>
</body>


</html>