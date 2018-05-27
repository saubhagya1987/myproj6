<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <c:set var="url" value="${pageContext.request.contextPath}"></c:set>   

<!-- we code these -->
</head>

<body>
<h1>Spring MVC - jQuery File Upload</h1>
<div style="width:500px;padding:20px">

	<input id="fileupload" type="file" name="files[]" data-url="${url}/ajax/upload" multiple>
	
	<div id="dropzone" class="fade well">Drop files here</div>
	
	<div id="progress" class="progress">
    	<div class="bar" style="width: 0%;"></div>
	</div>

	<table id="uploaded-files" class="table">
		<tr>
			<th>File Name</th>
			<th>File Size</th>
			<th>File Type</th>
			<th>Download</th>
		</tr>
	</table>
	
</div>
</body> 
</html>
