<%@include file="includes.jsp"%>
<%String action = request.getParameter("action");%>

<html>
<h1>Welcome to Login:</h1>
<div class="modal-body">
<form action="cardAuthenB" method="post">
		Enter your card number: <input type="text" name="cardNo"/>
		<input type="submit"/>
		<input type="hidden" name="action" value=<%=action%>>
</form>
</div>
</html>