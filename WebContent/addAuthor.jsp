<%@include file="includes.jsp"%>
<html>
<h2>Add Author</h2>
<div class="form-group">
	<form action="addAuthor" method="post">
		<input type="text" style="width: 400px;" name="authorName"
			class="form-control" placeholder="Enter Author's name" required
			autofocus /><br /> <input type="submit" class="btn btn-primary" />
	</form>
</div>
</html>