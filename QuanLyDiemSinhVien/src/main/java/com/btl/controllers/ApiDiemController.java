package com.btl.controllers;

import com.btl.pojo.Diem;
import com.btl.pojo.MonHoc;
import com.btl.pojo.LopHoc;
import com.btl.service.DiemService;
import com.btl.service.HocKyService;
import com.btl.service.LopHocService;
import com.btl.service.MonHocService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/diem")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiDiemController {

    private final DiemService diemService;
    private final MonHocService monHocService;
    private final LopHocService lopHocService;
    private final HocKyService hocKyService;


    @Autowired
    public ApiDiemController(DiemService diemService, MonHocService monHocService, LopHocService lopHocService, HocKyService hocKyService) {
        this.diemService = diemService;
        this.monHocService = monHocService;
        this.lopHocService = lopHocService;
        this.hocKyService = hocKyService;
    }

    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('SINHVIEN')")
    @GetMapping("/scores")
    public ResponseEntity<List<Diem>> getScoresBySubjectAndClassAndSemester(
            @RequestParam(value = "monHocId", defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", defaultValue = "0") int hocKyId ){
        if (monHocId > 0 && lopHocId > 0 && hocKyId > 0) {
            List<Diem> diemList = diemService.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);
            return ResponseEntity.ok(diemList);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

     @PreAuthorize("hasRole('GIANGVIEN') or hasRole('SINHVIEN')")
    @GetMapping("/average-scores")
    public ResponseEntity<Map<String, Object>> getAllAverageScores(
            @RequestParam(value = "monHocId", defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", defaultValue = "0") int hocKyId){
        List<Object[]> averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);
        return ResponseEntity.ok(Map.of(
                "monHocId", monHocId,
                "lopHocId", lopHocId,
                "hocKyId", hocKyId,
                "averageScores", averageScores
        ));
    }

    @PreAuthorize("hasRole('GIANGVIEN') or hasRole('SINHVIEN')")
     @GetMapping("/average-score-bySV")
    public ResponseEntity<Double> getAverageScore(
            @RequestParam(value = "sinhVienId") Integer sinhVienId,
            @RequestParam(value = "monHocId") Integer monHocId,
            @RequestParam(value = "lopHocId") Integer lopHocId,
            @RequestParam(value = "hocKyId") Integer hocKyId){
        if (sinhVienId != null && monHocId != null && lopHocId != null && hocKyId != null) {
            Double averageScore = diemService.getAverageScoreForStudent(sinhVienId, monHocId, lopHocId, hocKyId);
            return ResponseEntity.ok(averageScore);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

 @GetMapping("/form") 
  @PreAuthorize("hasRole('GIANGVIEN')")
    public ResponseEntity<Map<String, Object>> showDiemForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("diem", new Diem());
        response.put("sinhViens", diemService.getAllSinhViens());
        response.put("monHocs", diemService.getAllMonHocs());
        response.put("lopHocs", diemService.getAllLopHocs());
        response.put("hocKys", diemService.getAllHocKys());
        response.put("loaiDiems", diemService.getAllLoaiDiems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save") 
     @PreAuthorize("hasRole('GIANGVIEN')")
    public ResponseEntity<String> saveDiem(@RequestBody Diem diem) {
        try {
            if (diem.getId() == 0) {
                diemService.saveDiem(diem);
            } else {
                diemService.updateDiem(diem);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Diem saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving diem: " + e.getMessage());
        }
    }

    @GetMapping("/export/csv")
     @PreAuthorize("hasRole('GIANGVIEN')")
    public void exportScoresToCSV(
            @RequestParam(value = "monHocId", defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", defaultValue = "0") int lopHocId,
            @RequestParam(value = "hocKyId", defaultValue = "0") int hocKyId,
            HttpServletResponse response) throws IOException {
        List<Diem> diemList = diemService.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=diem.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Sinh vien,Lop hoc,Mon hoc,Diem,Loai diem");
            for (Diem diem : diemList) {
                writer.printf("%s,%s,%s,%s,%s%n",
                        diem.getSinhVien().getName(),
                        diem.getLopHoc().getName(),
                        diem.getMonHoc().getName(),
                        diem.getScore(),
                        diem.getLoaiDiem().getName());
            }
        }
    }

    @GetMapping("/export/pdf")
     @PreAuthorize("hasRole('GIANGVIEN')")
    public void exportScoresToPdf(
            @RequestParam("monHocId") int monHocId,
            @RequestParam("lopHocId") int lopHocId,
            @RequestParam("hocKyId") int hocKyId,
            HttpServletResponse response) throws IOException, DocumentException {
        List<Diem> diemList = diemService.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=diem.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("DANH SACH DIEM"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.addCell("Sinh vien");
            table.addCell("Lop hoc");
            table.addCell("Mon hoc");
            table.addCell("Diem");
            table.addCell("Loai diem");

            for (Diem diem : diemList) {
                table.addCell(diem.getSinhVien().getName());
                table.addCell(diem.getLopHoc().getName());
                table.addCell(diem.getMonHoc().getName());
                table.addCell(String.valueOf(diem.getScore()));
                table.addCell(diem.getLoaiDiem().getName());
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }

    @GetMapping("/export/average-scores/csv")
     @PreAuthorize("hasRole('GIANGVIEN')")
    public void exportAverageScoresToCsv(
            @RequestParam(value = "monHocId", defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", defaultValue = "0") int lopHocId,
             @RequestParam(value = "hocKyId", defaultValue = "0") int hocKyId,
            HttpServletResponse response) throws IOException {
        List<Object[]> averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=average_scores.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Student ID,Student Name,Average Score");

            for (Object[] row : averageScores) {
                writer.printf("%s,%s,%.2f%n", row[0], row[1], row[2]);
            }
        }
    }

    @GetMapping("/export/average-scores/pdf")
     @PreAuthorize("hasRole('GIANGVIEN')")
    public void exportAverageScoresToPdf(
            @RequestParam(value = "monHocId", defaultValue = "0") int monHocId,
            @RequestParam(value = "lopHocId", defaultValue = "0") int lopHocId,
             @RequestParam(value = "hocKyId", defaultValue = "0") int hocKyId,
            HttpServletResponse response) throws DocumentException, IOException {
        List<Object[]> averageScores = diemService.getAllAverageScores(monHocId, lopHocId, hocKyId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=average_scores.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("BANG DIEM TRUNG BINH"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.addCell("ID");
        table.addCell("Họ và tên");
        table.addCell("Điểm trung bình");

        for (Object[] row : averageScores) {
            table.addCell(String.valueOf(row[0]));
            table.addCell(String.valueOf(row[1]));
            table.addCell(String.format("%.2f", row[2]));
        }

        document.add(table);
        document.close();
    }
}
