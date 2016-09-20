
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="libraryApp.entity.Borrower"%>
<%
	int cardNo = Integer.parseInt(request.getParameter("cardNo"));
	Borrower bo = AdminstratorService.getIntance().getBorrowerById(cardNo);
%>
		<div class="modal-content">
			<form action="editBorrower" method="post">
			
				<input type="text" style="width: 400px;" name="name"
					value='<%=bo.getBorrowerName()%>' class="form-control"
					placeholder="Enter Borrower's name to Edit" required autofocus /><br />
					
					<input type="text" style="width: 400px;" name="address"
					value='<%=bo.getBorrowerAddress()%>' class="form-control"
					placeholder="Enter borrower's address to Edit" required autofocus /><br />
					
					<input type="text" style="width: 400px;" name="phone"
					value='<%=bo.getBorrowerPhone()%>' class="form-control"
					placeholder="Enter borrower's phone to Edit" required autofocus /><br />
					
				<input type="hidden" name="cardNo" value=<%=bo.getCardNo()%>>
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



