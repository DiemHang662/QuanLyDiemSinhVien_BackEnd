package com.btl.repository;

import com.btl.pojo.NguoiDung;


public interface NguoiDungRepository {
    NguoiDung getUserByUsername(String username);
    boolean authUser(String username, String password) ;
}

