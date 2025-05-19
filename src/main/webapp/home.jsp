<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String role = (String) session.getAttribute("role");
    String username = (String) session.getAttribute("username");

    String myId = (String) session.getAttribute("id");

    List<User> users = (List<User>) request.getAttribute("user");

    String editingId = (String) request.getParameter("edit"); // lấy user đang edit
    String successMessage = request.getParameter("success");
    String errorMessage = request.getParameter("error");

%>

    <html>
      <head>
        <title>Home Quan ly nguoi dung: <%= role %></title>
        <style>
          table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
          }
          th,
          td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
          }
          .pagination {
            margin-top: 20px;
            text-align: center;
          }
          .pagination a {
            margin: 0 5px;
            text-decoration: none;
          }
          input,
          select {
            padding: 5px;
            margin: 5px;
          }
          .form-inline {
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
            align-items: center;
          }
        </style>
      </head>
      <body>
        <h2>Home Quan ly nguoi dung : <%= role %></h2>

        <form action="/jsp-app/logout" method="GET">
          <button>Logout</button>
        </form>

        <% if (successMessage != null && !successMessage.isEmpty()) { %>
            <p style="color: green"><%= successMessage %></p>
        <% } %>
        
        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <p style="color: red"><%= errorMessage %></p>
        <% } %>

        <form action="/jsp-app/user" method="POST">
          <input name="username" placeholder="Username" value=<%= username %>>
          <input type="hidden" name="id" value="<%= myId %>">
          <input type="hidden" name="_method" value="PUT">
          <input type="hidden" name="_action" value="USER">
          <input type="hidden" name="id" value="<%= myId %>">
          <input name="password" placeholder="Password" />
          <button>Cap nhat tai khoan</button>
        </form>

    <% if("ADMIN".equalsIgnoreCase(role)) {%>

        <h2>Them moi nguoi dung</h2>

        <form action="/jsp-app/user" method="POST">
          <input name="username" placeholder="Username" />
          <input name="password" placeholder="Password" />
          <select name="role" placeholder="role">
            <option value="ADMIN">Admin</option>
            <option value="USER">User</option>
          </select>
          <input name="_method" type="hidden" value="POST" />
          <button>Add User</button>
        </form>

        <h2>Danh sach nguoi dung</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
               <% for (User user : users) {
                  boolean isEditing = String.valueOf(user.getId()).equals(editingId);
                  boolean isMe = String.valueOf(user.getId()).equals(myId);
               %>
                <tr>
                    <% if (isEditing) { %>
                    <form action="/jsp-app/user" method="post">
                        <td><input name="id" value="<%= user.getId() %>" readonly></td>
                        <td><input name="username" value="<%= user.getUsername() %>" readonly></td>
                        <td>
                            <select name="role" placeholder="role">
                                <option value="ADMIN">Admin</option>
                                <option value="USER">User</option>
                            </select>
                        </td>
                    <% if(isMe) {%>
                    <td>
                        <p>This is Me</p>
                    </td>

                <% } else { %>
                        <td>
                            <input name="_action" value="admin" type="hidden" readonly>
                            <input name="_method" value="put" type="hidden" readonly>
                            <button type="submit">Lưu</button>
                            <a href="/jsp-app/home">Hủy</a>
                        </td>
                        <% } %>
                    </form>
                           <% } else { %>
                    <td><%= user.getId() %></td>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getRole() %></td>
                    <% if(isMe) {%>
                    <td>
                        <p>This is Me</p>
                    </td>
                    <% } else {%>
                    <td>
                        <form action="/jsp-app/user" method="POST" style="display:inline;">
                            <input type="hidden" name="id" value="<%= user.getId() %>">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit">Xóa</button>
                        </form>
                        <a href="/jsp-app/home?&edit=<%= user.getId() %>">Sửa</a>
                    </td>
                    <% } %>
                <% } %>
                </tr>
               <% } %>
            </tbody>


        </table>


    <% } %>



</body>
</html>
