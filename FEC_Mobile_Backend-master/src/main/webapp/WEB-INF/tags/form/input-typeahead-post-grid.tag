<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String" description="Path to binding"%>
<%@ attribute name="helpLine" required="false" rtexprvalue="true" type="java.lang.String" description="Help line"%>
<%@ attribute name="helpLineCode" required="false" rtexprvalue="true" type="java.lang.String" description="Help line code"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required"%>
<%@ attribute name="cssInput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="visible" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Visible field"%>
<%@ attribute name="dataprovide" required="false" rtexprvalue="true" type="java.lang.String" description="Typeahead"%>
<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" description="Url ajax"%>
<%@ attribute name="parentControl" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control is parent text and parent id"%>
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="Id"%>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.lang.String" description="name"%>
<%@ attribute name="separate" required="true" rtexprvalue="true" type="java.lang.String" description="separate"%>
<%@ attribute name="defaultvalue" required="false" rtexprvalue="true" type="java.lang.String" description="defaultvalue"%>
<%@ attribute name="match" required="false" rtexprvalue="true" type="java.lang.Boolean" description="match"%>
<%@ attribute name="matchmsg" required="false" rtexprvalue="true" type="java.lang.String" description="matchmsg"%>
<%@ attribute name="text" required="true" rtexprvalue="true" type="java.lang.String" description="text control"%>
<%@ attribute name="hiddenControl" required="false" rtexprvalue="true" type="java.lang.String" description="hidden control"%>
<%@ attribute name="hiddenId" required="false" rtexprvalue="true" type="java.lang.String" description="hidden id"%>
<%@ attribute name="functionUpdate" required="false" rtexprvalue="true" type="java.lang.String" description="function update"%>
<%@ attribute name="hiddenvalue" required="false" rtexprvalue="true" type="java.lang.String" description="hiddenvalue field"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.String" description="readonly"%>
<%@ attribute name="restID1" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID2" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID3" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID4" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="index" required="false" rtexprvalue="true" type="java.lang.String" description="width"%>

<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>

<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:set var="tagreadonly" value=""></c:set> 
<c:if test="${readonly}"><c:set var="tagreadonly" value="readonly"></c:set> </c:if>
<c:if test="${empty readonly}"><c:set var="tagreadonly" value="false"></c:set> </c:if>
<c:if test="${empty separate }"><c:set var="separate" value="-"></c:set> </c:if>
<c:choose >
	<c:when test="${disable }">
		<c:set var="disableTag" value="disabled='true'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="disableTag" value=""></c:set>
		
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${fn:length(defaultvalue)==1}">
		<c:set var="defaultvalue" value="${param[text] }"></c:set>
	</c:when>
	<c:otherwise>
			<c:if test="${fn:indexOf(defaultvalue,separate)==0}">
				<c:set var="defaultvalue" value="${fn:substring(defaultvalue,1,fn:length(defaultvalue)) }"></c:set>
			</c:if>
	</c:otherwise>
</c:choose>
<c:set var="hiddenId" value="${hiddenId }"></c:set>
<c:if test="${ empty hiddenId }"> <c:set var="hiddenId" value="${id}"></c:set></c:if>
<c:choose >
	<c:when test="${not empty defaultvalue }">
		<c:set var="defaultvalueTag" value="value='${defaultvalue }'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="defaultvalueTag" value="${param[text] }"></c:set>
		
	</c:otherwise>
</c:choose>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>


	<c:if test="${ not empty helpLine }"> <c:set var="helpLine_msg" value="${helpLine}"></c:set></c:if>
	<c:if test="${ not empty helpLineCode }">
		 <spring:message var="helpLine_msg1" code="${helpLineCode}"></spring:message>
		 <c:set var="helpLine_msg" value="${helpLine_msg1}"></c:set>
	</c:if>
<c:if test="${visible }">

				<div id="scrollable-dropdown-menu">
				<input type="text" data-provide="${dataprovide }" ${tagreadonly } autocomplete="off" ${defaultvalueTag} name="${text }" id="${text }" 
				 class="${cssInput}"  value="${displayvalue}"/>
				 </div>
				<form:hidden path="${path }" id="${text }${id }" value="${hiddenvalue}"/>
				
				<c:set var="error_clss" value="${fn:replace(path,'.','_') }"></c:set>
				<span class="help-inline ${error_clss}_msg">
					<c:if test="${not empty helpLine_msg }">
						<c:out value="${helpLine_msg }"></c:out>
					</c:if>
					<form:errors path="${path}" cssClass="error" ></form:errors>
					<c:choose >
						<c:when test="${match}">
					<span id="${text}${id}error" class="error"></span>
					</c:when>
					</c:choose>
				</span>
	
</c:if>
<c:if test="${ not empty matchmsg}">
	<spring:message var="match_msg" code="${matchmsg}"></spring:message>
</c:if>
<script type="text/javascript">
$(document).ready(function(){
	var currentText="";
	$("#${text }").focus(function(){
	
		currentText=$("#${text }").val();
	});
	if($('#${text}${id }').val()=='' && '${match}'){
		$("#${text }").val("");
	}else if($('#${text}${id }').val()!='' && '${param[text] }'!=''){
		$("#${text }").val("${param[text]}");
	}
$("#${text }").typeahead({
	  source: function(query, process) {
		  	//$("#${text}").closest('.accordion-body').css('overflow','visible');
		  
	        objects = [];
	        map = {};
	        var parentid="";
	        var params="";
	        if($('#${parentControl}')){
	        	parentid=$('#${parentControl}').val();	        	
	        }

	       



	        
	        if($('#${restID1}').val()!=null){
	        	$('#${restID1}').val("");
	        }
	        if($('#${restID2}').val()!=null){
	        	$('#${restID2}').val("");
	        }
	        if($('#${restID3}').val()!=null){
	        	$('#${restID3}').val("");
	        }
	        if($('#${restID4}').val()!=null){
	        	$('#${restID4}').val("");
	        }
	        $.ajax({
	        	url:'${url}',
	        	data:{
	        		query:query,
	        		params:params,
	        		parent:parentid,
	        		'uniq_param' : (new Date()).getTime()
	        	},
	        	type: 'POST',
	        	success:function(result){
	        		var keys='${names}'.split('${separate}');
	        		
	        		 $.each(result, function(i, object) {
	     	            map['${text}'+object.${id}] = object;
	     	            var value=object.${id}+"@";
	     	           for(var i in keys){
	     	        	   
		        			value+=object[keys[i]]+'${separate}';
		        		}
	     	           value=value.slice(0,-1);
	     	            objects.push(value);
	     	        });
	        		   process(objects);
	        	}
	        })
	       
	     
	    },
	    updater: function(item) {
	    	//$("#${text}").closest('.accordion-body').css('overflow','hidden');
	    	
	    	name = item.substr(item.indexOf("@")+1);
	    	item=item.substr(0,item.indexOf("@"));
	    	
	       	
	    	
	        $('#${text}${id }').val(map['${text}'+item].${id});
	        currentText = name;
	             	     
	       if(${functionUpdate!=''} ||${functionUpdate!=null})
	       		${functionUpdate}(map['${text}'+item],'${index}');
	        return name;
	    },
	    highlighter: function (item) {
	    	item=item.substr(item.indexOf("@")+1);
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
	        return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	          return '<strong>' + match + '</strong>'
	        })
	      },
	      sorter: function (items) {
	          var beginswith = []
	            , caseSensitive = []
	            , caseInsensitive = []
	            , item

	          while (item = items.shift()) {
	            if (!item.substr(item.indexOf("@")+1).toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(item)
	            else if (~item.substr(~item.indexOf("@")+1).indexOf(this.query)) caseSensitive.push(item)
	            else caseInsensitive.push(item)
	          }

	          return beginswith.concat(caseSensitive, caseInsensitive)
	        },
	      items: 12
	});
	     	
	$("#${text }").change(function(){
		
		if(currentText!="" && currentText!=$("#${text }").val()){
			$('#${text}${id }').val("");
		}
		
		if($("#${text}${id}error")&&'${match}'=='true'){
			
			if($('#${text}${id }').val()=="" && $("#${text }").val()!="")
			{
				$("#${text}${id}error").html('${match_msg}');
				$("#${text }").val("");
				$('#${text}${id }').val("");
				currentText="";
			}else{
				$("#${text}${id}error").html("");
				currentText = $("#${text }").val();
			}
			
		}else{
			
			if($('#${text}${id }').val()=="" && $("#${text }").val()!="")
			{
				$('#${text}${id }').val("");
				currentText="";
				
			}else{
				currentText = $("#${text }").val();
			}			
		}

	});


});
</script>