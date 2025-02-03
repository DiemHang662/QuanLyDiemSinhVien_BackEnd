package com.btl.controllers;

import com.btl.pojo.GiangVien;
import com.btl.service.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/giangvien")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiGiangVienController {

    private final GiangVienService giangVienService;

    @Autowired
    public ApiGiangVienController(GiangVienService giangVienService) {
        this.giangVienService = giangVienService;
    }

    // Get all GiangVien
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GiangVien>> listGiangVien() {
        List<GiangVien> giangVienList = giangVienService.findAll();
        return ResponseEntity.ok(giangVienList);
    }

@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> getGiangVienById(@PathVariable("id") int id) {
    GiangVien giangVien = giangVienService.getGiangVienById(id);
    if (giangVien != null) {
        return ResponseEntity.ok(giangVien);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Giảng viên không tìm thấy"));
    }
}


    // Save or Update GiangVien
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdateGiangVien(@RequestBody GiangVien giangVien) {
        try {
            if (giangVien.getId() == 0) {
                giangVienService.save(giangVien);
                return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Giảng viên đã được lưu thành công"));
            } else {
                giangVienService.update(giangVien);
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Giảng viên đã được cập nhật thành công"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Lỗi khi lưu hoặc cập nhật giảng viên: " + e.getMessage()));
        }
    }

    // Delete GiangVien
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteGiangVien(@PathVariable("id") int id) {
        try {
            giangVienService.deleteById(id);
            return ResponseEntity.ok(new SuccessResponse("Đã xóa thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Lỗi khi xóa giảng viên: " + e.getMessage()));
        }
    }

@PostMapping("/{id}/upload-avatar")
public ResponseEntity<?> uploadAvatar(@PathVariable("id") int giangVienId, @RequestParam("avatar") MultipartFile file) {
    if (file.isEmpty()) {
        return ResponseEntity.badRequest().body("File is empty");
    }

    String avatarUrl = giangVienService.uploadAvatar(file, giangVienId);
    if (avatarUrl != null) {
        return ResponseEntity.ok(new SuccessResponse("Avatar uploaded successfully"));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar");
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
