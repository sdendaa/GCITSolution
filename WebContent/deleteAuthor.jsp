
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.Author"%>
<%
	int authorId = Integer.parseInt(request.getParameter("authorId"));
	Author a = AdminstratorService.getIntance().getAuthorById(authorId);
%>
		<div class="modal-content">
			<form action="deleteAuthor" method="post">
				<input type="text" style="width: 600px;" name="authorName"
					value=' You are about to delete <%=a.getAuthorName()%>. Do you want to submit?'  /><br />
				<input type="hidden" name="authorId" value=<%=a.getAuthorId()%>>
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
