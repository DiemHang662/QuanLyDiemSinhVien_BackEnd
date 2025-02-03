package com.btl.service;

import com.btl.pojo.GiangVien;
import com.btl.pojo.NguoiDung;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface NguoiDungService extends UserDetailsService {
    NguoiDung getUserByUsername(String username);
    boolean authUser(String username, String password);
     GiangVien getGiangVienById(int id); // Add this method
}