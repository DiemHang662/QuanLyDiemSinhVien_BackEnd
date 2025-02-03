package com.btl.repository;

import com.btl.pojo.DienDan;
import java.util.List;

public interface DienDanRepository {

    List<DienDan> getDienDanByMonHoc(int monHocId);

    DienDan getDienDanById(int id);

    void addDienDan(DienDan dienDan);

    void updateDienDan(DienDan dienDan);

    void deleteDienDan(int id);
}
