package com.btl.service;

import com.btl.pojo.TraLoiDienDan;

import java.util.List;

public interface TraLoiDienDanService {

    List<TraLoiDienDan> getTraLoiByDienDan(int dienDanId);

    void addTraLoi(TraLoiDienDan traLoiDienDan);

    void updateTraLoi(TraLoiDienDan traLoiDienDan);

    void deleteById(int id);
}
