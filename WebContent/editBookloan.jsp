
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.BookLoan"%>
<%
	int bookId = Integer.parseInt(request.getParameter("bookId"));
	int branchId = Integer.parseInt(request.getParameter("branchId"));
	BookLoan b = AdminstratorService.getIntance().getBookLoanById(branchId, bookId);
%>
		<div class="modal-content">
			<form action="editBookLoan" method="post">
			CheckOut Date:
				<input type="Date" style="width: 400px;" name="dateOut"
					value='<%=b.getCheckOutDate() %>' class="form-control"
					placeholder="0000-00-00 " required autofocus /><br />
					Due Date:
					<input type="Date" style="width: 400px;" name="dueDate"
					value='<%=b.getDueDate() %>' class="form-control"
					placeholder="0000-00-00 " required autofocus /><br />
				
				<input type="hidden" name="bookId" value=<%=bookId%>>
				<input type="hidden" name="branchId" value=<%=branchId%>>
				<input type="submit" class="btn btn-primary" />
				<a href="http://localhost:8080/LibraryWebApp/showAllBookLoaned.jsp" >Cancel</a>
			</form>
		</div>
		
<script type="text/javascript">
$(document).ready(function()
		{
		    $('.modal').on('hidden.bs.modal', function(e)
		    { 
		        $(this).removeData();
		    }) ;
		});
</script>



