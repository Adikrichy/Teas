package org.aldousdev.teas.dto.request;

import lombok.Data;

@Data
public class PasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
