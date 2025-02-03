package com.btl.service;

import com.btl.pojo.GiaoVu;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface GiaoVuService {
     GiaoVu findById(int id);
    GiaoVu findByUsername(String username);
    List<GiaoVu> findAll();
    void save(GiaoVu giaoVu);
    void update(GiaoVu giaoVu);
    void deleteById(int id);
    GiaoVu getGiaoVuById(int id);
 String uploadAvatar(MultipartFile file, int giaoVuId);
  public List<GiaoVu> getNameGiaoVu();
  GiaoVu getGiaoVuByUsername(String username);
}
