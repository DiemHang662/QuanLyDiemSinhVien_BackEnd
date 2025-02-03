package com.btl.service.impl;

import com.btl.pojo.GiaoVu;
import com.btl.repository.GiaoVuRepository;
import com.btl.service.GiaoVuService;
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
public class GiaoVuServiceImpl implements GiaoVuService {

    @Autowired
    private GiaoVuRepository giaoVuRepository;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public GiaoVu findById(int id) {
        return giaoVuRepository.findById(id);
    }
    
     @Override
    public GiaoVu getGiaoVuByUsername(String username) {
        return giaoVuRepository.findByUsername(username);
    }
    
      @Override
    @Transactional(readOnly = true)
    public GiaoVu getGiaoVuById(int id) {
        return giaoVuRepository.getGiaoVuById(id);
    }

    @Override
    public List<GiaoVu> findAll() {
        return giaoVuRepository.findAll();
    }

    @Override
    public void save(GiaoVu giaoVu) {
        giaoVuRepository.save(giaoVu);
    }

    @Override
    public void update(GiaoVu giaoVu) {
        giaoVuRepository.update(giaoVu);
    }

    @Override
    public void deleteById(int id) {
        giaoVuRepository.deleteById(id);
    }
    
    @Override
@Transactional
public String uploadAvatar(MultipartFile file, int giaoVuId) {
    try {
        // Upload the file to Cloudinary
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String avatarUrl = (String) uploadResult.get("secure_url");

        // Update the GiangVien record in the database
        GiaoVu giaoVu = giaoVuRepository.getGiaoVuById(giaoVuId);
        if (giaoVu != null) {
            giaoVu.setAvatar(avatarUrl);
            giaoVuRepository.update(giaoVu);
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
     public List<GiaoVu> getNameGiaoVu() {
        return giaoVuRepository.findAll(); // Adjust according to your repository method
    }

    @Override
    public GiaoVu findByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
