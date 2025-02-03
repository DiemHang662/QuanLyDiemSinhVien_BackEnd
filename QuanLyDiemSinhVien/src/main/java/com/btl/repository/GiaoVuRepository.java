package com.btl.repository;

import com.btl.pojo.GiangVien;
import com.btl.pojo.GiaoVu;
import com.btl.pojo.SinhVien;
import java.util.List;

public interface GiaoVuRepository {
    GiaoVu findById(int id);
    GiaoVu findByUsername(String username);
    List<GiaoVu> findAll();
    void save(GiaoVu giaoVu);
    void update(GiaoVu giaoVu);
    void deleteById(int id);
    GiaoVu getGiaoVuById(int id);

}
