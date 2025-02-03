<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chi Tiết Sinh Viên</title>
        <meta charset="UTF-8">
        <!-- Thêm liên kết đến Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
     <style>
        .content {
            margin-top: 20px;
        }
        .btn-group {
            margin-bottom: 20px;
            width:100%;
        }
       .btn-group .btn {
            margin-right: 20px; 
            margin-left: 20px;
        }
       .avatar-container {
            text-align: center;
            margin-bottom: 20px;
        }
        .avatar-container img {
            width: 300px;
            height: 300px;
            margin-top: 50px;
        }
        .details-container {
            margin-bottom: 20px;
            margin-left: 50px;
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

        <div class="container mt-4">
            <h1 class="text-center mb-4">THÔNG TIN SINH VIÊN</h1>
            <div class="card">
                <div class="card-body row">
                    <!-- Student Details Section (Left) -->
                    <div class="col-md-6 details-container">
                        <p><strong>ID:</strong> ${sinhVien.id}</p>
                        <p><strong>Họ và tên:</strong> ${sinhVien.name}</p>
                        <p><strong>Email:</strong> ${sinhVien.email}</p>
                        <p><strong>Ngày sinh:</strong> ${sinhVien.ngaySinh}</p>
                        <p><strong>Giới tính:</strong> ${sinhVien.gioiTinh}</p>
                        <p><strong>Quê quán:</strong> ${sinhVien.queQuan}</p>
                        <p><strong>Lớp:</strong> ${sinhVien.lopHoc.name}</p>
                        <p><strong>Khoa:</strong> ${sinhVien.khoa.name}</p>
                        <p><strong>Ngành đào tạo:</strong> ${sinhVien.nganhDaoTao.name}</p>
                        <a href="<c:url value='/sinhvien/monhoc?sinhVienId=${sinhVien.id}'/>">Xem Các Môn Học</a>
                    </div>

                    <!-- Avatar Section (Right) -->
                    <div class="col-md-4 avatar-container">
                        <img src="${sinhVien.avatar}" alt="Avatar Sinh Viên">
                    </div>
                </div>

                <div class="btn-group" role="group">
                    <a href="<c:url value='/dssv'/>" class="btn btn-success">Trở về danh sách</a>
                    <a href="<c:url value='/sinhvien/${sinhVien.id}/saveOrUpdate'/>" class="btn btn-warning">Sửa thông tin</a>
                </div>
            </div>
        </div>

        <!-- Thêm liên kết đến Bootstrap JS và các phụ thuộc -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
