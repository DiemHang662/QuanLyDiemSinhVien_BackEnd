package com.btl.service;

import com.btl.pojo.GiangVien;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface GiangVienService {
    GiangVien findById(int id);
    List<GiangVien> findAll();
    void save(GiangVien giangVien);
    void update(GiangVien giangVien);
    void deleteById(int id);
    String uploadAvatar(MultipartFile file, int giangVienId);
    GiangVien getGiangVienById(int id);
    GiangVien getGiangVienByUsername(String username);
    List<GiangVien> getNameGiangVien();
}
