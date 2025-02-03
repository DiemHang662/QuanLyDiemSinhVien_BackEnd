<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Danh Sách Câu Trả Lời</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            .navbar {
                margin-bottom: 20px;
            }
            .card {
                margin-bottom: 15px;
                border: 1px solid #e0e0e0;
            }
            .card-header {
                background-color: #F5F5F5;
                color: black;
                font-size: 1rem;
                font-weight: bold;
            }
            .card-body {
                font-size: 0.95rem;
                color: #333;
            }
            .form-control {
                border-radius: 3px;
            }
            .btn-primary, .btn-secondary {
                border-radius: 3px;
                font-size: 0.9rem;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
            }
            .container h1, .container h4 {
                color: #333;
                text-align: center;
            }
            .container {
                max-width: 800px;
            }
            textarea.form-control {
                resize: vertical;
            }
            
            h1{
                color: #4CAF50;
            }
        </style>
    </head>
    <body>
         <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <a class="navbar-brand" href="#">QUẢN LÝ ĐIỂM SINH VIÊN</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value='/'/>">Trang chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/dssv'/>">Sinh viên</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/dslop'/>">Lớp</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/diem'/>">Điểm</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/bctk'/>">Báo cáo thống kê</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/logout'/>">Đăng xuất</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container mt-4">
            <h1>DIỄN ĐÀN THẢO LUẬN MÔN HỌC</h1>
            <div class="row">
                <c:forEach var="traLoi" items="${traLoiDienDans}">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <c:choose>
                                    <c:when test="${not empty traLoi.sinhVien}">
                                        Sinh viên: ${traLoi.sinhVien.name}
                                    </c:when>
                                    <c:otherwise>
                                        Giảng viên: ${traLoi.giangVien.name}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="card-body">
                                <p class="card-text">${traLoi.content}</p>             
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="mt-4">
                <h4>Gửi Trả Lời</h4>
                <form action="<c:url value='/diendan/${dienDanId}/tra-loi'/>" method="post">
                    <div class="form-group">
                        <label for="content">Nội dung</label>
                        <textarea class="form-control" id="content" name="content" rows="4" required></textarea>
                    </div>
                    <input type="hidden" name="sinhVienId" value="${sessionScope.sinhVienId}"/>
                    <div class="d-flex justify-content-between mt-3">
                        <button type="submit" class="btn btn-primary">Gửi Trả Lời</button>
                        <a href="<c:url value='/diendan/${dienDanId}'/>" class="btn btn-success">Trở Về</a>
                    </div>
                </form>
            </div>

            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
