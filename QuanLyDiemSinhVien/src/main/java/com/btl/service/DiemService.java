package com.btl.service;

import com.btl.pojo.Diem;
import com.btl.pojo.DiemTrungBinh;
import com.btl.pojo.HocKy;
import com.btl.pojo.LoaiDiem;
import com.btl.pojo.LopHoc;
import com.btl.pojo.MonHoc;
import com.btl.pojo.SinhVien;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DiemService {

    List<Diem> getAllDiems();

    List<SinhVien> getAllSinhViens();

    List<MonHoc> getAllMonHocs();

    List<HocKy> getAllHocKys();

    List<LopHoc> getAllLopHocs();

    List<LoaiDiem> getAllLoaiDiems();

    List<Diem> getDiemBySinhVienIdAndMonHocIdAndLopHocIdAndHocKyId(int sinhVienId, int monHocId, int lopHocId, int hocKyId);

    List<Diem> getDiemByMonHocIdAndLopHocIdAndHocKyId(int monHocId, int lopHocId, int hocKyId);

    void saveDiem(Diem diem);

    void updateDiem(Diem diem);

    void deleteDiem(int id);

    void exportDiemToPdf(HttpServletResponse response, List<Diem> diemList) throws IOException;
    
    void exportAverageScoresToPdf(HttpServletResponse response, List<Object[]> averageScores) throws IOException;

    Double getAverageScoreForStudent(int sinhVienId, int monHocId, int lopHocId, int hocKyId);

    List<Object[]> getAllAverageScores(int monHocId, int lopHocId, int hocKyId);

    List<Object[]> getHighestAverageScoresByClass(int monHocId, int hocKyId);

}
