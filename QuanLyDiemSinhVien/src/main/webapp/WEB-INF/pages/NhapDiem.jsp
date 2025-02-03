<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nhập Điểm Sinh Viên</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        h2{
            text-align: center;
            margin-top: 20px;
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

<div class="container">
    <h2>NHẬP ĐIỂM SINH VIÊN</h2>
    <form action="<c:url value='/diem/save'/>" method="post">
            <input type="hidden" name="id" value="${diem.id}"/>
        <!-- Semester Selection -->
        <div class="form-group">
            <label for="hocKy">Học kỳ:</label>
            <select id="hocKy" name="hocKy.id" class="form-control">
                <c:forEach items="${hocKys}" var="hocKy">
                   <option value="${hocKy.id}" ${hocKy.id == diem.hocKy.id ? 'selected' : ''}>${hocKy.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="sinhVien">Sinh viên:</label>
            <select id="sinhVien" name="sinhVien.id" class="form-control">
                <c:forEach items="${sinhViens}" var="sinhVien">
                    <option value="${sinhVien.id}" ${sinhVien.id == diem.sinhVien.id ? 'selected' : ''}>${sinhVien.name}</option>
                </c:forEach>
            </select>
        </div>
        
           <div class="form-group">
            <label for="lopHoc">Lớp học:</label>
            <select id="lopHoc" name="lopHoc.id" class="form-control">
                <c:forEach items="${lopHocs}" var="lopHoc">
                   <option value="${lopHoc.id}" ${lopHoc.id == diem.lopHoc.id ? 'selected' : ''}>${lopHoc.name}</option>
                </c:forEach>
            </select>
        </div>


        <div class="form-group">
            <label for="monHoc">Môn học:</label>
            <select id="monHoc" name="monHoc.id" class="form-control">
                <c:forEach items="${monHocs}" var="monHoc">
                    <option value="${monHoc.id}" ${monHoc.id == diem.monHoc.id ? 'selected' : ''}>${monHoc.name}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Score Type Selection -->
        <div class="form-group">
            <label for="loaiDiem">Loại điểm:</label>
            <select id="loaiDiem" name="loaiDiem.id" class="form-control">
                <c:forEach items="${loaiDiems}" var="loaiDiem">
                    <option value="${loaiDiem.id}" ${loaiDiem.id == diem.loaiDiem.id ? 'selected' : ''}>${loaiDiem.name}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Score Input -->
        <div class="form-group">
           <label for="score">Điểm:</label>
           <div>
                 <input type="text" name="score" value="${diem.score.toString()}" />
           </div>
        </div>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary">Lưu thông tin</button>
    </form>
</div>
          <!-- Thêm liên kết đến Bootstrap JS và các phụ thuộc -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
