package com.dw.dynamic.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PasswordDTO {

    private String userId;

    private String currentPassword; // 현재 비밀번호

    private String newPassword;      // 변경된 비밀번호

    private String confirmNewPassword; // 변경된 비밀번호 확인

}
