package com.btl.service.impl;

import com.btl.pojo.HocKy;
import com.btl.pojo.LopHoc;
import com.btl.pojo.SinhVien;
import com.btl.repository.LopHocRepository;
import com.btl.service.LopHocService;
import com.btl.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LopHocServiceImpl implements LopHocService {

    @Autowired
    private LopHocRepository lopHocRepository;

    @Autowired
    private SinhVienService sinhVienService;

    @Override
    public LopHoc findById(int id) {
        return lopHocRepository.findById(id);
    }

    @Override
    public List<LopHoc> findAll() {
        return lopHocRepository.findAll();
    }

    @Override
    public List<LopHoc> getNameLopHoc() {
        return lopHocRepository.getNameLopHoc();
    }

    @Override
    public List<SinhVien> getStudentsByLopHocId(int lopHocId, int page, int pageSize) {
        return lopHocRepository.getStudentsByLopHocId(lopHocId, page, pageSize);
    }

    @Override
    public void save(LopHoc lopHoc) {
        lopHocRepository.save(lopHoc);
    }

    @Override
    public void update(LopHoc lopHoc) {
        lopHocRepository.update(lopHoc);
    }

    @Override
    public void deleteById(int id) {
        lopHocRepository.deleteById(id);
    }

    @Override
    public int countStudentsByLopHocId(int lopHocId) {
        return lopHocRepository.countStudentsByLopHocId(lopHocId);
    }
    
      @Override
    @Transactional(readOnly = true)
    public Integer getIdByName(String lopHocName) {
         return lopHocRepository.getIdByName(lopHocName);
    }

}
