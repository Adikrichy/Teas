package org.aldousdev.teas.service.user;

import jakarta.servlet.http.HttpServletResponse;
import org.aldousdev.teas.dto.request.SigninRequest;
import org.aldousdev.teas.dto.request.SignupRequest;
import org.aldousdev.teas.dto.response.SigninResponse;


public interface AuthenticationService {
    Long signup(SignupRequest signupRequest);
    SigninResponse signin(SigninRequest signinRequest, HttpServletResponse response);
    void logoutUser(HttpServletResponse response);
}