<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Báo cáo thống kê</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .chart-container {
            margin: 20px 40px;
        }

        h2 {
            text-align: center;
        }

        .small-table {
            width: 50%;
            margin: 0 auto;
            text-align: center;
            border-collapse: collapse;
        }
        .small-table th, .small-table td {
            border: 1px solid #dee2e6;
            padding: 8px;
        }

        .small-table thead th {
            background-color: #f8f9fa;
        }

        .small-table tbody tr:nth-child(odd) {
            background-color: #f2f2f2;
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
        <h2 class="mb-4">BÁO CÁO THỐNG KÊ</h2>

        <form action="${pageContext.request.contextPath}/bctk" method="get" class="mb-4">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="monHocId"><strong>Chọn môn học: </strong></label>
                    <select name="monHocId" id="monHocId" class="form-control" onchange="this.form.submit()">
                        <option value=""> --- Môn học ---</option>
                        <c:forEach var="monHoc" items="${monHocs}">
                            <option value="${monHoc.id}" <c:if test="${monHoc.id == selectedMonHocId}">selected</c:if>>
                                ${monHoc.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <label for="hocKyId"><strong>Chọn học kỳ: </strong></label>
                    <select name="hocKyId" id="hocKyId" class="form-control" onchange="this.form.submit()">
                        <option value=""> --- Học kỳ ---</option>
                        <c:forEach var="hocKy" items="${hocKys}">
                            <option value="${hocKy.id}" <c:if test="${hocKy.id == selectedHocKyId}">selected</c:if>>
                                ${hocKy.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>

        <!-- Table to display detailed report -->
        <c:if test="${not empty lopHocScores}">
            <table class="table table-striped small-table mt-2">
                <thead>
                    <tr>
                        <th>Lớp học</th>
                        <th>Điểm trung bình</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${lopHocScores}">
                        <tr>
                            <td>${entry.key}</td>
                            <td>${entry.value}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty lopHocScores}">
            <p>Không có dữ liệu để hiển thị.</p>
        </c:if>

        <c:if test="${not empty lopHocScores}">
            <div class="chart-container">
                <canvas id="myChart" width="300" height="150"></canvas>
            </div>
            <script>
                var ctx = document.getElementById('myChart').getContext('2d');
                var colors = ['rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(144, 238, 144, 0.2)'];
                var borderColors = ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(144, 238, 144, 1)'];

                var dataCount = ${fn:length(lopHocScores)};
                var chartColors = [];
                var chartBorderColors = [];

                for (var i = 0; i < dataCount; i++) {
                    chartColors.push(colors[i % colors.length]);
                    chartBorderColors.push(borderColors[i % borderColors.length]);
                }

                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: [
                            <c:forEach var="entry" items="${lopHocScores}" varStatus="entryStatus">
                                "${entry.key}"<c:if test="${not entryStatus.last}">,</c:if>
                            </c:forEach>
                        ],
                        datasets: [{
                            label: 'Điểm trung bình cao nhất của lớp',
                            data: [
                                <c:forEach var="entry" items="${lopHocScores}" varStatus="entryStatus">
                                    ${entry.value}<c:if test="${not entryStatus.last}">,</c:if>
                                </c:forEach>
                            ],
                            backgroundColor: chartColors,
                            borderColor: chartBorderColors,
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            </script>
        </c:if>
    </div>
</body>
</html>
