package com.doan.AppTuyenDung.DTO.Response;

import lombok.Data;

@Data
public class UserSettingResponse {
	private int id;
    private String categoryJobCode;
    private String addressCode;
    private String salaryJobCode;
    private String experienceJobCode;
    private Boolean isTakeMail;
    private Boolean isFindJob;
    private String file;
    private int userId;
}
