<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css" href="${url}/static/css/jcarousel.css" />
<script type="text/javascript" src="${url}/static/js/jcarousel.jquery.js"></script>
 <script type="text/javascript" src="${url}/static/js/jcarousel.js"></script> 

<c:if test="${flag == '0'}"> 
	
<div class="wrapper">		
	<div class="jcarousel-wrapper">
		<div data-jcarousel="true" class="jcarousel">
			<ul>
				<c:forEach items="${listResult}" var="itemImage"
						varStatus="i">
						
						<li style="width: 172px; padding: 0px; margin: 0px;" id="slide${i.index }">
							<div style="padding: 2px; border: 1px solid #ccc">
								<div class="bt_delete_images" style="position: absolute; text-align: right; width: 50%;">
									<c:if test="${removeImage != '1'}">
										<a href="javascript:void(0);"
											onclick="removeImage('${itemImage}', ${i.index });"> <img
											src="${url }/static/images/icon_delete.png" />
										</a>
									</c:if>								
								</div>
								<div>
								<img src="${url}/ajax/download?fileName=${itemImage}" style="height: 100px; width: 100px; display: inline;">
								</div>
							</div>
							<input name="${itemImage}" id="tagImageLinkShow" value="${lstLink[i.index]}" style="margin-top: 5px; height: 50px; width: 99%;"/>
						</li>
					</c:forEach>
				</ul>
			</div>
	
		<a data-jcarouselcontrol="true" href="#"
			class="jcarousel-control-prev">‹</a> <a
			data-jcarouselcontrol="true" href="#"
			class="jcarousel-control-next">›</a>
			
		<%--  <p data-jcarouselpagination="true" class="jcarousel-pagination">
		 	<c:forEach items="${listResult}" var="itemImage"
						varStatus="i">
						<a class="" href="#${i.index }">${i.index }</a>
			</c:forEach>			
		 </p>  --%>	

	</div>
</div>
	

</c:if>
<c:if test="${flag == '1'}"> 
<div style="position: absolute; text-align: right; width: 100%;">
							<c:if test="${removeImage != '1'}">
								<a href="javascript:void(0);"
									onclick="removeImage('${listResult[0]}', '0');"> <img
									src="${url }/static/images/icon_delete.png" />
								</a>
							</c:if>
							
						</div>

	<img id="imgOnlyOne" style="margin-bottom:5px;height: 280px; width: 640px;" src="${url}/ajax/download?fileName=${listResult[0]}">
 </c:if> 
<script type="text/javascript">

	function removeImage(image, index){		
		$("a.right.carousel-control").click();
		
		setTimeout(function() {
			$("#slide" + index).remove();
			var tagImageShow = $("#tagImageShow").val();
			if(tagImageShow != ""){
				var arr = tagImageShow.split(",");
				var newTagImageShow = "";
				for(var i = 0; i < arr.length; ++i){
					if(arr[i] != image){
						newTagImageShow += arr[i] + ",";
					}
				}

				if(newTagImageShow == ""){
					$("#showImageShow").html("");
				}else{
					newTagImageShow = newTagImageShow.substring(0, newTagImageShow.length - 1); 
				}
				$("#tagImageShow").val(newTagImageShow);
			}
		}, 1000);
	}
	var tagImageLinkShow = "";
	function getTextLink(id){
		var link = $("#"+id).val();
		tagImageLinkShow = $("#" + tagImageLinkShow).val();
		$("#tagImageLinkShow").val(tagImageLinkShow + '###' + id + link)
	}
</script>