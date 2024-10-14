package com.doan.AppTuyenDung.DTO.Request;

public class ChangePasswordRequest {
	public String phonenumber;
    public String password;
	public ChangePasswordRequest(String phonenumber, String password) {
		this.phonenumber = phonenumber;
		this.password = password;
	}
	public ChangePasswordRequest() {
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    // Getters and Setters
   
}