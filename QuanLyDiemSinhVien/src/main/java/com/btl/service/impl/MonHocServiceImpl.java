package com.btl.service.impl;

import com.btl.pojo.MonHoc;
import com.btl.repository.MonHocRepository;
import com.btl.service.MonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonHocServiceImpl implements MonHocService {

    private final MonHocRepository monHocRepository;

    @Autowired
    public MonHocServiceImpl(MonHocRepository monHocRepository) {
        this.monHocRepository = monHocRepository;
    }

    @Override
    public List<MonHoc> getNameMonHoc() {
        return monHocRepository.getNameMonHoc();
    }

    @Override
    @Transactional
    public List<MonHoc> getMonHocsBySinhVienId(int sinhVienId) {
        return monHocRepository.getMonHocsBySinhVienId(sinhVienId);
    }

    @Override
    public List<MonHoc> getAllMonHocs() {
        return monHocRepository.getAllMonHocs();
    }

    @Override
    public MonHoc getMonHocById(int id) {
        return monHocRepository.getMonHocById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getIdByName(String monHocName) {
        return monHocRepository.getIdByName(monHocName);
    }

}
