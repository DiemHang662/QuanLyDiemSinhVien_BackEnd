<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Đăng Ký Tài Khoản Sinh Viên</title>
        <style>
            body {
                background-color: #87CEEB; /* Màu nền của trang */
                font-family: Arial, sans-serif; /* Font chữ */
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .register-container {
                margin-top: 30px;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 600px; /* Đã thay đổi để chứa hai cột */
            }
            .register-container h2 {
                margin-top: 0;
                text-align: center;
                color: #4CAF50;
            }
            .register-container .form-row {
                display: flex;
                margin-bottom: 15px;
                justify-content: space-between;
            }
            .register-container .form-row > div {
                width: 48%; /* Đặt kích thước mỗi cột để có không gian cho hai cột */
            }
            .register-container label {
                display: block;
                margin-bottom: 5px;
            }
            .register-container input,
            .register-container select {
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .register-container button {
                background-color: #4CAF50; /* Màu nền của nút */
                color: white; /* Màu chữ của nút */
                border: none;
                padding: 10px;
                border-radius: 4px;
                cursor: pointer;
                width: 100%; /* Nút rộng bằng với chiều rộng của form */
            }
            .register-container button:hover {
                background-color: #45a049; /* Màu nền khi di chuột qua nút */
            }
            .register-container p {
                color: red; /* Màu của thông báo lỗi */
                text-align: center;
            }
            .register-container .login-link {
                text-align: center;
            }
            .register-container .login-link a {
                color: #4CAF50; /* Màu liên kết */
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <div class="register-container">
            <h2>ĐĂNG KÝ TÀI KHOẢN SINH VIÊN</h2>
            <form:form action="${pageContext.request.contextPath}/dangki" modelAttribute="sinhVien" method="post" enctype="multipart/form-data">
                <div class="form-row">
                    <div>
                        <form:label path="name">Họ và tên:</form:label>
                        <form:input path="name"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div>
                        <form:label path="queQuan">Quê Quán:</form:label>
                        <form:input path="queQuan"/>
                        <form:errors path="queQuan" cssClass="error"/>
                    </div>
                </div>
                <div class="form-row">
                    <div>
                        <form:label path="gioiTinh">Giới Tính:</form:label>
                        <form:select path="gioiTinh">
                            <form:option value="Nam">Nam</form:option>
                            <form:option value="Nu">Nữ</form:option>
                        </form:select>
                        <form:errors path="gioiTinh" cssClass="error"/>
                    </div>
                    <div>
                        <form:label path="ngaySinh">Ngày Sinh:</form:label>
                        <form:input path="ngaySinh" type="date"/>
                        <form:errors path="ngaySinh" cssClass="error"/>
                    </div>
                </div>
                <div class="form-row">
                    <div>
                        <form:label path="email">Email:</form:label>
                        <form:input path="email"/>
                        <form:errors path="email" cssClass="error"/>
                    </div>
                    <div>
                        <form:label path="username">Tên tài khoản:</form:label>
                        <form:input path="username"/>
                        <form:errors path="username" cssClass="error"/>
                    </div>
                </div>
                <div class="form-row">
                    <div>
                        <form:label path="password">Mật khẩu:</form:label>
                        <form:input path="password" type="password"/>
                        <form:errors path="password" cssClass="error"/>
                    </div>
                    <div>
                        <label for="file">Ảnh đại diện:</label>
                        <input type="file" id="file" name="file"/>
                    </div>
                </div>
                <div>
                    <button type="submit">Đăng Ký</button>
                </div>
            </form:form>

            <div class="login-link">
                <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập...</a>
            </div>

            <c:if test="${not empty errorMessage}">
                <p>${errorMessage}</p>
            </c:if>
        </div>
    </body>
</html>
