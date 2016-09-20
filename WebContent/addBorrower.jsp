<%@include file="includes.jsp"%>
<html>
<h2>Add Borrower</h2>
<div class="form-group">
	<form action="addBorrower" method="post">
		<input type="text" style="width: 500px;" name="name"
			class="form-control" placeholder="Enter borrower's name" required autofocus /><br /> 
			
			<input type="text" style="width: 500px;"name="address" class="form-control"
			placeholder="Enter borrower's Address" required autofocus /><br /> 
			
			<input type="text" style="width: 500px;" name="phone"
			class="form-control" placeholder="Enter borrower's Phone" required autofocus /><br /> 
			
			<input type="submit" class="btn btn-primary" />
	</form>
</div>
</html>
