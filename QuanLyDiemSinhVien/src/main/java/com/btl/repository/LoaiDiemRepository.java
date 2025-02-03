package com.btl.repository;

import com.btl.pojo.LoaiDiem;

import java.util.List;

public interface LoaiDiemRepository {
    List<LoaiDiem> getNameLoaiDiem();  // Changed from findAll
}
