package com.btl.service;

import com.btl.pojo.MonHoc;
import java.util.List;

public interface MonHocService {

    List<MonHoc> getNameMonHoc();

    List<MonHoc> getMonHocsBySinhVienId(int sinhVienId);
    
    List<MonHoc> getAllMonHocs();
    
    MonHoc getMonHocById(int id);
    
    Integer getIdByName(String monHocName);
}
