<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi Tiết Điểm</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0; /* Remove default margin */
            }

            .container {
                padding: 20px; /* Add padding for better spacing */
            }

            h1 {
                color: #4CAF50;
                text-align: center;
                margin-bottom: 20px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                font-weight: bold;
                margin-right: 10px;
            }

            .form-group input {
                width: 100%; /* Make input fields fill available space */
            }

            .btn {
                margin-top: 32px; /* Align button with input fields */
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            table, th, td {
                border: 1px solid #ddd;
            }

            th, td {
                padding: 10px;
                text-align: center;
            }

            th {
                background-color: #f2f2f2;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            tr:hover {
                background-color: #f1f1f1;
            }
            h3{
                margin-top:20px;
            }
        </style>
    </head>
    <body>
        <!-- Navbar Bootstrap -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <a class="navbar-brand" href="#">QUẢN LÝ ĐIỂM SINH VIÊN</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
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

        <div class="container">
            <h1>TRA CỨU ĐIỂM SINH VIÊN</h1>
            <form action="${pageContext.request.contextPath}/diem/details" method="get">
                <div class="form-row align-items-end">
                    <div class="form-group col-md-5">
                        <label for="sinhVienName">Tên sinh viên</label>
                        <select id="sinhVienName" name="sinhVienName" class="form-control">
                            <c:forEach var="sinhVien" items="${sinhVienList}">
                                <option value="${sinhVien.name}">${sinhVien.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-md-5">
                        <label for="monHocName">Tên môn học</label>
                        <select id="monHocName" name="monHocName" class="form-control">
                            <c:forEach var="monHoc" items="${monHocList}">
                                <option value="${monHoc.name}">${monHoc.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-md-5">
                        <label for="lopHocName">Tên lớp học</label>
                        <select id="lopHocName" name="lopHocName" class="form-control">
                            <c:forEach var="lopHoc" items="${lopHocList}">
                                <option value="${lopHoc.name}">${lopHoc.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-md-5">
                        <label for="hocKyName">Tên học kỳ</label>
                        <select id="hocKyName" name="hocKyName" class="form-control">
                            <c:forEach var="hocKy" items="${hocKyList}">
                                <option value="${hocKy.name}">${hocKy.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-md-1">
                        <input type="submit" value="Tra cứu" class="btn btn-success" />
                    </div>
                </div>
            </form>


            <c:if test="${diemList != null}">
                <h2>Kết Quả:</h2>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Sinh viên</th>
                            <th>Môn học</th>
                            <th>Điểm</th>
                            <th>Loại điểm</th>
                            <th>Học kỳ</th>
                            <th>Lớp học</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="diem" items="${diemList}">
                            <tr>
                                <td>${diem.id}</td>                
                                <td>${diem.sinhVien.name}</td>
                                <td>${diem.monHoc.name}</td>
                                <td>${diem.score}</td>
                                <td>${diem.loaiDiem.name}</td>
                                <td>${diem.hocKy.name}</td>
                                <td>${diem.lopHoc.name}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <h3>Điểm Trung Bình</h3>
            <p><strong>Điểm Trung Bình:</strong> ${averageScore != null ? averageScore : 'N/A'}</p>      
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
