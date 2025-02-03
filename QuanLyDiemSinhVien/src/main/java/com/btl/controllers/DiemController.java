package com.btl.controllers;

import com.btl.pojo.Diem;
import com.btl.pojo.DiemTrungBinh;
import com.btl.pojo.HocKy;
import com.btl.pojo.LoaiDiem;
import com.btl.pojo.LopHoc;
import com.btl.pojo.MonHoc;
import com.btl.pojo.SinhVien;
import com.btl.service.DiemService;
import com.btl.service.HocKyService;
import com.btl.service.LopHocService;
import com.btl.service.MonHocService;
import com.btl.service.SinhVienService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DiemController {

    private final DiemService diemService;
    private final MonHocService monHocService;
    private final LopHocService lopHocService;
    private final HocKyService hocKyService;
    private final SinhVienService sinhVienService;

    @Autowired
    public DiemController(DiemService diemService, MonHocService monHocService, HocKyService hocKyService, SinhVienService sinhVienService, LopHocService lopHocService) {
        this.diemService = diemService;
        this.monHocService = monHocService;
        this.sinhVienService = sinhVienService;
        this.lopHocService = lopHocService;
        this.hocKyService = hocKyService;
    }

    @GetMapping("/diem") // Xem điểm GK, CK
    public String showScoresBySubject(@RequestParam(value = "monHocId", required = false, defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", required = false, defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", required = false, defaultValue = "0") int hocKyId,
            Model model) {
        if (monHocId > 0 && lopHocId > 0) {
            List<Diem> diemList = diemService.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);
            List<MonHoc> monHocList = monHocService.getNameMonHoc();
            List<LopHoc> lopHocList = lopHocService.getNameLopHoc(); // Fixed service call
            List<HocKy> hocKyList = hocKyService.getNameHocKy(); // Fixed service call
            model.addAttribute("diemList", diemList);
            model.addAttribute("monHocList", monHocList);
            model.addAttribute("lopHocList", lopHocList);
            model.addAttribute("hocKyList", hocKyList);
            model.addAttribute("selectedMonHocId", monHocId);
            model.addAttribute("selectedLopHocId", lopHocId);
            model.addAttribute("selectedHocKyId", hocKyId);
        } else {
            model.addAttribute("error", "Both MonHocId and LopHocId are required");
            model.addAttribute("monHocList", monHocService.getNameMonHoc());
            model.addAttribute("lopHocList", lopHocService.getNameLopHoc()); // Ensure lopHocList is available
            model.addAttribute("hocKyList", hocKyService.getNameHocKy()); // Ensure lopHocList is available
        }
        return "DanhSachDiem";
    }

    // Xem điểm chi tiết của một SV
    @GetMapping("/diem/details")
    public String getDiemDetails(
            @RequestParam(value = "sinhVienName", required = false) String sinhVienName,
            @RequestParam(value = "monHocName", required = false) String monHocName,
            @RequestParam(value = "lopHocName", required = false) String lopHocName,
            @RequestParam(value = "hocKyName", required = false) String hocKyName,
            Model model) {

        List<Diem> diemList = null;
        Double averageScore = null;

        if (sinhVienName != null && monHocName != null && lopHocName != null && hocKyName != null) {
            // Lấy id của sinh viên, môn học, lớp học và học kỳ dựa trên tên
            Integer sinhVienId = sinhVienService.getIdByName(sinhVienName);
            Integer monHocId = monHocService.getIdByName(monHocName);
            Integer lopHocId = lopHocService.getIdByName(lopHocName);
            Integer hocKyId = hocKyService.getIdByName(hocKyName);

            if (sinhVienId != null && monHocId != null && lopHocId != null && hocKyId != null) {
                // Lấy danh sách điểm chi tiết
                diemList = diemService.getDiemBySinhVienIdAndMonHocIdAndLopHocIdAndHocKyId(sinhVienId, monHocId, lopHocId, hocKyId);

                // Lấy điểm trung bình
                averageScore = diemService.getAverageScoreForStudent(sinhVienId, monHocId, lopHocId, hocKyId);
            }
        }

        // Lấy danh sách cho các dropdown
        List<SinhVien> sinhVienList = sinhVienService.getNameSinhVien();
        List<MonHoc> monHocList = monHocService.getNameMonHoc();
        List<LopHoc> lopHocList = lopHocService.getNameLopHoc();
        List<HocKy> hocKyList = hocKyService.getNameHocKy();

        // Add attributes to the model
        model.addAttribute("sinhVienName", sinhVienName);
        model.addAttribute("monHocName", monHocName);
        model.addAttribute("lopHocName", lopHocName);
        model.addAttribute("hocKyName", hocKyName);
        model.addAttribute("diemList", diemList);
        model.addAttribute("averageScore", averageScore);

        model.addAttribute("sinhVienList", sinhVienList);
        model.addAttribute("monHocList", monHocList);
        model.addAttribute("lopHocList", lopHocList);
        model.addAttribute("hocKyList", hocKyList);

        return "TraCuuDiem";
    }

    @GetMapping("/DanhSachDTB") // Danh sách điểm trung bình
    public String getAllAverageScores(@RequestParam(value = "monHocId", required = false, defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", required = false, defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", required = false, defaultValue = "0") int hocKyId,
            Model model) {
        List<Object[]> averageScores = null;

        // Vérifier si les paramètres sont valides avant de récupérer les scores
        if (monHocId > 0 && lopHocId > 0 && hocKyId > 0) {
            averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);
        }

        // Récupération des listes de matières et de classes
        List<MonHoc> monHocList = monHocService.getNameMonHoc();
        List<LopHoc> lopHocList = lopHocService.getNameLopHoc();
        List<HocKy> hocKyList = hocKyService.getNameHocKy();

        // Ajouter les attributs au modèle
        model.addAttribute("monHocId", monHocId);
        model.addAttribute("lopHocId", lopHocId);
        model.addAttribute("hocKyId", hocKyId);
        model.addAttribute("averageScores", averageScores);
        model.addAttribute("monHocList", monHocList);
        model.addAttribute("lopHocList", lopHocList);
        model.addAttribute("hocKyList", hocKyList);
        // Retourner la vue JSP
        return "DanhSachDTB";
    }

    @GetMapping("/diem/form") // Form để nhập điểm
    public String showDiemForm(Model model) {
        model.addAttribute("diem", new Diem());
        model.addAttribute("sinhViens", diemService.getAllSinhViens());
        model.addAttribute("monHocs", diemService.getAllMonHocs());
        model.addAttribute("lopHocs", diemService.getAllLopHocs());
        model.addAttribute("hocKys", diemService.getAllHocKys());
        model.addAttribute("loaiDiems", diemService.getAllLoaiDiems());
        return "NhapDiem";
    }

    @PostMapping("/diem/save") // Lưu điểm
    public String saveDiem(@ModelAttribute Diem diem) {
        if (diem.getId() == 0) {
            diemService.saveDiem(diem);
        } else {
            diemService.updateDiem(diem);
        }
        return "redirect:/diem?monHocId=" + diem.getMonHoc().getId();
    }

    @GetMapping("/diem/export/csv")
    public void exportToCSV(@RequestParam(value = "monHocId", required = false, defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", required = false, defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", required = false, defaultValue = "0") int hocKyId,
            HttpServletResponse response) throws IOException {
        List<Diem> diemList = diemService.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=DiemTrungBinh.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Sinh vien,Lop hoc,Mon hoc,Hoc ky,Diem,Loai diem");
            for (Diem diem : diemList) {
                writer.printf("%s,%s,%s,%s,%.2f,%s%n",
                        diem.getSinhVien().getName(),
                        diem.getLopHoc().getName(),
                        diem.getMonHoc().getName(),
                        diem.getHocKy().getName(),
                        diem.getScore(),
                        diem.getLoaiDiem().getName());
            }
        }
    }

    @GetMapping("/diem/export/pdf")
    public void exportDiemToPdf(@RequestParam("monHocId") int monHocId,
            @RequestParam("lopHocId") int lopHocId,
            @RequestParam("hocKyId") int hocKyId,
            HttpServletResponse response) throws IOException {
        List<Diem> diemList = diemService.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);
        diemService.exportDiemToPdf(response, diemList);
    }

    @GetMapping("/exportAverageScoresToCsv") // Xuất csv điểm trung bình
    public void exportAverageScoresToCsv(@RequestParam(value = "monHocId", required = false, defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", required = false, defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", required = false, defaultValue = "0") int hocKyId,
            HttpServletResponse response) throws IOException {
        List<Object[]> averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);

        // Set the response headers
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=DiemTrungBinhSV.csv");

        // Write data to CSV
        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,Ho va ten,Diem trung binh");

            for (Object[] row : averageScores) {
                writer.printf("%s,%s,%.2f%n", row[0], row[1], row[2]);
            }
        }
    }
    
    @GetMapping("/exportAverageScoresToPdf")
    public void exportAverageScoresToPdf(
            @RequestParam int monHocId,
            @RequestParam int lopHocId,
            @RequestParam int hocKyId,
            HttpServletResponse response) throws IOException {

        // Retrieve the average scores from the service
        List<Object[]> averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);

        // Set up the response for PDF download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=DiemTrungBinh.pdf");

        // Export the data to PDF
        diemService.exportAverageScoresToPdf(response, averageScores);
    }
    
//   @GetMapping("/exportAverageScoresToPdf")
//public void exportAverageScoresToPdf(
//        @RequestParam(value = "monHocId", required = false, defaultValue = "0") int monHocId,
//        @RequestParam(value = "lopHocId", required = false, defaultValue = "0") int lopHocId,
//        @RequestParam(value = "hocKyId", required = false, defaultValue = "0") int hocKyId,
//        HttpServletResponse response) throws DocumentException, IOException {
//
//    List<Object[]> averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);
//
//    // Set the response headers
//    response.setContentType("application/pdf");
//    response.setHeader("Content-Disposition", "attachment; filename=DiemTrungBinhSV.pdf");
//
//    // Create a new PDF document
//    Document document = new Document();
//    try {
//        PdfWriter.getInstance(document, response.getOutputStream());
//        document.open();
//
//        // Add title
//        Paragraph title = new Paragraph("BẢNG ĐIỂM TRUNG BÌNH");
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        document.add(new Paragraph(" ")); // Add a blank line
//
//        // Create a table with 3 columns
//        PdfPTable table = new PdfPTable(3);
//        table.setWidthPercentage(100); // Set table width to 100% of page width
//        table.setSpacingBefore(10f);
//        table.setSpacingAfter(10f);
//        table.addCell("ID");
//        table.addCell("Họ và tên");
//        table.addCell("Điểm trung bình");
//
//        // Add rows to the table
//        for (Object[] row : averageScores) {
//            table.addCell(String.valueOf(row[0]));  // Student ID
//            table.addCell(String.valueOf(row[1]));  // Student Name
//            table.addCell(String.format("%.2f", row[2]));  // Average Score
//        }
//
//        // Add the table to the document
//        document.add(table);
//    } catch (Exception e) {
//        e.printStackTrace();  // Log the exception
//        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while generating the PDF.");
//    } finally {
//        document.close();  // Ensure the document is closed properly
//    }
//}

}
