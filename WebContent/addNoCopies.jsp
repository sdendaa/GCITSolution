<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.Book"%>

<%AdminstratorService adminService = new AdminstratorService();
 String bookId = request.getParameter("bookId");
 String branchId = request.getParameter("branchId");
 Book book = adminService.getBookById(Integer.parseInt(bookId));
%>
<div class="modal-body">
<form action="addNoCopies" method="post">
			Enter Number of Copies: <input type="text" name="noOfCopies">
			<input type="hidden" name="bookId" value=<%=bookId %>>
			<input type="hidden" name="branchId" value=<%=branchId%>>
		<input type="submit"/>
</form>
</div>
