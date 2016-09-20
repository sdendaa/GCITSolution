<%@page import="libraryApp.service.LibrarianService"%>
<%@page import="libraryApp.entity.Borrower"%>
<%@page import="libraryApp.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@include file="includes.jsp"%>

<% 
String cardNo = (String) request.getAttribute("cardNo");
String branchId = (String) request.getAttribute("branchId");
List<Book> books = new ArrayList<Book>();
/* books = LibrarianService.getIntance().getAllBooksFromBranch(branchId, 1, "book"); */
%>

<table class="table">
	<tr>
		<th>Book Title</th>
		<th>Check Out</th>
	</tr>
	<%for(Book b: books){ %>
	<tr>
		<td><%out.println(b.getBookTitle()); %></td>
		<td><button type="button" class="btn btn-md btn-success" onclick="javascript:location.href='checkOutBook?bookId=<%=b.getBookId()%>&branchId=<%=branchId%>&cardNo=<%=cardNo%>';">Check Out</button></td>
	</tr>
	<%} %>
</table>