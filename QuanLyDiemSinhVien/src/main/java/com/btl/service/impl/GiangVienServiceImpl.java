package com.btl.service.impl;

import com.btl.pojo.GiangVien;
import com.btl.pojo.SinhVien;
import com.btl.repository.GiangVienRepository;
import com.btl.service.GiangVienService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class GiangVienServiceImpl implements GiangVienService {

    @Autowired
    private GiangVienRepository giangVienRepository;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public GiangVien findById(int id) {
        return giangVienRepository.findById(id);
    }
    
     @Override
    public GiangVien getGiangVienByUsername(String username) {
        return giangVienRepository.findByUsername(username);
    }
    
      @Override
    @Transactional(readOnly = true)
    public GiangVien getGiangVienById(int id) {
        return giangVienRepository.getGiangVienById(id);
    }

    @Override
    public List<GiangVien> findAll() {
        return giangVienRepository.findAll();
    }

    @Override
    public void save(GiangVien giangVien) {
        giangVienRepository.save(giangVien);
    }

    @Override
    public void update(GiangVien giangVien) {
        giangVienRepository.update(giangVien);
    }

    @Override
    public void deleteById(int id) {
        giangVienRepository.deleteById(id);
    }
    
    @Override
@Transactional
public String uploadAvatar(MultipartFile file, int giangVienId) {
    try {
        // Upload the file to Cloudinary
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String avatarUrl = (String) uploadResult.get("secure_url");

        // Update the GiangVien record in the database
        GiangVien giangVien = giangVienRepository.getGiangVienById(giangVienId);
        if (giangVien != null) {
            giangVien.setAvatar(avatarUrl);
            giangVienRepository.update(giangVien);
            return avatarUrl;
        }
        return null;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

@Override
@Transactional
     public List<GiangVien> getNameGiangVien() {
        return giangVienRepository.findAll(); // Adjust according to your repository method
    }

}
