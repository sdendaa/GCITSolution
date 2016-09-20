<%@page import="libraryApp.service.LibrarianService"%>
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.Book"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%@include file="includes.jsp"%>

<%
LibrarianService libSer = new LibrarianService();
String cardNoTemp = request.getParameter("cardNo");
Integer cardNo = Integer.parseInt(cardNoTemp);
String branchIdTemp = (String) request.getParameter("branchId");
Integer branchId = Integer.parseInt(branchIdTemp);
List<Book> books = libSer.getAllBooksFromBranch(branchId, null, null);
%>
<form action="checkOut" method="post">
<table class="table" frame="box" rules="none">
	<tr>
		<th>Book Title</th>
		
		<th>checkout Book</th>
	</tr>
	<%for(Book b: books){ %>
	<tr>
		<td><%out.println(b.getBookTitle()); %></td>
	
<td>
				<input type="hidden" name="bookId" value=<%=b.getBookId()%>>
				<input type="hidden" name="cardNo" value=<%=cardNo%>>
				<input type="hidden" name="branchId" value=<%=branchId%>>
				<input type="submit"  value ="checkOut" class="btn btn-primary" />
</td>
	</tr>
	<%} %>
</table>
</form>
