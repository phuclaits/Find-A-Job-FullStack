package com.doan.AppTuyenDung.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.AppTuyenDung.DTO.GetAllJobTypeAdmin.JobtypeDTO;
import com.doan.AppTuyenDung.DTO.GetAllJobTypeAdmin.ResponseAllJobType;
import com.doan.AppTuyenDung.DTO.GetAllUserAdmin.AccountDTO;

import com.doan.AppTuyenDung.Repositories.AccountRepository;
import com.doan.AppTuyenDung.Repositories.AllCode.CodeGendersRepository;
import com.doan.AppTuyenDung.Services.AllCodeService;
import com.doan.AppTuyenDung.Services.JWTUtils;
import com.doan.AppTuyenDung.entity.CodeExpType;
import com.doan.AppTuyenDung.entity.CodeGender;
import com.doan.AppTuyenDung.entity.CodeJobLevel;
import com.doan.AppTuyenDung.entity.CodeJobType;
import com.doan.AppTuyenDung.entity.CodeProvince;
import com.doan.AppTuyenDung.entity.CodeRule;
import com.doan.AppTuyenDung.entity.CodeSalaryType;
import com.doan.AppTuyenDung.entity.CodeWorkType;
@RestController
@RequestMapping("/public/get-all-code")
public class AllcodeController {
    @Autowired
    private AllCodeService codeService;    


    @GetMapping("/exp-types")
    public ResponseEntity<List<CodeExpType>> getExpTypes() {
        return ResponseEntity.ok(codeService.getAllExpTypes());
    }

    @GetMapping("/job-levels")
    public ResponseEntity<List<CodeJobLevel>> getJobLevels() {
        return ResponseEntity.ok(codeService.getAllJobLevels());
    }

    @GetMapping("/job-types")
    public ResponseEntity<List<CodeJobType>> getJobTypes() {
        return ResponseEntity.ok(codeService.getAllJobTypes());
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<CodeProvince>> getProvinces() {
        return ResponseEntity.ok(codeService.getAllProvinces());
    }

    @GetMapping("/salary-types")
    public ResponseEntity<List<CodeSalaryType>> getSalaryTypes() {
        return ResponseEntity.ok(codeService.getAllSalaryTypes());
    }

    @GetMapping("/work-types")
    public ResponseEntity<List<CodeWorkType>> getWorkTypes() {
        return ResponseEntity.ok(codeService.getAllWorkTypes());
    }
    @GetMapping("/rules-user")
    public ResponseEntity<List<CodeRule>> getCodeRules() {
        return ResponseEntity.ok(codeService.getAllCodeRules());
    }
    @GetMapping("/genders-user")
    public ResponseEntity<List<CodeGender>> getCodeGenders() {
        return ResponseEntity.ok(codeService.getAllCodeGenders());
    }


    

}
