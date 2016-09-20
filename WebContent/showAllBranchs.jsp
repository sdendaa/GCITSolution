<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%@page import="java.util.List"%>
<%@page import = "java.util.ArrayList" %>
<%@include file="includes.jsp"%>

<%

AdminstratorService service = new AdminstratorService();
Integer totalCount = service.getBranchsCount();
Integer pageSize = 10;
Integer pageCount = 0;
if (totalCount % pageSize > 0) {
	pageCount = totalCount / pageSize + 1;
} else {
	pageCount = totalCount / pageSize;
}
List<LibBranch> branchList = new ArrayList<LibBranch>();
if (request.getAttribute("branchs") != null) {
	branchList = (List<LibBranch>) request.getAttribute("branchs");
} else {
	branchList = service.getAllBranchs(1, null);
}
%>

<html>
<head>
<title>List of available Books in data</title>
</head>
<body>

	<div class="input-group">
		<form action="searchBranchs" method="post">
			<input type="text" class="form-control" placeholder="Branch Name"
				name="searchString" aria-describedby="basic-addon1">
			<button type="submit">Search!</button>
		</form>
	</div>
	<nav aria-label="Page navigation">
		<ul class="pagination">
			<%
				for (int i = 1; i <= pageCount; i++) {
			%>
			<li><a href="pageBranchs?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
		</ul>
	</nav>


	<div class="form-group">
<table class="table" frame="box" rules="none">
	<tr>
		<th>Index</th>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		for (LibBranch br : branchList) {
	%>
	<tr>
		<td><%=branchList.indexOf(br)+1%></td>
		<td><%=br.getBranchName() %></td>
		<td><%=br.getBranchAddress() %></td>
		<td><button class="btn btn-sm btn-success" data-toggle="modal"
						data-target="#myModal1"
						href='editBranch.jsp?branchId=<%=br.getBranchId()%>'>Edit</button>
				<td><button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#myModal1" 
				 href='deleteBranch.jsp?branchId=<%=br.getBranchId()%>'>Delete</button>
	</tr>
	<%
		}
	%>
</table>
</div>

	<div id="myModal1" class="modal fade bs-example-modal-lg" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content" ></div>
		</div>
	</div>

</body>
</html>