<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.BookLoan"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@include file="includes.jsp"%>

<%
	List<BookLoan> loanList = new ArrayList<BookLoan>();
	loanList = AdminstratorService.getIntance().getAllBookLoaned();
%>
<html>
<head>
<title>List of available Books in data</title>
</head>
<body>

	<div class="form-group">
		<table class="table" frame="box" rules="none">
			<tr>
				<th>Index</th>
				<th>Borrower Name</th>
				<th>Branch Name</th>
				<th>Book Title</th>
				<th>CheckOut Date</th>
				<th>Due Date</th>
				<th>Edit</th>
			</tr>
			<%for (BookLoan bl : loanList) {%>
			<tr>
				<td><%=loanList.indexOf(bl)%></td>
				<td><%=bl.getBorrower().getBorrowerName()%></td>
				<td><%=bl.getBranch().getBranchName()%></td>
				<td><%=bl.getBook().getBookTitle()%></td>
				<td><%=bl.getCheckOutDate()%></td>
				<td><%=bl.getDueDate()%></td>
				<td><button class="btn btn-sm btn-success" data-toggle="modal"
						data-target="#myModal1"
						href='editBookloan.jsp?bookId=<%=bl.getBook().getBookId()%>&branchId=<%=bl.getBranch().getBranchId()%>'>Over-Ride</button></td>

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