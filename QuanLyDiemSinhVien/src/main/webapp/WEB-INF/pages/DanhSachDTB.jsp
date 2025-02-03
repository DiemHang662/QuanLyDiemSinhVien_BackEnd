<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh Sách Điểm Trung Bình</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        h1 {
            text-align: center;
            color: #4CAF50;
        }
        .btn-group {
            margin-bottom: 20px;
            width: 100%;
        }
        .btn-group .btn {
            margin-right: 20px;
            margin-left: 20px;
        }
        .form-control-plaintext {
            width: 100px;
            margin: 0 auto;
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
        <h1 class="mb-4">DANH SÁCH ĐIỂM TRUNG BÌNH MÔN HỌC</h1>

        <!-- Form to select subject and class -->
        <form action="${pageContext.request.contextPath}/DanhSachDTB" method="get" class="mb-4">
            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="monHocId"><strong>Môn học</strong></label>
                    <select id="monHocId" name="monHocId" class="form-control" onchange="this.form.submit()">
                        <c:forEach items="${monHocList}" var="monHoc">
                            <option value="${monHoc.id}" ${monHoc.id == monHocId ? 'selected' : ''}>
                                ${monHoc.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="lopHocId"><strong>Lớp học</strong></label>
                    <select id="lopHocId" name="lopHocId" class="form-control" onchange="this.form.submit()">
                        <c:forEach items="${lopHocList}" var="lopHoc">
                            <option value="${lopHoc.id}" ${lopHoc.id == lopHocId ? 'selected' : ''}>
                                ${lopHoc.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
               
                 <div class="col-md-4 mb-3">
                    <label for="hocKyId"><strong>Học kỳ</strong></label>
                    <select id="hocKyId" name="hocKyId" class="form-control" onchange="this.form.submit()">
                        <c:forEach items="${hocKyList}" var="hocKy">
                            <option value="${hocKy.id}" ${hocKy.id == hocKyId ? 'selected' : ''}>
                                ${hocKy.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>

        <!-- Table for Average Scores -->
        <h2 class="mb-4">ĐIỂM TRUNG BÌNH</h2>
        <c:if test="${not empty averageScores}">
            <table class="table table-striped table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>Sinh viên</th>
                        <th>Lớp học</th>
                        <th>Điểm trung bình</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${averageScores}" var="score">
                        <tr>
                            <td>${score[0]}</td> 
                            <td>${score[1]}</td> 
                            <td>
                                <input type="text" value="${score[2]}" class="form-control-plaintext" readonly />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty averageScores}">
            <p>Không có điểm trung bình nào để hiển thị.</p>
        </c:if>

        <!-- Navigation buttons -->
        <div class="btn-group" role="group">
            <a href="<c:url value='/diem'/>" class="btn btn-success">Trở về</a>
            <!-- Export Button -->
            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#exportModal">
                Xuất bảng điểm
            </button>
        </div>

        <div class="modal fade" id="exportModal" tabindex="-1" role="dialog" aria-labelledby="exportModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exportModalLabel">Chọn định dạng xuất</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <a href="${pageContext.request.contextPath}/exportAverageScoresToCsv?monHocId=${monHocId}&lopHocId=${lopHocId}&hocKyId=${param.hocKyId}" class="btn btn-info">Xuất CSV</a>
                        <a href="${pageContext.request.contextPath}/exportAverageScoresToPdf?monHocId=${monHocId}&lopHocId=${lopHocId}&hocKyId=${param.hocKyId}" class="btn btn-danger">Xuất PDF</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
