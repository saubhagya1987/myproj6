<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
   <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
   <spring:message var="welcome" code="welcome"/>
 
 <!-- start menutop and banner top -->
    <nav class="navbar navbar-default">
    <spring:message var="box_search" code="msg.search.box"></spring:message>
    
   <div class="banner-top1"> 
    <div class="banner-top">
    	<div class="container-fluid">       
        	<div class="row-fluid">
<!--                 <div class="span5" style="margin: 12px 0 0 0;"> -->
<%--                 	<img src="${url}/static/images/logo.png" onclick="location.href='${url}/homepage'" width="194" height="51"> --%>
<!--                 </div> -->
<!--                 <div class="span4 unit-right" > -->
<!--                 <form role="search" style="margin-top: 12px;">                     -->
<%--                       <input class="search_input_img" style="width: 240px; height: 20px; padding-left: 20px;" type="text" placeholder="&nbsp;${box_search }">                                                        	                   		                     --%>
<!--                      </form> -->
<!--                 </div> -->
                
<!--                 <div class="span3 unit-right"> -->
<!--                      <span style="font-weight: bold; color: #ff0000; float: right;">1900 555 555</span>           	 -->
<!--                 	<span style="color: #ff0000; float: right;">Hotline: </span> <br> -->
<%--                 	<span class="icon-map-marker"></span><span class="location">&nbsp;<spring:message code="msg.location"/>&nbsp;&nbsp;&nbsp;</span> --%>
                	<c:if test="${not empty sessionScope['scopedTarget.userProfile'].account }">
	                	<c:url value='/userProfile/edit' var="userProfile_url"></c:url> 
	                	<c:url value='/j_spring_security_logout' var="log_out_url"></c:url> 
	                    <span class="icon-user"></span>
	                    <a href="${userProfile_url } " style="color: #66cc33"><c:out value=" ${sessionScope['scopedTarget.userProfile'].fullName}"/> |</a><a href="${log_out_url } ">
	                    <spring:message code="log.out" /> </a>&nbsp;	
                     </c:if>
	                   
<%-- 	                     <a id="langvi" onclick="changeLang('${url}', 'vi');"href="javascript:void(0);" >  --%>
<%-- 	                     	<img src="${url}/static/images/vi.png" width="18" height="12">&nbsp; --%>
<!-- 	                     </a> -->
<%-- 	                     <a id="langen" onclick="changeLang('${url}', 'en');"	href="javascript:void(0);"> --%>
<%-- 	                   		 <img src="${url}/static/images/en.png" width="18" height="12"> --%>
<!-- 	                     </a> -->
                 </div>
            </div>
       
      </div>
    </div>
     
<div class="menu-top1" id="menu_top_vccb"> 
    <div class="menu-top"> 
      	<div class="container-fluid">        
        <!-- Note that the .navbar-collapse and .collapse classes have been removed from the #navbar -->
        <div id="navbar">
          <ul class="nav navbar-nav">
          	<li class="icon_home" id="menu_system" onclick="goHomeURL();" style="cursor: pointer;">
               		<a style="margin: 4px 0px 0px 0px; padding: 10px 15px;" class="dropdown-toggle"><img src="${url}/static/images/icon-home.png" width="30" height="30"></a>
               		
       	     </li>
           	 <spring:message var="menu_master_card" code="menu.master.card"></spring:message>
             <li class="dropdown menu_card" id="menu_system">
               		<a class="dropdown-toggle"  role="button" data-toggle="dropdown" href="#"><img src="${url}/static/images/icon-card.png" width="30" height="30">&nbsp;&nbsp;<span class="icon_card_left"><spring:message code="menu.master.card" /></span> <b class="caret"></b></a>
               		
               		<ul id="menu2" class="dropdown-menu" role="menu" aria-labelledby="drop2">   
                      	
                      <li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/homepage/quiz"><spring:message code="menu.quiz" /></a></li>
                      <li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/homepage/survey/view"><spring:message code="receive.cardinfo.survey" /> </a></li>
                    </ul>
       	     </li>
              <li class="dropdown" id="menu_master_data"> 
              	
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#"><img src="${url}/static/images/icon-revard.png" width="30" height="30">&nbsp;&nbsp;<span class="icon_card_left"><spring:message code="menu.rewardbenefit" /></span> <b class="caret"></b></a>
                    		<ul id="menu2" class="dropdown-menu" role="menu" aria-labelledby="drop2">	            
                        		<li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/rewards365"><spring:message code="benefitsdetail.365" /></a></li>
                        		<li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/rewardsbenefits"><spring:message code="titlebenefits.field.type.benefit" /></a></li>
                        		<li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/rewardsplatinum"><spring:message code="titlebenefits.field.type.platium" /></a></li>
							</ul>
             </li>
            <li class="dropdown card_compare">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><img src="${url}/static/images/icon-compare.png" width="30" height="30">&nbsp;&nbsp;<span class="icon_card_left"><spring:message code="msg.compare"/> </span> <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/homepage/cardfullcompare/view"><spring:message code="menu.card.cardcompare.frondend" /></a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="${url}/homepage/card_quick_compare/view"><spring:message code="menu.card.quick.compare.frondend" /></a></li>               
              </ul>
            </li>
            <li class="sendrequest">
              <a href="${url}/homepage/sendrequest/add"><img src="${url}/static/images/icon-service.png" width="30" height="30">&nbsp;&nbsp;<span class="icon_card_left"><spring:message code="msg.service.request"/> </span></a>              
            </li>
            <c:if test="${not empty sessionScope['scopedTarget.userProfile'].account }">
             <li><a href="${url}/userProfile/edit">&nbsp;&nbsp;<span class="icon_card_left"><spring:message code="menu.admin" /></span></a></li> 
             </c:if>
          </ul>
          
        </div><!--/.nav-collapse -->
      </div>
    </div>
</div>
      
    </nav>
   <!-- end menutop and banner top -->
    
<!-- and menutop -->
<script  type="text/javascript">
	$(document).ready(function() {

		var varURL = (document.URL).replace("http://", "");
		/* $("#menu_"+menuCurrent).addClass("active"); */
		sessionStorage.setItem('listMenu', lstMenuCard);
		$('#menu_system #menu2').prepend(lstMenuCard);
        if(typeof(Storage) !== "undefined") {
            if(menuCurrent='homepage'){
            	var lstMenuCard = service.loadLstMenuCard('${url}');
            	if(typeof(Storage) !== "undefined") {
            		sessionStorage.setItem('listMenu', lstMenuCard);
            		$('#menu_system #menu2').prepend(lstMenuCard);
            	}else{
            		$('#menu_system #menu2').prepend(lstMenuCard);
            	}
            }else{
	            if(sessionStorage.getItem('listMenu') == null){
	            	var lstMenuCard = service.loadLstMenuCard('${url}');
	    	        $('#menu_system #menu2').prepend(lstMenuCard);
	                sessionStorage.setItem('listMenu', lstMenuCard);
	            }else{
	            	$('#menu_system #menu2').prepend(sessionStorage.getItem('listMenu'));
	            }
            }
        } else {
        	var lstMenuCard = service.loadLstMenuCard('${url}');
        	$('#menu_system #menu2').prepend(lstMenuCard);
        } 
        $('.banner-top .icon-map-marker, .banner-top .location').unbind('click').click(function(event) {
            window.location.href = "${url}/homepage/map/search";
        });

        genderTypeAhead();
	});
	function onCompleteBD() {
		if ($("#stockOpenTextMenuequipmentId").val() != "" && $("#stockOpenTextMenuequipmentId").val() != undefined) {
			window.location.href = '${url}/equipmentProfileFull?id=' + $("#stockOpenTextMenuequipmentId").val();
		}
	}
	function goHomeURL(){
		window.location.href = '${url}';
	}

    function genderTypeAhead(){
        var labels, mapped;

        $('form[role=search] .search_input_img').typeahead({
            source: function(query, process){
                
                $.ajax({
                    url: '${url}/homepage/map/list_json_type_ahead',
                    type: 'POST',
                    dataType: 'json',
                    data: {'query': query},
                    success : function(data){
                        labels = [];
                        mapped = {};
                        $.each(data, function(i, v) {
                            var query_label = v.name + ' - ' + v.address;
                            mapped[query_label] = v;
                            labels.push(query_label);
                        });
                        //return the display array
                        process( labels );
                    }
                })
            },
            updater: function (item) {
                var lstBranch = [];
                lstBranch.push(mapped[item]);
                if(typeof(Storage) !== "undefined") {
                    localStorage.setItem('branch', JSON.stringify(mapped[item]));
                } else {
                    console.log('Sorry! No Web Storage support.. menu_frond_end');
                }
                window.location.href = "${url}/homepage/map/search";
                //return the string you want to go into the textbox (e.g. name)
                return item;
            }
        });
    }


</script>
