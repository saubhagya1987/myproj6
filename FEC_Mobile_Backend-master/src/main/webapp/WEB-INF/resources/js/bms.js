var hiddenValToForm = function (formId, mapVal, override){
	for (var key in mapVal) {
		var hiddenField =$('#'+formId+" > input:hidden[name="+key+"]");
		if(hiddenField.size() ==0){
			$('<input>').attr({
			    type: 'hidden',
			    name: key,
			    value:mapVal[key]
			}).appendTo('#'+formId);
		}
		else {
			hiddenField.val(mapVal[key]);
		}
	}
};
function submitAndSetHiddenVal (formId, mapVal, override){
	hiddenValToForm(formId, mapVal, override);
	$("#"+formId).submit();
}

function submitAndSetHiddenVal_Ajax (formId, mapVal, override, ajaxUrl , divId){
	hiddenValToForm(formId, mapVal, override);
	$.ajax({
		url : ajaxUrl,
		data : $('#'+formId).serialize(),
		dataType : 'text',
		type : 'POST',
		success : function(result) {
			$('#'+divId).html(result);	
		}
	});
}

function addMessageToBoard(board,status,message){
	var divMessage=$('<div>').addClass('alert').addClass('alert-'+status).addClass('fade in');
	var button=$("<button>").attr({
		'class':'close',
		'data-dismiss':'alert',
		type:'button'
	});
	button.html('x');
	button.appendTo(divMessage);
	divMessage.appendTo(board);
	divMessage.append(message);
	$(divMessage).alert();
}
function confirmMessage(msg,callback){
	bootbox.setLocale(eprocurementLocale);
	return bootbox.confirm(msg, callback); 
}
function alertMessage(msg,callback){
	bootbox.setLocale(eprocurementLocale);
	return bootbox.alert(msg, callback); 
}
function reset(){
	$("input[type=text],textarea").val("");
}
function accordion(selector){
	$('#'+selector).on('hidden', function (parent ) {
		var icon=$(parent.target).prev();
		icon =$(icon).find('.bms-icon-accordion');
		$(icon).attr('class','bms-icon-accordion bms-icon-accordion-down');
	});
	$('#'+selector).on('show', function (parent) {
		var icon=$(parent.target).prev();
		icon =$(icon).find('.bms-icon-accordion');
		icon.attr('class','bms-icon-accordion bms-icon-accordion-up');
	});
}
function log(data){
	try {
		console.log(data);
	}catch(e){
		
	}
}


//init Plupload
function InitPlupload(browse_button, container, uploadUrl, multi_selection, max_file_size, mime_types, filelist, imageConsole, FileUploaded, UploadComplete, url) {
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',

		browse_button : browse_button, // you can pass in id...
		container : document.getElementById(container), // ... or DOM Element
		// itself

		url : uploadUrl,

		// Resize images on client-side if we can
		resize : {},

		multi_selection : multi_selection,

		filters : {
			max_file_size : max_file_size,
			mime_types : mime_types
		},

		// Flash settings
		flash_swf_url : url + '/static/js/plupload-2.1.2/Moxie.swf',

		// Silverlight settings
		silverlight_xap_url : url + '/static/js/plupload-2.1.2/Moxie.xap',

		init : {
			PostInit : function() {
				document.getElementById(filelist).innerHTML = '';
			},

			FilesAdded : function(up, files) {
				plupload.each(files, function(file) {
					document.getElementById(filelist).innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
				});

				$("#" + filelist).show();
				uploader.start(); // auto start when FilesAdded
			},

			UploadProgress : function(up, file) {
				document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			},

			Error : function(up, err) {
				document.getElementById(imageConsole).innerHTML =  err.message;
				$("#" + imageConsole).show();
			},

			FileUploaded : FileUploaded,
			UploadComplete : UploadComplete
		}
	});

	uploader.init();
}

function findOneItem(lst, item) {
	// neu tag co gia tri thi cong them vao
	if (lst != "") {
		var arr = lst.split(",");
		var found = false;
		for ( var j = 0; j < arr.length; ++j) {
			if (arr[j] == item) {
				found = true;
			}
		}
		// neu obj.responses[i] chua co trong lst
		if (found == false) {
			lst += "," + item; // cong item vao cuoi lst
		}
	} else { // neu tag chua co gia tri thi gan = responses
		lst = item;
	}

	return lst;
}

function loadVideoPLayer(video, url) {

	$("#showImage").html("");
	ajaxUrl = url + '/ajax/showVideo?fileName=' + video;
	$.ajax({
		url : ajaxUrl,
		cache : false,
		success : function(result) {
			$("#videoContent").html(result);
		}
	});
}

function loadSliderImage(lstImg, url, flag) {	
	ajaxUrl = url + '/ajax/showImage?flag=' + flag + '&&listFile=' + lstImg;
	$.ajax({
		url : ajaxUrl,
		cache : false,
		success : function(result) {
			$("#showImage").html(result);

		}
	});
}

function cutString(str) {
	if (str.length > 2) {
		if (str.indexOf('["') == 0) {
			str = str.substring(2, str.length);
		}
		if (str.indexOf('"]') == str.length - 2) {
			str = str.substring(0, str.length - 2);
		}
	}

	return str;
}

function changeLang(url, lang) {
	var pathname = $(location).attr('pathname');
	var param = $(location).attr('search');
	
	if(param.length > 0){
		if(param[0] == "?"){
			param = param.substring(1, param.length);
 		}
		if(param.lastIndexOf("lang=") != -1){
			param = param.substring(0, param.lastIndexOf("lang="));
		}

		while(param.lastIndexOf("&") != -1){
			param = param.replace('&',',');
		};
 		
	}
	window.location = url+"/goToUrl?lang=" + lang + "&url=" + pathname + "&param=" + param;
	
}

function printFrame(id) {
    /*var frm = $(id).get(0).contentWindow;
    frm.focus();// focus on contentWindow is needed on some ie versions
    frm.print();*/
	var contents = $(id).html();
    var frame1 = $('<iframe />');
    frame1[0].name = "frame1";
    frame1.css({ "position": "absolute", "top": "-1000000px" });
    $("body").append(frame1);
    var frameDoc = frame1[0].contentWindow ? frame1[0].contentWindow : frame1[0].contentDocument.document ? frame1[0].contentDocument.document : frame1[0].contentDocument;
    frameDoc.document.open();
    //Create a new HTML document.
    frameDoc.document.write('<html><head><title>Report</title>');
    frameDoc.document.write('</head><body>');
    //Append the DIV contents.
    frameDoc.document.write(contents);
    frameDoc.document.write('</body></html>');
    frameDoc.document.close();
    
    // Remove Freeze panes
    $(frame1.contents().find('.fht-thead')).remove(); 
    $(frame1.contents().find('.fht-tbody table')).css("margin-top", "0px");
    setTimeout(function () {
        window.frames["frame1"].focus();
        window.frames["frame1"].print();
        frame1.remove();
    }, 500);
    return false;
}

function removeAjaxRecord(elm, url, elmMsgSuccess, elmMsgFail){
	$.ajax({
		url : url,
		type : 'GET',
		cache : false,				
		success : function(result) {					
			$(".alert").hide();		
			if(result){
				$(elmMsgSuccess).show();
				$(elmMsgSuccess).find(".alert").show();				
				$(elm).parents("tr:first").remove();			
			}else{				
				$(elmMsgFail).show();	
				$(elmMsgFail).find(".alert").show();
			}
			$(document).scrollTop($(".alert").offset().top - 100);
		}
	});		
}