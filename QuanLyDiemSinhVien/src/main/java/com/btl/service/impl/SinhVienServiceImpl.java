package com.btl.service.impl;

import com.btl.pojo.SinhVien;
import com.btl.pojo.LopHoc;
import com.btl.pojo.Khoa;
import com.btl.pojo.NganhDaoTao;
import com.btl.repository.SinhVienRepository;
import com.btl.service.SinhVienService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SinhVienServiceImpl implements SinhVienService {

    private final SinhVienRepository sinhVienRepository;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    public SinhVienServiceImpl(SinhVienRepository sinhVienRepository) {
        this.sinhVienRepository = sinhVienRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public SinhVien getSinhVienById(int id) {
        return sinhVienRepository.getSinhVienById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVien> findAll(int page, int pageSize) {
        return sinhVienRepository.findAll(page, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public int countAll() {
        return sinhVienRepository.countAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVien> findByLopHocId(int lopHocId) {
        return sinhVienRepository.findByLopHocId(lopHocId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVien> getNameSinhVien() {
        return sinhVienRepository.getNameSinhVien();
    }

//    @Override
//    @Transactional
//    public void save(SinhVien sinhVien) {
//        String hashedPassword = passwordEncoder.encode(sinhVien.getPassword());
//        sinhVien.setPassword(hashedPassword);
//        sinhVienRepository.save(sinhVien);
//    }
    @Override
    @Transactional
    public void update(SinhVien sinhVien) {
        String hashedPassword = passwordEncoder.encode(sinhVien.getPassword());
        sinhVien.setPassword(hashedPassword);
        sinhVienRepository.save(sinhVien);
    }

    @Override
    @Transactional
    public void saveOrUpdate(SinhVien sinhVien) {
        // Set default userRole if not provided
        if (sinhVien.getUserRole() == null || sinhVien.getUserRole().isEmpty()) {
            sinhVien.setUserRole("ROLE_SINHVIEN");
        }

        if (sinhVien.getId() != null) {
            // Fetch existing SinhVien from the repository
            SinhVien existingSinhVien = sinhVienRepository.getSinhVienById(sinhVien.getId());

            if (existingSinhVien != null) {
                // Preserve the existing avatar if the new one is null
                if (sinhVien.getAvatar() == null) {
                    sinhVien.setAvatar(existingSinhVien.getAvatar());
                }

                // Preserve the existing username if the new one is null or empty
                if (sinhVien.getUsername() == null || sinhVien.getUsername().isEmpty()) {
                    sinhVien.setUsername(existingSinhVien.getUsername());
                }

                // Preserve the existing password if no new password is provided
                if (sinhVien.getPassword() == null || sinhVien.getPassword().isEmpty()) {
                    sinhVien.setPassword(existingSinhVien.getPassword());
                } else {
                    // Hash the new password
                    String hashedPassword = passwordEncoder.encode(sinhVien.getPassword());
                    sinhVien.setPassword(hashedPassword);
                }
            }
        } else if (sinhVien.getPassword() != null && !sinhVien.getPassword().isEmpty()) {
            // Hash the new password if no existing SinhVien is found
            String hashedPassword = passwordEncoder.encode(sinhVien.getPassword());
            sinhVien.setPassword(hashedPassword);
        }

        sinhVienRepository.saveOrUpdate(sinhVien);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        sinhVienRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LopHoc> getAllLopHocs() {
        return sinhVienRepository.getAllLopHocs();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Khoa> getAllKhoas() {
        return sinhVienRepository.getAllKhoas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NganhDaoTao> getAllNganhDaoTaos() {
        return sinhVienRepository.getAllNganhDaoTaos();
    }

    @Override
    @Transactional
    public List<SinhVien> searchSinhVienByTerm(String searchTerm) {
        return sinhVienRepository.searchByTerm(searchTerm);
    }

    @Override
    @Transactional
    public boolean registerSinhVien(SinhVien sinhVien) {
        if (!isValidEmail(sinhVien.getEmail())) {
            return false;
        }
        if (sinhVienRepository.isEmailExists(sinhVien.getEmail())) {
            return false;
        }
        try {
            sinhVienRepository.save(sinhVien);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public void save(SinhVien sinhVien) {
        // Kiểm tra nếu mật khẩu đã được băm
        if (!isPasswordEncoded(sinhVien.getPassword())) {
            String hashedPassword = passwordEncoder.encode(sinhVien.getPassword());
            sinhVien.setPassword(hashedPassword);
        }
        sinhVienRepository.save(sinhVien);
    }

    private boolean isPasswordEncoded(String password) {
        return password != null && password.startsWith("$2a$") && password.length() == 60;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.endsWith("@ou.edu.vn");
    }

    @Override
    @Transactional
    public String uploadAvatar(MultipartFile file, int sinhVienId) {
        try {
            // Tải ảnh lên và lấy URL
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String avatarUrl = (String) uploadResult.get("secure_url");

            // Cập nhật URL của ảnh vào cơ sở dữ liệu
            SinhVien sinhVien = sinhVienRepository.getSinhVienById(sinhVienId);
            if (sinhVien != null) {
                sinhVien.setAvatar(avatarUrl);
                sinhVienRepository.saveOrUpdate(sinhVien);
                return avatarUrl;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getIdByName(String sinhVienName) {
        return sinhVienRepository.getIdByName(sinhVienName);
    }

    @Override
    public List<SinhVien> findByKhoaId(int khoaId, int page, int pageSize) {
        return sinhVienRepository.findByKhoaId(khoaId, page, pageSize);
    }

    @Override
    public int countByKhoaId(int khoaId) {
        return sinhVienRepository.countByKhoaId(khoaId);
    }

}
