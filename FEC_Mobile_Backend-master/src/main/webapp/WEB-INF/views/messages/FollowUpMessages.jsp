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
<script src="${url }/static/plugins/jquery.alphanum.js"></script>


<style type="text/css">
	.content{
		-webkit-appearance: textarea;
	}

</style>


<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>My Follow-up Messages</h2>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid unit_bg_content">
	<div class="row-fluid">
		<div class="message_info"></div>
	</div>
	<div class="row-fluid follow_up_message">

		<div class="span4 follow_up_message_left">
			<ul id="myTab" class="nav nav-tabs">

			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade active in" id="home">
					<!-- content message -->
				</div>
				<div class="tab-pane fade" id="profile"></div>
			</div>
		</div>

		<div class="span8 follow_up_message_right">



			<div class="wrap_message_right">

				<div class="info_admin">

					<div class="info_img_admin">
						<img src="" alt="">
					</div>

					<div class="info_item_admin">
						</br> <span class="info_admin_name"></span> </br> <span
							class="info_admin_email"></span>
					</div>

					<div class="info_action_admin">
						<span class="cancelMsg"><img
							src="${url}/static/images/ms_bt_cancel.png" alt="" width="39"
							height="39"></span> <span class="reAssignMsg"><img
							src="${url}/static/images/ms_bt_re-assign.png" alt="" width="39"
							height="39"></span> <span class="closeMsg"><img
							src="${url}/static/images/ms_bt_close.png" alt="" width="39"
							height="39"></span>
					</div>
					</br>
					<div class="title_support_admin">
						<span class="text"></span>
					</div>

				</div>

				<div class="info_chat">
					<div class="wrap_txt_chat">
						<div class="wrap_upload" id="uploadContainer">
							<a id="uploadFile" title="${icon_add }" href="javascript:void(0)"><img
								src="${url}/static/images/bt_attach.png" alt="" width="39"
								height="39"></a>
						</div>
						<textarea class="txt_chat" maxlength="1500"></textarea>
						<sec:authorize access="!hasAnyRole('R010')">
				<input class="botton_send" type="button" name="send_message"
							value="..........">
				</sec:authorize>
						
					</div>
					
					<div id="consoleLog" class="alert alert-error fade in" style="display: none"></div>
					<div id="messagesConsoleLog2" class="message">  </div>
					
					<div id="fileList"></div>
					<div class="wrap_chat_detail">
						<!-- content chat -->
					</div>
				</div>
			</div>
		</div>



		<div id="cancelMsg" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Cancel Message</h3>
			</div>
			<div class="modal-body">
				<div class="messages"></div>
				<span>Reason To Cancel</span> <span class="required">*</span>
				<textarea id="tex1" class="txt_cancel" style="width: 84%"></textarea>
			</div>
			<div class="modal-footer">
				<button class="btn_cancel">Cancel Message</button>
				<button class="btn-primary1" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>

		<div id="reAssignMsg" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Re-assign Message</h3>
			</div>
			<div class="modal-body">
				<div class="messages"></div>
				<div>
					<span>Assign To</span> <span class="required">*</span> <select
						id="sel1" name="sl_account" style="margin-left: 65px">
						<option value="-1"><spring:message code="msg.choose"/></option>
						<c:forEach items="${lstAccount }" var="item">
							<option value="${item.id }">${item.fullName }</option>
						</c:forEach>
					</select>
				</div>
				<div>
					<span>Reason To Re-assign</span> <span class="required">*</span>
					<textarea id="tex2" class="txt_assign" style="width: 82%"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn_assign">Re-assign</button>
				<button class="btn-primary1" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>

		<div id="closeMsg" class="modal hide fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Close Message</h3>
			</div>
			<div class="modal-body">
				<div class="messages"></div>
				<span>Reason To Close</span> <span class="required">*</span>
				<textarea id="tex3" class="txt_close" style="width: 84%"></textarea>
			</div>
			<div class="modal-footer">
				<button class="btn_close">Close Message</button>
				<button class="btn-primary1" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>


	</div>
	<!-- end follow_up_message -->


</div>
<!-- End tab Contact US -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						
						$(".txt_chat").alphanum({
							allow : '!@#$%^&*()+=[]\\\';,/{}|":?~`.- _<>', // Allow extra characters
							disallow : '' 
						});
						
						
						$('#myTab a').click(function(e) {
							e.preventDefault();
							$(this).tab('show');
						});

						var lstMessageType = message.getListMessageType();
						message.genderHtmlMessageType(lstMessageType);
						message.loadContentRightFirst();
						var typeId = $('#myTab').find('li.active a').attr(
								'data-type');
						message.genderLstMsgCustomer(typeId);

						var start = setInterval(
								function() {
									var check = message.checkMessageNew();
									if (check != null && check.length > 0) {
										
										var cusId = $(
												'#myTabContent .wrap_box_content.active')
												.find(
														'input[name="customerId"]')
												.val();
										var tId = $(
												'#myTabContent .wrap_box_content.active')
												.find('input[name="typeId"]')
												.val();
										var parentMsgId = $(
												'#myTabContent .wrap_box_content.active')
												.find('input[name="parentMsgId"]')
												.val();

										var data1 = message
												.getLstMessageByCustomerId(
														cusId, tId, parentMsgId);

										message.changeStatusIsRead(parentMsgId);
										
										message.genderHtmlMessageChat(data1);
										$.each(check,function(index, val) {
											var ele = $(
													'.wrap_box_content')
													.find(
															'input[name="parentMsgId"][value="'
																	+ val.parentMsgId
																	+ '"]');
											$(ele)
													.parent()
													.find(
															'.info_number')
													.text(
															val.commentsCount);
											$(ele)
													.parent()
													.find(
															'.wrap_item_number')
													.removeClass(
															'hide');
											var wrap_content_new = $(ele).parent();
											var mytab_content =  $(ele).parent().parent();

											setTimeout(function(){ 
												$(mytab_content).prepend(wrap_content_new);												
											}, 2000);

										});
										$('#myTabContent .wrap_box_content.active').find('.wrap_item_number').addClass('hide');
									}
								
									
									
								}, 5000);

						$('input[name="send_message"]')
								.unbind('click')
								.click(
										function(e) {
											var content = $(
													'.info_chat .wrap_txt_chat .txt_chat')
													.val();
											var subject = $(
													'.info_admin .title_support_admin .text')
													.text();
											var customerId = $(this).attr(
													'data-customerId');
											var typeId = $(this).attr(
													'data-typeId');
											var parentMsgId = $(this).attr(
													'data-parentMsgId');
											var contractMsgId = $(this).attr(
													'data-contractMsgId');
											var param = {
												"parentMsgId" : parentMsgId,
												"customerId" : customerId,
												"type" : typeId,
												"category" : '',
												"subcategory" : '',
												"content" : content,
												"subject" : subject,
												"contractMsgId" : contractMsgId
											}
											if(content != ''){
												var rs = message.send(param);
												rs = rs.toString()
												if (rs === true || rs === 'true') {
													$('.wrap_txt_chat .txt_chat')
															.val('');
													e.preventDefault();
													$('.wrap_txt_chat .txt_chat')
															.focus();
													var data = message
															.getLstMessageByCustomerId(
																	customerId,
																	typeId, parentMsgId);
													message
															.genderHtmlMessageChat(data);
												}
											}
											

										})

						$('.wrap_txt_chat .txt_chat').keypress( function(e) {
							
							
											//CongDT, 20160624
											//Khi CS click button send thì mới gửi message
											
											
											
											return ;
											
											
											
											var regex = new RegExp("^[a-zA-Z0-9]+$");
										    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
										    if (!regex.test(key)) {
										       event.preventDefault();
										       alert("Nhap ky tu thuong thoi");
										       return false;
										    }
											
											
											
											
											

											if ((e.keyCode == 13 || e.which == 13) && !e.shiftKey) {
												var content = $(
														'.info_chat .wrap_txt_chat .txt_chat')
														.val();
												var subject = $(
														'.info_admin .title_support_admin .text')
														.text();
												var customerId = $(this)
														.next()
														.attr('data-customerId');
												var typeId = $(this).next()
														.attr('data-typeId');
												var parentMsgId = $(this)
														.next()
														.attr(
																'data-parentMsgId');
												var contractMsgId = $(this)
														.next()
														.attr(
																'data-contractMsgId');
												var param = {
													"parentMsgId" : parentMsgId,
													"customerId" : customerId,
													"type" : typeId,
													"category" : '',
													"subcategory" : '',
													"content" : content,
													"subject" : subject,
													"contractMsgId" : contractMsgId
												}
												if(content == ''){
													e.preventDefault();
													$(this).focus();
												}
												if(content != ''){
													var rs = message.send(param);
													rs = rs.toString()
													if (rs === true
															|| rs === 'true') {
														$(this).val('');
														e.preventDefault();
														$(this).focus();
														var data = message
																.getLstMessageByCustomerId(
																		customerId,
																		typeId, parentMsgId);
														message
																.genderHtmlMessageChat(data);
													}
												}
												
											}
										})

						$('.info_action_admin .cancelMsg').unbind('click')
								.click(
										function() {
											$('#cancelMsg').find('.txt_cancel')
													.val('');
											$('#cancelMsg').modal('show');
										})

						$('.info_action_admin .reAssignMsg').unbind('click')
								.click(
										function() {
											$('#reAssignMsg').find(
													'.txt_assign').val('');
											$('#reAssignMsg').modal('show');
										})

						$('.info_action_admin .closeMsg').unbind('click')
								.click(function() {
									$('#closeMsg').find('.txt_close').val('');
									$('#closeMsg').modal('show');
								})

						$('#cancelMsg')
								.find('.btn_cancel')
								.unbind('click')
								.click(
										function() {
											var customerId = $(
													'input[name="send_message"]')
													.attr('data-customerId');
											var parentMsgId = $(
													'input[name="send_message"]')
													.attr('data-parentMsgId');
											var reason = $('#cancelMsg').find(
													'.txt_cancel').val();
											var typeId = $("#myTab").find(
													'li.active').find('a')
													.attr('data-type');
											if (reason == "") {
												$('#cancelMsg').find('.txt_cancel').addClass('errorfeborder');
											} else {
												$('#cancelMsg').find('.txt_cancel').removeClass('errorfeborder');
												var rs = message.changeStatusMsg(customerId, reason, '', 1,
																				 typeId, parentMsgId);
												if (rs === true
														|| rs === 'true') {
													message.actionMessage();
													$('#cancelMsg').modal(
															'hide');
													$('.message_info').html(core.message("success" , "Message was canceled."));
												}
											}

										})

						$('#reAssignMsg')
								.find('.btn_assign')
								.unbind('click')
								.click(
										function() {
											var customerId = $(
													'input[name="send_message"]')
													.attr('data-customerId');
											var parentMsgId = $(
													'input[name="send_message"]')
													.attr('data-parentMsgId');
											var reason = $('#reAssignMsg')
													.find('.txt_assign').val();
											var typeId = $("#myTab").find(
													'li.active').find('a')
													.attr('data-type');
											var accountId = $('#reAssignMsg')
													.find(
															'select[name="sl_account"]')
													.val();
											var name = $('#reAssignMsg')
													.find('select[name="sl_account"]')
													.find('option:selected')
													.text();
											var checkAC = true, checkRe = true;
											if (accountId == -1) {
												$('#reAssignMsg').find('#sel1').addClass('errorfeborder');
												checkAC = false;
											} else {
												$('#reAssignMsg').find('#sel1').removeClass('errorfeborder');
												checkAC = true;
											}

											if (reason == "") {
												$('#reAssignMsg').find('.txt_assign').addClass('errorfeborder');
												checkRe = false;
											} else {
												$('#reAssignMsg').find('.txt_assign').removeClass('errorfeborder');
												checkRe = true;

											}
											if (checkAC && checkRe) {

												var rs = message
														.changeStatusMsg(
																customerId,
																reason,
																accountId, 2,
																typeId, parentMsgId);
												if (rs === true
														|| rs === 'true') {
													message.actionMessage();
													$('#reAssignMsg').modal(
															'hide');
													$('.message_info').html(core.message("success" , "Message was reassigned to "+ name +"."));
												}
											}
										})

						$('#closeMsg')
								.find('.btn_close')
								.unbind('click')
								.click(
										function() {
											var customerId = $(
													'input[name="send_message"]')
													.attr('data-customerId');
											var parentMsgId = $(
													'input[name="send_message"]')
													.attr('data-parentMsgId');
											var reason = $('#closeMsg').find(
													'.txt_close').val();
											var typeId = $("#myTab").find(
													'li.active').find('a')
													.attr('data-type');
											if (reason == "") {
												$('#closeMsg').find('.txt_close').addClass('errorfeborder');
											} else {
												$('#closeMsg').find('.txt_close').removeClass('errorfeborder');
												var rs = message
														.changeStatusMsg(
																customerId,
																reason, '', 3,
																typeId, parentMsgId);
												if (rs === true
														|| rs === 'true') {
													message.actionMessage();
													$('#closeMsg')
															.modal('hide');
													$('.message_info').html(core.message("success" , "Message was closed."));
												}
											}

										})

						//init image uploader
						var uploadUrl = "${url}/ajax/uploadMsgAttachment";
						var uploadFile = 'uploadFile';
						var uploadContainer = 'uploadContainer';
						var maxFileSize = '5mb';
						var mimeTypes = [
								{
									title : "Image files",
									extensions : "jpg,jpeg,gif,png"
								}
								
								// , { title : "Zip files", extensions : "zip" }, 
								// { title : "Media files", extensions : "mpeg4,mob,3gpp,avi,wmv,mp3,m4a,ogg,wav" }, 
								// { title : "Text files", extensions : "doc,docx" }, 
								// { title : "PDF files", extensions : "pdf" }, 
								// { title : "Spreadsheet files", extensions : "xls,xlsx" }
								
								];

						var resize = {

						};
						var fileList = 'fileList';
						var consoleLog = 'consoleLog';
						var imageFileUploaded = function(up, file, info) {
							
							var obj = jQuery.parseJSON(info.response);
							if(obj.status != "error"){
								$(".txt_chat").val(cutString(obj.retObj));
							}else{
								$("#messagesConsoleLog2").html(obj.message);
								window.setTimeout(function () {
								  $("#messagesConsoleLog2").html("");
								}, 6000);
							}
						};

						var imageUploadComplete = function(up, files) {
							var fileName = $(".txt_chat").val();
							var type = files[files.length - 1].type;
							var sType = type.substring(0, type.indexOf("/"));
							var content = '';
							if (sType == 'image') {
								content = '<img src="${url}/ajax/download?fileName='
										+ fileName + '" alt="">'
							} else {
								content = '<a href="${url}/ajax/download?fileName='
										+ fileName + '">' + fileName + '</a>'
							}
							var subject = $(
									'.info_admin .title_support_admin .text')
									.text();
							var customerId = $('input[name="send_message"]')
									.attr('data-customerId');
							var typeId = $('input[name="send_message"]').attr(
									'data-typeId');
							var parentMsgId = $('input[name="send_message"]')
									.attr('data-parentMsgId');
							var param = {
								"parentMsgId" : parentMsgId,
								"customerId" : customerId,
								"type" : typeId,
								"category" : 1,
								"subcategory" : 1,
								"content" : fileName,
								"subject" : subject
							}
							var rs = message.send(param);
							rs = rs.toString()
							if (rs === true || rs === 'true') {
								$('.wrap_txt_chat .txt_chat').val('');
								$('.wrap_txt_chat .txt_chat').focus();
								var data = message.getLstMessageByCustomerId(
										customerId, typeId, parentMsgId);
								message.genderHtmlMessageChat(data);
							}

							$("#" + consoleLog).hide();
							$("#" + fileList).hide();
						};
						core.InitPlupload(uploadFile, uploadContainer,
								uploadUrl, false, maxFileSize, mimeTypes,
								fileList, consoleLog, imageFileUploaded,
								imageUploadComplete, '${url}', resize);

						function deleteImage() {
							$("#tagImage").val("");
							$("#img_banner").attr("src", "");
							$("#img_banner").addClass('hide');
						}

						$('#myTab li')
								.unbind('click')
								.click(
										function() {
											var typeId = $(this).find('a')
													.attr('data-type');
											message
													.genderLstMsgCustomer(typeId);
											message.loadContentRightFirst();

											$('#myTabContent .wrap_box_content')
													.unbind('click')
													.bind(
															'click',
															function(event) {
																var ele = $(this);
																setTimeout(function(){ 
																	$(ele).parent().prepend(ele);
																	$('.follow_up_message_left').scrollTop(0);
																}, 2000);
																$(
																		'#myTabContent .wrap_box_content')
																		.removeClass(
																				'active');
																$(this)
																		.addClass(
																				'active');

																var cusId = $(
																		this)
																		.find(
																				'input[name="customerId"]')
																		.val();
																var tId = $(
																		this)
																		.find(
																				'input[name="typeId"]')
																		.val();
																var parentMsgId = $(
																		this)
																		.find(
																				'input[name="parentMsgId"]')
																		.val();
																if (cusId != undefined
																		&& tId != undefined) {
																	message
																			.changeStatusIsRead(parentMsgId);
																}
																$(this)
																		.find(
																				'.wrap_item_number')
																		.addClass(
																				'hide');

																var data1 = message
																		.getLstMessageByCustomerId(
																				cusId,
																				tId, parentMsgId);
																message
																		.genderHtmlMessageRight(
																				data1,
																				cusId,
																				tId);
																message
																		.genderHtmlMessageChat(data1);

															});
										})

					});

	var message = {

		send : function(param) {

			var rs;
			$.ajax({
				url : '${url}/message/saveMsg',
				type : 'POST',
				contentType : 'application/json',
				processData : false,
				data : JSON.stringify(param),
				async : false,
				success : function(data) {
					rs = data;
				}
			})
			return rs;
		},

		actionMessage : function() {
			var typeId = $('#myTab').find('li.active a').attr('data-type');
			message.genderLstMsgCustomer(typeId);
			// get chat first person
			var box = $('#myTabContent').find('.wrap_box_content:first-child');
			if (box.length >= 1) {

				// get detail message chat
				$('.wrap_message_right').removeClass('hide');
				var tId = $(box).find('input[name="typeId"]').val();
				var cusId = $(box).find('input[name="customerId"]').val();
				var parentMsgId = $(box).find('input[name="parentMsgId"]').val();

				var data = message.getLstMessageByCustomerId(cusId, tId, parentMsgId);
				message.genderHtmlMessageRight(data, cusId, tId);
				message.genderHtmlMessageChat(data);
			} else {
				$('.wrap_message_right').addClass('hide');
			}
		},
		getListMessageCustomer : function(typeId) {
			var rs;
			$.ajax({
				url : '${url}/message/getListMessageCustomer',
				type : 'GET',
				async : false,
				data : {
					'typeId' : typeId
				},
				success : function(data) {
					rs = data;
				}
			})
			return rs;
		},
		getListMessageType : function() {
			var rs;
			$.ajax({
				url : '${url}/message/getLstMessageType',
				type : 'GET',
				async : false,
				success : function(data) {
					rs = data;
				}
			})
			return rs;
		},

		genderHtmlMessageType : function(lstMessageType) {
			var hideType = '';
			var htmlMessageCustomer = '';
			var html = '';
			var html1 = '';
			var classA = '';
			$
					.each(
							lstMessageType,
							function(index, val) {
								var typeId = val;							
								typeName = '';
								
							 $.ajax({
									url : '${url}/message/getOneMessageType',
									type : 'GET',
									async: false,
									data : {
										val :val
									}, success : function(data) {	
										typeName = data;
									}
								});
						
								/*if (val == 1) {
									typeName = '${allContractCCMTypes["1"]}';
								} else if (val == 2) {
									typeName = '${allContractCCMTypes["2"]}';
								} else if (val == 3) {
									typeName = '${allContractCCMTypes["3"]}';
								}
								else if (val == 4) {
									typeName = '${allContractCCMTypes["4"]}';
								}
								else if (val == 5) {
									typeName = '${allContractCCMTypes["5"]}';
								}
								else if (val == 6) {
									typeName = '${allContractCCMTypes["6"]}';
								}
								else if (val == 7) {
									typeName = '${allContractCCMTypes["7"]}';
								}
								else if (val == 8) {
									typeName = '${allContractCCMTypes["8"]}';
								}			*/					
								
								if (index == 0) {
									classA = 'active';
								} else {
									classA = '';
								}
								html += '<li class="'+ classA +'"><a href="#'+ val +'" data-type='+ typeId +' data-toggle="tab">'
										+ typeName + '</a></li>';
								html1 += '<div class="tab-pane fade '+ classA +' in" id="'+ val +'">';
								var listMessageCustomer = message
										.getListMessageCustomer(typeId);
								if(listMessageCustomer == null || listMessageCustomer == ''){
									 hideType = typeId;
								}
								$
										.each(
												listMessageCustomer,
												function(i, v) {

													var email = "";
													if (v.emailAddress != null) {
														email = v.emailAddress;
													}
													var urlImage = "${url}/ajax/download?fileName="
															+ v.imagePath;
													if (v.imagePath == null) {
														urlImage = "${url}/static/images/avata_c5.gif"
													}

													html1 += '<div class="wrap_box_content '
															+ (i == 0 ? "active"
																	: "")
															+ '">'
															+ '<div class="wrap_item_img"><img src="'+urlImage+'" alt=""></div>'
															+ '<div class="wrap_item_info">'
															+ '<div class="info_detail">'
															+ '<span class="info_detail1">Subject:</span>'
															+ '<span class="info_detail2">'
															+ escapeXml(v.subject)
															+ '</span>'
															+ '</div>'
															+ '<div class="info_detail">'
															+ '<span class="info_detail1">Name:</span>'
															+ '<span class="info_detail2">'
															+ escapeXml(v.fullName)
															+ '</span>'
															+ '</div>'
															+ '<div class="info_detail">'
															+ '<span class="info_detail1">Cell Phone:</span>'
															+ '<span class="info_detail2">'
															+ v.cellPhone
															+ '</span>'
															+ '</div>'
															+ '<div class="info_detail">'
															+ '<span class="info_detail1">Email:</span>'
															+ '<span class="info_detail2">'
															+ email
															+ '</span>'
															+ '</div>'
															+ '<div class="info_detail">'
															+ '<span class="info_detail1">Id Card No:</span>'
															+ '<span class="info_detail2">'
															+ v.idCardNumber
															+ '</span>'
															+ '</div>'
															+ '</div>'
															+ '<div class="wrap_item_number hide">'
																+ '<span class="info_number"></span>'
															+ '</div>'
															+ '<input type="hidden" name="customerId" value="'+ v.customerId +'">'
															+ '<input type="hidden" name="parentMsgId" value="'+ v.parentMsgId +'">'
															+ '<input type="hidden" name="typeId" value="'+ v.msgType +'">'
															+ '</div>'
												});
								html1 += '</div>';
							});
			$('.follow_up_message_left #myTab').html('').html(html);
			$('.follow_up_message_left #myTabContent').html('').html(html1);
			$('.follow_up_message_left #myTab').find('a[data-type="'+ hideType +'"]').hide();	
			
			setTimeout(function(){
				var paramType =  '${param["type"]}';	
				var idmsg =  '${param["idmsg"]}';					
				$('.follow_up_message_left #myTab').find('a[data-type="'+ paramType +'"]').click();
				$("#myTabContent").find("input[value='"+idmsg+"']").parents(".wrap_box_content:first").click();
			},200);
		
		},

		genderLstMsgCustomer : function(typeId) {
			var html1 = '';
			var listMessageCustomer = message.getListMessageCustomer(typeId);
			$
					.each(
							listMessageCustomer,
							function(i, v) {

								var email = "";
								if (v.emailAddress != null) {
									email = v.emailAddress;
								}
								var urlImage = "${url}/ajax/download?fileName="
										+ v.imagePath;
								if (v.imagePath == null) {
									urlImage = "${url}/static/images/avata_c5.gif"
								}
								
								var subject = "";
								if( v.subject !== null && v.subject !== undefined ){
									subject = v.subject.substring(0,25);
									if(v.subject.length > 25){
									  subject = subject + "...";
									}
								}

								html1 += '<div class="wrap_box_content '
										+ (i == 0 ? "active" : "")
										+ '">'
										+ '<div class="wrap_item_img"><img src="'+urlImage+'" alt=""></div>'
										+ '<div class="wrap_item_info">'
										+ '<div class="info_detail">'
										+ '<span class="info_detail1">Subject:</span>'
										+ '<span class="info_detail2">'
										+ escapeXml(subject)
										+ '</span>'
										+ '</div>'
										+ '<div class="info_detail">'
										+ '<span class="info_detail1">Name:</span>'
										+ '<span class="info_detail2">'
										+ escapeXml(v.fullName)
										+ '</span>'
										+ '</div>'
										+ '<div class="info_detail">'
										+ '<span class="info_detail1">Cell Phone:</span>'
										+ '<span class="info_detail2">'
										+ v.cellPhone
										+ '</span>'
										+ '</div>'
										+ '<div class="info_detail">'
										+ '<span class="info_detail1">Email:</span>'
										+ '<span class="info_detail2">'
										+ email
										+ '</span>'
										+ '</div>'
										+ '<div class="info_detail">'
										+ '<span class="info_detail1">Id Card No:</span>'
										+ '<span class="info_detail2">'
										+ v.idCardNumber
										+ '</span>'
										+ '</div>'
										+ '</div>'
										+ '<div class="wrap_item_number hide">'
											+ '<span class="info_number"></span>'
										+ '</div>'
										+ '<input type="hidden" name="customerId" value="'+ v.customerId +'">'
										+ '<input type="hidden" name="parentMsgId" value="'+ v.parentMsgId +'">'
										+ '<input type="hidden" name="typeId" value="'+ v.msgType +'">'
										+ '</div>'
							});
			html1 += '</div>';
			$('.follow_up_message_left #myTabContent').html('').html(html1);

			$('#myTabContent .wrap_box_content').unbind('click').bind(
					'click',
					function(event) {
						var ele = $(this);
						setTimeout(function(){ 
							$(ele).parent().prepend(ele);
							$('.follow_up_message_left').scrollTop(0);
						}, 2000);

						$('#myTabContent .wrap_box_content').removeClass(
								'active');
						$(this).addClass('active');

						var cusId = $(this).find('input[name="customerId"]')
								.val();
						var tId = $(this).find('input[name="typeId"]').val();
						var parentMsgId = $(this).find('input[name="parentMsgId"]').val();
						if (cusId != undefined && tId != undefined) {
							message.changeStatusIsRead(parentMsgId);
						}

						$(this).find('.wrap_item_number').addClass('hide');

						var data1 = message.getLstMessageByCustomerId(cusId,
								tId, parentMsgId);
						message.genderHtmlMessageRight(data1, cusId, tId);
						message.genderHtmlMessageChat(data1);

					});
		},

		loadContentRightFirst : function() {
			var customerId = $('#myTabContent .wrap_box_content.active').find(
					'input[name="customerId"]').val();
			var typeId = $('#myTabContent .wrap_box_content.active').find(
					'input[name="typeId"]').val();
			var parentMsgId = $('#myTabContent .wrap_box_content.active').find(
					'input[name="parentMsgId"]').val();

			if (customerId != undefined && typeId != undefined) {
				$('.wrap_message_right').removeClass('hide');
				message.changeStatusIsRead(parentMsgId);
				var data = message
						.getLstMessageByCustomerId(customerId, typeId, parentMsgId);
				message.genderHtmlMessageRight(data, customerId, typeId);
				message.genderHtmlMessageChat(data);
			} else {
				$('.wrap_message_right').addClass('hide');
			}

			$('#myTab').find('li').removeClass('active');
			$('#myTab').find('li').find('a[data-type="' + typeId + '"]')
					.parent().addClass('active');
			$('#myTabContent .wrap_box_content.active').find(
					'.wrap_item_number').addClass('hide');

		},

		getLstMessageByCustomerId : function(customerId, typeId, parentMsgId) {
			var rs;
			$.ajax({
				url : '${url}/message/getLstMessageByCustomerId',
				type : 'GET',
				async : false,
				data : {
					customerId : customerId,
					typeId : typeId,
					parentMsgId : parentMsgId,
					dashboard : false
				},
				success : function(data) {
					rs = data;
				}
			})
			return rs;
		},

		genderHtmlMessageRight : function(data, customerId, typeId) {
			if(typeId == 3){
				$('.wrap_message_right .info_action_admin .closeMsg').addClass('hide');
			}else{
				$('.wrap_message_right .info_action_admin .closeMsg').removeClass('hide');
			}

			if (data != '' || data != null) {

				var title = "";
				if (data[0].subject != null) {
					title = data[0].subject;
				}
				$('.wrap_message_right').find('.info_admin')
						.removeClass('hide');

				var urlImage = "${url}/ajax/download?fileName="
						+ data[0].customerImgPath;
				if (data[0].customerImgPath == null) {
					urlImage = "${url}/static/images/avata_c5.gif"
				}

				$('.info_admin .info_img_admin').find('img').attr('src',
						urlImage);
				$('.info_admin .info_item_admin')
						.find('.info_admin_name')
						.text(
								data[0].customerName != null ? data[0].customerName
										: "");
				$('.info_admin .info_item_admin')
						.find('.info_admin_email')
						.text(
								data[0].emailAddress != null ? data[0].emailAddress
										: "");
				$('.info_admin .title_support_admin').find('.text').text(title);
				$('.info_chat').find('input[name="send_message"]').attr(
						'data-customerId', customerId);
				$('.info_chat').find('input[name="send_message"]').attr(
						'data-typeId', typeId);
				$('.info_chat').find('input[name="send_message"]').attr(
						'data-parentMsgId', data[0].parentMsgId);
				$('.info_chat').find('input[name="send_message"]').attr('data-messageId', data[0].messageID);
				$('.info_chat').find('input[name="send_message"]').attr('data-contractMsgId', data[0].contractMsgId);

				$('.wrap_message_right').attr(
						'data-parentMsgId', data[0].parentMsgId);
				$('.wrap_message_right').attr('data-messageId', data[0].messageID);

			}

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
												+ val.content.replace(/\n/g,"<br>")
												+ '</div>'
											+ '</div>'
											+ '<div class="chat_detail_hour">'
												+ val.creDate
											+ '</div>' 
											+ fileName
											+ '</div>';
											/* console.log(val.content); */
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
												+ val.content.replace(/\n/g,"<br>")
												+ '</div>'
											+ '</div>' 
											+ fileName
											+ '</div>';
											/* console.log(val.content);  */
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

		changeStatusIsRead : function(parentMsgId) {
			$.ajax({
				url : '${url}/message/changeStatusIsRead',
				type : 'GET',
				data : {
					parentMsgId : parentMsgId,
				},
				success : function(data) {

				}
			})
		},

		checkMessageNew : function() {
			var rs;
			$.ajax({
				url : '${url}/message/checkMessageNew',
				type : 'GET',
				async : true,
				success : function(data) {
					rs = data;
				}
			})
			return rs;

		},

		changeStatusMsg : function(customerId, reason, accountId, status, typeId, parentMsgId){
			// status 1 : cancel 2 : re-assign 3 : close 
			//alert(customerId+" "+reason+" "+accountId+" "+status+" "+typeId);
			var rs;
			$.ajax({
				url : '${url}/message/changeStatusMsg',
				type : 'POST',
				async : false,
				data : {
					status : status,
					customerId : customerId,
					reason : reason,
					accountId : accountId,
					typeId : typeId,
					parentMsgId : parentMsgId
				},
				success : function(data) {
					rs = data;
				}
			})
			return rs;
		},

		stopInterVal : function(func) {
			clearInterval(func);
		},

	};
	
	function escapeXml(someHtmlString){
	  return $("<div>").text(someHtmlString).html();
	}
	
	
</script>
<style type="text/css" media="screen">
.btn_cancel, .btn_assign, .btn_close {
	display: inline-block;
	padding: 4px 12px;
	margin-bottom: 0;
	font-size: 12px;
	line-height: 20px;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',
		endColorstr='#ffe6e6e6', GradientType=0);
	border-color: #e6e6e6 #e6e6e6 #bfbfbf;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	filter: progid:DXImageTransform.Microsoft.gradient(enabled= false);
	border: 1px solid #cccccc;
	border-bottom-color: #b3b3b3;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px
		rgba(0, 0, 0, .05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px
		rgba(0, 0, 0, .05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px
		rgba(0, 0, 0, .05);
	color: #FFFFFF !important;
	background-color: #ff0000 !important;
}

.btn-primary1 {
	display: inline-block;
	padding: 4px 12px;
	margin-bottom: 0;
	font-size: 12px;
	line-height: 20px;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
	text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff',
		endColorstr='#ffe6e6e6', GradientType=0);
	border-color: #e6e6e6 #e6e6e6 #bfbfbf;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	filter: progid:DXImageTransform.Microsoft.gradient(enabled= false);
	border: 1px solid #cccccc;
	border-bottom-color: #b3b3b3;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px
		rgba(0, 0, 0, .05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px
		rgba(0, 0, 0, .05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, .2), 0 1px 2px
		rgba(0, 0, 0, .05);
	color: #FFFFFF !important;
	background-color: #ff0000 !important;
}

textarea {
	margin-bottom: 0px !important;
}
</style>