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
var num = 10; //number of pixels before modifying styles



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

	
</script>
<!--change html 25-11-16-->
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				 <!-- change html 25-11-16 -->
				
				<h4 style="padding: 8px 0 0 10px; margin:0px 0px 0px">
					<a onclick="back()" class="Color_back">Contact</a><span> &gt; </span>
					<span class="Color_back">Repossession</span>
				</h4>
			</div>

		</div>
	</div>
</div>
<!--change html 25-11-16-->
 <!-- change html 25-11-16 -->
<div class="container-fluid change_unit_bg_content">
	<div class="row-fluid">
    <!-- change html 25-11-16 -->
    
		<div class="span6 title_h2_area">
			<h3>
			<!-- Contract Termination -->
			<spring:message code="contract.repossession" />
					
			</h3>

		</div>
        
     <!-- change html 25-11-16 -->
     
	</div>

	<div class="row-fluid">

		<div class="well_content row-fluid">

			<form id="search_form" class="form-horizontal" action="http://localhost:8080/fe_credit/system/user/list/" method="POST" accept-charset="UTF-8">
	

<div class="messages">
						
</div>
				<div class="row-fluid ">
					<div class="accordion-group">
						<div class="accordion-heading">
							<div class="row-fluid">
								<div class="span2 title1">
									<h3>
										<spring:message code="search.criteria" />
										<!-- Search Criteria --> 
									</h3>

								</div>
								<div class="span1 unit_accordion" style="text-align: right;">
									<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="http://localhost:8080/fe_credit/system/user/list/#collapseOne"><i class="bms-icon-accordion-down bms-icon-accordion"></i></a>
								</div>
							</div>
						</div>
                        <!--change html 25-11-16-->
                        
						<div id="collapseOne" class="accordion-body collapse in border-group">
							<div class="accordion-inner">
								<div class="row-fluid">
									<div class="span8"></div>
									<div class="span12">
										<div class="span6">
										<div class="col_field_row">
                                        
    									<label for="contract-id" class="col_left"><spring:message code="contract.id" /></label>
   										 <input type="text" class="form-control col_right" id="contract-id" >

                                        </div>



										</div>
										<div class="span6">
											<div class="col_field_row">
                                        
    									<label for="customer-nam" class="col_left"><spring:message code="customer.name" /></label>
   										 <input type="text" class="form-control col_right" id="customer-nam" >

                                        </div>


										</div>
									</div>
									<div class="span12">
										<div class="span6">
                                        <div class="col_field_row">
                                        
    									<label for="request-date" class="col_left"><spring:message code="request.date" /></label>
   										 <input type="text" class="form-control col_right" id="request-date" >

                                        </div>

				

										</div>
										<div class="span6">
                                        <div class="col_field_row">
                                        
    									<label for="status" class="col_left"><spring:message code="status" /></label>
   										  <select name="status-txt" id="status" class="form-control col_right1" >
                                         	<option><spring:message code="actived" /></option>
                                            <option><spring:message code="deactived" /></option>
                                         </select>
                                         
                                        </div>

										
										</div>
									</div>
								</div>
							</div>
							<div class="text-center-area">
								<input type="hidden" id="action" name="action" value="search">
								
								<button type="button"  class="btn_renew_general" name="renew"><spring:message code="renew" /></button>
                                <button type="button" onclick="submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;1&#39;,&#39;maxPage&#39;:&#39;5&#39;})" class="btn_search_general" name="search"><spring:message code="search" /></button>
							</div>
						</div>
                        
                         <!--change html 25-11-16-->
                         
					</div>

				</div>
				
				<div class="row-fluid">
                 <!--change html 25-11-16-->
                 
					<div class="span12 bg_round_table">
                    <div class="repossession-table">
                    <div class="table-responsive ">
  						<table class="table reposs table-hover">
   									<thead>
  									 <tr>
	  									 <th><spring:message code="repossession.contractId" /></th>
	  									 <th><spring:message code="repossession.customerId" /></th>
	  									 <th><spring:message code="repossession.customerName" /></th>
	  									 <th><spring:message code="repossession.loanAmount" /></th>
	  									 <th><spring:message code="repossession.principalOutstanding" /></th>
	  									 <th style="width:20%"><spring:message code="customer.action" /></th>
  									 
										<!-- <th>Customer ID</th>
										<th>Customer Name</th>
										<th>Owner</th>										
										<th>Request Date</th>
										<th>Last Date</th>
										<th style="width:20%">Action</th> -->
                                     </tr>
										
									</thead>
        								<tbody>
        								<c:choose>
        								 <c:when test="${fn:length(bean.terminatedContracts ) <= 0}">
												<c:forEach var="msgBean" items="${bean.messages }"  end="0">
								   					<%-- <p><c:out value="${msgBean.message}"/></p> --%> 
								   					<div class="alert-box-area">
														<p><img src="${url}/static/images/alert-icon.jpg" alt=""/><span id="abc" >${msgBean.message}</span></p>			
									   				</div> 					
								   					
													 <td>
													  <div id="content" style="table-layout:fixed; width:405px; word-wrap:break-word; height:auto!important">
													  <script  type="text/JavaScript">
													        window.addEventListener("load", function () {
														        //alert("1");
														        var redirectLocation = "${url}/contract/repossession/details"; 
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
        								<c:forEach var="contractTerminationList" items="${bean.terminatedContracts }"
											varStatus="i">
											<tr>
										
												<%-- <td class="w50_stt text_center">${(bean.page-1)*bean.limit+i.index+1}</td> --%>
												<td><c:out value="${contractTerminationList.appleId }"></c:out></td>
												<td><c:out value="${contractTerminationList.customerId }"></c:out></td>
												<td><c:out value="${contractTerminationList.customerName }"></c:out></td>
												<td><%-- <c:out value="${contractTerminationList.loanAmount }"></c:out> --%>
													<c:set var="val" value="${contractTerminationList.loanAmount }" /> 
													<fmt:formatNumber value="${val}" pattern="#,###.##" var="pat" /> 
													${fn:replace(pat, ",", ",")}
												</td>												
												<td><%-- <c:out value="${contractTerminationList.principalOutstanding }"></c:out> --%>
													<c:set var="val" value="${contractTerminationList.principalOutstanding }" /> 
													<fmt:formatNumber value="${val}" pattern="#,###.##" var="pat" /> 
													${fn:replace(pat, ",", ",")}
												
												</td>
												
												
												<td>
			                                        <div class="edit-section-collection">
			                                        <ul>
			                                        
			                                        <li>
			                                        
			                                        <div class="view-collection">
			                                        <a href="${url}/contract/repossession/details?contractId=${contractTerminationList.appleId}&repossessionId=${contractTerminationList.id}"><img src="${url}/static/images/view.png" alt=""/></a><br/>View</div>
			                                        </li>
			                                        <sec:authorize access="hasAnyRole('DOC','UH','HOP','HOF','CRO')">
			                                        <li>
			                                        <div class="change-status-collection">
			                                        <a href="${url}/contract/repossession/status?contractId=${contractTerminationList.appleId}&repossessionId=${contractTerminationList.id}"><img src="${url}/static/images/status.png" alt=""/></a><br/>Status<br/>Change
			                                        </div>
			                                        </li>
			                                        </sec:authorize>
			                                        
			                                        <sec:authorize access="hasAnyRole('CA')">
			                                        <li>
			                                        <div class="change-assign-collection">
			                                        <a href="#"  id="${i.index}" onclick="showPopUp(this.id)" data-toggle="modal" data-target="#assignModel"><img src="${url}/static/images/assign-icon.png" alt=""/></a><br/>Assign
			                                        <input type="hidden" id="reassign_url_${i.index}" value="${url}/contract/repossession/reassigns?contractId=${contractTerminationList.appleId}&repossessionId=${contractTerminationList.id}&nextApproverLevel=${contractTerminationList.nextApproverLevel}"/>
			                                        <%-- <a href="${url}/contract/termination/reassign?contractId=${contractTerminationList.contractId}&contractTerminationId=${contractTerminationList.contractTerminationId}&nextApproverLevel=${contractTerminationList.nextApproverLevel}"  data-toggle="modal" data-target="#assignModel"><img src="${url}/static/images/assign-icon.png" alt=""/></a><br/>Assign --%>
			                                        </div>
			                                        </li>
			                                        </sec:authorize>
			                                        
			                                        <sec:authorize access="hasAnyRole('CA','CSA','FS')">
			                                        <li>
			                                        <div class="change-status-collection">
			                                        <a href="${url}/contract/repossession/viewBidder?contractId=${contractTerminationList.appleId}&repossessionId=${contractTerminationList.id}"><img src="${url}/static/images/bidder.png" alt=""/></a><br/>Bidder
			                                        </div>
			                                        </li>
			                                        </sec:authorize>
			                                        </ul>
			                                        </div>
                                        		</td>
                                        		
                                        		
												<script>	

												var urlToLoad = '';
													function showPopUp(id){
														var url = $("#reassign_url_"+id).val();
															//alert("id - " + id + ", url - " + url);
															urlToLoad = url;
															//$("#reassign_yes_btn").attr("href", url);
														}


													function reassign() {
														ajaxUrl = urlToLoad;
														$.ajax({
															url : ajaxUrl,
															
															success : function(result) {
																//alert("succ="+JSON.stringify(result));
																location.reload();
																//window.location.reload(true)
																//window.location.replace("${url}/contract/termination/view");
																//<a href="${url}/contract/termination/view">
																
															},error: function(err){
																//alert("err = "+JSON.stringify(err));
																}
														});
														//$("#assignModel").hide();
													}
												</script>
												
											</tr>
											
										</c:forEach>
										<td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
									</c:otherwise>
								</c:choose>
        								
                                        
                                        <%-- <td>
                                        <div class="edit-section-collection">
                                        <ul>
                                        <li>
                                        <div class="view-collection">
                                        <a href="${url}/contract/termination/details"><img src="${url}/static/images/view.png" alt=""/></a><br/>View</div>
                                       </li>
                                        <li>
                                        <div class="change-status-collection">
                                        <a href="${url}/contract/termination/status"><img src="${url}/static/images/status.png" alt=""/></a><br/>Status<br/>Change
                                        </div>
                                        </li>
                                        
                                        <li>
                                        <div class="change-assign-collection">
                                        <a href="#"  data-toggle="modal" data-target="#assignModel"><img src="${url}/static/images/assign-icon.png" alt=""/></a><br/>Assign
                                        </div>
                                        </li>
                                        </ul>
                                        </div>
                                        </td> --%>
                                        </tbody>
        							
  						</table>
                        
                        <!-- Modal -->
<div id="assignModel" class="modal fade assignModelArea" role="dialog">
  <div class="modal-dialog modal-sm">

    <!-- Modal content-->
    <div class="modal-content">
     
      <div class="modal-body assign-content">
        <p>Are you sure you want to assign to level approved</p>
      </div>
      <div class="modal-footer assign-footer ">
      	<a href="#" type="button" id="reassign_yes_btn" class="btn btn-success" onclick="reassign()">Yes</a>
        <a href="#" type="button" class="btn btn-danger" data-dismiss="modal">No</a>
      </div>
    </div>

  </div>
</div>
</div>
</div>

						
						<div>
							







<!--<div class="pagination1 pagination1-small pagination1-right" style="padding-right: 10px;">
	
	<script type="text/javascript">
		hiddenValToForm('search_form', {
			'page' : '1',
			'maxPage' : '10'
		});
	</script>
	
		<ul>
			

			

				<li><a href="javascript:submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;1&#39;,&#39;maxPage&#39;:&#39;10&#39;,&#39;isPaging&#39;: &#39;true&#39;})" class="paging-active">1</a></li>
			

				<li><a href="javascript:submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;2&#39;,&#39;maxPage&#39;:&#39;10&#39;,&#39;isPaging&#39;: &#39;true&#39;})" class="">2</a></li>
			

				<li><a href="javascript:submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;3&#39;,&#39;maxPage&#39;:&#39;10&#39;,&#39;isPaging&#39;: &#39;true&#39;})" class="">3</a></li>
			

				<li><a href="javascript:submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;4&#39;,&#39;maxPage&#39;:&#39;10&#39;,&#39;isPaging&#39;: &#39;true&#39;})" class="">4</a></li>
			
			
				<li><a href="javascript:submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;2&#39;,&#39;maxPage&#39;:&#39;10&#39;,&#39;isPaging&#39;: &#39;true&#39;})">Next</a></li>
			
		</ul>
	
</div>-->

						</div>
					</div>
                    
                 <!--change html 25-11-16-->
                 
				</div>
				
			</form>
		</div>
	</div>
</div>



		</div>			
		<div id="push"></div>
	
		




   
 
	</div>

