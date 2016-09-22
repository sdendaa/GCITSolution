<%@page import="libraryApp.entity.Publisher"%>
<%@page import="libraryApp.entity.Author"%>
<%@page import="libraryApp.entity.LibBranch"%>
<%@page import="libraryApp.service.AdminstratorService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
	/* List<Author> authors = new ArrayList<>();
	authors = AdminstratorService.getIntance().getAllAuthors(1, null);
	List<Publisher> publishers = new ArrayList<>();
			publishers = AdminstratorService.getIntance().getAllPublishers();
 */	
 
 AdminstratorService service = new AdminstratorService();
	List<Author> authorList = new ArrayList<>();
			authorList = service.getAllAuthors(1, null);
	List<Publisher> pubList =new ArrayList<>();
			pubList = AdminstratorService.getIntance().getAllPublishers(1, null);
	List<LibBranch> branchList =new ArrayList<>();
			branchList = AdminstratorService.getIntance().getAllBranchs(1, null);
		
	%>	
	
<html>
<head>
<link rel="stylesheet" type="text/css" href="myStyle.css">
</head>
<h2>Add Book</h2>
<form action="addBook" method="post">
	Title: <input type="text" name="bookTitle" /><br />
	 Select Author: <select
		name="authorId" id="selectAuthor">
		<% for(Author a : authorList) { %>
		<option value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%></option>
		<% 
		} %>
	</select><br /> 
	Select Publisher: 
	<select name="pubId" id="selectPub">
		<% for(Publisher p : pubList) { %>
		<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%></option>
		<% 
		} %>
	</select><br /> 
	Select Branch: 
	<select name="branchId" id="selectBr">
		<% for(LibBranch br : branchList) { %>
		<option value="<%=br.getBranchId()%>"><%=br.getBranchName()%></option>
		<% 
		} %>
	</select><br />
	
	<%--  <input type="hidden" name="authorId" value=<%=authorId%>>
	<input type="hidden" name="publisherId" value=<%=publisherId%>> 
	<input type="hidden" name="branchId" value=<%=branchId%>>  --%>
	<input type="submit" value="AddBook" class="btn btn-primary" /> 
	<!-- <input type="submit"  class="btn btn-primary" value="AddBook"
		 onclick="javascript:location.href='addNoCopies.jsp?'"> -->
</form>
</html>