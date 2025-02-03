package com.btl.service.impl;

import com.btl.pojo.TraLoiDienDan;
import com.btl.repository.TraLoiDienDanRepository;
import com.btl.service.TraLoiDienDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TraLoiDienDanServiceImpl implements TraLoiDienDanService {

    @Autowired
    private TraLoiDienDanRepository traLoiDienDanRepository;

    @Override
    public List<TraLoiDienDan> getTraLoiByDienDan(int dienDanId) {
        return this.traLoiDienDanRepository.getTraLoiByDienDan(dienDanId);
    }

    @Override
    public void addTraLoi(TraLoiDienDan traLoiDienDan) {
        this.traLoiDienDanRepository.addTraLoi(traLoiDienDan);
    }

    @Override
    public void updateTraLoi(TraLoiDienDan traLoiDienDan) {
        this.traLoiDienDanRepository.updateTraLoi(traLoiDienDan);
    }

    @Override
    public void deleteById(int id) {
        traLoiDienDanRepository.deleteById(id);
    }
}
