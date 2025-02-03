package com.btl.service.impl;

import com.btl.pojo.Diem;
import com.btl.pojo.DiemTrungBinh;
import com.btl.pojo.HocKy;
import com.btl.pojo.LoaiDiem;
import com.btl.pojo.LopHoc;
import com.btl.pojo.MonHoc;
import com.btl.pojo.SinhVien;
import com.btl.repository.DiemRepository;
import com.btl.service.DiemService;
import com.itextpdf.text.Element;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DiemServiceImpl implements DiemService {

    private final DiemRepository diemRepository;

    @Autowired
    public DiemServiceImpl(DiemRepository diemRepository) {
        this.diemRepository = diemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Diem> getAllDiems() {
        return diemRepository.getAllDiems();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVien> getAllSinhViens() {
        return diemRepository.getAllSinhViens();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonHoc> getAllMonHocs() {
        return diemRepository.getAllMonHocs();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LopHoc> getAllLopHocs() {
        return diemRepository.getAllLopHocs();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HocKy> getAllHocKys() {
        return diemRepository.getAllHocKys();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoaiDiem> getAllLoaiDiems() {
        return diemRepository.getAllLoaiDiems();
    }

    @Override
    @Transactional(readOnly = true)
    public void saveDiem(Diem diem) {
        diemRepository.saveDiem(diem);
    }

    @Override
    @Transactional(readOnly = true)
    public void updateDiem(Diem diem) {
        diemRepository.updateDiem(diem);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteDiem(int id) {
        diemRepository.deleteDiem(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Diem> getDiemBySinhVienIdAndMonHocIdAndLopHocIdAndHocKyId(int sinhVienId, int monHocId, int lopHocId, int hocKyId) {
        return diemRepository.getDiemBySinhVienIdAndMonHocIdAndLopHocIdAndHocKyId(sinhVienId, monHocId, lopHocId, hocKyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Diem> getDiemByMonHocIdAndLopHocIdAndHocKyId(int monHocId, int lopHocId, int hocKyId) {
        return diemRepository.getDiemByMonHocIdAndLopHocIdAndHocKyId(monHocId, lopHocId, hocKyId);
    }

    @Override
    public void exportDiemToPdf(HttpServletResponse response, List<Diem> diemList) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=DiemTrungBinh.pdf");

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Nội dung của tài liệu
            Paragraph title = new Paragraph("DANH SACH DIEM MON HOC");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell("Sinh vien");
            table.addCell("Lop hoc");
            table.addCell("Mon hoc");
            table.addCell("Hoc ky");
            table.addCell("Diem");
            table.addCell("Loai diem");

            for (Diem diem : diemList) {
                table.addCell(diem.getSinhVien().getName());
                table.addCell(diem.getLopHoc().getName());
                table.addCell(diem.getMonHoc().getName());
                table.addCell(diem.getHocKy().getName());
                table.addCell(String.valueOf(diem.getScore()));
                table.addCell(diem.getLoaiDiem().getName());
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();  // Đóng tài liệu trong khối finally
        }
    }
    
    @Override
    public void exportAverageScoresToPdf(HttpServletResponse response, List<Object[]> averageScores) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=DiemTrungBinh.pdf");

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Nội dung của tài liệu
            Paragraph title = new Paragraph("BANG DIEM TRUNG BINH");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Thêm dòng trắng

            // Tạo bảng với 3 cột
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.addCell("ID");
            table.addCell("Ho va ten");
            table.addCell("Diem trung binh");

            // Thêm dữ liệu vào bảng
            for (Object[] row : averageScores) {
                table.addCell(String.valueOf(row[0]));  
                table.addCell(String.valueOf(row[1])); 
                table.addCell(String.format("%.2f", row[2]));  
            }

            // Thêm bảng vào tài liệu
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();  // Đóng tài liệu trong khối finally
        }
    }

 @Override
    public Double getAverageScoreForStudent(int sinhVienId, int monHocId, int lopHocId, int hocKyId) {
        return diemRepository.getAverageScoreForStudent(sinhVienId, monHocId, lopHocId, monHocId);
    }

    @Override
    public List<Object[]> getAllAverageScores(int monHocId, int lopHocId, int hocKyId) {
        return diemRepository.getAllAverageScores( monHocId, lopHocId, hocKyId);
    }

    @Override
    public List<Object[]> getHighestAverageScoresByClass(int monHocId, int hocKyId) {
          return diemRepository.getHighestAverageScoresByClass(monHocId, hocKyId);
    }
}
