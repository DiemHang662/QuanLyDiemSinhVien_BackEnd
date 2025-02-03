package com.btl.service;

import com.btl.pojo.HocKy;

import java.util.List;

public interface HocKyService {
    List<HocKy> getNameHocKy();  // Changed from findAll
    Integer getIdByName(String hocKyName);
}
