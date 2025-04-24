package org.aldousdev.teas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String nickname;
    private String email;
    private String password;


}
