<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@include file="includes.jsp"%>

<%
	AdminstratorService service = new AdminstratorService();
	Integer totalCount = service.getPublisherCount();
	Integer pageSize = 10;
	Integer pageCount = 0;
	if (totalCount % pageSize > 0) {
		pageCount = totalCount / pageSize + 1;
	} else {
		pageCount = totalCount / pageSize;
	}
	List<Publisher> pubList = new ArrayList<Publisher>();
	if (request.getAttribute("pubList") != null) {
		pubList = (List<Publisher>) request.getAttribute("pubList");
	} else {
		pubList = service.getAllPublishers(1, null);
	}
%>
<html>
<head>
<title>List of available Publisher in data</title>
</head>
<body>

	<div class="input-group">
		<form action="searchPublishers" method="post">
			<input type="text" class="form-control" placeholder="publisher Name"
				name="searchString" aria-describedby="basic-addon1">
			<button type="submit">Search!</button>
		</form>
	</div>

	<nav aria-label="Page navigation">
		<ul class="pagination">
			<%
				for (int i = 1; i <= pageCount; i++) {
			%>
			<li><a href="pagePublishers?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
		</ul>
	</nav>

	<div class="form-group">
		<table class="table" frame="box" rules="none">
			<tr>
				<th>Index</th>
				<th>Publisher Name</th>
				<th>Publisher Address</th>
				<th>Publisher Phone</th>
				<th>Edit</th>
				<th>delete</th>
			</tr>
			<%
				for (Publisher pu : pubList) {
			%>
			<tr>
				<td><%=pubList.indexOf(pu) + 1%></td>
				<td><%=pu.getPublisherName()%></td>
				<td><%=pu.getPublisherAddress()%></td>
				<td><%=pu.getPublisherPhone()%></td>
				<td><button class="btn btn-sm btn-success" data-toggle="modal"
						data-target="#myModal1"
						href='editPublisher.jsp?publisherId=<%=pu.getPublisherId()%>'>Edit</button>
				<td><button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#myModal1" 
				 href='deletePublisher.jsp?publihserId=<%=pu.getPublisherId()%>'>Delete</button>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<div id="myModal1" class="modal fade bs-example-modal-lg" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content"></div>
		</div>
	</div>

</body>
</html>