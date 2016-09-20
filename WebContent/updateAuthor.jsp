<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="libraryApp.entity.Author"%>
<%
 String authorId = request.getParameter("authorId");
 List<Author>  aList = AdminstratorService.getIntance().getAllAuthors(1,  null);
%>
<html>
	<div class="modal-body">
	
	<form>
	Select Autho want to editr:
		<select name="authorId">
		<% for(Author a : aList) { %>
			<option value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%></option>
		<% } %>
	</select><br/>
	Enter Authors New Name: <input type="text" style="width: 200px;" name="	authorName" /><br/>
	<input type="submit"/>
	
	</form>
	</div>
</html>



