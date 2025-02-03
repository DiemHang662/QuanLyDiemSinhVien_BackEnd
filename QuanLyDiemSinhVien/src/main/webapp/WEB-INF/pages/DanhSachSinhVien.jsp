<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sinh viên</title>
    <meta charset="UTF-8">
    <!-- Thêm liên kết đến Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            text-align: center;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        button {
            margin-bottom: 20px;
        }
        .btn-group {
            margin-bottom: 20px;
            width: 100%;
        }
        .btn-group .btn {
            margin-right: 20px;
            margin-left: 20px;
        }
        .search-form, .filter-form {
            margin-bottom: 20px;
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
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value='/'/>">Trang chủ <span class="sr-only">(current)</span></a>
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

    <div class="container mt-2">
        <h1 class="text-center mb-2">DANH SÁCH SINH VIÊN</h1>

        <!-- Search Form -->
        <div class="row mb-2">
            <div class="col-md-12">
                <form action="<c:url value='/search'/>" method="get" class="search-form">
                    <div class="input-group">
                        <input type="text" name="searchTerm" value="${searchTerm}" class="form-control" placeholder="Tìm kiếm sinh viên..." required>
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="submit">Tìm kiếm</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Filter Form -->
        <div class="row mb-2">
            <div class="col-md-12">
                <form action="<c:url value='/dssv'/>" method="get" class="filter-form">
                    <div class="form-group">
                        <label for="khoa">Chọn khoa:</label>
                        <select name="khoaId" class="form-control">
                            <option value="-1">Tất cả các khoa</option>
                            <c:forEach items="${khoaList}" var="khoa">
                                <option value="${khoa.id}" <c:if test="${khoa.id == selectedKhoaId}">selected</c:if>>${khoa.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Lọc</button>
                </form>
            </div>
        </div>

        <!-- Table of Students -->
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Ngày Sinh</th>
                    <th>Giới tính</th>
                    <th>Quê quán</th>
                    <th>Khoa</th>
                    <th>Ngành Đào Tạo</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sinhVien" items="${sinhVienList}">
                    <tr>
                        <td>${sinhVien.id}</td>
                        <td>${sinhVien.name}</td>
                        <td>${sinhVien.ngaySinh}</td> 
                        <td>${sinhVien.gioiTinh}</td> 
                        <td>${sinhVien.queQuan}</td> 
                        <td>${sinhVien.khoa.name}</td>
                        <td>${sinhVien.nganhDaoTao.name}</td> 
                        <td>                              
                            <a href="<c:url value='/chitietsinhvien?id=${sinhVien.id}'/>" class="btn btn-primary btn-sm">Chi tiết</a>
                            <form action="<c:url value='/sinhvien/${sinhVien.id}/delete'/>" method="post" style="display: inline-flex;">
                                <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                            </form>                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination Controls -->
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value='/dssv?khoaId=${selectedKhoaId}&page=${currentPage - 1}&size=${pageSize}'/>" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                </c:if>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                        <a class="page-link" href="<c:url value='/dssv?khoaId=${selectedKhoaId}&page=${i}&size=${pageSize}'/>">${i}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="<c:url value='/dssv?khoaId=${selectedKhoaId}&page=${currentPage + 1}&size=${pageSize}'/>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>

        <div class="btn-group" role="group">
            <a href="<c:url value='/'/>" class="btn btn-success btn-sm">Quay về trang chủ</a>
            <a href="<c:url value='/sinhvien/form'/>" class="btn btn-success btn-sm">Thêm</a>
        </div>
    </div>

    <!-- Thêm liên kết đến Bootstrap JS và các phụ thuộc -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
