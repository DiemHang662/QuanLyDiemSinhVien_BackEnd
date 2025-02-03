package com.btl.service.impl;

import com.btl.pojo.HocKy;
import com.btl.repository.HocKyRepository;
import com.btl.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HocKyServiceImpl implements HocKyService {

    private final HocKyRepository hocKyRepository;

    @Autowired
    public HocKyServiceImpl(HocKyRepository hocKyRepository) {
        this.hocKyRepository = hocKyRepository;
    }

    @Override
    public List<HocKy> getNameHocKy() {
        return hocKyRepository.getNameHocKy();
    }
    
        @Override
    @Transactional(readOnly = true)
    public Integer getIdByName(String hocKyName) {
         return hocKyRepository.getIdByName(hocKyName);
    }
 
}
