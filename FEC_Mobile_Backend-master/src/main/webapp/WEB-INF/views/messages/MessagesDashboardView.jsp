<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="${url }/static/js/plupload-2.1.2/plupload.full.min.js"></script>

<script type="text/javascript">
	function back() {
		document.location.href = "";
	}
	function onRenew() {
		window.location.href = "${url}/message/dashboard/list";
	}

	$(document).ready(function($) {
		var typeId = "${mess.type}";
		var parentMsgId = "${mess.parentMsgId}";
		var messageId = "${mess.messageID}";
		var customerId = "${mess.customer.customerId}";
		if (customerId != "") {
			var customer = message.getInfoCustomer(customerId);
			var aa=customer.imagePath;
			document.getElementById("myImg").src ='${url}/ajax/download?fileName='+aa; 
			$('#detailMsg').find('.info_detail_cus').click(function(){
				window.location.href = '${url}/master_data/customer/view?id=' + customerId;
			})
			
		}
		var accountId = "${accountId}";

		var data = message.getLstMessageByCustomerId(
				customerId, messageId, typeId, parentMsgId, accountId);

		message.genderHtmlMessageChat(data);
	});

	var message = {
		getInfoCustomer : function(customerId) {
			var rs;
			$.ajax({
				url : '${url}/message/getInfoCustomer',
				type : 'GET',
				async : false,
				data : {
					customerId : customerId,
				},
				success : function(data) {
					rs = data;
				}
			})
			return rs;
		},

		getLstMessageByCustomerId : function(customerId, messageId, typeId, parentMsgId, accountId) {
			var rs;
			$.ajax({
				url : '${url}/message/getLstMessageByCustomerId',
				type : 'GET',
				async : false,
				data : {
					customerId : customerId,
					messageId : messageId,
					typeId : typeId,
					parentMsgId : parentMsgId,
					accountId : accountId,
					dashboard : true
				},
				success : function(data) {
					rs = data;
				
				}
			})
			return rs;
		},

		genderHtmlMessageChat : function(data) {
			var html = '';
			var fileName = '';
			$.each(data, function(index, val) {
				var managerName = 'No Name';
				if (val.managerName != null) {
					managerName = val.managerName;
				}
				if (val.fileName != null) {
					var subS = val.fileName.substring(
							(val.fileName.indexOf(".") + 1),
							val.fileName.length);
					if (subS === "jpg" || subS === "jpeg"
							|| subS === "gif" || subS === "png") {
						fileName = '<div class="fileContent">'
										+'<a href="${url}/ajax/download?fileName='+ val.fileName+ '">'
											+'<img src="${url}/ajax/download?fileName='+ val.fileName+ '" alt="">'
										+'</a>'
									+'</div>';
					} else {
						fileName = '<div class="fileContent"><a href="${url}/ajax/download?fileName='
								+ val.fileName
								+ '" title="">'
								+ val.fileName + '</a></div>';
					}

				} else {
					fileName = '';
				}

				if (val.isMsgUser == 1) {
					var urlImage = "${url}/ajax/download?fileName="
							+ val.customerImgPath;
					if (val.customerImgPath == null) {
						urlImage = "${url}/static/images/avata_c5.gif"
					}
					if(val.content != null){
						html += '<div class="info_chat_detail customer">'
							+ '<div class="chat_detail_img">'
							+ '<img src="'+urlImage+'" alt="">'
							+ '</div>'
							+ '<div class="chat_detail_text">'
								+'<div id="triangle_left"></div>'
								+ '<div class="content">'
								+ val.content
								+ '</div>'
							+ '</div>'
							+ '<div class="chat_detail_hour">'
								+ val.creDate
							+ '</div>' 
							+ fileName
							+ '</div>';
					}else{
						// show content is image
						html += '<div class="info_chat_detail customer">'
							+ '<div class="chat_detail_img">'
							+ '<img src="'+urlImage+'" alt="">'
							+ '</div>'
							+ fileName
							+ '<div class="chat_detail_hour">'
								+ val.creDate
							+ '</div>' 
							+ '</div>';
					}
				} else {
					var urlImage = "${url}/ajax/download?fileName="
							+ val.managerImgPath;
					if (val.managerImgPath == null) {
						urlImage = "${url}/static/images/avata_c5.gif"
					}
					if(val.content != null){
						html += '<div class="info_chat_detail admin">'
							+ '<div class="chat_detail_img">'
								+ '<img src="'+urlImage+'" alt="">'
								+ '<div class="chat_detail_name">'+ managerName +'</div>'
								+ '<div class="chat_detail_hour">'
								+ val.creDate
								+ '</div>'
							+ '</div>'
							+ '<div class="chat_detail_text">'
								+'<div id="triangle_right"></div>'
								+ '<div class="content">'
								+ val.content
								+ '</div>'
							+ '</div>' 
							+ fileName
							+ '</div>';
					}else{
						// show content is image
						html += '<div class="info_chat_detail admin">'
							+ '<div class="chat_detail_img">'
								+ '<img src="'+urlImage+'" alt="">'
								+ '<div class="chat_detail_name">'+ managerName +'</div>'
								+ '<div class="chat_detail_hour">'
								+ val.creDate
								+ '</div>'
							+ '</div>'
							+ fileName 
							+ '</div>';
					}
					
				}
			});

			$('.info_chat .wrap_chat_detail').html('').html(html);
		},
	}
</script>

<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			
			<div class="span6">
				<spring:message var="notification" code="menu.messages.notification"></spring:message>
				<spring:message var="dashboard" code="menu.messages.dashboard"></spring:message>
				<spring:message var="messagedetail" code="menu.messages.dashboard.detail"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"><c:out value="${notification }" /></a> 
					<span> > </span> 
					<span class="Colorgray"><c:out value="${dashboard }" /></span> 
					<span> > </span> 
					<span class="Colorgray"><c:out value="${messagedetail }" /></span>
				</h4>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid unit_bg_content">
<div id="detailMsg">
		<div>
			
			<div class="span12 title_h2">
				<h2>Message Detail</h2>
			</div>
			<br /> <br />
<!-- 			<h3 id="myModalLabel">Message Detail</h3> -->
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<span style=" margin-right: 84px;">Subject:</span> 
					<span>${mess.subject }</span>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span6">
					<span style=" margin-right: 70px;">Sent Date:</span>
					<fmt:formatDate var="var_createddate" value="${mess.createdDate}" pattern="${sessionScope.formatDate}" /> 
					<span>${var_createddate }</span>
				</div>
				<div class="span6">
					<span style=" margin-right: 67px;">Message Type:</span> 
					<span>${messType }</span>
				</div>
			</div>
			<div style="color: #05552e;font-weight: bold;margin-top: 10px;">Detail</div>
			<hr style="border-color: #000 !important;border-top: 0px;margin-bottom: 0px !important;">
			<div class="row-fluid">
				<div class="span5">
					<div class="wrap_box_content detail_admin">
						<div class="wrap_item_img">
							<img id="myImg" src="" alt="">
						</div>
						<div class="wrap_item_info">
							<div class="info_detail">
								<span class="info_detail_title">Name:</span> <span
									class="info_detail_name"><c:out value="${mess.customer.fullName}"/> </span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Cell Phone:</span> <span
									class="info_detail_phone">${mess.customer.cellPhone}</span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Email:</span> <span
									class="info_detai_email">${mess.customer.emailAddress}</span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Id Card No:</span> <span
									class="info_detail_cardno">${mess.customer.idCardNumber}</span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Detail infomation:</span> <span
									class="info_detail_cus"><i class="icon-th-list"></i></span>
							</div>
						</div>
					</div>
				</div>
				<div class="span7" style="margin-left:3px;width: 491px;">
					<div class="info_chat">
						<div class="wrap_chat_detail">

						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>


<style type="text/css" media="screen">
.info_chat_detail.admin .chat_detail_img img {
    width: 40px !important;
    height: 40px !important;
    border: 1px solid #efefef;
}
.info_chat_detail.admin .chat_detail_hour {
    color: #05552e;
    margin-top: 4px;
}
.info_chat_detail.admin .chat_detail_text {
    width: 245px !important;
}

.info_chat_detail.customer .chat_detail_text {
    width: 245px !important;
    margin-left: 30px;
}
.info_chat_detail.customer .chat_detail_img {
    width: 42px;
    padding-left: 1px !important;
    color: #05552e;
}
.info_chat_detail.customer .chat_detail_img img {
    width: 40px !important;
    height: 40px !important;
    margin: 0px 5px 0px 0px;
    border: 1px solid #efefef;
}
.wrap_box_content .wrap_item_img {
	width: 77px;
	height: 77px;
	float: left;
	padding: 7px 0px;
	text-align: center;
}

.wrap_box_content .wrap_item_img img {
	width: 77px;
    height: 77px;
	margin-top: 0px;
}

.wrap_box_content .wrap_item_info {
	height: 100px;
	float: left;
}

#messageList .wrap_item_img {
	width: 55px;
	height: 56px;
	float: left;
}

#messageList .wrap_item_img img {
	width: 55px;
	height: 55px;
}

#messageList .th_customer {
	min-width: 310px;
}

#messageList .wrap_info {
	float: left;
	height: 56px;
	line-height: 19px;
	margin-left: 7px;
	color: #258D5C;
}

#messageList .title {
	display: inline-block;
	width: 105px;
	color: #333333;
}

.info_chat .wrap_chat_detail {
	height: 290px;
	overflow: auto;
	background-color: #F5F5F5;
	padding: 15px 10px;
	color: #2D5C2E;
}

.colorFont {
	color: #008345;
}

.info_detail .info_detail_cus {
    width: 35px;
    height: 35px;
    display: inline-block;
    border-radius: 50%;
    background-color: #008345;
    text-align: center;
    cursor: pointer;
}
.info_detail_cus .icon-th-list {
    color: #fff;
    font-size: 18px;
    line-height: 37px;
}
.info_detail .info_detail_title {
    display: inline-block;
    width: 105px;
    color: #05552e;
    padding-left: 10px;
}
#detailMsg .info_detail {
	color: #008345;
	line-height: 18px;
}
.btn_general {
    color: #FFFFFF;
    background-color: #ff0000;
    border: none;
    padding: 5px 10px;
    text-align: center;
    min-width: 80px;
    cursor: pointer;
}
#detailMsg .wrap_box_content.detail_admin {
    min-height: 125px;
    background-color: #F5F5F5;
    padding: 10px;
}
#detailMsg .info_chat_detail.customer{
	float: left;
	width: 100%;
	margin: 5px auto;
}
#detailMsg .info_chat_detail.admin{
	float: right;
	width: 100%;
}

#triangle_right{
	width: 0;
	height: 0;
	border-style: solid;
	border-width: 6px 0 6px 18px;
	border-color: transparent transparent transparent #dbecda;
	position: absolute;
    right: -18px;
    top: 12px;
}
#triangle_left{
	width: 0;
	height: 0;
	border-style: solid;
	border-width: 6px 18px 6px 0;
	border-color: transparent #e7ffe5 transparent transparent;
	position: absolute;
	left: -18px;
    top: 12px;
}
.info_chat_detail.admin .fileContent{
	width: 100px;
}
</style>