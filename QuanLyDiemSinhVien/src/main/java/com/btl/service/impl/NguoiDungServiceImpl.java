package com.btl.service.impl;

import com.btl.pojo.GiangVien;
import com.btl.pojo.NguoiDung;
import com.btl.repository.GiangVienRepository;
import com.btl.repository.NguoiDungRepository;
import com.btl.service.NguoiDungService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private NguoiDungRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
     @Autowired
    private GiangVienRepository giangVienRepo;

    @Override
    public NguoiDung getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }
    
    
    @Override
    public GiangVien getGiangVienById(int id) {
        return this.giangVienRepo.findById(id); // Use appropriate method from your repository
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid User!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getUserRole()));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepo.authUser(username, password);
    }
}
