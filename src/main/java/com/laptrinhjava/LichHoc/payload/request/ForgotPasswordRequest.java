package com.laptrinhjava.LichHoc.payload.request;

import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {
    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
