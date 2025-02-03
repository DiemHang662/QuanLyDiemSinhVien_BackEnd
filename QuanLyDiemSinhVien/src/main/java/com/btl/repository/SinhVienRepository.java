package com.btl.repository;

import com.btl.pojo.Khoa;
import com.btl.pojo.LopHoc;
import com.btl.pojo.NganhDaoTao;
import com.btl.pojo.SinhVien;
import java.util.List;

public interface SinhVienRepository {

    List<SinhVien> findAll(int page, int pageSize);

    List<SinhVien> findByLopHocId(int lopHocId);

    List<SinhVien> getNameSinhVien();

    List<LopHoc> getAllLopHocs();

    List<Khoa> getAllKhoas();

    List<NganhDaoTao> getAllNganhDaoTaos();

    SinhVien getSinhVienById(int id);

    void save(SinhVien sinhVien);

    void update(SinhVien sinhVien);

    void saveOrUpdate(SinhVien sinhVien);

    void deleteById(int id);

    int countAll();

    List<SinhVien> searchByTerm(String searchTerm);

    boolean isEmailExists(String email);

    Integer getIdByName(String sinhVienName);

    List<SinhVien> findByKhoaId(int khoaId, int page, int pageSize);

    int countByKhoaId(int khoaId);
}
