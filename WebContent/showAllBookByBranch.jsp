<%@page import="libraryApp.service.LibrarianService"%>
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.Book"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%@include file="includes.jsp"%>

<%
LibrarianService libSer = new LibrarianService();
String branchIdTemp = request.getParameter("branchId");
Integer branchId = Integer.parseInt(branchIdTemp);
LibBranch br = AdminstratorService.getIntance().getBranchById(branchId);
/* System.out.println(br.getNoCopies().size()); */
List<Book> books = libSer.getAllBooksFromBranch(branchId, null, null);
%>

<table class="table" frame="box" rules="none">
	<tr>
		<th>Book Title</th>
		<!-- <th>Number of copies</th>  -->
		<th>Select Book</th>
		
	</tr>
	<%for(Book b: books){ 
	%>
	<tr>
		<td><%out.println(b.getBookTitle()); %></td>
		<td>
		<input type="button"  class="btn btn-md btn-success" value="Select" onclick="javascript:location.href='addNoCopies.jsp?bookId=<%=b.getBookId()%>&branchId=<%=branchId%>'">
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