package com.doan.AppTuyenDung.Controller;

import java.util.List;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.AppTuyenDung.DTO.GetAllJobTypeAdmin.JobtypeDTO;
import com.doan.AppTuyenDung.DTO.GetAllJobTypeAdmin.ResponseAllJobType;
import com.doan.AppTuyenDung.DTO.GetAllSkillAdmin.ResponseAllSkill;
import com.doan.AppTuyenDung.DTO.GetAllSkillAdmin.SkillDTO;
import com.doan.AppTuyenDung.DTO.GetAllSkillAdmin.SkillGetListDTO;
import com.doan.AppTuyenDung.DTO.Response.ApiResponse;
import com.doan.AppTuyenDung.DTO.Response.SkillResponse;
import com.doan.AppTuyenDung.Repositories.AccountRepository;
import com.doan.AppTuyenDung.Repositories.SkillRepository;
import com.doan.AppTuyenDung.Services.JWTUtils;
import com.doan.AppTuyenDung.Services.SkillService;
import com.doan.AppTuyenDung.entity.Post;
import com.doan.AppTuyenDung.entity.Skill;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@RestController
@RequestMapping
public class SkillController {
	@Autowired
	private SkillService service;
	@Autowired
    private JWTUtils jwtUtils;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private SkillRepository skillRepository;
	@GetMapping("/public/skill/get-all-skill-by-job-code")
	public ApiResponse<List<SkillResponse>> getAllSkillJobCode (@RequestParam (required = false) String categoryJobCode) {
		ApiResponse<List<SkillResponse>> lstSkill = new ApiResponse<>();
		lstSkill.setResult(service.GetSkillByCodeJob(categoryJobCode));
		return lstSkill;
	}


	@GetMapping("/public/get-list-skill")
    public ResponseEntity<ResponseAllSkill<Page<SkillGetListDTO>>> getAllJobType(
										@RequestParam(defaultValue = "10") int limit,
										@RequestParam(defaultValue = "0") int offset,
										@RequestParam(required = false) String search,
										@RequestParam (required = false) String categoryJobCode) 
	{
        Pageable pageable = PageRequest.of(offset, limit);
        try {
			if (search != null && !search.isEmpty()) 
			{
				search = "%" + search + "%";
			}
			
            Page<SkillGetListDTO> listSkills = service.getListSkill(pageable,search,categoryJobCode);
            ResponseAllSkill<Page<SkillGetListDTO>> response = new ResponseAllSkill<>(0, "Lấy dữ liệu thành công", listSkills);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseAllSkill<Page<SkillGetListDTO>> response = new ResponseAllSkill<>(-1, "Error from server", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

	@PostMapping("/public/createnewskill")
	public Map<String, Object> createNewSkill(@RequestHeader("Authorization") String token,@RequestBody SkillDTO skillDTO) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        // Giải mã token 
        String phonenumber = jwtUtils.extractUserName(token);
        System.out.println(phonenumber);
        var account = accountRepo.findByPhonenumber(phonenumber);
        if (account == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errCode", 1);
            errorResponse.put("errMessage", "Not access with authorization");
			return errorResponse;
        }
		Map<String, Object> response = service.createNewSkill(skillDTO);
        return response;
    }

	@PutMapping("/public/updateskill")
	public Map<String, Object> updateSkill(@RequestHeader("Authorization") String token,@RequestBody Skill skill) {
		if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        // Giải mã token 
        String phonenumber = jwtUtils.extractUserName(token);
        System.out.println(phonenumber);
        var account = accountRepo.findByPhonenumber(phonenumber);
        if (account == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errCode", 1);
            errorResponse.put("errMessage", "Not access with authorization");
			return errorResponse;
        }
		
		Map<String, Object> response = service.updateSkill(skill);
        return response;
	}
	
	@GetMapping("/public/getdetailsskillbyid")
	public Map<String, Object> getDetailSkillById(@RequestHeader("Authorization") String token,@RequestParam Integer id) {
		Map<String, Object> response = new HashMap<>();
		if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        // Giải mã token 
        String phonenumber = jwtUtils.extractUserName(token);
        var account = accountRepo.findByPhonenumber(phonenumber);
        System.out.println(account.getRoleCode().getCode());
        if (account == null || !account.getRoleCode().getCode().equals("ADMIN") ) {
            response.put("errCode", 1);
            response.put("errMessage", "Not access with authorization");
			return response;
        }
		return service.getDetailSkill(id);
	}
	
    @DeleteMapping("/public/delete-skill-id")
    public  Map<String, Object> deleteSkill(@RequestHeader("Authorization") String token,@RequestParam Integer id) {
        Map<String, Object> response = new HashMap<>();
		if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        // Giải mã token 
        String phonenumber = jwtUtils.extractUserName(token);
        var account = accountRepo.findByPhonenumber(phonenumber);
        System.out.println(account.getRoleCode().getCode());
        if (account == null || !account.getRoleCode().getCode().equals("ADMIN") ) {
            response.put("errCode", 1);
            response.put("errMessage", "Not access with authorization");
			return response;
        }

        return service.DeleteSkillById(id);
    }

}
	
	// @GetMapping("/public/get-list-skill")
	// public ResponseEntity<Map<String, Object>> getAllSkillJobCode (@RequestParam (required = false) String categoryJobCode,
	// 															@RequestParam(defaultValue = "6") int limit,
	// 															@RequestParam(defaultValue = "0") int offset,
	// 															@RequestParam (required = false) String search) {
	// 	Pageable pageable = PageRequest.of(offset, limit);
    //     if (search != null && !search.isEmpty()) 
    //     {
    //         search = "%" + search + "%";
    //     }
    //     Map<String, Object> response = service.getListSkill(pageable,search,categoryJobCode);
    //     return ResponseEntity.ok(response);
	// }
