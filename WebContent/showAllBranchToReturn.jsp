<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%@page import="libraryApp.entity.Book"%>
<%@include file="includes.jsp"%>

<%
AdminstratorService as = new AdminstratorService();
List<LibBranch> branches = as.getAllBranchs(null, null);
int cardNo = Integer.parseInt(request.getParameter("cardNo"));
List<Book> bookList  = new ArrayList<Book >();
%>

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
	branchList = service.getAllBranchsToReturn(cardNo);
	
%>

<html>
<head>
<title>List of available Branch in data</title>
</head>
<body>
	<form action="form-group">
		<table class="table" frame="box" rules="none">
			<tr>
				<th>Branch Name</th>
				<th>Branch Address</th>
				<th>Select Branch</th>
			</tr>

			<%
				for (LibBranch lb : branches) {
			%>
			<tr>
				<td>
					<%
						out.println(lb.getBranchName());
					%>
				</td>
				<td>
					<%
						out.println(lb.getBranchAddress());
					%>
				</td>
				<td>
					<button type="button" class="btn btn-md btn-success"
						onclick="javascript:location.href='showAllBookBorrowedByBranch.jsp?branchId=<%=lb.getBranchId()%>&cardNo=<%=cardNo%>'">Select
					</button>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</form>
	<div id="myModal1" class="modal fade bs-example-modal-lg" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content"></div>
		</div>
	</div>

</body>
</html>
