package com.btl.repository;

import com.btl.pojo.MonHoc;
import java.util.List;

public interface MonHocRepository {

    List<MonHoc> findByGiangVienId(int giangVienId);

    MonHoc findById(int id);

    List<MonHoc> getNameMonHoc();

    List<MonHoc> getMonHocsBySinhVienId(int sinhVienId);

    List<MonHoc> getAllMonHocs();

    MonHoc getMonHocById(int id);
    
    Integer getIdByName(String monHocName);
}
