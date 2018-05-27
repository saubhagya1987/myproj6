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

		
	<script type="text/javascript" src="${url}/static/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.loadmask.min.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery-ui-1.9.1.js"></script> 
    <script type="text/javascript" src="${url}/static/js/bootstrap.js"></script>
    <script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${url}/static/js/bootstrap-datepicker.vi.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${url}/static/js/bootbox.js"></script>
    <script type="text/javascript" src="${url}/static/js/jshashtable-3.0.js"></script>
	<script type="text/javascript" src="${url}/static/js/jquery.numberformatter.js"></script>

	
	<link rel="stylesheet" type="text/css" href="${url}/static/css/jquery.horizontal.scroll.css"> 
	<script type="text/javascript" src="${url}/static/js/jquery.horizontal.scroll.js"></script> 

 	<link rel="stylesheet" type="text/css" href="${url}/static/css/perfect-scrollbar.css"> 
	<script type="text/javascript" src="${url}/static/js/perfect-scrollbar.js"></script> 
	
	<script type="text/javascript" src="${url}/static/js/jquery.fileDownload.js"></script>
	
	
		<script type="text/javascript" src="${url}/static/js/jquery.multiple.select.js"></script>	
		
	
	
	<script type="text/javascript" src="${url}/static/js/pdfobject.js"></script>
	<script type="text/javascript" src="${url}/static/js/tinymce.min.js"></script>
	<script type="text/javascript" src="${url}/static/js/swfobject2.js"></script>	
	<script type="text/javascript" src="${url}/static/js/bms.js"></script>
	<script type="text/javascript" src="${url}/static/js/core.js"></script>
	
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

	
	function update_status() {
		 /* var alertboxPopup = true;
		 if (alertboxPopup) {
             $(".alert-box-area").addClass("alert-open");
             alertboxPopup = true;
         } else {
             $(".alert-box-area").removeClass("alert-open");
             alertboxPopup= false;
         } */
		ajaxUrl = '${url}/contract/repossession/updateStatus';
		
		$.ajax({
			url : ajaxUrl,
			data : $('#search_form').serialize(),
			dataType : 'json',
			type : 'POST',
			cache : false,
			success : function(result) {
				if(result!=null && result.info!=""){
									
					 /* var alertboxPopup = true;
					 if (alertboxPopup) {
						 $("#abc").html(result.info);
			             $(".alert-box-area").addClass("alert-open");
			             alertboxPopup = true;
			         } else {
			             $(".alert-box-area").removeClass("alert-open");
			             alertboxPopup= false;
			         }
					 setTimeout(function(){ 
							window.location.replace("${url}/contract/termination/view");
							 }, 3000); */
					window.location.replace("${url}/contract/repossession/view");
				}
				else{
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
							window.location.replace("${url}/contract/repossession/view");
							 }, 3000);
				}
				
				
				//<a href="${url}/contract/termination/view">
				
			},error: function(err){
				//alert("err = "+JSON.stringify(err));
				}
		});
		//$("#assignModel").hide();
	}

	function backTerminationData() {
		document.location.href = "${url}/contract/repossession/view";
	}
	
</script>
<style>
	.alertboxbtn{
	margin:0px;
	}
.alert-box-area{
	width:230px;
	height:auto;
	position:fixed;
	/*top:20%;*/
	left:50%;
	display:none;
	border-radius:4px;
	transform: translate(0,0);
	transition: transform .3s ease-out;
	    box-shadow: 0 5px 15px rgba(0,0,0,.5);
		border: 1px solid rgba(0,0,0,.2);
    border-radius: 6px;
	padding:15px;
	box-sizing:border-box;
	background:#fff;
	
	
	
}

.alert-box-area p img{
	margin:0px 10px 0px 0px;
	}
.alert-open{
	display:block;
	transform: translate(0,-25%);
	
}

.alert-box-footer{
	width:100%;
	display:table;
	text-align:center;
	}
	
.yes_btn{
	padding:3px 8px!important;
	}
	
.no_btn{
	padding:3px 8px!important;
	}
	
	.loader {
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid #333;
  width: 40px;
  height: 40px;
  -webkit-animation: spin 2s linear infinite;
  animation: spin 2s linear infinite;
}

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>

<!--change html 25-11-16-->
<div class="title_top">

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
				<spring:message var="menu_admin" code="menu.admin"></spring:message>
				 <!-- change html 25-11-16 -->
				
				<h4 style="padding: 8px 0 0 10px; margin:0px 0px 0px">
					<a onclick="back()" class="Color_back">Contract</a><span> &gt; </span>
					<a onclick="backTerminationData()" class="Color_back">Repossession</a><span> &gt; </span>
					<span class="Color_back">Status Update</span>
				</h4>
			</div>

		</div>
	</div>
</div>
 <!-- change html 25-11-16 -->
 <!--change html 25-11-16-->
<div class="container-fluid change_unit_pop_bg_content">
	<div class="row-fluid">
    <!-- change html 25-11-16 -->
    
		<!--<div class="span6 title_h2_area">
			<h2>
				
				 Repossession 
			</h2>

		</div>-->
        
     <!-- change html 25-11-16 -->
     
	</div>

	<div class="row-fluid">

		<div class="well_content row-fluid">
        
        
        

			<form id="search_form" class="form-horizontal" action="http://localhost:8080/fe_credit/system/user/list/" modelAttribute="bean" method="POST" accept-charset="UTF-8">
				








<div class="messages">
						
</div>
				<div class="row-fluid ">
					
                        <!--change html 25-11-16-->
                        
						
							<div class="accordion-inner-pop">
								<div class="row-fluid">
									<div class="span12">
										<div class="span6">
										<div class="col_field_row">
                                        
    									<label for="status" class="col_left">Status</label>
   										  <form:select name="status-txt" path="bean.status" id="status" class="form-control col_right1" >
                                         	<option>Approved</option>
                                            <option>Rejected</option>
                                          </form:select>
                                         
                                       <%--  <form:select id="accesFlagId"  required="true"  cssClass="" path="entity.accessFlag">
										<c:forEach items="${bean.userAccessFlag}" var="j">
											<form:option value="${j.intValue}">${j.desc}</form:option>
										</c:forEach>
									   </form:select> --%>
                                        
    									

                                        </div>
                                        <div class="col_field_row">
                                        
    									<label for="remarks" class="col_left">Remarks</label>
   										<!--  <textarea type="text" path="bean.remark"  class="form-control col_right" id="remarks"></textarea> -->
                                        
                                        <%-- <ext-form:input-text path="bean.remark"  cssInput="span10" ></ext-form:input-text>  --%>
                                        <form:input path="bean.remark" class="form-control col_right" id="remarks"></form:input>

                                        </div>



										</div>
										
									
									
								</div>
							</div>
							
						</div>
                        
                         <!--change html 25-11-16-->
                         
					</div>
                    <!--change html 25-11-16-->
                    <div class="text-center-area_pop">
								<input type="hidden" id="action" name="action" value="search">
								
								<button type="button"  class="btn_submit_general alertboxbtn" name="submit" onclick="update_status()">Submit</button>
                                <button type="button" class="btn_cancel_general" name="cancel"><a href="${url}/contract/repossession/view"> Cancel</a></button>
							</div>
                     <!--change html 25-11-16-->

				
                
              
				
				
				
			</form>
			
			<div class="alert-box-area">
			<p><img src="${url}/static/images/alert-icon.jpg" alt=""/><span id="abc" ></span></p>
			
			</div>
			
			

<!-- <div class="loader"></div> -->
              </div>
		</div>
	</div>
</div>



		</div>			
		<div id="push"></div>
	
		

	</div>


