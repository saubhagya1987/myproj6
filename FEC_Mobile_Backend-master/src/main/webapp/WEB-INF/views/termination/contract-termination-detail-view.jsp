<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="${url}/static/css/datepicker.css">
		
	<link rel="stylesheet" type="text/css" href="${url}/static/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.loadmask.css">	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/vccb_style.css">
	<link rel="stylesheet" type="text/css" href="${url}/static/css/non-responsive.css">
	<link rel="stylesheet" type="text/css" href="${url}/static/css/card_vccb.css">

		
	<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.loadmask.min.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/jquery-ui-1.9.1.js.download"></script> 
    <script type="text/javascript" src="${url}/static/js/bootstrap.js.download"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.vi.min.js.download" charset="UTF-8"></script>
    <script type="text/javascript" src="${url}/static/js/bootbox.js.download"></script>
    <script type="text/javascript" src="${url}/static/js/jshashtable-3.0.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.numberformatter.js.download"></script>

	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.horizontal.scroll.css"> 
	<script type="text/javascript" src="${url}/static/js/jquery.horizontal.scroll.js.download"></script> 

 	<link rel="stylesheet" type="text/css" href="${url}/static/css/perfect-scrollbar.css"> 
	<script type="text/javascript" src="${url}/static/js/perfect-scrollbar.js.download"></script> 
	
	<script type="text/javascript" src="${url}/static/js/jquery.fileDownload.js.download"></script>
	
	
		<script type="text/javascript" src="${url}/static/js/jquery.multiple.select.js.download"></script>	
		
	
	
	<script type="text/javascript" src="${url}/static/js/pdfobject.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/tinymce.min.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/swfobject2.js.download"></script>	
	<script type="text/javascript" src="${url}/static/js/bms.js.download"></script>
	<script type="text/javascript" src="${url}/static/js/core.js.download"></script>
	
	
<!-- hide 	
	<link rel="stylesheet" type="text/css" href="/fe_credit/static/css/bootstrap-responsive.css"/>
	
 end hide -->	
	
		
	 <!-- <style>
        .contentHolder { position:relative; margin:0px auto; padding:0px; width: 100%; min-height: 50px; overflow: hidden; }
        .spacer { text-align:center }
        h2 { text-align: center; }
      </style> -->

<!-- start srollbar -->

<!-- <script>window.jQuery || document.write('<script type="text/javascript" src="/fe_credit/static/css/scrollbar/jquery-1.11.0.min.js"><\/script>')</script>	 -->
<!-- <script>
		(function($){
			$(window).load(function(){
				
				$.mCustomScrollbar.defaults.theme="light-2"; //set "light-2" as the default theme
				
				$(".demo-y").mCustomScrollbar();
				
				$(".demo-x").mCustomScrollbar({
					axis:"x",
					advanced:{autoExpandHorizontalScroll:true}
				});
				
				$(".demo-yx").mCustomScrollbar({
					axis:"yx"
				});
				
				$(".scrollTo a").click(function(e){
					e.preventDefault();
					var $this=$(this),
						rel=$this.attr("rel"),
						el=rel==="content-y" ? ".demo-y" : rel==="content-x" ? ".demo-x" : ".demo-yx",
						data=$this.data("scroll-to"),
						href=$this.attr("href").split(/#(.+)/)[1],
						to=data ? $(el).find(".mCSB_container").find(data) : el===".demo-yx" ? eval("("+href+")") : href,
						output=$("#info > p code"),
						outputTXTdata=el===".demo-yx" ? data : "'"+data+"'",
						outputTXThref=el===".demo-yx" ? href : "'"+href+"'",
						outputTXT=data ? "$('"+el+"').find('.mCSB_container').find("+outputTXTdata+")" : outputTXThref;
					$(el).mCustomScrollbar("scrollTo",to);
					output.text("$('"+el+"').mCustomScrollbar('scrollTo',"+outputTXT+");");
				});
				
			});
		})(jQuery);
	</script> -->	
	<!-- end srollbar -->
<script type="text/javascript">

$(document).ready(function() {		
		
	/* accordion("accordion2");
	accordion("accordion3");
	accordion("accordion4");
	accordion("accordion2_3");
	accordion("accordion2_2"); */

	  //blockUI
	  $(window).unload(function () {
           unblockUI();
       });
     
       $(".checkWaitLoading").click(function () {
       	blockUI();
       });
       
       // remove key press enter 
		$(document).keypress(function(e) {			
			if(e.which == 13 && $("textarea").is(":focus") == false) {  
		    	e.preventDefault();				
		    }
		});
       
		$(document).bind('keydown keyup', function(e) {
		      if(e.keyCode == 116) {//f5
		         window.location.href = document.URL;
		      }
		  });
	
});
//Block ui
function blockUI() {
    $("body").mask($("#loading").text());
};

//Un block ui
function unblockUI() {
	$("body").unmask();
};
</script>





<!--  <script>

      jQuery(document).ready(function ($) {
        "use strict";
        $('#Default').perfectScrollbar();
        $('#SuppressScrollX').perfectScrollbar({suppressScrollX: true});
        $('#SuppressScrollY').perfectScrollbar({suppressScrollY: true});
        $("#Default").find(".ps-scrollbar-x-rail").css({"opacity":0.5});
        $("#Default").find(".ps-scrollbar-y-rail").css({"opacity":0.5});
      });
    </script> -->

<!--[if IE 8]>
<link rel="stylesheet" type="text/css" href="/fe_credit/static/css/ie8.css" /> 

<![endif]-->


<!-- 
 	<script type="text/javascript">
      jQuery(document).ready(function ($) {
        "use strict";
        $('#Default').perfectScrollbar();
        $('#LongThumb').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb2').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb3').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb4').perfectScrollbar({minScrollbarLength:100});
        $('#LongThumb5').perfectScrollbar({minScrollbarLength:100});
        
      });
    </script>
 -->
 
 
    <script type="text/javascript">
    	var eprocurementLocale='en';
    </script>
    
     				

<script type="text/javascript">
	// show tab from URL
	$(function () {
		   var activeTab = $('[href=' + location.hash + ']');
		   activeTab && activeTab.tab('show');
		});
</script>

<script type="text/javascript">
var num = 50; //number of pixels before modifying styles

$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.table_top').addClass('fixed_table');
    } else {
        $('.table_top').removeClass('fixed_table');
    }
});

$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.title_top').addClass('fixed1');
    } else {
        $('.title_top').removeClass('fixed1');
    }
});
$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.menu_top').addClass('fixed');
    } else {
        $('.menu_top').removeClass('fixed');
    }
});

function selectedValue() {
	// no need implement.
}
</script>

<!--start of change css(25-11-16)-->
<script type="text/javascript">
var num = 20; //number of pixels before modifying styles



$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.table_top').addClass('fixed_table');
    } else {
        $('.table_top').removeClass('fixed_table');
    }
});

$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.title_top').addClass('fixed1');
    } else {
        $('.title_top').removeClass('fixed1');
    }
});
$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.menu_top').addClass('fixed');
    } else {
        $('.menu_top').removeClass('fixed');
    }
});

function selectedValue() {
	// no need implement.
}
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
</script>


	<div id="wrap_body_backend">	
		<span style="display: none" id="loading">Loading...</span>
	
		











<!--24-11-16 change-css-->
<style>
.unit_bg_content {
	margin-top:212px;
}
</style>
<!--24-11-16 change-css-->


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
			window.location.href = '/fe_credit/equipmentProfileFull?id='
					+ $("#stockOpenTextMenuequipmentId").val();
		}
	}
</script>
 	
		<div id="eBody">
				








<script type="text/javascript" src="${url}/static/js/jquery.easyui.min.js.download"></script>
<link rel="stylesheet" type="text/css" href="${url}/static/css/easyui.css">


<style>
.change_unit_bg_content{
	margin-top: 145px;
	}
</style>

 <!--end of account-upload-file-->

<script>
	$(document).ready(function() {
		$("#chkDeleteAll").click(function() {
			$("input[name=chkDelete]").each(function() {
				this.checked = $("#chkDeleteAll").is(':checked');
				});
			});
			document.getElementById("action").value = "search";
			$("#del").click(function() {
				if (!confirm("Are you sure delete selected items ?")) {
					return false;
				}
				document.getElementById("action").value = "delete";
				$("#search_form").submit();
			});
			$("#new").click(function() {
				document.location.href = "/fe_credit/system/user/edit";
			});

			$("#stockCombobox").change(function() {
				$("#projectCombobox option").remove();
				$.ajax({url : '/fe_credit/system/user/project_json_codeOrNameAndStockListId?parent='+ $("#stockCombobox").val(),
					dataType : 'json',
					type : 'GET',
					cache : false,
					success : function(data) {
						$.each(data,function(i,object) {
							if (object.projectId == null) {
								$("#projectCombobox").append("<option value=''>"+ "</option>");
							} else {
								$("#projectCombobox").append("<option value='"+object.projectId+"'>"+ object.name+ "</option>");
							}
						});
					}
				});
			});

	});
	function deleteCallBack(result) {
		if (result) {
			$('#action').val("delete");
			$("#search_form").submit();
		}
	}
	function deleteItem() {
		confirmMessage('Are you sure delete selected items ?', deleteCallBack);
	}
	function newItem() {
		document.location.href = "/fe_credit/system/user/edit";
	}
	function importItem() {
		document.location.href = "/fe_credit/system/user/listsync/";
	}
</script>

<script>
	function blackUser() {
		document.location.href = "/fe_credit/system/user/list";
	}
	function black() {
		document.location.href;
	}
	function uploadUser(){
	    var x = document.getElementById("usersExcel");
	    var txt = "";
	    if ('files' in x) {
	        if (x.files.length == 0) {
	            txt = "Select one or more files.";
	        }
	    }
	}

	function alert_button() {
		var alertboxPopup = true;
		 if (alertboxPopup) {
			 $("#abc").html(result.error);
            $(".alert-box-area").addClass("alert-open");
            alertboxPopup = true;
        } else {
            $(".alert-box-area").removeClass("alert-open");
            alertboxPopup= false;
        }
		 setTimeout(function(){ 
				window.location.replace("${url}/contract/termination/details");
				 }, 3000);
	


		}

	function attachment() {
		ajaxUrl = '${url}/contract/termination/viewAttachments';
		$.ajax({
			url : ajaxUrl,
			success : function(result) {				
				//window.location.replace("${url}/contract/termination/view");				
				
			},error: function(err){
				//alert("err = "+JSON.stringify(err));
				}
		});
		
	}

	function view_attachment() {
		
		ajaxUrl = '${url}/contract/termination/viewAttachments';
		
		$.ajax({
			url : ajaxUrl,
			//data : $('#search_form').serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(result) {
				
				window.location.replace("${url}/contract/termination/details");		
				
			},error: function(err){
				//alert("err = "+JSON.stringify(err));
				}
		});
		
	}

	$(window).bind('popstate', function(){
		  window.location.href = window.location.href;
  });


	
	function backTerminationData() {
		document.location.href = "${url}/contract/termination/view";
	}
	
</script>
<!--change html 25-11-16-->
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				
				<h4 style="padding: 8px 0 0 10px; margin:0px 0px 0px">
					<a onclick="back()" class="Color_back">Contract</a><span> &gt; </span>
					<a onclick="backTerminationData()" class="Color_back">Termination</a><span> &gt; </span>
                    <span class="Colorgray">Details</span>
				</h4>
			</div>

		</div>
	</div>
</div>
<!--change html 25-11-16-->
<!--change html 25-11-16-->
<div class="container-fluid change_bidder_status_unit_bg_content">
	<div class="row-fluid">
		<!--<div class="span6 status_h2_area">
			<h2>Change Status</h2>

		</div>-->
	</div>


<%-- <c:if test="${fn:length(beans.contractTerminationDtoList) > 0}">
    <script>
        alert("Calling my function ..." + bean );
        
    </script>
</c:if> --%>


	<div class="row-fluid">

		<div class="well_content row-fluid">
        
			
			<form id="status_form" class="form-horizontal" action="#" method="POST" accept-charset="UTF-8">
			<%-- <p><c:out value="${fn:length(beans.contractTerminationDtoList > 0)}"/></p> --%>
			<%-- <p>aaaaaaaaaaaaaaaaa<c:out value="${fn:length(beans.contractTerminationDtoList)}"/></p> --%>
			<c:choose>
			<c:when test="${fn:length(beans.contractTerminationDtoList )<= 0}">
			 <%-- <p>iffffffffffff<c:out value="${fn:length(beans.contractTerminationDtoList)}"/></p> --%>
				<c:forEach var="msgBean" items="${beans.messages }"  end="0">
				
   					<%-- <p><c:out value="${msgBean.message}"/></p>  --%>
   					<div class="alert-box-area">
						<p><img src="${url}/static/images/alert-icon.jpg" alt=""/><span id="abc" >${msgBean.message}</span></p>			
	   				</div> 					
   					
					 <td>
					  <div id="content" style="table-layout:fixed; width:405px; word-wrap:break-word;">
					  <!-- <script  type="text/JavaScript">
					        window.addEventListener("load", function () {
						        alert("1");
						        var redirectLocation = "${url}/contract/termination/details"; 
					        	var alertboxPopup = true;
					   		 if (alertboxPopup) {
					   			alert("12");
					   			 //$("#abc").html(msgBean.message);
					               $(".alert-box-area").addClass("alert-open");
					               alertboxPopup = true;
					           } 
					   		alert("14");
					   		/* window.onload = function () {
							    if (! localStorage.justOnce) {
							        localStorage.setItem("justOnce", "true");
							        window.location.reload();
							    }
							}   
					        }, true);*/
					        });
					</script> -->
													<script  type="text/JavaScript">
													        window.addEventListener("load", function () {
														        //alert("1");
														        var redirectLocation = "${url}/contract/termination/details"; 
													        	var alertboxPopup = true;
													   		 if (alertboxPopup) {
													   			//alert("12");
													   			 //$("#abc").html(msgBean.message);
													               $(".alert-box-area").addClass("alert-open");
													               alertboxPopup = true;
													           } 
													   		//alert("14");
													   		window.onload = function () {
															    if (! localStorage.justOnce) {
															        localStorage.setItem("justOnce", "true");
															        window.location.reload();
															    }
															}  
													        }, true);
													</script>
					</div>
					
					</td>
   				</c:forEach>
   					
			</c:when> 
			<c:otherwise> 
			<%-- <p>elseeeeeeeeeeeeeeeeeeeee<c:out value="${fn:length(beans.contractTerminationDtoList)}"/></p> --%>
				<c:forEach var="bean" items="${beans.contractTerminationDtoList }" varStatus="i">
				
				<%-- <p><c:out value="${bean.contractTerminationId}"/></p>
				<p><c:out value="${beans.id}"/></p> --%>
				<c:if test="${bean.contractTerminationId == beans.id}" >
   

				<div class="row-fluid">
					<div class="span12">
                   
                   <div class="status-content-area">
                   <!--<div class="status-title">
                   <h2>Status</h2>
                   </div>-->
                   
                   <div class="status_form_details">
                   
                   <div class="row-fluid">
                   <div class="status_form_section">
                   <div class="span5 Margin-status-right">
                   <div class="form-area-section">
                   <label for="contract_termination_id" class="col_collection_view_left"><spring:message code="contract.termination.id" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="contract_termination_id"  value="${bean.contractTerminationId }">
                   </div>
                   <div class="form-area-section">
                   <label for="contract_id" class="col_collection_view_left"><spring:message code="contract.id" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="contract_id"  value="${bean.contractId }">
                   </div>
                   
                   <!-- <div class="form-area-section">
                   <label for="total_wave_request" class="col_collection_view_left"> Total Waive Request</label>
   					<input type="text" class="form-control col_collection_view_right" id="total_wave_request" placeholder="abcdefg" >
                   </div> -->
                   
                    <div class="form-area-section">
                   <label for="penalty_fee" class="col_collection_view_left"><spring:message code="penalty.fee.requested" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="penalty_fee"   value="${bean.penaltyFeeRequested }" >
   					
   													<script  type="text/JavaScript">
													        window.addEventListener("load", function () {
													        	
													        	        var x = $('#penalty_fee').val();
													        	        $('#penalty_fee').val(addCommas(x));
													        	  
													        	
													        	function addCommas(x) {
													        	    var parts = x.toString().split(".");
													        	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
													        	    return parts.join(".");
													        	}
													   		 
													        }, true);
													</script>
                   </div>
                   
                   <div class="form-area-section">
                   <label for="early_termination" class="col_collection_view_left"> <spring:message code="early.termination.fee.requested" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="early_termination"  value="${bean.earlyTerminationFeeRequested }">
   													<script  type="text/JavaScript">
													        window.addEventListener("load", function () {
													        	
													        	        var x = $('#early_termination').val();
													        	        $('#early_termination').val(addCommas(x));
													        	  
													        	
													        	function addCommas(x) {
													        	    var parts = x.toString().split(".");
													        	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
													        	    return parts.join(".");
													        	}
													   		 
													        }, true);
													</script>
                   </div>
                   
                   
                   <div class="form-area-section">
                   <label for="middle_name" class="col_collection_view_left"> <spring:message code="customer.name" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="middle_name" value="${bean.customerName }">
                   </div>
                   
                    <!-- <div class="form-area-section">
                   <label for="first_name" class="col_collection_view_left"> First Name</label>
   					<input type="text" class="form-control col_collection_view_right" id="first_name" placeholder="abcdefg" >
                   </div>
                   
                    <div class="form-area-section">
                   <label for="middle_name" class="col_collection_view_left"> Middle Name</label>
   					<input type="text" class="form-control col_collection_view_right" id="middle_name" placeholder="abcdefg" >
                   </div>
                   
                   <div class="form-area-section">
                   <label for="last_name" class="col_collection_view_left"> Last Name</label>
   					<input type="text" class="form-control col_collection_view_right" id="last_name" placeholder="abcdefg" >
                   </div> -->
                   
                   <div class="form-area-section">
                   <label for="dpd" class="col_collection_view_left"><spring:message code="dpd" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="dpd"  value="${bean.dpd }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="bucket" class="col_collection_view_left"> <spring:message code="bucket" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="bucket"  value="${bean.bucket }">
                   </div>
                   <div class="form-area-section">
                   <label for="unit_code" class="col_collection_view_left"> <spring:message code="unit.code" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="unit_code" value="${bean.unitCode }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="reason" class="col_collection_view_left"> <spring:message code="reason" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="reason"  value="${bean.reason }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="change_off_flag" class="col_collection_view_left"> <spring:message code="charge.off.flag" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="change_off_flag"  value="${bean.chargeOffFlag }">
                   </div>
                   
                   </div>
                   
                   <div class="span5">
                   
                   <div class="form-area-section">
                   <label for="principle_outstanding" class="col_collection_view_left"> <spring:message code="principle.outstanding" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="principle_outstanding"  value="${bean.principleOutstanding }">
   													<script  type="text/JavaScript">
													        window.addEventListener("load", function () {
													        	
													        	        var x = $('#principle_outstanding').val();
													        	        $('#principle_outstanding').val(addCommas(x));
													        	  
													        	
													        	function addCommas(x) {
													        	    var parts = x.toString().split(".");
													        	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
													        	    return parts.join(".");
													        	}
													   		 
													        }, true);
													</script>
   					
   					
                   </div>
                   
                   <div class="form-area-section">
                   <label for="overdue_charges" class="col_collection_view_left"> <spring:message code="overdue.charges" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="overdue_charges"  value="${bean.overdueCharges }">
                   </div>
                   
                    <div class="form-area-section">
                   <label for="interest_outstanding" class="col_collection_view_left"><spring:message code="interest.outstanding" /> </label>
   					<input type="text" class="form-control col_collection_view_right" id="interest_outstanding"  value="${bean.interestOutstanding }">
                   </div>
                   
                    <div class="form-area-section">
                   <label for="prepayment_penalty" class="col_collection_view_left"> <spring:message code="prepayment.penalty" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="prepayment_penalty"  value="${bean.prepaymentPenalty }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="refund" class="col_collection_view_left"> <spring:message code="refunds" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="refund"  value="${bean.refunds }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="inter_fund" class="col_collection_view_left"> <spring:message code="inter.fund" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="inter_fund"  value="${bean.interFund }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="total_amount_paid" class="col_collection_view_left"><spring:message code="total.amount.paid.by.customer" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="total_amount_paid"  value="${bean.totalAmontPaidByCustomer }">
   					<%-- <p class="form-control col_collection_view_right" id="total_amount_paid"><fmt:formatNumber value="${bean.totalAmontPaidByCustomer}" type="currency"/></p> --%>
   					
   													<script  type="text/JavaScript">
													        window.addEventListener("load", function () {
													        	
													        	        var x = $('#total_amount_paid').val();
													        	        $('#total_amount_paid').val(addCommas(x));
													        	  
													        	
													        	function addCommas(x) {
													        	    var parts = x.toString().split(".");
													        	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
													        	    return parts.join(".");
													        	}
													   		 
													        }, true);
													</script>
                   </div>
                   
                   <div class="form-area-section">
                   <label for="billed_inst" class="col_collection_view_left"> <spring:message code="billed.inst" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="billed_inst"  value="${bean.billedInst }">
                   </div>
                   
                   <div class="form-area-section">
                   <label for="phone_number" class="col_collection_view_left"> <spring:message code="phone.number" /></label>
   					<input type="text" class="form-control col_collection_view_right" id="phone_number"  value="${bean.phoneNumber }">
                   </div>
                   <div class="form-area-section">
                   <label for=" payer" class="col_collection_view_left"> <spring:message code="customer.payer" /></label>
   					<input type="text" class="form-control col_collection_view_right" id=" payer"  value="${bean.payer }">
                   </div>
                   
                   <div class="view-attechment-link">
                  <a href="${url}/contract/termination/viewAttachments"><spring:message code="view.attachment" /></a>
                  <%-- <a href="${url}/contract/termination/viewAttachments"  onclick="view_attachment()"><spring:message code="view.attachment" /></a> --%>
                 
		                 

                   </div>
                   
                   </div>
                   
                   </div>
                   
                   
                   
                   
                   
                   </div>
                   
                   
                   
                   </div>
                   
                   <!--change html 25-11-16-->
                  <div class="text-bidder1-center-area-left">
								
								<button type="button"  class="btn_back_general" name="back"> <a href="${url}/contract/termination/view" >Back</a></button>
                                
							</div>


				</div>
                   
                   </div>
                        
						
						<div>
							


						</div>
					</div>
                    
                    
			</c:if>		
           
        </c:forEach>
		</c:otherwise>
		</c:choose>		
				
			</form>
            </div>
		</div>
	</div>
</div>



		</div>			
		<div id="push"></div>
		
		
	
		




   
 
	</div>
