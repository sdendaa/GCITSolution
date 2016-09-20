
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%
	int branchId = Integer.parseInt(request.getParameter("branchId"));
	LibBranch p = AdminstratorService.getIntance().getBranchById(branchId);
%>
		<div class="modal-content">
			<form action="editBranchLibr" method="post">
			
				<input type="text" style="width: 400px;" name="branchName"
					value='<%=p.getBranchName()%>' class="form-control"
					placeholder="Enter branch's name to Edit" required autofocus /><br />
					
					<input type="text" style="width: 400px;" name="branchAddress"
					value='<%=p.getBranchAddress()%>' class="form-control"
					placeholder="Enter branch's address to Edit" required autofocus /><br />
					
				<input type="hidden" name="branchId" value=<%=p.getBranchId()%>>
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