package com.doan.AppTuyenDung.Controller;


import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doan.AppTuyenDung.DTO.CvDTO;
import com.doan.AppTuyenDung.DTO.Response.ApiResponse;
import com.doan.AppTuyenDung.DTO.Response.CvsResponse;
import com.doan.AppTuyenDung.Repositories.AccountRepository;
import com.doan.AppTuyenDung.Services.CvService;
import com.doan.AppTuyenDung.Services.JWTUtils;
import com.doan.AppTuyenDung.entity.Company;
import com.doan.AppTuyenDung.entity.Cv;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping
public class CvController {
	@Autowired
	private CvService cvService;
	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private AccountRepository accountRepo;
	@GetMapping("/public/cv/get-all-cv-by-userId")
	public ApiResponse<Page<CvsResponse>> getAllCvByUserId(@RequestParam(defaultValue = "5") int limit
															,@RequestParam(defaultValue = "0") int offset
															,@RequestParam (required = false) Integer userId) {
		ApiResponse<Page<CvsResponse>> apiRs = new ApiResponse<>();
		Pageable pageable = PageRequest.of(offset, limit);
        Page<CvsResponse> listCv = cvService.getAllCvByUserId(userId,pageable);
        apiRs.setMessage("Lấy thành công danh sách cv của userId: "+ userId);
        apiRs.setResult(listCv);
        return apiRs;
	}
	@PostMapping("/public/createCVnew")
	public ResponseEntity<Map<String, Object>> CreateCVnew(@RequestHeader("Authorization") String token, 
    @ModelAttribute CvDTO cvs,@RequestPart("filePDF" ) MultipartFile filePDF) {
		Map<String, Object> responseERORR = new HashMap<>();
		if(filePDF == null)
		{
			responseERORR.put("errCode", -1);
            responseERORR.put("errMessage", "Cần bổ sung File PDF Cv");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);    // File PDF không tồn tại
		}
		if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        // Giải mã token 
        String phonenumber = jwtUtils.extractUserName(token);

        var account = accountRepo.findByPhonenumber(phonenumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
		
		try{
            String base64Pdf = Base64.getEncoder().encodeToString(filePDF.getBytes());
            String result = "data:application/pdf;base64," + base64Pdf;
            cvs.setFile(result.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> response = cvService.handleCreateCv(cvs);
        return ResponseEntity.ok(response);
		

	}
	
}
