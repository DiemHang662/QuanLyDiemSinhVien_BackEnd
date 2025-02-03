package com.btl.repository;

import com.btl.pojo.HocKy;

import java.util.List;

public interface HocKyRepository {
    List<HocKy> getNameHocKy();  // Changed from findAll
    Integer getIdByName(String hocKyName);
}
