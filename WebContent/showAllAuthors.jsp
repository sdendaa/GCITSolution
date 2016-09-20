<%@include file="includes.jsp"%>
<%@page import="libraryApp.entity.Author"%>
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%
AdminstratorService service = new AdminstratorService();
	Integer totalCount = service.getAuthorsCount();
	Integer pageSize = 10;
	Integer pageCount = 0;
	if (totalCount % pageSize > 0) {
		pageCount = totalCount / pageSize + 1;
	} else {
		pageCount = totalCount / pageSize;
	}
	List<Author> authors = new ArrayList<Author>();
	if (request.getAttribute("authors") != null) {
		authors = (List<Author>) request.getAttribute("authors");
	} else {
		authors = service.getAllAuthors(1, null);
	}
%>
<script>
function search(pageNo){
	$.ajax({
		url: "pageAuthors",
		data:{
			searchString: $('#searchString').val(),
			pageNo: pageNo
		}
	}).done(function(data){
		$('#pageAuthors').html(data);
	})
	$.ajax({
		url: "searchAuthors",
		data:{
			searchString: $('#searchString').val(),
			pageNo: pageNo
		}
	}).done(function(data){
		$('#authorsTable').html(data);
	})
}
</script>
<html>
<head>
<title>List of available Authors in LMS</title>
</head>
<body>

	<div class="input-group">
		<form name="authorForm" id="authorForm">
			<input type="text" class="form-control" placeholder="Author Name" name="searchString" id="searchString" aria-describedby="basic-addon1">
			<button type="button" onclick="search(1)">Search!</button>
		</form>
	</div>

	<nav aria-label="Page navigation" id="pageAuthors">
		<ul class="pagination">
			<%
				for (int i = 1; i <= pageCount; i++) {
			%>
			<li><a href='javascript:search(<%=i%>)'><%=i%></a></li>
			<%
				}
			%>
		</ul>
	</nav>

	<div class="form-group">
		<table class="table" id="authorsTable" frame="box" rules="none">
			<tr>
				<th>Index</th>
				<th>Author Name</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<%
				for (Author a : authors) {
			%>
			<tr>
				<td><%=authors.indexOf(a) %></td>
				<td><%=a.getAuthorName()%></td>
				<td><button class="btn btn-sm btn-success" name= "edit" data-toggle="modal" data-target="#myModal1"  href='editAuthor.jsp?authorId=<%=a.getAuthorId()%>'>Edit</button>
				<td><button class="btn btn-sm btn-danger"  name= "delete"  data-toggle="modal" data-target="#myModal1"  href='deleteAuthor.jsp?authorId=<%=a.getAuthorId()%>'>Delete</button>
			</tr>
			<%
				}
			%>
		</table>

	</div>
	

	<div id="myModal1" class="modal fade bs-example-modal-lg" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content"></div>
		</div>
	</div>
</body>
</html>