package com.btl.repository;

import com.btl.pojo.GiangVienLopHoc;
import java.util.List;

public interface GiangVienLopHocRepository {
    List<GiangVienLopHoc> findByLopHocId(int lopHocId);
    List<GiangVienLopHoc> findByGiangVienId(int giangVienId);
    void save(GiangVienLopHoc giangVienLopHoc);
    void deleteById(int giangVienId, int lopHocId);
}
