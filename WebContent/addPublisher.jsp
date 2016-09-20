<%@include file="includes.jsp"%>
<html>
<h2>Add Publisher</h2>
<div class="form-group">
	<form action="addPublisher" method="post">
		<input type="text" style="width: 500px;" name="publisherName"
			class="form-control" placeholder="Enter Publisher's name" required
			autofocus /><br /> <input type="text" style="width: 500px;"
			name="publisherAddress" class="form-control"
			placeholder="Enter Publisher's Address" required autofocus /><br /> <input
			type="text" style="width: 500px;" name="publisherPhone"
			class="form-control" placeholder="Enter Publisher's Phone" required
			autofocus /><br /> <input type="submit" class="btn btn-primary" />
	</form>
</div>
</html>

