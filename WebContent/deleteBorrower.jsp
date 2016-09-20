
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.Borrower"%>
<%
	int cardNo = Integer.parseInt(request.getParameter("cardNo"));
	Borrower b = AdminstratorService.getIntance().getBorrowerById(cardNo);
%>
		<div class="modal-content">
			<form action="deleteBorrower" method="post">
				<input type="text" style="width: 600px;" name="publisherName"
					value=' You are about to delete <%=b.getBorrowerName()%>. Do you want to submit?'  /><br />
				<input type="hidden" name="cardNo" value=<%=b.getCardNo()%>>
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
