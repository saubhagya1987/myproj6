<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script src="${url}/static/js/vendor/jquery.ui.widget.js"></script>
<script src="${url}/static/js/jquery.iframe-transport.js"></script>
<script src="${url}/static/js/jquery.fileupload.js"></script>
<!-- Modal -->




<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-hidden="true">×</button>
	<spring:message var="msg_header" code="document.view"></spring:message>
	<h3 id="myModalLabel">${msg_header}</h3>
</div>
<div class="form-horizontal">
	<div class="modal-body">
		<div class="row-fluid">
			<div class="control-group">
				<spring:message var="msg_selectrepository"
					code="document.field.selectrepository"></spring:message>
				<label class="control-label"> <i class="icon-folder-open"></i>${msg_selectrepository
					}
				</label>
				<div class="controls">
					<label class="checkbox">
						${documentbean.entity.repository.repositoryName} </label>

				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group">
				<spring:message var="msg_fileName" code="document.field.fileName"></spring:message>
				<label class="control-label"> <i class="icon-edit"></i>${msg_fileName
					}
				</label>
				<div class="controls">
					<label class="checkbox"> ${documentbean.entity.fileName} </label>

				</div>
			</div>

		</div>


		<div class="row-fluid">
			<div class="control-group">
				<spring:message var="msg_title" code="document.field.title"></spring:message>
				<label class="control-label"> <i class="icon-edit"></i>${msg_title
					}
				</label>
				<div class="controls">
					<label class="checkbox"> ${documentbean.entity.title} </label>

				</div>
			</div>

		</div>
		<div class="row-fluid">
			<div class="control-group">
				<spring:message var="msg_keywords" code="document.field.keywords"></spring:message>
				<label class="control-label"> <i class="icon-edit"></i>${msg_keywords
					}
				</label>
				<div class="controls">
					<label class="checkbox"> ${documentbean.entity.keywords} </label>

				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group">
				<spring:message var="msg_comment" code="document.field.comment"></spring:message>
				<label class="control-label"> <i class="icon-edit"></i>${msg_comment
					}
				</label>
				<div class="controls">
					<label class="checkbox"> ${documentbean.entity.bComment} </label>

				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<spring:message var="msg_uploadedDate"
						code="document.field.uploadedDate"></spring:message>
					<label class="control-label"> <i class="icon-calendar"></i>${msg_uploadedDate}
					</label>
					<div class="controls">
						<label class="checkbox"><fmt:formatDate type="both"
								pattern="dd/MM/yyyy hh:mm"
								value="${documentbean.entity.uploadDate }" /> </label>

					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<spring:message var="msg_modifiedDate"
						code="document.field.modifiedDate"></spring:message>
					<label class="control-label"> <i class="icon-calendar"></i>${msg_modifiedDate}
					</label>
					<div class="controls">
						<label class="checkbox"><fmt:formatDate type="both"
								pattern="dd/MM/yyyy hh:mm"
								value="${documentbean.entity.modifiedDate }" /> </label>

					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<spring:message var="msg_uploadedBy"
						code="document.field.uploadBy"></spring:message>
					<label class="control-label"> <i class="icon-user"></i>${msg_uploadedBy}
					</label>
					<div class="controls">
						<label class="checkbox">${documentbean.entity.uploadByName }</label>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<spring:message var="msg_buttonClose" code="button.close"></spring:message>
	<button class="btn btn-info" data-dismiss="modal" aria-hidden="true">${msg_buttonClose}</button>
</div>





