<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap/easyui.css" />
<script type="text/javascript"
	src="${url}/static/js/jquery.easyui.min.js"></script>

<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="notification" code="menu.messages.notification"></spring:message>
				<spring:message var="dashboard" code="message.import.list"></spring:message>
				<h4 style="padding: 8px 0 0 10px;">
					<a onclick="back()" class="Color_back"> <c:out
							value="${notification }"></c:out></a> <span> > </span> <span
						class="Colorgray"><c:out value="${dashboard } "></c:out></span>
				</h4>

			</div>
		</div>
	</div>
</div>

<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="span12 title_h2">
			<h2>
				<spring:message var="dashboard" code="message.import.list"></spring:message>
				<c:out value="${dashboard } "></c:out>
			</h2>
		</div>

	</div>

	<form:form method="POST" id="search_form" cssClass="form-horizontal"
		modelAttribute="bean">
		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid">
		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="search.area"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion2" href="#collapseOne" >
							<i class="bms-icon-accordion-down bms-icon-accordion" ></i>
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
								<ext-form:input-text path="customerName" cssInput="span10" labelCode="customer.field.customer.name"></ext-form:input-text>
							</div>
							<div class="span6">
								<ext-form:input-text path="cellPhone" cssInput="span10" labelCode="customer.field.cell.phone"></ext-form:input-text>
							</div>
						</div>	
						<div class="row-fluid">
							<div class="span6">
								<ext-form:input-text path="subject" cssInput="span10" labelCode="customer.messages.field.subject"></ext-form:input-text>
							</div>
							<div class="span6">
								<div class="control-group">
									<label class="control-label">
										<spring:message code="message.dashboard.message.type" />
									</label>
									<div class="controls">
									<form:select id="messageType" cssClass=""
								path="messageType">
								<form:option value=""><spring:message code="msg.all"></spring:message> </form:option>
									<c:forEach items="${messageTypeDAO}" var="j">
									<form:option value="${j.code }">${j.name }</form:option>
									</c:forEach>
						</form:select>
									</div>									
								</div>
							</div>
						</div>
					<div class="row-fluid ">
							<div class="span6 ">
							<fmt:formatDate var="dateFromSelected" value="${bean.formSendDate}" pattern="${sessionScope.formatDate}" />
								<ext-form:input-date path="formSendDate" id="dateFrom" value="${dateFromSelected }" labelCode="customer.messages.field.created.date.from"></ext-form:input-date>
							</div>
							
							<div class="span6 ">
							<fmt:formatDate var="dateFromSelected" value="${bean.toSendDate}" pattern="${sessionScope.formatDate}" />
								<ext-form:input-date path="toSendDate" id="dateto" value="${dateFromSelected }" labelCode="customer.messages.field.created.date.to"></ext-form:input-date>
							</div>
							
							<div class="row-fluid ">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">
										<spring:message code="pendingPayment.import.date.searchIsRead" />
									</label>
									<div class="controls">
										<form:select path="isRead" class="span10" itemValue="languageId">
											<form:option value=""><spring:message code="msg.all"></spring:message> </form:option>
											<form:option value="1">Read </form:option>
											<form:option value="0">UnRead</form:option>
										</form:select> 
									</div>									
								</div>
							</div>
							</div>
						</div>
						
				</div>
			</div>
			<!-- button -->	
		<div class="text-center">
			<spring:message var="apply_now_renew" code="apply.now.renew"></spring:message>
			<sec:authorize access="!hasAnyRole('R010')">
			<input type="button" class="btn_review" onclick="onRenew()"name="renew" value="${apply_now_renew }" style="margin: 6px" />
			</sec:authorize>
			<input type="hidden" id="action" name="action" value="search" />
			<spring:message var="msg_buttonSearch" code="button.search"></spring:message>
			<sec:authorize access="!hasAnyRole('R010')">
			<input type="submit" class="btn_search_general" name="search"
				value="${msg_buttonSearch }" />
			</sec:authorize>
		</div>
		
		</div>
		</div>
		<!-- start table -->
		<div class="row-fluid">
			<div class="row-fluid row-align">
				<div class="accordion-inner">
					<div class="input-area">
						<div class="row-fluid row-align">
							<div class="span12 bg_round_table control-group">
								<!-- toolbar table -->
								<div class="title_table2 row-fluid">
									<div class="span6 title">
										<!-- 										<h3> -->
										<%-- 											<spring:message code="message.dashboard" /> --%>
										<!-- 										</h3> -->
											<h3>
										<spring:message var="search_title" code="msg.search.title"></spring:message>
											<c:out value="${search_title }"></c:out>
											</h3>
									</div>
									<div class="span6">
										<ext:pagination bean="${bean}" maxPage="5"
											formId="search_form"></ext:pagination>
									</div>
								</div>
								<!-- end toolbar table -->


								<table id="messageList"
									class="table table-striped table-hover table-bordered out-tbl"
									style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th style="border-top-left-radius: 0px;"><spring:message
													code="message.dashboard.stt" /></th>
											<th class="th_customer"><spring:message
													code="message.dashboard.customer" /></th>
											<th><spring:message code="message.dashboard.subject" />
											</th>
											<th><spring:message code="pendingPayment.import.date" />
											</th>
											<th><spring:message code="message.dashboard.message.type" /></th>
											<th><spring:message code="pendingPayment.import.date.searchIsRead" />
											</th>
											
										</tr>
									</thead>
									<tbody id="messageList">
										<c:forEach var="item" items="${bean.listResult}" varStatus="i">
											<tr>
												<td class="text_center">${(bean.page-1)*bean.limit+i.index+1}</td>

												<td>



													<div class="wrap_item_img">
														<c:if test="${empty item.customerImgPath }">
															<img src="${url}/static/images/avata_c5.gif"
																height="55px" width="55px" />
														</c:if>
														<c:if test="${not empty  item.customerImgPath}">
															<img
																src='${url}/ajax/download?fileName=${ item.customerImgPath}'
																height="55px" width="55px">
														</c:if>
													</div>

													<div class="wrap_info">


														<div>
															<span class="title"><spring:message
																	code="message.customer.name" /> : </span> <span class="name">
																<c:out value="${item.fullName}"/> &nbsp;</span>
														</div>
														<div>
															<span class="title"><spring:message
																	code="message.birthday" /> : </span> <span class="dob">
																<fmt:formatDate value="${item.dob}" pattern="${sessionScope.formatDate}" />&nbsp;
															</span>
														</div>
													<%-- 	<div>
															<span class="title"><spring:message
																	code="apply.now.id.card.no" /> : </span> <span class="phone">${item.cellphone}&nbsp;</span>
														</div> --%>
														<div>
															<span class="title"><spring:message
																	code="message.cell.phone" /> : </span> <span class="phone">${item.cellphone}&nbsp;</span>
														</div>


													</div>
												</td>
												<td ><span class="subject">${item.subject}</span> <span
													class="hide category">${item.category}</span> <span
													class="hide subcategory">${item.subcategory}</span></td>
												<td class="text_center createdDate"><fmt:formatDate
														value="${item.DATEIMPORT}"
														pattern="${sessionScope.formatDate} HH:mm" />
														</td>
												<td class="text_center type" data-type="${item.type}">												
													${listMess[item.type] }
												</td>
												<td class="text_center status"><c:choose>
														<c:when test="${item.isRead == 1}">
                                                        			Read
                                                    	</c:when>
                                                    	<c:when test="${item.isRead ==0}">
                                                        			UnRead
                                                    	</c:when>
													</c:choose></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>


								<div class="title_table2 row-fluid">
									<div class="span6 title">
										<div
											class="pagination1 pagination1-small pagination1-right pagination1-left-total-css">
											<spring:message var="msg_Found" code="page.Found" />
											<spring:message var="msg_records" code="page.records" />
											<spring:message var="msg_page" code="page.page" />

											&nbsp;<span class="text">${msg_Found }</span><span
												class="number">${bean.total} </span><span class="text">${msg_records }</span><span
												class="number"> ${bean.totalPage}</span> <span class="text">${msg_page }</span>
										</div>
									</div>
									<div class="span6">
										<ext:pagination bean="${bean}" maxPage="5"
											formId="search_form"></ext:pagination>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end table -->

	</form:form>
	<div id="detailMsg" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">Message Detail</h3>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<span style=" margin-right: 84px;">Subject</span> <span class="data_subject colorFont"> </span>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span6">
					<span style=" margin-right: 70px;">Sent Date</span> <span class="data_send_date colorFont">
					</span>
				</div>
				<div class="span6">
					<span style=" margin-right: 67px;">Message Type</span> <span class="data_message_type colorFont">
					</span>
				</div>
			</div>
			<!-- <div class="row-fluid">
				<div class="span6">
					<span style=" margin-right: 22px;">Message Category</span> <span
						class="data_msg_category colorFont"> </span>
				</div>
				<div class="span6">
					<span style=" margin-right: 22px;">Message Sub-Category</span> <span
						class="data_sub_category colorFont"> </span>
				</div>
			</div> -->
			<div style="color: #05552e;font-weight: bold;margin-top: 10px;">Detail</div>
			<hr style="border-color: #000 !important;border-top: 0px;margin-bottom: 0px !important;">
			<div class="row-fluid">
				<div class="span5">
					<div class="wrap_box_content detail_admin">
						<div class="wrap_item_img">
							<img id="myImg" src="${url}/static/images/hinhthe.jpg" alt="">
						</div>
						<div class="wrap_item_info">
							<div class="info_detail">
								<span class="info_detail_title">Name:</span> <span
									class="info_detail_name">Đỗ Duy Quân</span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Cell Phone:</span> <span
									class="info_detail_phone">0902261608</span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Email:</span> <span
									class="info_detai_email">quan@yahoo.com</span>
							</div>
							<div class="info_detail">
								<span class="info_detail_title">Id Card No:</span> <span
									class="info_detail_cardno">333112</span>
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
			
			<div class="row-fluid">
				<div class="span12" style="text-align: right;margin: 20px 20px 20px 0px;">
					<button class="btn_general" data-dismiss="modal"
					aria-hidden="true">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function back() {
		document.location.href = "";
	}
	function onRenew() {
		window.location.href = "${url}/message/dashboard/list";
	}

	$(document).ready(function($) {

	});

	$('.popup_detail').unbind('click')
			.click(
					function() {
						$('#detailMsg').modal('show');
						var subject = $(this).parents('tr').find('.subject')
								.text();
						var sentDate = $(this).parents('tr').find(
								'.createdDate').text();
						var mesCategory = $(this).parents('tr').find(
								'.category').text();
						var mesSubCategory = $(this).parents('tr').find(
								'.subcategory').text();
						var mesType = $(this).parents('tr').find('.type')
								.text();
						var typeId = $(this).parents('tr').find('.type').attr(
								'data-type');
						

						$('#detailMsg').find('.data_subject').text(subject);
						$('#detailMsg').find('.data_send_date').text(sentDate);
						$('#detailMsg').find('.data_message_type')
								.text(mesType);
						$('#detailMsg').find('.data_msg_category').text(
								mesCategory);
						$('#detailMsg').find('.data_sub_category').text(
								mesSubCategory);

						var parentMsgId = $(this).attr(
								'data-parentMsgId');
						var messageId = $(this).attr(
								'data-messageId');
						var customerId = $(this).attr('data-customerId');
						if (customerId != "") {
							var customer = message.getInfoCustomer(customerId);
							$('#detailMsg').find('.info_detail_name').text(
									customer.fullName);
							$('#detailMsg').find('.info_detail_phone').text(
									customer.cellPhone);
							$('#detailMsg').find('.info_detai_email').text(
									customer.emailAddress);
							$('#detailMsg').find('.info_detail_cardno').text(
									customer.idCardNumber);
							//var path=${url}+'/ajax/download?fileName='+customer.imagePath;
							var aa=customer.imagePath;
							document.getElementById("myImg").src ='${url}/ajax/download?fileName='+aa; 
							$('#detailMsg').find('.info_detail_cus').click(function(){
								window.location.href = '${url}/master_data/customer/view?id=' + customerId;
							})
							
						}

						var accountId = $(this).attr('data-accountId');

						var data = message.getLstMessageByCustomerId(
								customerId, messageId, typeId, parentMsgId, accountId);

						message.genderHtmlMessageChat(data);
					});

	function viewMessage(parentMsgId, messageId, contractMsgId,type) {
		$.ajax({
			url : '${url}/message/assign_accountId',
			type : 'POST',
			data : {
				'parentMsgId' : parentMsgId,
				'messageId' : messageId,
				'contractMsgId' : contractMsgId
			},
			success : function(data) {
				if (data === "true" || data === true) {
					window.location.href = '${url}/message/follow_up?type='+type+'&idmsg='+messageId;
				}
			}
		})

	}
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
			$
					.each(
							data,
							function(index, val) {
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
												+ '<div class="chat_detail_name">'+ val.managerName +'</div>'
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
												+ '<div class="chat_detail_name">'+ val.managerName +'</div>'
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