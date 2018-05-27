<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String" description="Path to binding"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String" description="Label name"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String" description="Label code"%>
<%@ attribute name="helpLine" required="false" rtexprvalue="true" type="java.lang.String" description="Help line"%>
<%@ attribute name="helpLineCode" required="false" rtexprvalue="true" type="java.lang.String" description="Help line code"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required"%>
<%@ attribute name="cssInput" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="disable" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Disable field"%>
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
<%@ attribute name="labelControl" required="false" rtexprvalue="true" type="java.lang.String" description="label control"%>
<%@ attribute name="displayLabelControl" required="false" rtexprvalue="true" type="java.lang.String" description="display label control"%>
<%@ attribute name="displayLabelControl1" required="false" rtexprvalue="true" type="java.lang.String" description="display label control 1"%>
<%@ attribute name="displayLabelControl2" required="false" rtexprvalue="true" type="java.lang.String" description="display label control 2"%>
<%@ attribute name="text" required="true" rtexprvalue="true" type="java.lang.String" description="text control"%>
<%@ attribute name="inputControl" required="false" rtexprvalue="true" type="java.lang.String" description="input control"%>
<%@ attribute name="inputControl1" required="false" rtexprvalue="true" type="java.lang.String" description="input control 1"%>
<%@ attribute name="inputControl2" required="false" rtexprvalue="true" type="java.lang.String" description="input control 2"%>
<%@ attribute name="hiddenControl" required="false" rtexprvalue="true" type="java.lang.String" description="hidden control"%>
<%@ attribute name="hiddenId" required="false" rtexprvalue="true" type="java.lang.String" description="hidden id"%>
<%@ attribute name="functionUpdate" required="false" rtexprvalue="true" type="java.lang.String" description="function update"%>
<%@ attribute name="functionUpdate2" required="false" rtexprvalue="true" type="java.lang.String" description="function update"%>
<%@ attribute name="cssHidden" required="false" rtexprvalue="true" type="java.lang.String" description="Css of Input"%>
<%@ attribute name="isGrid" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Grid field"%>
<%@ attribute name="displayvalue" required="false" rtexprvalue="true" type="java.lang.String" description="displayvalue field"%>
<%@ attribute name="hiddenvalue" required="false" rtexprvalue="true" type="java.lang.String" description="hiddenvalue field"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.String" description="readonly"%>
<%@ attribute name="parentControl1" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control next is parent text and parent id"%>
<%@ attribute name="parentControl2" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control next is parent text and parent id"%>
<%@ attribute name="parentControl3" required="false" rtexprvalue="true" type="java.lang.String" description="Parent Control next is parent text and parent id"%>
<%@ attribute name="removeAttribute" required="false" rtexprvalue="true" type="java.lang.String" description="Remove attribute when select"%>
<%@ attribute name="restID1" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID2" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="restID3" required="false" rtexprvalue="true" type="java.lang.String" description="Rest id value"%>
<%@ attribute name="csswidth" required="false" rtexprvalue="true" type="java.lang.String" description="width"%>
<%@ attribute name="param1" required="false" rtexprvalue="true" type="java.lang.String" description="Parameter in request"%>
<%@ attribute name="param2" required="false" rtexprvalue="true" type="java.lang.String" description="Parameter in request"%>
<%@ attribute name="updateUnmatch" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Update value hiddent when unmatch"%>
<%@ attribute name="onchange" required="false" rtexprvalue="true" type="java.lang.String" description="on change"%>

<c:if test="${empty csswidth }"><c:set var="csswidth" value="100"></c:set> </c:if>
<%@ taglib tagdir="/WEB-INF/tags/form" prefix="ext-form" %>

<c:set var="label_msg" value=""></c:set>
<c:set var="helpLine_msg" value=""></c:set>
<c:if test="${empty disable }"><c:set var="disableTag" value="false"></c:set> </c:if>
<c:set var="tagreadonly" value=""></c:set> 
<c:if test="${readonly}"><c:set var="tagreadonly" value="readonly"></c:set> </c:if>
<c:if test="${empty readonly}"><c:set var="tagreadonly" value="false"></c:set> </c:if>
<c:if test="${empty separate }"><c:set var="separate" value="-"></c:set> </c:if>
<c:set var="displaykeys" value="${fn:split(displayLabelControl, separate)}" />
<c:set var="displaykeys1" value="${fn:split(displayLabelControl1, separate)}" />
<c:set var="displaykeys2" value="${fn:split(displayLabelControl2, separate)}" />
<c:set var="displaynames" value="${fn:split(names, separate)}" />
<c:if test="${empty param1}"><c:set var="param1" value=""></c:set> </c:if>
<c:if test="${empty param2}"><c:set var="param2" value=""></c:set> </c:if>

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
<c:set var="displayLabelControl_msg" value="${displayLabelControl}"></c:set>
<c:set var="displayLabelControl_msg1" value="${displayLabelControl1}"></c:set>
<c:set var="displayLabelControl_msg2" value="${displayLabelControl2}"></c:set>

<c:if test="${ empty displayLabelControl }"> <c:set var="displayLabelControl_msg" value="${id}"></c:set></c:if>
<c:choose >
	<c:when test="${not empty defaultvalue }">
		<c:set var="defaultvalueTag" value="value='${defaultvalue }'"></c:set>
	</c:when>
	<c:otherwise>
			<c:set var="defaultvalueTag" value="${param[text] }"></c:set>
		
	</c:otherwise>
</c:choose>
<c:if test="${empty visible }"><c:set var="visible" value="true"></c:set> </c:if>

	<c:if test="${ not empty label }"> <c:set var="label_msg" value="${label}"></c:set></c:if>
	<c:if test="${ not empty labelCode }">
		<spring:message var="label_msg1" code="${labelCode}"></spring:message>
		 <c:set var="label_msg" value="${label_msg1}"></c:set>
	</c:if>

	<c:if test="${ not empty helpLine }"> <c:set var="helpLine_msg" value="${helpLine}"></c:set></c:if>
	<c:if test="${ not empty helpLineCode }">
		 <spring:message var="helpLine_msg1" code="${helpLineCode}"></spring:message>
		 <c:set var="helpLine_msg" value="${helpLine_msg1}"></c:set>
	</c:if>
<c:if test="${visible }">

<c:choose>
<c:when test="${isGrid}">
<div class="control-group">
					<div id="scrollable-dropdown-menu">
					<input type="text" data-provide="${dataprovide }" ${tagreadonly } autocomplete="off" ${defaultvalueTag} name="${text }" id="${text }" 
				 ${disableTag } class="${cssInput}" style="width:${csswidth}px" value="${displayvalue}"/>
				 </div>
				<form:hidden path="${path }" id="${text }${id }" class="${cssHidden}" value="${hiddenvalue}"/>
				
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
			
</div>
</c:when>
<c:otherwise>
<div class="control-group">
	
			<label class="control-label" for="${path}">
				${label_msg } 
				<c:if test="${required }">
					<span class="required">*</span>
				</c:if>
			</label>
			<div class="controls">
				<div id="scrollable-dropdown-menu">
					<input onchange="${onchange}" type="text" data-provide="${dataprovide }" autocomplete="off" ${tagreadonly } ${defaultvalueTag} name="${text }" id="${text }" class="${cssInput}" ${disableTag }/>
				</div>
				<form:hidden path="${path }" id="${text }${id }" class="${cssHidden}" />
				
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
			</div>
</div>
</c:otherwise>
</c:choose>



</c:if>
<c:if test="${ not empty matchmsg}">
	<spring:message var="match_msg" code="${matchmsg}"></spring:message>
</c:if>
<script language="javascript">
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
	        	if($('#${parentControl1}').val()!=null){
	        		parentid=$('#${parentControl}').val()+'_'+$('#${parentControl1}').val();
	        		if($('#${parentControl2}').val()!=null){
	        			parentid=$('#${parentControl}').val()+'_'+$('#${parentControl1}').val()+'_'+$('#${parentControl2}').val();
	        		}
	        		if($('#${parentControl3}').val()!=null){
	        			parentid=$('#${parentControl}').val()+'_'+$('#${parentControl1}').val()+'_'+$('#${parentControl2}').val()+'_'+$('#${parentControl3}').val();
	        		}
	        	}else{
	        		parentid=$('#${parentControl}').val();
	        	}
	        	
	        }

	        if('${param1}' != ""){
	        	params= '${param1}';
	        	 if('${param1}' != ""){
	 	        	params =params + "_"+ '${param2}';
	        	 }
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
	        var next= '${url}';
	        if ('${text }'=='subcategorytext' ) {
	        	next+= '?pcId='+$("#categorytextid").val();
			}else if ('${text }'=='categorytext' ) {
	        	next+= '?ptId='+$("#productTypeCombobox").val();
			}
	        $.ajax({
	        	url:next,
	        	data:{
	        		query:query,
	        		params:params,
	        		parent:parentid,
	        		'uniq_param' : (new Date()).getTime()
	        	},
	        	cache:false,
	        	/* async : false, */
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
	    	
	    	<c:if test="${fn:length(displaynames)>1}">
	    		<c:if test="${not empty removeAttribute}">
		    		name="";
	    		<c:forEach items="${displaynames}" varStatus="i">
			         		<c:if test="${removeAttribute ne displaynames[i.index]}">
			         			name+=map['${text}'+item].${displaynames[i.index]}+'${separate}';
			         		</c:if>
	         	</c:forEach> 
	         	name=name.slice(0,-1);  
	         	</c:if>
         	</c:if>
     	
	    	
	        $('#${text}${id }').val(map['${text}'+item].${id});
	        currentText = name;
	        value="";
	        value1="";
	        value2="";
	        try{
	        <c:choose>
		        <c:when test="${fn:length(displaykeys)>1}">
		        		
			         <c:forEach items="${displaykeys}" varStatus="i">
			         	value+=map['${text}'+item].${displaykeys[i.index]}+'${separate}';
		    		</c:forEach> 
			        value=value.slice(0,-1);  
		        </c:when>
		        <c:otherwise>
		       	 	value=map['${text}'+item].${displayLabelControl_msg};
		        </c:otherwise>
	        </c:choose>	
	        }catch(ex){}
	        

	        if($('#${labelControl}')) {
	        	$('#${labelControl}').html(value);
	        }
	        if($('#${inputControl}')) 
	        	$('#${inputControl}').val(value);
	        	
	        // value1
        	try{
     	        <c:choose>
     		        <c:when test="${fn:length(displaykeys1)>1}">
     		       		console.log('aaaaa');
     			         <c:forEach items="${displaykeys1}" varStatus="i">
     			         	value1+=map['${text}'+item].${displaykeys1[i.index]}+'${separate}';
     		    		</c:forEach> 
     			        value1=value1.slice(0,-1);  
     		        </c:when>
     		        <c:otherwise>
     		       		<c:if test="${ not empty displayLabelControl_msg1 }">
     		       	 		value1=map['${text}'+item].${displayLabelControl_msg1};
     		       	 	</c:if>
     		        </c:otherwise>
     	        </c:choose>	
     	        }catch(ex){}


     	       if($('#${inputControl1}')) 
   	        	$('#${inputControl1}').val(value1);
   	       // value 2
   	       try{
 	        	<c:choose>
			        <c:when test="${fn:length(displaykeys2)>1}">
				         <c:forEach items="${displaykeys2}" varStatus="i">
				         	value2+=map['${text}'+item].${displaykeys2[i.index]}+'${separate}';
			    		</c:forEach> 
				        value2=value2.slice(0,-1);  
			        </c:when>
			        <c:otherwise>
			        	<c:if test="${ not empty displayLabelControl_msg2 }">
			       	 		value2=map['${text}'+item].${displayLabelControl_msg2};
			       	 	</c:if>
			        </c:otherwise>
		        </c:choose>	
		
	        }catch(ex){}	

	       if($('#${inputControl2}')) 
	        	$('#${inputControl2}').val(value2);
	       //end value2 
	        
	        
	       if($('#${hiddenControl}')) 
	        	$('#${hiddenControl}').val(map['${text}'+item].${hiddenId})	
	       if(${functionUpdate!=''} ||${functionUpdate!=null})
	       		${functionUpdate}(map['${text}'+item].${hiddenId},value);
	       if(${functionUpdate2!=''} ||${functionUpdate2!=null})
		       		${functionUpdate2}(map['${text}'+item]);
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
		//$("#${text}").closest('.accordion-body').css('overflow','hidden');
		
		
		
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

		// update value hidden
		
		<c:if test="${not empty updateUnmatch && updateUnmatch}">
			$('#${text}${id }').val($("#${text }").val());
		</c:if>
	});

 	/* $('.my-typeahead-container').on('mousedown', 'ul.typeahead', function(e) {
		  e.preventDefault();
		})  */

});
</script>