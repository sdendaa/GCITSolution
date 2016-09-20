
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.Author"%>
<%
	int authorId = Integer.parseInt(request.getParameter("authorId"));
	Author a = AdminstratorService.getIntance().getAuthorById(authorId);
%>
		<div class="modal-content">
			<form action="editAuthor" method="post">
				<input type="text" style="width: 400px;" name="authorName"
					value='<%=a.getAuthorName()%>' class="form-control"
					placeholder="Enter Author's name to Edit" required autofocus /><br />
				<input type="hidden" name="authorId" value=<%=a.getAuthorId()%>>
				<input type="submit" class="btn btn-primary" />
				<a href="http://localhost:8080/LibraryWebApp/showAllAuthors.jsp" >Cancel</a>
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



