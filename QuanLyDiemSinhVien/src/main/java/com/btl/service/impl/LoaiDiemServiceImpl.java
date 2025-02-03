package com.btl.service.impl;

import com.btl.pojo.LoaiDiem;
import com.btl.repository.LoaiDiemRepository;
import com.btl.service.LoaiDiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiDiemServiceImpl implements LoaiDiemService {

    private final LoaiDiemRepository loaiDiemRepository;

    @Autowired
    public LoaiDiemServiceImpl(LoaiDiemRepository loaiDiemRepository) {
        this.loaiDiemRepository = loaiDiemRepository;
    }

    @Override
    public List<LoaiDiem> getNameLoaiDiem() {  // Changed from findAll
        return loaiDiemRepository.getNameLoaiDiem();
    }
}
