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
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/datepicker.css">
		
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/jquery.loadmask.css">	
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/vccb_style.css">
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/non-responsive.css">
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/card_vccb.css">

		
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery-1.8.3.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery.loadmask.min.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery-ui-1.9.1.js.download"></script> 
    <script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/bootstrap.js.download"></script>
    <script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/bootstrap-datepicker.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/bootstrap-datepicker.vi.min.js.download" charset="UTF-8"></script>
    <script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/bootbox.js.download"></script>
    <script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jshashtable-3.0.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery.numberformatter.js.download"></script>

	
	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/jquery.horizontal.scroll.css"> 
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery.horizontal.scroll.js.download"></script> 

 	<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/perfect-scrollbar.css"> 
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/perfect-scrollbar.js.download"></script> 
	
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery.fileDownload.js.download"></script>
	
	
		<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery.multiple.select.js.download"></script>	
		
	
	
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/pdfobject.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/tinymce.min.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/swfobject2.js.download"></script>	
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/bms.js.download"></script>
	<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/core.js.download"></script>
	
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

<!--end of change css(25-11-16)-->

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
	

<!-- start menutop and banner top -->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="banner-top">
		<div class="container-fluid">

			<div class="row-fluid">
				<div class="span4">
					<img src="./MOBILE MANAGEMENT SYSTEM_files/logo1.png" width="269" height="75">
				</div>
				<div class="span4">
					<h2 style="padding: 25px 0 0 0; color: #ff0000;">Mobile
						Management System</h2>
				</div>


				<!-- 				<div class="span2"> -->
				<!-- 					<form class="navbar-form navbar-left" role="search"> -->
				<!-- 						<div class="form-group"> -->
				
				<!-- 							<input type="text" class="form-control" -->
				
				<!-- 						</div> -->
				<!-- 					</form> -->
				<!-- 				</div> -->


				<div class="span4 unit-right">
					
					



					
					

					<div class="row-fluid">
						<div class="span9">
							<span><span>Welcome: </span><a href="http://localhost:8080/fe_credit/userProfile/edit" style="color: #666">
										Chhabra </a>  <br> <span>

									My account |&nbsp;</span> <a style="color: #666" href="http://localhost:8080/fe_credit/j_spring_security_logout">Log out </a>
							</span> <br> <span style="color: #666">Version: 3.0</span>
						</div>
						<div class="span3">

							<img class="img-circle" src="./MOBILE MANAGEMENT SYSTEM_files/avata_c5.gif" width="55" height="55">
						</div>
					</div>

					<!-- 					<a -->
					
					<!-- 						href="javascript:void(0);"> <img -->
					
					<!-- 					</a>  -->
					
					<!-- 						href="javascript:void(0);"> <img -->
					
					<!-- 					</a> -->
				</div>

			</div>

		</div>
	</div>
	<div class="menu-top_backend">
		<div class="container-fluid">
			<!-- Note that the .navbar-collapse and .collapse classes have been removed from the #navbar -->
			<div id="navbar">
				<ul class="nav navbar-nav">

					<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/index">Home </a></li>
					 
					<li class="dropdown" id="menu_customer"><a class="dropdown-toggle" role="button" data-toggle="dropdown" href="http://localhost:8080/fe_credit/system/user/list/#">Customers <b class="caret"></b></a>
						<ul id="menu_message" class="dropdown-menu" role="menu" aria-labelledby="drop2">
								
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/customer/list">Customers </a></li>
								
								
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/customer/init">Import Customer </a></li>
								
								
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/hobby/list">Hobby </a></li>
								

							</ul></li>
				


			 
					<li class="dropdown" id="menu_master_message"><a class="dropdown-toggle" role="button" data-toggle="dropdown" href="http://localhost:8080/fe_credit/system/user/list/#">Messages &amp; Notification
							<b class="caret"></b></a>
						<ul id="menu_message" class="dropdown-menu" role="menu" aria-labelledby="drop2">
							<li role="presentation">
							 
								<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/message/follow_up">My Follow-up Messages </a>
										
								 		
								<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/message/dashboard/list">Message Dashboard </a> 
								
										
								<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/message/dashboard/importlist">List Import Message </a> 
									
								
									</li><li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/message/init">Import Message </a></li>
								
								
								<!-- Push message -->
								
									<li class="dropdown-submenu pull-left" role="presentation">
										<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/user/list/#">
										Push Message </a>
										<ul class="dropdown-menu supmenu-w2" role="menu" style="left:90%">
											<li role="presentation">
												<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/push_message/init">
													Import Push Message 
												</a>
											</li> 
											<li role="presentation">
												<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/push_message/list_template">
													List Template Message
												</a>
											</li>
										</ul>
									</li>
								
								
							
						</ul></li>
			
			 
					<li class="dropdown" id="menu_loan_request_data"><a class="dropdown-toggle" role="button" data-toggle="dropdown" href="http://localhost:8080/fe_credit/system/user/list/#">Loan Requests <b class="caret"></b></a>
						<ul id="menu3" class="dropdown-menu" role="menu" aria-labelledby="drop2">
							<li role="presentation">
							
							<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/apply_now/list">Submitted Loan Request </a></li>
							<li role="presentation">
							
							<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/loan/list">Loan Calculator </a></li>
							<li role="presentation">
							
							<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/loan/listdetail">Loan Calculator Setup </a></li>
						</ul></li>
				

 					
					<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/contract/list">Mobile Banking </a></li>
					
					 
					<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/banner/list">Banners </a></li>
					

					 
					<li class="dropdown" id="menu_navigation_data"><a class="dropdown-toggle" role="button" data-toggle="dropdown" href="http://localhost:8080/fe_credit/system/user/list/#">Navigations <b class="caret"></b></a>
						<ul id="menu3" class="dropdown-menu" role="menu" aria-labelledby="drop2">

							
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/pos/list">POS </a></li>
								
						</ul></li>
					
					 
					<li class="dropdown" id="menu_master_data"><a class="dropdown-toggle" role="button" data-toggle="dropdown" href="http://localhost:8080/fe_credit/system/user/list/#">CMS <b class="caret"></b></a>
						<ul id="menu3" class="dropdown-menu" role="menu" aria-labelledby="drop2">

							<li role="presentation">
							<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/cms/list">CMS </a></li>
							<li role="presentation">
							<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/contact/show">About </a></li>
							<li role="presentation">
							<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/master_data/cms_category/list">Category </a></li>
						</ul></li>
						
					 
					<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/dashboard/view">Reports </a></li>
					

					 
					<li class="dropdown" id="menu_system"><a class="dropdown-toggle" role="button" data-toggle="dropdown" href="http://localhost:8080/fe_credit/system/user/list/#">Admin <b class="caret"></b></a>
						<ul id="menu2" class="dropdown-menu" role="menu" aria-labelledby="drop2">
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/account/list/">Account manage </a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/team/list/">Team manage</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/system_config">System Config</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/user/list/">Manage Users </a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/cms/record/list/">Collection Records </a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/role/list/">Manage Roles </a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/module/list/">Manage Module </a></li>
							<!-- MGM config -->
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/mgm_config">Config mgm exchange rate</a></li>
								
							<!-- Job quartz -->		
							<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/quartz_job/">Scheduler</a></li>
										
							<li class="dropdown-submenu pull-left role=" presentation"=""><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/masterdata/list/">Master Data </a>
								<ul class="dropdown-menu supmenu-w2" role="menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/masterdataDetail/list/">Master Data Detail </a></li> 
								</ul>
							</li>
							<li class="dropdown-submenu pull-left role=" presentation"="">
								<a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/user/list/#">
								Export file </a>
								<ul class="dropdown-menu supmenu-w2" role="menu">
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/exportFile/redeemTransactionPoint/list/">Redeem transaction point </a>
									</li> 
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://localhost:8080/fe_credit/system/exportFile/dataToVTiger/list/">Data to V-Tiger </a>
									</li>
								</ul>
							</li>
						
				
				</ul>

			</li></ul></div>
			<!--/.nav-collapse -->
		</div>
	</div>
</nav>
<!-- end menutop and banner top -->

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
				
<script type="text/javascript" src="./MOBILE MANAGEMENT SYSTEM_files/jquery.easyui.min.js.download"></script>
<link rel="stylesheet" type="text/css" href="./MOBILE MANAGEMENT SYSTEM_files/easyui.css">


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
				
				 <!-- change html 25-11-16 -->
				
				<h4 style="padding: 8px 0 0 10px; margin:0px 0px 0px">
					<a onclick="back()" class="Color_back">CMS</a><span> &gt; </span>
					<span class="Color_back">Contract Termination</span>
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
			<h2>
				Contract Termination
					
			</h2>

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

										Search Criteria 
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
                                        
    									<label for="contract-id" class="col_left">Contract ID</label>
   										 <input type="text" class="form-control col_right" id="contract-id" >

                                        </div>



										</div>
										<div class="span6">
											<div class="col_field_row">
                                        
    									<label for="customer-nam" class="col_left">Customer Name</label>
   										 <input type="text" class="form-control col_right" id="customer-nam" >

                                        </div>


										</div>
									</div>
									<div class="span12">
										<div class="span6">
                                        <div class="col_field_row">
                                        
    									<label for="request-date" class="col_left">Request Date</label>
   										 <input type="text" class="form-control col_right" id="request-date" >

                                        </div>

				

										</div>
										<div class="span6">
                                        <div class="col_field_row">
                                        
    									<label for="status" class="col_left">Status</label>
   										  <select name="status-txt" id="status" class="form-control col_right1" >
                                         	<option>Actived</option>
                                            <option>Deactived</option>
                                         </select>
                                         
                                        </div>

										
										</div>
									</div>
								</div>
							</div>
							<div class="text-center-area">
								<input type="hidden" id="action" name="action" value="search">
								
								<button type="button"  class="btn_renew_general" name="renew">Renew</button>
                                <button type="button" onclick="submitAndSetHiddenVal(&#39;search_form&#39;,{&#39;page&#39;:&#39;1&#39;,&#39;maxPage&#39;:&#39;5&#39;})" class="btn_search_general" name="search">Search</button>
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
										<th>Customer ID</th>
										<th>Customer Name</th>
										<th>Owner</th>										
										<th>Request Date</th>
										<th>Last Date</th>
										<th style="width:20%">Action</th>
                                     </tr>
										
									</thead>
        								<tbody>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td>
                                        <div class="edit-section-res">
                                        <ul>
                                        <li>
                                        <div class="view">
                                        <a href="#"><img src="MOBILE MANAGEMENT SYSTEM_files/view.png" alt=""/></a><br/>View</div>
                                       </li>
                                        <li>
                                        <div class="change-status">
                                        <a href="#"><img src="MOBILE MANAGEMENT SYSTEM_files/status.png" alt=""/></a><br/>Status<br/>Change
                                        </div>
                                        </li>
                                        </ul>
                                        </div>
                                        </td>
                                        </tbody>
        							
  						</table>
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
	
		




   
 <!-- start menubottom -->  	
    <div class="footer">
    	<div>	
    	<!-- 
    	<div class="row">
        <div class="span1">
        </div>
        <div class="span9">
        	 <ul class="menu-footer">
                <li><a href="/fe_credit/homepage">Homepage</a></li>
                <li><a>|</a></li>
                <li><a href="https://vietcapitalbank.com.vn/gioi-thieu-chung" target="_blank">About us</a></li>
                <li><a>|</a></li>
                <li><a href="https://vietcapitalbank.com.vn/lien-he" target="_blank">Contact us</a></li>
                <li><a>|</a></li>
                <li><a href="#">Site map</a></li>
                <li><a>|</a></li>
                <li><a href="#">FAQs</a></li>
                <li><a>|</a></li>
                <li><a href="#">Download</a></li>
                <li><a>|</a></li>
                <li><a href="/fe_credit/homepage/termcondition/view">Term & Condition</a></li>
              </ul>
        </div>
        <div class="span2 text-center" style="padding-top: 10px;">
        <img src="/fe_credit/static/images/t.jpg" width="23" height="23">&nbsp;<img src="/fe_credit/static/images/y.jpg" width="23" height="23">
        <img src="/fe_credit/static/images/f.jpg" width="23" height="23">&nbsp;<img src="/fe_credit/static/images/i.jpg" width="23" height="23">
        </div>        
      </div>
       -->
      <div class="row-fluid">
          <div class="text-center">
          <p></p>
          <p style="margin: 0px;"><img src="./MOBILE MANAGEMENT SYSTEM_files/img_footer_24.png" width="30" height="30"><span>(08) 39 333 888</span> <img src="./MOBILE MANAGEMENT SYSTEM_files/img_footer_ac.png" width="30" height="30"><span>User Guide   |  Version 01.01</span></p>
          
          <p>Copyright 2015 © FE Credit. All rights reserved.</p>
         
          </div>
      </div>
      </div>
    </div>   		
	<!-- end bottom --> 
	</div>


