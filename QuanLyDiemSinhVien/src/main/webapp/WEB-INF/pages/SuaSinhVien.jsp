<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!DOCTYPE html>
<html>
<head>
    <title>Sửa sinh viên</title>
    <meta charset="UTF-8">
    <!-- Thêm liên kết đến Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
           <style>
        h1 {
           text-align: center;
        }
 
        button{
            text-align: center;
            align-items: center;
            margin: 20px 500px;
            width:150px;
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
                
    <div class="container mt-4">
        <h1>SỬA THÔNG TIN SINH VIÊN</h1>
        <form action="<c:url value='/sinhvien/${sinhVien.id}/saveOrUpdate'/>" method="post">
            <input type="hidden" name="id" value="${sinhVien.id}"/>
            
            <div class="form-group">
                <label for="name">Họ và tên:</label>
                <input type="text" id="name" name="name" class="form-control" value="${sinhVien.name}" />
                <span style="color:red">${error.name}</span>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" class="form-control" value="${sinhVien.email}" />
                <span style="color:red">${error.email}</span>
            </div>

            <div class="form-group">
                <label for="ngaySinh">Ngày sinh:</label>
                <input type="date" id="ngaySinh" name="ngaySinh" class="form-control" value="${sinhVien.ngaySinh}" />
                <span style="color:red">${error.ngaySinh}</span>
            </div>

            <div class="form-group">
                <label for="queQuan">Quê quán:</label>
                <input type="text" id="queQuan" name="queQuan" class="form-control" value="${sinhVien.queQuan}" />
                <span style="color:red">${error.queQuan}</span>
            </div>

            <div class="form-group">
                <label for="lopHoc">Lớp:</label>
                <select id="lopHoc" name="lopHoc.id" class="form-control">
                    <c:forEach items="${lopHocs}" var="lopHoc">
                        <option value="${lopHoc.id}" ${lopHoc.id == sinhVien.lopHoc.id ? 'selected' : ''}>${lopHoc.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="khoa">Khoa:</label>
                <select id="khoa" name="khoa.id" class="form-control">
                    <c:forEach items="${khoas}" var="khoa">
                        <option value="${khoa.id}" ${khoa.id == sinhVien.khoa.id ? 'selected' : ''}>${khoa.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="nganhDaoTao">Ngành đào tạo:</label>
                <select id="nganhDaoTao" name="nganhDaoTao.id" class="form-control">
                    <c:forEach items="${nganhDaoTaos}" var="nganhDaoTao">
                        <option value="${nganhDaoTao.id}" ${nganhDaoTao.id == sinhVien.nganhDaoTao.id ? 'selected' : ''}>${nganhDaoTao.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Giới tính:</label><br>
                <div class="form-check">
                    <input type="radio" id="gioiTinhNam" name="gioiTinh" value="Nam" class="form-check-input"
                           <c:if test="${sinhVien.gioiTinh == 'Nam'}">checked</c:if> />
                    <label for="gioiTinhNam" class="form-check-label">Nam</label>
                </div>
                <div class="form-check">
                    <input type="radio" id="gioiTinhNu" name="gioiTinh" value="Nu" class="form-check-input"
                           <c:if test="${sinhVien.gioiTinh == 'Nu'}">checked</c:if> />
                    <label for="gioiTinhNu" class="form-check-label">Nữ</label>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Lưu thông tin</button>
        </form>
    </div>

    <!-- Thêm liên kết đến Bootstrap JS và các phụ thuộc -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
