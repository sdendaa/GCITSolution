<%@include file="includes.jsp"%>
<html>
<h2>Add Branch</h2>
<div class="form-group">
	<form action="addBranch" method="post">
	
		<input type="text" style="width: 500px;" name="branchName"
			class="form-control" placeholder="Enter branch's name" required autofocus /><br /> 
			
			<input type="text" style="width: 500px;"name="branchAddress" class="form-control"
			placeholder="Enter branch's Address" required autofocus /><br /> 
			
			<input type="submit" class="btn btn-primary" />
	</form>
</div>
</html>