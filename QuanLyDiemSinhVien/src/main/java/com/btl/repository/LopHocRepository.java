package com.btl.repository;

import com.btl.pojo.LopHoc;
import com.btl.pojo.MonHoc;
import com.btl.pojo.SinhVien;
import java.util.List;

public interface LopHocRepository {

    LopHoc findById(int id);

    List<LopHoc> findAll();

    List<SinhVien> getStudentsByLopHocId(int lopHocId, int page, int pageSize);

    List<LopHoc> getNameLopHoc();

    void save(LopHoc lopHoc);

    void update(LopHoc lopHoc);

    void deleteById(int id);

    int countStudentsByLopHocId(int lopHocId);

    Integer getIdByName(String lopHocName);
}
