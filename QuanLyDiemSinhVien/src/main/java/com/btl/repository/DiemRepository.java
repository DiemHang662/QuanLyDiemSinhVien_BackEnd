package com.btl.repository;

import com.btl.pojo.Diem;
import com.btl.pojo.DiemTrungBinh;
import com.btl.pojo.HocKy;
import com.btl.pojo.Khoa;
import com.btl.pojo.LoaiDiem;
import com.btl.pojo.LopHoc;
import com.btl.pojo.MonHoc;
import com.btl.pojo.NganhDaoTao;
import com.btl.pojo.SinhVien;

import java.util.List;
import java.util.Map;

public interface DiemRepository {

    List<Diem> getAllDiems();

    List<Diem> getDiemBySinhVienIdAndMonHocIdAndLopHocIdAndHocKyId(int sinhVienId, int monHocId, int lopHocId, int hocKyId);

    List<SinhVien> getAllSinhViens();

    List<MonHoc> getAllMonHocs();

    List<LopHoc> getAllLopHocs();

    List<HocKy> getAllHocKys();

    List<LoaiDiem> getAllLoaiDiems();

    List<Diem> getDiemByMonHocIdAndLopHocIdAndHocKyId(int monHocId, int lopHocId, int hocKyId);

    void saveDiem(Diem diem);

    void updateDiem(Diem diem);

    void deleteDiem(int id);

    List<Diem> getDiemByLoaiDiemId(int loaiDiemId);

    List<Diem> getDiemBySinhVienId(int sinhVienId);

    Double getAverageScoreForStudent(int sinhVienId, int monHocId, int lopHocId, int hocKyId);
    
    List<Object[]> getAllAverageScores(int monHocId, int lopHocId, int hocKyId);
    
    List<Object[]> getHighestAverageScoresByClass(int monHocId, int hocKyId);

}
