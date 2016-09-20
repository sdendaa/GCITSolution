
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%
	int branchId = Integer.parseInt(request.getParameter("branchId"));
	LibBranch a = AdminstratorService.getIntance().getBranchById(branchId);
%>
		<div class="modal-content">
			<form action="deleteBranch" method="post">
				<input type="text" style="width: 600px;" name="branchName"
					value=' You are about to delete <%=a.getBranchName()%>. Do you want to submit?'  /><br />
				<input type="hidden" name="branchId" value=<%=a.getBranchId()%>>
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
