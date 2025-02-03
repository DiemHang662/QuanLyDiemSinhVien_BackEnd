package com.btl.controllers;

import com.btl.pojo.Khoa;
import com.btl.pojo.LopHoc;
import com.btl.pojo.NganhDaoTao;
import com.btl.pojo.SinhVien;
import com.btl.service.SinhVienService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sinhvien")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiSinhVienController {

    private final SinhVienService sinhVienService;

    @Autowired
    public ApiSinhVienController(SinhVienService sinhVienService) {
        this.sinhVienService = sinhVienService;
    }

    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<PaginatedResponse<SinhVien>> listSinhVien(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "7") int pageSize) {

        if (page < 1) {
            page = 1;
        }

        List<SinhVien> sinhVienList = sinhVienService.findAll(page, pageSize);
        int totalItems = sinhVienService.countAll();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        if (page > totalPages) {
            page = totalPages;
            sinhVienList = sinhVienService.findAll(page, pageSize);
        }

        PaginatedResponse<SinhVien> response = new PaginatedResponse<>(sinhVienList, page, totalPages);
        return ResponseEntity.ok(response);
    }

    // Xem thông tin một sSV
    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping(value = "/chitiet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<?> viewSinhVienDetail(@PathVariable("id") int id) {
        SinhVien sinhVien = sinhVienService.getSinhVienById(id);
        if (sinhVien != null) {
            return ResponseEntity.ok(sinhVien);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Sinh viên không tìm thấy"));
        }
    }

    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable("id") int sinhVienId, @RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String avatarUrl = sinhVienService.uploadAvatar(file, sinhVienId);
        if (avatarUrl != null) {
            return ResponseEntity.ok("Avatar uploaded successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar");
    }

    // Xem thông tin một SV
    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<?> getSinhVienById(@PathVariable("id") int id) {
        SinhVien sinhVien = sinhVienService.getSinhVienById(id);
        if (sinhVien != null) {
            return ResponseEntity.ok(sinhVien);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Sinh viên không tìm thấy"));
        }
    }

    // Thêm sinh viên
    @PreAuthorize("hasRole('GIANGVIEN')")
    @PostMapping("/saveOrUpdate")
    @CrossOrigin
    public ResponseEntity<?> saveOrUpdateSinhVien(@RequestBody SinhVien sinhVien) {
        try {

            if (sinhVien.getUserRole() == null) {
                sinhVien.setUserRole("ROLE_SINHVIEN");
            }
            sinhVienService.saveOrUpdate(sinhVien);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Sinh viên đã được lưu thành công"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Lỗi khi lưu sinh viên: " + e.getMessage()));
        }
    }

    // Hiện form 
    @PreAuthorize("hasRole('GIANGVIEN')")
    @GetMapping(value = "/form", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormOptionsResponse> getFormOptions() {
        List<LopHoc> lopHocs = sinhVienService.getAllLopHocs();
        List<Khoa> khoas = sinhVienService.getAllKhoas();
        List<NganhDaoTao> nganhDaoTaos = sinhVienService.getAllNganhDaoTaos();

        FormOptionsResponse response = new FormOptionsResponse(lopHocs, khoas, nganhDaoTaos);
        return ResponseEntity.ok(response);
    }

    // Sửa thông tin
    @PreAuthorize("hasRole('GIANGVIEN')")
    @PostMapping("/{id}/update")
    public ResponseEntity<?> saveOrUpdateSinhVien(@PathVariable("id") Integer id, @RequestBody SinhVien sinhVien) {
        try {
            // Tìm sinh viên hiện tại từ ID
            SinhVien existingSinhVien = sinhVienService.getSinhVienById(id);
            if (existingSinhVien == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Sinh viên không tồn tại"));
            }

            // Cập nhật thông tin sinh viên
            existingSinhVien.setName(sinhVien.getName());
            existingSinhVien.setLopHoc(sinhVien.getLopHoc());
            existingSinhVien.setKhoa(sinhVien.getKhoa());
            existingSinhVien.setNganhDaoTao(sinhVien.getNganhDaoTao());
            existingSinhVien.setEmail(sinhVien.getEmail());
            existingSinhVien.setNgaySinh(sinhVien.getNgaySinh());
            existingSinhVien.setQueQuan(sinhVien.getQueQuan());
            existingSinhVien.setGioiTinh(sinhVien.getGioiTinh());
            // existingSinhVien.setAvatar(sinhVien.getAvatar());

            // Đảm bảo userRole được thiết lập trước khi lưu
            if (existingSinhVien.getUserRole() == null) {
                existingSinhVien.setUserRole("ROLE_SINHVIEN");
            }

            sinhVienService.saveOrUpdate(existingSinhVien);

            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Sinh viên đã được cập nhật thành công"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Lỗi khi cập nhật sinh viên: " + e.getMessage()));
        }
    }

    // Xóa một sinh viên
    @PreAuthorize("hasRole('GIANGVIEN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSinhVien(@PathVariable("id") int id) {
        try {
            sinhVienService.deleteById(id);
            return ResponseEntity.ok(new SuccessResponse("Đã xóa thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Lỗi khi xóa sinh viên: " + e.getMessage()));
        }
    }

    //Đăng kí tài khoản
    @PostMapping("/dangki")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody SinhVien sinhVien, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        if (sinhVien.getUserRole() == null || sinhVien.getUserRole().isEmpty()) {
            sinhVien.setUserRole("ROLE_SINHVIEN");
        }

        if (sinhVien.getUsername() == null || sinhVien.getUsername().isEmpty()
                || sinhVien.getPassword() == null || sinhVien.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên tài khoản và mật khẩu không được để trống.");
        }

        // Attempt to register the student
        boolean isRegistered = sinhVienService.registerSinhVien(sinhVien);
        if (!isRegistered) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên tài khoản hoặc email đã tồn tại. Vui lòng thử lại.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
    }

    public static class PaginatedResponse<T> {

        private List<T> items;
        private int currentPage;
        private int totalPages;

        public PaginatedResponse(List<T> items, int currentPage, int totalPages) {
            this.items = items;
            this.currentPage = currentPage;
            this.totalPages = totalPages;
        }

        // Getters and setters
        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            this.items = items;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }

    // Helper class for form options response
    public static class FormOptionsResponse {

        private List<LopHoc> lopHocs;
        private List<Khoa> khoas;
        private List<NganhDaoTao> nganhDaoTaos;

        public FormOptionsResponse(List<LopHoc> lopHocs, List<Khoa> khoas, List<NganhDaoTao> nganhDaoTaos) {
            this.lopHocs = lopHocs;
            this.khoas = khoas;
            this.nganhDaoTaos = nganhDaoTaos;
        }

        // Getters and setters
        public List<LopHoc> getLopHocs() {
            return lopHocs;
        }

        public void setLopHocs(List<LopHoc> lopHocs) {
            this.lopHocs = lopHocs;
        }

        public List<Khoa> getKhoas() {
            return khoas;
        }

        public void setKhoas(List<Khoa> khoas) {
            this.khoas = khoas;
        }

        public List<NganhDaoTao> getNganhDaoTaos() {
            return nganhDaoTaos;
        }

        public void setNganhDaoTaos(List<NganhDaoTao> nganhDaoTaos) {
            this.nganhDaoTaos = nganhDaoTaos;
        }
    }

    // Helper class for success response
    public static class SuccessResponse {

        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Helper class for error response
    public static class ErrorResponse {

        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
