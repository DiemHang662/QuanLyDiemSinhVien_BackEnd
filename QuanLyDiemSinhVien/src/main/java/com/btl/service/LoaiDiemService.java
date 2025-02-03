package com.btl.service;

import com.btl.pojo.LoaiDiem;

import java.util.List;

public interface LoaiDiemService {
    List<LoaiDiem> getNameLoaiDiem();  // Changed from findAll
}
