package org.aldousdev.teas.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class SigninRequest {
    private String email;
    private String password;


}

