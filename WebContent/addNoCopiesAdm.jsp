<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.Book"%>

<%//AdminstratorService adminService = new AdminstratorService();
 Book book =(Book)request.getAttribute("book");
 Integer bookId=book.getBookId();
 String branchId = request.getParameter("branchId");
 //Book book = AdminstratorService.getIntance().getBookById(Integer.parseInt(bookId));
%>
<h2>Number of Copies you want to add</h2>
<div class="modal-body">
<form action="addNoCopiesAdm" method="post">
			Enter Number of Copies: <input type="text" name="noOfCopies" 
			placeholder= 1 required autofocus>
			<input type="hidden" name="bookId" value=<%=bookId %>>
			<input type="hidden" name="branchId" value=<%=branchId%>>
		<input type="submit"/>
</form>
</div>
