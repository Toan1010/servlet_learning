<%@ page import="models.Flight" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    int currentPage = (int) request.getAttribute("currentPage");
    int totalPages = (int) request.getAttribute("totalPages");
    String editingId = request.getParameter("edit"); // lấy chuyến bay đang edit
%>

<html>
<head>
    <title>Quản lý chuyến bay</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { margin: 0 5px; text-decoration: none; }
        input, select { padding: 5px; margin: 5px; }
        .form-inline { display: flex; flex-wrap: wrap; gap: 5px; align-items: center; }
    </style>
</head>
<body>

<h2>Danh sách chuyến bay</h2>

<form action="/jsp-app/logout" method="GET">
    <button>Logout</button>
</form>

<!-- Add New Flight -->
<h3>Thêm chuyến bay</h3>
<form class="form-inline" method="post" action="/jsp-app/flight/add">
    <input name="macb" placeholder="Mã chuyến bay" required>
    <input name="gadi" placeholder="Ga đi" required>
    <input name="gaden" placeholder="Ga đến" required>
    <input name="dodai" placeholder="Độ dài" type="number" required>
    <input name="giodi" placeholder="Giờ đi (HH:mm:ss)" required>
    <input name="gioden" placeholder="Giờ đến (HH:mm:ss)" required>
    <input name="chiphi" placeholder="Chi phí" type="number" required>
    <button type="submit">Thêm</button>
</form>

<!-- Flight List Table -->
<table>
    <thead>
        <tr>
            <th>Mã CB</th>
            <th>Ga đi</th>
            <th>Ga đến</th>
            <th>Độ dài</th>
            <th>Giờ đi</th>
            <th>Giờ đến</th>
            <th>Chi phí</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <% for (Flight flight : flights) {
            boolean isEditing = flight.getMacb().equals(editingId);
        %>
            <tr>
            <% if (isEditing) { %>
                <form action="/jsp-app/flight/update" method="post">
                    <td><input name="macb" value="<%= flight.getMacb() %>" readonly></td>
                    <td><input name="gadi" value="<%= flight.getGadi() %>"></td>
                    <td><input name="gaden" value="<%= flight.getGaden() %>"></td>
                    <td><input name="dodai" type="number" value="<%= flight.getDodai() %>"></td>
                    <td><input name="giodi" value="<%= flight.getGiodi() %>"></td>
                    <td><input name="gioden" value="<%= flight.getGioden() %>"></td>
                    <td><input name="chiphi" type="number" value="<%= flight.getChiphi() %>"></td>
                    <td>
                        <button type="submit">Lưu</button>
                        <a href="/jsp-app/home?page=<%= currentPage %>">Hủy</a>
                    </td>
                </form>
            <% } else { %>
                <td><%= flight.getMacb() %></td>
                <td><%= flight.getGadi() %></td>
                <td><%= flight.getGaden() %></td>
                <td><%= flight.getDodai() %></td>
                <td><%= flight.getGiodi() %></td>
                <td><%= flight.getGioden() %></td>
                <td><%= flight.getChiphi() %></td>
                <td>
                    <form action="/jsp-app/flight/delete" method="post" style="display:inline;">
                        <input type="hidden" name="macb" value="<%= flight.getMacb() %>">
                        <button type="submit">Xóa</button>
                    </form>
                    <a href="/jsp-app/home?page=<%= currentPage %>&edit=<%= flight.getMacb() %>">Sửa</a>
                </td>
            <% } %>
            </tr>
        <% } %>
    </tbody>
</table>

<!-- Pagination -->
<div class="pagination">
    <% for (int i = 1; i <= totalPages; i++) { %>
        <% if (i == currentPage) { %>
            <strong><%= i %></strong>
        <% } else { %>
            <a href="/jsp-app/home?page=<%= i %>"><%= i %></a>
        <% } %>
    <% } %>
</div>

</body>
</html>
