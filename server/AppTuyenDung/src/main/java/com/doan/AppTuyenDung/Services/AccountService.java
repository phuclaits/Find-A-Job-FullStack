package com.doan.AppTuyenDung.Services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doan.AppTuyenDung.DTO.GetAllUserAdmin.AccountDTO;
import com.doan.AppTuyenDung.DTO.Request.ChangePasswordRequest;
import com.doan.AppTuyenDung.Repositories.AccountPagingAndSorting;
import com.doan.AppTuyenDung.Repositories.AccountRepository;
import com.doan.AppTuyenDung.Repositories.UserRepository;
import com.doan.AppTuyenDung.entity.Account;
import com.doan.AppTuyenDung.entity.User;
import java.util.stream.*;
import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
@Service
public class AccountService {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	UserRepository uRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	// public String changePassword(int idUser, ChangePasswordRequest changePass) {
	// 	Optional<User> uOptional = uRepository.findById(idUser);
	// 	User user = uOptional.get();
	// 	if(uOptional.get()==null) {
	// 		return "Không tìm thấy user";
	// 	}
		
	// 	String oldPassEncode = passwordEncoder.encode(changePass.oldPassword);

	// 	String newPassEncode = passwordEncoder.encode(changePass.newPassword);
	// 	Account account = accountRepository.findByUserId(idUser);
	// 	boolean isPasswordMatch = passwordEncoder.matches(changePass.oldPassword, account.getPassword());
    //   if (isPasswordMatch && !newPassEncode.isEmpty()) {
    //   	account.setPassword(newPassEncode);
    //   	Account accountSave = accountRepository.save(account);
    //   }
    //   else { 
    // 	  return "Password củ không đúng";
    //   }
	// 	return "Thay đổi password thành công";
	// }


	public Map<String, Object> checkUserPhone(String userPhone) {
        Map<String, Object> response = new HashMap<>();

        if (userPhone == null || userPhone.isEmpty()) {
            response.put("errCode", 1);
            response.put("errMessage", "Missing required parameters!");
            return response;
        }

        boolean accountOptional = accountRepository.existsByPhonenumber(userPhone);
        if (accountOptional == true) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }

        return response;
    }

	public Map<String, Object> changePasswordByPhone(String phonenumber, String newPassword) {
        Map<String, Object> response = new HashMap<>();
		
        Account account = accountRepository.findByPhonenumber(phonenumber);
        if (account != null) {
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
            response.put("errCode", 0);
            response.put("errMessage", "ok");
        } else {

            response.put("errCode", 1);
            response.put("errMessage", "SĐT không tồn tại");
        }

        return response;
    }


     // get list user => role ADMIN => /admin/list-user
     @Autowired
    private AccountPagingAndSorting accountPagingAndSorting;;

    public Page<AccountDTO> getAllUsers(String search, Pageable pageable) {
        
        return accountPagingAndSorting.findAllWithDetails(search, pageable);
    }


}
