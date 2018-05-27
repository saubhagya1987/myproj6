<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>
 <ul>
 	<li><span id="btnExpandCollapseFolder"><a><i class="icon-folder-open"></i>...</a></span>
 	</li>
	<c:forEach var="child" items="${treeFolder}">
		<c:set var="node" value="${child}" scope="request"></c:set>
			<jsp:include page="FolderTree.jsp"/>
		</c:forEach>
</ul>
<script type="text/javascript">
	$('.treeFolder span > a').click(function(){
		$(".treeFolder span[class*='badge-warning']").each(function(){
			$(this).removeClass('badge-warning');
		});
		$(".treeFolder span[id*='btnExpandCollapse']").each(function(){
			$(this).removeClass('badge-warning');
		});
		$(this).parent().addClass('badge-warning');
	});
	$('#btnExpandCollapseFolder').click(function(){
		$('.treeFolder li:has(ul)').find(' > ul > li').hide();
		$('.currentfolder').html("\\");
		$('#urlfolder').val('');
	});
	function chooseLinkFolder(link){
		$('#linkFolder').val(link);
		$('.currentfolder').html(link);
		$('#urlfolder').val(link);
		//console.log($('.currentfolder'));
	}
	
</script>					