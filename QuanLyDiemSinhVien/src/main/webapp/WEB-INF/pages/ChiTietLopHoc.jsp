<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chi Tiết Lớp Học</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .content {
                margin-top: 20px;
            }

            h1 {
                margin-bottom: 30px;
                text-align: center;
            }
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
            .btn-group {
                margin-bottom: 20px;
                width:100%;
            }
            .btn-group .btn {
                margin-right: 20px;
                margin-left: 20px;
            }
            .pagination {
                justify-content: center;
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

        <div class="container content">
            <div>
                <h1>THÔNG TIN LỚP HỌC</h1>
                <p><strong>Tên lớp: </strong> ${lopHoc.name}</p>
                <p><strong>Giảng Viên: </strong>
                    <c:forEach var="giangVienLopHoc" items="${giangVienLopHocList}">
                        ${giangVienLopHoc.giangVien.name}<c:if test="${not empty giangVienLopHocList && !loop.last}"> </c:if>
                    </c:forEach>
                </p>  
                <table class="table table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Email</th>
                            <th>Ngày Sinh</th>
                            <th>Giới Tính</th>
                            <th>Quê quán</th>
                            <th>Khoa</th>
                            <th>Ngành đào tạo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sinhVien" items="${sinhVienList}">
                            <tr>
                                <td>${sinhVien.id}</td>
                                <td>${sinhVien.name}</td>
                                <td>${sinhVien.email}</td>
                                <td>${sinhVien.ngaySinh}</td>
                                <td>${sinhVien.gioiTinh}</td>
                                <td>${sinhVien.queQuan}</td> 
                                <td>${sinhVien.khoa.name}</td>
                                <td>${sinhVien.nganhDaoTao.name}</td> 
                            </tr>
                        </c:forEach>
                        <c:if test="${empty sinhVienList}">
                            <tr>
                                <td colspan="8" class="text-center">Không có sinh viên nào</td>
                            </tr>
                        </c:if>
                    </tbody>

                </table>

                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="<c:url value='/lophoc/${lopHoc.id}?page=${currentPage - 1}&size=${pageSize}'/>" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                                <a class="page-link" href="<c:url value='/lophoc/${lopHoc.id}?page=${i}&size=${pageSize}'/>">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="<c:url value='/lophoc/${lopHoc.id}?page=${currentPage + 1}&size=${pageSize}'/>" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>


                <div class="btn-group" role="group">
                    <a href="<c:url value='/dslop'/>" class="btn btn-success">Trở về danh sách</a>
                    <a href="<c:url value='/lophoc/edit/${lopHoc.id}'/>" class="btn btn-primary">Thêm</a>
                </div>
            </div>
        </div>

        <!-- Thêm liên kết đến Bootstrap JS và các phụ thuộc -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
