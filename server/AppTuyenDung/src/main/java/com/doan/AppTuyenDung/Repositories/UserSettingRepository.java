package com.doan.AppTuyenDung.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doan.AppTuyenDung.entity.UserSetting;

public interface UserSettingRepository extends JpaRepository<UserSetting, Integer>  {
	public UserSetting findByUserId(Integer userId);
}
