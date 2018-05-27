<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
 <ul>
 	<li><span id="btnExpandCollapse"><a onclick="openFilterDocument(''); return false;"><i class="icon-folder-open"></i>...</a></span></li>
	<c:forEach var="child" items="${treeRepository}">
		<c:set var="node" value="${child}" scope="request"></c:set>
			<jsp:include page="RepositoryTree.jsp"/>
		</c:forEach>
</ul>
<script type="text/javascript">
	$('.tree span > a').click(function(){
		$(".tree span[class*='badge-warning']").each(function(){
			$(this).removeClass('badge-warning');
		});
		$(".tree span[id*='btnExpandCollapse']").each(function(){
			$(this).removeClass('badge-warning');
		});
		$(this).parent().addClass('badge-warning');
	});
	$('#btnExpandCollapse').click(function(){
		$('.tree li:has(ul)').find(' > ul > li').hide();
	});
	function openFilterDocument(id){
		
		if(id==null)
		  ajaxUrl= '${url}/system/document/ajax_filter_document?fragments=body';
		else
		  $("#hidRepositoryID").val(id);
		  ajaxUrl= '${url}/system/document/ajax_filter_document?fragments=body&entity.repository.repositoryId='+id;
		$.ajax({url:ajaxUrl,
			data : {
			'uniq_param' : (new Date()).getTime(),
			},
			success :function(result){
		    $("#tblFilterViewDocument").html(result);
		  }});
	}
</script>					