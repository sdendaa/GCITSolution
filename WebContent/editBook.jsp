
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.Book"%>
<%
	int bookId = Integer.parseInt(request.getParameter("bookId"));
	Book b = AdminstratorService.getIntance().getBookById(bookId);
%>
		<div class="modal-content">
			<form action="editBook" method="post">
			
				<input type="text" style="width: 400px;" name="title"
					value='<%=b.getTitle() %>' class="form-control"
					placeholder="Enter book title to Edit" required autofocus /><br />
				
				<input type="hidden" name="bookId" value=<%=b.getBookId()%>>
				<input type="submit" class="btn btn-primary" />
				<a href="http://localhost:8080/LibraryWebApp/index.jsp" >Cancel</a>
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



