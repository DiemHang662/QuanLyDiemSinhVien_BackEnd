<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập</title>
        <style>
            body {
                background-color: #87CEEB;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .login-container {
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 350px;
            }
            .login-container h2 {
                margin-top: 0;
                text-align: center;
            }
            .login-container div {
                margin-bottom: 15px;
            }
            .login-container label {
                display: block;
                margin-bottom: 5px;
            }
            .login-container input,
            .login-container select {
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .login-container button {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px;
                border-radius: 4px;
                cursor: pointer;
                margin-left: 120px;
            }
            .login-container button:hover {
                background-color: #45a049;
            }
            .login-container p {
                color: red;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>ĐĂNG NHẬP</h2>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div>
                    <label for="username">Tên tài khoản</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div>
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div>
                    <label for="userRole">Vai trò</label>
                    <select id="userRole" name="userRole" required>
                        <option value="">-- Chọn vai trò --</option>
                        <option value="ROLE_SINHVIEN">Sinh viên</option>
                        <option value="ROLE_GIANGVIEN">Giảng viên</option>
                        <option value="ROLE_GIAOVU">Giáo vụ</option>
                    </select>
                </div>
                <div>
                    <button type="submit">Đăng nhập</button>
                </div>

                <div class="mb-4">
                    <a href="${pageContext.request.contextPath}/dangki">Chưa có tài khoản? Đăng kí...</a>
                </div>
            </form>
            <c:if test="${param.error}">
                <p><fmt:message key="error.invalid_credentials" /></p>
            </c:if>
        </div>
    </body>
</html>
