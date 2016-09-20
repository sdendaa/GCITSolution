<%@page import="libraryApp.service.LibrarianService"%>
<%@page import="libraryApp.service.BorrowerService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.BookLoan"%>
<%@page import="libraryApp.entity.Book"%>
<%@include file="includes.jsp"%>

<%
LibrarianService libSer = new LibrarianService();
BorrowerService borSer = new BorrowerService();
String cardNoTemp = request.getParameter("cardNo");
Integer cardNo = Integer.parseInt(cardNoTemp);
String branchIdTemp = request.getParameter("branchId");
Integer branchId= Integer.parseInt(branchIdTemp);
List<Book> bookList = borSer.getAllBorrowedBooks(branchId, cardNo);

%>

<table class="table" frame="box" rules="none">
	<tr>
		<th>Book Title</th>
		<th>Select Book</th>  
	</tr>
	<%for(Book b: bookList){ %>
	<tr>
		<td><%out.println(b.getBookTitle()); %></td>
		<td>
		<input type="button"  class="btn btn-md btn-success" value="Return" onclick="javascript:location.href='returnBook?bookId=<%=b.getBookId()%>&branchId=<%=branchId%>'">
		</td>
	</tr>
	<%} %>
</table>
<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content"></div>
	</div>
</div>