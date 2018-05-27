/*!
 * Một số function dùng chung khi sử dụng ajax
 * Sử dụng cho trường hợp @ResponseBody ReturnObject
 * Ver : 0.1
 *
 * @author CongDT
 * @since Mar 11, 2015 1:19:04 PM
 *
 */
var ajaxUtil = {};
ajaxUtil.isSuccess = function (resp) {
	if (resp && resp.status == "success") {
		return true;
	} else {
		return false;
	}
}

ajaxUtil.isInfo = function (resp) {
	if (resp && resp.status == "info") {
		return true;
	} else {
		return false;
	}
}

ajaxUtil.showMsg = function (resp) {
	if(resp && resp.status){
		$(".messages:first").append('<div class="alert alert-'
				+resp.status
				+ ' fade in">'
				+ '<button class="close" data-dismiss="alert" type="button">×</button> '
				+resp.message + '</div>');
	}
}
function showErrMsg(thisMsg) {
	$(".messages:first").append('<div class="alert alert-error fade in">'
		 + '<button class="close" data-dismiss="alert" type="button">×</button> '
		+thisMsg + '</div>');
}
function showOkMsg(thisMsg) {
	$(".messages:first").append('<div class="alert alert-success fade in">'
		 + '<button class="close" data-dismiss="alert" type="button">×</button> '
		+thisMsg + '</div>');
}

$(document).ajaxStart(function () {
	if($("#wrapDiv").length){
		ajaxUtil.objMaskAjax = $("#wrapDiv");
	}else{
		ajaxUtil.objMaskAjax = $("#eBody");
	}
	ajaxUtil.objMaskAjax.mask('Processing...');
});
$(document).ajaxComplete(function () {
	ajaxUtil.objMaskAjax.unmask();
});
$(document).ajaxSuccess(function (event, xhr, settings) {});
$(document).ajaxError(function (resp, ex) {});
