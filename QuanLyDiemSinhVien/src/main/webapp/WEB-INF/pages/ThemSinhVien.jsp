<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm Sinh Viên</title>
    <meta charset="UTF-8">
    <!-- Thêm liên kết đến Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 800px;
        }
        .form-group label {
            font-weight: bold;
        }
        .form-group {
            margin-bottom: 1.5rem;
        }
        .btn-group {
            margin-top: 1rem;
        }
        
        h1{
            color: #4CAF50;
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
        <h1 class="text-center mb-4">THÊM SINH VIÊN MỚI</h2>

        <c:url value="/sinhvien/saveOrUpdate" var="saveOrUpdateUrl" />
        <form:form method="post" action="${saveOrUpdateUrl}" modelAttribute="sinhVien" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Họ và Tên:</label>
                <form:input id="name" path="name" class="form-control" />
                <form:errors path="name" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <form:input id="email" path="email" class="form-control" />
                <form:errors path="email" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="ngaySinh">Ngày Sinh:</label>
                <form:input id="ngaySinh" path="ngaySinh" type="date" class="form-control" />
                <form:errors path="ngaySinh" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="queQuan">Quê Quán:</label>
                <form:input id="queQuan" path="queQuan" class="form-control" />
                <form:errors path="queQuan" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="gioiTinh">Giới Tính:</label>
                <form:select id="gioiTinh" path="gioiTinh" class="form-control">
                    <form:option value="Nam">Nam</form:option>
                    <form:option value="Nữ">Nữ</form:option>
                </form:select>
                <form:errors path="gioiTinh" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="lopHoc">Lớp Học:</label>
                <form:select id="lopHoc" path="lopHoc.id" class="form-control">
                    <form:options items="${lopHocs}" itemValue="id" itemLabel="name" />
                </form:select>
                <form:errors path="lopHoc.id" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="khoa">Khoa:</label>
                <form:select id="khoa" path="khoa.id" class="form-control">
                    <form:options items="${khoas}" itemValue="id" itemLabel="name" />
                </form:select>
                <form:errors path="khoa.id" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <label for="nganhDaoTao">Ngành Đào Tạo:</label>
                <form:select id="nganhDaoTao" path="nganhDaoTao.id" class="form-control">
                    <form:options items="${nganhDaoTaos}" itemValue="id" itemLabel="name" />
                </form:select>
                <form:errors path="nganhDaoTao.id" cssClass="text-danger" />
            </div>
            <div class="form-group">
                <input type="submit" class="btn btn-primary" value="Lưu" />
            </div>
        </form:form>

        <div class="btn-group" role="group">
            <a href="${pageContext.request.contextPath}/dssv" class="btn btn-secondary">Quay lại danh sách sinh viên</a>
        </div>
    </div>

    <!-- Thêm liên kết đến Bootstrap JS và các phụ thuộc -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
