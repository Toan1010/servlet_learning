<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>



<form action="/jsp-app/login" method="POST">
    <h1>Login</h1>

    <label for="name">Username: </label>
    <input type="text" id="name" name="username" required/>
    <br/>

    <label for="password">Password: </label>
    <input type="password" id="password" name="password" required/>
    <br/>

<%-- Kiểm tra nếu có lỗi đăng nhập --%>
<%
    String error = request.getParameter("error");
    if ("true".equals(error)) {
%>
    <p style="color: red;">Sai thông tin đăng nhập!</p>
<%
    }
%>
    <button type="submit">Submit</button>
</form>

</body>
</html>
