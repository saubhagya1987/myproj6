<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ext" %>
 
<%@ attribute name="bean" required="true" rtexprvalue="true" type="vn.com.unit.fe_credit.bean.AbstractBean" description="AbstractBean support binding"%>
<%@ attribute name="maxPage" rtexprvalue="true" type="java.lang.Integer" description="Max page to display" %>
<%@ attribute name="formId" rtexprvalue="true" type="java.lang.String" description="Form id to submit form" %>
<%@ attribute name="ajaxUrl" rtexprvalue="true" type="java.lang.String" required="true" description="url to call AJAX" %>
<%@ attribute name="divId" rtexprvalue="true" type="java.lang.String" required="true" description="Div id to show ajax page after call Ajax" %>

<div class="pagination1 pagination1-small pagination1-right" style="padding-right: 10px;">
<c:if test="${empty  maxPage}">
	<c:set var="maxPage" value="5"></c:set>
</c:if>
<script type="text/javascript">
	hiddenValToForm('${formId}',{'page':'${bean.assetBean.page}','maxPage':'${maxPage}'});
</script>
<c:if test="${bean.assetBean.total > bean.assetBean.limit }">
	<ul>
		<c:if test="${bean.assetBean.page gt 1 }">
			<li><a href="javascript:submitAndSetHiddenVal_Ajax('${formId}',{'page':'${bean.assetBean.page-1 }','maxPage':'${maxPage}'},'','${ajaxUrl }','${divId }')">
					<spring:message code="pagination.prev"></spring:message>
			</a></li>
		</c:if>
		
		<c:forEach var="i" items="${bean.assetBean.listPage }">
			
			<li><a href="javascript:submitAndSetHiddenVal_Ajax('${formId}',{'page':'${i }','maxPage':'${maxPage}'},'','${ajaxUrl }','${divId }')" class="${bean.assetBean.page eq i?'paging-active':'' }">${i }</a></li>
		</c:forEach>
		<c:if test="${bean.assetBean.page < bean.assetBean.totalPage }">
			<li><a href="javascript:submitAndSetHiddenVal_Ajax('${formId}',{'page':'${bean.assetBean.page+1 }','maxPage':'${maxPage}'},'','${ajaxUrl }','${divId }')"><spring:message code="pagination.next"></spring:message></a></li>
		</c:if>
	</ul>
</c:if>
</div>
