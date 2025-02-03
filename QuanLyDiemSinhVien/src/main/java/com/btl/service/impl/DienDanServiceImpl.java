package com.btl.service.impl;

import com.btl.pojo.DienDan;
import com.btl.repository.DienDanRepository;
import com.btl.service.DienDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DienDanServiceImpl implements DienDanService {

    @Autowired
    private DienDanRepository dienDanRepository;

    @Override
    public List<DienDan> getDienDanByMonHoc(int monHocId) {
        return this.dienDanRepository.getDienDanByMonHoc(monHocId);
    }

    @Override
    public DienDan getDienDanById(int id) {
        return this.dienDanRepository.getDienDanById(id);
    }

    @Override
    public void addDienDan(DienDan dienDan) {
        this.dienDanRepository.addDienDan(dienDan);
    }

    @Override
    public void updateDienDan(DienDan dienDan) {
        this.dienDanRepository.updateDienDan(dienDan);
    }

    @Override
    public void deleteDienDan(int id) {
        this.dienDanRepository.deleteDienDan(id);
    }
}
