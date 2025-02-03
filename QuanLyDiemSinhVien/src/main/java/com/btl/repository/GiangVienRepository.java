package com.btl.repository;

import com.btl.pojo.GiangVien;
import com.btl.pojo.SinhVien;
import java.util.List;

public interface GiangVienRepository {
    GiangVien findById(int id);
    GiangVien findByUsername(String username);
    List<GiangVien> findAll();
    void save(GiangVien giangVien);
    void update(GiangVien giangVien);
    void deleteById(int id);
    GiangVien getGiangVienById(int id);

}
