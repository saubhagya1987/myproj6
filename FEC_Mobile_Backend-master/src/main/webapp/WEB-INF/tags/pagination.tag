<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="bean" required="true" rtexprvalue="true"
	type="vn.com.unit.fe_credit.bean.AbstractBean"
	description="AbstractBean support binding"%>
<%@ attribute name="maxPage" rtexprvalue="true" type="java.lang.Integer"
	description="Max page to display"%>
<%@ attribute name="formId" rtexprvalue="true" type="java.lang.String"
	description="Form id to submit form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ext"%>
<div class="pagination1 pagination1-small pagination1-right"
	style="padding-right: 10px;">
	<c:if test="${empty  maxPage}">
		<c:set var="maxPage" value="5"></c:set>
	</c:if>
	<script type="text/javascript">
		hiddenValToForm('${formId}', {
			'page' : '${bean.page}',
			'maxPage' : '${maxPage}'
		});
	</script>
	<c:if test="${bean.total > bean.limit }">
		<ul>
			<c:if test="${bean.page gt 1 }">
				<li><a
					href="javascript:submitAndSetHiddenVal('${formId}',{'page':'${bean.page-1 }','maxPage':'${maxPage}','isPaging': 'true'})">
						<spring:message code="pagination.prev"></spring:message>
				</a></li>
			</c:if>

			<c:forEach var="i" items="${bean.listPage }">

				<li><a
					href="javascript:submitAndSetHiddenVal('${formId}',{'page':'${i }','maxPage':'${maxPage}','isPaging': 'true'})"
					class="${bean.page eq i?'paging-active':'' }">${i }</a></li>
			</c:forEach>
			<c:if test="${bean.page < bean.totalPage }">
				<li><a
					href="javascript:submitAndSetHiddenVal('${formId}',{'page':'${bean.page+1 }','maxPage':'${maxPage}','isPaging': 'true'})"><spring:message
							code="pagination.next"></spring:message></a></li>
			</c:if>
		</ul>
	</c:if>
</div>
