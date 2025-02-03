package com.btl.service.impl;

import com.btl.pojo.GiangVienLopHoc;
import com.btl.repository.GiangVienLopHocRepository;
import com.btl.service.GiangVienLopHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GiangVienLopHocServiceImpl implements GiangVienLopHocService {

    @Autowired
    private GiangVienLopHocRepository giangVienLopHocRepository;

    @Override
    public List<GiangVienLopHoc> findByLopHocId(int lopHocId) {
        return giangVienLopHocRepository.findByLopHocId(lopHocId);
    }

    @Override
    public List<GiangVienLopHoc> findByGiangVienId(int giangVienId) {
        return giangVienLopHocRepository.findByGiangVienId(giangVienId);
    }

    @Override
    public void save(GiangVienLopHoc giangVienLopHoc) {
        giangVienLopHocRepository.save(giangVienLopHoc);
    }

    @Override
    public void deleteById(int giangVienId, int lopHocId) {
        giangVienLopHocRepository.deleteById(giangVienId, lopHocId);
    }
}
