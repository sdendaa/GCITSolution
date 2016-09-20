<%@page import="libraryApp.service.LibrarianService"%>
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.LibBranch"%>

<%
LibrarianService libSer = new LibrarianService();
 String branchId = (String)request.getParameter("branchId");
 int num  = (branchId!=null) ? Integer.parseInt(branchId) : 0;
 LibBranch lb = AdminstratorService.getIntance().getBranchById(num);
%>

<div class="modal-body">
	<form action="updateBranchDetail" method="post">

		<table class="table">

			<tr>
				<th>Enter Branch Name: <input type="text" name="branchName"
					value=<%=lb.getBranchName()%>></th>
			</tr>

			<tr>
				<th>Enter Branch Address: <input type="text" name="branchAddress"
					value=<%=lb.getBranchAddress()%>></th>
			</tr>
			
		</table>

		<input type="hidden" name="branchId" value=<%=lb.getBranchId()%>>
		<input type="submit" />
	</form>
</div>

