package com.doan.AppTuyenDung.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import com.doan.AppTuyenDung.DTO.Request.CompanyDTO;
import com.doan.AppTuyenDung.DTO.Response.ApiResponse;
import com.doan.AppTuyenDung.DTO.Response.CompanyResponse;
import com.doan.AppTuyenDung.Repositories.AccountRepository;

import com.doan.AppTuyenDung.Repositories.CodeRuleRepository;
import com.doan.AppTuyenDung.Repositories.CompanyRepository;
import com.doan.AppTuyenDung.Repositories.PostRepository;
import com.doan.AppTuyenDung.Repositories.UserRepository;
import com.doan.AppTuyenDung.Repositories.AllCode.CodeCensorStatusRepository;
import com.doan.AppTuyenDung.Repositories.AllCode.CodeStatusRepository;
import com.doan.AppTuyenDung.Services.CloudinaryService;
import com.doan.AppTuyenDung.Services.CompanyService;
import com.doan.AppTuyenDung.Services.JWTUtils;
import com.doan.AppTuyenDung.entity.Company;
import com.doan.AppTuyenDung.entity.Post;
import com.doan.AppTuyenDung.entity.User;
@RestController
@RequestMapping("/public")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JWTUtils jwtUtils; 
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

	 @PutMapping("/admin/company/ban/{copanyId}")
	 public ApiResponse<CompanyResponse> banCompany(@PathVariable int copanyId) {
		 ApiResponse<CompanyResponse> apiResponse = new ApiResponse<>();
		 apiResponse.setResult(companyService.banCompany(copanyId));
		 apiResponse.setMessage("Công ty đã bị cấm");
		 return apiResponse;
	 }
	 @PutMapping("/admin/company/unban/{copanyId}")
	 public ApiResponse<CompanyResponse> unBanCompany(@PathVariable int copanyId) {
		 ApiResponse<CompanyResponse> apiResponse = new ApiResponse<>();
		 apiResponse.setResult(companyService.unBanCompany(copanyId));
		 apiResponse.setMessage("Công ty đã được mở cấm");
		 return apiResponse;
	 }
	 @PutMapping("/admin/company/update/{companyID}")
	 public ApiResponse<CompanyResponse> updateCompany(@PathVariable int companyID, @RequestBody CompanyDTO companyDTO) {
		 CompanyResponse updateCPN = companyService.updateCompany(companyID, companyDTO);
		 ApiResponse<CompanyResponse> apiResponse = new ApiResponse<>();
		 apiResponse.setResult(updateCPN);
		 apiResponse.setMessage("Sửa công ty thành công");
		 return apiResponse;
	 }
	 @GetMapping("/admin/company/get_all_company")
	 public ApiResponse<List<CompanyResponse>> getAllCompany() {
		 ApiResponse<List<CompanyResponse>> lstCpn = new ApiResponse<>();
		 lstCpn.setResult(companyService.getCompanies());
		 lstCpn.setMessage("Hiện tất cả công ty thành công" );
		 return lstCpn;
	 }
	 @GetMapping("/admin/company/get_company/{companyID}")
	 public ApiResponse<CompanyResponse> getAllCompany(@PathVariable int companyID) {
		 ApiResponse<CompanyResponse> apiResponse = new ApiResponse<>();
		 apiResponse.setResult(companyService.getCompanyByID(companyID));
		 apiResponse.setMessage("Tìm thấy công ty với id: "+companyID );
		 return apiResponse;
    }
    @GetMapping("/admin/api/get-detail-company-by-userId")
    public ResponseEntity<?> getDetailCompanyByUserId(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "companyId", required = false) String companyId) 
    {
        Integer parsedUserId = userId != null && !"null".equalsIgnoreCase(userId) ? Integer.parseInt(userId) : null;
        Integer parsedCompanyId = companyId != null && !"null".equalsIgnoreCase(companyId) ? Integer.parseInt(companyId) : null;
        if (userId == null && companyId == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errCode", 1);
            errorResponse.put("errMessage", "Missing required parameters!");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Map<String, Object> response = companyService.getDetailCompanyByUserId(parsedUserId, parsedCompanyId);
        return ResponseEntity.ok(response);
    }
//    @GetMapping("path")
//    public Map<String, Object >getDetailCompanyById(@RequestParam Integer id) {
//        Map<String, Object> Response = new HashMap<>();
//        if (id == null) {
//            Response.put("errCode", 1);
//            Response.put("errMessage", "Missing required parameters!");
//        }
//        Optional<Company> optionalCompany = companyRepository.findById(id);
//        if (!optionalCompany.isPresent()) {
//            Response.put("errCode", 2);
//            Response.put("errMessage", "Company not found!");
//            return Response;
//        }
//        
//        Company company = optionalCompany.get();
//        List<User> users = userRepository.findByCompanyId(company.getId());
//        List<Integer> userIds = users.stream().map(User::getId).toList();
//        List<Post> posts = postRepository.findTop5ByStatusCodeAndUserIdIn("PS1", userIds);
//
//        // Assuming you want to set postData in the company object
//        // company.setPostData(posts);
//
//        // Handle file conversion if needed
//        if (company.getFile() != null) {
//               
//        }
//        Response.put("Error", 0);
//        Response.put("errMessage", "Successfully");
//        return Response;
//    }
    

    @PostMapping("/admin/create-company")
    public ResponseEntity<Map<String, Object>> createNewCompany(@RequestHeader("Authorization") String token, 
    @ModelAttribute Company company,@RequestPart("filethumb" ) MultipartFile filethumb,
    @RequestPart("fileCover")MultipartFile fileCover, @RequestPart(value="file",required = false)MultipartFile file) throws IOException
    {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        // Giải mã token 
        String phonenumber = jwtUtils.extractUserName(token);

        System.out.println(phonenumber);
        var account = accountRepo.findByPhonenumber(phonenumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        company.setUser(account.getUser());
        
        try{
            String base64Pdf = Base64.getEncoder().encodeToString(file.getBytes());
            String result = "data:application/pdf;base64," + base64Pdf;
            company.setFile(result.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> response = companyService.createNewCompany(company,filethumb,fileCover);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-list-company")
    public ResponseEntity<Map<String, Object>> getListCompany(@RequestParam(defaultValue = "10") int limit,
                                                              @RequestParam(defaultValue = "0") int offset,
                                                              @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(offset, limit);
        if (search != null && !search.isEmpty()) 
        {
            search = "%" + search + "%";
        }
        Map<String, Object> response = companyService.GetListCompany(search,pageable);
        return ResponseEntity.ok(response);
    }
    


}
